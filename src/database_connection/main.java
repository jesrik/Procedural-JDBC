package database_connection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class main {

	public static void main(String[] args) {
		
		DatabaseConnection db = new DatabaseConnection();
		
//		try {
			ResultSet rs;
			
			ArrayList<Object> params = new ArrayList<Object>();
//			int i = 64; String email = "####"; int lvl = 7;
//			params.add(i); params.add(email); params.add(lvl);
//			System.out.println(db.executeProcedure("test_add", params));
//			
//			rs = db.getResultSet("getPlayers", null);
//			while(rs.next())
//				System.out.printf("ID: %d\n", rs.getInt("id"));
//			rs.close();
			java.util.Date today = Calendar.getInstance().getTime();

		    // (2) create a date "formatter" (the date format we want)
		    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		    params.add(today);
		    db.executeProcedure("toDates", params);
//			Date today = new Date(800000000);
//			Date currentDate = new java.sql.Date(today.getTime());
			System.out.println(formatter.format(today));
			
//			ArrayList<Object> al = new ArrayList<Object>();
//			al.add(7899); al.add("FIXED IT"); al.add(0); al.add(37.9);
//			Object[] o = db.getObject("test_Injection", al, 4);
//			for(Object e : o)
//				System.out.println(e);
//			}
//		
//		} catch (SQLException E) {
//			System.out.println("SQLException: " + E.getMessage());
//			System.out.println("SQLState: " + E.getSQLState());
//			System.out.println("Error code: " + E.getErrorCode());
//		}
	
	}

}
