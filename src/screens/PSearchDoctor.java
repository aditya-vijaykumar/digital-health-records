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

public class PSearchDoctor {
  private TableView<DoctorsSP> table = new TableView();
  private ObservableList<DoctorsSP> data = FXCollections.observableArrayList();

  public VBox display() {
    VBox root = new VBox();
    VBox parentRoot = new VBox();
    MenuBar menuBar = PatientsMenuBar.getMenuBar();
    tableInit();

    Text title = new Text("Search The Doctor Database");
    title.setFont(new Font(30.0));

    VBox subroot1 = new VBox();
    Text t1 = new Text("Enter the specialization field to look for doctors");
    TextField speclznField = new TextField();
    subroot1.getChildren().addAll(t1, speclznField);
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
        String speclznFieldStr = "%" + speclznField.getText().trim() + "%";
        String SELECT_QUERY = "SELECT * FROM DOCTORS WHERE SPECIALIZATION LIKE ?";
        try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
            .prepareStatement(SELECT_QUERY);) {
          preparedStatement.setString(1, speclznFieldStr);
          ResultSet rs = preparedStatement.executeQuery();
          data.clear();
          while (rs.next()) {
            data.add(new DoctorsSP("Dr. " + rs.getString(2) + " " + rs.getString(3), rs.getString(5), rs.getInt(6),
                rs.getString(7), rs.getString(8), rs.getString(9)));
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
    TableColumn doctorName = new TableColumn("Doctor's Name");
    doctorName.setMinWidth(50);
    doctorName.setCellValueFactory(new PropertyValueFactory<DoctorsSP, String>("name"));
    // 2nd
    TableColumn qualiColumn = new TableColumn("Qualification");
    qualiColumn.setMinWidth(80);
    qualiColumn.setCellValueFactory(new PropertyValueFactory<DoctorsSP, String>("quali"));
    // 3rd
    TableColumn docAge = new TableColumn("Age");
    docAge.setMinWidth(25);
    docAge.setCellValueFactory(new PropertyValueFactory<DoctorsSP, Integer>("age"));
    // 4th
    TableColumn docGen = new TableColumn("Gender");
    docGen.setMinWidth(10);
    docGen.setCellValueFactory(new PropertyValueFactory<DoctorsSP, String>("gender"));
    // 5th
    TableColumn docSpec = new TableColumn("Specializations");
    docSpec.setMinWidth(100);
    docSpec.setCellValueFactory(new PropertyValueFactory<DoctorsSP, String>("specialization"));
    // 6th
    TableColumn docAbt = new TableColumn("About");
    docAbt.setMinWidth(100);
    docAbt.setCellValueFactory(new PropertyValueFactory<DoctorsSP, String>("about"));

    table.getColumns().addAll(doctorName, docAge, docGen, qualiColumn, docSpec, docAbt);
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table.maxHeight(250);

    String SELECT_QUERY = "SELECT * FROM DOCTORS";
    try (PreparedStatement preparedStatement = JDBCService.getInstance().getConnection()
        .prepareStatement(SELECT_QUERY);) {
      ResultSet rs = preparedStatement.executeQuery();
      data.clear();
      while (rs.next()) {
        data.add(new DoctorsSP("Dr. " + rs.getString(2) + " " + rs.getString(3), rs.getString(5), rs.getInt(6),
            rs.getString(7), rs.getString(8), rs.getString(9)));
      }
      table.setItems(data);
    } catch (SQLException e) {
      System.out.println("Exception caught" + e.toString());
    }
  }

  public static class DoctorsSP {

    private final SimpleStringProperty name;
    private final SimpleStringProperty quali;
    private final SimpleIntegerProperty age;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty specialization;
    private final SimpleStringProperty about;

    DoctorsSP(String name, String quali, int age, String gender, String specialization, String about) {
      this.name = new SimpleStringProperty(name);
      this.quali = new SimpleStringProperty(quali);
      this.age = new SimpleIntegerProperty(age);
      this.gender = new SimpleStringProperty(gender);
      this.specialization = new SimpleStringProperty(specialization);
      this.about = new SimpleStringProperty(about);
    }

    public String getName() {
      return name.get();
    }

    public String getQuali() {
      return quali.get();
    }

    public int getAge() {
      return age.get();
    }

    public String getGender() {
      return gender.get();
    }

    public String getSpecialization() {
      return specialization.get();
    }

    public String getAbout() {
      return about.get();
    }

  }
}
