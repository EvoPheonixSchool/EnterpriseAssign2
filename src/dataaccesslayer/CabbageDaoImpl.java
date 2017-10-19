/* File: CabbageDaoImpl.java
 * Author: Stanley Pieda
 * Date: Sept, 2017
 * Description: Sample solution to Assignment 2
 * References:
 * Ram N. (2013). Data Access Object Design Pattern or DAO Pattern [blog] Retrieved from
 * http://ramj2ee.blogspot.in/2013/08/data-access-object-design-pattern-or.html
 */ 
package dataaccesslayer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import datatransfer.Cabbage;

/**
 * Class provided by Stan Pieda
 * Modified by Sheldon McGrath
 */
public class CabbageDaoImpl implements CabbageDao{

	/**
	 * Inserts cabbage given into the database
	 * @param cabbage
	 * @return String for success or error
	 */
    public String insertCabbage(Cabbage cabbage){
    	//gets connection to server
    	DataSource source = new DataSource();
		Connection con = source.getConnection();
		PreparedStatement pstmt = null;
		try{
			//creates statement to insert the cabbage
			pstmt = con.prepareStatement(
					"INSERT INTO Cabbages (linenumber, alpha, beta, charlie, delta, uuid) " +
					"VALUES(?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, cabbage.getLineNumber());
			pstmt.setString(2, cabbage.getAlpha());
			pstmt.setString(3, cabbage.getBeta());	
			pstmt.setString(4, cabbage.getCharlie());
			pstmt.setString(5,  cabbage.getDelta());
			pstmt.setString(6,  cabbage.getUUID());
			//attempts statement
			pstmt.executeUpdate();
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}catch(Exception e){
			//check if successful
			return "fail";
		}
		finally{
			try{ if(pstmt != null){ pstmt.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
			try{ if(con != null){ con.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
		}
		return null;
	}

	/**
	 * Gets a cabbage object by searching with a UUID
	 * @param uuid
	 * @return Cabbage object requested
	 */
	@Override
	public Cabbage getCabbageByUUID(String uuid){
		//creates connection
		DataSource source = new DataSource();
		Connection con = source.getConnection();
		PreparedStatement pstmt = null;
		Cabbage cabbage = new Cabbage();
		try{
			//searches for cabbage
			pstmt = con.prepareStatement(
					"SELECT * FROM Cabbages WHERE uuid = ?");
			pstmt.setString(1, uuid);
			ResultSet rs = pstmt.executeQuery();
			//reads data abd creates cabbage
			rs.next();
			cabbage.setId(rs.getInt("id"));
			cabbage.setLineNumber(rs.getInt("linenumber"));
			cabbage.setAlpha(rs.getString("alpha"));
			cabbage.setBeta(rs.getString("beta"));
			cabbage.setCharlie(rs.getString("charlie"));
			cabbage.setDelta(rs.getString("delta"));
			cabbage.setUUID(rs.getString("uuid"));
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		finally{
			try{ if(pstmt != null){ pstmt.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
			try{ if(con != null){ con.close(); }}
			catch(SQLException ex){System.out.println(ex.getMessage());}
		}
		return cabbage;
	}
}
