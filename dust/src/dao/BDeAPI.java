package dao;

import persistence.jdbc.Queries;
import persistence.lucene.Searcher;

public interface BDeAPI {
	Queries executeSQLRequest(String query);
	Searcher executeTextualRequest(String query);
	boolean addSiteInDirectory(String name, String type, int price, double latitude, double longitude, String description);
}
