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
import services.NavigationService;

public class AboutTheApp {

  public VBox display() {
    VBox root = new VBox();
    VBox parentRoot = new VBox();
    ScrollPane sideBarScroller = new ScrollPane();
    sideBarScroller = new ScrollPane(root);
    sideBarScroller.setFitToWidth(true);

    Text title = new Text("About the App");
    title.setFont(new Font(30.0));
    Text subtitle1 = new Text("This app has been built as a part of Application Development with JAVA Mini Project.");
    subtitle1.setFont(new Font(18.0));
    Text subtitle2 = new Text("Project Members: ");
    subtitle2.setFont(new Font(18.0));

    Text subtitle3 = new Text("Aditya Vijaykumar");
    subtitle3.setFont(new Font(15.0));

    Text subtitle4 = new Text("Womika Khan");
    subtitle4.setFont(new Font(15.0));

    Text subtitle5 = new Text("Uzair Ahmed");
    subtitle5.setFont(new Font(15.0));

    VBox subroot1 = new VBox();
    subroot1.getChildren().addAll(subtitle1, subtitle2, subtitle3, subtitle4, subtitle5);
    subroot1.setSpacing(40);
    subroot1.setMaxWidth(360);
    subroot1.setAlignment(Pos.TOP_CENTER);

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
    root.getChildren().addAll(title, subroot1, btns);
    parentRoot.getChildren().addAll(sideBarScroller);
    return parentRoot;
  }
}
