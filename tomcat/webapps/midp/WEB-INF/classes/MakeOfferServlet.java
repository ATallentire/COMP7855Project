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

public class MakeOfferServlet extends HttpServlet {
  String id;
  String itemID;
  String poster;
  String title;
  String price;
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    // Set response content type
	  id = (request.getParameter("id"));
	  itemID = request.getParameter("itemID");
	  poster = request.getParameter("poster");
	  title = request.getParameter("title");
	  price = request.getParameter("price");
	  
      response.setContentType("text/html");
	
      PrintWriter out = response.getWriter();
	  
	  if(id.equals(poster)){
		  out.println("<html>\n" +
					"<body>\n" + 
					"<div align=\"center\" >\n" +
					"<h1> " + "You can't make an offer on your own item!" + "</h1>\n" + 
					"<br />\n" +
					"<br />\n" +
					"<br />\n" +
					"<br />\n" +
					"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
					"<input type=\"button\" value=\"Back to Search\" onclick=\"location.href='http://localhost:8081/midp/search?id="+id+"';\" />\n" +
					"</form>\n</body>\n</html\n");
	  }
	  else{
		  out.println("<html>\n" +
					"<body>\n" + 
					"<div align=\"center\" >\n" +
					"<h1> " + "Make an offer on " + title + " </h1>\n" + 
					"<br />\n" +
					"<br />\n" +
					"<b> Asking Price: </b>$" + price + " \n" +
					"<br />\n" +
					"<b> Posted by User ID: </b>" + poster + " \n" +
					"<br />\n" +
					"<b> Item Number: </b>" + itemID + " \n" +
					"<br />\n" +
					"<br />\n" +
					"<b> Your Offer: </b>$ <input type=\"text\" name=\"offer\" placeholder=\"Enter Amount\">\n"   +
					"<form action=\"/midp/makeoffer\" method=\"POST\" >\n" +
					"<br />\n" +
					"<input type=\"submit\" value=\"Continue\" />\n" +
					"<br />\n" +
					"<br />\n" +
					"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
					"<input type=\"button\" value=\"Back to Search\" onclick=\"location.href='http://localhost:8081/midp/search?id="+id+"';\" />\n" +
					"<br />\n" +
					"<br />\n" +
					"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
					"</form>\n</body>\n</html\n");
	  }

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