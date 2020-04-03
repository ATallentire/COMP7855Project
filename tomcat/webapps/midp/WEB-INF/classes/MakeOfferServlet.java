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
					"<body bgcolor=\"#d9d9d9\">\n" +
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
					"<body bgcolor=\"#d9d9d9\">\n" +
					"<div align=\"center\" >\n" +
					"<form action=\"/midp/makeoffer\" method=\"POST\" >\n" +
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
					"<br />\n" +
					"<input type=\"submit\" value=\"Continue\" />\n" +
					"<br />\n" +
					"<br />\n" +
					"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
					"<input type=\"button\" value=\"Back to Search\" onclick=\"location.href='http://localhost:8081/midp/search?id="+id+"';\" />\n" +
					"<br />\n" +
					"<br />\n" +
					"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
					"<input type=\"hidden\" name=\"itemID\" value="+itemID+" />\n" +
					"</form>\n</body>\n</html\n");
	  }

  }
// Method to handle POST method request.
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
	
	id = request.getParameter("id");
	itemID = request.getParameter("itemID");
	price = request.getParameter("offer");
	DataTransfer DB = new DataTransfer();
	ArrayList<String[]> item = DB.ReadItemsDB(itemID, false, true);
	String[] itemData = item.get(0);
	String minPrice = itemData[8];
	
	if(Integer.parseInt(price) > Integer.parseInt(minPrice)){
		DB.WriteOfferDB(itemID, id, price, "0", "Pending", false);
	}
	else{
		DB.WriteOfferDB(itemID, id, price, "0", "Declined", false);
	}
	
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");
	 out.println("<html>\n" +
					"<body bgcolor=\"#d9d9d9\">\n" +
					"<div align=\"center\" >\n" +
					"<h1> " + "Offer sumbitted on " + title + " </h1>\n" + 
					"<br />\n" +
					"<br />\n" +
					"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
					"<input type=\"button\" value=\"Back to Search\" onclick=\"location.href='http://localhost:8081/midp/search?id="+id+"';\" />\n" +
					"</form>\n</body>\n</html\n");

	
  }
}