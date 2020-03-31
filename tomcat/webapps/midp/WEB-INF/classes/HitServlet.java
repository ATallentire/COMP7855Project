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

public class HitServlet extends HttpServlet {
  private int mCount;
  private String imDir = "C:/COMP7855Project/tomcat/webapps/midp/Images";
  
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    // Set response content type


      response.setContentType("text/html");

      PrintWriter out = response.getWriter();
      out.println("<html>\n" +
                "<body>\n" + 
				"<div align=\"center\" >\n" +
                "<form action=\"/midp/home\" method=\"GET\">\n" +
				"<h1> " + "Login with your 5 digit ID" + " </h1>\n" +
				"<br />\n" +
				"<br />\n" +
                "User ID: <input type=\"text\" name=\"id\" placeholder=\"5 Digit ID\" />\n"   +
                "<br />\n" +
				"<br />\n" +
                "<input type=\"submit\" value=\"Login\" />\n"
                + 
                "</form>\n</body>\n</html\n");


  }
// Method to handle POST method request.
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {

	//DataTransfer DB = new DataTransfer();
	
    PrintWriter out = response.getWriter();
	/// HANDLE IMAGE HERE
    response.setContentType("text");


  }
}



