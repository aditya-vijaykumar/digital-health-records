package screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import screens.components.PatientsMenuBar;
import services.JDBCService;
import services.NavigationService;

public class PSearchMedicines {
  private TableView<Medicines> table = new TableView();
  private ObservableList<Medicines> data = FXCollections.observableArrayList();

  public VBox display() {
    VBox root = new VBox();
    VBox parentRoot = new VBox();
    MenuBar menuBar = PatientsMenuBar.getMenuBar();
    tableInit();

    Text title = new Text("Search The Medicines Database");
    title.setFont(new Font(30.0));

    VBox subroot1 = new VBox();
    Text t1 = new Text("Enter the condition for which you are looking for medicines");
    TextField medForCondtn = new TextField();
    subroot1.getChildren().addAll(t1, medForCondtn);
    subroot1.setSpacing(10);
    subroot1.setMaxWidth(360);
    subroot1.setAlignment(Pos.TOP_LEFT);

    Button b1 = new Button("Search");
    Button b2 = new Button("Go Back");

    HBox btns = new HBox();
    btns.setAlignment(Pos.CENTER);
    btns.setSpacing(30);
    btns.maxHeight(180);
    btns.getChildren().addAll(b1, b2);

    Separator separator = new Separator();
    separator.setPrefWidth(300);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        String medForCondtnStr = "%" + medForCondtn.getText().trim() + "%";
        String SELECT_QUERY = "SELECT * FROM MEDICINES WHERE FOR_CONDITION LIKE ?";
        try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
            .prepareStatement(SELECT_QUERY);) {
          preparedStatement.setString(1, medForCondtnStr);
          ResultSet rs = preparedStatement.executeQuery();
          data.clear();
          while (rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4));
            data.add(new Medicines(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
          }
          table.setItems(data);
        } catch (SQLException e) {
          System.out.println("Exception caught" + e.toString());
        }
      }
    });

    b2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        NavigationService.getInstance().popScreen();
      }
    });
    root.setAlignment(Pos.TOP_CENTER);
    root.setPadding(new Insets(50, 25, 25, 50));
    root.setSpacing(30);
    root.getChildren().addAll(title, subroot1, btns, separator, table);
    parentRoot.getChildren().addAll(menuBar, root);
    return parentRoot;
  }

  private void tableInit() {
    TableColumn medicineIDs = new TableColumn("Medicine ID");
    medicineIDs.setMinWidth(50);
    medicineIDs.setCellValueFactory(new PropertyValueFactory<Medicines, Integer>("medID"));
    // 2nd
    TableColumn medName = new TableColumn("Medicine Name");
    medName.setMinWidth(100);
    medName.setCellValueFactory(new PropertyValueFactory<Medicines, String>("name"));
    // 3rd
    TableColumn medCost = new TableColumn("Cost");
    medCost.setMinWidth(70);
    medCost.setCellValueFactory(new PropertyValueFactory<Medicines, Integer>("cost"));

    // 4th
    TableColumn medFor = new TableColumn("Medication for the Condition");
    medFor.setMinWidth(100);
    medFor.setCellValueFactory(new PropertyValueFactory<Medicines, String>("forCondition"));

    table.getColumns().addAll(medicineIDs, medName, medCost, medFor);
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table.maxHeight(250);

    String SELECT_QUERY = "SELECT * FROM MEDICINES";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(SELECT_QUERY);) {
      ResultSet rs = preparedStatement.executeQuery();
      data.clear();
      while (rs.next()) {
        System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4));
        data.add(new Medicines(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
      }
      table.setItems(data);
    } catch (SQLException e) {
      System.out.println("Exception caught" + e.toString());
    }
  }

  public static class Medicines {

    private final SimpleIntegerProperty medID;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty cost;
    private final SimpleStringProperty forCondition;

    Medicines(int medID, String name, int cost, String forCondition) {
      this.medID = new SimpleIntegerProperty(medID);
      this.name = new SimpleStringProperty(name);
      this.cost = new SimpleIntegerProperty(cost);
      this.forCondition = new SimpleStringProperty(forCondition);
    }

    public int getMedID() {
      return medID.get();
    }

    public String getName() {
      return name.get();
    }

    public int getCost() {
      return cost.get();
    }

    public String getForCondition() {
      return forCondition.get();
    }
  }

  public static void showAlertMessage(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.show();
  }
}
