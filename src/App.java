import javafx.application.*;
import javafx.stage.Stage;
import screens.WelcomeScreen;
import services.JDBCService;
import services.NavigationService;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage myStage) {
        WelcomeScreen ws = new WelcomeScreen();
        NavigationService.getInstance().pushScreen(ws.display());
        NavigationService.getInstance().getStage().show();
    }

    @Override
    public void stop() throws Exception {
        JDBCService.getInstance().endConnection();
        super.stop();
    }
}
