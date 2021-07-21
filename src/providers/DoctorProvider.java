package providers;

import java.sql.*;

import models.Doctor;
import models.HealthRecord;
import models.Patient;
import screens.DoctorDashboard;
import screens.DoctorRegistrationTwo;
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

  public boolean doctorLogin(String email, String password) {
    String SELECT_QUERY = "SELECT * FROM DOCTORS_LOGIN WHERE email = ? AND password = ?";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(SELECT_QUERY)) {
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);

      System.out.println(preparedStatement);

      ResultSet resultSetLogin = preparedStatement.executeQuery();
      if (resultSetLogin.next()) {
        String SELECT_QUERY2 = "SELECT * FROM DOCTORS WHERE email = ?";
        PreparedStatement prptStmt = JDBCService.getInstance().getConnection().prepareStatement(SELECT_QUERY2);
        prptStmt.setString(1, email);
        System.out.println(prptStmt);
        ResultSet resultSet = prptStmt.executeQuery();
        if (resultSet.next()) {
          Doctor du = new Doctor(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
              resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7),
              resultSet.getString(8), resultSet.getString(9));
          setUser(du);
          DoctorDashboard dds = new DoctorDashboard();
          NavigationService.getInstance().clearAllAndPush(dds.display());
          return true;
        } else {
          return false;
        }
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

  public boolean medicAssnCreds(String username, String password) {
    String SELECT_QUERY = "SELECT * FROM MEDIC_ASSN_LOGIN WHERE USERNAME = ? AND password = ?";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(SELECT_QUERY)) {
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      System.out.println(preparedStatement);
      ResultSet resultSetLogin = preparedStatement.executeQuery();
      if (resultSetLogin.next()) {
        DoctorRegistrationTwo dr2 = new DoctorRegistrationTwo();
        NavigationService.getInstance().pushScreen(dr2.display());
        return true;
      }
    } catch (SQLException e) {
      // print SQL exception information
      printSQLException(e);
    }
    return false;
  }

  public boolean doctorRegistration(Doctor newAccount, String password) {
    String INSERT_QUERY = "INSERT INTO DOCTORS VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(INSERT_QUERY)) {
      preparedStatement.setString(1, newAccount.getMLicense());
      preparedStatement.setString(2, newAccount.getFName());
      preparedStatement.setString(3, newAccount.getLName());
      preparedStatement.setString(4, newAccount.getEmail());
      preparedStatement.setString(5, newAccount.getQuali());
      preparedStatement.setInt(6, newAccount.getAge());
      preparedStatement.setString(7, newAccount.getGender());
      preparedStatement.setString(8, newAccount.getSpclzn());
      preparedStatement.setString(9, newAccount.getAbout());
      System.out.println(preparedStatement);
      int rslt = preparedStatement.executeUpdate();
      if (rslt > 0) {
        String INSERT_QUERY2 = "INSERT INTO DOCTORS_LOGIN VALUES(?, ?, ?)";
        PreparedStatement prptStmt = JDBCService.getInstance().getConnection().prepareStatement(INSERT_QUERY2);
        prptStmt.setString(1, newAccount.getEmail());
        prptStmt.setString(2, password);
        prptStmt.setString(3, newAccount.getMLicense());
        int res = prptStmt.executeUpdate();
        if (res > 0) {
          System.out.println("Successfully registered a new doctor's acccount");
          return true;
        }
      }
    } catch (SQLException e) {
      // print SQL exception information
      printSQLException(e);
    }
    return false;
  }

  public boolean updateProfile(Doctor newAccount) {
    String UPDATE_QUERY = "UPDATE DOCTORS SET QUALIFICATIONS = ?,  AGE = ? , GENDER = ? , SPECIALIZATION = ?, ABOUT = ?  WHERE MLICENSE = ?";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(UPDATE_QUERY)) {
      preparedStatement.setString(1, newAccount.getQuali());
      preparedStatement.setInt(2, newAccount.getAge());
      preparedStatement.setString(3, newAccount.getGender());
      preparedStatement.setString(4, newAccount.getSpclzn());
      preparedStatement.setString(5, newAccount.getAbout());
      preparedStatement.setString(6, user.getMLicense());
      System.out.println(preparedStatement);
      int rslt = preparedStatement.executeUpdate();
      if (rslt > 0) {
        System.out.println("Successfully updated acccount");
        //update the dctor variable
        String SELECT_QUERY = "SELECT * FROM DOCTORS WHERE email = ?";
        PreparedStatement prptStmt = JDBCService.getInstance().getConnection().prepareStatement(SELECT_QUERY);
        prptStmt.setString(1, user.getEmail());
        System.out.println(prptStmt);
        ResultSet resultSet = prptStmt.executeQuery();
        if (resultSet.next()) {
          Doctor du = new Doctor(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
              resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7),
              resultSet.getString(8), resultSet.getString(9));
          setUser(du);
          // DoctorDashboard dds = new DoctorDashboard();
          // NavigationService.getInstance().clearAllAndPush(dds.display());
          return true;
        }
      }
    } catch (SQLException e) {
      // print SQL exception information
      printSQLException(e);
    }
    return false;
  }

  public boolean createNewHealthRecord(Patient thePatient, HealthRecord newRecord) {
    String INSERT_QUERY = "INSERT INTO HEALTH_RECORDS(CREATE_DATE, SYMPTOMS, MED_CONDITION, DIAGNOSIS, MEDICATION) VALUES(?, ?, ?, ?, ?)";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(INSERT_QUERY)) {
      preparedStatement.setString(1, newRecord.getCreateDate());
      preparedStatement.setString(2, newRecord.getSymptoms());
      preparedStatement.setString(3, newRecord.getMedCondtn());
      preparedStatement.setString(4, newRecord.getDiagnosis());
      preparedStatement.setString(5, newRecord.getMedication());
      System.out.println(preparedStatement);
      int rslt = preparedStatement.executeUpdate();
      if (rslt > 0) {
        String SELECT_QUERY = "SELECT MAX(RECORD_NO) FROM HEALTH_RECORDS";
        PreparedStatement prpdStmtTwo = JDBCService.getInstance().getConnection().prepareStatement(SELECT_QUERY);
        // prpdStmtTwo.setString(1, newRecord.getCreateDate());
        // prpdStmtTwo.setString(2, newRecord.getSymptoms());
        // prpdStmtTwo.setString(3, newRecord.getDiagnosis());
        // prpdStmtTwo.setString(4, newRecord.getMedication());
        System.out.println(prpdStmtTwo);
        ResultSet res = prpdStmtTwo.executeQuery();
        if (res.next()) {
          int hr_id = res.getInt(1);
          String INSERT_QUERY2 = "INSERT INTO CONSULTATIONS VALUES(?, ?, ?, ?)";
          PreparedStatement prpdStmtThree = JDBCService.getInstance().getConnection().prepareStatement(INSERT_QUERY2);
          prpdStmtThree.setString(1, this.user.getMLicense());
          prpdStmtThree.setInt(2, thePatient.getPatientID());
          prpdStmtThree.setInt(3, hr_id);
          prpdStmtThree.setString(4, newRecord.getCreateDate());
          int rslt2 = prpdStmtThree.executeUpdate();
          if (rslt2 > 0) {
            System.out.println("Successfully created the health record");
            return true;
          }
        }
      }
    } catch (SQLException e) {
      // print SQL exception information
      printSQLException(e);
    }
    return false;
  }

}