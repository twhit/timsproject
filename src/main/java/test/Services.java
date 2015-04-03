package test;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import test.Part;
import test.ListPartsCommand;
import test.UpdatePartCommand;
import test.Constants;
import test.DeleteCommand;

@Path("part")
public class Services {
	ObjectMapper mapper = new ObjectMapper();
	
	// Browse all parts
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response browseParts(@QueryParam("offset") int offset,
			@QueryParam("count") int count) {
		
		ListPartsCommand command = new ListPartsCommand();
		ArrayList<Part> list = command.execute();
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, list);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String partString = null;
		try {
			partString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(partString).build();
	}
	
	// get part by model number
	@GET
	@Path("{modelNum}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPart(@PathParam("modelNum") String modelNum) {
		ListPartsCommand command = new ListPartsCommand();
		String partString = null;
		try {
			partString = mapper.writeValueAsString(command.executeByModel(modelNum));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(partString).build();
	}
	
	// Search parts by name by adding /name/{name} to url
	
	@GET
	@Path("name/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPartsByName(@PathParam("name") String name, @QueryParam("offset") int offset,
			@QueryParam("count") int count) {
		ListPartsCommand command = new ListPartsCommand();
		ArrayList<Part> list = command.executeByName(name);
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, list);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String partString = null;
		try {
			partString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(partString).build();
	}
	
	@GET
	@Path("type/{type}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPartsByType(@PathParam("type") String type, @QueryParam("offset") int offset,
			@QueryParam("count") int count) {
		ListPartsCommand command = new ListPartsCommand();
		ArrayList<Part> list = command.executeByType(type);
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, list);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String partString = null;
		try {
			partString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(partString).build();
	}
	
	@GET
	@Path("filename/{filename}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPartsByFileName(@PathParam("filename") String type, @QueryParam("offset") int offset,
			@QueryParam("count") int count) {
		ListPartsCommand command = new ListPartsCommand();
		ArrayList<Part> list = command.executeByFileName(type);
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put(Constants.Pagination.DATA, list);
		hm.put(Constants.Pagination.OFFSET, offset);
		hm.put(Constants.Pagination.COUNT, count);
		String partString = null;
		try {
			partString = mapper.writeValueAsString(hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(partString).build();
	}

	// Update a part
	@POST
	@Path("{modelNum}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN  })
	public Response updateParts(String payload, @PathParam("modelNum") String modelNum) {
		UpdatePartCommand update = new UpdatePartCommand();
		Part p = null;
		try {
			p = mapper.readValue(payload, Part.class);
			p.setModelNum(modelNum);
		} catch (Exception ex) {
			ex.printStackTrace();
			Response.status(400).entity("could not read string").build();
		}
		try {
			update.execute(p);
		} catch (Exception e) {
			e.printStackTrace();
			Response.status(500).build();
		}
		return Response.status(200).build();
	}
	
	@DELETE
	@Path("{modelNum}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response deletePart(@PathParam("modelNum") String modelNum) {
		DeleteCommand command = new DeleteCommand();
		String s = "";
		try {
			s = command.execute(modelNum);
		} catch (Exception e) {
			e.printStackTrace();
			Response.status(500).build();
		}
		return Response.status(200).entity(s).build();
	}
	
}


