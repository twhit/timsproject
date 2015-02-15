package test;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import test.Part;
import test.ConnectionProvider;

public class GetPartCommand {

	public Part execute(String modelNum) {
		Part p = new Part();
		try {
			Connection connection = ConnectionProvider.getConnection();
			// Statement stmt = connection.createStatement();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Parts WHERE modelNum = ?");
			stmt.setString(1, modelNum);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				p.setName(rs.getString("name"));
				p.setType(rs.getString("type"));
				p.setModelNum(rs.getString("modelNum"));
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}		
}
