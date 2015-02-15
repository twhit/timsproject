package test;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import test.Part;
import test.ConnectionProvider;

public class UpdatePartCommand {

	public String execute(Part p) {

		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("UPDATE PARTS SET name=?, type=? WHERE modelNum=?");
			stmt.setString(1, p.getName());
			stmt.setString(2, p.getType());
			stmt.setString(3, p.getModelNum());
			stmt.executeUpdate();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "-1";
	}

}
