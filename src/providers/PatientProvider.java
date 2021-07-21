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

  public boolean patientLogin(String email, String password) {
    String SELECT_QUERY = "SELECT * FROM PATIENTS_LOGIN WHERE email = ? AND password = ?";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(SELECT_QUERY)) {
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      System.out.println(preparedStatement);

      ResultSet resultSetLogin = preparedStatement.executeQuery();
      if (resultSetLogin.next()) {
        String SELECT_QUERY2 = "SELECT * FROM PATIENTS WHERE email = ?";
        PreparedStatement prptStmt = JDBCService.getInstance().getConnection().prepareStatement(SELECT_QUERY2);
        prptStmt.setString(1, email);
        System.out.println(prptStmt);
        ResultSet resultSet = prptStmt.executeQuery();
        if (resultSet.next()) {
          Patient pu = new Patient(resultSet.getInt("PATIENT_ID"), resultSet.getString("FNAME"),
              resultSet.getString("LNAME"), resultSet.getString("EMAIL"), resultSet.getString("BLOOD_GROUP"),
              resultSet.getString("GENDER"), resultSet.getInt("AGE"), resultSet.getInt("HEIGHT"),
              resultSet.getInt("WEIGHT"), resultSet.getString("ALLERGIES"));
          setUser(pu);

          PatientDashboard pds = new PatientDashboard();
          NavigationService.getInstance().clearAllAndPush(pds.display());
          return true;
        }
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

  public boolean patientRegistration(Patient newAccount, String password) {
    String INSERT_QUERY = "INSERT INTO PATIENTS(FNAME, LNAME, EMAIL, BLOOD_GROUP, GENDER, AGE, HEIGHT, WEIGHT, ALLERGIES) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(INSERT_QUERY)) {
      preparedStatement.setString(1, newAccount.getFName());
      preparedStatement.setString(2, newAccount.getLName());
      preparedStatement.setString(3, newAccount.getEmail());
      preparedStatement.setString(4, newAccount.getBloodGrp());
      preparedStatement.setString(5, newAccount.getGender());
      preparedStatement.setInt(6, newAccount.getAge());
      preparedStatement.setInt(7, newAccount.getHeight());
      preparedStatement.setInt(8, newAccount.getWeight());
      preparedStatement.setString(9, newAccount.getAllergies());
      System.out.println(preparedStatement);
      int rslt = preparedStatement.executeUpdate();
      if (rslt > 0) {
        String INSERT_QUERY2 = "INSERT INTO PATIENTS_LOGIN VALUES(?, ?)";
        PreparedStatement prptStmt = JDBCService.getInstance().getConnection().prepareStatement(INSERT_QUERY2);
        prptStmt.setString(1, newAccount.getEmail());
        prptStmt.setString(2, password);
        int res = prptStmt.executeUpdate();
        if (res > 0) {
          System.out.println("Successfully registered a new patient's acccount");
          return true;
        }
      }
    } catch (SQLException e) {
      // print SQL exception information
      printSQLException(e);
    }
    return false;
  }

  public boolean updateProfile(Patient newAccount) {
    String UPDATE_QUERY = "UPDATE PATIENTS SET FNAME = ?, LNAME = ?, BLOOD_GROUP = ?, GENDER = ?, AGE = ?, HEIGHT = ?, WEIGHT = ?, ALLERGIES = ? WHERE PATIENT_ID = ?";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(UPDATE_QUERY)) {
      preparedStatement.setString(1, newAccount.getFName());
      preparedStatement.setString(2, newAccount.getLName());
      preparedStatement.setString(3, newAccount.getBloodGrp());
      preparedStatement.setString(4, newAccount.getGender());
      preparedStatement.setInt(5, newAccount.getAge());
      preparedStatement.setInt(6, newAccount.getHeight());
      preparedStatement.setInt(7, newAccount.getWeight());
      preparedStatement.setString(8, newAccount.getAllergies());
      preparedStatement.setInt(9, user.getPatientID());
      System.out.println(preparedStatement);
      int rslt = preparedStatement.executeUpdate();
      if (rslt > 0) {
        System.out.println("Successfully updated patient's acccount");
        // update patient var
        String SELECT_QUERY2 = "SELECT * FROM PATIENTS WHERE email = ?";
        PreparedStatement prptStmt = JDBCService.getInstance().getConnection().prepareStatement(SELECT_QUERY2);
        prptStmt.setString(1, user.getEmail());
        System.out.println(prptStmt);
        ResultSet resultSet = prptStmt.executeQuery();
        if (resultSet.next()) {
          Patient pu = new Patient(resultSet.getInt("PATIENT_ID"), resultSet.getString("FNAME"),
              resultSet.getString("LNAME"), resultSet.getString("EMAIL"), resultSet.getString("BLOOD_GROUP"),
              resultSet.getString("GENDER"), resultSet.getInt("AGE"), resultSet.getInt("HEIGHT"),
              resultSet.getInt("WEIGHT"), resultSet.getString("ALLERGIES"));
          setUser(pu);
          // PatientDashboard pds = new PatientDashboard();
          // NavigationService.getInstance().popScreen();
          return true;
        }
      }
    } catch (SQLException e) {
      // print SQL exception information
      printSQLException(e);
    }
    return false;
  }

  public Patient[] findPatients(String email) {
    Patient[] searchResults = new Patient[10];
    int i = 0;
    String SELECT_QUERY = "SELECT * FROM PATIENTS WHERE email LIKE ?";
    String subString = "%" + email + "%";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(SELECT_QUERY)) {
      preparedStatement.setString(1, subString);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next() && i <= 10) {
        Patient pu = new Patient(resultSet.getInt("PATIENT_ID"), resultSet.getString("FNAME"),
            resultSet.getString("LNAME"), resultSet.getString("EMAIL"), resultSet.getString("BLOOD_GROUP"),
            resultSet.getString("GENDER"), resultSet.getInt("AGE"), resultSet.getInt("HEIGHT"),
            resultSet.getInt("WEIGHT"), resultSet.getString("ALLERGIES"));
        searchResults[i++] = pu;
      }
    } catch (SQLException e) {
      // print SQL exception information
      printSQLException(e);
    }
    return searchResults;
  }

  public Patient findPatient(String email) {
    String SELECT_QUERY = "SELECT * FROM PATIENTS WHERE email = ?";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(SELECT_QUERY)) {
      preparedStatement.setString(1, email);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Patient pu = new Patient(resultSet.getInt("PATIENT_ID"), resultSet.getString("FNAME"),
            resultSet.getString("LNAME"), resultSet.getString("EMAIL"), resultSet.getString("BLOOD_GROUP"),
            resultSet.getString("GENDER"), resultSet.getInt("AGE"), resultSet.getInt("HEIGHT"),
            resultSet.getInt("WEIGHT"), resultSet.getString("ALLERGIES"));
        return pu;
      }
    } catch (SQLException e) {
      // print SQL exception information
      printSQLException(e);
    }
    return null;
  }

}