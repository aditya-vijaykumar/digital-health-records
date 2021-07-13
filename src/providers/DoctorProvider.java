package providers;

import java.sql.*;

import models.DoctorUser;

public final class DoctorProvider {

  private DoctorUser user;
  private final static DoctorProvider INSTANCE = new DoctorProvider();

  private DoctorProvider() {
  }

  public static DoctorProvider getInstance() {
    return INSTANCE;
  }

  public void setUser(DoctorUser u) {
    this.user = u;
  }

  public DoctorUser getDoctor() {
    return this.user;
  }

  // database function
  public ResultSet fetchFromDatabse() {
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/6jf8B9VQl4", "6jf8B9VQl4", "T1a7u4LCMY");
      stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM Authors");
      return rs;
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    return rs;
  }
}