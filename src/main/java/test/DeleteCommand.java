package test;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import test.ConnectionProvider;

public class DeleteCommand {

	public String execute(String modelNum) {

		try {
			Connection connection = ConnectionProvider.getConnection();
			String filename = "";
			PreparedStatement stmt = connection
					.prepareStatement("SELECT filename FROM Parts WHERE modelNum = ?");
			stmt.setString(1, modelNum);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				filename = rs.getString("filename");
			}
			System.out.println(filename);
		    stmt = connection
					.prepareStatement("DELETE FROM PARTS WHERE modelNum = ?;DELETE FROM IMAGES WHERE filename = ?");
			stmt.setString(1, modelNum);
			stmt.setString(2, filename);
			stmt.executeQuery();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "-1";
	}
	
	public String executeImage(String filename) {

		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM IMAGES WHERE filename = ?");
			stmt.setString(1, filename);
			ResultSet rs = stmt.executeQuery();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "-1";
	}

}
