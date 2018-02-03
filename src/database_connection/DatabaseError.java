package database_connection;
import java.sql.SQLException;

public class DatabaseError {
	
	// Would much rather use a log service
	public DatabaseError(SQLException e) {
		System.out.println("SQLException: " + e.getMessage());
		System.out.println("SQLState: " + e.getSQLState());
		System.out.println("Error code: " + e.getErrorCode());
	}
	
}
