package screens;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Patient;
import providers.DoctorProvider;
import providers.PatientProvider;
import services.NavigationService;

public class PatientRegistration {
  VBox root;
  VBox parentRoot;
  ScrollPane sideBarScroller;

  String fNameStr;
  String lNameStr;
  String emailStr;
  String passwordStr;
  String bldGrpStr;
  String genderStr;
  int ageInt;
  int heightInt;
  int weightInt;
  String allergiesStr;

  public VBox display() {
    this.root = new VBox();
    this.parentRoot = new VBox();

    this.sideBarScroller = new ScrollPane(this.root);
    this.sideBarScroller.setFitToWidth(true);

    Text title = new Text("Patient Registration");
    title.setFont(new Font(30.0));

    // Fname
    Text t1 = new Text("First Name");
    TextField fName = new TextField();
    VBox subroot1 = new VBox();
    subroot1.getChildren().addAll(t1, fName);
    subroot1.setSpacing(10);
    subroot1.setMaxWidth(300);
    subroot1.setAlignment(Pos.TOP_LEFT);

    // Lname
    Text t2 = new Text("Last Name");
    TextField lName = new TextField();
    VBox subroot2 = new VBox();
    subroot2.getChildren().addAll(t2, lName);
    subroot2.setSpacing(10);
    subroot2.setMaxWidth(300);
    subroot2.setAlignment(Pos.TOP_LEFT);

    // Email
    Text t3 = new Text("Email");
    TextField email = new TextField();
    VBox subroot3 = new VBox();
    subroot3.getChildren().addAll(t3, email);
    subroot3.setSpacing(10);
    subroot3.setMaxWidth(300);
    subroot3.setAlignment(Pos.TOP_LEFT);

    // Password
    Text t4 = new Text("Password");
    PasswordField password = new PasswordField();
    VBox subroot4 = new VBox();
    subroot4.getChildren().addAll(t4, password);
    subroot4.setSpacing(10);
    subroot4.setMaxWidth(300);
    subroot4.setAlignment(Pos.TOP_LEFT);

    // Blood Group
    Text t5 = new Text("Blood Group");
    ToggleGroup tg1 = new ToggleGroup();
    RadioButton r11 = new RadioButton("A+ve");
    RadioButton r12 = new RadioButton("B+ve");
    RadioButton r13 = new RadioButton("O+ve");
    RadioButton r14 = new RadioButton("AB+ve");
    RadioButton r15 = new RadioButton("A-ve");
    RadioButton r16 = new RadioButton("B-ve");
    RadioButton r17 = new RadioButton("O-ve");
    RadioButton r18 = new RadioButton("AB-ve");
    r11.setToggleGroup(tg1);
    r12.setToggleGroup(tg1);
    r13.setToggleGroup(tg1);
    r14.setToggleGroup(tg1);
    r15.setToggleGroup(tg1);
    r16.setToggleGroup(tg1);
    r17.setToggleGroup(tg1);
    r18.setToggleGroup(tg1);
    HBox bloodGroupToggle1 = new HBox();
    HBox bloodGroupToggle2 = new HBox();
    bloodGroupToggle1.setAlignment(Pos.CENTER);
    bloodGroupToggle1.setSpacing(5);
    bloodGroupToggle1.maxHeight(50);
    bloodGroupToggle1.getChildren().addAll(r11, r12, r13, r14);
    bloodGroupToggle2.setAlignment(Pos.CENTER);
    bloodGroupToggle2.setSpacing(5);
    bloodGroupToggle2.maxHeight(50);
    bloodGroupToggle2.getChildren().addAll(r15, r16, r17, r18);
    VBox subroot5 = new VBox();
    subroot5.getChildren().addAll(t5, bloodGroupToggle1, bloodGroupToggle2);
    subroot5.setSpacing(10);
    subroot5.setMaxWidth(300);
    subroot5.setAlignment(Pos.TOP_LEFT);
    tg1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
        RadioButton rb = (RadioButton) tg1.getSelectedToggle();
        if (rb != null) {
          bldGrpStr = rb.getText();
        }
      }
    });

    // Gender - edit
    Text t6 = new Text("Gender");
    ToggleGroup tg2 = new ToggleGroup();
    RadioButton r21 = new RadioButton("Male");
    RadioButton r22 = new RadioButton("Female");
    RadioButton r23 = new RadioButton("Non-Binary");
    r21.setToggleGroup(tg2);
    r22.setToggleGroup(tg2);
    r23.setToggleGroup(tg2);
    HBox genderToggle = new HBox();
    genderToggle.setAlignment(Pos.CENTER);
    genderToggle.setSpacing(5);
    genderToggle.maxHeight(50);
    genderToggle.getChildren().addAll(r21, r22, r23);
    VBox subroot6 = new VBox();
    subroot6.getChildren().addAll(t6, genderToggle);
    subroot6.setSpacing(10);
    subroot6.setMaxWidth(300);
    subroot6.setAlignment(Pos.TOP_LEFT);
    tg2.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
        RadioButton rb = (RadioButton) tg2.getSelectedToggle();
        if (rb != null) {
          String s = rb.getText();
          // update the string
          if (s.equals("Male")) {
            genderStr = "M";
          } else if (s.equals("Female")) {
            genderStr = "F";
          } else {
            genderStr = "O";
          }
        }
      }
    });

    // Age
    Text t7 = new Text("Age");
    TextField age = new TextField();
    // force the field to be numeric only
    age.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        if (!arg2.matches("\\d*")) {
          age.setText(arg2.replaceAll("[\\D]", ""));
        }
      }
    });
    VBox subroot7 = new VBox();
    subroot7.getChildren().addAll(t7, age);
    subroot7.setSpacing(10);
    subroot7.setMaxWidth(300);
    subroot7.setAlignment(Pos.TOP_LEFT);

    // Height
    Text t8 = new Text("Height");
    TextField height = new TextField();
    // force the field to be numeric only
    age.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        if (!arg2.matches("\\d*")) {
          age.setText(arg2.replaceAll("[\\D]", ""));
        }
      }
    });
    VBox subroot8 = new VBox();
    subroot8.getChildren().addAll(t8, height);
    subroot8.setSpacing(10);
    subroot8.setMaxWidth(300);
    subroot8.setAlignment(Pos.TOP_LEFT);

    // Weight
    Text t9 = new Text("Weight");
    TextField weight = new TextField();
    // force the field to be numeric only
    age.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
        if (!arg2.matches("\\d*")) {
          age.setText(arg2.replaceAll("[\\D]", ""));
        }
      }
    });
    VBox subroot9 = new VBox();
    subroot9.getChildren().addAll(t9, weight);
    subroot9.setSpacing(10);
    subroot9.setMaxWidth(300);
    subroot9.setAlignment(Pos.TOP_LEFT);

    // Allergies
    Text t10 = new Text("Allergies");
    TextField allergies = new TextField();
    VBox subroot10 = new VBox();
    subroot10.getChildren().addAll(t10, allergies);
    subroot10.setSpacing(10);
    subroot10.setMaxWidth(300);
    subroot10.setAlignment(Pos.TOP_LEFT);

    Separator separator = new Separator();
    separator.setPrefWidth(300);
    Button b1 = new Button("Register");
    Button b2 = new Button("Go Back");

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(5);
    btns.maxHeight(50);
    btns.getChildren().addAll(b1, b2);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        fNameStr = fName.getText().trim();
        lNameStr = lName.getText().trim();
        emailStr = email.getText().trim();
        passwordStr = password.getText().trim();
        ageInt = Integer.parseInt(age.getText().trim());
        heightInt = Integer.parseInt(height.getText().trim());
        weightInt = Integer.parseInt(weight.getText().trim());
        allergiesStr = allergies.getText().isEmpty() ? "" : allergies.getText().trim();

        if (fNameStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "First Name is Required!", "Please enter your First Name");
          return;
        }
        if (lNameStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Last Name is Required!", "Please enter your Last Name");
          return;
        }
        if (emailStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Email Required!", "Please enter your email");
          return;
        }
        if (passwordStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Password Required!", "Please enter your password");
          return;
        }
        if (genderStr.isEmpty() || genderStr.length() > 1) {
          showAlertMessage(Alert.AlertType.ERROR, "Gender is Required!",
              "There was an error while choosing the gender field, please try again");
          return;
        }
        if (bldGrpStr.isEmpty() || bldGrpStr.length() > 6) {
          showAlertMessage(Alert.AlertType.ERROR, "Blood Group is Required!",
              "There was an error while choosing the blood group field, please try again");
          return;
        }
        if (ageInt < 0 || ageInt > 130) {
          showAlertMessage(Alert.AlertType.ERROR, "Valid Age is Required!", "Please enter valid age");
          return;
        }
        if (heightInt < 0) {
          showAlertMessage(Alert.AlertType.ERROR, "Valid Height is Required!", "Please enter valid height");
          return;
        }
        if (weightInt < 0) {
          showAlertMessage(Alert.AlertType.ERROR, "Valid Weight is Required!", "Please enter valid weight");
          return;
        }

        Patient newAccount = new Patient(-1, fNameStr, lNameStr, emailStr, bldGrpStr, genderStr, ageInt, heightInt,
            weightInt, allergiesStr);

        if (PatientProvider.getInstance().patientRegistration(newAccount, passwordStr)) {
          showAlertMessage(Alert.AlertType.INFORMATION, "Registered Account Successfully!",
              "Details were valid and account has been succesfully registered.");
          fName.clear();
          lName.clear();
          email.clear();
          password.clear();
          age.clear();
          height.clear();
          weight.clear();
          allergies.clear();
          return;
        } else {
          showAlertMessage(Alert.AlertType.ERROR, "Registration Failed!",
              "There was an error while registering, contact the admin for details");
          return;
        }
      }
    });
    b2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        NavigationService.getInstance().popScreen();
      }
    });

    this.root.setAlignment(Pos.TOP_CENTER);
    this.root.setPadding(new Insets(50, 25, 25, 50));
    this.root.setSpacing(30);
    this.root.getChildren().addAll(title, subroot1, subroot2, subroot3, subroot4, subroot5, subroot6, subroot7,
        subroot8, subroot9, subroot10, separator, btns);

    this.parentRoot.getChildren().add(this.sideBarScroller);
    return this.parentRoot;
  }

  public static void showAlertMessage(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.show();
  }
}
