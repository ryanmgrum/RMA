package RMA;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** LoginController contains the logic and field references to the GUI controls
 *  on the Login form.
 */
public class LoginController implements Initializable {
    /** txtUsername references the TextField where the user enters their username to
     *  log into the RMA database.
     */
    @FXML
    private TextField txtUsername;
    /** txtPassword references the TextField where the user enters their password to
     *  log into the RMA database.
      */
    @FXML
    private TextField txtPassword;
    /** chobInstances contains the list of SQL Server instances found on the network.
     */
    @FXML
    private ChoiceBox<String> chobInstances;
    /** btnConnect is the button the user presses to connect to the selected instance.
     */
    @FXML
    private Button btnConnect;
    /** model is the data model for the Login form.
     */
    private LoginModel model;

    /** Default empty constructor, used when {@link javafx.fxml.FXMLLoader} loads the fxml file.
     */
    public LoginController() {}

    /** initialize ties together the GUI's elements with the LoginModel model and creates any
     *  necessary {@link ChangeListener}s on the controls.
     *  (Requirement 1.1.1, 1.2.1, 1.4.1)
     * @param url The {@link URL} location of the fxml file that describes the Login form's layout.
     * @param rb {@link ResourceBundle} that contains the data for the user's locale.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new LoginModel();
        txtUsername.textProperty().bindBidirectional(model.userProperty());
        txtUsername.textProperty().addListener(
            new ChangeListener<String>() {
                /** changed listens for changes to the Username {@link TextField} to decide whether the Connect {@link Button}
                 *  should be enabled.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link String} previous value.
                 * @param newValue The {@link String} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if (chobInstances.getSelectionModel().selectedItemProperty().getValue() != null)
                        if (!txtPassword.textProperty().getValue().isEmpty() && !chobInstances.getSelectionModel().selectedItemProperty().getValue().isEmpty())
                            btnConnect.setDisable(newValue.isEmpty() || newValue.isBlank());
                        else
                            btnConnect.setDisable(true);
                }
            }
        );
        txtPassword.textProperty().bindBidirectional(model.passProperty());
        txtPassword.textProperty().addListener(
            new ChangeListener<String>() {
                /** changed listens for changes to the Password {@link TextField} to decide whether the Connect {@link Button}
                 *  should be enabled.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link String} previous value.
                 * @param newValue The {@link String} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if (chobInstances.getSelectionModel().selectedItemProperty().getValue() != null)
                        if (!txtUsername.textProperty().getValue().isEmpty() && !chobInstances.getSelectionModel().selectedItemProperty().getValue().isEmpty())
                            btnConnect.setDisable(newValue.isEmpty() || newValue.isBlank());
                        else
                            btnConnect.setDisable(true);
                }
            }
        );
        chobInstances.setItems(model.instancesProperty());
        chobInstances.valueProperty().bindBidirectional(model.selectedInstanceProperty());
        chobInstances.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                /** changed listens for changes to the Instances {@link ChoiceBox} in order to decide whether to enable
                 *  the Connect {@link Button}.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link String} previous value.
                 * @param newValue The {@link String} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    btnConnect.setDisable(newValue.isEmpty() || newValue.isBlank());
                }
            }
        );
    }

    /** chobInstancesOnAction contains the logic to process the user clicking on the chobInstances {@link ChoiceBox}.
     *  (Requirement 1.4.2)
     * @param event The {@link Event} information pertaining to the user's action.
     */
    @FXML
    private void chobInstancesOnAction(Event event) {
        if (txtUsername.getText() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please fill in your username before selecting an instance!");
            a.show();
            txtUsername.requestFocus();
            event.consume();
        } else if (txtUsername.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please fill in your username before selecting an instance!");
            a.show();
            txtUsername.requestFocus();
            event.consume();
        } else if (txtPassword.getText() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please fill in your password before selecting an instance!");
            a.show();
            txtPassword.requestFocus();
            event.consume();
        } else if (txtPassword.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please fill in your password before selecting an instance!");
            a.show();
            txtPassword.requestFocus();
            event.consume();
        }
    }

    /** btnConnectOnAction contains the logic to process the user clicking on the
     *  Connect {@link Button}. In particular, it first checks that the user has filled
     *  out every field, and then attempts to connect to the RMA database stored in the
     *  user-selected SQL Server instance. If successful, it initializes an instance of
     *  DBService, and then launches the main RMAListView window. If it is unable to
     *  connect or find an RMA database in the given instance, a pop-up is shown.
     * @param event The {@link ActionEvent} information pertaining to the user's action.
     */
    @FXML
    private void btnConnectOnAction(ActionEvent event) {
        btnConnect.setDisable(true);
        if (!model.getUser().isEmpty() &&
            !model.getUser().isBlank() &&
            !model.getPass().isEmpty() &&
            !model.getPass().isBlank() &&
            !model.getSelectedInstance().isEmpty() &&
            !model.getSelectedInstance().isBlank()
        )
            try {
                // Initialize DBService.
                DBService.getInstance(model.getUser(), model.getPass(), model.getSelectedInstance());

                // Load the RMAListView screen.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("RMAListView_V4.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("RMA List View");
                stage.setScene(scene);
                stage.getScene().getWindow().setOnCloseRequest(
                        new EventHandler<WindowEvent>() {
                            /** handle listens for the WINDOW_CLOSE_REQUEST in order to ask the user whether they
                             *  want to close before continuing.
                             * @param windowEvent The {@link WindowEvent} that was passed to this method.
                             */
                            @Override
                            public void handle(WindowEvent windowEvent) {
                                if (windowEvent.getEventType().equals(WindowEvent.WINDOW_CLOSE_REQUEST)) {
                                    // Ask the user if they want to close.
                                    ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button text from OK to Confirm
                                    Alert a = new Alert(
                                            Alert.AlertType.CONFIRMATION,
                                            "Are you sure you want to exit?",
                                            ButtonType.NO,
                                            confirm
                                    );
                                    a.setTitle("RMA List View Close Confirmation");
                                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                    Optional<ButtonType> result = a.showAndWait();

                                    if (result.isPresent() && result.get() != confirm)
                                        windowEvent.consume();
                                }
                            }
                        }
                );

                stage.show();

                // Close this window.
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (SQLException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Error while connecting to SQL Server database!" + System.lineSeparator() + e.getLocalizedMessage());
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.show();
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Error while initializing form!" + System.lineSeparator() + e);
                e.printStackTrace();
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.show();
            }
        // Re-enable the button.
        btnConnect.setDisable(false);
    }
}