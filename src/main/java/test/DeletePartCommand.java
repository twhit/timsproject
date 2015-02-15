package test;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import test.ConnectionProvider;

public class DeletePartCommand {

	public String execute(String modelNum) {

		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM PARTS WHERE modelNum = ?");
			stmt.setString(1, modelNum);
			ResultSet rs = stmt.executeQuery();
			return "Part " + modelNum + " deleted.";

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "-1";
	}

}
