import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SqlServerDatabase {
	public SqlServerDatabase() {}
	public Connection connection;
    public void dbConnect(String db_connect_string, String db_userid, String db_password){
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(
    db_connect_string, db_userid, db_password);
            System.out.println("connected");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public ResultSet executeWithResult(String query){
    	Statement stmt = null;
    	try {
    		stmt = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public void execute(String query){
    	Statement stmt = null;
    	try {
    		stmt = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
