import java.sql.*;
import java.io.*;
import java.util.*;
import sqlTransfer.*;

public class testJDBC {
	
	
  public static void main(String args[]) {
	//DataTransfer DB = new DataTransfer();
	SearchUtility SU = new SearchUtility();
	
	ArrayList<String[]> itemDetails = new ArrayList<String[]>();
	String[] data = new String[8];
	
	//-------------------------------
	data[0] = "0010";

	data[1] = "1234";

	data[2] = "Nice Image";

	data[3] = "who knows"; //desc

	data[4] = null; //kw1

	data[5] = null; //kw2

	data[6] = "150"; //askPrice

	data[7] = "120"; //minPrice
	itemDetails.add(data);
	
	//--------------------------------
	String[] data1 = new String[8];
	data1[0] = "1000";

	data1[1] = "4321";

	data1[2] = "Bad Image";

	data1[3] = "just cardboard"; //desc

	data1[4] = "cardboard"; //kw1

	data1[5] = null; //kw2

	data1[6] = "275"; //askPrice

	data1[7] = "200"; //minPrice
	itemDetails.add(data1);
	
	//------------------------------
	String[] data2 = new String[8];
	data2[0] = "1111";

	data2[1] = "4102";

	data2[2] = "Weird Image";

	data2[3] = "just a box"; //desc

	data2[4] = null; //kw1

	data2[5] = "box"; //kw2

	data2[6] = "300"; //askPrice

	data2[7] = "250"; //minPrice
	itemDetails.add(data2);
	
	//-----------------------------
	String[] data3 = new String[8];
	data3[0] = "9999";

	data3[1] = "4102";

	data3[2] = "Just an Image";

	data3[3] = "a cardboard box?"; //desc

	data3[4] = "cardboard"; //kw1

	data3[5] = "box"; //kw2

	data3[6] = "350"; //askPrice

	data3[7] = "250"; //minPrice
	itemDetails.add(data3);
	
	//----------------------------
	String[] data4 = new String[8];
	data4[0] = "0000";

	data4[1] = "9999";

	data4[2] = "Blah Image";

	data4[3] = "cardboard box"; //desc

	data4[4] = null; //kw1

	data4[5] = null; //kw2

	data4[6] = "700"; //askPrice

	data4[7] = "500"; //minPrice
	itemDetails.add(data4);
	
	
	String titleSearch = "box";
	String[] keywords = {null,null};
	double[] searchPrice = {1, 500};

	System.out.println("Itemgallery Size = " + itemDetails.size());

try {
	//ArrayList<String[]> photoDetails = DB.ReadDB();

	ArrayList<String[]> itemGallery = SU.searchFunc(titleSearch, keywords, searchPrice, itemDetails);
	
	System.out.println("Itemgallery Size = " + itemGallery.size());
	
	for (int i = 0; i < itemGallery.size(); i++) {
		System.out.println("-------------Next Item-----------");
		String[] item = itemGallery.get(i);
		
		for (int j = 0; j < 8; j++) {
			System.out.println("Picture Results:" + item[j]);
		}
	}
	}
catch(Exception ex) {
System.out.println("Failed: " + ex);}

/*	  Connection con = null;
  try {
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Austin");
		con.setAutoCommit(false);
		
		//using Transactions
        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO photos (name, caption, imDate, latitude, longitude) VALUES (?,?,?,?,?)");

		String imageName = "WIN_20190124_11_24_07_Pro.jpg";
		String caption = "Caption2";
		String date = "20000101";
		String lat = "49";
		String lon = "120";
		

        preparedStatement.setString(1, imageName);
        preparedStatement.setString(2, caption);
		preparedStatement.setString(3, date);
		preparedStatement.setString(4, lat);
		preparedStatement.setString(5, lon);
        int row = preparedStatement.executeUpdate();
		
		con.commit();
		con.close();
		System.out.println("Run cleanly");
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
*/	}
}