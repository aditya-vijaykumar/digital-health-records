package screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.HealthRecord;
import screens.components.DoctorsMenuBar;
import services.NavigationService;

public class DViewHealthRecord {
  private HealthRecord record;
  private String patientName;
  private String patientId;

  public DViewHealthRecord(HealthRecord record, String patientName, int p_id) {
    this.record = record;
    this.patientName = patientName;
    this.patientId = String.valueOf(p_id);
  }

  public VBox display() {
    VBox root = new VBox();
    VBox parentRoot = new VBox();
    ScrollPane sideBarScroller = new ScrollPane();
    sideBarScroller = new ScrollPane(root);
    sideBarScroller.setFitToWidth(true);
    MenuBar menuBar = DoctorsMenuBar.getMenuBar();

    Text title = new Text("Viewing Health Record");
    title.setFont(new Font(30.0));
    Text subtitle1 = new Text("Patient Name : " + patientName);
    subtitle1.setFont(new Font(18.0));
    Text subtitle2 = new Text("Patient ID : " + patientId);
    subtitle2.setFont(new Font(18.0));

    VBox subroot1 = new VBox();
    subroot1.getChildren().addAll(subtitle1, subtitle2);
    subroot1.setSpacing(10);
    subroot1.setMaxWidth(360);
    subroot1.setAlignment(Pos.TOP_LEFT);

    Text t21 = new Text("Date of Health Record Creation");
    t21.setStyle("-fx-font-weight: bold");
    Text t22 = new Text(record.getCreateDate());
    VBox subroot2 = new VBox();
    subroot2.getChildren().addAll(t21, t22);
    subroot2.setSpacing(10);
    subroot2.setMaxWidth(360);
    subroot2.setAlignment(Pos.TOP_LEFT);

    Text t31 = new Text("Symptoms");
    t31.setStyle("-fx-font-weight: bold");
    Text t32 = new Text(record.getSymptoms());
    VBox subroot3 = new VBox();
    subroot3.getChildren().addAll(t31, t32);
    subroot3.setSpacing(10);
    subroot3.setMaxWidth(360);
    subroot3.setAlignment(Pos.TOP_LEFT);

    Text t41 = new Text("Medical Condition");
    t41.setStyle("-fx-font-weight: bold");
    Text t42 = new Text(record.getMedCondtn());
    VBox subroot4 = new VBox();
    subroot4.getChildren().addAll(t41, t42);
    subroot4.setSpacing(10);
    subroot4.setMaxWidth(360);
    subroot4.setAlignment(Pos.TOP_LEFT);

    Text t51 = new Text("Diagnosis");
    t51.setStyle("-fx-font-weight: bold");
    Text t52 = new Text(record.getDiagnosis());
    VBox subroot5 = new VBox();
    subroot5.getChildren().addAll(t51, t52);
    subroot5.setSpacing(10);
    subroot5.setMaxWidth(360);
    subroot5.setAlignment(Pos.TOP_LEFT);

    Text t61 = new Text("Medication/Treatment");
    t61.setStyle("-fx-font-weight: bold");
    Text t62 = new Text(record.getMedication());
    VBox subroot6 = new VBox();
    subroot6.getChildren().addAll(t61, t62);
    subroot6.setSpacing(10);
    subroot6.setMaxWidth(360);
    subroot6.setAlignment(Pos.TOP_LEFT);

    Button b1 = new Button("Go Back");

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(5);
    btns.maxHeight(50);
    btns.getChildren().addAll(b1);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        NavigationService.getInstance().popScreen();
      }
    });
    root.setAlignment(Pos.TOP_CENTER);
    root.setPadding(new Insets(50, 25, 25, 90));
    root.setSpacing(30);
    root.getChildren().addAll(title, subroot1, subroot2, subroot3, subroot4, subroot5, subroot6, btns);
    parentRoot.getChildren().addAll(menuBar, sideBarScroller);
    return parentRoot;
  }
}
