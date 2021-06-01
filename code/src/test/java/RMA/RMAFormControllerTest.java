package RMA;

import javafx.scene.control.*;
import javafx.scene.layout.Region;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RMAFormControllerTest{
    private Label lblRMANumberValue;
    private ComboBox<String> cmbCustomerName;
    private ComboBox<CustomerAddress> cmbBusinessName;
    private ComboBox<String> cmbPoNumber;
    private TextArea txtShippingAddress;
    private Label lblOwnerValue;
    private ComboBox<String> cmbReasonCode;
    private ComboBox<String> cmbCreditReplaceRepair;
    private ComboBox<String> cmbRMAStatus;
    private TextArea txtAddInfoSpecialInt;
    private ComboBox<PurchaseOrderProduct> cmbProduct;
    private TextField txtReturnLabelTrack;
    private TextField txtQuantity;
    private TextArea txtInitialEvaluation;
    private ComboBox<String> cmbDisposition;
    private TextArea txtDispositionNotes;
    private TextField txtReplaceRepairTracking;
    private DatePicker dtpkrReplaceRepairShipDate;
    private CheckBox cbShipIndicator;
    private Button btnSave;
    private Button btnSaveClose;
    private Button btnCancel;
    private RMAFormModel model;
    private boolean customerConnectionError;
    private boolean customerRevert;
    private boolean businessConnectionError;
    private boolean businessRevert;
    private boolean poConnectionError;
    private boolean poRevert;

    private RMAFormModel modelTest;
    private RMAFormController modelTestController;
    private DBService db;
    @Before
    public void setUp() throws Exception {
        db = DBService.getInstance("admin","admin","KINGKAI-HP\\SQLEXPRESS");
        // First initialize a new RMAFormModel.
        modelTest = new RMAFormModel();
        modelTestController = new RMAFormController();
        initialize();
    }

    @Test
    public void initialize() throws SQLException {


        // Create TextFormatter for txtQuantity to restrict input.
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (!change.isContentChange())
                return change;

            String text = change.getControlNewText();

            if (!text.matches("^\\d*"))
                return null;
            else
                return change;
        });

        // Bind the model to the UI elements.
        modelTest.setRmaId("7");
        modelTest.setSelectedCustomerName("Electronia Inc.");
        modelTest.setCreated(LocalDateTime.now());
        modelTest.setCreatedBy("analyst");
        modelTest.setLastModified(LocalDateTime.now());
        modelTest.setLastModifiedBy("analyst");
        modelTest.setSelectedBusinessName(db.getCustomerAddress(3));
        modelTest.setShippingAddress(db.getCustomerAddress(3));
        modelTest.setSelectedRMAStatus("Dropped at Shop");
        modelTest.setSelectedPONumber("48593FF29");
        modelTest.setSelectedReturnReasonCode("ERROR");
        modelTest.setSelectedCreditReplaceRepair("Replace");
        modelTest.setAdditionalInfo("Testing White Box");
        modelTest.setSelectedProduct(db.getPurchaseOrderProduct(6));
        modelTest.setReturnQuantity(6);
        modelTest.setReturnLabelTracker("123456track");
        modelTest.setInitialEvaluation("Test worked");
        modelTest.setSelectedDisposition("Repair and add to inventory, credit customer");
        modelTest.setDispositionNotes("White Box Testing");
        modelTest.setReplacementTrackingNumber("");
        modelTest.setReplacementShipDate(null);
        modelTest.setShipReplacementRepair(false);

}

    @Test
    public void cmbCustomerNameOnHidden() {
        if (customerConnectionError)
            try { // Populate cmbBusinessName
                model.setSelectedBusinessName(null);
                model.businessNamesProperty().setAll(DBService.getInstance().getCustomerBusinessNames(model.getSelectedCustomerName()));
                cmbBusinessName.setDisable(false);
                customerConnectionError = false;
            } catch (SQLException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(
                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                e.getLocalizedMessage() + System.lineSeparator() +
                                "Please select the value again to try again."
                );
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
                customerConnectionError = true;
            }
    }


        @Test
        public void btnCancelOnAction() {
        }
    /**Title: Test Test Save Button No missing info
     * Description: Testing all the logic of the Save method
     * Requirement: 4.6, 4.10.1, 4.10.2
     * Assumptions: All Alert Confirmation works, no issue with the database
     * Input: All Required fields completed
     * Expected Result: Confirmation pop: Would you like to save the RMA?
     * User Confirmed Save
     * This is the message for Save: New RMA created and saved.
     * Actual Result: Confirmation pop: Would you like to save the RMA?
     * User Confirmed Save
     * This is the message for Save: New RMA created and saved.
     * Test Outcome: Pass
     */
        @Test
        public void btnSaveOnActionComplete() throws SQLException {
            btnSaveOnActionTest(true, false);
        }
     /**Title: Test Test Save Button Missing Customer name
     * Description: Testing all the logic of the Save method
     * Requirement: 4.6, 4.10.1, 4.10.2
     * Assumptions: Customer Name is missing
     * Input: All Required fields completed
     * Expected Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * Actual Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * Test Outcome: Pass
     */
    @Test
    public void btnSaveOnActionCustomerName() throws SQLException {
        modelTest.setSelectedCustomerName(null);
        btnSaveOnActionTest(true, false);
        modelTest.setSelectedCustomerName("Electronia Inc.");
        }
    /**Title: Test Test Save Button Missing Business Name
     * Description: Testing all the logic of the Save method
     * Requirement: 4.6, 4.10.1, 4.10.2
     * Assumptions: All Alert Confirmation works, no issue with the database
     * Input: Business Name is missing
     * Expected Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * Actual Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * This is the message for Save: New RMA created and saved.
     * Test Outcome: Pass
     */
    @Test
    public void btnSaveOnActionBusinessName() throws SQLException {
        modelTest.setSelectedBusinessName(null);
        btnSaveOnActionTest(true, false);
        modelTest.setSelectedBusinessName(db.getCustomerAddress(3));
    }
    /**Title: Test Test Save Button Missing PO Number
     * Description: Testing all the logic of the Save method
     * Requirement: 4.6, 4.10.1, 4.10.2
     * Assumptions: All Alert Confirmation works, no issue with the database
     * Input: PO Number is missing
     * Expected Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * Actual Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * This is the message for Save: New RMA created and saved.
     * Test Outcome: Pass
     */
    @Test
    public void btnSaveOnActionPONumber() throws SQLException {
        modelTest.setSelectedPONumber(null);
        btnSaveOnActionTest(true, false);
        modelTest.setSelectedPONumber("48593FF29");
    }
    /**Title: Test Test Save Button Missing Reason Code
     * Description: Testing all the logic of the Save method
     * Requirement: 4.6, 4.10.1, 4.10.2
     * Assumptions: All Alert Confirmation works, no issue with the database
     * Input: Reason Code is missing
     * Expected Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * Actual Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * This is the message for Save: New RMA created and saved.
     * Test Outcome: Pass
     */
    @Test
    public void btnSaveOnActionReasonCode() throws SQLException {
        modelTest.setSelectedReturnReasonCode(null);
        btnSaveOnActionTest(true, false);
        modelTest.setSelectedReturnReasonCode("ERROR");
    }
    /**Title: Test Test Save Button Missing Credit Replace Repair
     * Description: Testing all the logic of the Save method
     * Requirement: 4.6, 4.10.1, 4.10.2
     * Assumptions: All Alert Confirmation works, no issue with the database
     * Input: Credit Replace Repair is missing
     * Expected Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * Actual Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * This is the message for Save: New RMA created and saved.
     * Test Outcome: Pass
     */
    @Test
    public void btnSaveOnActionCreditReplaceRepair() throws SQLException {
        modelTest.setSelectedCreditReplaceRepair(null);
        btnSaveOnActionTest(true, false);
        modelTest.setSelectedCreditReplaceRepair("Replace");
    }
    /**Title: Test Test Save Button Missing RMA Status
     * Description: Testing all the logic of the Save method
     * Requirement: 4.6, 4.10.1, 4.10.2
     * Assumptions: All Alert Confirmation works, no issue with the database
     * Input: RMA Status is missing
     * Expected Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * Actual Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * This is the message for Save: New RMA created and saved.
     * Test Outcome: Pass
     */
    @Test
    public void btnSaveOnActionRMAStatus() throws SQLException {
        modelTest.setSelectedRMAStatus(null);
        btnSaveOnActionTest(true, false);
        modelTest.setSelectedRMAStatus("Dropped at Shop");
    }
    /**Title: Test Test Save Button Missing Product
     * Description: Testing all the logic of the Save method
     * Requirement: 4.6, 4.10.1, 4.10.2
     * Assumptions: All Alert Confirmation works, no issue with the database
     * Input: Product is missing
     * Expected Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * Actual Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * This is the message for Save: New RMA created and saved.
     * Test Outcome: Pass
     */
    @Test
    public void btnSaveOnActionProduct() throws SQLException {
        modelTest.setSelectedProduct(null);
        btnSaveOnActionTest(true, false);
        modelTest.setSelectedProduct(db.getPurchaseOrderProduct(6));
    }
    /**Title: Test Test Save Button Quantity is 0
     * Description: Testing all the logic of the Save method
     * Requirement: 4.6, 4.10.1, 4.10.2
     * Assumptions: All Alert Confirmation works, no issue with the database
     * Input: Quantity is 0
     * Expected Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * Actual Result: This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * This is the message for Save: New RMA created and saved.
     * Test Outcome: Pass
     */
    @Test
    public void btnSaveOnActionQuantity() throws SQLException {
        modelTest.setReturnQuantity(0);
        btnSaveOnActionTest(true,false);
        modelTest.setReturnQuantity(6);
    }
    /**Title: Test Test Save Button Cancel on Confirm Prompt
     * Description: Testing all the logic of the Save method
     * Requirement: 4.6, 4.10.1, 4.10.2
     * Assumptions: All Alert Confirmation works, no issue with the database
     * Input: Cancels
     * Expected Result: Confirmation pop: Would you like to save the RMA?
     * User Cancel Save
     * Actual Result: Confirmation pop: Would you like to save the RMA?
     * User Cancel Save
     * This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.
     * This is the message for Save: New RMA created and saved.
     * Test Outcome: Pass
     */
    @Test
    public void btnSaveOnActionCancel() throws SQLException {
        btnSaveOnActionTest(false, false);
    }
    /**Title: Test Test Save & Close Button Confirm
     * Description: Testing all the logic of the Save method
     * Requirement: 4.6, 4.10.1, 4.10.3
     * Assumptions: All Alert Confirmation works, no issue with the database
     * Input: Cancels
     * Expected Result: Confirmation pop: Would you like to save the RMA?
     * User Confirmed Save
     * This is the message for Save: New RMA created and saved.and Window closes
     * Actual Result: Confirmation pop: Would you like to save the RMA?
     * User Confirmed Save
     * This is the message for Save: New RMA created and saved.and Window closes
     * Test Outcome: Pass
     */
        @Test
        public void btnSaveCloseAction() throws SQLException {
            btnSaveOnActionTest(true, true);
        }
    /**Title: Test Test Save Button Cancel on Confirm Prompt
     * Description: Testing all the logic of the Save method
     * Requirement: 4.6, 4.10.1, 4.10.3
     * Assumptions: All Alert Confirmation works, no issue with the database
     * Input: Cancels
     * Expected Result: Confirmation pop: Would you like to save the RMA?
     * User Cancel Save
     * Actual Result: Confirmation pop: Would you like to save the RMA?
     * User Cancel Save
     * Test Outcome: Pass
     */
    @Test
    public void btnSaveCloseActionCancels() throws SQLException {
        btnSaveOnActionTest(false, true);
    }
    /**Title: Test Test Save Button Cancel on Confirm Prompt
     * Description: Testing all the logic of the Save method
     * Requirement: 2.4,2.4.1,
     * Assumptions: All Alert Confirmation works, no issue with the database
     * Input: Cancels
     * Expected Result: All dropdown were updated with default options RMA status List removed Closed from the list.
     * Actual Result: All Test passeed all list have been updated as
     * [Big Food Inc., Electronia Inc., Everything Inc.]
     * [SHP_ERROR-STK Wrong Item shipped in error, BROKEN-RET Damaged-Replace when returned, BROKEN-XSHIP Damaged-Replace Immediately,
     * ERROR Customer ordered wrong item, BROKEN-FLD Scrapped by Customer]
     * [Diagnose, Dropped at Shop, Pulled with Tech, Ready for installation, Waiting for Replacement]
     * [Credit, no return of item, Put item back into inventory, credit customer, Reject returned item,
     * return item to customer, Repair and add to inventory, credit customer, Replace item, credit customer, Replace item, scrap when returned, credit customer, Scrap item, credit customer]
     * User Cancel Save
     * Test Outcome: Pass
     */
    @Test
        public void getInitialRMAOptionsTst() throws SQLException {
        // First create an instance of DBService.
        //DBService dbs = DBService.getInstance();

        // Now populate dropdowns.
        modelTest.customerNamesProperty().setAll(db.getCustomerNames());
        modelTest.returnReasonCodesProperty().setAll(modelTest.convertHashMapToArrayList(db.getReturnReasonCodes()));
        ArrayList<String> list = db.getRMAStatuses();
        list.remove("Closed");
        modelTest.rmaStatusesProperty().setAll(list);
        modelTest.dispositionsProperty().setAll(db.getDispositions());

        assertEquals("[Big Food Inc., Electronia Inc., Everything Inc.]",modelTest.customerNamesProperty().toString());
        assertEquals("[SHP_ERROR-STK Wrong Item shipped in error, BROKEN-RET Damaged-Replace when returned, BROKEN-XSHIP Damaged-Replace Immediately, ERROR Customer ordered wrong item, BROKEN-FLD Scrapped by Customer]",modelTest.returnReasonCodesProperty().toString());
        assertEquals("[Diagnose, Dropped at Shop, Pulled with Tech, Ready for installation, Waiting for Replacement]",modelTest.rmaStatusesProperty().toString());
        assertEquals("[Credit, no return of item, Put item back into inventory, credit customer, Reject returned item, return item to customer, Repair and add to inventory, credit customer, Replace item, credit customer, Replace item, scrap when returned, credit customer, Scrap item, credit customer]",
                modelTest.dispositionsProperty().toString());
        System.out.println("TC_50 was ran " + "Expected = All dropdown were updated with default options RMA status List removed Closed from the list." + System.lineSeparator() +
                "Actual = All Test passeed all list have been updated as" + System.lineSeparator()+
                modelTest.customerNamesProperty().toString() + System.lineSeparator() +
                modelTest.returnReasonCodesProperty().toString() + System.lineSeparator() +
                modelTest.rmaStatusesProperty().toString() +
                modelTest.dispositionsProperty().toString());

        }

        protected void btnSaveOnActionTest(boolean confirm, boolean closeForm) throws SQLException {
            String miss = "This is the Alert for missing information:One or more of the required fields are missing. Please complete before continuing.";
            String save = "This is the message for Save: New RMA created and saved.";
        /*Alert miss = new Alert(Alert.AlertType.CONFIRMATION,
                "One or more of the required fields are missing. Please complete before continuing."
        );
        miss.setTitle("Required Field Missing!");
        miss.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Alert save = new Alert(Alert.AlertType.INFORMATION, "New RMA created and saved.");
        save.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        save.setTitle("RMA Save Confirmed");
*/
            // Check if all required fields are filled in.
            if (!modelTest.getSelectedCustomerName().isEmpty() &&
                    modelTest.getSelectedBusinessName() != null &&
                    !modelTest.getSelectedPONumber().isEmpty() &&
                    !modelTest.getSelectedReturnReasonCode().isEmpty() &&
                    !modelTest.getSelectedCreditReplaceRepair().isEmpty() &&
                    !modelTest.getSelectedRMAStatus().isEmpty() &&
                    modelTest.getSelectedProduct() != null &&
                    modelTest.getReturnQuantity() > 0
            ) {
                // Ask the user if they want to save the RMA before processing.
               /* ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button context from OK to Confirm
                Alert a = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "Would you like to save the RMA?",
                        confirm,
                        ButtonType.CANCEL
                );
                a.setTitle("Save RMA Verification");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            Optional<ButtonType> result = a.showAndWait();*/
                System.out.println("Confirmation pop: Would you like to save the RMA?");
                // if (result.isPresent() && result.get() == confirm)
                if (confirm ==true)
                    try {   System.out.println("User Confirmed Save");
                        // Save RMA into the database.
                        DBService dbs = DBService.getInstance("admin","admin","KINGKAI-HP\\SQLEXPRESS");
                        modelTest.setRmaId(dbs.createRMA(modelTest.getSelectedRMAStatus()));
                        db.createRMADetails(
                                modelTest.getRmaId(),
                                modelTest.getSelectedReturnReasonCode(),
                                modelTest.getSelectedCreditReplaceRepair(),
                                modelTest.getSelectedProduct().getPurchaseOrderProductId(),
                                modelTest.getReturnQuantity(),
                                modelTest.getReturnLabelTracker(),
                                modelTest.getAdditionalInfo(),
                                modelTest.getSelectedPONumber(),
                                modelTest.getInitialEvaluation(),
                                "", // engineeringEvaluation
                                modelTest.getSelectedDisposition(),
                                modelTest.getDispositionNotes(),
                                modelTest.getReplacementTrackingNumber(),
                                modelTest.getReplacementShipDate(),
                                modelTest.getShipReplacementRepair()
                        );
                        //lblOwnerValue.setText(db.getUser());
                        //save.showAndWait();
                        if (closeForm == true){
                            System.out.println(save + "and Window closes");
                        }else
                        System.out.println(save);
                    } catch (SQLException e) {
                   /* Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Error while connecting to SQL Server database!" + System.lineSeparator() + e.getLocalizedMessage());
                    error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    error.show();*/
                        System.out.println("SQL connection error: " + e);
                    }else{
                    System.out.println("User Cancel Save");
                }
            } else {
                //miss.showAndWait();
                System.out.println(miss);
            }
    }

}