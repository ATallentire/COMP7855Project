import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

//@WebServlet("/uploads")
public class ParamServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		String dataString = request.getParameter("date") + "_" + request.getParameter("caption")
		+"_" + request.getParameter("lat") + "_" + request.getParameter("long");
		System.out.println(dataString);
		
		// Write to txtfile
	}
}