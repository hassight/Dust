package business;

import java.util.ArrayList;

public class Offer {
	private Hotel hotel;
	private ArrayList<Excursion> excursions = new ArrayList<Excursion>();
	private double price;
	
	public Offer() {
		
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public ArrayList<Excursion> getExcursions() {
		return excursions;
	}

	public void setExcursions(ArrayList<Excursion> excursions) {
		this.excursions = excursions;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Offer [price: " + price + ", hotel: " + hotel + ", excursions: " + excursions + "]";
	}
}