package business;

import java.util.ArrayList;

public class Excursion {
	private ArrayList<Ride> rides = new ArrayList<Ride>();
	private ArrayList<Place> places = new ArrayList<Place>();
	private double price;
	
	public Excursion() {
		
	}

	public ArrayList<Ride> getRides() {
		return rides;
	}

	public void setRides(ArrayList<Ride> rides) {
		this.rides = rides;
	}

	public ArrayList<Place> getPlaces() {
		return places;
	}

	public void setPlaces(ArrayList<Place> places) {
		this.places = places;
	}
	
	public double getPrice() {
		for (Ride ride : rides) {
			price += ride.getTripCost();
		}
		
		for (Place place : places) {
			price += place.getPrice();
		}
		
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Excursion [rides: " + rides + ", places: " + places + "]";
	}
}
