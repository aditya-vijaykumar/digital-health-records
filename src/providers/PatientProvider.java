package providers;

import java.sql.*;

import models.Patient;
import screens.PatientDashboard;
import screens.WelcomeScreen;
import services.JDBCService;
import services.NavigationService;

public final class PatientProvider {

  private Patient user;
  private final static PatientProvider INSTANCE = new PatientProvider();

  private PatientProvider() {
  }

  public static PatientProvider getInstance() {
    return INSTANCE;
  }

  private void setUser(Patient u) {
    this.user = u;
  }

  public Patient getPatient() {
    return this.user;
  }

  public boolean patientLogin(String email, String password) {
    String SELECT_QUERY = "SELECT * FROM patients WHERE email = ? AND password = ?";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(SELECT_QUERY)) {
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);

      System.out.println(preparedStatement);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        Patient pu = new Patient(resultSet.getInt("patientid"), resultSet.getString("fname"),
            resultSet.getString("lname"), resultSet.getString("email"), resultSet.getString("bloodgroup"),
            resultSet.getString("allergies"), resultSet.getInt("weight"), resultSet.getInt("height"),
            resultSet.getInt("age"));
        setUser(pu);

        PatientDashboard pds = new PatientDashboard();
        NavigationService.getInstance().pushScreen(pds.display());
        return true;
      }

    } catch (SQLException e) {
      // print SQL exception information
      printSQLException(e);
    }
    return false;
  }

  public boolean patientLogout() {
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