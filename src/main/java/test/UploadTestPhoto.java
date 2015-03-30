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

/**
 * Servlet implementation class UploadFile
 */
@WebServlet("/UploadTestPhoto")
public class UploadTestPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		File tempfolder = new File("temp/");
		File[] tempFiles = tempfolder.listFiles();
		for(File f : tempFiles) f.delete();
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

//save uploaded file to new location
		private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {
	 
			try {
				OutputStream out = new FileOutputStream(new File(
						uploadedFileLocation));
				int read = 0;
				byte[] bytes = new byte[1024];
	 
				out = new FileOutputStream(new File(uploadedFileLocation));
				while ((read = uploadedInputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
			} catch (IOException e) {
	 
				e.printStackTrace();
			}
	 
		}
}
		