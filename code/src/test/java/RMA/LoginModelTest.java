package RMA;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;
/*Log-ins
        Deciding on information
        Deciding on information
        User Name: RMA Analyst, RMA Engineer, RMA Admin
*/


public class LoginModelTest {
    //set up all the properties
    private StringProperty userTest, passTest, roleTest,
            dbTest, selectedInstanceTest;
    private ObservableList<String> instanceTest;
    private LoginModel modelTest;

    @Before
    public void setUp(){
        modelTest = new LoginModel();
        instanceTest = FXCollections.observableArrayList();
        userTest = new SimpleStringProperty();
        roleTest = new SimpleStringProperty();
        dbTest = new SimpleStringProperty();
        passTest = new SimpleStringProperty();
        selectedInstanceTest = new SimpleStringProperty();
    }

    /**
     * Title: Test getInstance Method
     * Description: Test the method and server instance exist
     * Requirement: 1.0, 1.4
     * Assumptions: No issue with user's system and server exist
     * Input: N/A
     * Expected Result: Arraylist contains
     * * KINGKAI-HP\MSSQLSERVER
     * * KINGKAI-HP\SQLEXPRESS
     * Actual Result: Arraylist contains
     * * KINGKAI-HP\MSSQLSERVER
     * * KINGKAI-HP\SQLEXPRESS
     * Test Outcome: Passed
     */
    @Test
    public void getInstanceSuccess() {

        instanceTest.add("KINGKAI-HP\\MSSQLSERVER");
        instanceTest.add("KINGKAI-HP\\SQLEXPRESS");
        assertArrayEquals(instanceTest.toArray(), modelTest.instancesProperty().toArray());
        System.out.println("TC_12 was ran." + System.lineSeparator() +"Expected = " + instanceTest + System.lineSeparator() +
                "Actual = " + modelTest.instancesProperty());
    }
}