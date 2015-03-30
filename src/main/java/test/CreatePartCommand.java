package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import test.Part;
import test.ConnectionProvider;

public class CreatePartCommand {

	public String execute(Part p) throws IOException {

		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			IOUtils.copy(p.getFile(), output);
			byte[] filecontent = output.toByteArray();
			System.out.println(filecontent.length);
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO PARTS(name, type, modelNum, file, size, filename) VALUES(?, ?, ?, ?, ?, ?)");
			stmt.setString(1, p.getName());
			stmt.setString(2, p.getType());
			stmt.setString(3, p.getModelNum());
			stmt.setBytes(4, filecontent);
			stmt.setLong(5, filecontent.length);
			stmt.setString(6, p.getFileName());
			stmt.executeQuery();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "-1";
	}

}
