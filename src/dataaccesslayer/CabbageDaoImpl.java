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

public class CabbageDaoImpl implements CabbageDao{
	
    public void insertCabbage(Cabbage cabbage){
    	DataSource source = new DataSource();
		Connection con = source.getConnection();
		PreparedStatement pstmt = null;
		try{
			pstmt = con.prepareStatement(
					"INSERT INTO Cabbages (linenumber, alpha, beta, charlie, delta, uuid) " +
					"VALUES(?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, cabbage.getLineNumber());
			pstmt.setString(2, cabbage.getAlpha());
			pstmt.setString(3, cabbage.getBeta());	
			pstmt.setString(4, cabbage.getCharlie());
			pstmt.setString(5,  cabbage.getDelta());
			pstmt.setString(6,  cabbage.getUUID());
			pstmt.executeUpdate();
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
	}

	@Override
	public Cabbage getCabbageByUUID(String uuid){
		DataSource source = new DataSource();
		Connection con = source.getConnection();
		PreparedStatement pstmt = null;
		Cabbage cabbage = new Cabbage();
		try{
			pstmt = con.prepareStatement(
					"SELECT * FROM Cabbages WHERE uuid = ?");
			pstmt.setString(1, uuid);
			ResultSet rs = pstmt.executeQuery();
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
