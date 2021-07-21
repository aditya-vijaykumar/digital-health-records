package screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import providers.PatientProvider;
import screens.components.PatientsMenuBar;
import services.NavigationService;

public class PatientDashboard {
  VBox root;
  VBox parentRoot;

  public VBox display() {
    this.root = new VBox();
    this.parentRoot = new VBox();
    MenuBar menuBar = PatientsMenuBar.getMenuBar();

    Text title = new Text("Patient's Dashboard - Hello " + PatientProvider.getInstance().getPatient().getName());
    title.setFont(new Font(30.0));

    ImageView docImg = new ImageView("file:images/search.png");
    docImg.setFitHeight(180);
    docImg.setPreserveRatio(true);
    Button b1 = new Button();
    b1.setPrefSize(180, 180);
    b1.setPadding(new Insets(0));
    b1.setGraphic(docImg);

    ImageView patImg = new ImageView("file:images/insert.png");
    patImg.setFitHeight(180);
    patImg.setPreserveRatio(true);
    Button b2 = new Button();
    b2.setPrefSize(180, 180);
    b2.setGraphic(patImg);
    b2.setPadding(new Insets(0));

    ImageView medImg = new ImageView("file:images/meds.png");
    medImg.setFitHeight(180);
    medImg.setPreserveRatio(true);
    Button b3 = new Button();
    b3.setPrefSize(180, 180);
    b3.setGraphic(medImg);
    b3.setPadding(new Insets(0));

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(150);
    btns.maxHeight(180);

    btns.getChildren().addAll(b1, b2, b3);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        PSearchDoctor psd = new PSearchDoctor();
        NavigationService.getInstance().pushScreen(psd.display());
      }
    });

    b2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        PFindRecords pfr = new PFindRecords();
        NavigationService.getInstance().pushScreen(pfr.display());
      }
    });

    b3.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        PSearchMedicines psm = new PSearchMedicines();
        NavigationService.getInstance().pushScreen(psm.display());
      }
    });

    this.root.setAlignment(Pos.TOP_CENTER);
    this.root.setPadding(new Insets(50, 25, 25, 50));
    this.root.setSpacing(30);
    this.root.getChildren().addAll(title, btns);
    this.parentRoot.getChildren().addAll(menuBar, this.root);
    return this.parentRoot;
  }
}
