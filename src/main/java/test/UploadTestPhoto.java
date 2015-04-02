package test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.annotation.WebServlet;

import test.PartFinder;
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
		
		InputStream stream = null;
		String fileName = "";
		try {	
			List<FileItem> formItems = upload.parseRequest(request);
			for (FileItem item : formItems) {
			        fileName = FilenameUtils.getName(item.getName());
			        stream = item.getInputStream();
			}
			
			
			Part p = PartFinder.findPart(fileName, stream);
			GetFileFromPOSTGRESCommand gf = new GetFileFromPOSTGRESCommand();
			InputStream is = gf.execute(p.getFileName());
			BufferedImage imag2 = ImageIO.read(new ByteArrayInputStream(IOUtils.toByteArray(is)));
			ImageIO.write(imag2, "png", new File("temp2.png"));
			// Heroku won't play nice with jsp's, so we just render the html with the search results here.
			PrintWriter pw = response.getWriter();
			pw.println("<!DOCTYPE html>");
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<title>Part Match</title>");
			pw.println("</head>");
			pw.println("<body>");
			pw.println("<p></p>");
			pw.println("<p></p>");
			pw.println("<br/>Part Name = " + p.getName());
			pw.println("<br/>Part Type = " + p.getType());
			pw.println("<br/>Model Number = " + p.getModelNum());
			pw.println("<br/><form action=\"https://timsproject.herokuapp.com/rest/image/inline/" + p.getFileName() + "\" target=\"_blank\"><input type=\"submit\" value=\"Show Part\"></form>" );
			pw.println("<br/><img src=\"https://timsproject.herokuapp.com/rest/image/inline/" + p.getFileName() + "\">");
			pw.println("</body>");
			pw.println("</html>");
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     //       getServletContext().getRequestDispatcher("/index2.html").forward(
     //               request, response);

    }
}
		