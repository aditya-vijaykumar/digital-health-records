package screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import services.NavigationService;

public class ScreenTwo {
  VBox root = new VBox();

  public VBox display() {
    Text t1 = new Text("Name of the Author");
    TextField tf1 = new TextField();
    VBox subroot1 = new VBox();
    subroot1.getChildren().addAll(t1, tf1);
    subroot1.setSpacing(10);
    subroot1.setMaxWidth(300);

    Text t2 = new Text("Book published");
    TextField tf2 = new TextField();
    VBox subroot2 = new VBox();
    subroot2.getChildren().addAll(t2, tf2);
    subroot2.setSpacing(10);
    subroot2.setMaxWidth(300);

    Separator separator = new Separator();
    separator.setPrefWidth(300);
    Button b1 = new Button("Go Back");
    Button b2 = new Button("Show Data");

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(5);
    btns.maxHeight(50);
    btns.getChildren().addAll(b1, b2);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        NavigationService navService = NavigationService.getInstance();
        navService.goBack();
      }
    });

    VBox vbox = new VBox();
    vbox.setSpacing(5);
    vbox.maxHeight(250);
    vbox.setPadding(new Insets(10, 10, 20, 10));

    this.root.getChildren().addAll(subroot1, subroot2, separator, btns, vbox);
    this.root.setSpacing(25);
    this.root.setAlignment(Pos.TOP_CENTER);
    this.root.setPadding(new Insets(20, 0, 0, 10));
    return this.root;
  }
}
