package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.annotation.WebServlet;

import test.PartFinder;
/**
 * Servlet implementation class UploadFile
 */
@WebServlet("/UploadPart")
public class UploadPart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		// Parse the request
		FileItemIterator iter;
		InputStream stream = null;
		String partName = "";
		String partType = "";
		String partModel = "";
		String fileName = "";
		try {	
			List<FileItem> formItems = upload.parseRequest(request);
			for (FileItem item : formItems) {
			    if (item.isFormField()) {
			        // Process regular form field
			        String fieldname = item.getFieldName();
			        if (fieldname.equals("partName")) partName = item.getString();
			        if (fieldname.equals("partType")) partType = item.getString();
			        if (fieldname.equals("partModel")) partModel = item.getString();
			    } else {
			        // Process form file field (input type="file").
			        fileName = FilenameUtils.getName(item.getName());
			        stream = item.getInputStream();
			        
			    }
			}
			//String msg = FaceFinder.findFace(fileName, stream, 1);String uploadedFileLocation = "/images/" + fileDetail.getFileName();
			 
			// save it
		//	String uploadedFileLocation = "images/" + fileName;
			
		//	writeToFile(stream, uploadedFileLocation);

			Part p = new Part();
			p.setFile(stream);
			p.setName(partName);
			p.setType(partType);
			p.setModelNum(partModel);
			p.setFileName(fileName);
			
			CreatePartCommand cmd = new CreatePartCommand();
			cmd.execute(p);
			
			
			//String msg = FaceFinder.findFace(fileName, stream, 1);
			
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
		