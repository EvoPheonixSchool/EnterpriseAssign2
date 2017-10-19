/* File: DataSource.java
 * Author: Stanley Pieda
 * Date: Sept, 2017
 * Description: Sample solution to Assignment 2
 * References:
 * Ram N. (2013). Data Access Object Design Pattern or DAO Pattern [blog] Retrieved from
 * http://ramj2ee.blogspot.in/2013/08/data-access-object-design-pattern-or.html
 */ 
package dataaccesslayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**creates the connection to the database for the server
 * Class provided by Stan Pieda
 */
public class DataSource {
	private Connection con = null;
	private final String connectionString = "jdbc:mysql://localhost/assignment2";
	private final String username = "assignment2";
	private final String password = "password";

	/**
	 * main logic, creating the connection to the database for the server
	 * @return
	 */
	public Connection getConnection(){
		try {
		if(con != null){
			System.out.println("Cannot create new connection, one exists already");
		}
		else{
			con = DriverManager.getConnection(connectionString, username, password);
		}
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return con;
	}
}
