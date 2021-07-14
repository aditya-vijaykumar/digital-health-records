package screens.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import providers.PatientProvider;

public class PatientsMenuBar {
  public static MenuBar getMenuBar() {
    // Creating file menu
    Menu account = new Menu("Account");
    MenuItem item1 = new MenuItem("View Profile");
    MenuItem item2 = new MenuItem("Edit Profile");
    MenuItem item3 = new MenuItem("Logout");

    item3.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        if (!PatientProvider.getInstance().patientLogout()) {
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
    about.getItems().add(item6);

    MenuBar menuBar = new MenuBar();
    menuBar.setTranslateX(0);
    menuBar.setTranslateY(0);
    menuBar.setMaxWidth(Double.MAX_VALUE);
    menuBar.getMenus().addAll(account, about);
    return menuBar;
  }
}
