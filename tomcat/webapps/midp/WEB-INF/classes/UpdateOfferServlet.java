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

public class UpdateOfferServlet extends HttpServlet {
  String id;
  String itemID;
  String buyer;
  String price;
  String status;
  DataTransfer DB = new DataTransfer();
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    // Set response content type
	  id = (request.getParameter("id"));
	  itemID = request.getParameter("itemID");
	  buyer = request.getParameter("buyerID");
	  price = request.getParameter("price");
	  status = request.getParameter("status");
      response.setContentType("text/html");
	
      PrintWriter out = response.getWriter();
	  
	  // If offer accepted - show confirmation
	  if(status.equals("Accepted")){
		  DB.WriteOfferDB(itemID, buyer, price,"0", "Accepted", true);
		  out.println("<html>\n" +
					"<body bgcolor=\"#d9d9d9\">\n" +
					"<div align=\"center\" >\n" +
					"<h1> " + "Offer from UserID "+buyer+" accepted.</h1>\n" + 
					"<br />\n" +
					"<br />\n" +
					"<br />\n" +
					"<br />\n" +
					"<input type=\"button\" value=\"Back to Postings\" onclick=\"location.href='http://localhost:8081/midp/viewpostings?id="+id+"&action=view';\" />\n" +
					"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
					"</form>\n</body>\n</html\n");
	  }
	  // If offer declined - show confirmation
	  else if(status.equals("Declined")){
		  DB.WriteOfferDB(itemID, buyer, price, "0", "Declined", true);
		  out.println("<html>\n" +
					"<body bgcolor=\"#d9d9d9\">\n" +
					"<div align=\"center\" >\n" +
					"<h1> " + "Offer from UserID "+buyer+" declined.</h1>\n" + 
					"<br />\n" +
					"<br />\n" +
					"<br />\n" +
					"<br />\n" +
					"<input type=\"button\" value=\"Back to Postings\" onclick=\"location.href='http://localhost:8081/midp/viewpostings?id="+id+"&action=view';\" />\n" +
					"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
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
	ArrayList<String[]> item = DB.ReadItemsDB(itemID, false, true);
	String[] itemData = item.get(0);
	String minPrice = itemData[8];
	
  }	
}
