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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Patient;
import providers.PatientProvider;
import screens.components.DoctorsMenuBar;
import services.NavigationService;

public class DSearchPatient {
  String emailStr;

  public VBox display() {
    VBox root = new VBox();
    VBox parentRoot = new VBox();
    MenuBar menuBar = DoctorsMenuBar.getMenuBar();

    Text title = new Text("Search Patient");
    title.setFont(new Font(30.0));

    VBox subroot1 = new VBox();
    Text t1 = new Text("Patient's Email");
    TextField email = new TextField();
    subroot1.getChildren().addAll(t1, email);
    subroot1.setSpacing(10);
    subroot1.setMaxWidth(300);
    subroot1.setAlignment(Pos.TOP_LEFT);

    Button b1 = new Button("Search");

    VBox subroot2 = new VBox();
    subroot2.setSpacing(10);
    subroot2.setMaxWidth(300);
    subroot2.setAlignment(Pos.TOP_LEFT);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        emailStr = email.getText().trim();

        if (emailStr.isEmpty()) {
          showAlertMessage(Alert.AlertType.ERROR, "Email Required!", "Please enter the patient's email");
          return;
        }
        Patient pFound = PatientProvider.getInstance().findPatient(emailStr);

        if (pFound == null) {
          Text resultText = new Text("There were 0 matches for your query");
          resultText.setFont(new Font(21.0));
          subroot2.getChildren().setAll(resultText);
        } else {
          VBox card = searchResultCard(pFound);
          subroot2.getChildren().setAll(card);
        }
      }
    });

    Button b2 = new Button("Go Back");

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(30);
    btns.maxHeight(180);

    btns.getChildren().addAll(b1, b2);

    b2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        NavigationService.getInstance().popScreen();
      }
    });
    Separator separator = new Separator();
    separator.setPrefWidth(300);

    root.setAlignment(Pos.TOP_CENTER);
    root.setPadding(new Insets(50, 25, 25, 50));
    root.setSpacing(30);
    root.getChildren().addAll(title, subroot1, btns, separator, subroot2);
    parentRoot.getChildren().addAll(menuBar, root);
    return parentRoot;
  }

  private VBox searchResultCard(Patient pu) {
    VBox card = new VBox();
    final Background unfocusBackground = new Background(
        new BackgroundFill(Color.web("#F4F4F4"), new CornerRadii(5), new Insets(10, 10, 10, 10)));

    Text cardTitle = new Text("Name: " + pu.getName());
    cardTitle.setFont(new Font(21.0));
    Text cardEmail = new Text("Email: " + pu.getEmail());
    cardEmail.setFont(new Font(18.0));
    Text cardAge = new Text("Age: " + pu.getAge());
    cardAge.setFont(new Font(18.0));

    Button btn = new Button("Create Health Record.");
    btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        DNewHealthRecord dnhr = new DNewHealthRecord(pu);
        NavigationService.getInstance().pushScreen(dnhr.display());
        ;
      }
    });

    card.getChildren().addAll(cardTitle, cardEmail, cardAge, btn);
    card.setSpacing(10);
    card.setMaxWidth(300);
    card.setAlignment(Pos.TOP_CENTER);
    // card.setBackground( unfocusBackground );
    card.setBackground(unfocusBackground);
    return card;
  }

  public static void showAlertMessage(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.show();
  }
}
