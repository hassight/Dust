package business.engine;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.index.CorruptIndexException;

import business.data.AbstractSite;
import business.data.ActivitySite;
import business.data.Coordinates;
import business.data.Distance;
import business.data.Excursion;
import business.data.HistoricSite;
import business.data.Hotel;
import business.data.Offer;
import business.data.OfferEntry;
import business.data.Ride;
import business.data.Transport;
import business.data.TransportEnum;
import business.spring.SpringIoC;
import persistence.QueriesManager;
import persistence.jdbc.Queries;

public class OfferCalculator {
	private OfferEntry offerEntry;
	
	public OfferCalculator(OfferEntry offerEntry) {
		this.offerEntry = offerEntry; 
	}
	
	public void initExcursions(OfferEntry offerEntry, Offer offer) {
		ArrayList<Excursion> excursions = new ArrayList<Excursion>();
		
		int randomNumberExcursions = (int) (Math.random() * ((offerEntry.getMaxVisitDuration() - offerEntry.getMinVisitDuration()) + 1)) + offerEntry.getMinVisitDuration();
	
		for (int i = 0; i < randomNumberExcursions; i++) {
			Excursion e = (Excursion) SpringIoC.getBean("excursion");
			excursions.add(e);
		}
		
		offer.setExcursions(excursions);
		
		int max = excursions.size() - 1, min = 0;
		int index;
		
		switch (offerEntry.getPace()) {
			// The lower the pace, the more rest days there are
			case 1:
				for (int i = 0; i < (Math.round(excursions.size() * 0.67)); i++) {
					index = (int) (Math.random() * ((max - min) + 1)) + min;
					
					while (excursions.get(index).isRest()) {
						index = (int) (Math.random() * ((max - min) + 1)) + min;
					}
					excursions.get(index).setRest(true);
				}
				break;
			case 2:
				for (int i = 0; i < (Math.round(excursions.size() * 0.5)); i++) {
					index = (int) (Math.random() * ((max - min) + 1)) + min;
					
					while (excursions.get(index).isRest()) {
						index = (int) (Math.random() * ((max - min) + 1)) + min;
					}
					excursions.get(index).setRest(true);
				}
				break;
			case 3:
				index = (int) (Math.random() * ((max - min) + 1)) + min;
				excursions.get(index).setRest(true);
				break;
			default:
				break;
		}
	}
	
	private ArrayList<Hotel> getHotels() {
		ArrayList<Hotel> hotelsList = new ArrayList<Hotel>();
		String hotelsQuery = "SELECT id_hotel, name, price, beach_name, latitude, longitude FROM hotel "
				+ "INNER JOIN coordinates ON coordinates.id_coordinates = hotel.id_coordinates";
		Queries queries = QueriesManager.getInstance().executeSQLQuery(hotelsQuery);
		ResultSet hotels = queries.getResultsSet();
		try {
			while(queries.nextIterator()) {
				Hotel hotel = (Hotel) SpringIoC.getBean("hotel");
				hotel.setName(hotels.getString(2));
				hotel.setPrice(hotels.getInt(3));
				hotel.setBeachName(hotels.getString(4));
				hotel.setCoordinates(new Coordinates(hotels.getDouble(5), hotels.getDouble(6)));
				hotelsList.add(hotel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return hotelsList;
	}
	
	private ArrayList<Ride> getRides(String siteType){
		ArrayList<Ride> ridesList = new ArrayList<Ride>();
		ArrayList<Integer> idSitesWithType = new ArrayList<Integer>();
		
		try {
			String query =  "SELECT * FROM site";
			if(!siteType.trim().isEmpty()) {
				query += " WHERE type = '" + siteType + "'";
			}
			
			Queries queries = QueriesManager.getInstance().executeSQLQuery(query);
			ResultSet sites = queries.getResultsSet();
			
			while(queries.nextIterator()) {
				int currentSite = sites.getInt(1);
				String currentSiteType = sites.getString(3);
				
				if(currentSiteType.equals(siteType)|| siteType.trim().isEmpty()) {
					idSitesWithType.add(currentSite);
				}
			}
			
			for(Integer key : idSitesWithType) {
				String rideQuery = "SELECT departureSite.name, departureSite.type, departureSite.price, "
						+ "departureCoord.latitude, departureCoord.longitude, "
						+ "arrivalSite.name, arrivalSite.type, arrivalSite.price, "
						+ "arrivalCoord.latitude, arrivalCoord.longitude, "
						+ "transport.type, transport.price FROM ride "
						+ "INNER JOIN site AS departureSite ON departureSite.id_site = ride.departure_site "
						+ "INNER JOIN site AS arrivalSite ON arrivalSite.id_site = ride.arrival_site "
						+ "INNER JOIN coordinates AS departureCoord ON departureCoord.id_coordinates = departureSite.id_coordinates "
						+ "INNER JOIN coordinates AS arrivalCoord ON arrivalCoord.id_coordinates = arrivalSite.id_coordinates "
						+ "INNER JOIN transport ON transport.id_transport = ride.id_transport "
						+ "WHERE ride.departure_site = " + key + " OR ride.arrival_site = " + key;
				queries = QueriesManager.getInstance().executeSQLQuery(rideQuery);
				ResultSet rides = queries.getResultsSet();
				
				try {
					while(queries.nextIterator()) {
						AbstractSite departureSite, arrivalSite;
						Transport transport;

						String departureSiteType = rides.getString(2);
						String arrivalSiteType = rides.getString(7);
						
						if (departureSiteType.equals("historic")) {
							departureSite = (HistoricSite) SpringIoC.getBean("historic");
						} else {
							departureSite = (ActivitySite) SpringIoC.getBean("activity");
						}
						
						if (arrivalSiteType.equals("historic")) {
							arrivalSite = (HistoricSite) SpringIoC.getBean("historic");
						} else {
							arrivalSite = (ActivitySite) SpringIoC.getBean("activity");
						}
						
						departureSite.setName(rides.getString(1));
						departureSite.setPrice(rides.getInt(3));
						departureSite.setCoordinates(new Coordinates(rides.getDouble(4), rides.getDouble(5)));
						
						arrivalSite.setName(rides.getString(6));
						arrivalSite.setPrice(rides.getInt(8));
						arrivalSite.setCoordinates(new Coordinates(rides.getDouble(9), rides.getDouble(10)));
						
						String transportType = rides.getString(11);
						transport = (Transport) SpringIoC.getBean("transport");
						if (transportType.equals("bus")) {
							transport.setType(TransportEnum.BUS);
						} else {
							transport.setType(TransportEnum.BOAT);
						}
						transport.setPrice(rides.getInt(12));
						
						if (siteType.trim().isEmpty() || (departureSiteType.equals(siteType) && arrivalSiteType.equals(siteType))) {
							Ride ride = (Ride) SpringIoC.getBean("ride");
							ride.setDepartureSite(departureSite);
							ride.setArrivalSite(arrivalSite);
							ride.setTransport(transport);
							ridesList.add(ride);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ridesList;
	}
	
	private ArrayList<Ride> getRidesLucene(String enteredKeywords, String siteType){
		ArrayList<Ride> ridesList = new ArrayList<Ride>();
		
		try {
			String query =  "SELECT * FROM site";
			if(!siteType.trim().isEmpty()) {
				query += " WHERE type = '" + siteType + "'";
			}
			query += " WITH " + enteredKeywords;
			
			HashMap<BigDecimal, HashMap<String, String>> sites = QueriesManager.getInstance().mixedQueries(query);
			ArrayList<BigDecimal> keys = QueriesManager.getInstance().sortHashMapKeys(sites);
			
			for(BigDecimal key : keys) {
				if(siteType.trim().equals("") || siteType.equals(sites.get(key).get("type"))) {
					HashMap<String,String> currentSiteResults = sites.get(key);

					String rideQuery = "SELECT departureSite.name, departureSite.type, departureSite.price, "
							+ "departureCoord.latitude, departureCoord.longitude, "
							+ "arrivalSite.name, arrivalSite.type, arrivalSite.price, "
							+ "arrivalCoord.latitude, arrivalCoord.longitude, "
							+ "transport.type, transport.price FROM ride "
							+ "INNER JOIN site AS departureSite ON departureSite.id_site = ride.departure_site "
							+ "INNER JOIN site AS arrivalSite ON arrivalSite.id_site = ride.arrival_site "
							+ "INNER JOIN coordinates AS departureCoord ON departureCoord.id_coordinates = departureSite.id_coordinates "
							+ "INNER JOIN coordinates AS arrivalCoord ON arrivalCoord.id_coordinates = arrivalSite.id_coordinates "
							+ "INNER JOIN transport ON transport.id_transport = ride.id_transport "
							+ "WHERE ride.departure_site = " + currentSiteResults.get("id_site") + " OR ride.arrival_site = " + currentSiteResults.get("id_site");
					Queries queries = QueriesManager.getInstance().executeSQLQuery(rideQuery);
					ResultSet rides = queries.getResultsSet();
					
					try {
						while(queries.nextIterator()) {
							AbstractSite departureSite, arrivalSite;
							Transport transport;
	
							String departureSiteType = rides.getString(2);
							String arrivalSiteType = rides.getString(7);
							
							if (departureSiteType.equals("historic")) {
								departureSite = (HistoricSite) SpringIoC.getBean("historic");
							} else {
								departureSite = (ActivitySite) SpringIoC.getBean("activity");
							}
							
							if (arrivalSiteType.equals("historic")) {
								arrivalSite = (HistoricSite) SpringIoC.getBean("historic");
							} else {
								arrivalSite = (ActivitySite) SpringIoC.getBean("activity");
							}
							
							departureSite.setName(rides.getString(1));
							departureSite.setPrice(rides.getInt(3));
							departureSite.setCoordinates(new Coordinates(rides.getDouble(4), rides.getDouble(5)));
							
							arrivalSite.setName(rides.getString(6));
							arrivalSite.setPrice(rides.getInt(8));
							arrivalSite.setCoordinates(new Coordinates(rides.getDouble(9), rides.getDouble(10)));
							
							String transportType = rides.getString(11);
							transport = (Transport) SpringIoC.getBean("transport");
							if (transportType.equals("bus")) {
								transport.setType(TransportEnum.BUS);
							} else {
								transport.setType(TransportEnum.BOAT);
							}
							transport.setPrice(rides.getInt(12));
							
							if (siteType.trim().isEmpty() || (departureSiteType.equals(siteType) && arrivalSiteType.equals(siteType))) {
								Ride ride = (Ride) SpringIoC.getBean("ride");
								ride.setDepartureSite(departureSite);
								ride.setArrivalSite(arrivalSite);
								ride.setTransport(transport);
								ridesList.add(ride);
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return ridesList;
	}
	
	public void calculateOfferPrice(ArrayList<Offer> offersList) {
		for(Offer offer : offersList) {
			double distance = 0;
			int hotelPrice = offer.getHotel().getPrice() * 4;
			int transportPrice = 0, sitePrice = 0;
			ArrayList<Excursion> excursions = offer.getExcursions();
			
			Coordinates hotelCoordinates = new Coordinates(offer.getHotel().getCoordinates().getLatitude(), offer.getHotel().getCoordinates().getLongitude());
			
			for(Excursion excursion : excursions) {
				ArrayList<Ride> ridesList = excursion.getRides();
				
				if(!excursion.isRest()) {
					sitePrice += ridesList.get(0).getDepartureSite().getPrice();
					for(Ride ride : ridesList) {
						sitePrice += ride.getArrivalSite().getPrice();
						
						Coordinates departureSiteCoordinates = new Coordinates(ride.getDepartureSite().getCoordinates().getLatitude(), ride.getDepartureSite().getCoordinates().getLongitude());
						Coordinates arrivalSiteCoordinates = new Coordinates(ride.getArrivalSite().getCoordinates().getLatitude(), ride.getArrivalSite().getCoordinates().getLongitude());
						
						distance = Distance.getKMDistance(departureSiteCoordinates, arrivalSiteCoordinates);
						
						transportPrice += (int) Math.floor(ride.getTransport().getPrice() + (distance * Transport.FACTOR));
					}
					
					Coordinates firstSiteCoordinates = new Coordinates(ridesList.get(0).getDepartureSite().getCoordinates().getLatitude(), ridesList.get(0).getDepartureSite().getCoordinates().getLongitude());
					Coordinates lastSiteCoordinates = new Coordinates(ridesList.get(ridesList.size() - 1).getDepartureSite().getCoordinates().getLatitude(), ridesList.get(ridesList.size() - 1).getDepartureSite().getCoordinates().getLongitude());
					
					distance = Distance.getKMDistance(hotelCoordinates, firstSiteCoordinates);
					transportPrice += (int) Math.floor(ridesList.get(0).getTransport().getPrice() + (distance * Transport.FACTOR));

					distance = Distance.getKMDistance(lastSiteCoordinates, hotelCoordinates);
					transportPrice += (int) Math.floor(ridesList.get(ridesList.size()-1).getTransport().getPrice() + (distance * Transport.FACTOR));
				}
			}
			
			int totalPrice = hotelPrice + transportPrice + sitePrice;
			offer.setPrice(totalPrice);
		}
	}
	
	public ArrayList<Offer> getOffers() {
		ArrayList<Offer> generatedOffersList = new ArrayList<Offer>();
		ArrayList<Offer> sortedOffersList = new ArrayList<Offer>();
		ArrayList<Hotel> hotelsList = new ArrayList<Hotel>();
		ArrayList<Ride> ridesList = new ArrayList<Ride>();
		
		hotelsList = this.getHotels();
		if(offerEntry.getKeywords().trim().isEmpty()) {
			ridesList = this.getRides(offerEntry.getSiteType());
		} else {
			ridesList = this.getRidesLucene(offerEntry.getKeywords(), offerEntry.getSiteType());
		}
		
		for(Hotel hotel : hotelsList) {
			Offer offer = (Offer) SpringIoC.getBean("offer");
			offer.setHotel(hotel);
			initExcursions(offerEntry, offer);
			generatedOffersList.add(offer);
		}
		
		ExcursionUtility.organizeExcursions(generatedOffersList, ridesList);
		
		this.calculateOfferPrice(generatedOffersList);

		for(Offer currentOffer : generatedOffersList) {
			if (currentOffer.getPrice() >= offerEntry.getMinPrice() && currentOffer.getPrice() <= offerEntry.getMaxPrice()) {
				sortedOffersList.add(0, currentOffer);
			} else {
				sortedOffersList.add(currentOffer);
			}
		}
		initOfferInfo(sortedOffersList);
		
		return sortedOffersList;
	}
	
	public void initOfferInfo(ArrayList<Offer> offers) {
		int offerNum = 1;
		for(Offer offer: offers) {
			offer.setName("Offer n°" + offerNum++);
			for(int index=0;index<offer.getExcursions().size();index++) {
				Excursion excursion = offer.getExcursions().get(index);
				ExcursionUtility.setExcursionDescription(excursion);
				excursion.setName("Day n°" + index);
			}	
		}
	}

	public OfferEntry getOfferEntry() {
		return offerEntry;
	}

	public void setOfferEntry(OfferEntry offerEntry) {
		this.offerEntry = offerEntry;
	}
}