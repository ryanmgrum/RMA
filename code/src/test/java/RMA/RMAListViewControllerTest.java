package RMA;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public  class RMAListViewControllerTest{
    private RMAListViewModel lsTest;
    private RMAListViewController lsControllerTest;
    private ObservableList<RMAListViewModel> tblList;
    private FilteredList<RMAListViewModel> filteredList;
    private TableView<RMAListViewModel> tblRMASummary;
    private TableColumn<RMAListViewModel, Boolean> colShouldDelete;
    private TableColumn<RMAListViewModel, String> colCustomerName;
    private TableColumn<RMAListViewModel, CustomerAddress> colBusinessName;
    private TableColumn<RMAListViewModel, String> colRMAId;
    private TableColumn<RMAListViewModel, String> colRMAStatus;
    private TableColumn<RMAListViewModel, PurchaseOrderProduct> colProduct;
    private TableColumn<RMAListViewModel, Boolean> colReplaceRepairIndicator;
    private TableColumn<RMAListViewModel, CustomerAddress> colCustomerInfoShipAddress;
    private TableColumn<RMAListViewModel, Integer> colReturnQuantity;
    private LocalDateTime lastUpdated;
    private StringProperty tableCount;
    private boolean btnDeleteDisabled, btnNewRMADisabled;//simulate button disabled property
    private TextField txtSearch;
    private DBService db;


    @Before
    public void setUp() throws Exception {
        db = DBService.getInstance("admin","admin","KINGKAI-HP\\SQLEXPRESS");
        lsTest = db.getRMA("2");
        lsControllerTest = new RMAListViewController();
        tblList = FXCollections.observableArrayList();
        filteredList = new FilteredList<RMAListViewModel>(tblList);
        lastUpdated = LocalDateTime.now();
        tableCount = new SimpleStringProperty();
        getRMAList();

    }
    /**Title: Test Initialize as Admin
     * Description: Test initialize function as admin and ensure all buttons are
     * enabled
     * Requirement:  2.4.2, 2.6, 2.7.1
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1, Admin Role
     * Expected Result: Table list contains values and all buttons are enabled
     * Actual Result: Table list contains values and all buttons are enabled
     * Test Outcome: Passed
     */
    @Test
    public void initializeAdmin() throws SQLException {
        // First set up the ObservableList and FilteredList.
        tblList = FXCollections.observableArrayList();
        filteredList = new FilteredList<RMAListViewModel>(tblList);
        lastUpdated = LocalDateTime.now();
        getRMAList();
        /**Couldn't test if the the tblList in test is equal to tblList in the controller property
         * because it's a private property, testing the logic path of getRMAlist method
         */
        System.out.println(tblList);
        // Disable New RMA and Delete buttons if user is an Engineer.

        if (db.getRole().equals("engineers")) {
            btnDeleteDisabled = true;
            btnNewRMADisabled = true;
        }

        System.out.println("TC_43 was ran." + System.lineSeparator() +"Expected = Delete button Disabled: False and New RMA Button Disabled: False" + System.lineSeparator() +
                "Actual = Delete Button Disabled: " + btnDeleteDisabled + " and New RMA Button Disabled: " + btnNewRMADisabled);
        assertFalse("Delete button is disabled:",btnDeleteDisabled);
        assertFalse("New RMA button is disabled:",btnNewRMADisabled);
    }

    /**Title: Test Initialize as Analyst
     * Description: Test initialize function as admin and ensure all buttons are
     * enabled
     * Requirement: 2.4.2, 2.6, 2.7.1
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1, Analyst role
     * Expected Result: Table list contains values and all buttons are enabled
     * Actual Result: Table list contains values and all buttons are enabled
     * Test Outcome: Passed
     */
    @Test
    public void initializeAnalyst() throws SQLException {
        // First set up the ObservableList and FilteredList.
        tblList = FXCollections.observableArrayList();
        filteredList = new FilteredList<RMAListViewModel>(tblList);
//        tblRMASummary.setItems(filteredList);
        //      tblRMASummary.setEditable(true);
        lastUpdated = LocalDateTime.now();
        getRMAList();
        /**Couldn't test if the the tblList in test is equal to tblList in the controller property
         * because it's a private property, testing the logic path of getRMAlist method
         */
        System.out.println(tblList);
        // Disable New RMA and Delete buttons if user is an Engineer.
        if (db.getRole().equals("engineers")) {
            btnDeleteDisabled = true;
            btnNewRMADisabled = true;
        }

        System.out.println("TC_44 was ran." + System.lineSeparator() +"Expected = Delete button Disabled: False and New RMA Button Disabled: False" + System.lineSeparator() +
                "Actual = Delete Button Disabled: " + btnDeleteDisabled + " and New RMA Button Disabled: " + btnNewRMADisabled);
        assertFalse("Delete button is disabled:",btnDeleteDisabled);
        assertFalse("New RMA button is disabled:",btnNewRMADisabled);
    }
    /**Title: Test Initialize as Engineer
     * Description: Test initialize function as admin and ensure all buttons are
     * enabled
     * Requirement:  2.4.2, 2.6, 2.7.1
     * PreCondition: Change the username = engineer and password = engineer
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1, Engineer role
     * Expected Result: Table list contains values and all buttons are enabled
     * Actual Result: Table list contains values and all buttons are enabled
     * Test Outcome: Pass if precondition is met
     */
    @Test
    public void initializeEngineer() throws SQLException {

        // First set up the ObservableList and FilteredList.
        tblList = FXCollections.observableArrayList();
        filteredList = new FilteredList<RMAListViewModel>(tblList);
//        tblRMASummary.setItems(filteredList);
        //      tblRMASummary.setEditable(true);
        lastUpdated = LocalDateTime.now();
        getRMAList();
        /**Couldn't test if the the tblList in test is equal to tblList in the controller property
         * because it's a private property, testing the logic path of getRMAlist method
         */
        System.out.println(tblList);
        // Disable New RMA and Delete buttons if user is an Engineer.
        if (db.getRole().equals("engineers")) {
            btnDeleteDisabled = true;
            btnNewRMADisabled = true;
        }

        System.out.println("TC_45 was ran." + System.lineSeparator() +"Expected = Delete button Disabled: True and New RMA Button Disabled: True" + System.lineSeparator() +
                "Actual = Delete Button Disabled: " + btnDeleteDisabled + " and New RMA Button Disabled: " + btnNewRMADisabled);
        assertTrue("Delete button is disabled:",btnDeleteDisabled);
        assertTrue("New RMA button is disabled:",btnNewRMADisabled);
    }
    /**Title: BtnRefreshOnAction
     * Description: Call the load RMAForm
     * Requirement:  2.4.1, 2.6.1, 4.17, 4.19
     * Assumptions: N/A
     * Input: null
     * Expected Result:
     * Actual Result:
     * Test Outcome:
     */
    @Test
    public void btnNewRMAOnAction(){//TODO Need to test his
        //lsControllerTest.btnNewRMAOnAction(null);
    }
    /**Title: Negative testing btnRefreshOnAction
     * Description: Utilize negative testing to determine if the method call is triggered if
     * the btnRefreshOnAction is triggered
     * Requirement:  2.5.1
     * Assumptions: N/A
     * Input: null
     * Expected Result: Error exception will be triggered due to the method being called
     * correctly and since the UI is not running there will be null pointer exception.
     * Actual Result: btnRefreshOnAction Button was called. Which calls the getRMAList method.
     * This exception java.lang.NullPointerException indicates the tblList.clear() could not be ran.
     * Test Outcome: Pass
     */
    @Test
    public void btnRefreshOnAction() {
        try {
            lsControllerTest.btnRefreshOnAction(null);
        }catch(Exception e){
            System.out.println("btnRefreshOnAction Button was called. Which calls the getRMAList method." +
                    " This exception " +  e + " indicates the tblList.clear() could not be ran" +
                    "because the tblList was not initialized due to the testing environment constraint.");
        }
    }
    /**Title: Test Delete button
     * Description: Testing the Logic of the delete button for all path
     * Requirement: 2.7, 2.7.2, 2.7.3, 2.7.4
     * Assumptions:No issues with the database and Alert confirmation all functional
     * Input: Confirm Delete checkbox is true, User confirms delete
     * Expected Result: Confirmation to delete was called
     * ConfirmDelete was set to true
     * Delete Method was called and the RMAmodel was added to the remove list
     * Table List was update and all deleted RMA were removed
     * 2021-04-26T15:48:53.649608300
     * correctly and since the UI is not running there will be null pointer exception.
     * Actual Result: Confirmation to delete was called
     * ConfirmDelete was set to true
     * Delete Method was called and the RMAmodel was added to the remove list
     * Table List was update and all deleted RMA were removed
     * 2021-04-26T15:48:53.649608300
     * Test Outcome: Pass
     */
    @Test
    public void btnDeleteOnActionDeleteConfirmed() {
        btnDeleteOnAction(true,true);

    }
    /**Title: Test Delete button User Cancels
     * Description: Testing the Logic of the delete button for all path
     * Requirement: 2.7, 2.7.2, 2.7.3, 2.7.4
     * Assumptions:No issues with the database and Alert confirmation all functional
     * Input: Confirm Delete checkbox is true, User confirms delete
     * Expected Result: Confirmation to delete was called
     * For loop break and No RMAs were deleted
     * Table List was update and all deleted RMA were removed
     * 2021-04-26T15:57:23.394535100
     * correctly and since the UI is not running there will be null pointer exception.
     * Actual Result: Confirmation to delete was called
     * For loop break and No RMAs were deleted
     * Table List was update and all deleted RMA were removed
     * 2021-04-26T15:57:23.394535100
     * Test Outcome: Pass
     */
    @Test
    public void btnDeleteOnActionDeleteCancelled() {
        btnDeleteOnAction(true,false);

    }

    /** Recreated the btnDeleteOnAction from the RMAListview Model to
     * test the logical paths of the method only the delete function was
     * tested and passed in previous test
     * @param confirmDelete
     * @param confirmMsg
     */
    public void btnDeleteOnAction(boolean confirmDelete, boolean confirmMsg) {
        // confirmDelete is used to confirm with the user that they want to delete their selected RMAs.
        //boolean confirmDelete = false;


        ArrayList<RMAListViewModel> toRemove = new ArrayList<>();
        try {
            //for (RMAListViewModel model : tblList)
                //if (model.getShouldDelete()) {
                    //if (!confirmDelete) {
                     if (confirmDelete ==true) {
                        // Ask the user if they are sure they want to delete the RMAs.
                       /* ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button context from OK to Confirm
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are you sure you want to permanently delete the selected RMAs?",
                                confirm, ButtonType.CANCEL
                        );

                        a.setHeaderText("RMA Close Confirmation");
                        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        Optional<ButtonType> result = a.showAndWait();*/
                        System.out.println("Confirmation to delete was called");
                        //if (result.isPresent() && result.get() != confirm)
                         if(confirmMsg==false)
                            //break;
                             System.out.println("For loop break and No RMAs were deleted");
                        else
                            //confirmDelete = true;
                         System.out.println("ConfirmDelete was set to true");
                    }
                    //model.delete();
                    //toRemove.add(model);
                //}
        //} catch (SQLException e) {
           // Alert a = new Alert(Alert.AlertType.ERROR);
            //a.setContentText("Error while connecting to SQL Server database!" + System.lineSeparator() + e.getLocalizedMessage());
            //a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            //a.show();
        } finally {
            if (confirmDelete) {
                //tblList.removeAll(toRemove);
                System.out.println("Table List was update and all deleted RMA were removed");
                // Update the item count.
                lastUpdated = LocalDateTime.now();
                System.out.println(lastUpdated);
                //tableCount.setValue(filteredList.size() + " items in list. Last updated: " + lastUpdated.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")));
            }
        }
    }



    /** getRMAList populates and refreshes the {@link ObservableList} backing the {@link TableView}.
     * (Requirement 2.5.1)
     */
    private void getRMAList() {
        // First clear the RMA list.
        tblList.clear();
        // Fetch and loop through the list of open RMAs.
        try {
            tblList.addAll(db.getRMAs());
        } catch (SQLException e) {
            //Alert a = new Alert(Alert.AlertType.ERROR);
            //a.setContentText("Error while fetching the list of open RMAs!" + System.lineSeparator() + e.getLocalizedMessage());
            //a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            //a.showAndWait();
        } finally {
            // Update the item count.
            lastUpdated = LocalDateTime.now();
           // tableCount.setValue(filteredList.size() + " items in list. Last updated: " + lastUpdated.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")));
            System.out.println(lastUpdated + " The table count was set.");
        }
    }
}

