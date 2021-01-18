package tests;

import java.io.IOException;

import org.apache.lucene.search.ScoreDoc;

import persistence.lucene.Indexer;
import persistence.lucene.Searcher;

public class TestSearchLucene {

	public static void main(String[] args) {
		Indexer indexer = new Indexer();
		
		try {
			indexer.initIndexer();
			indexer.createIndexFromDirectory();
			indexer.closeIndexer();
			
			Searcher searcher = new Searcher(indexer.getIndexDirectoryPath(), indexer.getSecondFieldName());
	
			try {
				searcher.search(indexer.getMaxSearch(), "botanique harrison");
			} catch (org.apache.lucene.queryparser.classic.ParseException e) {
				e.printStackTrace();
			}
			
			searcher.initIterator();
			ScoreDoc currentInfo;
			
			while((currentInfo = searcher.nextIterator()) != null) {
				int currentId = searcher.getDocumentName(currentInfo, indexer.getFirstFieldName());
				float currentScore = searcher.getDocumentScore(currentInfo);
				System.out.println("currentId="+currentId+" currentScore="+currentScore);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
