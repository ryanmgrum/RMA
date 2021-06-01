package RMA;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** The RMA class starts the JavaFX runtime and launches the Login screen.
 */
public class RMA extends Application {

    /** start opens the Login screen.
     * @param stage The initial {@link Stage} passed from the JavaFX runtime.
     * @throws IOException If there is an issue finding or loading the Login FXML file or Controller.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Login_V2.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("RMA Login");
        stage.show();
    }

    /** main starts the JavaFX runtime.
     * @param args The {@link String} arguments passed in from the command line.
     */
    public static void main(String[] args) {launch(args);}
}