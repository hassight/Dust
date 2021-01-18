package persistence.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	private String dataDirectoryPath;
	private String indexDirectoryPath;
	private int maxSearch;
	private String firstFieldName;
	private String secondFieldName;
	private String thirdFieldName;
	private IndexWriter writer;
	private TextFileFilter textFileFilter;
	
	
	/**
	 * Initialize indexer
	 * 
	 * @throws IOException
	 */
	public void initIndexer() throws IOException {
		this.readLuceneConfig();
		
		Directory indexDirectory = FSDirectory.open(Paths.get(this.getIndexDirectoryPath()));
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		config.setOpenMode(OpenMode.CREATE);
		writer = new IndexWriter(indexDirectory, config);
		textFileFilter = new TextFileFilter();
	}
	
	
	/**
	 * Close the indexWriter
	 * 
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	public void closeIndexer() throws CorruptIndexException, IOException {
		writer.close();
	}
	
	
	/**
	 * Get the configuration from LuceneConstants class
	 */
	private void readLuceneConfig() {
		this.setDataDirectoryPath(LuceneConstants.DATA_DIRECTORY_FIELD_NAME);
		this.setIndexDirectoryPath(LuceneConstants.INDEX_DIRECTORY_FIELD_NAME);
		this.setMaxSearch(LuceneConstants.MAX_SEARCH_NUMBER_FIELD_NAME);
		this.setFirstFieldName(LuceneConstants.INDEX_FIRST_FIELD_FIELD_NAME);
		this.setSecondFieldName(LuceneConstants.INDEX_SECOND_FIELD_FIELD_NAME);
		this.setThirdFieldName(LuceneConstants.INDEX_THIRD_FIELD_FIELD_NAME);
	}
	
	
	/**
	 * Get document information/content
	 * 
	 * @param file
	 * 
	 * @return document
	 * 
	 * @throws IOException
	 */
	private Document getDocument(File file) throws IOException {
		Document document = new Document();
		
		TextField contentField = new TextField(this.getSecondFieldName(), new FileReader(file));
		TextField fileNameField = new TextField(this.getFirstFieldName(), file.getName(), TextField.Store.YES);
		TextField filePathField = new TextField(this.getThirdFieldName(), file.getCanonicalPath(), TextField.Store.YES);
		
		document.add(contentField);
		document.add(fileNameField);
		document.add(filePathField);
		
		return document;
	}
	
	
	/**
	 * Create index from file
	 * 
	 * @param file
	 * 
	 * @throws IOException
	 */
	public void createIndexFromFile(File file) throws IOException {
		Document document = getDocument(file);
		this.getWriter().addDocument(document);
	}
	
	
	/**
	 * Get the current number of documents buffered in RAM
	 * 
	 * @return numRamDocs
	 * 
	 * @throws IOException
	 */
	public int createIndexFromDirectory() throws IOException {
		File[] files = new File(this.getDataDirectoryPath()).listFiles();
		  
		for(File file : files) {
		     if(!file.isDirectory() && !file.isHidden()  && file.exists() && file.canRead() && this.textFileFilter.accept(file)){
		    	 createIndexFromFile(file);
		     }
		}
		
		return this.getWriter().numRamDocs();
	}

	
	/**
	 * @return the dataDirectoryPath
	 */
	public String getDataDirectoryPath() {
		return dataDirectoryPath;
	}
	
	/**
	 * @param dataDirectoryPath the dataDirectoryPath to set
	 */
	public void setDataDirectoryPath(String dataDirectoryPath) {
		this.dataDirectoryPath = dataDirectoryPath;
	}
	
	/**
	 * @return the indexDirectoryPath
	 */
	public String getIndexDirectoryPath() {
		return indexDirectoryPath;
	}

	/**
	 * @param indexDirectoryPath the indexDirectoryPath to set
	 */
	public void setIndexDirectoryPath(String indexDirectoryPath) {
		this.indexDirectoryPath = indexDirectoryPath;
	}
	
	/**
	 * @return the maxSearch
	 */
	public int getMaxSearch() {
		return maxSearch;
	}
	
	/**
	 * @param maxSearch the maxSearch to set
	 */
	public void setMaxSearch(int maxSearch) {
		this.maxSearch = maxSearch;
	}
	
	/**
	 * @return the firstFieldName
	 */
	public String getFirstFieldName() {
		return firstFieldName;
	}
	
	/**
	 * @param firstFieldName the firstFieldName to set
	 */
	public void setFirstFieldName(String firstFieldName) {
		this.firstFieldName = firstFieldName;
	}
	
	/**
	 * @return the secondFieldName
	 */
	public String getSecondFieldName() {
		return secondFieldName;
	}

	/**
	 * @param secondFieldName the secondFieldName to set
	 */
	public void setSecondFieldName(String secondFieldName) {
		this.secondFieldName = secondFieldName;
	}
	
	/**
	 * @return the thirdFieldName
	 */
	public String getThirdFieldName() {
		return thirdFieldName;
	}

	/**
	 * @param thirdFieldName the thirdFieldName to set
	 */
	public void setThirdFieldName(String thirdFieldName) {
		this.thirdFieldName = thirdFieldName;
	}

	/**
	 * @return the writer
	 */
	public IndexWriter getWriter() {
		return writer;
	}
}
