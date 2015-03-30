package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import test.ConnectionProvider;

public class SaveFileToDBCommand {
	
	public void execute(String fileName, InputStream fis, String partName, String partType, String partModel) {

		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			IOUtils.copy(fis, output);
			byte[] filecontent = output.toByteArray();
			System.out.println(filecontent.length);
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO Parts (filename, file, filesize, name, type, model) VALUES (?, ?, ?, ?, ?, ?)");
			stmt.setString(1, fileName);
			stmt.setBytes(2, filecontent);
			stmt.setLong(3, filecontent.length);
			stmt.setString(4, partName);
			stmt.setString(5, partType);
			stmt.setString(6, partModel);
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
} 


