package test;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import test.Part;
import test.ConnectionProvider;

public class CreatePartCommand {

	public String execute(Part p) {

		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO PARTS(name, type, modelNum, filename) VALUES(?, ?, ?, ?)");
			stmt.setString(1, p.getName());
			stmt.setString(2, p.getType());
			stmt.setString(3, p.getModelNum());
			stmt.setString(4, p.getFileName());
			stmt.executeQuery();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "-1";
	}

}
