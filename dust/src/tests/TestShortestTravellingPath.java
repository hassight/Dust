package tests;

import java.util.ArrayList;

import business.data.AbstractSite;
import business.data.Coordinates;
import business.data.HistoricSite;
import business.shortestpath.NearestNeighbour;
import business.shortestpath.Route;

public class TestShortestTravellingPath {

	public static void main(String[] args) {
		ArrayList<AbstractSite> sites = new ArrayList<AbstractSite>();
		
		AbstractSite s = new HistoricSite();
		s.setCoordinates(new Coordinates(48.94483845951156, 2.2032813591788947));
		s.setName("Maison");
		AbstractSite s1 = new HistoricSite();
		s1.setCoordinates(new Coordinates(48.946337522327184, 2.196872381163732));
		s1.setName("Carrefour sartrouville");
		AbstractSite s2 = new HistoricSite();
		s2.setCoordinates(new Coordinates(48.92837406849263, 2.144391006445354));
		s2.setName("Carrefour montesson");
		AbstractSite s3 = new HistoricSite();
		s3.setCoordinates(new Coordinates(49.00222858737012, 2.2225694890669843));
		s3.setName("Boulot");
		
		sites.add(s);
		sites.add(s1);
		sites.add(s2);
		sites.add(s3);
		
		Route shortestRoute = new NearestNeighbour().findShortestRoute(sites);
		
		System.out.println("Shortest path:\n" + shortestRoute);
		
		// Get each site name individually
		for (AbstractSite site : NearestNeighbour.getShortestRouteSites()) {
			System.out.println(site.getName());
		}
		
		System.out.println("Total distance: " + shortestRoute.calculateTotalDistance() + "km");
	}
}
