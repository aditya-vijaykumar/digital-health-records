package screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import screens.components.DoctorsMenuBar;
import services.NavigationService;

public class DSearchPatient {
  public VBox display() {
    VBox root = new VBox();
    MenuBar menuBar = DoctorsMenuBar.getMenuBar();

    Text title = new Text("Search Patient");
    title.setFont(new Font(30.0));

    Button b1 = new Button("Go Back");

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(150);
    btns.maxHeight(180);

    btns.getChildren().addAll(b1);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        NavigationService.getInstance().popScreen();
      }
    });
    root.setAlignment(Pos.TOP_CENTER);
    root.setPadding(new Insets(0, 25, 25, 50));
    root.setSpacing(30);
    root.getChildren().addAll(menuBar, title, btns);
    return root;
  }
}
