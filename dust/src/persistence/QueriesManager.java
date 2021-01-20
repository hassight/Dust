package persistence;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.ScoreDoc;

import com.mysql.jdbc.ResultSetMetaData;

import dao.BDeAPI;
import persistence.jdbc.Queries;
import persistence.lucene.Indexer;
import persistence.lucene.Searcher;

public class QueriesManager implements BDeAPI {
	private static QueriesManager instance = new QueriesManager();
	private Indexer indexer = new Indexer();
	private Searcher searcher;
	private boolean alreadyIndexed = false;
	private HashMap<BigDecimal, HashMap<String, String>> mixedQueryResults;
	
	public static QueriesManager getInstance() {
		return instance;
	}

	@Override
	public void createIndex() {
		Indexer indexer = this.getIndexer();
		
		try {
			indexer.initIndexer();
			indexer.createIndexFromDirectory();
			this.setIndexer(indexer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override	
	public void closeIndex() {
		try {
			this.getIndexer().closeIndexer();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addSiteInDirectory(String directory, String name, String type, int price, double latitude, double longitude, String description) {
		if(!this.isAlreadyIndexed()) {
			this.createIndex();
			this.setAlreadyIndexed(true);
		}
		
		Queries queries = new Queries();
		int idSite = queries.addSite(name, type, price, latitude, longitude);
		String fileName = directory + "/" + String.valueOf(idSite) + ".txt";
		
		boolean creationSuccess = FileManager.createFile(fileName);
		
		if (creationSuccess) {
			boolean modificationSuccess = FileManager.writeInFile(fileName, description);
			
			if (modificationSuccess) {
				try {
					this.getIndexer().createIndexFromFile(new File(fileName));
					
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}	
	}
	
	@Override
	public Queries executeSQLQuery(String query) {
		if(this.isAlreadyIndexed() == false) {
			this.createIndex();
			this.setAlreadyIndexed(true);
		}
		
		// We call Queries class to execute the SQL part (using JDBC)
		Queries queries = new Queries();
		queries.executeQuery(query);
		
		return queries;
	}

	@Override
	public Searcher executeTextualQuery(String halfQuery) {
		if(this.isAlreadyIndexed() == false) {
			this.createIndex();
			this.setAlreadyIndexed(true);
		}
		this.closeIndex();
		
		try {
			this.setSearcher(new Searcher(this.getIndexer().getIndexDirectoryPath(), this.getIndexer().getSecondFieldName()));
			
			try {
				this.getSearcher().search(this.getIndexer().getMaxSearch(), halfQuery);
			} catch (org.apache.lucene.queryparser.classic.ParseException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return this.getSearcher();
	}
	
	
	@Override
	public HashMap<BigDecimal, HashMap<String, String>> mixedQueries (String query) throws CorruptIndexException, SQLException, IOException {
		if (!this.isAlreadyIndexed()) {
			this.createIndex();
			this.setAlreadyIndexed(true);
		}
		
		// We split the query in two to execute the SQL part and the textual part individually
		String[] decomposedQuery = query.split(" WITH ");
		ResultSet sqlResult = this.executeSQLQuery(decomposedQuery[0]).getResultsSet();
		this.executeTextualQuery(decomposedQuery[1]);
		
		ScoreDoc currentScore;
		int foundDocuments = 0;
		// We create a HashMap to store for each score, the SQL results associated
		this.mixedQueryResults = new HashMap<BigDecimal, HashMap<String, String>>();
		
		while (foundDocuments < this.getSearcher().getDocsIterator().length && sqlResult.next()) {
			while ((currentScore = this.getSearcher().nextIterator()) != null) {
				int fileName = Integer.valueOf(this.getSearcher().getDocumentName(currentScore, this.getIndexer().getFirstFieldName()));
				
				// If filename (primary key here) is equal to primary key from table: simulate join of both results
				if (fileName == sqlResult.getInt(1)) {
					HashMap<String, String> sitesResults = new HashMap<String, String>();
					
					ResultSetMetaData metaData = (ResultSetMetaData) sqlResult.getMetaData();
					
					// We put every column name and its value, inside the sitesResult HashMap
					for (int i = 1; i <= metaData.getColumnCount(); i++) {
						switch ((int) metaData.getColumnType(i)) {
							// 4 is an integer
							case 4:
								sitesResults.put(metaData.getColumnName(i), String.valueOf(sqlResult.getInt(i)));
								break;
							// Everything else should be a string in our case
							default:
								sitesResults.put(metaData.getColumnName(i), String.valueOf(sqlResult.getString(i)));
								break;
						}
					}
					this.mixedQueryResults.put(BigDecimal.valueOf(currentScore.score), sitesResults);
					foundDocuments++;
				}
			}
			searcher.initIterator();
		}

		return this.getMixedQueryResults();
	}
	
	@Override
	public ArrayList<BigDecimal> sortHashMapKeys(HashMap<BigDecimal, HashMap<String, String>> unsortedHashMap) {
		ArrayList<BigDecimal> sortedKeys = new ArrayList<BigDecimal>(unsortedHashMap.keySet());
		Collections.sort(sortedKeys);
		Collections.reverse(sortedKeys);
		
		return sortedKeys;
	}

	public Indexer getIndexer() {
		return indexer;
	}

	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}

	public Searcher getSearcher() {
		return searcher;
	}

	public void setSearcher(Searcher searcher) {
		this.searcher = searcher;
	}

	public boolean isAlreadyIndexed() {
		return alreadyIndexed;
	}

	public void setAlreadyIndexed(boolean alreadyIndexed) {
		this.alreadyIndexed = alreadyIndexed;
	}

	public HashMap<BigDecimal, HashMap<String, String>> getMixedQueryResults() {
		return mixedQueryResults;
	}
}
