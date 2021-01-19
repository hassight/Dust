package dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.index.CorruptIndexException;

import persistence.jdbc.Queries;
import persistence.lucene.Searcher;

public interface BDeAPI {
	void createIndex();
	
	void closeIndex();
	
	Queries executeSQLQuery(String query);
	
	Searcher executeTextualQuery(String query);
	
	boolean addSiteInDirectory(String name, String type, int price, double latitude, double longitude, String description);
	
	HashMap<BigDecimal, HashMap<String, String>> mixedQueries (String query) throws CorruptIndexException, SQLException, IOException;
	
	ArrayList<BigDecimal> sortHashMapKeys(HashMap<BigDecimal, HashMap<String, String>> unsortedHashMap);
}
