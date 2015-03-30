package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.FileUtils;
import javax.servlet.annotation.WebServlet;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class UploadFile
 */
@WebServlet("/UploadTestPhoto")
public class UploadTestPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// Check that we have a file upload request
		
		// Parse the request
		
		InputStream stream = null;
		String fileName = "";
		try {	
			List<FileItem> formItems = upload.parseRequest(request);
			for (FileItem item : formItems) {
			        fileName = FilenameUtils.getName(item.getName());
			        stream = item.getInputStream();
			}
			
			
			String msg = PartFinder.findPart(fileName, stream);
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            getServletContext().getRequestDispatcher("/index.jsp").forward(
                    request, response);

    }
}
		