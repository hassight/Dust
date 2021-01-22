package tests;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.ScoreDoc;

import persistence.QueriesManager;
import persistence.jdbc.Queries;
import persistence.lucene.Searcher;

public class TestMixedQuery {

	public static void main(String[] args) {
		HashMap<BigDecimal, HashMap<String, String>> hashMap;
		try {
			// MIXED QUERY
			hashMap = QueriesManager.getInstance().mixedQueries("SELECT * FROM site WITH parc WHERE type = 'historic'");
			System.out.println("HASHMAP RESULTS:\n" + hashMap + "\n");
			System.out.println("SORTED KEYS:\n" + QueriesManager.getInstance().sortHashMapKeys(hashMap) + "\n");
			
			// SQL QUERY
			Queries queries = QueriesManager.getInstance().executeSQLQuery("SELECT * FROM site WHERE type = 'activity'");
			ResultSet results = queries.getResultsSet();
			
			while (queries.nextIterator()) {
				System.out.println("id: " + results.getInt(1) + ", name: " + results.getString(2) + ", type: " + results.getString(3)
				+ ", price: " + results.getInt(4) + ", id_coords: " + results.getInt(5) + "\n");
			}
			
			// TEXTUAL QUERY
			Searcher searcher = QueriesManager.getInstance().executeTextualQuery("parc");
			ScoreDoc currentScoreDoc;
			
			while((currentScoreDoc = searcher.nextIterator()) != null) {
				int fileName = Integer.valueOf(searcher.getDocumentName(currentScoreDoc, QueriesManager.getInstance().getIndexer().getFirstFieldName()));
				float currentScore = searcher.getDocumentScore(currentScoreDoc);
				System.out.println(fileName + "\n" + currentScore);
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
