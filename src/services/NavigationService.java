package services;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NavigationService {
  private Stage primaryStage = new Stage();
  private Parent prevScreen;
  private final static NavigationService INSTANCE = new NavigationService();

  private NavigationService() {
    primaryStage.setTitle("Digital Health Records");
    //Temporary
    VBox root = new VBox();
    this.prevScreen = root;
    //Simple screen
    Scene newscene = new Scene(root, 1280, 720);
    String css = this.getClass().getResource("customstyle.css").toExternalForm();
    newscene.getStylesheets().addAll(css);
    primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
    primaryStage.setScene(newscene);
  }

  public static NavigationService getInstance() {
    return INSTANCE;
  }

  public Stage getStage() {
    return this.primaryStage;
  }

  public void switchToScreen(Parent node) {
    this.prevScreen = this.primaryStage.getScene().getRoot();
    try {
      this.primaryStage.getScene().setRoot(node);

    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public void goBack() {
    try {
      this.primaryStage.getScene().setRoot(this.prevScreen);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }
}
