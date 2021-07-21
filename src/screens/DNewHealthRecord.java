package screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.HealthRecord;
import models.Patient;
import providers.DoctorProvider;
import screens.components.DoctorsMenuBar;
import services.NavigationService;

public class DNewHealthRecord {
  private Patient forPatient;

  String dateStr;
  String symptomsStr;
  String med_condnStr;
  String diagnosisStr;
  String medicationStr;

  public DNewHealthRecord(Patient pu) {
    this.forPatient = pu;
    this.dateStr = java.time.LocalDate.now().toString();
  }

  public VBox display() {
    VBox root = new VBox();
    VBox parentRoot = new VBox();
    ScrollPane sideBarScroller = new ScrollPane();
    sideBarScroller = new ScrollPane(root);
    sideBarScroller.setFitToWidth(true);
    MenuBar menuBar = DoctorsMenuBar.getMenuBar();

    Text title = new Text("Create New Health Record");
    title.setFont(new Font(30.0));
    Text subtitle1 = new Text("Patient Name : " + forPatient.getName());
    subtitle1.setFont(new Font(18.0));
    Text subtitle2 = new Text("Patient ID : " + forPatient.getPatientID());
    subtitle2.setFont(new Font(18.0));

    VBox subroot6 = new VBox();
    subroot6.getChildren().addAll(subtitle1, subtitle2);
    subroot6.setSpacing(10);
    subroot6.setMaxWidth(360);
    subroot6.setAlignment(Pos.TOP_LEFT);

    DatePicker date = new DatePicker();
    date.setValue(java.time.LocalDate.now());
    date.getEditor().setEditable(false);
    date.setEditable(false);

    date.setOnMouseClicked(e -> {
      if (!date.isEditable())
        date.hide();
    });
    Text t5 = new Text("Date of Health Record Creation");
    VBox subroot5 = new VBox();
    subroot5.getChildren().addAll(t5, date);
    subroot5.setSpacing(10);
    subroot5.setMaxWidth(360);
    subroot5.setAlignment(Pos.TOP_LEFT);

    // Symptoms
    Text t1 = new Text("Symptoms");
    TextArea symptoms = new TextArea();
    symptoms.setPromptText("Enter any or all symptoms patient has");
    // symptoms.getParent().requestFocus();
    symptoms.setPrefColumnCount(6);
    symptoms.setPrefRowCount(4);
    symptoms.setPrefHeight(90);
    symptoms.setPrefWidth(360);
    VBox subroot1 = new VBox();
    subroot1.getChildren().addAll(t1, symptoms);
    subroot1.setSpacing(10);
    // subroot1.setMaxHeight(150);
    subroot1.setMaxWidth(360);
    subroot1.setAlignment(Pos.TOP_LEFT);

    // Medical Condition
    Text t2 = new Text("Medical Condition");
    TextArea med_condn = new TextArea();
    med_condn.setPromptText("Medical Condition experienced by the patient and reason for consultation");
    // med_condn.getParent().requestFocus();
    med_condn.setPrefColumnCount(6);
    med_condn.setPrefRowCount(4);
    med_condn.setPrefHeight(90);
    med_condn.setPrefWidth(360);
    VBox subroot2 = new VBox();
    subroot2.getChildren().addAll(t2, med_condn);
    subroot2.setSpacing(10);
    subroot2.setMaxHeight(150);
    subroot2.setMaxWidth(360);
    subroot2.setAlignment(Pos.TOP_LEFT);

    // Diagnosis
    Text t3 = new Text("Diagnosis");
    TextArea diagnosis = new TextArea();
    diagnosis.setPromptText("Diagnosis and inferences by the Doctor");
    // diagnosis.getParent().requestFocus();
    diagnosis.setPrefColumnCount(6);
    diagnosis.setPrefRowCount(10);
    diagnosis.setPrefHeight(190);
    diagnosis.setPrefWidth(360);
    VBox subroot3 = new VBox();
    subroot3.getChildren().addAll(t3, diagnosis);
    subroot3.setMaxHeight(300);
    subroot3.setSpacing(10);
    subroot3.setMaxWidth(360);
    subroot3.setAlignment(Pos.TOP_LEFT);

    // Medication
    Text t4 = new Text("Medication/Treatment");
    TextArea medication = new TextArea();
    medication.setPromptText("Medication or treatment recommended by the Doctor");
    // medication.getParent().requestFocus();
    medication.setPrefColumnCount(6);
    medication.setPrefRowCount(4);
    medication.setPrefHeight(90);
    medication.setPrefWidth(360);
    VBox subroot4 = new VBox();
    subroot4.getChildren().addAll(t4, medication);
    subroot4.setMaxHeight(150);
    subroot4.setSpacing(10);
    subroot4.setMaxWidth(360);
    subroot4.setAlignment(Pos.TOP_LEFT);

    VBox.setVgrow(symptoms, Priority.ALWAYS);
    VBox.setVgrow(med_condn, Priority.ALWAYS);
    VBox.setVgrow(diagnosis, Priority.ALWAYS);
    VBox.setVgrow(medication, Priority.ALWAYS);
    VBox.setVgrow(subroot1, Priority.ALWAYS);
    VBox.setVgrow(subroot2, Priority.ALWAYS);
    VBox.setVgrow(subroot3, Priority.ALWAYS);
    VBox.setVgrow(subroot4, Priority.ALWAYS);

    Separator separator = new Separator();
    separator.setPrefWidth(360);
    Button b1 = new Button("Create");
    Button b2 = new Button("Go Back");

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(5);
    btns.maxHeight(50);
    btns.getChildren().addAll(b1, b2);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        symptomsStr = symptoms.getText();
        med_condnStr = med_condn.getText();
        diagnosisStr = diagnosis.getText();
        medicationStr = medication.getText();

        if (symptomsStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Symptoms is Required!", "Please enter the patient's Symptoms");
          return;
        }
        if (med_condnStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Medical Condition is Required!",
              "Please enter the patient's Medical Condition");
          return;
        }
        if (diagnosisStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Diagnosis is Required!", "Please enter your Diagnosis");
          return;
        }
        if (medicationStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Medication Required!", "Please enter the recommended Medication");
          return;
        }

        HealthRecord newRecord = new HealthRecord(0, dateStr, symptomsStr, med_condnStr, diagnosisStr, medicationStr);
        if (DoctorProvider.getInstance().createNewHealthRecord(forPatient, newRecord)) {
          showAlertMessage(Alert.AlertType.INFORMATION, "Created Health Record Successfully!",
              "Details were valid and health record has been succesfully created.");
          NavigationService.getInstance().popScreen();
          NavigationService.getInstance().popScreen();
          return;
        } else {
          showAlertMessage(Alert.AlertType.ERROR, "Health Record Creation Failed!",
              "There was an error while creating the health record, contact the admin for details");
          return;
        }

      }
    });
    b2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        NavigationService.getInstance().popScreen();
      }
    });
    root.setAlignment(Pos.TOP_CENTER);
    root.setPadding(new Insets(50, 25, 25, 90));
    root.setSpacing(30);
    root.getChildren().addAll(title, subroot6, subroot5, subroot1, subroot2, subroot3, subroot4, btns);
    parentRoot.getChildren().addAll(menuBar, sideBarScroller);
    return parentRoot;

  }

  public static void showAlertMessage(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.show();
  }
}
