package database_connection;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {
	
	// Connection information
	private String dbUrl;
	private String user;
	private String password;
	private Connection connection;
	
	/**
	 * Initialize a new connection to server
	 */
	public DatabaseConnection(String dbUrl, String user, String password) {
		this.dbUrl = dbUrl;
		this.user = user;
		this.password = password;
		connection = connect();
	}
	
	/**
	 * Utility to create a connection between the server and application.
	 * @return Connection 
	 */
	private Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.err.println("Unable to load driver.");
			e.printStackTrace();
		}
	
		try {
			Connection conn = DriverManager.getConnection(dbUrl, user, password);
			return conn;
		} catch (SQLException E) {
			new DatabaseError(E);
			return null;
		}
	}
	
	/**
	 * Utility to execute a procedure on server.
	 * @param procedure Procedure name.
	 * @param params Parameter list for back end procedure.
	 * @return 0 on success, -1 on exception
	 */
	public int executeProcedure(String procedure, ArrayList<Object> params) {
		try {
			CallableStatement cStatement; 
			if(params == null)
				cStatement = connection.prepareCall("{call " + procedure + "()}");
			
			else {
				String numParams = "";
				for(int i=0; i < params.size(); i++) {
					numParams += "?";
					if(i != params.size()-1)
						numParams += ", ";
				}
				
				cStatement = connection.prepareCall("{call " + procedure + "(" + numParams + ")}");
			
				for(int i=0; i < params.size(); i++)
					setInput(cStatement, params.get(i), i);
			}
			
			cStatement.execute();
			return 0;
			
		} catch (SQLException e) {
			new DatabaseError(e);
			return -1;
		}
	}
	
	/**
	 * Utility to return a result set from a stored procedure in back end.
	 * @param procedure Procedure name.
	 * @param params Parameter list for back end procedure.
	 * @return ResultSet containing records
	 */
	public ResultSet getResultSet(String procedure, ArrayList<Object> params) {
		try {
			CallableStatement cStatement;
			
			if(params == null)
				cStatement = connection.prepareCall("{call " + procedure + "()}");
			
			else {
				String numParams = "";
				for(int i=0; i < params.size(); i++) {
					numParams += "?";
					if(i != params.size()-1)
						numParams += ", ";
				}
				
				cStatement = connection.prepareCall("{call " + procedure + "(" + numParams + ")}");
			
				for(int i=0; i < params.size(); i++)
					setInput(cStatement, params.get(i), i);
			}
			
			if(cStatement.execute())
				return cStatement.getResultSet();
			else
				throw new SQLException();
			
		} catch (SQLException e) {
			new DatabaseError(e);
			return null;
		}
	}
	
	/**
	 * Function to return values from back end.
	 * @param procedure Procedure name
	 * @param params Parameter list for back end procedure
	 * @param returnValues Number of INOUT variables
	 * @return Array of any object type containing updated variables from back end
	 */
	public Object[] getObject(String procedure, ArrayList<Object> params, int returnValues) {
		try {
			CallableStatement cStatement;
			ArrayList<Object> types = new ArrayList<Object>();
			
			if(params == null)
				cStatement = connection.prepareCall("{call " + procedure + "()}");
			
			else {
				String numParams = "";
				for(int i=0; i < params.size(); i++) {
					numParams += "?";
					if(i != params.size()-1)
						numParams += ", ";
				}
				
				cStatement = connection.prepareCall("{call " + procedure + "(" + numParams + ")}");
			
				for(int i=0; i < params.size(); i++) {
					setInput(cStatement, params.get(i), i);
					types.add(params.get(i).getClass());
				}
			}
			
			if(cStatement.execute()) {
				Object[] results = new Object[returnValues]; 
				int index = 0;
				
				for(Object o : types) 
					getInput(cStatement, o, index++, results);
				
				return results;
			}
			else
				throw new SQLException();
			
		} catch (SQLException e) {
			new DatabaseError(e);
			return null;
		}
	}
	
	/**
	 * Utility to set parameter for back end procedure.
	 * @param cStatement Back end statement 
	 * @param obj Parameter to add
	 * @param index Parameter number
	 */
	private void setInput(CallableStatement cStatement, Object obj, int index) {
		try {
			
			Class<?> o = obj.getClass();
			
			if(o.equals(Integer.class))
				cStatement.setInt(index+1, (int) obj);
			
			else if(o.equals(String.class))
				cStatement.setString(index+1, (String) obj);
			
			else if(o.equals(float.class))
				cStatement.setFloat(index+1, (float) obj);
			
			else if(o.equals(Double.class))
				cStatement.setDouble(index+1, (Double) obj);
			
			} catch (SQLException e) {
				new DatabaseError(e);
			}
	}
	
	/**
	 * Utility to get parameters back from back end procedure.
	 * @param cStatement Back end statement
	 * @param obj Parameter to receive
	 * @param index Parameter number
	 * @param results Result array
	 */
	private void getInput(CallableStatement cStatement, Object obj, int index, Object[] results) {
		try {
			
			if(obj.equals(Integer.class))
				results[index] = cStatement.getInt(index+1);
			
			else if(obj.equals(String.class))
				results[index] = cStatement.getString(index+1);
			
			else if(obj.equals(float.class))
				results[index] = cStatement.getFloat(index+1);
			
			else if(obj.equals(Double.class))
				results[index] = cStatement.getDouble(index+1);
			
			} catch (SQLException e) {
				new DatabaseError(e);
			}
	}
	
}

