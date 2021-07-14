package services;

import java.sql.*;

public final class JDBCService {

  private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/HEALTHRECORDS";
  private static final String DATABASE_USERNAME = "aditya";
  private static final String DATABASE_PASSWORD = "avk123";
  private Connection con;
  private final static JDBCService INSTANCE = new JDBCService();

  private JDBCService() {
    try {
      this.con = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    } catch (Exception e) {
      System.out.println("There was an error while connecting to the database");
      System.out.println(e.toString());
    }
  }

  public static JDBCService getInstance() {
    return INSTANCE;
  }

  public Connection getConnection() {
    return this.con;
  }

  public Statement getStatement() throws SQLException {
    return this.con.createStatement();
  }

  public void endConnection() {
    try {
      this.con.close();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }
}