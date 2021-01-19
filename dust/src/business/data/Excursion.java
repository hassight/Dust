package business.data;

import java.util.ArrayList;

public class Excursion {
	private ArrayList<Ride> rides = new ArrayList<Ride>();
	private ArrayList<AbstractSite> visitedSites = new ArrayList<AbstractSite>();
	private boolean rest;
	
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

	public boolean isRestDay() {
		return rest;
	}

	public void setRestDay(boolean restDay) {
		this.rest = restDay;
	}

	@Override
	public String toString() {
		return "Excursion [rides: " + rides + ", visitedSites: " + visitedSites + "]";
	}
}
