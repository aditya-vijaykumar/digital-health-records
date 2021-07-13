import javafx.application.*;
import javafx.stage.Stage;
import screens.WelcomeScreen;
import services.NavigationService;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage myStage) {
        WelcomeScreen ws = new WelcomeScreen();
        NavigationService navService = NavigationService.getInstance();
        navService.switchToScreen(ws.display());
        navService.getStage().show();
    }
}
