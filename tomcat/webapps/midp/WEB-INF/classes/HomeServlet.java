import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import sqlTransfer.DataTransfer;

public class HomeServlet extends HttpServlet {
String id;
  
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    // Set response content type
	  PrintWriter out = response.getWriter();
	  id = (request.getParameter("id"));
	  
	  // Redirect to login page if no ID entered
	  if (id.length() == 0){
		out.println("<html>\n" +
		"<meta http-equiv=\"Refresh\" content=\"0; url=http://localhost:8081/midp/login\" />\n" +
		"</html\n");
	  }
	  // Check ID length, add leading 0's if too short
	  else if(id.length() < 5){
		  while(id.length() < 5){
			  id = "0" + id;
		  }
	  }
	  // Else truncate if too long
	  else{
		id = id.substring(0,5);
	  }
      response.setContentType("text/html");

      out.println("<html>\n" +
                "<body bgcolor=\"#d9d9d9\">\n" +
				"<div align=\"center\" >\n" +
                "<form action=\"/midp/search\" method=\"GET\">\n" +
				"<h1> " + "Home Page" + " </h1>\n" +
				"<br />\n" +
				"<br />\n" +
				"User ID: " + id + " \n" +
				"<br />\n" +
				"<br />\n" +
			    "Buy " + " \n"   +
                "<br />\n" +
                "<input type=\"submit\" value=\"Go to Search Page\" />\n" +
				"<input type=\"button\" value=\"View all Offers\" onclick=\"location.href='http://localhost:8081/midp/viewoffers?id="+id+"&action=view&source=buy';\" />\n" +
				"<br />\n" +
				"<br />\n" +
                "Sell " + " \n"   +
                "<br />\n" +
                "<input type=\"button\" value=\"Create Posting\" onclick=\"location.href='http://localhost:8081/midp/posting?id="+id+"';\" />\n" +
				"<input type=\"button\" value=\"View all Postings\" onclick=\"location.href='http://localhost:8081/midp/viewpostings?id="+id+"&action=view';\"/>\n" +
				"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
				"<br />\n" +
				"<br />\n" +
				"<br />\n" +
				"<br />\n" +
				"<input type=\"button\" value=\"Logout\" onclick=\"location.href='http://localhost:8081/midp/login';\" />\n" +
                "</form>\n</body>\n</html\n");

  }
// Method to handle POST method request.
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {

	DataTransfer DB = new DataTransfer();
	
    PrintWriter out = response.getWriter();
	/// HANDLE IMAGE HERE
    response.setContentType("text");

	
  }
}