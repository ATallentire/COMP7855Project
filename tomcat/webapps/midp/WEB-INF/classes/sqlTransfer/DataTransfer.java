package sqlTransfer;
import java.sql.*;
import java.io.*;
import java.util.*;

public class DataTransfer {
	
	// SET ORACLE DB PASSWORD HERE
	String password = "jonah1995";
//	Connection con = null;
//	Statement stmt = null;
	public DataTransfer() {
		try {
                    Class.forName("oracle.jdbc.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", password);
					DatabaseMetaData meta = con.getMetaData();
					ResultSet sellTable = meta.getTables(null, null, "SELLERS", null);

					Statement stmt = con.createStatement();
					if (!sellTable.next()) {
						stmt.executeUpdate("CREATE TABLE SELLERS (buyerID char(5), itemID char(30), imageName char(20), description char(100), filters char(30)), askPrice char(10), minPrice char(10)");
						System.out.println("No Sell Table, One has been created");
                    }
					else {
						System.out.println("Sell Table exists");
					}
					
					ResultSet tables = meta.getTables(null, null, "OFFERS", null);
					if (!sellTable.next()) {
						stmt.executeUpdate("CREATE TABLE OFFERS (itemID char(30), buyerID char(5), offerPrice char(10), counterPrice char(10))");
						System.out.println("No Offers Table, One has been created");
                    }
					else {
						System.out.println("Offers Table exists");
					}
					con.close();
		}
        catch(Exception ex) { }
				  
	}
	
	public void WriteDB(String imageName, String caption, String date, String lat, String lon) {
		Connection con = null;
		try {
		 con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", password);
		con.setAutoCommit(false);
		
		//using Transactions
        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO photos (name, caption, imDate, latitude, longitude) VALUES (?,?,?,?,?)");

        preparedStatement.setString(1, imageName);
        preparedStatement.setString(2, caption);
		preparedStatement.setString(3, date);
		preparedStatement.setString(4, lat);
		preparedStatement.setString(5, lon);
        int row = preparedStatement.executeUpdate();
		
		con.commit();
		con.close();
		}
		
		catch(SQLException ex) {
			try {
				con.rollback();
                                con.close();
			} catch (SQLException e) {
				System.out.println("\nError Rolling back\n");	
			} 
			System.out.println("\n--- SQLException caught ---\n"); 
			while (ex != null) { 
				System.out.println("Message: " + ex.getMessage ()); 
				System.out.println("SQLState: " + ex.getSQLState ()); 
				System.out.println("ErrorCode: " + ex.getErrorCode ()); 
				ex = ex.getNextException(); 
				System.out.println("");
			} 
		}
	}
	
	public ArrayList<String[]> ReadItemsDB() {
		ArrayList<String[]> allData = new ArrayList<String[]>();

		Connection con = null;
		
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", password);
			con.setAutoCommit(true);
			Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM photos");
					
			while (rs.next()) {
				String[] data = new String[5];
				
				data[0] = rs.getString("name");
				System.out.println(data[0]);
				data[1] = rs.getString("CAPTION");
				System.out.println(data[1]);
				data[2] = rs.getString("IMDATE");
				System.out.println(data[2]);
				data[3] = rs.getString("LATITUDE");
				System.out.println(data[3]);
				data[4] = rs.getString("LONGITUDE");
				System.out.println(data[4]);
				allData.add(data);
				}
					
			stmt.close();
			con.close();
			}
			catch(SQLException ex) {
				try {
					con.rollback();
									con.close();
				} catch (SQLException e) {
					System.out.println("\nError Rolling back\n");	
				} 
				System.out.println("\n--- SQLException caught ---\n"); 
				while (ex != null) { 
					System.out.println("Message: " + ex.getMessage ()); 
					System.out.println("SQLState: " + ex.getSQLState ()); 
					System.out.println("ErrorCode: " + ex.getErrorCode ()); 
					ex = ex.getNextException(); 
					System.out.println("");
				} 
			}
	
		return allData;
	}
}
