package RMA;

import javafx.scene.control.Button;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class RMADetailsFormControllerTest {
    private RMADetailsFormModelTest modelTest;
    private DBService db;

    @Before
    public void setUp() throws Exception {
        db = DBService.getInstance("admin","admin","KINGKAI-HP\\SQLEXPRESS");
        // First initialize a new RMAFormModel.
        modelTest = new RMADetailsFormModelTest();
        initialize();
    }
    /**Title: Load the RMA Details
     * Description: Test the  Load the RMA Details Method
     * Requirement: 5.6.2, 5.6.2.1, 5.6.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: p
     * Expected = p
     * Actual = p
     * Test Outcome: Passed
     */
    @Test
    public void loadRMADetails() throws Exception {
        modelTest.getRMADetails();
        System.out.println("TC_46 was ran. and indication above states it getRMADetails from form model was called.");
    }
    /**Title: Initialize the Super Class
     * Description: This is just used to initialize the RMA Form Model Super Class
     * Requirement:
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: N/A
     * Expected = N/A
     * Actual = N/A
     * Test Outcome: Passed
     */
    @Test
    public void initialize() throws Exception {
        modelTest.setUp();
    }
    /**Title: Home Button Confirm
     * Description: This is to test the path to call the home button method
     * Requirement: 5.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: User chooses to confirm
     * Expected = Returns to the home screen
     * Actual = Button Home to return the user to the home screen worked
     * Test Outcome: Passed
     */
    @Test
    public void btnHomeOnActionConfirm() {
        btnHomeOnAction(true);//user chose to confirm to return home
    }
    /**Title: Home Button Cancel
     * Description: This is to test the path to call the home button method
     * Requirement: 5.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: User chooses to cancel
     * Expected = does not return to home screen
     * Actual = The button to return to home screen was not called the test passed
     * Test Outcome: Passed
     */
    @Test
    public void btnHomeOnActionCancel() {
        btnHomeOnAction(false); //user chose to cancel to return to home
    }
    /**Title: Customer Name Hyperlink
     * Description: This is a copy of the hplPONumber1OnAction method
     * to test path coverage
     * Requirement: 5.4, 5.4.1, 5.4.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: AddressID = 3 PONumber = 219389VF3
     * Expected = address set to populate with the PO Number
     * Actual = Electronia Inc.
     * Solid State Store
     * 250 Industrial Park St
     * Building #6
     * Pittsfield
     * Somerset
     * ME
     * 04967
     * United States of America
     * Phone: 207-689-7885
     *
     * PO Numbers:
     * Test Outcome: Passed
     */
    @Test
    public void hplCustomerNameDetails1OnAction() throws SQLException {
        RMADetailsFormModel model = new RMADetailsFormModel();
        model.setSelectedPONumber("219389VF3");
        model.setShippingAddress(db.getCustomerAddress(3));
        StringBuilder result = new StringBuilder(model.getShippingAddress() + System.lineSeparator() +
                "PO Numbers:" + System.lineSeparator());

        for (String po : model.poNumbersProperty())
            result.append(po).append(System.lineSeparator());

        model.setDetailsWindowText(result.toString());
        assertEquals(result.toString(),model.getDetailsWindowText());
        System.out.println("TC_49 was ran." + System.lineSeparator() + "Expected = " + result +
                System.lineSeparator() + "Actual= " + model.getDetailsWindowText());

    }
    /**Title: PO Number on Action
     * Description: This is a copy of the hplPONumber1OnAction method
     * to test path coverage and testing the string setting for the details window
     * Requirement: 5.4.1, 5.4.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: PONumber = 219389VF3
     * Expected = Product PO Number: 219389VF3
     * Product Category: Electronics
     * Product Name: Hard Drive
     * Quantity Ordered: 30
     * Order Date: 2021-04-22
     * Deliver Date: 2021-04-28
     * Product PO Number: 219389VF3
     * Product Category: Electronics
     * Product Name: Monitor
     * Quantity Ordered: 15
     * Order Date: 2021-04-22
     * Deliver Date: 2021-04-28
     * Product PO Number: 219389VF3
     * Product Category: Electronics
     * Product Name: Mouse
     * Quantity Ordered: 150
     * Order Date: 2021-04-22
     * Deliver Date: 2021-04-28
     * Product PO Number: 219389VF3
     * Product Category: Electronics
     * Product Name: Smartphone
     * Quantity Ordered: 50
     * Order Date: 2021-04-22
     * Deliver Date: 2021-04-28
     * Actual = Product PO Number: 219389VF3
     * Product Category: Electronics
     * Product Name: Hard Drive
     * Quantity Ordered: 30
     * Order Date: 2021-04-22
     * Deliver Date: 2021-04-28
     * Product PO Number: 219389VF3
     * Product Category: Electronics
     * Product Name: Monitor
     * Quantity Ordered: 15
     * Order Date: 2021-04-22
     * Deliver Date: 2021-04-28
     * Product PO Number: 219389VF3
     * Product Category: Electronics
     * Product Name: Mouse
     * Quantity Ordered: 150
     * Order Date: 2021-04-22
     * Deliver Date: 2021-04-28
     * Product PO Number: 219389VF3
     * Product Category: Electronics
     * Product Name: Smartphone
     * Quantity Ordered: 50
     * Order Date: 2021-04-22
     * Deliver Date: 2021-04-28
     *
     * PO Numbers:
     * Test Outcome: Passed
     */
    @Test
    public void hplPONumber1OnAction() throws SQLException {
        RMADetailsFormModel model = new RMADetailsFormModel();
        StringBuilder result = new StringBuilder();
        for (PurchaseOrderProduct product : db.getPurchaseOrderProducts("219389VF3") )
            result.append("Product PO Number: ").append(product.getPONumber()).append(System.lineSeparator()).append("Product Category: ").append(product.getProductCategory()).append(System.lineSeparator()).append("Product Name: ").append(product.getProductName()).append(System.lineSeparator()).append("Quantity Ordered: ").append(product.getQuantity()).append(System.lineSeparator()).append("Order Date: ").append(product.getOrderDate()).append(System.lineSeparator()).append("Deliver Date: ").append(product.getDeliverDate()).append(System.lineSeparator());

        model.setDetailsWindowText(result.toString());
        assertEquals(result.toString(), model.getDetailsWindowText());
        System.out.println("TC_50 was ran." + System.lineSeparator() + "Expected = " + result +
                System.lineSeparator() + "Actual= " + model.getDetailsWindowText());
    }
    /**Title: Special Instruction Edit button
     * Description: This is to test the path coverage of the edit button and simulated the edit and save method
     * Requirement: 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: tested all input [Edit, Confirmed], [Edit, Canceled], [Save, Confirmed], [Save,Canceled]
     * Expected = All 4 test runs and firs tone will confirm edit, second will cancel, 3rd will confirm save, 4th will cancel save
     * Actual = TC_51 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Edit
     * All other TextField and ComboBox were disabled except SpecialInstructionEdit and corresponding text field.
     * TC_52 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     * TC_53 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Save
     * All other TextField and ComboBox were enabled and change to the field we saved
     * TC_54 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     * Test Outcome: Passed
     */
    @Test
    public void btnSpecialInstructionEdit1OnAction() {
        System.out.println("TC_51 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("SpecialInstructionEdit","Edit", true);
        System.out.println("TC_52 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("SpecialInstructionEdit","Edit", false);
        System.out.println("TC_53 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("SpecialInstructionEdit","Save", true);
        System.out.println("TC_54 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("SpecialInstructionEdit","Save", false);
    }
    /**Title: Return Label Tracking Edit button
     * Description: This is to test the path coverage of the edit button and simulated the edit and save method
     * Requirement: 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: tested all input [Edit, Confirmed], [Edit, Canceled], [Save, Confirmed], [Save,Canceled]
     * Expected = All 4 test runs and firs tone will confirm edit, second will cancel, 3rd will confirm save, 4th will cancel save
     * Actual = TC_55 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Edit
     * All other TextField and ComboBox were disabled except ReturnLabelTrackingEdit and corresponding text field.
     * TC_56 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     * TC_57 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Save
     * All other TextField and ComboBox were enabled and change to the field we saved
     * TC_58 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     * Test Outcome: Passed
     */
    @Test
    public void btnReturnLabelTrackingEdit1OnAction() {
        System.out.println("TC_55 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("ReturnLabelTrackingEdit","Edit", true);
        System.out.println("TC_56 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("ReturnLabelTrackingEdit","Edit", false);
        System.out.println("TC_57 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("ReturnLabelTrackingEdit","Save", true);
        System.out.println("TC_58 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("ReturnLabelTrackingEdit","Save", false);
    }
    /**Title: Quantity Edit button
     * Description: This is to test the path coverage of the edit button and simulated the edit and save method
     * Requirement: 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: tested all input [Edit, Confirmed], [Edit, Canceled], [Save, Confirmed], [Save,Canceled]
     * Expected = All 4 test runs and firs tone will confirm edit, second will cancel, 3rd will confirm save, 4th will cancel save
     * Actual = TC_133 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Edit
     * All other TextField and ComboBox were disabled except QuantityEdit and corresponding text field.
     * TC_134 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     * TC_135 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Save
     * All other TextField and ComboBox were enabled and change to the field we saved
     * TC_136 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     */
    @Test
    public void btnQuantityEdit1OnAction() {
        System.out.println("TC_133 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("QuantityEdit","Edit", true);
        System.out.println("TC_134 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("QuantityEdit","Edit", false);
        System.out.println("TC_135 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("QuantityEdit","Save", true);
        System.out.println("TC_136 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("QuantityEdit","Save", false);
    }
    /**Title: Initial Evaluation Edit button
     * Description: This is to test the path coverage of the edit button and simulated the edit and save method
     * Requirement: 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: tested all input [Edit, Confirmed], [Edit, Canceled], [Save, Confirmed], [Save,Canceled]
     * Expected = All 4 test runs and firs tone will confirm edit, second will cancel, 3rd will confirm save, 4th will cancel save
     * Actual = TC_137 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Edit
     * All other TextField and ComboBox were disabled except InitialEvaluationEdit and corresponding text field.
     * TC_138 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     * TC_139 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Save
     * All other TextField and ComboBox were enabled and change to the field we saved
     * TC_140 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     */
    @Test
    public void btnInitialEvaluationEdit1OnAction() {
        System.out.println("TC_137 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("InitialEvaluationEdit","Edit", true);
        System.out.println("TC_138 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("InitialEvaluationEdit","Edit", false);
        System.out.println("TC_139 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("InitialEvaluationEdit","Save", true);
        System.out.println("TC_140 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("InitialEvaluationEdit","Save", false);
    }
    /**Title: Engineering Evaluation Edit button
     * Description: This is to test the path coverage of the edit button and simulated the edit and save method
     * Requirement: 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: tested all input [Edit, Confirmed], [Edit, Canceled], [Save, Confirmed], [Save,Canceled]
     * Expected = All 4 test runs and firs tone will confirm edit, second will cancel, 3rd will confirm save, 4th will cancel save
     * Actual = TC_141 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Edit
     * All other TextField and ComboBox were disabled except EngineeringEvaluation and corresponding text field.
     * TC_142 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     * TC_143 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Save
     * All other TextField and ComboBox were enabled and change to the field we saved
     * TC_144 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     */
    @Test
    public void btnEngineeringEvaluationEdit1OnAction() {
        System.out.println("TC_141 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("EngineeringEvaluation","Edit", true);
        System.out.println("TC_142 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("EngineeringEvaluation","Edit", false);
        System.out.println("TC_143 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("EngineeringEvaluation","Save", true);
        System.out.println("TC_144 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("EngineeringEvaluation","Save", false);
    }
    /**Title: Disposition Notes Edit button
     * Description: This is to test the path coverage of the edit button and simulated the edit and save method
     * Requirement: 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: tested all input [Edit, Confirmed], [Edit, Canceled], [Save, Confirmed], [Save,Canceled]
     * Expected = All 4 test runs and firs tone will confirm edit, second will cancel, 3rd will confirm save, 4th will cancel save
     * Actual = TC_145 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Edit
     * All other TextField and ComboBox were disabled except DispositionNotes and corresponding text field.
     * TC_146 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     * TC_147 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Save
     * All other TextField and ComboBox were enabled and change to the field we saved
     * TC_148 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     */
    @Test
    public void btnDispositionNotesEdit1OnAction() {
        System.out.println("TC_145 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("DispositionNotes","Edit", true);
        System.out.println("TC_146 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("DispositionNotes","Edit", false);
        System.out.println("TC_147 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("DispositionNotes","Save", true);
        System.out.println("TC_148 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("DispositionNotes","Save", false);
    }
    /**Title: Replacement Repair Tracking Edit button
     * Description: This is to test the path coverage of the edit button and simulated the edit and save method
     * Requirement: 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: tested all input [Edit, Confirmed], [Edit, Canceled], [Save, Confirmed], [Save,Canceled]
     * Expected = All 4 test runs and firs tone will confirm edit, second will cancel, 3rd will confirm save, 4th will cancel save
     * Actual = TC_149 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Edit
     * All other TextField and ComboBox were disabled except ReplacementRepairTracking and corresponding text field.
     * TC_150 was ran.
     * button text = Edit therefore Would you like to edit the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     * TC_151 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Confirm on the Pop, and the button text was change to Save
     * All other TextField and ComboBox were enabled and change to the field we saved
     * TC_152 was ran.
     * Save button text = Save therefore Would you like to save the changes to the  will populate in the pop up
     * Customer Selected Cancel on the pop up and nothing changed.
     */
    @Test
    public void btnReplacementRepairTrackingEdit1OnAction() {
        System.out.println("TC_149 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("ReplacementRepairTracking","Edit", true);
        System.out.println("TC_150 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("ReplacementRepairTracking","Edit", false);
        System.out.println("TC_151 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("ReplacementRepairTracking","Save", true);
        System.out.println("TC_152 was ran.");
        btnReplacementRepairTrackingEdit1OnActionTest("ReplacementRepairTracking","Save", false);
    }

    /**This method was created to only simulate the
     * on action home button to test path coverage
     * @param confirmB The boolean value passed to this method.
     */
    private void btnHomeOnAction(boolean confirmB){
        boolean confirm;
        /* Ask the user if they want to close.
        ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button text from OK to Confirm
        Alert a = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit?",
                confirm,
                ButtonType.NO
        );
        a.setHeaderText("RMA Details Close Confirmation");
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = a.showAndWait();
        */
        confirm = confirmB;
        //if (result.isPresent() && result.get() == confirm)
        if (confirm) {
            //btnHome.getScene().getWindow().hide();
            System.out.println("TC_47 was ran. Button Home to return the user to the home screen worked");
        }else {
            System.out.println("TC_48 was ran. The button to return to home screen was not called the test passed");
        }
    }

    /**
     * This is just to simulate and test the path of the Edit buttons
     *
     */
   private void btnReplacementRepairTrackingEdit1OnActionTest(String btnName, String btnText, boolean saveB) {

        //if (btnReplacementRepairTrackingEdit1.getText().equals("Edit"))
            if (btnText.equals("Edit")){ //change if statement for testing purposes
            // Ask the user if they want to edit the field.
            //if (editConfirmationMessage(btnReplacementRepairTrackingEdit1, "Replacement/Repair Tracking #", "Save"))
                if (editConfirmationMessageTest( saveB, "Edit")){//
                // Set field to be editable and disable the other modifiable (or used to modify) controls.
               /* txtReplacementRepairTracking1.setEditable(true);
                cmbEmployeeInfo1.setDisable(true);
                cmbRMAStatus1.setDisable(true);
                cmbCreditReplaceRepair1.setDisable(true);
                cmbReasonCode1.setDisable(true);
                btnSpecialInstructionEdit1.setDisable(true);
                cmbProductDetail1.setDisable(true);
                btnReturnLabelTrackingEdit1.setDisable(true);
                btnQuantityEdit1.setDisable(true);
                btnInitialEvaluationEdit1.setDisable(true);
                btnEngineeringEvaluationEdit1.setDisable(true);
                cmbDisposition1.setDisable(true);
                btnDispositionNotesEdit1.setDisable(true);
                dpReplacementRepairDate1.setDisable(true);
                cbReplacementRepairShip1.setDisable(true);*/
               System.out.println("All other TextField and ComboBox were disabled except " + btnName + " and corresponding text field.");
            }
        } else {
            // Ask the user if they want to save the field.
            //if (editConfirmationMessage(btnReplacementRepairTrackingEdit1, "Replacement/Repair Tracking #", "Edit"))
                 if (editConfirmationMessageTest( saveB, "Save")){
               // try {
                    // Update the field value in the database.
                   /* DBService.getInstance().updateRMAReplacementTrackingNumber(model.getRmaId(), model.getReplacementTrackingNumber());
                    updateLastModifiedDetails();
                    model.updateRMAProgress();

                    // Set field to be non-editable and enable the other modifiable (or used to modify) controls.
                    txtReplacementRepairTracking1.setEditable(false);
                    cmbEmployeeInfo1.setDisable(false);
                    cmbRMAStatus1.setDisable(false);
                    cmbCreditReplaceRepair1.setDisable(false);
                    cmbReasonCode1.setDisable(false);
                    btnSpecialInstructionEdit1.setDisable(false);
                    cmbProductDetail1.setDisable(false);
                    btnReturnLabelTrackingEdit1.setDisable(false);
                    btnQuantityEdit1.setDisable(false);
                    btnInitialEvaluationEdit1.setDisable(false);
                    if (DBService.getInstance().getRole().equals("admins"))
                        btnEngineeringEvaluationEdit1.setDisable(false);
                    cmbDisposition1.setDisable(false);
                    btnDispositionNotesEdit1.setDisable(false);
                    dpReplacementRepairDate1.setDisable(false);
                    cbReplacementRepairShip1.setDisable(false);*/
                /*} catch (SQLException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText(
                            "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                    e.getLocalizedMessage() + System.lineSeparator() +
                                    "Please click \"Save\" to try again."
                    );
                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    a.showAndWait();
                }*/
                     System.out.println("All other TextField and ComboBox were enabled and change to the field we saved");
            }
        }
    }

    /** editConfirmationMessage displays the confirmation message when the user clicks an "Edit" or "Save" {@link Button}
     *  on the form.
     * @param confB A reference to the {@link boolean} in order to edit its text.
     * @param btn The {@link String} name of the button.
     *
    */
    private boolean editConfirmationMessageTest(boolean confB, String btn) {
        String msg;
        boolean confirm;
        //if (btnName.getText().equals("Save"))
        if (btn.equals("Save")) {
            msg = "Would you like to save the changes to the ";
            System.out.println("Save button text = Save therefore " + msg + " will populate in the pop up");
        }else {
            msg = "Would you like to edit the ";
            System.out.println("button text = Edit therefore " + msg + " will populate in the pop up");
        }
        /*ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button context from OK to Confirm

        Alert a = new Alert(Alert.AlertType.CONFIRMATION,msg + name + "?", confirm, ButtonType.CANCEL);
        a.setTitle(editSave.equals("Save") ? "Confirm Edit" : "Confirm Save");
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = a.showAndWait();*/
        confirm = confB;
        if(confirm){
        //if (result.isPresent() && result.get() == confirm) {
            //btnName.setText(editSave);
            System.out.println("Customer Selected Confirm on the Pop, and the button text was change to " + btn);
            return true;
        } else {
            System.out.println("Customer Selected Cancel on the pop up and nothing changed.");
            return false;

        }
    }

}