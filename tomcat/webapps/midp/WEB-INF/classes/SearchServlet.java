import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import sqlTransfer.*;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;


public class SearchServlet extends HttpServlet {
  
	int itemCount = 0;
	public static ArrayList<String[]> itemGallery = new ArrayList<String[]>();
	DataTransfer DB = new DataTransfer();
	SearchUtility SU = new SearchUtility();
	String id = null;
  // Method to handle initial GET method request.
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
		itemCount = 0;
		
		id = request.getParameter("id");
		String title = "Search for Products";
	
	// Set response content type
      response.setContentType("text/html");
	// Send HTML code. This page allows user to enter search info.
	// Searching will execute post request.
      PrintWriter out = response.getWriter();
      out.println(
			"<html>\n" +			
			"<body bgcolor=\"#d9d9d9\">\n" +
			"<ul>\n" +
			"<div align=\"center\" >\n" +
			"<form action=\"/midp/search\" method=\"POST\">\n" +
			"<h1> " + title + " </h1>\n" + 
			"<br />\n" +
			"<br />\n" +	
			
			"<b> Posting Title </b>" +
			"<br />\n" +
			"<input type=\"text\" name=\"titleSearch\" placeholder=\"Title\">\n"   +
			"<br />\n" +
			"<br />\n" +

			"<b> Keywords </b>" +
			"<br />\n" +
			"<input type=\"text\" name=\"kw1\" placeholder=\"First Keyword\" /> <input type=\"text\" name=\"kw2\" placeholder=\"Second Keyword\" />\n"   +
			"<br />\n" +
			"You do not need to fill both keywords" + 
			"<br />\n" +
			"<br />\n" +

			"<b> Price Range </b>" +
			"<br />\n" +
			"Minimum Price $: <input type=\"text\" name=\"minPrice\" value=\"1\" /> \n"  +
			"<br />\n" +
			"Maximum Price $: <input type=\"text\" name=\"maxPrice\" value=\"100000\" /> \n" +
			"<br />\n" +
			"<br />\n" +				
			
			"<input type=\"submit\" value=\"Search Now\" />\n" +
			"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
			"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
//			"<input type=\"button\" value=\"Upload from desktop\" onclick=\"location.href='http://localhost:8081/midp/select.html';\" />\n" +
			"</div>\n</form>\n" +
			"</form>\n</body>\n</html>");

  }
  
  
// Method to handle POST method request from search button.
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
	
	String action = request.getParameter("action");
	
	if (action == null) {
		
		// Get search parameters
		String titleSearch = request.getParameter("titleSearch");
		String kw1 = request.getParameter("kw1");
		String kw2 = request.getParameter("kw2");
		String minPriceString = request.getParameter("minPrice");
		String maxPriceString = request.getParameter("maxPrice");
		String id = request.getParameter("id");
		
		double[] searchPrice = new double[2];
		double minPrice = 1;
		double maxPrice = 10000;
		String[] keywords = new String[2];
	
		//Checking for any empty search fields that will ruin the search
		if(titleSearch.equals(""))
			titleSearch = null;

		if(kw1.equals(""))
			kw1 = null;
		
		if(kw2.equals(""))
			kw2 = null;

		if(minPriceString.equals(""))
			minPriceString = "1";
		
		if(maxPriceString.equals(""))
			maxPriceString = "100000";
	
		// Parse strings and convert to numeric data
		try{
			if(minPriceString != null){
				minPrice = Double.parseDouble(minPriceString);
			}

			if(maxPriceString != null){
				maxPrice = Double.parseDouble(maxPriceString);
			}
		}
		catch (Exception e) {}
		
		searchPrice[0] = minPrice;
		searchPrice[1] = maxPrice;
		keywords[0] = kw1;
		keywords[1] = kw2;
		
		// Read all items from database
		ArrayList<String[]> itemDetails = DB.ReadItemsDB("", false, false);
		// Search all items with search parameters
		itemGallery = SU.searchFunc(titleSearch, keywords, searchPrice, itemDetails);
		itemCount = 0;
	}
	
	
	PrintWriter out = response.getWriter();
	response.setContentType("text/html");
	
	// Select action based on button pressed
	if ("Left".equals(action) && itemCount > 0) {
		itemCount = itemCount - 1;
	} else if ("Right".equals(action) && itemCount < (itemGallery.size() - 1)) {
		itemCount = itemCount + 1;
	}

	// Get current item
	if(itemGallery.size() > 0){
		String[] itemData = itemGallery.get(itemCount);
		
		String title = itemData[2];
		
		// Read offers on current item and determine status
		ArrayList<String[]> offers = DB.ReadOfferDB(itemData[1], false, true);
		String[] offerDetails;
		boolean hasOffers = true;
		boolean sold = false;
		
		if (offers.size() == 0){
			hasOffers = false;
		}
		else{
			for (int i=0;i<offers.size();i++){
				offerDetails = offers.get(i);
				if(offerDetails[4].equals("Accepted"))
					sold = true;
			}
		}
	// Send html code to show item information
	try{		
			String docType =
			"<!doctype html public \"-//w3c//dtd html 4.0 " +
			"transitional//en\">\n";
			out.println(
				"<html>\n" +			
				"<body bgcolor=\"#d9d9d9\">\n" +
				"<ul>\n" +
				"<div align=\"center\" >\n" +
				"<form action=\"/midp/search\" method=\"POST\">\n" +
				"<h1> " + title + " </h1>\n" + 
				"<h2 align=\"center\">" + (itemCount + 1)  + "/" + itemGallery.size() + " Items" + "</h2>\n" + 
				"<br />\n" +		
				"<br />\n" +
				"<input type=\"submit\" name=\"action\" value=\"Left\" />\n" +   //DownCount
				"<input type=\"button\" value=\"Search Again\" onclick=\"location.href='http://localhost:8081/midp/search?id="+id+"';\" />\n" +
				"<input type=\"submit\" name=\"action\" value=\"Right\" />\n" +   //UpCount
				"<br />\n" +
				"<br />\n" +
				"<img id=\"myImg\" src=\"Images/" + itemData[3] + "\"" + "height=\"480\">\n\n" + //photoData[0]
				"<b> " +  " </b>\n" + 
				"<br />\n" +		
				"<b> Item Description: </b>" + itemData[4] + "<br />\n" +
				"<b>Asking Price: </b>$" + itemData[7] + "<br />\n" +
				"<b> Keywords: </b>" + itemData[5] + "&nbsp;&nbsp;" + itemData[6] + "\n"+
				"<br />\n" +
				"<br />\n");
				
				// Set status section based on offer state
				if(!sold && !hasOffers){
					out.println(
					"<b>Status: </b> Available - No offers yet \n" +
					"<br />\n" +
					"<input type=\"button\" value=\"Make an Offer\" onclick=\"location.href='http://localhost:8081/midp/makeoffer?id="+id+"&itemID="+itemData[1]+"&poster="+itemData[0]+"&title="+title+"&price="+itemData[7]+"';\" />\n");
				}
				else if(!sold){
					out.println(
					"<b>Status: </b> Available - Offers on item \n" +
					"<br />\n" +
					"<input type=\"button\" value=\"Make an Offer\" onclick=\"location.href='http://localhost:8081/midp/makeoffer?id="+id+"&itemID="+itemData[1]+"&poster="+itemData[0]+"&title="+title+"&price="+itemData[7]+"';\" />\n");
				}
				else{
					out.println(
					"<b>Status: </b> Sold! \n" +
					"<br />\n");
				}
				out.println("<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
				"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
				"</div>\n</form>\n" +
				"</form>\n</body>\n</html>");		

		}
		catch(Exception e){}
	}
	// Show no items found page
	else{
		String docType =
			"<!doctype html public \"-//w3c//dtd html 4.0 " +
			"transitional//en\">\n";
			out.println(
				"<html>\n" +			
				"<body bgcolor=\"#d9d9d9\">\n" +
				"<ul>\n" +
				"<div align=\"center\" >\n" +
				"<form action=\"/midp/hits\" method=\"POST\">\n" +
				"<h1> " + "No Items, Try Expanding Search" + " </h1>\n" + 
				"<h2 align=\"center\">" + "0"  + "/" + "0" + " Items" + "</h2>\n" +
				"<br />\n" +		
				"<br />\n" +
				"<input type=\"button\" value=\"Search Again\" onclick=\"location.href='http://localhost:8081/midp/search?id="+id+"';\" />\n" +
				"<br />\n" +
				"<br />\n" +
				"<b> " +  " </b>\n" +
				"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
				"</div>\n</form>\n" +
				"</form>\n</body>\n</html>");
	}
	}
}