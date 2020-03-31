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
  
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    // Set response content type
	  id = (request.getParameter("id"));
      response.setContentType("text/html");

      PrintWriter out = response.getWriter();
      out.println("<html>\n" +
                "<body>\n" + 
				"<div align=\"center\" >\n" +
				"<h1> " + "Current Offers" + " </h1>\n" + 
				"User ID: " + id + " \n" +
				"<br />\n" +
				"<br />\n" +
                "<form action=\"/midp/home\" method=\"GET\" >\n" +
				"<br />\n" +
				"<br />\n" +
                "<input type=\"submit\" value=\"Back to Home Page\" />\n" +
				"<br />\n" +
				"<br />\n" +
				"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
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