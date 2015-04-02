package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import test.Part;
import test.ConnectionProvider;

public class CreatePartCommand {

	public String execute(Part p, InputStream is) throws IOException {

		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			IOUtils.copy(is, output);
			byte[] filecontent = output.toByteArray();
			System.out.println(filecontent.length);
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO PARTS(name, type, modelnum, filename) VALUES(?, ?, ?, ?);"
									+ "INSERT INTO IMAGES(filename, file) VALUES (?, ?)");
			stmt.setString(1, p.getName());
			stmt.setString(2, p.getType());
			stmt.setString(3, p.getModelNum());
			stmt.setString(4, p.getFileName());
			stmt.setString(5, p.getFileName());
			stmt.setBytes(6, filecontent);
			stmt.executeQuery();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "-1";
	}

}
