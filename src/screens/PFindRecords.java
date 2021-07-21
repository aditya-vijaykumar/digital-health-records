package screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import models.HealthRecord;
import providers.PatientProvider;
import screens.components.PatientsMenuBar;
import services.JDBCService;
import services.NavigationService;

public class PFindRecords {
  private TableView<Consultations> table = new TableView();
  private ObservableList<Consultations> data = FXCollections.observableArrayList();

  public VBox display() {
    VBox root = new VBox();
    VBox parentRoot = new VBox();
    MenuBar menuBar = PatientsMenuBar.getMenuBar();
    tableInit();

    Text title = new Text("Search Among Past Consultations");
    title.setFont(new Font(30.0));

    VBox subroot1 = new VBox();
    Text t1 = new Text("Doctor's First Name");
    TextField doctorFirstName = new TextField();
    subroot1.getChildren().addAll(t1, doctorFirstName);
    subroot1.setSpacing(10);
    subroot1.setMaxWidth(300);
    subroot1.setAlignment(Pos.TOP_LEFT);

    Button b1 = new Button("Search");
    Button b2 = new Button("Go Back");

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(30);
    btns.maxHeight(180);
    btns.getChildren().addAll(b1, b2);

    Separator separator = new Separator();
    separator.setPrefWidth(300);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        String doctorNameStr = "%" + doctorFirstName.getText().trim() + "%";
        String SELECT_QUERY = "SELECT C.HR_ID, C.DATE_OF_CONSULT, D.FNAME, D.LNAME, H.SYMPTOMS, D.MLICENSE FROM CONSULTATIONS C, HEALTH_RECORDS H,DOCTORS D WHERE D.FNAME LIKE ? AND C.P_ID = ? AND C.MLICENSE = D.MLICENSE AND C.HR_ID = H.RECORD_NO;";
        try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
            .prepareStatement(SELECT_QUERY);) {
          preparedStatement.setString(1, doctorNameStr);
          preparedStatement.setInt(2, PatientProvider.getInstance().getPatient().getPatientID());
          ResultSet rs = preparedStatement.executeQuery();
          data.clear();
          while (rs.next()) {
            System.out.println(rs.getString(3) + " " + rs.getString(4) + rs.getDate(2).toString() + rs.getString(5)
                + String.valueOf(rs.getInt(1)));
            data.add(new Consultations(String.valueOf(rs.getInt(1)), rs.getDate(2).toString(),
                rs.getString(3) + " " + rs.getString(4), rs.getString(5), rs.getString(6)));
          }
          table.setItems(data);
        } catch (SQLException e) {
          System.out.println("Exception caught" + e.toString());
        }

      }
    });

    b2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        NavigationService.getInstance().popScreen();
      }
    });
    root.setAlignment(Pos.TOP_CENTER);
    root.setPadding(new Insets(50, 25, 25, 50));
    root.setSpacing(30);
    root.getChildren().addAll(title, subroot1, btns, separator, table);
    parentRoot.getChildren().addAll(menuBar, root);
    return parentRoot;
  }

  private void tableInit() {
    TableColumn healthRecordNo = new TableColumn("Health Record Number");
    healthRecordNo.setMinWidth(100);
    healthRecordNo.setCellValueFactory(new PropertyValueFactory<Consultations, String>("healthRecordNumber"));
    // 2nd
    TableColumn recordDate = new TableColumn("Date");
    recordDate.setMinWidth(100);
    recordDate.setCellValueFactory(new PropertyValueFactory<Consultations, String>("dateOfConsultation"));
    // 3rd
    TableColumn patientName = new TableColumn("Doctor's Name");
    patientName.setMinWidth(100);
    patientName.setCellValueFactory(new PropertyValueFactory<Consultations, String>("doctorName"));

    // 4th
    TableColumn symptomsClm = new TableColumn("Symptoms");
    symptomsClm.setMinWidth(100);
    symptomsClm.setCellValueFactory(new PropertyValueFactory<Consultations, String>("symptoms"));

    table.getColumns().addAll(healthRecordNo, recordDate, patientName, symptomsClm);
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table.maxHeight(250);

    TableColumn<Consultations, Void> colBtn = new TableColumn("Button Column");

    Callback<TableColumn<Consultations, Void>, TableCell<Consultations, Void>> cellFactory = new Callback<TableColumn<Consultations, Void>, TableCell<Consultations, Void>>() {
      @Override
      public TableCell<Consultations, Void> call(final TableColumn<Consultations, Void> param) {
        final TableCell<Consultations, Void> cell = new TableCell<Consultations, Void>() {
          private final Button btn = new Button("View");
          {
            btn.setOnAction((ActionEvent event) -> {
              Consultations data = (Consultations) this.getTableView().getItems().get(this.getIndex());

              String SELECT_QUERY = "SELECT H.CREATE_DATE, H.SYMPTOMS , H.MED_CONDITION, H.DIAGNOSIS , H.MEDICATION  FROM HEALTH_RECORDS H,PATIENTS P WHERE H.RECORD_NO = ?";
              try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
                  .prepareStatement(SELECT_QUERY);) {
                preparedStatement.setInt(1, Integer.parseInt(data.getHealthRecordNumber()));
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                  showAlertMessage(Alert.AlertType.ERROR, "Error",
                      "There was an error while oening the record. Restart the app or contact the admin for details");
                  return;
                }
                HealthRecord newRecord = new HealthRecord(Integer.parseInt(data.getHealthRecordNumber()),
                    rs.getDate(1).toString(), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                PViewHealthRecord pvhr = new PViewHealthRecord(newRecord, data.getDoctorName(),
                    data.getDoctorLicense());
                NavigationService.getInstance().pushScreen(pvhr.display());
              } catch (SQLException e) {
                System.out.println("Exception caught" + e.toString());
              }
              System.out.println("selectedData: " + data.getHealthRecordNumber() + data.getDoctorName());
            });
          }

          @Override
          public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setGraphic(null);
            } else {
              setGraphic(btn);
            }
          }
        };
        return cell;
      }
    };

    colBtn.setCellFactory(cellFactory);
    table.getColumns().add(colBtn);

    String SELECT_QUERY = "SELECT C.HR_ID, C.DATE_OF_CONSULT, D.FNAME, D.LNAME, H.SYMPTOMS, C.MLICENSE FROM CONSULTATIONS C, HEALTH_RECORDS H, DOCTORS D WHERE C.P_ID = ? AND C.HR_ID = H.RECORD_NO AND D.MLICENSE = C.MLICENSE";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(SELECT_QUERY);) {
      preparedStatement.setInt(1, PatientProvider.getInstance().getPatient().getPatientID());
      ResultSet rs = preparedStatement.executeQuery();
      data.clear();
      while (rs.next()) {
        System.out.println(rs.getString(3) + " " + rs.getString(4) + rs.getDate(2).toString() + rs.getString(5)
            + String.valueOf(rs.getInt(1)));
        data.add(new Consultations(String.valueOf(rs.getInt(1)), rs.getDate(2).toString(),
            rs.getString(3) + " " + rs.getString(4), rs.getString(5), rs.getString(6)));
      }
      table.setItems(data);
    } catch (SQLException e) {
      System.out.println("Exception caught" + e.toString());
    }
  }

  public static class Consultations {

    private final SimpleStringProperty doctorName;
    private final SimpleStringProperty dateOfConsultation;
    private final SimpleStringProperty symptoms;
    private final SimpleStringProperty healthRecordNumber;
    private final SimpleStringProperty doctorLicense;

    Consultations(String hr_id, String dateOfConsultation, String doctorName, String symptoms, String doctorLicense) {
      this.healthRecordNumber = new SimpleStringProperty(hr_id);
      this.dateOfConsultation = new SimpleStringProperty(dateOfConsultation);
      this.doctorName = new SimpleStringProperty(doctorName);
      this.symptoms = new SimpleStringProperty(symptoms);
      this.doctorLicense = new SimpleStringProperty(doctorLicense);
    }

    public String getDoctorName() {
      return doctorName.get();
    }

    public String getDateOfConsultation() {
      return dateOfConsultation.get();
    }

    public String getSymptoms() {
      return symptoms.get();
    }

    public String getHealthRecordNumber() {
      return healthRecordNumber.get();
    }

    public String getDoctorLicense() {
      return doctorLicense.get();
    }
  }

  public static void showAlertMessage(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.show();
  }
}
