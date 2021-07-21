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
import models.Doctor;
import providers.DoctorProvider;
import services.NavigationService;

public class DoctorRegistrationTwo {
  VBox root;
  ScrollPane sideBarScroller;

  String mLicenseStr;
  String fNameStr;
  String lNameStr;
  String emailStr;
  String passwordStr;
  String qualiStr;
  int ageInt;
  String genderStr;
  String specializationStr;
  String aboutStr;

  public VBox display() {
    this.root = new VBox();
    this.sideBarScroller = new ScrollPane(this.root);
    this.sideBarScroller.setFitToWidth(true);

    Text title = new Text("Doctor Registration");
    title.setFont(new Font(30.0));
    Text subtitle = new Text("Please enter accurate information.");
    subtitle.setFont(new Font(21.0));

    // One field
    Text t1 = new Text("Medical License Number");
    TextField mLicense = new TextField();
    VBox subroot1 = new VBox();
    subroot1.getChildren().addAll(t1, mLicense);
    subroot1.setSpacing(10);
    subroot1.setMaxWidth(300);
    subroot1.setAlignment(Pos.TOP_LEFT);

    // Fname
    Text t2 = new Text("First Name");
    TextField fName = new TextField();
    VBox subroot2 = new VBox();
    subroot2.getChildren().addAll(t2, fName);
    subroot2.setSpacing(10);
    subroot2.setMaxWidth(300);
    subroot2.setAlignment(Pos.TOP_LEFT);

    // Lname
    Text t3 = new Text("Last Name");
    TextField lName = new TextField();
    VBox subroot3 = new VBox();
    subroot3.getChildren().addAll(t3, lName);
    subroot3.setSpacing(10);
    subroot3.setMaxWidth(300);
    subroot3.setAlignment(Pos.TOP_LEFT);

    // Email
    Text t4 = new Text("Email");
    TextField email = new TextField();
    VBox subroot4 = new VBox();
    subroot4.getChildren().addAll(t4, email);
    subroot4.setSpacing(10);
    subroot4.setMaxWidth(300);
    subroot4.setAlignment(Pos.TOP_LEFT);

    // Password
    Text t5 = new Text("Password");
    PasswordField password = new PasswordField();
    VBox subroot5 = new VBox();
    subroot5.getChildren().addAll(t5, password);
    subroot5.setSpacing(10);
    subroot5.setMaxWidth(300);
    subroot5.setAlignment(Pos.TOP_LEFT);

    // Qualifications
    Text t6 = new Text("Qualifications");
    TextField qualifications = new TextField();
    VBox subroot6 = new VBox();
    subroot6.getChildren().addAll(t6, qualifications);
    subroot6.setSpacing(10);
    subroot6.setMaxWidth(300);
    subroot6.setAlignment(Pos.TOP_LEFT);

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

    // Gender - edit
    Text t8 = new Text("Gender");
    ToggleGroup tg = new ToggleGroup();
    RadioButton r1 = new RadioButton("Male");
    RadioButton r2 = new RadioButton("Female");
    RadioButton r3 = new RadioButton("Non-Binary");
    r1.setToggleGroup(tg);
    r2.setToggleGroup(tg);
    r3.setToggleGroup(tg);
    HBox genderToggle = new HBox();
    genderToggle.setAlignment(Pos.CENTER);
    genderToggle.setSpacing(5);
    genderToggle.maxHeight(50);
    genderToggle.getChildren().addAll(r1, r2, r3);
    VBox subroot8 = new VBox();
    subroot8.getChildren().addAll(t8, genderToggle);
    subroot8.setSpacing(10);
    subroot8.setMaxWidth(300);
    subroot8.setAlignment(Pos.TOP_LEFT);
    tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
        RadioButton rb = (RadioButton) tg.getSelectedToggle();
        if (rb != null) {
          String s = rb.getText();
          // change the label
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

    // Specialization
    Text t9 = new Text("Specialization");
    TextField specialization = new TextField();
    VBox subroot9 = new VBox();
    subroot9.getChildren().addAll(t9, specialization);
    subroot9.setSpacing(10);
    subroot9.setMaxWidth(300);
    subroot9.setAlignment(Pos.TOP_LEFT);

    // About
    Text t10 = new Text("About");
    TextField about = new TextField();
    VBox subroot10 = new VBox();
    subroot10.getChildren().addAll(t10, about);
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
        mLicenseStr = mLicense.getText().trim();
        fNameStr = fName.getText().trim();
        lNameStr = lName.getText().trim();
        emailStr = email.getText().trim();
        passwordStr = password.getText().trim();
        qualiStr = qualifications.getText().trim();
        ageInt = Integer.parseInt(age.getText().trim());
        specializationStr = specialization.getText().trim();
        aboutStr = about.getText().trim();

        if (mLicenseStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Medical License is Required!",
              "Please enter the doctor's Medical License Number");
          return;
        }
        if (fNameStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "First Name is Required!", "Please enter the doctor's First Name");
          return;
        }
        if (lNameStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Last Name is Required!", "Please enter the doctor's Last Name");
          return;
        }
        if (emailStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Email is Required!", "Please enter the doctor's Email");
          return;
        }
        if (qualiStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Qualifications is Required!",
              "Please enter the doctor's Qualifications");
          return;
        }
        if (specializationStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Specialization is Required!",
              "Please enter the doctor's Specialization");
          return;
        }
        if (genderStr.isEmpty() || genderStr.length() > 1) {
          showAlertMessage(Alert.AlertType.ERROR, "Gender is Required!",
              "There was an error while choosing the gender field, please try again");
          return;
        }
        if (ageInt < 0 || ageInt > 100) {
          showAlertMessage(Alert.AlertType.ERROR, "Valid Age is Required!", "Please enter the doctor's valid age");
          return;
        }
        if (passwordStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Password Required!",
              "Please enter the new doctor account's password");
          return;
        }
        // showAlertMessage(Alert.AlertType.INFORMATION, "Details sent to database!",
        //     "All details have been sent to database for validation and account registration");

        Doctor newAccount = new Doctor(mLicenseStr, fNameStr, lNameStr, emailStr, qualiStr, ageInt, genderStr,
            specializationStr, aboutStr);
        if (DoctorProvider.getInstance().doctorRegistration(newAccount, passwordStr)) {
          showAlertMessage(Alert.AlertType.INFORMATION, "Registered Account Successfully!",
              "Details were valid and account has been succesfully registered.");
          mLicense.clear();
          fName.clear();
          lName.clear();
          email.clear();
          password.clear();
          qualifications.clear();
          age.clear();
          specialization.clear();
          about.clear();
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
    VBox newRoot = new VBox();
    newRoot.getChildren().add(this.sideBarScroller);
    return newRoot;
  }

  public static void showAlertMessage(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.show();
  }
}
