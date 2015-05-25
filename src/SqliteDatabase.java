import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SqliteDatabase {
	public SqliteDatabase() {}
	public Connection connection;
    public void dbConnect(){
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:testdb.db");
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
