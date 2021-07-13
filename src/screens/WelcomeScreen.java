package screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.NavigationService;

public class WelcomeScreen {
  VBox root = new VBox();

  public VBox display() {
    ImageView logoimgview = new ImageView("file:images/icon.png");
    Label titleLabel = new Label("Digital Health Records App", logoimgview);
    titleLabel.setContentDisplay(ContentDisplay.TOP);
    Button b1 = new Button("Screen One");
    Button b2 = new Button("Screen Two");

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(5);
    btns.maxHeight(50);
    btns.getChildren().addAll(b1, b2);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        NavigationService navService = NavigationService.getInstance();
        ScreenOne s1 = new ScreenOne();
        navService.switchToScreen(s1.display());
      }
    });

    b2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        NavigationService navService = NavigationService.getInstance();
        ScreenTwo s2 = new ScreenTwo();
        navService.switchToScreen(s2.display());
      }
    });

    this.root.getChildren().addAll(titleLabel, btns);
    this.root.setAlignment(Pos.CENTER);
    return this.root;
  }
}
