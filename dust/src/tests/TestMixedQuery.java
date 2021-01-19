package tests;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.lucene.index.CorruptIndexException;

import persistence.QueriesManager;

public class TestMixedQuery {

	public static void main(String[] args) {
		HashMap<BigDecimal, HashMap<String, String>> hashMap;
		try {
			hashMap = QueriesManager.getInstance().mixedQueries("SELECT * FROM site WITH parc");
			System.out.println("HASHMAP RESULTS:\n" + hashMap + "\n");
			System.out.println("SORTED KEYS:\n" + QueriesManager.getInstance().sortHashMapKeys(hashMap));
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
