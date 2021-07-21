package screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import providers.DoctorProvider;
import services.NavigationService;

public class DoctorRegistrationOne {
  VBox root;
  String usernameStr;
  String passwordStr;

  public VBox display() {
    this.root = new VBox();
    Text title = new Text("Doctor Registration");
    title.setFont(new Font(30.0));
    Text subtitle = new Text(
        "This is an access controlled registration, only authorized medical council personnel may enter the right credentials and continue.");
    subtitle.setFont(new Font(21.0));

    // One field
    VBox subroot1 = new VBox();
    Text t1 = new Text("Username");
    TextField username = new TextField();
    subroot1.getChildren().addAll(t1, username);
    subroot1.setSpacing(10);
    subroot1.setMaxWidth(300);
    subroot1.setAlignment(Pos.TOP_LEFT);

    Text t2 = new Text("Password");
    PasswordField password = new PasswordField();
    VBox subroot2 = new VBox();
    subroot2.getChildren().addAll(t2, password);
    subroot2.setSpacing(10);
    subroot2.setMaxWidth(300);
    subroot2.setAlignment(Pos.TOP_LEFT);

    Separator separator = new Separator();
    separator.setPrefWidth(300);
    Button b1 = new Button("Enter");
    Button b2 = new Button("Go Back");

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(5);
    btns.maxHeight(50);
    btns.getChildren().addAll(b1, b2);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        usernameStr = username.getText().trim();
        passwordStr = password.getText().trim();
        if (usernameStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Username Required!", "Please enter your username");
          return;
        }
        if (passwordStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Password Required!", "Please enter your password");
          return;
        }
        // showAlertMessage(Alert.AlertType.INFORMATION, "Details sent to database!",
        // "Username and password have been sent to database for validation");

        if (!DoctorProvider.getInstance().medicAssnCreds(usernameStr, passwordStr)) {
          showAlertMessage(Alert.AlertType.ERROR, "Login Failed!", "Invalid credentials, you are not authorized.");
          return;
        } else {
          username.clear();
          password.clear();
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
    this.root.getChildren().addAll(title, subroot1, subroot2, separator, btns);
    return this.root;
  }

  public static void showAlertMessage(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.show();
  }
}
