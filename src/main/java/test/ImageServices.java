package test;

import java.io.InputStream;

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
	@Produces("image/*")
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
		cmd.execute(fileDetail.getFileName(), uploadedInputStream, fileDetail.getSize());
		return Response.status(200).build();
 
	}
}
