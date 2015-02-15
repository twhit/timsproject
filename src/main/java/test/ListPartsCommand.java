package test;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import test.Part;
import test.ConnectionProvider;

public class ListPartsCommand {

	public ArrayList<Part> execute() {
		ArrayList<Part> ret = new ArrayList<Part>();
		try {
			Connection connection = ConnectionProvider.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Parts");
			while (rs.next()) {
				Part p = new Part();
				p.setName(rs.getString("name"));
				p.setType(rs.getString("type"));
				p.setModelNum(rs.getString("modelNum"));
				ret.add(p);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	
	public ArrayList<Part> executeByName(String name) {
		ArrayList<Part> ret = new ArrayList<Part>();
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Parts WHERE name = ?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Part p = new Part();
				p.setName(rs.getString("name"));
				p.setType(rs.getString("type"));
				p.setModelNum(rs.getString("modelNum"));
				ret.add(p);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public ArrayList<Part> executeByType(String type) {
		ArrayList<Part> ret = new ArrayList<Part>();
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Parts WHERE type = ?");
			stmt.setString(1, type);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Part p = new Part();
				p.setName(rs.getString("name"));
				p.setType(rs.getString("type"));
				p.setModelNum(rs.getString("modelNum"));
				ret.add(p);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
