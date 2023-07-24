package example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {
    private final String UName = "root";
    private final String Password = "";
    private final String URL = "jdbc:mysql://localhost:3306/myshare";

    private static Db instance;
    private Connection con;

    private Db() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, UName, Password);
            System.out.println("Database Connection Success");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Class Error " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Database Connection Error " + ex.getMessage());
        }
    }

    public static Db getSingleInstance() {
        try {
            if (instance == null) {
                instance = new Db();
            } else if (instance.con.isClosed()) {
                instance = new Db();
            } else {
                return instance;
            }
            return instance;
        } catch (SQLException ex) {
            System.out.println("Database Connection Error " + ex.getMessage());
            return null;
        }
    }

    public boolean ExecuteQuery(String sqlQ) {
        try {
            Statement st = con.createStatement();
            int result = st.executeUpdate(sqlQ);
            return result > 0;
        } catch (SQLException ex) {
            System.out.println("SQL Error " + ex.getMessage());
            return false;
        }
    }

    public Connection getConnection() {
        return con;
    }
}
