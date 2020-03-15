import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class SearchServlet extends HttpServlet {
  
	int imageCount = 0;
	public static ArrayList<String> photoGallery = null;
  
// Method to handle initial GET method request.
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
		imageCount = 0;
    
		String title = "Photo Gallery";
	
	// Set response content type
      response.setContentType("text/html");

      PrintWriter out = response.getWriter();
      out.println(
			"<html>\n" +			
			"<body bgcolor=\"#d9d9d9\">\n" +
			"<ul>\n" +
			"<div align=\"center\" >\n" +
			"<form action=\"/midp/hits\" method=\"POST\">\n" +
			"<h1> " + title + " </h1>\n" + 
			"<p> Enter nothing into search criteria you dont want to use </p>" +
			"<br />\n" +
			"<br />\n" +	
			
			"<b> Caption </b>" +
			"<br />\n" +
			"<input type=\"text\" name=\"caption\" placeholder=\"Caption\">\n"   +
			"<br />\n" +
			"<br />\n" +

			"<b> Time </b>" +
			"<br />\n" +
			"<input type=\"text\" name=\"start_time\" placeholder=\"Start Time\" /> <input type=\"text\" name=\"end_time\" placeholder=\"End Time\" />\n"   +
			"<br />\n" +
			"Format of time, yyyyMMddHHmmss" + 
			"<br />\n" +
			"<br />\n" +

			"<b> Location </b>" +
			"<br />\n" +
			"<input type=\"text\" name=\"center_lat\" placeholder=\"Center Latitude\" /> \n"  +
			"<br />\n" +
			"<input type=\"text\" name=\"center_lon\" placeholder=\"Center Longitude\" /> \n" +
			"<br />\n" +
			"<input type=\"text\" name=\"Radius\" placeholder=\"Radius\" />\n" +
			"<br />\n" +
			"<br />\n" +				
			
			"<input type=\"submit\" value=\"Search Now\" />\n" +
			
			"<br />\n" +
			"<br />\n" +	
			"<input type=\"button\" value=\"Upload A Photo\" onclick=\"location.href='http://localhost:8081/midp/uploads';\" />\n" +
			"</div>\n</form>\n" +
			"</form>\n</body>\n</html>");



  }
  
  
// Method to handle POST method request.
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {

	/*	
	request.getParameter("caption")
	request.getParameter("start_time")
	request.getParameter("end_time")
	request.getParameter("center_lat")
	request.getParameter("center_lon")
	request.getParameter("Radius")
	*/

	int Count;
	File file = new File("C:/COMP7855Project/tomcat/webapps/midp/Images");
	String[] imageList = file.list();
		
			
	photoGallery = new ArrayList<String>();
	imageCount = 0;
	//photoGallery.add(imageList[0]);
	
	PrintWriter out = response.getWriter();
	response.setContentType("text/html");
	String title = "Searched Photo Gallery";

	try{

			String docType =
			"<!doctype html public \"-//w3c//dtd html 4.0 " +
			"transitional//en\">\n";
			out.println(
				"<html>\n" +			
				"<body bgcolor=\"#d9d9d9\">\n" +
				"<ul>\n" +
				"<div align=\"center\" >\n" +
				"<form action=\"/midp/hits\" method=\"POST\">\n" +
				"<h1> " + title + " </h1>\n" + 
				"<h2 align=\"center\">" + (imageCount + 1)  + "/" + "1" + " Photos" + "</h2>\n" + //(photoGallery.size())
				"<br />\n" +		
				"<br />\n" +
				"<input type=\"submit\" name=\"action\" value=\"Left\" />\n" +   //DownCount
				"<input type=\"button\" value=\"Search Again\" onclick=\"location.href='http://localhost:8081/midp/hits';\" />\n" +
				"<input type=\"submit\" name=\"action\" value=\"Right\" />\n" +   //UpCount
				"<br />\n" +
				"<br />\n" +
				"<img id=\"myImg\" src=\"Images/mypicture.jpeg\" width=\"640\" height=\"480\">\n\n" + //photoGallery.get(imageCount)
				"<br />\n" +
				"<b> " +  " </b>\n" + //photoGallery.get(imageCount)
				"</div>\n</form>\n" +
				"</form>\n</body>\n</html>");		

		}
		catch(Exception e){
			response.sendRedirect("http://localhost:8081/midp/hits");
		}
	}
}