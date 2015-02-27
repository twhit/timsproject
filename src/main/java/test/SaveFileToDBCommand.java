package test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import test.ConnectionProvider;

public class SaveFileToDBCommand {

	public void execute(String fileName, InputStream fis, long l) {

		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			IOUtils.copy(fis, output);
			byte[] filecontent = output.toByteArray();
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO IMAGES VALUES (?, ?)");
			stmt.setString(1, fileName);
			stmt.setBinaryStream(2, fis, l);
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}