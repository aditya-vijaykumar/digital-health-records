package screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Patient;
import providers.PatientProvider;
import services.NavigationService;

public class PViewProfile {

  public VBox display() {
    VBox root = new VBox();
    VBox parentRoot = new VBox();
    ScrollPane sideBarScroller = new ScrollPane();
    sideBarScroller = new ScrollPane(root);
    sideBarScroller.setFitToWidth(true);

    Patient profile = PatientProvider.getInstance().getPatient();

    Text title = new Text("Your Profile");
    title.setFont(new Font(30.0));
    Text subtitle1 = new Text("Name : " + profile.getName());
    subtitle1.setFont(new Font(18.0));
    Text subtitle2 = new Text("Patient ID Number : " + profile.getPatientID());
    subtitle2.setFont(new Font(18.0));

    Text subtitle3 = new Text("Email : " + profile.getEmail());
    subtitle3.setFont(new Font(18.0));

    Text subtitle4 = new Text("Gender : " + profile.getGender());
    subtitle4.setFont(new Font(18.0));

    Text subtitle5 = new Text("Age : " + profile.getAge());
    subtitle5.setFont(new Font(18.0));

    Text subtitle6 = new Text("Blood Group : " + profile.getBloodGrp());
    subtitle6.setFont(new Font(18.0));

    Text subtitle7 = new Text("Height : " + profile.getHeight() + " cms");
    subtitle7.setFont(new Font(18.0));

    Text subtitle8 = new Text("Weight : " + profile.getWeight() + " kgs");
    subtitle8.setFont(new Font(18.0));

    VBox subroot1 = new VBox();
    subroot1.getChildren().addAll(subtitle1, subtitle2, subtitle3, subtitle4, subtitle5, subtitle6, subtitle7,
        subtitle8);
    subroot1.setSpacing(25);
    subroot1.setMaxWidth(360);
    subroot1.setAlignment(Pos.TOP_LEFT);

    Text t21 = new Text("Allergies");
    t21.setFont(new Font(18.0));
    Text t22 = new Text(profile.getAllergies());
    t22.setStyle("-fx-font-weight: medium");
    VBox subroot2 = new VBox();
    subroot2.getChildren().addAll(t21, t22);
    subroot2.setSpacing(10);
    subroot2.setMaxWidth(360);
    subroot2.setAlignment(Pos.TOP_LEFT);
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
    root.getChildren().addAll(title, subroot1, subroot2, btns);
    parentRoot.getChildren().addAll(sideBarScroller);
    return parentRoot;
  }
}
