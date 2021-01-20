package business.shortestpath;

import java.util.ArrayList;

import business.data.AbstractSite;
import business.data.Distance;

public class Route {
	private ArrayList<AbstractSite> sites = new ArrayList<AbstractSite>();
	
	public Route(ArrayList<AbstractSite> sites) {
		this.sites = sites;
	}

	public ArrayList<AbstractSite> getSites() {
		return sites;
	}
	
	public int calculateTotalDistance() {
		int sitesSize = this.getSites().size();
		
		return (int) (this.getSites().stream().mapToDouble(x -> {
			int siteIndex = this.getSites().indexOf(x);
			double returnValue = 0;
			if (siteIndex < sitesSize - 1) {
				returnValue = Distance.getKMDistance(this.getSites().get(siteIndex).getCoordinates(), this.getSites().get(siteIndex + 1).getCoordinates());
			}
			
			return returnValue;
		}).sum() + Distance.getKMDistance(this.getSites().get(sitesSize - 1).getCoordinates(), this.getSites().get(0).getCoordinates()));
	}
	
	public String toString() {
		String characters = "";
		
		for (AbstractSite site : sites) {
			characters += site.toString() + "\n";
		}
		
		return characters;
	}
}
