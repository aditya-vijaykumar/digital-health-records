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
import providers.DoctorProvider;
import screens.components.DoctorsMenuBar;
import services.NavigationService;

public class DoctorDashboard {
  VBox root;

  public VBox display() {
    this.root = new VBox();

    MenuBar menuBar = DoctorsMenuBar.getMenuBar();

    Text title = new Text("Doctor's Dashboard - Hello Dr." + DoctorProvider.getInstance().getDoctor().getName());
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

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(150);
    btns.maxHeight(180);

    btns.getChildren().addAll(b1, b2);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        DSearchPatient dsp = new DSearchPatient();
        NavigationService.getInstance().pushScreen(dsp.display());
      }
    });

    b2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        DAddNewPatient dap = new DAddNewPatient();
        NavigationService.getInstance().pushScreen(dap.display());
      }
    });

    this.root.setAlignment(Pos.TOP_CENTER);
    this.root.setPadding(new Insets(0, 25, 25, 50));
    this.root.setSpacing(30);
    this.root.getChildren().addAll(menuBar, title, btns);
    return this.root;
  }
}
