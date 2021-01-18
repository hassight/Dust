package persistence;


import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

import org.apache.lucene.index.CorruptIndexException;

import dao.BDeAPI;
import persistence.jdbc.Queries;
import persistence.lucene.Indexer;
import persistence.lucene.Searcher;

public class QueriesManager implements BDeAPI {
	private static QueriesManager instance = new QueriesManager();
	private Indexer indexer = new Indexer();
	private Searcher searcher;
	private boolean alreadyIndexed = false;
	
	public static QueriesManager getInstance() {
		return instance;
	}

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
	
	public void closeIndex() {
		try {
			this.getIndexer().closeIndexer();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addSiteInDirectory(String name, String type, int price, double latitude, double longitude, String description) {
		if(!this.isAlreadyIndexed()) {
			this.createIndex();
			this.setAlreadyIndexed(true);
		}
		
		Queries queries = new Queries();
		int idSite = queries.addSite(name, type, price, latitude, longitude);
		String fileName = "data/" + String.valueOf(idSite) + ".txt";
		
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
	public Queries executeSQLRequest(String query) {
		if(this.isAlreadyIndexed() == false) {
			this.createIndex();
			this.setAlreadyIndexed(true);
		}
		
		Queries queries = new Queries();
		queries.executeQuery(query);
		
		return queries;
	}

	@Override
	public Searcher executeTextualRequest(String query) {
		if(this.isAlreadyIndexed() == false) {
			this.createIndex();
			this.setAlreadyIndexed(true);
		}
		this.closeIndex();
		
		try {
			this.setSearcher(new Searcher(this.getIndexer().getIndexDirectoryPath(), this.getIndexer().getSecondFieldName()));
			
			try {
				this.getSearcher().search(this.getIndexer().getMaxSearch(), query);
			} catch (org.apache.lucene.queryparser.classic.ParseException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return this.getSearcher();
	}
	
	
	// REQUETE MIXTE
	/*public Hashmap<BigDecimal, int id> mixedQueries (String query) {
		if (!this.isAlreadyIndexed()) {
			this.createIndex();
			this.setAlreadyIndexed(true);
		}
		
		String[] decomposedQuery = query.split(" WITH ");
		ResultSet sqlResult = this.executeSQLRequest(decomposedQuery[0]).getResultsSet();
		
		this.executeTextualRequest(decomposedQuery[1]);
		
		
	}*/

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
}
