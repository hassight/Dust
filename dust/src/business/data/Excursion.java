package business.data;

import java.util.ArrayList;

public class Excursion {
	private ArrayList<Ride> rides = new ArrayList<Ride>();
	private ArrayList<AbstractSite> visitedSites = new ArrayList<AbstractSite>();
	private boolean rest;
	private String description;
	private String name;
	
	public Excursion() {
		
	}

	public ArrayList<Ride> getRides() {
		return rides;
	}

	public void setRides(ArrayList<Ride> rides) {
		this.rides = rides;
	}

	public ArrayList<AbstractSite> getVisitedSites() {
		return visitedSites;
	}

	public void setVisitedSites(ArrayList<AbstractSite> visitedSites) {
		this.visitedSites = visitedSites;
	}

	public boolean isRest() {
		return rest;
	}

	public void setRest(boolean rest) {
		this.rest = rest;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Excursion [rides: " + rides + ", visitedSites: " + visitedSites + ", name: " + name + ", rest day: " + rest
				+ ", description: " + description + "]";
	}	
}
