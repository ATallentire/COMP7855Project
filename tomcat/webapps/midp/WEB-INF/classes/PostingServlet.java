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

public class PostingServlet extends HttpServlet {
String id;
  
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
	  // Set response content type
	  id = (request.getParameter("id"));
      response.setContentType("text/html");
		
	  // Send html code to show posting upload page
	  // On submit data is sent to upload servlet in post request
      PrintWriter out = response.getWriter();
      out.println("<html>\n" +
                "<body bgcolor=\"#d9d9d9\">\n" +
				"<div align=\"center\" >\n" +
				"<h1> " + "Enter Posting Information" + " </h1>\n" + 
				"User ID: " + id + " \n" +
				"<br />\n" +
				"<br />\n" +
                "<form action=\"/midp/uploads\" method=\"POST\" enctype=\"multipart/form-data\" >\n" +
				"Select Photo to Upload: <input type=\"file\" name=\"fileName\" />\n" +
				"<br />\n" +
				"<br />\n" +
				"Posting Title: <input type=\"text\" name=\"title\" />\n"   +
                "<br />\n" +
				"Posting Description (Max 100 characters): <input type=\"text\" name=\"description\" />\n"   +
                "<br />\n" +
			    "Asking Price: $<input type=\"text\" name=\"price\" />\n"   +
                "<br />\n" +
				"Minimum Price: $<input type=\"text\" name=\"minPrice\" />\n"   +
				"<br />\n" +
				"Keywords: <input type=\"text\" name=\"kw1\" placeholder=\"First Keyword\" /> <input type=\"text\" name=\"kw2\" placeholder=\"Second Keyword\" />\n"   +
				"<br />\n" +
				"<br />\n" +
                "<input type=\"submit\" value=\"Continue\" />\n" +
				"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
				"<br />\n" +
				"<br />\n" +
				"<input type=\"hidden\" name=\"id\" value="+id+" />\n" +
                "</form>\n</body>\n</html\n");

  }
// Method to handle POST method request.
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
	
    PrintWriter out = response.getWriter();
    response.setContentType("text");

	
  }
}