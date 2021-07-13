package screens;

import java.sql.*;
import javafx.application.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import services.NavigationService;

public class ScreenOne {
  TableView<Authors> table = new TableView();
  VBox root = new VBox();

  public VBox display() {
    TableColumn authorname = new TableColumn("Author Name");
    authorname.setMinWidth(100);
    authorname.setCellValueFactory(new PropertyValueFactory<Authors, String>("authorName"));
    TableColumn bookname = new TableColumn("Book Name");
    bookname.setMinWidth(100);
    bookname.setCellValueFactory(new PropertyValueFactory<Authors, String>("bookName"));
    ObservableList<Authors> data = FXCollections.observableArrayList();
    table.getColumns().addAll(authorname, bookname);
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table.maxHeight(250);

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

    VBox vbox = new VBox();
    vbox.setSpacing(5);
    vbox.maxHeight(250);
    vbox.setPadding(new Insets(10, 10, 20, 10));
    vbox.getChildren().addAll(table);

    b1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        NavigationService navService = NavigationService.getInstance();
        navService.goBack();
      }
    });

    b2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      public void handle(ActionEvent ae) {
        Connection con = null;
        Statement stmt = null;
        try {
          con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/6jf8B9VQl4", "6jf8B9VQl4", "T1a7u4LCMY");
          stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT * FROM Authors");

          data.clear();
          while (rs.next()) {
            System.out.println(rs.getString(1) + ":" + rs.getString(2));
            data.add(new Authors(rs.getString(1), rs.getString(2)));
          }
          table.setItems(data);
        } catch (Exception e) {
          System.out.println("Exception caught" + e.toString());
        }
      }
    });

    this.root.getChildren().addAll(subroot1, subroot2, separator, btns, vbox);
    this.root.setSpacing(25);
    this.root.setAlignment(Pos.TOP_CENTER);
    this.root.setPadding(new Insets(20, 0, 0, 10));

    return this.root;
  }

  public static class Authors {

    private final SimpleStringProperty authorName;
    private final SimpleStringProperty bookName;

    Authors(String aName, String bName) {
      this.authorName = new SimpleStringProperty(aName);
      this.bookName = new SimpleStringProperty(bName);
    }

    public String getAuthorName() {
      return authorName.get();
    }

    public String getBookName() {
      return bookName.get();
    }
  }
}
