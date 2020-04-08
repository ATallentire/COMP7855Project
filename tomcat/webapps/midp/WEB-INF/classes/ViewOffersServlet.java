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

public class ViewOffersServlet extends HttpServlet {
	String id;
	String action;
	String source;
	int itemNum = 1;
	DataTransfer DB = new DataTransfer();
	
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
      // Set response content type
	  id = (request.getParameter("id"));
	  action = request.getParameter("action");
	  source = request.getParameter("source");
	  
	  // Determine source of get request (buyer or seller)
	  if (source.equals("buy")){
		  // Read all offers for userID
		  ArrayList<String[]> offers = DB.ReadOfferDB(id, true, false);
		  
		  // Determine action
		  if (action.equals("view")){
			  itemNum = 1;
		  }
		  else if (action.equals("Left") && itemNum > 1){
			  itemNum -= 1;
		  }
		  else if (action.equals("Right") && itemNum < offers.size()){
			  itemNum += 1;
		  }
		  
		  response.setContentType("text/html");
		  PrintWriter out = response.getWriter();
		  
		  // Check offers not empty
		  if (offers.size() != 0){
		  String[] offerDetails = offers.get(itemNum - 1);
		  
		  String itemID = offerDetails[0];
		  // Read item data and store in string array
		  ArrayList<String[]> posts = DB.ReadItemsDB(itemID, false, true);
		  String[] postDetails = posts.get(0);
		  // Send html code to display current offer webpage
		  out.println("<html>\n" +
					"<body bgcolor=\"#d9d9d9\">\n" +
					"<div align=\"center\" >\n" +
					"<form action=\"/midp/viewoffers\" method=\"GET\" >\n" +
					"<h1> " + "Current Offers" + " </h1>\n" + 
					"For User ID: " + id + " \n" +
					"<br />\n" +
					"<br />\n" +
					"<input type=\"submit\" name=\"action\" value=\"Left\" />\n" +   //DownCount
					"<b> " + "Offer " + itemNum + "/" + offers.size() + " </b>\n" + 
					"<input type=\"submit\" name=\"action\" value=\"Right\" />\n" +   //UpCount
					"<br />\n" + "<br />\n" +
					"<b> " + postDetails[2] + " </b>\n" + 
					"<br />\n" + "<br />\n" +
					"<img id=\"myImg\" src=\"Images/" + postDetails[3] + "\" + height=\"480\">\n\n" +
					"<br />\n" +
					"<br />\n" +
					"<b> Description: </b>" + postDetails[4] + "\n" +
					"<br />\n" +
					"<br />\n" +
					"<b> Asking Price: </b>$" + postDetails[7] + "&nbsp;&nbsp; \n" +
					"<br />\n" +
					"<br />\n" +
					"<b> Your Offer Amount: </b>$" + offerDetails[2] + "\n" +
					"<br />\n" +
					"<br />\n" +
					"<b> Status of Offer: </b>" + offerDetails[4] + "\n" +
					"<br />\n" +
					"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
					"<br />\n" +
					"<br />\n" +
					"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
					"<input type=\"hidden\" name=\"source\" value=buy />\n" +
					"</form>\n</body>\n</html\n");
		  }
		  // Else show no offers found page
		  else{
			  out.println("<html>\n" +
					"<body>\n" + 
					"<body bgcolor=\"#d9d9d9\">\n" +
					"<div align=\"center\" >\n" +
					"<form action=\"/midp/viewpostings\" method=\"GET\" >\n" +
					"<h1> " + "Current Offers" + " </h1>\n" + 
					"<br />\n" +
					"<br />\n" +
					"<h1> " + "No active offers found for User ID " + id + " \n" +  " </h1>\n" +
					"<br />\n" +
					"<br />\n" +
					"<br />\n" +
					"<br />\n" +
					"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
					"<br />\n" +
					"<br />\n" +
					"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
					"<input type=\"hidden\" name=\"source\" value=buy />\n" +
					"</form>\n</body>\n</html\n");
		  }
	  }
	  // If source is sell
	  else if (source.equals("sell")){
		  String itemID = request.getParameter("itemID");
		  ArrayList<String[]> allOffers = DB.ReadOfferDB(itemID, false, true);
		  ArrayList<String[]> offers = new ArrayList<String[]>();
		  String[] tempOffer;
		  
		  // Filter offers that are below minimum
		  for (int i = 0; i< allOffers.size(); i++){
			  tempOffer = allOffers.get(i);
			  if(!(tempOffer[4].equals("Declined - Too Low"))){
				  offers.add(tempOffer);
			  }
		  }
		  // Determine action
		  if (action.equals("view")){
			  itemNum = 1;
		  }
		  else if (action.equals("Left") && itemNum > 1){
			  itemNum -= 1;
		  }
		  else if (action.equals("Right") && itemNum < offers.size()){
			  itemNum += 1;
		  }
		  
		  response.setContentType("text/html");
		  PrintWriter out = response.getWriter();
		  // Get details of current offer
		  String[] offerDetails = offers.get(itemNum - 1);
		  // If offer is pending
		  if (offerDetails[4].equals("Pending")){
			  // Read details for current item
			  ArrayList<String[]> posts = DB.ReadItemsDB(itemID, false, true);
			  String[] postDetails = posts.get(0);
			  // Send html code to display current offer and product
			  out.println("<html>\n" +
						"<body bgcolor=\"#d9d9d9\">\n" +
						"<div align=\"center\" >\n" +
						"<form action=\"/midp/viewoffers\" method=\"GET\" >\n" +
						"<h1> " + "Current Offers" + " </h1>\n" + 
						"On items from User ID: " + id + " \n" +
						"<br />\n" +
						"<br />\n" +
						"<input type=\"submit\" name=\"action\" value=\"Left\" />\n" +   //DownCount
						"<b> " + "Offer " + itemNum + "/" + offers.size() + " </b>\n" + 
						"<input type=\"submit\" name=\"action\" value=\"Right\" />\n" +   //UpCount
						"<br />\n" + "<br />\n" +
						"<b> " + postDetails[2] + " </b>\n" +
						"<br />\n" + 
						"<b> Offer from UserID: </b>" + offerDetails[1] + "\n" +						
						"<br />\n" + 
						"<br />\n" +
						"<b> Asking Price: </b>$" + postDetails[7] + "&nbsp;&nbsp;<b> Minimum Price: </b>$" + postDetails[8] + "\n" +
						"<br />\n" +
						"<br />\n" +
						"<b>Offer Amount: </b>$" + offerDetails[2] + "\n" +
						"<br />\n" +
						"<br />\n" +
						"<input type=\"button\" value=\"Accept Offer\" onclick=\"location.href='http://localhost:8081/midp/update?id="+id+"&status=Accepted&itemID="+itemID+"&buyerID="+offerDetails[1]+"&price="+offerDetails[2]+"';\" />\n" +
						"<input type=\"button\" value=\"Decline Offer\" onclick=\"location.href='http://localhost:8081/midp/update?id="+id+"&status=Declined&itemID="+itemID+"&buyerID="+offerDetails[1]+"&price="+offerDetails[2]+"';\"/>\n" +
						"<br />\n" +
						"<input type=\"button\" value=\"Back to Postings\" onclick=\"location.href='http://localhost:8081/midp/viewpostings?id="+id+"&action=view';\" />\n" +
						"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
						"<br />\n" +
						"<br />\n" +
						"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
						"<input type=\"hidden\" name=\"source\" value=sell />\n" +
						"<input type=\"hidden\" name=\"itemID\" value="+itemID+" />\n" +
						"</form>\n</body>\n</html\n");

	  }
	  // If offer was declined
	  else if (offerDetails[4].equals("Declined")){
		  ArrayList<String[]> posts = DB.ReadItemsDB(itemID, false, true);
			  String[] postDetails = posts.get(0);
			  out.println("<html>\n" +
						"<body bgcolor=\"#d9d9d9\">\n" +
						"<div align=\"center\" >\n" +
						"<form action=\"/midp/viewoffers\" method=\"GET\" >\n" +
						"<h1> " + "Current Offers" + " </h1>\n" + 
						"On items from User ID: " + id + " \n" +
						"<br />\n" +
						"<br />\n" +
						"<input type=\"submit\" name=\"action\" value=\"Left\" />\n" +   //DownCount
						"<b> " + "Offer " + itemNum + "/" + offers.size() + " </b>\n" + 
						"<input type=\"submit\" name=\"action\" value=\"Right\" />\n" +   //UpCount
						"<br />\n" + "<br />\n" +
						"<b> " + postDetails[2] + " </b>\n" + 
						"<br />\n" + 
						"<b> Offer from UserID: </b>" + offerDetails[1] + "\n" +
						"<br />\n" + 
						"<br />\n" +
						"<b> Asking Price: </b>$" + postDetails[7] + "&nbsp;&nbsp;<b> Minimum Price: </b>$" + postDetails[8] + "\n" +
						"<br />\n" +
						"<br />\n" +
						"<b>Offer Amount: </b>$" + offerDetails[2] + "\n" +
						"<br />\n" +
						"<br />\n" +
						"<h1> " + "This offer has been declined" + " </h1>\n" +
						"<br />\n" +
						"<input type=\"button\" value=\"Back to Postings\" onclick=\"location.href='http://localhost:8081/midp/viewpostings?id="+id+"&action=view';\" />\n" +
						"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
						"<br />\n" +
						"<br />\n" +
						"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
						"<input type=\"hidden\" name=\"source\" value=sell />\n" +
						"<input type=\"hidden\" name=\"itemID\" value="+itemID+" />\n" +
						"</form>\n</body>\n</html\n");
	  }
		  // If offer was accepted
	  	  else if (offerDetails[4].equals("Accepted")){
		  ArrayList<String[]> posts = DB.ReadItemsDB(itemID, false, true);
			  String[] postDetails = posts.get(0);
			  out.println("<html>\n" +
						"<body bgcolor=\"#d9d9d9\">\n" +
						"<div align=\"center\" >\n" +
						"<form action=\"/midp/viewoffers\" method=\"GET\" >\n" +
						"<h1> " + "Current Offers" + " </h1>\n" + 
						"On items from User ID: " + id + " \n" +
						"<br />\n" +
						"<br />\n" +
						"<input type=\"submit\" name=\"action\" value=\"Left\" />\n" +   //DownCount
						"<b> " + "Offer " + itemNum + "/" + offers.size() + " </b>\n" + 
						"<input type=\"submit\" name=\"action\" value=\"Right\" />\n" +   //UpCount
						"<br />\n" + "<br />\n" +
						"<b> " + postDetails[2] + " </b>\n" + 
						"<br />\n" + 
						"<b> Offer from UserID: </b>" + offerDetails[1] + "\n" +
						"<br />\n" +
						"<br />\n" +
						"<b> Asking Price: </b>$" + postDetails[7] + "&nbsp;&nbsp;<b> Minimum Price: </b>$" + postDetails[8] + "\n" +
						"<br />\n" +
						"<br />\n" +
						"<b>Offer Amount: </b>$" + offerDetails[2] + "\n" +
						"<br />\n" +
						"<br />\n" +
						"<h1> " + "This offer has been accepted. Please contact the buyer." + " </h1>\n" +
						"<br />\n" +
						"<input type=\"button\" value=\"Back to Postings\" onclick=\"location.href='http://localhost:8081/midp/viewpostings?id="+id+"&action=view';\" />\n" +
						"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
						"<br />\n" +
						"<br />\n" +
						"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
						"<input type=\"hidden\" name=\"source\" value=sell />\n" +
						"<input type=\"hidden\" name=\"itemID\" value="+itemID+" />\n" +
						"</form>\n</body>\n</html\n");
			}
		}
	}
// Method to handle POST method request.
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();
    response.setContentType("text");

	
  }
}