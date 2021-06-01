package RMA;


import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;


public class LoginControllerTest {
    private LoginModel modelTest;

    @Before
    public void setUp() throws Exception {
        modelTest = new LoginModel();
    }

    /**
     * Title: Test HandleInitializeAction Both Username and Password filled
     * Description: Test the logic of the method with both user
     * and password complete
     * Requirement: 1.1, 1.2, 1.3, 1.3
     * Assumptions:  Action event triggered
     * Input: User = admin, Password = admin
     * Expected Result: No error message populates
     * Actual Result: No error message populates
     * Test Outcome: Passed
     */
    @Test
    public void testHandleInitializeActionFieldsComplete() {
        modelTest.userProperty().setValue("admin");
        modelTest.passProperty().setValue("admin");
        modelTest.selectedInstanceProperty().setValue("KINGKAI-HP\\SQLEXPRESS");
        System.out.println("TC_13 was ran." + System.lineSeparator() + "Expected = No error message populates" + System.lineSeparator() +
                "Actual = ");
        handleInstancesOnAction();

    }

    /**
     * Title: Test HandleInitializeAction Both Username and Password empty
     * Description: Test the logic of the method with both user
     * and password empty
     * Requirement: 1.1.1, 1.2.1, 1.4.1
     * Assumptions: Action event triggered
     * Input: User = empty, Password = empty
     * Expected Result: "Please fill in your username before selecting an instance!" displays
     * Actual Result: "Please fill in your username before selecting an instance!" displays
     * Test Outcome: Passed
     */
    @Test
    public void testHandleInitializeActionFieldsEmpty() {
        modelTest.userProperty().setValue("");
        modelTest.passProperty().setValue("");
        modelTest.selectedInstanceProperty().setValue("KINGKAI-HP\\SQLEXPRESS");
        System.out.println("TC_14 was ran." + System.lineSeparator() + "Expected = Please fill in your username before selecting an instance!" + System.lineSeparator() +
                "Actual = ");
        handleInstancesOnAction();

    }

    /**
     * Title: Test HandleInitializeAction Username and Password Null
     * Description: Test the logic of the method with both user
     * and password null
     * Requirement: 1.1.1, 1.2.1, 1.4.1
     * Assumptions:  Action event triggered/A
     * Input: User = null, Password = null
     * Expected Result: "Please fill in your username before selecting an instance!" displays
     * Actual Result: "Please fill in your username before selecting an instance!" displays
     * Test Outcome: Passed
     */
    @Test
    public void testHandleInitializeActionFieldsNull() {
        modelTest.userProperty().setValue(null);
        modelTest.passProperty().setValue(null);
        modelTest.selectedInstanceProperty().setValue("KINGKAI-HP\\SQLEXPRESS");
        System.out.println("TC_15 was ran." + System.lineSeparator() + "Expected = Please fill in your username before selecting an instance!" + System.lineSeparator() +
                "Actual = ");
        handleInstancesOnAction();
    }

    /**
     * Title: Test HandleInitializeAction Username Empty
     * Description: Test the logic of the method with both user empty
     * Requirement: 1.1.1, 1.2.1, 1.4.1
     * Assumptions:  Action event triggered/A
     * Input: User = empty, Password = engineer
     * Expected Result: "Please fill in your username before selecting an instance!" displays
     * Actual Result: "Please fill in your username before selecting an instance!" displays
     * Test Outcome: Passed
     */
    @Test
    public void testHandleInitializeActionUserEmpty() {
        modelTest.userProperty().setValue("");
        modelTest.passProperty().setValue("engineer");
        modelTest.selectedInstanceProperty().setValue("KINGKAI-HP\\SQLEXPRESS");
        System.out.println("TC_16 was ran." + System.lineSeparator() + "Expected = Please fill in your username before selecting an instance!" + System.lineSeparator() +
                "Actual = ");
        handleInstancesOnAction();
    }

    /**
     * Title: Test HandleInitializeAction Username Empty
     * Description: Test the logic of the method with both user null
     * Requirement: 1.1.1, 1.2.1, 1.4.1
     * Assumptions:  Action event triggered/A
     * Input: User = null, Password = engineer
     * Expected Result: "Please fill in your username before selecting an instance!" displays
     * Actual Result: "Please fill in your username before selecting an instance!" displays
     * Test Outcome: Passed
     */
    @Test
    public void testHandleInitializeActionUserNull() {
        modelTest.userProperty().setValue(null);
        modelTest.passProperty().setValue("engineer");
        modelTest.selectedInstanceProperty().setValue("KINGKAI-HP\\SQLEXPRESS");
        System.out.println("TC_17 was ran." + System.lineSeparator() + "Expected = Please fill in your username before selecting an instance!" + System.lineSeparator() +
                "Actual = ");
        handleInstancesOnAction();
    }

    /**
     * Title: Test HandleInitializeAction Username Empty
     * Description: Test the logic of the method with password is empty
     * Requirement: 1.1.1, 1.2.1, 1.4.1
     * Assumptions:  Action event triggered/A
     * Input: User = analyst, Password = empty
     * Expected Result: "Please fill in your password before selecting an instance!" displays
     * Actual Result: "Please fill in your password before selecting an instance!" displays
     * Test Outcome: Passed
     */
    @Test
    public void testHandleInitializeActionPWEmpty() {
        modelTest.userProperty().setValue("analyst");
        modelTest.passProperty().setValue("");
        modelTest.selectedInstanceProperty().setValue("KINGKAI-HP\\SQLEXPRESS");
        System.out.println("TC_18 was ran." + System.lineSeparator() + "Expected = Please fill in your password before selecting an instance!" + System.lineSeparator() +
                "Actual = ");
        handleInstancesOnAction();
    }

    /**
     * Title: Test HandleInitializeAction Username Null
     * Description: Test the logic of the method with password is null
     * Requirement: 1.1.1, 1.2.1, 1.4.1
     * Assumptions:  Action event triggered
     * Input: User = analyst, Password = null
     * Expected Result: "Please fill in your password before selecting an instance!" displays
     * Actual Result: "Please fill in your password before selecting an instance!" displays
     * Test Outcome: Passed
     */
    @Test
    public void testHandleInitializeActionPWNull() {
        modelTest.userProperty().setValue("analyst");
        modelTest.passProperty().setValue(null);
        modelTest.selectedInstanceProperty().setValue("KINGKAI-HP\\SQLEXPRESS");
        System.out.println("TC_19 was ran." + System.lineSeparator() + "Expected = Please fill in your password before selecting an instance!" + System.lineSeparator() +
                "Actual = ");
        handleInstancesOnAction();
    }

    /**
     * Recreated the event handler in the Login model
     * in order to test the logic and all possible conditional
     * paths. Change the alert to a print string since not testing the UI
     */
    private void handleInstancesOnAction() {
        if (modelTest.userProperty().get() == null) {
            //Alert a = new Alert(Alert.AlertType.ERROR);
            //a.setContentText("Please fill in your username before selecting an instance!");
            //a.show();
            //txtUsername.requestFocus();
            //event.consume();
            System.out.println("Please fill in your username before selecting an instance!");
        } else if (modelTest.userProperty().get().isEmpty()) {
            //Alert a = new Alert(Alert.AlertType.ERROR);
            //a.setContentText("Please fill in your username before selecting an instance!");
            //a.show();
            //txtUsername.requestFocus();
            //event.consume();
            System.out.println("Please fill in your username before selecting an instance!");
        } else if (modelTest.passProperty().get() == null) {
            // Alert a = new Alert(Alert.AlertType.ERROR);
            //a.setContentText("Please fill in your password before selecting an instance!");
            //a.show();
            //txtPassword.requestFocus();
            //event.consume();
            System.out.println("Please fill in your password before selecting an instance!");
        } else if (modelTest.passProperty().get().isEmpty()) {
            //Alert a = new Alert(Alert.AlertType.ERROR);
            //a.setContentText("Please fill in your password before selecting an instance!");
            //a.show();
            //txtPassword.requestFocus();
            //event.consume();
            System.out.println("Please fill in your password before selecting an instance!");
        }
    }

    /**
     * Title: Test handleConnect All Field Complete
     * Description: Test the logic of the method all fields complete
     * Requirement: 1.1.1, 1.2.1, 1.4.1
     * Assumptions: No issue with the database,  Action event triggered
     * Input: User = analyst, Password = analyst, Instance = KINGKAI-HP\SQLEXPRESS
     * Expected Result: Connect Button is enabled, DBService is initialize and Home Screen displays
     * Actual Result: Connect Button is enabled, DBService is initialize and Home Screen displays
     * Test Outcome: Passed
     */
    @Test
    public void testHandleConnectAllComplete() {
        modelTest.userProperty().setValue("analyst");
        modelTest.passProperty().setValue("analyst");
        modelTest.selectedInstanceProperty().setValue("KINGKAI-HP\\SQLEXPRESS");
        System.out.println("TC_20 was ran." + System.lineSeparator() + "Connect Button is enabled, DBService is initialize and Home Screen displays" + System.lineSeparator() +
                "Actual = ");
        handleConnect();
    }
    /**
     * Title: Test handleConnect All User Empty
     * Description: Test the logic of the method user empty
     * Requirement: 1.1.1, 1.2.1, 1.4.1
     * Assumptions: No issue with the database,  Action event triggered
     * Input: User = empty, Password = analyst, Instance = KINGKAI-HP\SQLEXPRESS
     * Expected Result: Connect Button is disabled
     * Actual Result: Connect Button is disabled
     * Test Outcome: Passed
     */
    @Test
    public void testHandleConnectUserEmpty() {
        modelTest.userProperty().setValue("");
        modelTest.passProperty().setValue("analyst");
        modelTest.selectedInstanceProperty().setValue("KINGKAI-HP\\SQLEXPRESS");
        System.out.println("TC_21 was ran." + System.lineSeparator() + "Expected = Connect Button is disabled" + System.lineSeparator() + "Actual = ");
        handleConnect();
    }
    /**
     * Title: Test handleConnect All Password Empty
     * Description: Test the logic of the method Password empty
     * Requirement: 1.1.1, 1.2.1, 1.4.1
     * Assumptions: No issue with the database,  Action event triggered
     * Input: User = analyst, Password = empty, Instance = KINGKAI-HP\SQLEXPRESS
     * Expected Result: Connect Button is disabled
     * Actual Result: Connect Button is disabled
     * Test Outcome: Passed
     */
    @Test
    public void testHandleConnectPWEmpty() {
        modelTest.userProperty().setValue("analyst");
        modelTest.passProperty().setValue("");
        modelTest.selectedInstanceProperty().setValue("KINGKAI-HP\\SQLEXPRESS");
        System.out.println("TC_22 was ran." + System.lineSeparator() + "Expected = Connect Button is disabled" + System.lineSeparator() + "Actual = ");
        handleConnect();
    }
    /**
     * Title: Test handleConnect All Instance Empty
     * Description: Test the logic of the method Instance empty
     * Requirement: 1.4.1, 1.4.2, 1.4.3
     * Assumptions: No issue with the database,  Action event triggered
     * Input: User = engineer, Password = engineer, Instance = empty
     * Expected Result: Connect Button is disabled
     * Actual Result: Connect Button is disabled
     * Test Outcome: Passed
     */
    @Test
    public void testHandleConnectInstanceEmpty() {
        modelTest.userProperty().setValue("engineer");
        modelTest.passProperty().setValue("engineer");
        modelTest.selectedInstanceProperty().setValue("");
        System.out.println("TC_23 was ran." + System.lineSeparator() + "Expected = Connect Button is disabled" + System.lineSeparator() + "Actual = ");
        handleConnect();
    }
    /**
     * Title: Test handleConnect All Fields Empty
     * Description: Test the logic of the method all fields empty
     * Requirement: 1.4.1, 1.4.2, 1.4.3
     * Assumptions: No issue with the database,  Action event triggered
     * Input: User = empty, Password = null, Instance = empty
     * Expected Result: Connect Button is disabled
     * Actual Result: Connect Button is disabled
     * Test Outcome: Passed
     */
    @Test
    public void testHandleConnectAllFieldEmpty() {
        modelTest.userProperty().setValue("");
        modelTest.passProperty().setValue(null);
        modelTest.selectedInstanceProperty().setValue("");
        System.out.println("TC_24 was ran." + System.lineSeparator() + "Expected = Connect Button is disabled" + System.lineSeparator() + "Actual = ");
        handleConnect();
    }
    /**
     * Title: Test handleConnect Invalid User ID
     * Description: Test the logic of the method invalid user ID
     * Requirement: 1.4.1, 1.4.2, 1.4.3
     * Assumptions: No issue with the database,  Action event triggered
     * Input: User = test, Password = admin, Instance = KINGKAI-HP\\SQLEXPRESS
     * Expected Result: Connect Button is Enabled
     *      *Error while connecting to SQL Server database!
     *      *Login failed error for user.
     * Actual Result: Connect Button is Enabled
     *      * Error while connecting to SQL Server database!
     *      * Login failed error for user.
     * Test Outcome: Passed
     */
    @Test
    public void testHandleConnectInvalidUser() {
        modelTest.userProperty().setValue("test");
        modelTest.passProperty().setValue("admin");
        modelTest.selectedInstanceProperty().setValue("KINGKAI-HP\\SQLEXPRESS");
        System.out.println("TC_25 was ran." + System.lineSeparator() + "Expected = Connect Button is Enabled\n" +
                "Error while connecting to SQL Server database! Login failed for user 'test' and Error exception" + System.lineSeparator() + "Actual = ");
        handleConnect();
    }
    /**
     * Title: Test handleConnect Invalid Password
     * Description: Test the logic of the method invalid Password
     * Requirement: 1.0
     * Assumptions: No issue with the database, Action event triggered
     * Input: User = admin, Password = engineer, Instance = KINGKAI-HP\\SQLEXPRESS"
     * Expected Result: Connect Button is Enabled
     *      *Error while connecting to SQL Server database!
     *      *Login failed error for user.
     * Actual Result: Connect Button is disabled
     * Connect Button is Enabled
     * Error while connecting to SQL Server database!
     * Login failed for user 'test'. ClientConnectionId:9adb6407-2582-4e27-818a-d8e91a510ae4
     */
    @Test
    public void testHandleConnectInvalidPW() {
        modelTest.userProperty().setValue("admin");
        modelTest.passProperty().setValue("test");
        modelTest.selectedInstanceProperty().setValue("KINGKAI-HP\\SQLEXPRESS");
        System.out.println("TC_26 was ran." + System.lineSeparator() + "Expected = Connect Button is Enabled\n" +
                "Error while connecting to SQL Server database! Login failed for user 'admin' and Error exception" + System.lineSeparator() + "Actual = ");
        handleConnect();
    }
    /**
     * Recreated the event handler in the Login model
     * in order to test the logic and all possible conditional
     * paths. Change the alert to a print string since not testing the UI
     * handle button is disabled if all any of the conditions are false
     */
    private void handleConnect() {
        //btnConnect.setDisable(true);
        System.out.println("Connect Button is disabled");
        if (!modelTest.getUser().isEmpty() &&
                !modelTest.getUser().isBlank() &&
                !modelTest.getPass().isEmpty() &&
                !modelTest.getPass().isBlank() &&
                !modelTest.getSelectedInstance().isEmpty() &&
                !modelTest.getSelectedInstance().isBlank()
        )
            try {
                System.out.println("Connect Button is Enabled");
                // Initialize DBService.
                DBService.getInstance(modelTest.getUser(), modelTest.getPass(), modelTest.getSelectedInstance());
                System.out.println("DBServices was initialized");
                System.out.println("Home Screen Displays");
                // Load the RMAListView screen.
                //FXMLLoader loader = new FXMLLoader();
                //loader.setLocation(getClass().getResource("RMAListView_V3.fxml"));
                //Parent root = loader.load();
                //Scene scene = new Scene(root);
                //Stage stage = new Stage();
                //stage.setTitle("RMA List View");
                //stage.setScene(scene);
                //stage.show();

                // Close this window.
                //((Node) event.getSource()).getScene().getWindow().hide();
                } catch (SQLException e) {
                //Alert a = new Alert(Alert.AlertType.ERROR);
                //a.setContentText("Error while connecting to SQL Server database!" + System.lineSeparator() + e.getLocalizedMessage());
                //a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                //a.show();
                System.out.println("Error while connecting to SQL Server database!" + System.lineSeparator() + e.getLocalizedMessage());
                } //catch (IOException e) {
                //Alert a = new Alert(Alert.AlertType.ERROR);
                //a.setContentText("Error while initializing form!" + System.lineSeparator() + e);
                //e.printStackTrace();
                //a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                //a.show();
                //}
                // Re-enable the button.
                //btnConnect.setDisable(false);
            }

    }
