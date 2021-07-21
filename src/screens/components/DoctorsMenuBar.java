package screens.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import providers.DoctorProvider;
import screens.AboutTheApp;
import screens.DEditProfile;
import screens.DViewProfile;
import services.NavigationService;

public class DoctorsMenuBar {
  public static MenuBar getMenuBar() {
    // Creating file menu
    Menu account = new Menu("Account");
    MenuItem item1 = new MenuItem("View Profile");
    MenuItem item2 = new MenuItem("Edit Profile");
    MenuItem item3 = new MenuItem("Logout");

    item1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        DViewProfile dvp = new DViewProfile();
        NavigationService.getInstance().pushScreen(dvp.display());
      }
    });
    item2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        DEditProfile dep = new DEditProfile();
        NavigationService.getInstance().pushScreen(dep.display());
      }
    });
    item3.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        if (!DoctorProvider.getInstance().doctorLogout()) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText(null);
          alert.setContentText("Error while logging out");
          alert.show();
        }
      }
    });

    account.getItems().addAll(item1, item2, item3);

    Menu about = new Menu("About");
    MenuItem item6 = new MenuItem("About the app");
    item6.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        AboutTheApp ata = new AboutTheApp();
        NavigationService.getInstance().pushScreen(ata.display());
      }
    });
    about.getItems().add(item6);

    MenuBar menuBar = new MenuBar();
    menuBar.setTranslateX(0);
    menuBar.setTranslateY(0);
    menuBar.getMenus().addAll(account, about);
    menuBar.prefWidthProperty().bind(NavigationService.getInstance().getStage().widthProperty());
    return menuBar;
  }
}
