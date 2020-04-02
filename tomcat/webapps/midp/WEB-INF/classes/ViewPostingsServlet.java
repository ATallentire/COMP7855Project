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

public class ViewPostingsServlet extends HttpServlet {
	String id;
	String action;
	int itemNum = 1;
	DataTransfer DB = new DataTransfer();
	
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    // Set response content type
	  id = (request.getParameter("id"));
	  action = request.getParameter("action");
	  ArrayList<String[]> postings = DB.ReadItemsDB(id, true);
		  
	  if (action.equals("view")){
		  itemNum = 1;
	  }
	  else if (action.equals("Left") && itemNum > 1){
		  itemNum -= 1;
	  }
	  else if (action.equals("Right") && itemNum < postings.size()){
		  itemNum += 1;
	  }
	  
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
	  
	  if (postings.size() != 0){
	  String[] postDetails = postings.get(itemNum - 1);
      out.println("<html>\n" +
                "<body>\n" + 
				"<div align=\"center\" >\n" +
				"<form action=\"/midp/viewpostings\" method=\"GET\" >\n" +
				"<h1> " + "Current Postings" + " </h1>\n" + 
				"For User ID: " + id + " \n" +
				"<br />\n" +
				"<br />\n" +
				"<input type=\"submit\" name=\"action\" value=\"Left\" />\n" +   //DownCount
				"<b> " + "Posting " + itemNum + "/" + postings.size() + " </b>\n" + 
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
				"<b> Asking Price: </b>$" + postDetails[7] + "&nbsp;&nbsp;<b> Minimum Price: </b>$" + postDetails[8] + "\n" +
				"<br />\n" +
				"<br />\n" +
				"<b> Keywords: </b>" + postDetails[5] + "&nbsp;&nbsp;" + postDetails[6] + "\n" +
				"<br />\n" +
				"<br />\n" +
				"<br />\n" +
				"<br />\n" +
                "<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
				"<br />\n" +
				"<br />\n" +
				"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
                "</form>\n</body>\n</html\n");
	  }
	  else{
		  out.println("<html>\n" +
                "<body>\n" + 
				"<div align=\"center\" >\n" +
				"<form action=\"/midp/viewpostings\" method=\"GET\" >\n" +
				"<h1> " + "Current Postings" + " </h1>\n" + 
				"<br />\n" +
				"<br />\n" +
				"<h1> " + "No postings found for User ID " + id + " \n" +  " </h1>\n" +
				"<br />\n" +
				"<br />\n" +
				"<br />\n" +
				"<br />\n" +
                "<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
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