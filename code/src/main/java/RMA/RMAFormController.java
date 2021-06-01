package RMA;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/** RMAFormController contains the logic and field references to the GUI controls on the RMAForm.
 */
public class RMAFormController implements Initializable {
    /**
     * lblRMANumberValue displays the assigned RMA number on save.
     */
    @FXML
    private Label lblRMANumberValue;
    /**
     * cmbCustomerName contains list of customer names in the database.
     * (Requirement 4.1)
     */
    @FXML
    private ComboBox<String> cmbCustomerName;
    /**
     * cmbBusinessName contains the list of business names for the selected customer name in the database.
     * (Requirement 4.2)
     */
    @FXML
    private ComboBox<CustomerAddress> cmbBusinessName;
    /**
     * cmbPoNumber contains the PO numbers associated with the selected business name.
     * (Requirement 4.11)
     */
    @FXML
    private ComboBox<String> cmbPoNumber;
    /**
     * txtCustomerInfoShip is used to show the shipping address associated with the selected business,
     * and auto populates when business name is selected.
     * (Requirement 4.12)
     */
    @FXML
    private TextArea txtShippingAddress;
    /**
     * lblOwnerValue shows the employee username assigned to the RMA upon saving.
     */
    @FXML
    private Label lblOwnerValue;
    /**
     * cmbReasonCode contains the list of return reason codes in the database.
     * (Requirement 4.3)
     */
    @FXML
    private ComboBox<String> cmbReasonCode;
    /**
     * cmbCreditReplaceRepair contains the values Credit, Replace, and Repair, used to determine which action we
     * will be taking on the return.
     * (Requirement 4.4)
     */
    @FXML
    private ComboBox<String> cmbCreditReplaceRepair;
    /**
     * cmbRMAStatus contains list of RMA Statuses in the database.
     * (Requirement 4.5)
     */
    @FXML
    private ComboBox<String> cmbRMAStatus;
    /**
     * txtAddInfoSpecialInt is used to store any additional instructions about the return entered by the user.
     * (Requirement 4.13)
     */
    @FXML
    private TextArea txtAddInfoSpecialInt;
    /**
     * cmbProduct contains the list of {@link PurchaseOrderProduct}s for the chosen PO number in the database.
     * (Requirement 4.7)
     */
    @FXML
    private ComboBox<PurchaseOrderProduct> cmbProduct;
    /**
     * txtReturnLabelTrack is used to store the return label tracking number.
     * (Requirement 4.7.2)
     */
    @FXML
    private TextField txtReturnLabelTrack;
    /**
     * txtQuantity is used to enter the customer's return quantity.
     * (Requirement 4.7.3)
     */
    @FXML
    private TextField txtQuantity;
    /**
     * txtInitialEvaluation allows the user to enter in their initial evaluation of the returned product(s).
     * (Requirement 4.8)
     */
    @FXML
    private TextArea txtInitialEvaluation;
    /**
     * cmbDisposition contains the list of disposition in the database.
     * (Requirement 4.9)
     */
    @FXML
    private ComboBox<String> cmbDisposition;
    /**
     * txtDispositionNotes allows the user to enter in notes about the chosen disposition.
     * (Requirement 4.9.2)
     */
    @FXML
    private TextArea txtDispositionNotes;
    /**
     * txtReplaceRepairTracking allow to enter the tracking number for the replacement or repair being shipped
     * back to the customer.
     * (Requirement 4.14)
     */
    @FXML
    private TextField txtReplaceRepairTracking;
    /**
     * dtpkrReplaceRepairShipDate allows the user to choose the date when we will be shipping
     * back the replacement or repair.
     * Requirement 4.15)
     */
    @FXML
    private DatePicker dtpkrReplaceRepairShipDate;
    /**
     * cbShipIndicator indicates that the return/replacement has been shipped.
     * (Requirement 4.16)
     */
    @FXML
    private CheckBox cbShipIndicator;
    /**
     * btnSave saves the RMA to the database.
     * (Requirement 4.10)
     */
    @FXML
    private Button btnSave;
    /**
     * btnSaveClose saves the RMA to the database and then closes the form.
     * (Requirement 4.10)
     */
    @FXML
    private Button btnSaveClose;
    /**
     * btnCancel closes the form without saving.
     * (Requirement 4.10)
     */
    @FXML
    private Button btnCancel;
    /**
     * model is the data model for the RMAForm.
     */
    private RMAFormModel model;
    /**
     * customerConnectionError is used to track whether the user had a connection error while selecting a value for the Customer
     * so that selecting the same value will allow it to try again.
     * (Requirement 4.1.2)
     */
    private boolean customerConnectionError;
    /** customerRevert is used to revert back to the old Customer selection without prompting again.
     */
    private boolean customerRevert;
    /**
     * businessConnectionError is used to track whether the user had a connection error while selecting a value for the Business,
     * so that selecting the same value will allow it to try again.
     * (Requirement 4.2.2)
     */
    private boolean businessConnectionError;
    /** businessRevert is used to revert back to the old Business selection without prompting again.
     */
    private boolean businessRevert;
    /**
     * poConnectionError is used to track whether the user had a connection error while selecting a value for the PO Number,
     * so that selecting the same value will allow it to try again.
     * (Requirement 4.11.3)
     */
    private boolean poConnectionError;
    /** poRevert is used to revert back to the old PO Number selection without prompting again.
      */
    private boolean poRevert;


    /** Default empty constructor, used when {@link javafx.fxml.FXMLLoader} loads the fxml file.
     */
    public RMAFormController() {}

    /**
     * initialize ties together the GUI's elements with the RMAFormModel and
     * creates any necessary {@link ChangeListener}s on the controls.
     * (Requirement 4.1.1, 4.2.1, 4.4.1, 4.4.1.1, 4.7.1, 4.7.3.1, 4.9.1, 4.11.1, 4.11.2, 4.12.1,
     *  4.18, 4.18.1, 4.18.2., 4.18.3, 4.18.4)
     *
     * @param url The {@link URL} location of the fxml file that describes
     *            the RMAForm window elements and their layout.
     * @param rb  {@link ResourceBundle} that contains locale-specific data.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // First initialize a new RMAFormModel.
        model = new RMAFormModel();

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
        lblRMANumberValue.textProperty().bindBidirectional(model.rmaIDProperty());
        cmbCustomerName.setItems(model.customerNamesProperty());
        cmbCustomerName.valueProperty().bindBidirectional(model.selectedCustomerNameProperty());
        cmbBusinessName.setItems(model.businessNamesProperty());
        cmbBusinessName.valueProperty().bindBidirectional(model.selectedBusinessNameProperty());
        cmbBusinessName.setDisable(true);
        cmbPoNumber.setItems(model.poNumbersProperty());
        cmbPoNumber.valueProperty().bindBidirectional(model.selectedPONumberProperty());
        cmbPoNumber.setDisable(true);
        txtShippingAddress.textProperty().bind(model.shippingAddressProperty().asString());
        txtShippingAddress.setEditable(false);
        cmbReasonCode.setItems(model.returnReasonCodesProperty());
        cmbReasonCode.valueProperty().bindBidirectional(model.selectedReturnReasonCodeProperty());
        cmbCreditReplaceRepair.setItems(model.creditReplaceRepairProperty());
        cmbCreditReplaceRepair.valueProperty().bindBidirectional(model.selectedCreditReplaceRepairProperty());
        cmbRMAStatus.setItems(model.rmaStatusesProperty());
        cmbRMAStatus.valueProperty().bindBidirectional(model.selectedRMAStatusProperty());
        txtAddInfoSpecialInt.textProperty().bindBidirectional(model.additionalInfoProperty());
        cmbProduct.setItems(model.productsProperty());
        cmbProduct.valueProperty().bindBidirectional(model.selectedProductProperty());
        cmbProduct.setDisable(true);
        txtReturnLabelTrack.textProperty().bindBidirectional(model.returnLabelTrackerProperty());
        txtQuantity.textProperty().bindBidirectional(model.returnQuantityProperty(), new NumberStringConverter());
        txtQuantity.setTextFormatter(textFormatter);
        txtInitialEvaluation.textProperty().bindBidirectional(model.initialEvaluationProperty());
        cmbDisposition.setItems(model.dispositionsProperty());
        cmbDisposition.valueProperty().bindBidirectional(model.selectedDispositionProperty());
        txtDispositionNotes.textProperty().bindBidirectional(model.dispositionNotesProperty());
        txtReplaceRepairTracking.textProperty().bindBidirectional(model.replacementTrackingNumberProperty());
        dtpkrReplaceRepairShipDate.valueProperty().bindBidirectional(model.replacementShipDateProperty());
        cbShipIndicator.selectedProperty().bindBidirectional(model.shipReplacementRepairProperty());
        customerConnectionError = false;
        customerRevert = false;
        businessConnectionError = false;
        businessRevert = false;
        poConnectionError = false;
        poRevert = false;


        // Add ChangeListeners to Customer, Business Name, PO Number, and CreditReplaceRepair ComboBoxes.
        cmbCustomerName.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    /** changed checks the new and previous value to decide whether to clear the Business Name,
                     *  PO Number, Shipping Address info, and Product after picking a Customer Name.
                     * @param observableValue The {@link ObservableValue} variable that changed.
                     * @param oldValue The old {@link String} value of the {@link ObservableValue}.
                     * @param newValue The new {@link String} value of the {@link ObservableValue}.
                     */
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                        if (newValue != null && oldValue != null) { // They chose another Customer Name.
                            if (!newValue.equals(oldValue)) { // They chose a different Customer Name.
                                // Confirm if user wants to reset their choices.
                                if (!customerRevert && model.getSelectedBusinessName() != null) { // They selected a Business Name before selecting a different Customer Name.
                                    ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button text from OK to Confirm
                                    Alert a = new Alert(
                                            Alert.AlertType.CONFIRMATION,
                                            "Business Name, PO Number, Shipping Address, and Product will be cleared! " +
                                                    "Would you like to continue?",
                                            confirm,
                                            ButtonType.CANCEL
                                    );
                                    a.setTitle("Reset Business Name, PO Number, Shipping Address, and Product");
                                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                    Optional<ButtonType> result = a.showAndWait();

                                    if (result.isPresent() && result.get() == confirm) {
                                        // Clear it backwards so that other change listeners don't fire.
                                        model.setSelectedProduct(null);
                                        model.clearProducts();
                                        cmbProduct.setDisable(true);

                                        model.setShippingAddress(CustomerAddress.EMPTY_ADDRESS);

                                        model.setSelectedPONumber(null);
                                        model.clearPONumbers();
                                        cmbPoNumber.setDisable(true);
                                        poConnectionError = false;

                                        model.setSelectedBusinessName(null);
                                        model.clearBusinessNames();
                                        try { // Populate cmbBusinessName
                                            model.businessNamesProperty().setAll(DBService.getInstance().getCustomerBusinessNames(newValue));
                                            customerConnectionError = false;
                                        } catch (SQLException e) {
                                            Alert error = new Alert(Alert.AlertType.ERROR);
                                            error.setContentText(
                                                    "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                                            e.getLocalizedMessage() + System.lineSeparator() +
                                                            "Please select the value again to try again."
                                            );
                                            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                            error.showAndWait();
                                            customerConnectionError = true;
                                        } finally {
                                            businessConnectionError = false;
                                        }
                                    } else { // Reset the value to oldValue
                                        customerRevert = true;
                                        model.setSelectedCustomerName(oldValue);
                                    }
                                } else if (!customerRevert) { // They selected another Customer Name and did not select a Business Name beforehand.
                                    model.clearBusinessNames();
                                    try { // Populate cmbBusinessName
                                        model.businessNamesProperty().setAll(DBService.getInstance().getCustomerBusinessNames(newValue));
                                        customerConnectionError = false;
                                    } catch (SQLException e) {
                                        Alert error = new Alert(Alert.AlertType.ERROR);
                                        error.setContentText(
                                            "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                            e.getLocalizedMessage() + System.lineSeparator() +
                                            "Please select the value again to try again."
                                        );
                                        error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                        error.showAndWait();
                                        customerConnectionError = true;
                                    }
                                } else // Let change go through and reset customerRevert.
                                    customerRevert = false;
                            }
                        } else if (newValue != null) { // They chose a Customer Name and oldValue was null.
                            try { // Populate cmbBusinessName
                                model.businessNamesProperty().setAll(DBService.getInstance().getCustomerBusinessNames(newValue));
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
                    }
                }
        );

        cmbBusinessName.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<CustomerAddress>() {
                    /** changed checks the new and previous value to decide whether to clear PO Number,
                     *  Shipping Address info, and Product after picking a Business Name.
                     * @param observableValue The {@link ObservableValue} variable that changed.
                     * @param oldValue The old {@link String} value of the {@link ObservableValue}.
                     * @param newValue The new {@link String} value of the {@link ObservableValue}.
                     */
                    @Override
                    public void changed(ObservableValue<? extends CustomerAddress> observableValue, CustomerAddress oldValue, CustomerAddress newValue) {
                        if (newValue == null && oldValue != null) { // The value is being programmatically set, continue.
                            if (model.getSelectedCustomerName() == null) { // The entire form is being cleared.
                                model.clearBusinessNames();
                                cmbBusinessName.setDisable(true);
                                model.setShippingAddress(CustomerAddress.EMPTY_ADDRESS);
                            } else { // The customer name is being changed.
                                try { // Populate cmbBusinessName
                                    model.clearBusinessNames();
                                    model.setSelectedBusinessName(null);
                                    model.businessNamesProperty().setAll(DBService.getInstance().getCustomerBusinessNames(model.getSelectedCustomerName()));
                                    cmbBusinessName.setDisable(false);
                                    businessConnectionError = false;
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
                                    businessConnectionError = true;
                                    customerConnectionError = true;
                                }
                            }
                        } else if (newValue != null && oldValue != null) { // They chose another Business Name.
                            if (!newValue.equals(oldValue)) { // They chose a different Business Name.
                                // Confirm if user wants to reset their choices.
                                if (!businessRevert && !model.getSelectedPONumber().isEmpty()) {  // The user selected a PO Number before selecting a different Business Name.
                                    ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button text from OK to Confirm
                                    Alert a = new Alert(
                                        Alert.AlertType.CONFIRMATION,
                                        "PO Number and Product will be cleared! Would you like to continue?",
                                        confirm,
                                        ButtonType.CANCEL
                                    );
                                    a.setTitle("Reset PO Number and Product");
                                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                    Optional<ButtonType> result = a.showAndWait();

                                    if (result.isPresent() && result.get().equals(confirm)) {
                                        // Clear it backwards so that other change listeners don't fire.
                                        model.setSelectedProduct(null);
                                        model.clearProducts();
                                        cmbProduct.setDisable(true);

                                        model.setShippingAddress(newValue.createShippingAddress());

                                        model.setSelectedPONumber(null);
                                        model.clearPONumbers();
                                        try { // Populate cmbPoNumber
                                            model.poNumbersProperty().setAll(DBService.getInstance().getCustomerAddressPONumbers(model.getSelectedBusinessName()));
                                            businessConnectionError = false;
                                        } catch (SQLException e) {
                                            Alert error = new Alert(Alert.AlertType.ERROR);
                                            error.setContentText(
                                                    "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                                            e.getLocalizedMessage() + System.lineSeparator() +
                                                            "Please select the value again to try again."
                                            );
                                            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                            error.showAndWait();
                                            businessConnectionError = true;
                                        } finally {
                                            poConnectionError = false;
                                        }
                                    } else { // Reset to the old value.
                                        businessRevert = true;
                                        model.setSelectedBusinessName(oldValue);
                                    }
                                } else if (!businessRevert) { // User selected another Business Name and did not select a PO Number beforehand.
                                    model.setShippingAddress(newValue.createShippingAddress());
                                    model.clearPONumbers();
                                    try { // Populate cmbPoNumber
                                        model.poNumbersProperty().setAll(DBService.getInstance().getCustomerAddressPONumbers(model.getSelectedBusinessName()));
                                        businessConnectionError = false;
                                    } catch (SQLException e) {
                                        Alert a = new Alert(Alert.AlertType.ERROR);
                                        a.setContentText(
                                                "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                                        e.getLocalizedMessage() + System.lineSeparator() +
                                                        "Please select the value again to try again."
                                        );
                                        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                        a.showAndWait();
                                        businessConnectionError = true;
                                    }
                                } else // Let change go through and reset businessRevert
                                    businessRevert = false;
                            }
                        } else if (newValue != null) { // They chose a Business Name and oldValue was null.
                            model.setShippingAddress(newValue.createShippingAddress());
                            try { // Populate cmbPoNumber
                                model.poNumbersProperty().setAll(DBService.getInstance().getCustomerAddressPONumbers(model.getSelectedBusinessName()));
                                cmbPoNumber.setDisable(false);
                                businessConnectionError = false;
                            } catch (SQLException e) {
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setContentText(
                                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                                e.getLocalizedMessage() + System.lineSeparator() +
                                                "Please select the value again to try again."
                                );
                                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                a.showAndWait();
                                businessConnectionError = true;
                            }
                        }
                    }
                }
        );

        cmbPoNumber.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    /** changed checks the new and previous value to decide whether to clear Product after picking a PO Number.
                     * @param observableValue The {@link ObservableValue} variable that changed.
                     * @param oldValue The old {@link String} value of the {@link ObservableValue}.
                     * @param newValue The new {@link String} value of the {@link ObservableValue}.
                     */
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                        if (newValue == null && oldValue != null) { // The value is being programmatically set, continue.
                            if (model.getSelectedBusinessName() == null) { // The form is being cleared or business name was changed.
                                model.clearPONumbers();
                                cmbPoNumber.setDisable(true);

                                model.setSelectedProduct(null);
                                model.clearProducts();
                                cmbProduct.setDisable(true);

                                poConnectionError = false;
                            } else { // The user selected another business name.
                                try { // Populate cmbPoNumber
                                    model.setSelectedProduct(null);
                                    model.clearProducts();
                                    cmbProduct.setDisable(true);

                                    model.poNumbersProperty().setAll(DBService.getInstance().getCustomerAddressPONumbers(model.getSelectedBusinessName()));
                                    cmbPoNumber.setDisable(false);
                                    businessConnectionError = false;
                                    poConnectionError = false;
                                } catch (SQLException e) {
                                    Alert a = new Alert(Alert.AlertType.ERROR);
                                    a.setContentText(
                                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                        e.getLocalizedMessage() + System.lineSeparator() +
                                        "Please select the value again to try again."
                                    );
                                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                    a.showAndWait();
                                    businessConnectionError = true;
                                    poConnectionError = true;
                                }
                            }
                        } else if (newValue != null && oldValue != null) { // They chose another PO nNumber.
                            if (!newValue.equals(oldValue)) { // They chose a different PO number.
                                // Confirm if user wants to reset their choices.
                                if (!poRevert && model.getSelectedProduct() != null) { // The user selected a Product before selecting a different PO Number
                                    ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button text from OK to Confirm
                                    Alert a = new Alert(
                                            Alert.AlertType.CONFIRMATION,
                                            "Product will be cleared! Would you like to continue?",
                                            confirm,
                                            ButtonType.CANCEL
                                    );
                                    a.setTitle("Reset Product");
                                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                    Optional<ButtonType> result = a.showAndWait();

                                    if (result.isPresent() && result.get() == confirm) {
                                        model.setSelectedProduct(null);
                                        model.clearProducts();
                                        try { // Populate cmbProducts
                                            model.productsProperty().setAll(DBService.getInstance().getPurchaseOrderProducts(newValue));
                                            poConnectionError = false;
                                        } catch (SQLException e) {
                                            Alert error = new Alert(Alert.AlertType.ERROR);
                                            error.setContentText(
                                                    "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                                            e.getLocalizedMessage() + System.lineSeparator() +
                                                            "Please select the value again to try again."
                                            );
                                            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                            error.showAndWait();
                                            poConnectionError = true;
                                        }
                                    } else { // Set PO Number back to oldValue
                                        poRevert = true;
                                        model.setSelectedPONumber(oldValue);
                                    }
                                } else if (!poRevert) { // The user selected another PO Number and did not selected a Product beforehand.
                                    model.clearProducts();
                                    try { // Populate cmbProducts
                                        model.productsProperty().setAll(DBService.getInstance().getPurchaseOrderProducts(newValue));
                                        poConnectionError = false;
                                    } catch (SQLException e) {
                                        Alert a = new Alert(Alert.AlertType.ERROR);
                                        a.setContentText(
                                            "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                            e.getLocalizedMessage() + System.lineSeparator() +
                                            "Please select the value again to try again."
                                        );
                                        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                        a.showAndWait();
                                        poConnectionError = true;
                                    }
                                } else // Let the change go through and reset poRevert.
                                    poRevert = false;
                            }
                        } else if (newValue != null) { // They chose a PO number and oldValue was null.
                            try { // Populate cmbProducts
                                model.productsProperty().setAll(DBService.getInstance().getPurchaseOrderProducts(newValue));
                                cmbProduct.setDisable(false);
                                poConnectionError = false;
                            } catch (SQLException e) {
                                Alert a = new Alert(Alert.AlertType.ERROR);
                                a.setContentText(
                                    "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                    e.getLocalizedMessage() + System.lineSeparator() +
                                    "Please select the value again to try again."
                                );
                                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                a.showAndWait();
                                poConnectionError = true;
                            }
                        }
                    }
                }
        );

        cmbCreditReplaceRepair.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                /** changed checks the new and previous value to decide whether to clear and
                 *  disable the fields under Replacement Information, or re-enable them,
                 *  depending on whether the user selects the "Credit" option.
                 * @param observableValue The {@link ObservableValue} variable that changed.
                 * @param oldValue        The old {@link String} value of the {@link ObservableValue}.
                 * @param newValue        The new {@link String} value of the {@link ObservableValue}.
                 */
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    // First handle the user selecting "Credit".
                    if (newValue != null) // null is passed in when getSelectionModel().clearSelection() is called.
                        if (newValue.equals("Credit")) {
                            // Check any of the Replacement Information fields are populated.
                            if (
                               (!model.getReplacementTrackingNumber().isEmpty() && !model.getReplacementTrackingNumber().isBlank()) ||
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
                                    model.setReplacementTrackingNumber("");
                                    txtReplaceRepairTracking.setDisable(true);
                                    model.setReplacementShipDate(null);
                                    dtpkrReplaceRepairShipDate.setDisable(true);
                                    model.setShipReplacementRepair(false);
                                    cbShipIndicator.setDisable(true);
                                } else // Set value back to empty using Platform.runLater() or to its previous value.
                                    if (oldValue == null)
                                        Platform.runLater(() -> cmbCreditReplaceRepair.getSelectionModel().clearSelection());
                                    else
                                        model.setSelectedCreditReplaceRepair(oldValue);
                            } else { // Disable the controls.
                                txtReplaceRepairTracking.setDisable(true);
                                dtpkrReplaceRepairShipDate.setDisable(true);
                                cbShipIndicator.setDisable(true);
                            }
                        } else { // Re-enable the controls.
                            txtReplaceRepairTracking.setDisable(false);
                            dtpkrReplaceRepairShipDate.setDisable(false);
                            cbShipIndicator.setDisable(false);
                        }
                }
            }
        );
    }

    /**
     * cmbCustomerNameOnHidden checks, after the dropdown menu closes, whether the user had experienced a
     * customerConnectionError, and if so, attempts the query again to populate Business Name.
     *
     * @param event The {@link Event} passed to the method.
     */
    @FXML
    public void cmbCustomerNameOnHidden(Event event) {
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

    /**
     * cmbBusinessNameOnHidden checks, after the dropdown menu closes, whether the user had experienced a
     * businessConnectionError, and if so, attempts the query again to populate PO Number.
     *
     * @param event The {@link Event} passed to the method.
     */
    @FXML
    public void cmbBusinessNameOnHidden(Event event) {
        if (businessConnectionError) // Attempt the connection again if the user got an error last time.
            try { // Populate cmbPoNumber
                model.setSelectedPONumber(null);
                model.poNumbersProperty().setAll(DBService.getInstance().getCustomerAddressPONumbers(model.getSelectedBusinessName()));
                cmbPoNumber.setDisable(false);
                businessConnectionError = false;
            } catch (SQLException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(
                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                e.getLocalizedMessage() + System.lineSeparator() +
                                "Please select the value again to try again."
                );
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
                businessConnectionError = true;
            }
    }

    /**
     * cmbPONumberOnHidden checks, after the dropdown menu closes, whether the user had experienced a
     * poConnectionError, and if so, attempts the query again to populate Product.
     *
     * @param event The {@link Event} passed to the method.
     */
    @FXML
    public void cmbPONumberOnHidden(Event event) {
        if (poConnectionError) // Attempt the connection again if the user got an error last time.
            try { // Populate cmbProducts
                model.setSelectedProduct(null);
                model.clearProducts();
                model.productsProperty().setAll(DBService.getInstance().getPurchaseOrderProducts(model.getSelectedPONumber()));
                cmbProduct.setDisable(false);
                poConnectionError = false;
            } catch (SQLException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(
                        "Error while connecting to SQL Server database!" + System.lineSeparator() +
                                e.getLocalizedMessage() + System.lineSeparator() +
                                "Please select the value again to try again."
                );
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
                poConnectionError = true;
            }
    }


    /**
     * btnCancelOnAction closes the screen without saving.
     * (Requirement 4.10.1, 4.10.2)
     * @param event The {@link ActionEvent} that is passed to the method.
     */
    @FXML
    protected void btnCancelOnAction(ActionEvent event) {
        // Ask the user if they want to cancel and close the form.
        ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button context from OK to Confirm
        Alert a = new Alert(
                Alert.AlertType.CONFIRMATION,
                "All data input will be lost and the form will be closed! Would you like to continue?",
                confirm,
                ButtonType.CANCEL
        );
        a.setTitle("Clear and Close Form without Saving");
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = a.showAndWait();

        // Clear and close the form.
        if (result.isPresent() && result.get() == confirm) {
            model.setSelectedCustomerName(null);
            model.setSelectedBusinessName(null);
            model.clearBusinessNames();
            model.setSelectedPONumber(null);
            model.clearPONumbers();
            model.setSelectedProduct(null);
            model.setShippingAddress(CustomerAddress.EMPTY_ADDRESS);
            model.setSelectedReturnReasonCode("");
            model.setSelectedCreditReplaceRepair("");
            model.setSelectedRMAStatus("");
            model.setAdditionalInfo("");
            model.setSelectedProduct(null);
            model.clearProducts();
            model.setReturnQuantity(0);
            model.setReturnLabelTracker("");
            model.setInitialEvaluation("");
            model.setSelectedDisposition("");
            model.setDispositionNotes("");
            model.setReplacementTrackingNumber("");
            model.setReplacementShipDate(null);
            model.setShipReplacementRepair(false);
            btnCancel.getScene().getWindow().hide();
        }
    }

    /**
     * btnSaveOnAction Saves the RMA and all the data into the database.
     * (Requirement 4.6, 4.10.1, 4.10.2)
     * @param event The {@link ActionEvent} that is passed to the method.
     */
    @FXML
    protected void btnSaveOnAction(ActionEvent event) {
        Alert miss = new Alert(Alert.AlertType.INFORMATION,
                "One or more of the required fields (with an asterisk * by the name) are missing input. Please complete before continuing."
        );
        miss.setTitle("Required Field Missing!");
        miss.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Alert save = new Alert(Alert.AlertType.INFORMATION, "New RMA created and saved.");
        save.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        save.setTitle("RMA Save Confirmed");

        // Check if all required fields are filled in.
        if (!model.getSelectedCustomerName().isEmpty() &&
             model.getSelectedBusinessName() != null &&
            !model.getSelectedPONumber().isEmpty() &&
            !model.getSelectedReturnReasonCode().isEmpty() &&
            !model.getSelectedCreditReplaceRepair().isEmpty() &&
            !model.getSelectedRMAStatus().isEmpty() &&
             model.getSelectedProduct() != null &&
             model.getReturnQuantity() > 0
        ) {
            // Ask the user if they want to save the RMA before processing.
            ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button context from OK to Confirm
            Alert a = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Would you like to save the RMA?",
                    confirm,
                    ButtonType.CANCEL
            );
            a.setTitle("Save RMA Verification");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            Optional<ButtonType> result = a.showAndWait();

            if (result.isPresent() && result.get() == confirm)
                try {
                    // Save RMA into the database.
                    DBService dbs = DBService.getInstance();
                    model.setRmaId(dbs.createRMA(model.getSelectedRMAStatus()));
                    dbs.createRMADetails(
                        model.getRmaId(),
                        model.getSelectedReturnReasonCode().substring(0, model.getSelectedReturnReasonCode().indexOf(" ")),
                        model.getSelectedCreditReplaceRepair(),
                        model.getSelectedProduct().getPurchaseOrderProductId(),
                        model.getReturnQuantity(),
                        model.getReturnLabelTracker(),
                        model.getAdditionalInfo(),
                        model.getSelectedPONumber(),
                        model.getInitialEvaluation(),
                        "", // engineeringEvaluation
                        model.getSelectedDisposition(),
                        model.getDispositionNotes(),
                        model.getReplacementTrackingNumber(),
                        model.getReplacementShipDate(),
                        model.getShipReplacementRepair()
                    );
                    lblOwnerValue.setText(dbs.getUser());
                    save.showAndWait();
                } catch (SQLException e) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Error while connecting to SQL Server database!" + System.lineSeparator() + e.getLocalizedMessage());
                    error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    error.show();
                }
        } else
            miss.showAndWait();
    }

    /**
     * btnSaveCloseAction Saves the RMA and all the data into the database and closes the form.
     * (Requirement 4.6, 4.10.1, 4.10.2)
     * @param event The {@link ActionEvent} that is passed to the method.
     */
    @FXML
    protected void btnSaveCloseAction(ActionEvent event) {
        Alert miss = new Alert(Alert.AlertType.INFORMATION,
                "One or more of the required fields (with an asterisk * by the name) are missing input. Please complete before continuing."
        );
        miss.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        miss.setTitle("Required Field Missing!");

        Alert save = new Alert(Alert.AlertType.INFORMATION, "New RMA created and saved.");
        save.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        save.setTitle("RMA Save Confirmed");

        if (!model.getSelectedCustomerName().isEmpty() &&
                model.getSelectedBusinessName() != null &&
                !model.getSelectedPONumber().isEmpty() &&
                !model.getSelectedReturnReasonCode().isEmpty() &&
                !model.getSelectedCreditReplaceRepair().isEmpty() &&
                !model.getSelectedRMAStatus().isEmpty() &&
                model.getSelectedProduct() != null &&
                model.getReturnQuantity() > 0
        ) {
            // Ask the user if they want to save and close the RMA before processing.
            ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button context from OK to Confirm
            Alert a = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Would you like to save and close the RMA?",
                    confirm,
                    ButtonType.CANCEL
            );
            a.setTitle("Save and Close RMA Verification");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            Optional<ButtonType> result = a.showAndWait();

            if (result.isPresent() && result.get() == confirm)
                try {
                    // Save RMA into the database.
                    DBService dbs = DBService.getInstance();
                    model.setRmaId(dbs.createRMA(model.getSelectedRMAStatus()));
                    dbs.createRMADetails(
                        model.getRmaId(),
                        model.getSelectedReturnReasonCode().substring(0, model.getSelectedReturnReasonCode().indexOf(" ")),
                        model.getSelectedCreditReplaceRepair(),
                        model.getSelectedProduct().getPurchaseOrderProductId(),
                        model.getReturnQuantity(),
                        model.getReturnLabelTracker(),
                        model.getAdditionalInfo(),
                        model.getSelectedPONumber(),
                        model.getInitialEvaluation(),
                        "", // engineeringEvaluation
                        model.getSelectedDisposition(),
                        model.getDispositionNotes(),
                        model.getReplacementTrackingNumber(),
                        model.getReplacementShipDate(),
                        model.getShipReplacementRepair()
                    );
                    lblOwnerValue.setText(dbs.getUser());
                    save.showAndWait();
                    btnSaveClose.getScene().getWindow().hide();
                } catch(SQLException e) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Error while connecting to SQL Server database!" + System.lineSeparator() + e.getLocalizedMessage());
                    error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    error.show();
                }
        } else
            miss.showAndWait();
    }

    /** getInitialRMAOptions fetches the Customer names, Return Reason Codes, RMA Statuses, and Dispositions from the
     *  database.
     *  (Requirement 4.5.1)
     * @throws SQLException If there is an issue connecting to the database.
     */
    public void getInitialRMAOptions() throws SQLException {
        // First create an instance of DBService.
        DBService dbs = DBService.getInstance();

        // Now populate dropdowns.
        model.customerNamesProperty().setAll(dbs.getCustomerNames());
        model.returnReasonCodesProperty().setAll(model.convertHashMapToArrayList(dbs.getReturnReasonCodes()));
        ArrayList<String> list = dbs.getRMAStatuses();
        list.remove("Closed");
        model.rmaStatusesProperty().setAll(list);
        model.dispositionsProperty().setAll(dbs.getDispositions());
    }
}