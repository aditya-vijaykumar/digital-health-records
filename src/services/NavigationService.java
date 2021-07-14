package services;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NavigationService {
  private Stage primaryStage = new Stage();
  private PrevScreenStack prevScreen = new PrevScreenStack();
  private final static NavigationService INSTANCE = new NavigationService();

  private NavigationService() {

    primaryStage.setTitle("Digital Health Records");
    // Temporary
    VBox root = new VBox();
    prevScreen.push(root);
    // Simple screen
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

  public void pushScreen(Parent node) {
    prevScreen.push(this.primaryStage.getScene().getRoot());
    try {
      this.primaryStage.getScene().setRoot(node);

    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public void clearAllAndPush(Parent node) {
    prevScreen.reset();
    prevScreen.push(this.primaryStage.getScene().getRoot());
    try {
      this.primaryStage.getScene().setRoot(node);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public void popScreen() {
    try {
      this.primaryStage.getScene().setRoot(prevScreen.pop());
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  private static class PrevScreenStack {
    private int maxSize;
    private int top;
    private Parent[] prevScreen;

    PrevScreenStack() {
      maxSize = 10;
      prevScreen = new Parent[maxSize];
      top = -1;
    }

    void push(Parent node) {
      prevScreen[++top] = node;
    }

    Parent pop() {
      return prevScreen[top--];
    }

    void reset() {
      top = -1;
    }
  }
}
