package screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import services.NavigationService;

public class PatientStartScreen {
  VBox root = new VBox();

  public VBox display() {

    ImageView logoimgview = new ImageView("file:images/icon.png");
    logoimgview.maxHeight(270);
    logoimgview.maxWidth(270);
    logoimgview.setFitHeight(270);
    logoimgview.setFitWidth(270);
    Label titleLabel = new Label("Patient Start Screen", logoimgview);
    titleLabel.setFont(new Font(30.0));
    titleLabel.setContentDisplay(ContentDisplay.TOP);

    Button b1 = new Button("Login");
    Button b2 = new Button("Registration");
    Button b3 = new Button("Go Back");

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(25);
    btns.maxHeight(50);
    btns.getChildren().addAll(b1, b2, b3);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        PatientLoginScreen ps = new PatientLoginScreen();
        NavigationService.getInstance().pushScreen(ps.display());
      }
    });

    b2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        PatientRegistration prs = new PatientRegistration();
        NavigationService.getInstance().pushScreen(prs.display());
      }
    });

    b3.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        NavigationService.getInstance().popScreen();
      }
    });

    this.root.getChildren().addAll(titleLabel, btns);
    this.root.setAlignment(Pos.CENTER);

    this.root.setPadding(new Insets(50, 25, 25, 50));
    this.root.setSpacing(30);
    return this.root;
  }
}
