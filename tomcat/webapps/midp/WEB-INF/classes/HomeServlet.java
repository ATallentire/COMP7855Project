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
	  id = (request.getParameter("id"));
	  System.out.println("Home got ID: " + id);
      response.setContentType("text/html");

      PrintWriter out = response.getWriter();
      out.println("<html>\n" +
                "<body>\n" + 
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
				"<br />\n" +
				"<br />\n" +
                "Sell " + " \n"   +
                "<br />\n" +
                "<input type=\"button\" value=\"Create Posting\" onclick=\"location.href='http://localhost:8081/midp/posting?id="+id+"';\" />\n" +
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