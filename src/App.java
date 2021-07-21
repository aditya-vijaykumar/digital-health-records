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

// - Patient registration is v simple

// - Doctor regsitration is access controlled, you have to enter an access
// key/credentials otherwise any random person can register themself as a doctor
// xD

// - Doctor basically searches for a patient by email id, if a record in
// patients table is found then he/she can create a health record which will be
// added to the patient's reference

// - Doctor can get a list of all the patients he has consulted along with the
//   date, and health record. We can use group by here to group by the patient id.

// - Both Patient & Doctor can search for medicines in the medicine table

// - Editing Profiles

// - Patient can obviously view all of these health records

// - Patients can search for doctors based on specialization.
