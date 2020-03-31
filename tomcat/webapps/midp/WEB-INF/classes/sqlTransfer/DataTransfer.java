package sqlTransfer;
import java.sql.*;
import java.io.*;
import java.util.*;

public class DataTransfer {
	
	// SET ORACLE DB PASSWORD HERE
	String password = "jonah1995";
	//String password = "Austin";

	//Check if tables exist on startup
	public DataTransfer() {
		try {
					System.out.println("DataTransfer Constructor");
                    Class.forName("oracle.jdbc.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", password);
					DatabaseMetaData meta = con.getMetaData();
					ResultSet sellTable = meta.getTables(null, null, "PRODUCTS", null);

					Statement stmt = con.createStatement();
					if (!sellTable.next()) {
						stmt.executeUpdate("CREATE TABLE PRODUCTS (buyerID char(5), itemID char(5), title char(100), imageName char(30), description char(100), keyword1 char(30), keyword2 char(30), askPrice char(10), minPrice char(10))");
						System.out.println("No Product Table, One has been created");
                    }
					else {
						System.out.println("Product Table exists");
					}
					while(sellTable.next()){}
					
					stmt.close();
					
					DatabaseMetaData soMeta = con.getMetaData();
					ResultSet tables = soMeta.getTables(null, null, "OFFERS", null);
					Statement stmt2 = con.createStatement();
					if (!tables.next()) {
						stmt2.executeUpdate("CREATE TABLE OFFERS (itemID char(5), buyerID char(5), offerPrice char(10), counterPrice char(10))");
						System.out.println("No Offers Table, One has been created");
                    }
					else {
						System.out.println("Offers Table exists");
					}
					stmt2.close();
					con.close();
		}
        catch(Exception ex) { }
				  
	}
	
	//Return the number of items currently in the Product table
	public int NumOfItems(){
		Connection con = null;
		int itemNum = 0;
		
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", password);
			con.setAutoCommit(true);
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT count(*) FROM PRODUCTS");
			rs.next();
			itemNum = rs.getInt(1);
			
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
	
		return itemNum;
	}
	
	//A new product is being added, store it in the database
	public void WriteItemsDB(String buyerID, String itemID, String title, String imageName, String description, String kw1, String kw2, String askPrice, String minPrice) {
		Connection con = null;
		try {
		 con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", password);
		con.setAutoCommit(false);
		
		//using Transactions
        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO PRODUCTS (buyerID, itemID, title, imageName, description, keyword1, keyword2, askPrice, minPrice) VALUES (?,?,?,?,?,?,?,?,?)");

        preparedStatement.setString(1, buyerID);
        preparedStatement.setString(2, itemID);
		preparedStatement.setString(3, title);
		preparedStatement.setString(4, imageName);
		preparedStatement.setString(5, description);
		preparedStatement.setString(6, kw1);
		preparedStatement.setString(7, kw2);
		preparedStatement.setString(8, askPrice);
		preparedStatement.setString(9, minPrice);
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
	
	//An offer has been made on a posted item, add that to the table
	public void WriteOfferDB(String itemID, String buyerID, String offerPrice, String counterPrice, boolean reWrite) {
		Connection con = null;
		try {
		 con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", password);
		 
		 if(!reWrite){
			con.setAutoCommit(false);
			
			//using Transactions
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO OFFERS (itemID, buyerID, offerPrice, counterPrice) VALUES (?,?,?,?)");

			preparedStatement.setString(1, itemID);
			preparedStatement.setString(2, buyerID);
			preparedStatement.setString(3, offerPrice);
			preparedStatement.setString(4, counterPrice);
			int row = preparedStatement.executeUpdate();
			
			con.commit();
		 }
		 else{
			 con.setAutoCommit(true);
			 Statement stmt = con.createStatement();
			 String update = "UPDATE OFFERS set counterPrice=" + counterPrice + " where (itemID=" + itemID + ", buyerID=" + buyerID + ")";
			 stmt.executeUpdate(update);
			 stmt.close();
		 }
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
	
	//Retrieve all data from the Product postings
	public ArrayList<String[]> ReadItemsDB(String buyerID, boolean buyList) {
		ArrayList<String[]> allData = new ArrayList<String[]>();

		Connection con = null;
		
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", password);
			con.setAutoCommit(true);
			Statement stmt = con.createStatement();
			
			//Return everything, passes to Search for buyers
			if(!buyList) {
				ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTS");
						
						
						//INSERT INTO PRODUCTS (buyerID, itemID, imageName, description, keyword1, keyword2, askPrice, minPrice)
				while (rs.next()) {
					String[] data = new String[8];
					
					data[0] = rs.getString("buyerID");

					data[1] = rs.getString("itemID");

					data[2] = rs.getString("imageName");

					data[3] = rs.getString("description");

					data[4] = rs.getString("keyword1");

					data[5] = rs.getString("keyword2");

					data[6] = rs.getString("askPrice");

					data[7] = rs.getString("minPrice");

					allData.add(data);
					}
			}
			//Return only buyer items, for buyer product page
			else {
				String update = "SELECT * FROM PRODUCTS where BUYERID=" + buyerID;
				 ResultSet rs = stmt.executeQuery(update);
			
				while (rs.next()) {
					String[] data = new String[8];
					
					data[0] = rs.getString("buyerID");

					data[1] = rs.getString("itemID");

					data[2] = rs.getString("imageName");

					data[3] = rs.getString("description");

					data[4] = rs.getString("keyword1");

					data[5] = rs.getString("keyword2");

					data[6] = rs.getString("askPrice");

					data[7] = rs.getString("minPrice");

					allData.add(data);
					}
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
	
	//Retrieve all the offers made, or offers from a particular buyer, or offers on a particular item
	public ArrayList<String[]> ReadOfferDB(String id, boolean buyer, boolean seller) {
		ArrayList<String[]> allData = new ArrayList<String[]>();

		Connection con = null;
		
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", password);
			con.setAutoCommit(true);
			Statement stmt = con.createStatement();
			String request = "";
			
			//Return all items a buyer has made offers on
			if(buyer) {
				request = "SELECT * FROM OFFERS where BUYERID=" + id;
			}
			//return all offers for a given seller's products
			else if(seller) {
				request = "SELECT * FROM OFFERS where ITEMID=" + id;
			}
			else {
				request = "SELECT * FROM OFFERS";
			}	
			
			
			ResultSet rs = stmt.executeQuery(request);
					//INSERT INTO OFFERS (itemID, buyerID, offerPrice, counterPrice)
			while (rs.next()) {
				String[] data = new String[4];
				
				data[0] = rs.getString("itemID"); //Universal item ID

				data[1] = rs.getString("buyerID"); //This is who is making an offer

				data[2] = rs.getString("offerPrice");

				data[3] = rs.getString("counterPrice");

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
