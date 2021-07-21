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

public class DEditProfile {
  VBox root;
  ScrollPane sideBarScroller;

  String qualiStr;
  int ageInt;
  String genderStr;
  String specializationStr;
  String aboutStr;

  public VBox display() {
    this.root = new VBox();
    this.sideBarScroller = new ScrollPane(this.root);
    this.sideBarScroller.setFitToWidth(true);

    Doctor du = DoctorProvider.getInstance().getDoctor();

    Text title = new Text("Update Your Profile");
    title.setFont(new Font(30.0));
    Text subtitle = new Text("Please enter accurate information.");
    subtitle.setFont(new Font(21.0));

    // Qualifications
    Text t6 = new Text("Qualifications");
    TextField qualifications = new TextField();
    qualifications.setText(du.getQuali());
    VBox subroot6 = new VBox();
    subroot6.getChildren().addAll(t6, qualifications);
    subroot6.setSpacing(10);
    subroot6.setMaxWidth(300);
    subroot6.setAlignment(Pos.TOP_LEFT);

    // Age
    Text t7 = new Text("Age");
    TextField age = new TextField();
    age.setText(String.valueOf(du.getAge()));
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
    if (du.getGender().equals("M")) {
      tg.selectToggle(r1);
    } else if (du.getGender().equals("F")) {
      tg.selectToggle(r2);
    } else {
      tg.selectToggle(r3);
    }
    genderStr = du.getGender();
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
    specialization.setText(du.getSpclzn());
    VBox subroot9 = new VBox();
    subroot9.getChildren().addAll(t9, specialization);
    subroot9.setSpacing(10);
    subroot9.setMaxWidth(300);
    subroot9.setAlignment(Pos.TOP_LEFT);

    // About
    Text t10 = new Text("About");
    TextField about = new TextField();
    about.setText(du.getAbout());
    VBox subroot10 = new VBox();
    subroot10.getChildren().addAll(t10, about);
    subroot10.setSpacing(10);
    subroot10.setMaxWidth(300);
    subroot10.setAlignment(Pos.TOP_LEFT);

    Separator separator = new Separator();
    separator.setPrefWidth(300);
    Button b1 = new Button("Update");
    Button b2 = new Button("Go Back");

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(5);
    btns.maxHeight(50);
    btns.getChildren().addAll(b1, b2);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        qualiStr = qualifications.getText().trim();
        ageInt = Integer.parseInt(age.getText().trim());
        specializationStr = specialization.getText().trim();
        aboutStr = about.getText().trim();

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

        // showAlertMessage(Alert.AlertType.INFORMATION, "Details sent to database!",
        // "All details have been sent to database for validation and account
        // registration");

        Doctor updateAccount = new Doctor("", "", "", "", qualiStr, ageInt, genderStr, specializationStr, aboutStr);
        if (DoctorProvider.getInstance().updateProfile(updateAccount)) {
          showAlertMessage(Alert.AlertType.INFORMATION, "Updated Profile Successfully!",
              "Details were valid and your profile has been succesfully updated.");
          NavigationService.getInstance().popScreen();
          return;
        } else {
          showAlertMessage(Alert.AlertType.ERROR, "Updation Failed!",
              "There was an error while updating, contact the admin for details");
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
    this.root.getChildren().addAll(title, subroot6, subroot7, subroot8, subroot9, subroot10, separator, btns);
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
