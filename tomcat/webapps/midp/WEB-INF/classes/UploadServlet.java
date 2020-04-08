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

import sqlTransfer.DataTransfer;

//@WebServlet("/uploads")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServletFileUpload uploader = null;
	private String imDir = "C:/COMP7855Project/tomcat/webapps/midp/Images";
	String kw1 = "";
	String kw2 = "";
	String price = "";
	String minPrice = "";
	String fileName = "";
	String id ="";
	String description = "";
	String title ="Post Title";
	DataTransfer DB = new DataTransfer();
	
	@Override
	public void init() throws ServletException{
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		File filesDir = new File(imDir);
		fileFactory.setRepository(filesDir);
		this.uploader = new ServletFileUpload(fileFactory);
	}
	// Get method allows download from server
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		if(fileName == null || fileName.equals("")){
			throw new ServletException("File Name can't be null or empty");
		}
		File file = new File(request.getServletContext().getAttribute("FILES_DIR")+File.separator+fileName);
		if(!file.exists()){
			throw new ServletException("File doesn't exists on server.");
		}
		System.out.println("File location on server::"+file.getAbsolutePath());
		ServletContext ctx = getServletContext();
		InputStream fis = new FileInputStream(file);
		String mimeType = ctx.getMimeType(file.getAbsolutePath());
		response.setContentType(mimeType != null? mimeType:"application/octet-stream");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		ServletOutputStream os = response.getOutputStream();
		byte[] bufferData = new byte[1024];
		int read=0;
		while((read = fis.read(bufferData))!= -1){
			os.write(bufferData, 0, read);
		}
		os.flush();
		os.close();
		fis.close();
		System.out.println("File downloaded at client successfully");
	}

	// Post method allows upload to server from desktop
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*if(!ServletFileUpload.isMultipartContent(request)){
			throw new ServletException("Content type is not multipart/form-data");
		}*/
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<html><head></head><body>");
		
		try {
			List<FileItem> fileItemsList = uploader.parseRequest(request);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			System.out.println(request.getParameter("source"));

			while(fileItemsIterator.hasNext()){
				FileItem fileItem = fileItemsIterator.next();
				
				if( fileItem.isFormField()){
					String fieldname = fileItem.getFieldName();
					String fieldvalue = fileItem.getString();
					if(fieldname.equals("kw1") && fieldvalue != null){
						kw1 = fieldvalue;
					}
					else if(fieldname.equals("kw2") && fieldvalue != null){
						kw2 = fieldvalue;
					}
					else if(fieldname.equals("price")){
						if (fieldvalue == null || fieldvalue == "")
							price = "0";
						else
							price = fieldvalue;
					}
					else if(fieldname.equals("minPrice")){
						if (fieldvalue == null || fieldvalue == "")
							minPrice = "0";
						else
							minPrice = fieldvalue;
					}
					else if(fieldname.equals("id")){
						id = fieldvalue;
					}
					else if(fieldname.equals("description")){
						description = fieldvalue;
					}
					else if(fieldname.equals("title") && fieldvalue != null && fieldvalue != ""){
						title = fieldvalue;
					}
				}

				else{
				File file = new File(imDir+File.separator+fileItem.getName());
				System.out.println("Absolute Path at server="+file.getAbsolutePath());
				fileItem.write(file);
				fileName = fileItem.getName();
				
				}
			}
			// Output confirmation page
			String itemID = Integer.toString(DB.NumOfItems() + 1);
			out.write(
			"<html>\n" +
			"<body bgcolor=\"#d9d9d9\">\n" +
			"<h1> " + "Posting for user "+id+" created." + " </h1>\n" +
			"<br />\n" +
			"<br />\n" +
			"File "+fileName+ " uploaded successfully." +
			"<br />\n" +
			"<br />\n" +
			"  <li><b>Posting Title</b>: "
			+ title + "\n" +
			"  <li><b>Posting Description</b>: "
			+ description + "\n" +
			"  <li><b>Asking Price</b>: $"
			+ price + "\n" +
			"  <li><b>Minimum Price</b>: $"
			+ minPrice + "\n" +	
			"  <li><b>First Keyword</b>: "
			+ kw1 + "\n" +
			"  <li><b>Second Keyword</b>: "
			+ kw2 + "\n" +
			"<br />\n" +
			"<br />\n" +
			"  <li><b>Item ID</b>: "
			+ itemID + "\n" +
			"<br />\n" +
			"<br />\n" +
			"<input type=\"button\" value=\"Back to Home Page\" onclick=\"location.href='http://localhost:8081/midp/home?id="+id+"';\" />\n" +
			"</div>\n</form>\n" +
			"</form>\n</body>\n</html>");
	
		// Truncate description to 100 chars
		// Truncate keywords to 30 characters
		title = truncate(title, 100);
		description = truncate(description, 100);
		kw1 = truncate(kw1, 30);
		kw2 = truncate(kw2, 30);
		
		//Upload new item to database
		DB.WriteItemsDB(id, itemID, title, fileName, description, kw1, kw2, price, minPrice);

		} catch (FileUploadException e) {
			out.write("Exception in uploading file." + e);
		} catch (Exception e) {
			out.write("Exception in uploading file." + e);
		}
		out.write("</body></html>");
	}
	
	public static String truncate(String value, int length) {
        // Ensure String length is longer than requested size.
        if (value.length() > length) {
            return value.substring(0, length);
        } else {
            return value;
        }
    }

}