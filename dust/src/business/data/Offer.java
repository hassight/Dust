package business.data;

import java.util.ArrayList;

public class Offer {
	private String name;
	private Hotel hotel;
	private ArrayList<Excursion> excursions = new ArrayList<Excursion>();
	private double price;
	
	public Offer() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
