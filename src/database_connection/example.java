package database_connection;

import java.sql.*;
import java.util.ArrayList;

public class example {

	public static void main(String[] args) {
		String dbUrl = "";
		String user = "";
		String password = "";
		
		try {
		
		DatabaseConnection db = new DatabaseConnection(dbUrl, user, password);
		
		ArrayList<Object> params = new ArrayList<Object>();
		
		// Execute procedure
		params.add(777); params.add("email@domain.com"); params.add(0);
		db.executeProcedure("testExecuteProcedure", params);

		ResultSet rs;
		
		// Get result set
		rs = db.getResultSet("testResultSet", null);
		while(rs.next())
			System.out.println(rs.getString("email"));
		
		params = new ArrayList<Object>();
		Object[] values;
		
		// Get values from back end
		params.add(9.7); params.add("INPUT"); params.add(7);
		values = db.getObject("testGetValues", params, 3);
		for(Object v : values)
			System.out.println(v);
		
		} catch (SQLException e) {
			new DatabaseError(e);
		}
	}

}
