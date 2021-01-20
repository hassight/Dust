package business;

import java.util.ArrayList;
import java.util.Iterator;

import business.data.*;


public class ExcursionCalculator {
	
	public static void initSiteList(Excursion excursion){
		ArrayList<Ride> rides =  excursion.getRides();
		ArrayList<AbstractSite> sites = new ArrayList<AbstractSite>();
		for(int index = 0; index<rides.size();index++) {
			AbstractSite arrival = rides.get(index).getArrivalSite();
			AbstractSite departure = rides.get(index).getDepartureSite();
			if(index == 0) {
				sites.add(departure);
			}
			sites.add(arrival);
		}
		excursion.setVisitedSites(sites);
	}
	
	public static double getDistanceInKM(double lat1, double long1, double lat2, double long2) {
		double earthRadius = 6378.137;
		double distance;
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);
		long1 = Math.toRadians(long1);
		long2 = Math.toRadians(long2);
		distance = Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(long1-long2));
		return earthRadius * distance;
	}
	
	public static int getNearestSiteToRide(ArrayList<Offer> offers, ArrayList<Ride> rides) {
		int nearest = -1;
		double distanceMin = 6378.137;
		
		for(Offer offer : offers) {
			Hotel hotel = offer.getHotel();
			for(int i=0 ; i < rides.size() ; i++) {
				double hotelLatitude = hotel.getCoordinates().getLatitude();
				double hotelLongitude = hotel.getCoordinates().getLongitude();
				double siteLatitude = rides.get(i).getDepartureSite().getCoordinates().getLatitude();
				double siteLongitude = rides.get(i).getDepartureSite().getCoordinates().getLongitude();
				
				double distance = ExcursionCalculator.getDistanceInKM(hotelLatitude, hotelLongitude, siteLatitude, siteLongitude);
				
				if(distance <= distanceMin) {
					distanceMin = distance;
					nearest = i;
				}
			}
		}
		
		return nearest;
	}
	
	public static int getNextSiteToRide(ArrayList<Ride> rides, AbstractSite arrivalSite) {
		int index;
		if(rides.size()>0) {
			do {
				index = (int)(Math.random()*(rides.size()));
			}while(!rides.get(index).getDepartureSite().getName().equals(arrivalSite.getName()));
		}
		else {
			index = -1;
		}	
		return index;
	}
	
	public static void organizeExcursions(ArrayList<Offer> offers, ArrayList<Ride> rides) {
		for(Offer offer : offers) {
			ArrayList<Excursion> excursions = offer.getExcursions();
			
			ArrayList<Ride> currentRides = new ArrayList<Ride>();
			Iterator<Ride> iterator = rides.iterator();
			while(iterator.hasNext()){
				currentRides.add((Ride) iterator.next());	
			}
			
			for(Excursion excursion : excursions) {
				if(!excursion.isRest()) {
					int nearestRide = ExcursionCalculator.getNearestSiteToRide(offers, currentRides);
					if(nearestRide == -1) {
						excursion.setRest(true);
						continue;
					}
					excursion.getRides().add(currentRides.get(nearestRide));
					AbstractSite arrivalSite = currentRides.get(nearestRide).getArrivalSite();
					AbstractSite departureSite = currentRides.get(nearestRide).getDepartureSite();
					ExcursionCalculator.removeLinkedRides(arrivalSite, departureSite, currentRides);
					
					int nextRide = ExcursionCalculator.getNextSiteToRide(currentRides, arrivalSite);
					if(nextRide == -1) {
						excursion.setRest(true);
						continue;
					}
					excursion.getRides().add(currentRides.get(nextRide));
					ExcursionCalculator.removeLinkedRides(arrivalSite, departureSite, currentRides);
				}
			}
		}
	}

	private static void removeLinkedRides(AbstractSite arrivalSite, AbstractSite departureSite,
			ArrayList<Ride> currentRides) {
		ArrayList<Ride> toRemove = new ArrayList<Ride>();
		for(int i=0 ; i<currentRides.size() ; i++) {
			if(currentRides.get(i).getArrivalSite().getName().equals(arrivalSite.getName())
				|| currentRides.get(i).getArrivalSite().getName().equals(departureSite.getName())
				|| currentRides.get(i).getDepartureSite().getName().equals(departureSite.getName())
				) {
				toRemove.add(currentRides.get(i));
			}
		}
		for(Ride ride:toRemove) {
			currentRides.remove(ride);
		}
		
	}
	
	public static void setExcursionDescription(Excursion excursion) {
		if(excursion.isRest()) {
			excursion.setDescription("Journée de repos !");
		}
		else {
			initSiteList(excursion);
			for(int index = 0; index<excursion.getVisitedSites().size();index++) {
				AbstractSite site = excursion.getVisitedSites().get(index);
				if(index!=0) {
					excursion.setDescription(excursion.getDescription()+" ----> "+site.getName());
				}
				else {
					excursion.setDescription(site.getName());
				}
				
			}
		}
	}
	
}
