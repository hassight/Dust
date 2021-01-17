package persistence.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {

	private PreparedStatement preparedStatement = null;
	private ResultSet resultsSet;

	
	/**
	 * Close the preparedStatement
	 */
	private void closePreparedStatement() {
		try {
			this.getPreparedStatement().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the iterator to the first element
	 * 
	 * @param results
	 */
	public void initIterator(ResultSet results){
		this.setResultsSet(results);
	}
	
	/**
	 * Get the next element of the iterator
	 */
	public boolean nextIterator(){
		boolean currentElement = false;
		try {
			currentElement = this.getResultsSet().next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(currentElement == false){
			return false;
		}
		return true;
	}
	
	
	public void executeQuery(String query) {
		try {
			Statement statement = (Statement) JdbcConnection.getConnection().createStatement();
			
			ResultSet result = statement.executeQuery(query);
			this.setResultsSet(result);
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
	}
	
	/**
	 * Insert lines in database to add a site
	 * 
	 * @param name
	 * @param type
	 * @param price
	 * @param latitude
	 * @param longitude
	 * 
	 * @return idSite
	 */
	public int addSite(String name, String type, int price,boolean bus_disponibility,boolean boat_disponibility ,double latitude, double longitude) {
		int idSite = 0;
		try {
			String addCoordinates = "INSERT INTO coordinates (latitude, longitude) VALUES (?, ?)";
	
			this.preparedStatement = JdbcConnection.getConnection().prepareStatement(addCoordinates);
			
			this.preparedStatement.setDouble(1, latitude);
			this.preparedStatement.setDouble(2, longitude);
	
			int result = this.preparedStatement.executeUpdate();
			
			ResultSet keys = this.preparedStatement.getGeneratedKeys();
			keys.next();
			int idCoordinates = keys.getInt(1);
			this.closePreparedStatement();
			
			if(result != 0) {
				String addSite = "INSERT INTO site (name, type, price, id_coordinates) VALUES (?, ?, ?, ?)";
			
				this.preparedStatement = JdbcConnection.getConnection().prepareStatement(addSite);
				
				this.preparedStatement.setString(1, name);
				this.preparedStatement.setString(2, type);
				this.preparedStatement.setInt(3, price);
				this.preparedStatement.setInt(4, idCoordinates);
		
				result = this.preparedStatement.executeUpdate();
				
				keys = this.preparedStatement.getGeneratedKeys();
				keys.next();
				idSite = keys.getInt(1);
				this.closePreparedStatement();
			}
			else {
				return 0;
			}
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
		
		return idSite;
	}

	/**
	 * @return the preparedStatement
	 */
	private PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	/**
	 * @return the resultsSet
	 */
	public ResultSet getResultsSet() {
		return resultsSet;
	}


	/**
	 * @param resultsSet the resultsSet to set
	 */
	private void setResultsSet(ResultSet resultsSet) {
		this.resultsSet = resultsSet;
	}
	
	
	public void fillRides() {

        PreparedStatement insertStatement, countStatement, fetchStatement;


        try {
            String addRideQuery = "INSERT INTO ride (departure_site, arrival_site, id_transport) VALUES (?,?,?)";
            String getSitesQuery = "SELECT * FROM site";
            String getSizeSite = "SELECT COUNT(*) FROM site" ;

            countStatement = JdbcConnection.getConnection().prepareStatement(getSizeSite);
            ResultSet taille = countStatement.executeQuery();
            taille.next();
            int tailleSite = taille.getInt(1);
            countStatement.close();

            fetchStatement = JdbcConnection.getConnection().prepareStatement(getSitesQuery);

            ResultSet result = fetchStatement.executeQuery();

            insertStatement = JdbcConnection.getConnection().prepareStatement(addRideQuery);

            result.next();
            
            for (int k = 2; k<= tailleSite; k++) {
                insertStatement.setInt(1,1);
                insertStatement.setInt(2,k);
                if(result.getInt(1) == 19 || k == 19) {
                    insertStatement.setInt(3,2);
                }else {
                    insertStatement.setInt(3,1);
                }
                insertStatement.executeUpdate();
            }
            
            while(result.next()) {

                for (int i = 1; i <= tailleSite; i++) {
                    if(result.getInt(1)!=i) {
                        insertStatement.setInt(1,result.getInt(1));
                        insertStatement.setInt(2,i);
                        if(result.getInt(1) == 19 || i == 19) {
                            insertStatement.setInt(3,2);
                        }else {
                            insertStatement.setInt(3,1);
                        }
                        insertStatement.executeUpdate();
                    }
                    
                }


            }

        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

    }
}
