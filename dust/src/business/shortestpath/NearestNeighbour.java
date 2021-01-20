package business.shortestpath;

import java.util.ArrayList;

import business.data.AbstractSite;
import business.data.Distance;

public class NearestNeighbour {
	private static ArrayList<AbstractSite> shortestRouteSites;

	public Route findShortestRoute(ArrayList<AbstractSite> sites) {
		shortestRouteSites = new ArrayList<AbstractSite>(sites.size());
		
		AbstractSite site = sites.get((int) (sites.size() * Math.random()));
		updateRoutes(shortestRouteSites, sites, site);
		while (sites.size() >= 1) {
			site = getNextSite(sites, site);
			updateRoutes(shortestRouteSites, sites, site);
		}
		
		return new Route(shortestRouteSites);
	}
	
	private void updateRoutes(ArrayList<AbstractSite> shortestRouteSites, ArrayList<AbstractSite> sites, AbstractSite site) {
		shortestRouteSites.add(site);
		sites.remove(site);
	}
	
	private AbstractSite getNextSite(ArrayList<AbstractSite> sites, AbstractSite site) {
		return sites.stream().min((site1, site2) -> {
			int flag = 0;
			if (Distance.getKMDistance(site.getCoordinates(), site1.getCoordinates()) < Distance.getKMDistance(site.getCoordinates(), site2.getCoordinates())) {
				flag = -1;
			} else if (Distance.getKMDistance(site.getCoordinates(), site1.getCoordinates()) > Distance.getKMDistance(site.getCoordinates(), site2.getCoordinates())) {
				flag = 1;
			}
			
			return flag;
		}).get();
	}
	
	public static ArrayList<AbstractSite> getShortestRouteSites() {
		return shortestRouteSites;
	}
}
