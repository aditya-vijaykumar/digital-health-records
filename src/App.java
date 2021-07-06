import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

import javafx.application.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
    TableView<Authors> table = new TableView();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage myStage) {
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

        myStage.setTitle("JDBC");
        VBox root = new VBox();
        Scene newscene = new Scene(root, 400, 600);
        String css = this.getClass().getResource("/customstyle.css").toExternalForm();
        newscene.getStylesheets().addAll(css);
        myStage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));

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
        // Region spacer = new Region();
        separator.setPrefWidth(300);
        Button b1 = new Button("Insert Data");
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
                String str1 = tf1.getText();
                String str2 = tf2.getText();
                Connection con = null;
                Statement stmt = null;
                try {
                    con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/6jf8B9VQl4", "root", "");
                    stmt = con.createStatement();
                    System.out.println("Inserting the values into table");
                    String upqry = "INSERT INTO Authors VALUES('" + str1 + "', '" + str2 + "');";
                    int row = stmt.executeUpdate(upqry);
                    System.out.println("Inserting the values into table");

                } catch (Exception e) {
                    System.out.println("Exception caught" + e.toString());
                }
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

        root.getChildren().addAll(subroot1, subroot2, separator, btns, vbox);
        root.setSpacing(25);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20, 0, 0, 10));
        myStage.setScene(newscene);
        myStage.show();

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

        public void setAuthorName(String aName) {
            authorName.set(aName);
        }

        public String getBookName() {
            return bookName.get();
        }

        public void setBookName(String bName) {
            bookName.set(bName);
        }

    }
}
