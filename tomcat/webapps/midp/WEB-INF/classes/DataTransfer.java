import java.sql.*;
import java.io.*;

public static class DataTransfer {
	Connection con = null;
	
	public void TableCheck() {
		try {
                    Class.forName("oracle.jdbc.OracleDriver");
					con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Austin");
					DatabaseMetaData meta = con.getMetaData();
					ResultSet tables = meta.getTables(null, null, "photos", null);
					
					if (!tables.next()) {
						con.executeUpdate("CREATE TABLE photos (name string(10), caption string(30), date string(10), latitude string(10), longitude string(10));");
						System.out.println("No Table, One has been created");
                    }
					else {
						System.out.println("Table exists");
					}
					con.close();
		}
        catch(SQLException ex) { }
				  
	}
	
	public void WriteDB(String imageName, String caption, String date, String lat, String lon) {
	
		try {
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Austin");
		con.setAutoCommit(false);
		
		//using Transactions
        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO photos (name, caption, date, latitude, longitude) VALUES (?,?,?,?,?)");

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
	
	public ArrayList<String[]> ReadDB() {
		ArrayList<String[]> allData = new ArrayList<String[]>;
		
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Austin");
			
			Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM photos");
			
			while (rs.next()) {
				String[] data = new String[];
				
				data[0] = rs.getString("name");
				data[1] = rs.getString("caption");
				data[2] = rs.getString("date");
				data[3] = rs.getString("latitude");
				data[4] = rs.getString("longitude");
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
