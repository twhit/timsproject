package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("image")
public class ImageServices {
	@GET
	@Path("download/{filename}")
	@Produces(MediaType.WILDCARD)
	public Response getFile(@PathParam("filename") String filename) {
		try {
			GetFileFromPOSTGRESCommand getFile = new GetFileFromPOSTGRESCommand();
			InputStream is = getFile.execute(filename);
			ResponseBuilder response = Response.ok((Object) is);
			response.header("Content-Disposition", "attachment; filename=\""
					+ filename + "\"");
			return response.build();
		} catch (Exception e) {
			return Response.status(404).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("inline/{filename}")
	@Produces({"image/png", "image/bmp"})
	public Response renderFile(@PathParam("filename") String filename) {
		try {
			GetFileFromPOSTGRESCommand getFile = new GetFileFromPOSTGRESCommand();
			InputStream is = getFile.execute(filename);
			ResponseBuilder response = Response.ok((Object) is);
			response.header("Content-Disposition", "inline; filename=\""
					+ filename + "\"");
			return response.build();
		} catch (Exception e) {
			return Response.status(404).entity(e.getMessage()).build();
		}
	}

	
	@POST
	@Path("upload/db")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadDBFile(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
		SaveFileToDBCommand cmd = new SaveFileToDBCommand();
	//	cmd.execute(fileDetail.getFileName(), uploadedInputStream, fileDetail.getSize());
		return Response.status(200).build();
 
	}
	
	@POST
	@Path("upload/face")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadToFaceFinder(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
		SaveFileToDBCommand cmd = new SaveFileToDBCommand();
	//	cmd.execute(fileDetail.getFileName(), uploadedInputStream, fileDetail.getSize());
		return Response.status(200).build();
 
	}
	@Path("/file")
	public class UploadFileService {
	 
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
 
		String uploadedFileLocation = "/images/" + fileDetail.getFileName();
 
		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);
 
		String output = "File uploaded to : " + uploadedFileLocation;
 
		return Response.status(200).entity(output).build();
 
	}

	// save uploaded file to new location
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
}
