package providers;

import java.sql.*;

import models.Doctor;
import screens.DoctorDashboard;
import screens.WelcomeScreen;
import services.JDBCService;
import services.NavigationService;

public final class DoctorProvider {

  private Doctor user;
  private final static DoctorProvider INSTANCE = new DoctorProvider();

  private DoctorProvider() {
  }

  public static DoctorProvider getInstance() {
    return INSTANCE;
  }

  private void setUser(Doctor u) {
    this.user = u;
  }

  public Doctor getDoctor() {
    return this.user;
  }

  public boolean doctorLogin(String email, String password) {
    String SELECT_QUERY = "SELECT * FROM doctors WHERE email = ? AND password = ?";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(SELECT_QUERY)) {
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);

      System.out.println(preparedStatement);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        Doctor du = new Doctor(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
            resultSet.getString(4), resultSet.getInt(7));
        setUser(du);
        DoctorDashboard dds = new DoctorDashboard();
        NavigationService.getInstance().pushScreen(dds.display());
        return true;
      }

    } catch (SQLException e) {
      // print SQL exception information
      printSQLException(e);
    }
    return false;
  }

  public boolean doctorLogout() {
    try {
      setUser(null);
      WelcomeScreen ws = new WelcomeScreen();
      NavigationService.getInstance().clearAllAndPush(ws.display());
      return true;
    } catch (Exception e) {
      System.out.println(e.toString());
      return false;
    }
  }

  public static void printSQLException(SQLException ex) {
    for (Throwable e : ex) {
      if (e instanceof SQLException) {
        e.printStackTrace(System.err);
        System.err.println("SQLState: " + ((SQLException) e).getSQLState());
        System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
        System.err.println("Message: " + e.getMessage());
        Throwable t = ex.getCause();
        while (t != null) {
          System.out.println("Cause: " + t);
          t = t.getCause();
        }
      }
    }
  }
}