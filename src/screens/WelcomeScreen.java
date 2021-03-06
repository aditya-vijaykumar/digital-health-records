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

public class WelcomeScreen {
  VBox root = new VBox();

  public VBox display() {

    ImageView logoimgview = new ImageView("file:images/icon.png");
    logoimgview.setFitHeight(300);
    logoimgview.setFitWidth(300);
    Label titleLabel = new Label("Digital Health Records App", logoimgview);
    titleLabel.setFont(new Font(30.0));
    titleLabel.setContentDisplay(ContentDisplay.TOP);

    ImageView docImg = new ImageView("file:images/doctors-welcome.png");
    docImg.setFitHeight(180);
    docImg.setPreserveRatio(true);
    Button b1 = new Button();
    b1.setPrefSize(180, 180);
    b1.setPadding(new Insets(0));
    b1.setGraphic(docImg);

    ImageView patImg = new ImageView("file:images/patients-welcome.png");
    patImg.setFitHeight(180);
    patImg.setPreserveRatio(true);
    Button b2 = new Button();
    b2.setPrefSize(180, 180);
    b2.setGraphic(patImg);
    b2.setPadding(new Insets(0));

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(150);
    btns.maxHeight(180);

    btns.getChildren().addAll(b1, b2);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        DoctorStartScreen ds = new DoctorStartScreen();
        NavigationService.getInstance().pushScreen(ds.display());
      }
    });

    b2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        PatientStartScreen ps = new PatientStartScreen();
        NavigationService.getInstance().pushScreen(ps.display());
      }
    });

    this.root.getChildren().addAll(titleLabel, btns);
    this.root.setAlignment(Pos.CENTER);

    this.root.setPadding(new Insets(50, 25, 25, 50));
    this.root.setSpacing(100);
    return this.root;
  }
}
