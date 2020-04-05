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
		System.out.println("Search got ID: " + id);
		String title = "Search for Products";
	
	// Set response content type
      response.setContentType("text/html");

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
			"Maximum Price $: <input type=\"text\" name=\"maxPrice\" value=\"10000\" /> \n" +
			"<br />\n" +
			"<br />\n" +				
			
			"<input type=\"submit\" value=\"Search Now\" />\n" +
			"	<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
			
//			"<input type=\"button\" value=\"Upload from desktop\" onclick=\"location.href='http://localhost:8081/midp/select.html';\" />\n" +
			"</div>\n</form>\n" +
			"</form>\n</body>\n</html>");

  }
  
  
// Method to handle POST method request.
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
	
	String action = request.getParameter("action");
	
	if (action == null) {
		
		String titleSearch = request.getParameter("titleSearch");
		String kw1 = request.getParameter("kw1");
		String kw2 = request.getParameter("kw2");
		String minPriceString = request.getParameter("minPrice");
		String maxPriceString = request.getParameter("maxPrice");
		
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
			maxPriceString = "10000";

	
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
		
		ArrayList<String[]> itemDetails = DB.ReadItemsDB("", false);
		itemGallery = SU.searchFunc(titleSearch, keywords, searchPrice, itemDetails);
		itemCount = 0;
	}
	
/*
	int Count;
	File file = new File("C:/COMP7855Project/tomcat/webapps/midp/Images");
	String[] imageList = file.list();
*/		

	
	PrintWriter out = response.getWriter();
	response.setContentType("text/html");

	if ("Left".equals(action) && itemCount > 0) {
		itemCount = itemCount - 1;
	} else if ("Right".equals(action) && itemCount < (itemGallery.size() - 1)) {
		itemCount = itemCount + 1;
	}

	if(itemGallery.size() > 0){
		String[] itemData = itemGallery.get(itemCount);
		String title = itemData[3];
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
				"<input type=\"button\" value=\"Search Again\" onclick=\"location.href='http://localhost:8081/midp/search';\" />\n" +
				"<input type=\"submit\" name=\"action\" value=\"Right\" />\n" +   //UpCount
				"<br />\n" +
				"<br />\n" +
				"<img id=\"myImg\" src=\"Images/" + itemData[0] + "\"" + "width=\"640\" height=\"480\">\n\n" + //photoData[0]
				"<b> " +  " </b>\n" + 
				"<br />\n" +		
				"<b> Item Description: </b>" + itemData[4] + "<br />\n" +
				"<b>Asking Price: </b>" + itemData[7] + "<br />\n" +
				"</div>\n</form>\n" +
				"</form>\n</body>\n</html>");		

		}
		catch(Exception e){}
	}
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
				
				"</div>\n</form>\n" +
				"</form>\n</body>\n</html>");
	}
	}
}