package RMA;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;


/** RMADetailsFormController contains the logic and field references to the GUI controls on the RMADetailScreen.
 */
public class RMADetailsFormController implements Initializable {
    /** btnHome is used to return to the RMA List View screen.
     *  (Requirement 5.7)
     */
    @FXML
    private Button btnHome;
    /** pgRMAProgress is used to display how far along the RMA is to completion.
     * (Requirement 5.1)
     */
    @FXML
    private ProgressBar pgRMAProgress;
    /** tpInformation is used to expand collapse the Information section of the RMA Details form.
     *  (Requirement 5.2)
     */
    @FXML
    private TitledPane tpInformation;
    /** lblAutoGenNumber1 displays the {@link String} RMA ID.
     * (Requirement 5.2.2)
     */
    @FXML
    private Label lblAutoGenNumber1;
    /** hplCustomerNameDetails1 displays customer-specific information when clicked.
     */
    @FXML
    private Hyperlink hplCustomerNameDetails1;
    /** lblBusinessNameDetails1 holds the {@link String} Business Name referenced in this RMA request.
     */
    @FXML
    private Label lblBusinessNameDetails1;
    /** lblRmaAgeValue1 holds the number of days this RMA has been open since creation.
     * (Requirement 5.2.4)
     */
    @FXML
    private Label lblRMAAgeValue1;
    /** hplPoNumber1 displays details about the Purchase Order referenced in this RMA request.
     */
    @FXML
    private Hyperlink hplPONumber1;
    /** txtShippingAddress1 contains the Business's address details.
     */
    @FXML
    private TextArea txtShippingAddress1;
    /** lblAutoGenLastModifiedBy1 contains the date, time, and user that last modified this RMA request before opening.
     *(Requirement 5.2.3)
     */
    @FXML
    private Label lblAutoGenLastModifiedBy1;
    /** cmbEmployeeInfo1 contains the list of {@link String} usernames of employees stored in the database.
     */
    @FXML
    private ComboBox<String> cmbEmployeeInfo1;
    /** cmbRMAStatus1 contains the {@link String} list of RMA Statuses in the database.
     */
    @FXML
    private ComboBox<String> cmbRMAStatus1;
    /** cmbCreditReplaceRepair1 contains the {@link String} list of Credit, Replace, or Repair options.
     */
    @FXML
    private ComboBox<String> cmbCreditReplaceRepair1;
    /** cmbReasonCode1 contains the list of {@link String} Return Reason Codes in the database.
     */
    @FXML
    private ComboBox<String> cmbReasonCode1;
    /** txtSpecialInstruction1 contains additional info entered by an Admin or Analyst about the RMA request.
     */
    @FXML
    private TextArea txtSpecialInstruction1;
    /** lblAutoGenCreatedBy1 contains the username that created the RMA request.
     *  (Requirement 5.2.5)
     */
    @FXML
    private Label lblAutoGenCreatedBy1;
    /** btnSpecialInstructionEdit1 allows the user to edit and save changes to the txtSpecialInstruction1 {@link TextArea}.
     */
    @FXML
    private Button btnSpecialInstructionEdit1;
    /** tpProductInformation allows the user to expand and collapse the Product Information section of the RMA Details form.
     */
    @FXML
    private TitledPane tpProductInformation;
    /** cmbProductDetail1 contains the list of {@link PurchaseOrderProduct}s for the selected PO Number.
     */
    @FXML
    private ComboBox<PurchaseOrderProduct> cmbProductDetail1;
    /** txtReturnLabelTracking1 contains the return tracking label from the customer for the RMA.
     */
    @FXML
    private TextField txtReturnLabelTracking1;
    /** btnReturnLabelTrackingEdit1 allows the user to edit and save changes to the txtReturnLabelTracking1
     *  {@link TextField}.
     */
    @FXML
    private Button btnReturnLabelTrackingEdit1;
    /** txtQuantity1 holds the entered return quantity of the given Product for this RMA request.
     */
    @FXML
    private TextField txtQuantity1;
    /** btnQuantityEdit1 allows the user to edit and save changes to the txtQuantity1 {@link TextField}.
     */
    @FXML
    private Button btnQuantityEdit1;
    /** tpProductEvaluation allows the user to expand and collapse the Product Evaluation section.
     * (Requirement 5.6)
     */
    @FXML
    private TitledPane tpProductEvaluation;
    /** txtInitialEvaluation1 contains the initial evaluation of the returned Product for this RMA.
     */
    @FXML
    private TextArea txtInitialEvaluation1;
    /** btnInitialEvaluationEdit1 allows the user to edit and save changes to the txtInitialEvaluation1 {@link TextField}.
     */
    @FXML
    private Button btnInitialEvaluationEdit1;
    /** txtEngineeringEvaluation1 contains the evaluation of the returned Product by a user in the Engineers group.
     */
    @FXML
    private TextArea txtEngineeringEvaluation1;
    /** btnEngineeringEvaluationEdit1 allows the user to edit and save changes to the txtEngineeringEvaluation1 {@link TextArea}.
     */
    @FXML
    private Button btnEngineeringEvaluationEdit1;
    /** tpProductDisposition allows the user to collapse and expand the Product Disposition section of the RMA Details form.
     */
    @FXML
    private TitledPane tpProductDisposition;
    /** cmbDisposition contains the {@link String} list of dispositions available in the database.
     */
    @FXML
    private ComboBox<String> cmbDisposition1;
    /** txtDispositionNotes1 contains any notes to further describe the selected disposition.
     */
    @FXML
    private TextArea txtDispositionNotes1;
    /** btnDispositionNotesEdit1 button allows the user to edit and save changes to the txtDispositionNotes1 {@link TextArea}.
     */
    @FXML
    private Button btnDispositionNotesEdit1;
    /** tpReplacementDetail allows the user to collapse and expand the Replacement Detail section of the RMA Details form.
     * (Requirement 5.3)
     */
    @FXML
    private TitledPane tpReplacementDetail;
    /** txtReplacementRepairTracking1 contains the shipping tracking number for the replacement or repair that will be
     *  sent back to the customer.
     */
    @FXML
    private TextField txtReplacementRepairTracking1;
    /** btnReplacementRepairTrackingEdit1 allows the user to edit and save changes to the txtReplacementRepairTracking1
     *  {@link TextArea}.
     */
    @FXML
    private Button btnReplacementRepairTrackingEdit1;
    /** dpReplacementRepairDate1 lets the user select the date the replacement or repair is being sent out for shipping.
     */
    @FXML
    private DatePicker dpReplacementRepairDate1;
    /** cbReplacementRepairShip1 is used to signal that the replacement or repair is being shipped.
     */
    @FXML
    private CheckBox cbReplacementRepairShip1;
    /** txtDetailsWindow contains any additional details about the selected Customer or PO Number.
     * (Requirement 5.4)
     */
    @FXML
    private TextArea txtDetailsWindow;
    /** model contains the {@link RMADetailsFormModel} backing this form.
     */
    private RMADetailsFormModel model;
    /** These doubles are used to resize the collapsed TitlePanes back to their initial height.
     */
    private double tpInformationPrefHeight;
    private double tpProductPrefHeight;
    private double tpEvaluationPrefHeight;
    private double tpDispositionPrefHeight;
    private double tpReplaceRepairPrefHeight;
    /** The following booleans are used to revert a change made to the corresponding {@link ComboBox} or other control.
     */
    private boolean ownerRevert = false;
    private boolean rmaStatusRevert = false;
    private boolean creditReplaceRepairRevert = false;
    private boolean returnReasonCodeRevert = false;
    private boolean productRevert = false;
    private boolean dispositionRevert = false;
    private boolean dpReplacementRepairDate1Revert = false;
    private boolean cbReplacementRepairShip1Revert = false;
    /** initialSetup is used to skip ChangeListeners when setting initial values while loading form.
     */
    private boolean initialSetup = true;


    /** Default empty constructor, used when {@link javafx.fxml.FXMLLoader} loads the fxml file.
     */
    public RMADetailsFormController(){}

    /** loadRMADetails loads the form with the details for the passed-in RMA ID.
     *  (Requirement 5.2.4, 5.2.8, 5.4.1)
     * @param  rmaId The {@link String} RMA ID to load from the database.
     * @throws SQLException If there is an issue connecting to the SQL Server database or the SQL query.
     */
    public void loadRMADetails(String rmaId) throws SQLException {
        // Load model with the requested RMA's details.
        model.getRMADetails(rmaId);

        // Set the age.
        model.updateAge();

        // Set the current progress.
        model.updateRMAProgress();

        // Populate txtDetailsWindow with the default initial output.
        hplCustomerNameDetails1.fire();

        // Update all future changes.
        initialSetup = false;
    }

    /** initialize ties together the GUI's elements with the RMAFormModel and
     *  (Requirement 5.2.6, 5.2.7, 5.2.7.1, 5.2.8.1, 5.2.8.1.1, 5.2.8.1.2, 5.2.8.2,
     *               5.3.1, 5.4.1, 5.5, 5.6.1, 5.6.2, 5.6.2.1, 5.6.3, 5.8)
     * @param location The {@link URL} location of the fxml file that describes
     *                 the RMADetailForm window elements and their layout.
     * @param resources {@link ResourceBundle} that contains locale-specific data.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // First create a new RMADetailsFormModel.
        model = new RMADetailsFormModel();

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

        // Next bind the model to the GUI.
        pgRMAProgress.progressProperty().bindBidirectional(model.rmaProgressProperty());
        lblAutoGenNumber1.textProperty().bind(model.rmaIDProperty());
        hplCustomerNameDetails1.textProperty().bind(model.selectedCustomerNameProperty());
        lblBusinessNameDetails1.textProperty().bind(model.selectedBusinessNameProperty().asString());
        lblRMAAgeValue1.textProperty().bind(model.ageProperty().asString());
        hplPONumber1.textProperty().bind(model.selectedPONumberProperty());
        txtShippingAddress1.textProperty().bind(model.shippingAddressProperty().asString());
        lblAutoGenLastModifiedBy1.textProperty().bind(Bindings.concat(model.lastModifiedByProperty(), ", ", model.lastModifiedProperty()));
        cmbEmployeeInfo1.valueProperty().bindBidirectional(model.selectedOwnerProperty());
        cmbEmployeeInfo1.setItems(model.ownersProperty());
        cmbRMAStatus1.valueProperty().bindBidirectional(model.selectedRMAStatusProperty());
        cmbRMAStatus1.setItems(model.rmaStatusesProperty());
        cmbCreditReplaceRepair1.valueProperty().bindBidirectional(model.selectedCreditReplaceRepairProperty());
        cmbCreditReplaceRepair1.setItems(model.creditReplaceRepairProperty());
        cmbReasonCode1.valueProperty().bindBidirectional(model.selectedReturnReasonCodeProperty());
        cmbReasonCode1.setItems(model.returnReasonCodesProperty());
        txtSpecialInstruction1.textProperty().bindBidirectional(model.additionalInfoProperty());
        lblAutoGenCreatedBy1.textProperty().bind(Bindings.concat(model.createdByProperty(), ", ", model.createdProperty()));
        cmbProductDetail1.valueProperty().bindBidirectional(model.selectedProductProperty());
        cmbProductDetail1.setItems(model.productsProperty());
        txtReturnLabelTracking1.textProperty().bindBidirectional(model.returnLabelTrackerProperty());
        txtQuantity1.textProperty().bindBidirectional(model.returnQuantityProperty(), new NumberStringConverter());
        txtQuantity1.setTextFormatter(textFormatter);
        txtInitialEvaluation1.textProperty().bindBidirectional(model.initialEvaluationProperty());
        txtEngineeringEvaluation1.textProperty().bindBidirectional(model.engineeringEvaluationProperty());
        cmbDisposition1.valueProperty().bindBidirectional(model.selectedDispositionProperty());
        cmbDisposition1.setItems(model.dispositionsProperty());
        txtDispositionNotes1.textProperty().bindBidirectional(model.dispositionNotesProperty());
        txtReplacementRepairTracking1.textProperty().bindBidirectional(model.replacementTrackingNumberProperty());
        dpReplacementRepairDate1.valueProperty().bindBidirectional(model.replacementShipDateProperty());
        cbReplacementRepairShip1.selectedProperty().bindBidirectional(model.shipReplacementRepairProperty());
        txtDetailsWindow.textProperty().bindBidirectional(model.detailsWindowTextProperty());

        // Set selection permissions for Analyst and Engineer.
        if (DBService.getInstance().getRole().equals("engineers")) { // Disable all controls except btnEngineeringEvaluationEdit1.
            cmbEmployeeInfo1.setEditable(false);
            cmbEmployeeInfo1.getEditor().setEditable(false);
            cmbRMAStatus1.setEditable(false);
            cmbCreditReplaceRepair1.setEditable(false);
            cmbReasonCode1.setEditable(false);
            btnSpecialInstructionEdit1.setDisable(true);
            cmbProductDetail1.setEditable(false);
            btnReturnLabelTrackingEdit1.setDisable(true);
            btnQuantityEdit1.setDisable(true);
            btnInitialEvaluationEdit1.setDisable(true);
            cmbDisposition1.setEditable(false);
            btnDispositionNotesEdit1.setDisable(true);
            btnReplacementRepairTrackingEdit1.setDisable(true);
            dpReplacementRepairDate1.setEditable(false);
            cbReplacementRepairShip1.setDisable(true);
        } else if (DBService.getInstance().getRole().equals("analysts"))
            btnEngineeringEvaluationEdit1.setDisable(true);

        // Add ChangeListeners to each ComboBox so that their updates push to the database.
        cmbEmployeeInfo1.valueProperty().addListener(
            new ChangeListener<String>() {
                /** changed listens for changes to the cmbEmployeeInfo1 {@link ComboBox} in order to update them in the
                 *  back-end database.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link String} previous value.
                 * @param newValue The {@link String} new value to commit to the database.
                 */
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if (!initialSetup) // Ignore initially setting value.
                        if (!ownerRevert) {
                            try {
                                DBService.getInstance().updateRMAOwner(model.getRmaId(), newValue);
                                updateLastModifiedDetails();
                                model.updateRMAProgress();
                            } catch (SQLException e) {
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setContentText(
                                    "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                    e.getLocalizedMessage() + System.lineSeparator() +
                                    "Please select the value again to try again."
                                );
                                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                a.showAndWait();
                                ownerRevert = true;
                                model.setSelectedOwner(oldValue);
                            }
                        } else // Let the reversion go through.
                            ownerRevert = false;
                }
            }
        );

        cmbRMAStatus1.valueProperty().addListener(
            new ChangeListener<String>() {
                /** changed listens for changes to the cmbRMAStatus1 {@link ComboBox}, in particular the "Closed" option,
                 *  to override and revert the decision if the RMA is not ready for closing, or confirm with the user
                 *  before committing the close, because nothing can be modified after committing it.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link String} previous value.
                 * @param newValue The {@link String} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if (!initialSetup) // Ignore initially setting value.
                        if (!rmaStatusRevert) {
                            if (newValue.equals("Closed")) {
                                if (model.getRMAProgress() == 1.0) {
                                    // Ask the user if they want to continue with closing the request.
                                    ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button context from OK to Confirm
                                    Alert a = new Alert(
                                        Alert.AlertType.CONFIRMATION,
                                        "Closing an RMA request will mean no additional changes may be made to it. " +
                                        "Do you want to continue?",
                                        confirm,
                                        ButtonType.CANCEL
                                    );
                                    a.setTitle("Closing RMA Confirmation");
                                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                    Optional<ButtonType> result = a.showAndWait();

                                    // Update database and disable all controls.
                                    if (result.isPresent() && result.get() == confirm) {
                                        try {
                                            DBService.getInstance().updateRMAStatus(model.getRmaId(), newValue);
                                            updateLastModifiedDetails();
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
                                            btnReplacementRepairTrackingEdit1.setDisable(true);
                                            dpReplacementRepairDate1.setDisable(true);
                                            cbReplacementRepairShip1.setDisable(true);
                                        } catch (SQLException e) {
                                            Alert error = new Alert(Alert.AlertType.ERROR);
                                            error.setContentText(
                                                "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                                e.getLocalizedMessage() + System.lineSeparator() +
                                                "Please select the value again to try again."
                                            );
                                            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                            error.showAndWait();
                                            rmaStatusRevert = true;
                                            model.setSelectedRMAStatus(oldValue);
                                        }
                                    } else { // Revert the change.
                                        rmaStatusRevert = true;
                                        model.setSelectedRMAStatus(oldValue);
                                    }
                                } else { // Notify the user about being unable to close at this time and revert the change.
                                    Alert a = new Alert(
                                        Alert.AlertType.ERROR,
                                        "The RMA is not completely filled out. Please set any missing fields and try again.",
                                        ButtonType.OK
                                    );
                                    a.setTitle("Closing RMA Error");
                                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                    Optional<ButtonType> result = a.showAndWait();
                                    rmaStatusRevert = true;
                                    model.setSelectedRMAStatus(oldValue);
                                }
                            } else { // Change can go through, update database.
                                try {
                                    DBService.getInstance().updateRMAStatus(model.getRmaId(), newValue);
                                    updateLastModifiedDetails();
                                    model.updateRMAProgress();
                                } catch (SQLException e) {
                                    Alert error = new Alert(Alert.AlertType.ERROR);
                                    error.setContentText(
                                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                        e.getLocalizedMessage() + System.lineSeparator() +
                                        "Please select the value again to try again."
                                    );
                                    error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                    error.showAndWait();
                                    rmaStatusRevert = true;
                                    model.setSelectedRMAStatus(oldValue);
                                }
                            }
                        } else // Let the reversion go through.
                            rmaStatusRevert = false;
                }
            }
        );

        cmbCreditReplaceRepair1.valueProperty().addListener(
            new ChangeListener<String>() {
                /** changed listens for changes to the cmbCreditReplaceRepair1 {@link ComboBox} in case the user selects
                 *  or de-selects the "Credit" option in order to clear, disable, and hide, or re-enable, the Replacement
                 *  Detail section.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link String} previous value.
                 * @param newValue The {@link String} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if (!initialSetup) {
                        if (!creditReplaceRepairRevert) {
                            if (newValue.equals("Credit")) {
                                // Ask the user if they want to confirm clearing the Replacement Detail section because
                                // it is unnecessary if the customer is receiving a Credit.
                                if ((!model.getReplacementTrackingNumber().isEmpty() && !model.getReplacementTrackingNumber().isBlank()) ||
                                        model.getReplacementShipDate() != null ||
                                        model.getShipReplacementRepair()
                                ) {
                                    // Ask the user if they want to continue with clearing and disabling the fields.
                                    ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button context from OK to Confirm
                                    Alert a = new Alert(
                                            Alert.AlertType.CONFIRMATION,
                                            "Selecting \"Credit\" will clear all data entered under the " +
                                                    "\"Replacement Information\" section and disable those controls " +
                                                    "because the customer will not be receiving a replacement or repair when " +
                                                    "they are receiving credit. Do you want to continue?",
                                            confirm,
                                            ButtonType.CANCEL
                                    );
                                    a.setTitle("Clearing Replacement Information Verification");
                                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                    Optional<ButtonType> result = a.showAndWait();

                                    // Clear and disable the controls under Replacement Information.
                                    if (result.isPresent() && result.get() == confirm) {
                                        try {
                                            DBService.getInstance().updateRMACreditReplaceRepair(model.getRmaId(), newValue);

                                            model.setReplacementTrackingNumber("");
                                            DBService.getInstance().updateRMAReplacementTrackingNumber(model.getRmaId(), model.getReplacementTrackingNumber());
                                            txtReplacementRepairTracking1.setDisable(true);

                                            model.setReplacementShipDate(null);
                                            DBService.getInstance().updateRMAReplacementShipDate(model.getRmaId(), model.getReplacementShipDate());
                                            dpReplacementRepairDate1.setDisable(true);

                                            model.setShipReplacementRepair(false);
                                            DBService.getInstance().updateRMAShipReplacementRepair(model.getRmaId(), model.getShipReplacementRepair());
                                            cbReplacementRepairShip1.setDisable(true);

                                            tpReplacementDetail.setExpanded(false);
                                            tpReplacementDetail.setDisable(true);

                                            updateLastModifiedDetails();
                                            model.updateRMAProgress();
                                        } catch (SQLException e) {
                                            Alert error = new Alert(Alert.AlertType.ERROR);
                                            error.setContentText(
                                                    "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                                            e.getLocalizedMessage() + System.lineSeparator() +
                                                            "Please select the value again to try again."
                                            );
                                            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                            error.showAndWait();
                                            creditReplaceRepairRevert = true;
                                            model.setSelectedCreditReplaceRepair(oldValue);
                                        }
                                    } else {// Set value back to its previous value.
                                        creditReplaceRepairRevert = true;
                                        model.setSelectedCreditReplaceRepair(oldValue);
                                    }
                                } else { // Update the value and disable the controls.
                                    try {
                                        DBService.getInstance().updateRMACreditReplaceRepair(model.getRmaId(), newValue);

                                        txtReplacementRepairTracking1.setDisable(true);
                                        dpReplacementRepairDate1.setDisable(true);
                                        cbReplacementRepairShip1.setDisable(true);
                                        tpReplacementDetail.setExpanded(false);
                                        tpReplacementDetail.setDisable(true);

                                        updateLastModifiedDetails();
                                        model.updateRMAProgress();
                                    } catch (SQLException e) {
                                        Alert error = new Alert(Alert.AlertType.ERROR);
                                        error.setContentText(
                                                "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                                        e.getLocalizedMessage() + System.lineSeparator() +
                                                        "Please select the value again to try again."
                                        );
                                        error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                        error.showAndWait();
                                        creditReplaceRepairRevert = true;
                                        model.setSelectedCreditReplaceRepair(oldValue);
                                    }
                                }
                            } else { // Re-enable the controls.
                                try {
                                    DBService.getInstance().updateRMACreditReplaceRepair(model.getRmaId(), newValue);

                                    txtReplacementRepairTracking1.setDisable(false);
                                    dpReplacementRepairDate1.setDisable(false);
                                    cbReplacementRepairShip1.setDisable(false);
                                    tpReplacementDetail.setDisable(false);
                                    tpReplacementDetail.setExpanded(true);

                                    updateLastModifiedDetails();
                                    model.updateRMAProgress();
                                } catch (SQLException e) {
                                    Alert error = new Alert(Alert.AlertType.ERROR);
                                    error.setContentText(
                                            "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                                    e.getLocalizedMessage() + System.lineSeparator() +
                                                    "Please select the value again to try again."
                                    );
                                    error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                    error.showAndWait();
                                    creditReplaceRepairRevert = true;
                                    model.setSelectedCreditReplaceRepair(oldValue);
                                }
                            }
                        } else // Let the reversion go through.
                            creditReplaceRepairRevert = false;
                    } else if (newValue.equals("Credit")) { // Disable the controls.
                        txtReplacementRepairTracking1.setDisable(true);
                        dpReplacementRepairDate1.setDisable(true);
                        cbReplacementRepairShip1.setDisable(true);
                        tpReplacementDetail.setExpanded(false);
                        tpReplacementDetail.setDisable(true);
                    }
                }
            }
        );

        cmbReasonCode1.valueProperty().addListener(
            new ChangeListener<String>() {
                /** changed listens for changes to cmbReasonCode1 {@link ComboBox} in order to push the changes to the
                 *  database.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link String} previous value.
                 * @param newValue The {@link String} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if (!initialSetup) // Ignore initially setting value.
                        if (!returnReasonCodeRevert) {
                            try {
                                DBService.getInstance().updateRMAReturnReasonCode(model.getRmaId(), newValue.substring(0, newValue.indexOf(" ")));
                                updateLastModifiedDetails();
                                model.updateRMAProgress();
                            } catch (SQLException e) {
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setContentText(
                                    "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                    e.getLocalizedMessage() + System.lineSeparator() +
                                    "Please select the value again to try again."
                                );
                                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                a.showAndWait();
                                returnReasonCodeRevert = true;
                                model.setSelectedReturnReasonCode(oldValue);
                            }
                        } else // Let the reversion go through.
                            returnReasonCodeRevert = false;
                }
            }
        );

        cmbProductDetail1.valueProperty().addListener(
            new ChangeListener<PurchaseOrderProduct>() {
                /** changed listens for changes to the cmbProductDetail1 {@link ComboBox} in order to push them to the
                 *  back-end database.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link PurchaseOrderProduct} previous value.
                 * @param newValue The {@link PurchaseOrderProduct} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends PurchaseOrderProduct> observableValue, PurchaseOrderProduct oldValue, PurchaseOrderProduct newValue) {
                    if (!initialSetup) // Ignore initially setting value.
                        if (!productRevert) {
                            try {
                                DBService.getInstance().updateRMAProduct(model.getRmaId(), newValue.getPurchaseOrderProductId());
                                updateLastModifiedDetails();
                                model.updateRMAProgress();
                            } catch (SQLException e) {
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setContentText(
                                    "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                    e.getLocalizedMessage() + System.lineSeparator() +
                                    "Please select the value again to try again."
                                );
                                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                a.showAndWait();
                                productRevert = true;
                                model.setSelectedProduct(oldValue);
                            }
                        } else // Let the reversion go through.
                            productRevert = false;
                }
            }
        );

        cmbDisposition1.valueProperty().addListener(
            new ChangeListener<String>() {
                /** changed listens for changes to the cmbDisposition1 {@link ComboBox} in order to push them to the
                 *  back-end database.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link String} previous value.
                 * @param newValue The {@link String} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if (!initialSetup) // Ignore initially setting value.
                        if (!dispositionRevert) {
                            try {
                                DBService.getInstance().updateRMADisposition(model.getRmaId(), newValue);
                                updateLastModifiedDetails();
                                model.updateRMAProgress();
                            } catch (SQLException e) {
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setContentText(
                                    "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                    e.getLocalizedMessage() + System.lineSeparator() +
                                    "Please select the value again to try again."
                                );
                                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                a.showAndWait();
                                dispositionRevert = true;
                                model.setSelectedDisposition(oldValue);
                            }
                        } else // Let the reversion go through.
                            dispositionRevert = false;
                }
            }
        );

        pgRMAProgress.progressProperty().addListener(
            new ChangeListener<Number>() {
                /** changed listens for changes to the pgRMAProgress {@link ProgressBar} value in order to notify the
                 *  user that the RMA request can be closed.
                 * @param observableValue The {@link ObservableValue} that was changed.
                 * @param oldValue The {@link Number} previous value.
                 * @param newValue The {@link Number} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                    // Notify the user that they can close the RMA request.
                    if (newValue.doubleValue() == 1.0 && !model.getSelectedRMAStatus().equals("Closed")) {
                        Alert a = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "The RMA Request is ready to be closed (select \"Closed\" under RMA Status).",
                            ButtonType.OK
                        );
                        a.setTitle("RMA Ready to be Closed");
                        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        a.showAndWait();
                    }
                }
            }
        );

        // Add ChangeListener to cbReplaceRepairShip1 to update RMA Progress and Last Modified details.
        cbReplacementRepairShip1.selectedProperty().addListener(
            new ChangeListener<Boolean>() {
                /** changed listens for changes to cbReplacementRepairShip1 so that the Last Modified and RMA Progress
                 *  details are updated and any changes are pushed to the database.
                 * @param observableValue The {@link ObservableValue} that was changed.
                 * @param oldValue The {@link Boolean} previous value.
                 * @param newValue The {@link Boolean} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                    if (!initialSetup) // Ignore initial values set during detail population.
                        if (!cbReplacementRepairShip1Revert)
                            try {
                                DBService.getInstance().updateRMAShipReplacementRepair(model.getRmaId(), newValue);
                                updateLastModifiedDetails();
                                model.updateRMAProgress();
                            } catch (SQLException e) {
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setContentText(
                                    "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                    e.getLocalizedMessage() + System.lineSeparator() +
                                    "Please select the value again to try again."
                                );
                                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                a.showAndWait();
                                cbReplacementRepairShip1Revert = true;
                                model.setShipReplacementRepair(oldValue);
                            }
                        else // Let the change go through.
                            cbReplacementRepairShip1Revert = false;
                }
            }
        );

        // Add ChangeListener to dpReplacementRepairDate1 to update RMA Progress and Last Modified details.
        dpReplacementRepairDate1.valueProperty().addListener(
            new ChangeListener<LocalDate>() {
                /** changed listens for changes to dpReplacementRepairDate1 so that the Last Modified and RMA Progress
                 *  details are updated and any changes are pushed to the database.
                 * @param observableValue The {@link ObservableValue} that was changed.
                 * @param oldValue The {@link LocalDate} previous value.
                 * @param newValue The {@link LocalDate} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate oldValue, LocalDate newValue) {
                    if (!initialSetup) // Ignore initial setup setting values.
                        if (!dpReplacementRepairDate1Revert)
                            try {
                                DBService.getInstance().updateRMAReplacementShipDate(model.getRmaId(), newValue);
                                updateLastModifiedDetails();
                                model.updateRMAProgress();
                            } catch (SQLException e) {
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setContentText(
                                    "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                    e.getLocalizedMessage() + System.lineSeparator() +
                                    "Please select the value again to try again."
                                );
                                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                a.showAndWait();
                                dpReplacementRepairDate1Revert = true;
                                model.setReplacementShipDate(oldValue);
                            }
                        else // Let the reversion go through.
                            dpReplacementRepairDate1Revert = false;
                }
            }
        );
        // Add another listener to dpReplacementRepairDate1 for when the user clears the TextField portion.
        dpReplacementRepairDate1.getEditor().textProperty().addListener(
            new ChangeListener<String>() {
                /** changed listens for changes to the {@link TextField} portion of the {@link DatePicker} for when
                 *  the user clears the field so that the actual stored value for the value is cleared.
                 * @param observable The {@link ObservableValue} that changed.
                 * @param oldValue The {@link String} previous value.
                 * @param newValue The {@link String} new value.
                 */
                @Override
                public void changed(final ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!initialSetup)
                        if (newValue.isEmpty())
                            dpReplacementRepairDate1.setValue(null);
                }
            }
        );

        // ChangeListeners that set the PrefHeight for each TitledPane to 0 if not expanded.
        tpInformation.expandedProperty().addListener(
            new ChangeListener<Boolean>() {
                /** changed listens for the {@link TitledPane}s being collapsed and expanded in order to visually make
                 *  the collapsed pane look fully closed, and returning the height to the original height when expanded.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link Boolean} previous value.
                 * @param newValue The {@link Boolean} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                    tpInformation.setMaxHeight(!newValue ? 0.0 : tpInformationPrefHeight);
                }
            }
        );
        tpProductInformation.expandedProperty().addListener(
            new ChangeListener<Boolean>() {
                /** changed listens for the {@link TitledPane}s being collapsed and expanded in order to visually make
                 *  the collapsed pane look fully closed, and returning the height to the original height when expanded.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link Boolean} previous value.
                 * @param newValue The {@link Boolean} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                    tpProductInformation.setMaxHeight(!newValue ? 0.0 : tpProductPrefHeight);
                }
            }
        );
        tpProductEvaluation.expandedProperty().addListener(
            new ChangeListener<Boolean>() {
                /** changed listens for the {@link TitledPane}s being collapsed and expanded in order to visually make
                 *  the collapsed pane look fully closed, and returning the height to the original height when expanded.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link Boolean} previous value.
                 * @param newValue The {@link Boolean} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                    tpProductEvaluation.setMaxHeight(!newValue ? 0.0 : tpEvaluationPrefHeight);
                }
            }
        );
        tpProductDisposition.expandedProperty().addListener(
            new ChangeListener<Boolean>() {
                /** changed listens for the {@link TitledPane}s being collapsed and expanded in order to visually make
                 *  the collapsed pane look fully closed, and returning the height to the original height when expanded.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link Boolean} previous value.
                 * @param newValue The {@link Boolean} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                    tpProductDisposition.setMaxHeight(!newValue ? 0.0 : tpDispositionPrefHeight);
                }
            }
        );
        tpReplacementDetail.expandedProperty().addListener(
            new ChangeListener<Boolean>() {
                /** changed listens for the {@link TitledPane}s being collapsed and expanded in order to visually make
                 *  the collapsed pane look fully closed, and returning the height to the original height when expanded.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link Boolean} previous value.
                 * @param newValue The {@link Boolean} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                    tpReplacementDetail.setMaxHeight(!newValue ? 0.0 : tpReplaceRepairPrefHeight);
                }
            }
        );

        // Set the height to reset the TitledPanes' height on expansion.
        tpInformationPrefHeight = tpInformation.getPrefHeight();
        tpProductPrefHeight = tpProductInformation.getPrefHeight();
        tpEvaluationPrefHeight = tpProductEvaluation.getPrefHeight();
        tpDispositionPrefHeight = tpProductDisposition.getPrefHeight();
        tpReplaceRepairPrefHeight = tpReplacementDetail.getPrefHeight();

        // Set each ComboBox's OnShowing method and the DatePicker's OnShowing method to close if the user is an engineer.
        cmbEmployeeInfo1.setOnShowing(
            new EventHandler<Event>() {
                /** handle checks whether the user is an Engineer, and if so, prevents them from opening the {@link ComboBox}.
                 * @param event The {@link Event} passed to this event handler.
                 */
                @Override
                public void handle(Event event) {
                    if (DBService.getInstance().getRole().equals("engineers"))
                        Platform.runLater(() -> cmbEmployeeInfo1.hide());
                }
            }
        );
        cmbRMAStatus1.setOnShowing(
            new EventHandler<Event>() {
                /** handle checks whether the user is an Engineer, and if so, prevents them from opening the {@link ComboBox}.
                 * @param event The {@link Event} passed to this event handler.
                 */
                @Override
                public void handle(Event event) {
                    if (DBService.getInstance().getRole().equals("engineers"))
                        Platform.runLater(() -> cmbRMAStatus1.hide());
                }
            }
        );
        cmbCreditReplaceRepair1.setOnShowing(
            new EventHandler<Event>() {
                /** handle checks whether the user is an Engineer, and if so, prevents them from opening the {@link ComboBox}.
                 * @param event The {@link Event} passed to this event handler.
                 */
                @Override
                public void handle(Event event) {
                    if (DBService.getInstance().getRole().equals("engineers"))
                        Platform.runLater(() -> cmbCreditReplaceRepair1.hide());
                }
            }
        );
        cmbReasonCode1.setOnShowing(
            new EventHandler<Event>() {
                /** handle checks whether the user is an Engineer, and if so, prevents them from opening the {@link ComboBox}.
                 * @param event The {@link Event} passed to this event handler.
                 */
                @Override
                public void handle(Event event) {
                    if (DBService.getInstance().getRole().equals("engineers"))
                        Platform.runLater(() -> cmbReasonCode1.hide());
                }
            }
        );
        cmbProductDetail1.setOnShowing(
            new EventHandler<Event>() {
                /** handle checks whether the user is an Engineer, and if so, prevents them from opening the {@link ComboBox}.
                 * @param event The {@link Event} passed to this event handler.
                 */
                @Override
                public void handle(Event event) {
                    if (DBService.getInstance().getRole().equals("engineers"))
                        Platform.runLater(() -> cmbProductDetail1.hide());
                }
            }
        );
        cmbDisposition1.setOnShowing(
            new EventHandler<Event>() {
                /** handle checks whether the user is an Engineer, and if so, prevents them from opening the {@link ComboBox}.
                 * @param event The {@link Event} passed to this event handler.
                 */
                @Override
                public void handle(Event event) {
                    if (DBService.getInstance().getRole().equals("engineers"))
                        Platform.runLater(() -> cmbDisposition1.hide());
                }
            }
        );
        dpReplacementRepairDate1.setOnShowing(
            new EventHandler<Event>() {
                /** handle checks whether the user is an Engineer, and if so, prevents them from opening the {@link ComboBox}.
                 * @param event The {@link Event} passed to this event handler.
                 */
                @Override
                public void handle(Event event) {
                    if (DBService.getInstance().getRole().equals("engineers"))
                        Platform.runLater(() -> dpReplacementRepairDate1.hide());
                }
            }
        );
    }

    /** btnHomeOnAction is used to close the RMA request and return the user to the RMA List View.
     * @param event The {@link ActionEvent} passed to this event handler.
     */
    @FXML
    protected void btnHomeOnAction(ActionEvent event) {
        // Ask the user if they want to close.
        ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button text from OK to Confirm
        Alert a = new Alert(
            Alert.AlertType.CONFIRMATION,
            "Are you sure you want to exit?",
            confirm,
            ButtonType.NO
        );
        a.setTitle("RMA Details Close Confirmation");
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = a.showAndWait();

        if (result.isPresent() && result.get() == confirm)
            btnHome.getScene().getWindow().hide();
    }

    /** hplCustomerNameDetails1OnAction fires when the hplCustomerNameDetails1 is selected.
     * @param event The {@link ActionEvent} passed to this event handler.
     * (Requirement 5.4.2, 5.5)
     */
    @FXML
    protected void hplCustomerNameDetails1OnAction(ActionEvent event) {
        String result = model.getShippingAddress() + System.lineSeparator() +
                "PO Numbers:" + System.lineSeparator();

        for (String po : model.poNumbersProperty())
            result += po + System.lineSeparator();

        model.setDetailsWindowText(result);
    }

    /** hplPONumber1OnAction fires when the hplPONumber1 is selected.
     * @param event The {@link ActionEvent} passed to this event handler.
     * (Requirement 5.4.2, 5.5)
     */
    @FXML
    protected void hplPONumber1OnAction(ActionEvent event) {
        String result = "";
        for (PurchaseOrderProduct product : model.productsProperty())
            result +=
                "Product PO Number: " + product.getPONumber() + System.lineSeparator() +
                "Product Category: " + product.getProductCategory() + System.lineSeparator() +
                "Product Name: " + product.getProductName() + System.lineSeparator() +
                "Quantity Ordered: " + product.getQuantity() + System.lineSeparator() +
                "Order Date: " + product.getOrderDate() + System.lineSeparator() +
                "Deliver Date: " + product.getDeliverDate() + System.lineSeparator()
            ;

        model.setDetailsWindowText(result);
    }

    /** btnSpecialInstructionEdit1OnAction first asks the user whether they want to edit the {@link TextArea}, and if so,
     *  changes the txtSpecialInstruction1 {@link TextArea} to editable, the "Edit" button to "Save", and disables
     *  all other modifiable (or used to modify) controls while the editing is in progress. Once the user clicks on
     *  "Save", the new contents of the {@link TextArea} will be pushed to the database, the disabled controls will be
     *  re-enabled, and the button text will revert back to "Edit", along with changing the {@link TextArea} back to
     *  non-editable.
     * @param event The {@link ActionEvent} that is passed to this event handler.
     * (Requirement 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3)
     */
    @FXML
    protected void btnSpecialInstructionEdit1OnAction(ActionEvent event) {
        if (btnSpecialInstructionEdit1.getText().equals("Edit")) {
            // Ask the user if they want to edit the field.
            if (editConfirmationMessage(btnSpecialInstructionEdit1, "Additional Info/Special Instruction", "Save")) {
                // Set field to be editable and disable the other modifiable (or used to modify) controls.
                txtSpecialInstruction1.setEditable(true);
                cmbEmployeeInfo1.setDisable(true);
                cmbRMAStatus1.setDisable(true);
                cmbCreditReplaceRepair1.setDisable(true);
                cmbReasonCode1.setDisable(true);
                cmbProductDetail1.setDisable(true);
                btnReturnLabelTrackingEdit1.setDisable(true);
                btnQuantityEdit1.setDisable(true);
                btnInitialEvaluationEdit1.setDisable(true);
                btnEngineeringEvaluationEdit1.setDisable(true);
                cmbDisposition1.setDisable(true);
                btnDispositionNotesEdit1.setDisable(true);
                if (!model.getSelectedCreditReplaceRepair().equals("Credit")) {
                    btnReplacementRepairTrackingEdit1.setDisable(true);
                    dpReplacementRepairDate1.setDisable(true);
                    cbReplacementRepairShip1.setDisable(true);
                }
            }
        } else {
            // Ask the user if they want to save the field.
            if (editConfirmationMessage(btnSpecialInstructionEdit1, "Additional Info/Special Instruction", "Edit")) {
                try {
                    // Update the field value in the database.
                    DBService.getInstance().updateRMAAdditionalInfo(model.getRmaId(), model.getAdditionalInfo());
                    updateLastModifiedDetails();
                    model.updateRMAProgress();

                    // Set field to be non-editable and enable the other modifiable (or used to modify) controls.
                    txtSpecialInstruction1.setEditable(false);
                    cmbEmployeeInfo1.setDisable(false);
                    cmbRMAStatus1.setDisable(false);
                    cmbCreditReplaceRepair1.setDisable(false);
                    cmbReasonCode1.setDisable(false);
                    cmbProductDetail1.setDisable(false);
                    btnReturnLabelTrackingEdit1.setDisable(false);
                    btnQuantityEdit1.setDisable(false);
                    btnInitialEvaluationEdit1.setDisable(false);
                    if (DBService.getInstance().getRole().equals("admins"))
                        btnEngineeringEvaluationEdit1.setDisable(false);
                    cmbDisposition1.setDisable(false);
                    btnDispositionNotesEdit1.setDisable(false);
                    if (!model.getSelectedCreditReplaceRepair().equals("Credit")) {
                        btnReplacementRepairTrackingEdit1.setDisable(false);
                        dpReplacementRepairDate1.setDisable(false);
                        cbReplacementRepairShip1.setDisable(false);
                    }
                } catch (SQLException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText(
                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                        e.getLocalizedMessage() + System.lineSeparator() +
                        "Please click \"Save\" to try again."
                    );
                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    a.showAndWait();
                }
            }
        }
    }

    /** btnReturnLabelTrackingEdit1OnAction first asks the user whether they want to edit the {@link TextField}, and if
     *  so, changes the txtReturnLabelTrackingEdit1 {@link TextField} to editable, the "Edit" button to "Save", and
     *  disables all other modifiable (or used to modify) controls while the editing is in progress. Once the user
     *  clicks on "Save", the new contents of the {@link TextField} will be pushed to the database, the disabled controls
     *  will be re-enabled, and the button text will revert back to "Edit", along with changing the {@link TextField}
     *  back to non-editable.
     * (Requirement 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3)
     * @param event The {@link ActionEvent} that was passed to this method.
     */
    @FXML
    protected void btnReturnLabelTrackingEdit1OnAction(ActionEvent event) {
        if (btnReturnLabelTrackingEdit1.getText().equals("Edit")) {
            // Ask the user if they want to edit the field.
            if (editConfirmationMessage(btnReturnLabelTrackingEdit1, "Return Label Tracking #", "Save")) {
                // Set field to be editable and disable the other modifiable (or used to modify) controls.
                txtReturnLabelTracking1.setEditable(true);
                cmbEmployeeInfo1.setDisable(true);
                cmbRMAStatus1.setDisable(true);
                cmbCreditReplaceRepair1.setDisable(true);
                cmbReasonCode1.setDisable(true);
                btnSpecialInstructionEdit1.setDisable(true);
                cmbProductDetail1.setDisable(true);
                btnQuantityEdit1.setDisable(true);
                btnInitialEvaluationEdit1.setDisable(true);
                btnEngineeringEvaluationEdit1.setDisable(true);
                cmbDisposition1.setDisable(true);
                btnDispositionNotesEdit1.setDisable(true);
                if (!model.getSelectedCreditReplaceRepair().equals("Credit")) {
                    btnReplacementRepairTrackingEdit1.setDisable(true);
                    dpReplacementRepairDate1.setDisable(true);
                    cbReplacementRepairShip1.setDisable(true);
                }
            }
        } else {
            // Ask the user if they want to save the field.
            if (editConfirmationMessage(btnReturnLabelTrackingEdit1, "Return Label Tracking #", "Edit")) {
                try {
                    // Update the field value in the database.
                    DBService.getInstance().updateRMAReturnLabelTracker(model.getRmaId(), model.getReturnLabelTracker());
                    updateLastModifiedDetails();
                    model.updateRMAProgress();

                    // Set field to be non-editable and enable the other modifiable (or used to modify) controls.
                    txtReturnLabelTracking1.setEditable(false);
                    cmbEmployeeInfo1.setDisable(false);
                    cmbRMAStatus1.setDisable(false);
                    cmbCreditReplaceRepair1.setDisable(false);
                    cmbReasonCode1.setDisable(false);
                    btnSpecialInstructionEdit1.setDisable(false);
                    cmbProductDetail1.setDisable(false);
                    btnQuantityEdit1.setDisable(false);
                    btnInitialEvaluationEdit1.setDisable(false);
                    if (DBService.getInstance().getRole().equals("admins"))
                        btnEngineeringEvaluationEdit1.setDisable(false);
                    cmbDisposition1.setDisable(false);
                    btnDispositionNotesEdit1.setDisable(false);
                    if (!model.getSelectedCreditReplaceRepair().equals("Credit")) {
                        btnReplacementRepairTrackingEdit1.setDisable(false);
                        dpReplacementRepairDate1.setDisable(false);
                        cbReplacementRepairShip1.setDisable(false);
                    }
                } catch (SQLException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText(
                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                        e.getLocalizedMessage() + System.lineSeparator() +
                        "Please click \"Save\" to try again."
                    );
                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    a.showAndWait();
                }
            }
        }
    }

    /** btnQuantityEdit1OnAction first asks the user whether they want to edit the {@link TextField}, and if
     *  so, changes the txtQuantity1 {@link TextField} to editable, the "Edit" button to "Save", and disables all other
     *  modifiable (or used to modify) controls while the editing is in progress. Once the user clicks on "Save", the
     *  new contents of the {@link TextField} will be pushed to the database, the disabled controls will be re-enabled,
     *  and the button text will revert back to "Edit", along with changing the {@link TextField} back to non-editable.
     * (Requirement 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3)
     * @param event The {@link ActionEvent} that was passed to this method.
     */
    @FXML
    protected void btnQuantityEdit1OnAction(ActionEvent event) {
        if (btnQuantityEdit1.getText().equals("Edit")) {
            // Ask the user if they want to edit the field.
            if (editConfirmationMessage(btnQuantityEdit1, "Quantity", "Save")) {
                // Set field to be editable and disable the other modifiable (or used to modify) controls.
                txtQuantity1.setEditable(true);
                cmbEmployeeInfo1.setDisable(true);
                cmbRMAStatus1.setDisable(true);
                cmbCreditReplaceRepair1.setDisable(true);
                cmbReasonCode1.setDisable(true);
                btnSpecialInstructionEdit1.setDisable(true);
                cmbProductDetail1.setDisable(true);
                btnReturnLabelTrackingEdit1.setDisable(true);
                btnInitialEvaluationEdit1.setDisable(true);
                btnEngineeringEvaluationEdit1.setDisable(true);
                cmbDisposition1.setDisable(true);
                btnDispositionNotesEdit1.setDisable(true);
                if (!model.getSelectedCreditReplaceRepair().equals("Credit")) {
                    btnReplacementRepairTrackingEdit1.setDisable(true);
                    dpReplacementRepairDate1.setDisable(true);
                    cbReplacementRepairShip1.setDisable(true);
                }
            }
        } else {
            // Ask the user if they want to save the field.
            if (editConfirmationMessage(btnQuantityEdit1, "Quantity", "Edit")) {
                try {
                    // Update the field value in the database.
                    DBService.getInstance().updateRMAReturnQuantity(model.getRmaId(), model.getReturnQuantity());
                    updateLastModifiedDetails();
                    model.updateRMAProgress();

                    // Set field to be non-editable and enable the other modifiable (or used to modify) controls.
                    txtQuantity1.setEditable(false);
                    cmbEmployeeInfo1.setDisable(false);
                    cmbRMAStatus1.setDisable(false);
                    cmbCreditReplaceRepair1.setDisable(false);
                    cmbReasonCode1.setDisable(false);
                    btnSpecialInstructionEdit1.setDisable(false);
                    cmbProductDetail1.setDisable(false);
                    btnReturnLabelTrackingEdit1.setDisable(false);
                    btnInitialEvaluationEdit1.setDisable(false);
                    if (DBService.getInstance().getRole().equals("admins"))
                        btnEngineeringEvaluationEdit1.setDisable(false);
                    cmbDisposition1.setDisable(false);
                    btnDispositionNotesEdit1.setDisable(false);
                    if (!model.getSelectedCreditReplaceRepair().equals("Credit")) {
                        btnReplacementRepairTrackingEdit1.setDisable(false);
                        dpReplacementRepairDate1.setDisable(false);
                        cbReplacementRepairShip1.setDisable(false);
                    }
                } catch (SQLException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText(
                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                        e.getLocalizedMessage() + System.lineSeparator() +
                        "Please click \"Save\" to try again."
                    );
                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    a.showAndWait();
                }
            }
        }
    }

    /** btnInitialEvaluationEdit1OnAction first asks the user whether they want to edit the {@link TextArea}, and if so,
     *  changes the txtInitialEvaluation1 {@link TextArea} to editable, the "Edit" button to "Save", and disables
     *  all other modifiable (or used to modify) controls while the editing is in progress. Once the user clicks on
     *  "Save", the new contents of the {@link TextArea} will be pushed to the database, the disabled controls will be
     *  re-enabled, and the button text will revert back to "Edit", along with changing the {@link TextArea} back to
     *  non-editable.
     * @param event The {@link ActionEvent} that is passed to this event handler.
     * (Requirement 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3)
     */
    @FXML
    protected void btnInitialEvaluationEdit1OnAction(ActionEvent event) {
        if (btnInitialEvaluationEdit1.getText().equals("Edit")) {
            // Ask the user if they want to edit the field.
            if (editConfirmationMessage(btnInitialEvaluationEdit1, "Initial Evaluation", "Save")) {
                // Set field to be editable and disable the other modifiable (or used to modify) controls.
                txtInitialEvaluation1.setEditable(true);
                cmbEmployeeInfo1.setDisable(true);
                cmbRMAStatus1.setDisable(true);
                cmbCreditReplaceRepair1.setDisable(true);
                cmbReasonCode1.setDisable(true);
                btnSpecialInstructionEdit1.setDisable(true);
                cmbProductDetail1.setDisable(true);
                btnReturnLabelTrackingEdit1.setDisable(true);
                btnQuantityEdit1.setDisable(true);
                btnEngineeringEvaluationEdit1.setDisable(true);
                cmbDisposition1.setDisable(true);
                btnDispositionNotesEdit1.setDisable(true);
                if (!model.getSelectedCreditReplaceRepair().equals("Credit")) {
                    btnReplacementRepairTrackingEdit1.setDisable(true);
                    dpReplacementRepairDate1.setDisable(true);
                    cbReplacementRepairShip1.setDisable(true);
                }
            }
        } else {
            // Ask the user if they want to save the field.
            if (editConfirmationMessage(btnInitialEvaluationEdit1, "Initial Evaluation", "Edit")) {
                try {
                    // Update the field value in the database.
                    DBService.getInstance().updateRMAInitialEvaluation(model.getRmaId(), model.getInitialEvaluation());
                    updateLastModifiedDetails();
                    model.updateRMAProgress();

                    // Set field to be non-editable and enable the other modifiable (or used to modify) controls.
                    txtInitialEvaluation1.setEditable(false);
                    cmbEmployeeInfo1.setDisable(false);
                    cmbRMAStatus1.setDisable(false);
                    cmbCreditReplaceRepair1.setDisable(false);
                    cmbReasonCode1.setDisable(false);
                    btnSpecialInstructionEdit1.setDisable(false);
                    cmbProductDetail1.setDisable(false);
                    btnReturnLabelTrackingEdit1.setDisable(false);
                    btnQuantityEdit1.setDisable(false);
                    if (DBService.getInstance().getRole().equals("admins"))
                        btnEngineeringEvaluationEdit1.setDisable(false);
                    cmbDisposition1.setDisable(false);
                    btnDispositionNotesEdit1.setDisable(false);
                    if (!model.getSelectedCreditReplaceRepair().equals("Credit")) {
                        btnReplacementRepairTrackingEdit1.setDisable(false);
                        dpReplacementRepairDate1.setDisable(false);
                        cbReplacementRepairShip1.setDisable(false);
                    }
                } catch (SQLException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText(
                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                        e.getLocalizedMessage() + System.lineSeparator() +
                        "Please click \"Save\" to try again."
                    );
                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    a.showAndWait();
                }
            }
        }
    }

    /** btnEngineeringEvaluationEdit1OnAction first asks the user whether they want to edit the {@link TextArea}, and if so,
     *  changes the txtEngineeringEvaluation1 {@link TextArea} to editable, the "Edit" button to "Save", and disables
     *  all other modifiable (or used to modify) controls while the editing is in progress. Once the user clicks on
     *  "Save", the new contents of the {@link TextArea} will be pushed to the database, the disabled controls will be
     *  re-enabled, and the button text will revert back to "Edit", along with changing the {@link TextArea} back to
     *  non-editable.
     * @param event The {@link ActionEvent} that is passed to this event handler.
     * (Requirement 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3)
     */
    @FXML
    protected void btnEngineeringEvaluationEdit1OnAction(ActionEvent event) {
        if (btnEngineeringEvaluationEdit1.getText().equals("Edit")) {
            // Ask the user if they want to edit the field.
            if (editConfirmationMessage(btnEngineeringEvaluationEdit1, "Engineering Evaluation", "Save")) {
                // Set field to be editable and disable the other modifiable (or used to modify) controls.
                txtEngineeringEvaluation1.setEditable(true);
                if (DBService.getInstance().getRole().equals("admins")) {
                    cmbEmployeeInfo1.setDisable(true);
                    cmbRMAStatus1.setDisable(true);
                    cmbCreditReplaceRepair1.setDisable(true);
                    cmbReasonCode1.setDisable(true);
                    btnSpecialInstructionEdit1.setDisable(true);
                    cmbProductDetail1.setDisable(true);
                    btnReturnLabelTrackingEdit1.setDisable(true);
                    btnQuantityEdit1.setDisable(true);
                    btnInitialEvaluationEdit1.setDisable(true);
                    cmbDisposition1.setDisable(true);
                    btnDispositionNotesEdit1.setDisable(true);
                    if (!model.getSelectedCreditReplaceRepair().equals("Credit")) {
                        btnReplacementRepairTrackingEdit1.setDisable(true);
                        dpReplacementRepairDate1.setDisable(true);
                        cbReplacementRepairShip1.setDisable(true);
                    }
                }
            }
        } else {
            // Ask the user if they want to save the field.
            if (editConfirmationMessage(btnEngineeringEvaluationEdit1, "Engineering Evaluation", "Edit")) {
                try {
                    // Update the field value in the database.
                    DBService.getInstance().updateRMAEngineeringEvaluation(model.getRmaId(), model.getEngineeringEvaluation());
                    updateLastModifiedDetails();
                    model.updateRMAProgress();

                    // Set field to be non-editable and enable the other modifiable (or used to modify) controls.
                    txtEngineeringEvaluation1.setEditable(false);
                    if (DBService.getInstance().getRole().equals("admins")) {
                        cmbEmployeeInfo1.setDisable(false);
                        cmbRMAStatus1.setDisable(false);
                        cmbCreditReplaceRepair1.setDisable(false);
                        cmbReasonCode1.setDisable(false);
                        btnSpecialInstructionEdit1.setDisable(false);
                        cmbProductDetail1.setDisable(false);
                        btnReturnLabelTrackingEdit1.setDisable(false);
                        btnQuantityEdit1.setDisable(false);
                        btnInitialEvaluationEdit1.setDisable(false);
                        cmbDisposition1.setDisable(false);
                        btnDispositionNotesEdit1.setDisable(false);
                        if (!model.getSelectedCreditReplaceRepair().equals("Credit")) {
                            btnReplacementRepairTrackingEdit1.setDisable(false);
                            dpReplacementRepairDate1.setDisable(false);
                            cbReplacementRepairShip1.setDisable(false);
                        }
                    }
                } catch (SQLException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText(
                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                        e.getLocalizedMessage() + System.lineSeparator() +
                        "Please click \"Save\" to try again."
                    );
                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    a.showAndWait();
                }
            }
        }
    }

    /** btnDispositionNotesEdit1OnAction first asks the user whether they want to edit the {@link TextArea}, and if so,
     *  changes the txtDispositionNotes1 {@link TextArea} to editable, the "Edit" button to "Save", and disables
     *  all other modifiable (or used to modify) controls while the editing is in progress. Once the user clicks on
     *  "Save", the new contents of the {@link TextArea} will be pushed to the database, the disabled controls will be
     *  re-enabled, and the button text will revert back to "Edit", along with changing the {@link TextArea} back to
     *  non-editable.
     * (Requirement 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3)
     * @param event The {@link ActionEvent} that was passed to this method.
     */
    @FXML
    protected void btnDispositionNotesEdit1OnAction(ActionEvent event) {
        if (btnDispositionNotesEdit1.getText().equals("Edit")) {
            // Ask the user if they want to edit the field.
            if (editConfirmationMessage(btnDispositionNotesEdit1, "Disposition Notes", "Save")) {
                // Set field to be editable and disable the other modifiable (or used to modify) controls.
                txtDispositionNotes1.setEditable(true);
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
                if (!model.getSelectedCreditReplaceRepair().equals("Credit")) {
                    btnReplacementRepairTrackingEdit1.setDisable(true);
                    dpReplacementRepairDate1.setDisable(true);
                    cbReplacementRepairShip1.setDisable(true);
                }
            }
        } else {
            // Ask the user if they want to save the field.
            if (editConfirmationMessage(btnDispositionNotesEdit1, "Disposition Notes", "Edit")) {
                try {
                    // Update the field value in the database.
                    DBService.getInstance().updateRMADispositionNotes(model.getRmaId(), model.getDispositionNotes());
                    updateLastModifiedDetails();
                    model.updateRMAProgress();

                    // Set field to be non-editable and enable the other modifiable (or used to modify) controls.
                    txtDispositionNotes1.setEditable(false);
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
                    if (!model.getSelectedCreditReplaceRepair().equals("Credit")) {
                        btnReplacementRepairTrackingEdit1.setDisable(false);
                        dpReplacementRepairDate1.setDisable(false);
                        cbReplacementRepairShip1.setDisable(false);
                    }
                } catch (SQLException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText(
                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                        e.getLocalizedMessage() + System.lineSeparator() +
                        "Please click \"Save\" to try again."
                    );
                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    a.showAndWait();
                }
            }
        }
    }

    /** btnReplacementRepairTrackingEdit1OnAction first asks the user whether they want to edit the {@link TextField}, and if
     *  so, changes the txtReplacementRepairTracking1 {@link TextField} to editable, the "Edit" button to "Save", and disables all other
     *  modifiable (or used to modify) controls while the editing is in progress. Once the user clicks on "Save", the
     *  new contents of the {@link TextField} will be pushed to the database, the disabled controls will be re-enabled,
     *  and the button text will revert back to "Edit", along with changing the {@link TextField} back to non-editable.
     * (Requirement 5.2.1, 5.2.1.1, 5.2.1.2, 5.2.1.3)
     * @param event The {@link ActionEvent} that was passed to this method.
     */
    @FXML
    protected void btnReplacementRepairTrackingEdit1OnAction(ActionEvent event) {
        if (btnReplacementRepairTrackingEdit1.getText().equals("Edit")) {
            // Ask the user if they want to edit the field.
            if (editConfirmationMessage(btnReplacementRepairTrackingEdit1, "Replacement/Repair Tracking #", "Save")) {
                // Set field to be editable and disable the other modifiable (or used to modify) controls.
                txtReplacementRepairTracking1.setEditable(true);
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
                cbReplacementRepairShip1.setDisable(true);
            }
        } else {
            // Ask the user if they want to save the field.
            if (editConfirmationMessage(btnReplacementRepairTrackingEdit1, "Replacement/Repair Tracking #", "Edit")) {
                try {
                    // Update the field value in the database.
                    DBService.getInstance().updateRMAReplacementTrackingNumber(model.getRmaId(), model.getReplacementTrackingNumber());
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
                    cbReplacementRepairShip1.setDisable(false);
                } catch (SQLException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText(
                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                        e.getLocalizedMessage() + System.lineSeparator() +
                        "Please click \"Save\" to try again."
                    );
                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    a.showAndWait();
                }
            }
        }
    }

    /** editConfirmationMessage displays the confirmation message when the user clicks an "Edit" or "Save" {@link Button}
     *  on the form.
     * @param btnName A reference to the {@link Button} in order to edit its text.
     * @param name The {@link String} name of the button.
     * @param editSave The resulting {@link String} text to set to the button if the user clicks "Yes".
     * @return True if the confirmation message was accepted, or false if cancelled.
     */
    private boolean editConfirmationMessage(Button btnName, String name, String editSave) {
        String msg = "";
        if (btnName.getText().equals("Save"))
            msg = "Would you like to save the changes to the ";
        else
            msg = "Would you like to edit the ";

        ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button context from OK to Confirm

        Alert a = new Alert(Alert.AlertType.CONFIRMATION,msg + name + "?", confirm, ButtonType.CANCEL);
        a.setTitle(editSave.equals("Save") ? "Confirm Edit" : "Confirm Save");
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = a.showAndWait();

        if (result.isPresent() && result.get() == confirm) {
            btnName.setText(editSave);
            return true;
        } else
            return false;
    }

    /** updateLastModifiedDetails updates the Last Modified username and {@link LocalDateTime}.
     * @throws SQLException If there is an error connecting to the database or with the SQL query
     */
    private void updateLastModifiedDetails() throws SQLException {
        model.setLastModified(LocalDateTime.now());
        model.setLastModifiedBy(DBService.getInstance().getUser());
        DBService.getInstance().updateRMALastModified(model.getRmaId());
        DBService.getInstance().updateRMALastModifiedBy(model.getRmaId());
    }
}