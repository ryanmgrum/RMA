package RMA;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/** RMAFormModel contains the fields and methods that make up the RMAForm model.
 */
public class RMAFormModel {
    /** rmaId contains the RMA ID to identify the RMA.
     */
    private StringProperty rmaId;
    /** created contains the date and time that the RMA was created.
     */
    private ObjectProperty<LocalDateTime> created;
    /** createdBy holds the username of the RMA creator.
     */
    private StringProperty createdBy;
    /** lastModified contains the date and time the RMA was last modified.
     */
    private ObjectProperty<LocalDateTime> lastModified;
    /** lastModifiedBy contains the username of the last person to modify the RMA record.
     */
    private StringProperty lastModifiedBy;
    /** selectedCustomerName contains the selected customer name.
     */
    private StringProperty selectedCustomerName;
    /** customerNames contains the {@link ObservableList} of {@link String} customer names.
     */
    private ObservableList<String> customerNames;
    /** selectedBusinessName contains the selected business name CustomerAddress of the selected customer name.
     */
    private ObjectProperty<CustomerAddress> selectedBusinessName;
    /** businessNames contains the {@link ObservableList} of {@link CustomerAddress} addresses for the selected
     *  customer name, with their showBusinessName set to true.
     */
    private ObservableList<CustomerAddress> businessNames;
    /** shippingAddress contains the the business's address.
     */
    private ObjectProperty<CustomerAddress> shippingAddress;
    /** selectedRMAStatus contains the selected RMA Status for the RMA.
     */
    private StringProperty selectedRMAStatus;
    /** rmaStatuses contains the {@link ObservableList} of {@link String} RMA statuses for the RMA.
     */
    private ObservableList<String> rmaStatuses;
    /** selectedPONumber contains the selected PO number from the available PO numbers for the selected business name.
     */
    private StringProperty selectedPONumber;
    /** poNumbers contains the {@link ObservableList} of {@link String}s containing the available PO numbers for the
     *  given business name.
     */
    private ObservableList<String> poNumbers;
    /** selectedReturnReasonCode contains the selected Return Reason Code.
     */
    private StringProperty selectedReturnReasonCode;
    /** returnReasonCodes contains the {@link ObservableList} of {@link String} Return Reason Codes.
     */
    private ObservableList<String> returnReasonCodes;
    /** selectedCreditReplaceRepair contains the selected Credit, Replace, or Repair option.
     */
    private StringProperty selectedCreditReplaceRepair;
    /** creditReplaceRepair contains the {@link ObservableList} of {@link String}s, specifically Credit, Replace, and
     *  Repair.
     */
    private ObservableList<String> creditReplaceRepair;
    /** additionalInfo contains any additional user-provided information about the RMA return.
     */
    private StringProperty additionalInfo;
    /** selectedProduct contains the selected PurchaseOrderProduct.
     */
    private ObjectProperty<PurchaseOrderProduct> selectedProduct;
    /** products contains the {@link ObservableList} of available PurchaseOrderProducts for the selected PO number.
     */
    private ObservableList<PurchaseOrderProduct> products;
    /** returnQuantity contains the number of products being returned.
     */
    private IntegerProperty returnQuantity;
    /** returnLabelTracker contains the customer-provided return label tracking number for the given RMA return.
     */
    private StringProperty returnLabelTracker;
    /** initialEvaluation contains the initial evaluation of the returned product(s) by an Analyst.
     */
    private StringProperty initialEvaluation;
    /** selectedDisposition contains the selected disposition description.
     */
    private StringProperty selectedDisposition;
    /** dispositions contains the {@link ObservableList} of {@link String}s containing disposition descriptions.
     */
    private ObservableList<String> dispositions;
    /** dispositionNotes contains any additional Analyst-provided notes about the chosen disposition.
     */
    private StringProperty dispositionNotes;
    /** replacementTrackingNumber contains the tracking number used to ship the replacement or repair back to the
     *  customer.
     */
    private StringProperty replacementTrackingNumber;
    /** replacementShipDate contains shipping date of the our replacement or return.
     */
    private ObjectProperty<LocalDate> replacementShipDate;
    /** shipReplacementRepair is used to indicate that we are shipping the replacement or repair.
     */
    private BooleanProperty shipReplacementRepair;


    /** Default constructor that initializes fields to empty values (besides creditReplaceRepair,
     *  which contains fixed values).
     */
    public RMAFormModel() {
        // Initialize properties
        rmaId = new SimpleStringProperty();
        created = new SimpleObjectProperty<LocalDateTime>();
        createdBy = new SimpleStringProperty();
        lastModified = new SimpleObjectProperty<LocalDateTime>();
        lastModifiedBy = new SimpleStringProperty();
        selectedCustomerName = new SimpleStringProperty();
        customerNames = FXCollections.observableArrayList();
        selectedBusinessName = new SimpleObjectProperty<CustomerAddress>();
        businessNames = FXCollections.observableArrayList();
        shippingAddress = new SimpleObjectProperty<CustomerAddress>(CustomerAddress.EMPTY_ADDRESS);
        selectedRMAStatus = new SimpleStringProperty();
        rmaStatuses = FXCollections.observableArrayList();
        selectedPONumber = new SimpleStringProperty();
        poNumbers = FXCollections.observableArrayList();
        selectedReturnReasonCode = new SimpleStringProperty();
        returnReasonCodes = FXCollections.observableArrayList();
        selectedCreditReplaceRepair = new SimpleStringProperty();
        creditReplaceRepair = FXCollections.observableArrayList("Credit", "Replace", "Repair");
        additionalInfo = new SimpleStringProperty();
        selectedProduct = new SimpleObjectProperty<PurchaseOrderProduct>();
        products = FXCollections.observableArrayList();
        returnQuantity = new SimpleIntegerProperty();
        returnLabelTracker = new SimpleStringProperty();
        initialEvaluation = new SimpleStringProperty();
        selectedDisposition = new SimpleStringProperty();
        dispositions = FXCollections.observableArrayList();
        dispositionNotes = new SimpleStringProperty();
        replacementTrackingNumber = new SimpleStringProperty();
        replacementShipDate = new SimpleObjectProperty<>();
        shipReplacementRepair = new SimpleBooleanProperty();
    }

    /** getRmaId returns the {@link String} RMA ID that was created on save.
     * @return The {@link String} RMA ID that was entered in the database.
     */
    public String getRmaId() {return rmaId.getValueSafe();}

    /** setRmaId sets the rmaId to the provided newRMAId.
     * @param newRMAId {@link String} value of the ID created on save.
     */
    public void setRmaId(String newRMAId) {rmaId.setValue(newRMAId);}

    /** rmaIDProperty returns the {@link StringProperty} field containing the RMA ID.
     * @return The {@link StringProperty} rmaId field.
     */
    public StringProperty rmaIDProperty() {return rmaId;}

    /** getSelectedCustomerName returns the {@link String} selected customer name stored in the selectedCustomerName field.
     * @return A {@link String} containing the customer name selected by the user.
     */
    public String getSelectedCustomerName() {return selectedCustomerName.getValueSafe();}

    /** setSelectedCustomerName sets the selectedCustomerName field value to the passed-in newSelectedCustomerName {@link String}.
     * @param newSelectedCustomerName The new {@link String} customer name selected by the user.
     */
    public void setSelectedCustomerName(String newSelectedCustomerName) {selectedCustomerName.setValue(newSelectedCustomerName);}

    /** getSelectedCustomerNameProperty returns the {@link StringProperty} containing the selected customer name.
     * @return The {@link StringProperty} selectedCustomerName field.
     */
    public StringProperty selectedCustomerNameProperty() {return selectedCustomerName;}

    /** customerNamesProperty returns the {@link ObservableList} of {@link String} customer names.
     * @return The {@link ObservableList} customerNames field.
     */
    public ObservableList<String> customerNamesProperty() {return customerNames;}

    /** getSelectedBusinessName returns the selected {@link String} business name stored in selectedBusinessName.
     * @return A {@link String} containing the business name selected by the user.
     */
    public CustomerAddress getSelectedBusinessName() {return selectedBusinessName.get();}

    /** setSelectedBusinessName sets the selectedBusinessName field value to the passed-in newSelectedBusinessName {@link String}.
     * @param newSelectedBusinessName The new {@link String} business name selected by the user.
     */
    public void setSelectedBusinessName(CustomerAddress newSelectedBusinessName) {selectedBusinessName.setValue(newSelectedBusinessName);}

    /** selectedBusinessNameProperty returns the {@link StringProperty} containing the selected business name.
     * @return The {@link StringProperty} selectedBusinessName field.
     */
    public ObjectProperty<CustomerAddress> selectedBusinessNameProperty() {return selectedBusinessName;}

    /** clearBusinessNames clears the {@link ObservableList} containing the list of business names for the given
     *  customer name.
     */
    public void clearBusinessNames() {businessNames.clear();}

    /** businessNamesProperty returns the {@link ObservableList} of {@link CustomerAddress} addresses
     *  with their showBusinessName set to true.
     * @return The {@link ObservableList} businessNames field.
     */
    public ObservableList<CustomerAddress> businessNamesProperty() {return businessNames;}

    /** getCreated returns the {@link LocalDateTime} stored in created.
     * @return The {@link LocalDateTime} stored in created.
     */
    public LocalDateTime getCreated() {return created.get();}

    /** setCreated sets the {@link LocalDateTime} date and time stored in the created field to the
     *  new user-entered newCreated {@link LocalDateTime}.
     * @param newCreated The new {@link LocalDateTime} date and time of when the RMA was created
     */
    public void setCreated(LocalDateTime newCreated) {created.set(newCreated);}

    /** createdProperty returns the {@link ObjectProperty} containing the
     *  creation {@link LocalDateTime} date and time of this RMA request.
     * @return The {@link ObjectProperty} created field.
     */
    public ObjectProperty<LocalDateTime> createdProperty() {return created;}

     /** getCreatedBy returns the username {@link String} that created the RMA request.
     * @return The {@link String} stored in the createdBy field.
     */
    public String getCreatedBy() {return createdBy.getValueSafe();}

    /** setCreatedBy sets the username that created this RMA request to the passed-in newCreatedBy {@link String} value.
     * @param newCreatedBy The new {@link String} value of the name of employee that created the RMA.
     */
    public void setCreatedBy(String newCreatedBy) {createdBy.setValue(newCreatedBy);}

    /** createdByProperty returns the {@link StringProperty} createdBy field.
     * @return The {@link StringProperty} createdBy field.
     */
    public StringProperty createdByProperty() {return createdBy;}

    /** getLastModified returns the {@link LocalDateTime} stored in the getLastModified field.
     * @return The {@link LocalDateTime} stored in lastModified field.
     */
    public LocalDateTime getLastModified() {return lastModified.get();}

    /** setLastModified sets the {@link LocalDateTime} date and time value stored in lastModified to the new
     *  provided newLastModified {@link LocalDateTime} value.
     * @param newLastModified The new {@link LocalDateTime} value of when RMA was last modified.
     */
    public void setLastModified(LocalDateTime newLastModified) {lastModified.set(newLastModified);}

    /** lastModifiedProperty returns the {@link ObjectProperty} containing the
     *  lastModified {@link LocalDateTime} date and time of this RMA.
     * @return The {@link ObjectProperty} lastModified field.
     */
    public ObjectProperty<LocalDateTime> lastModifiedProperty() {return lastModified;}

    /** getLastModifiedBy returns the name {@link String} employee name stored in the lastModifiedBy field.
     * @return A {@link String} containing the name stored in lastModifiedBy.
     */
    public String getLastModifiedBy() {return lastModifiedBy.getValueSafe();}

    /** setLastModifiedBy sets the {@link String} name of employee that modified the RMA record to the passed-in
     *  {@link String} newLastModifiedBy value.
     * @param newLastModifiedBy The {@link String} username that last modified the RMA.
     */
    public void setLastModifiedBy(String newLastModifiedBy) {lastModifiedBy.setValue(newLastModifiedBy);}

    /** lastModifiedByProperty returns the {@link StringProperty} lastModifiedBy field.
     * @return The {@link StringProperty} lastModifiedBy field.
     */
    public StringProperty lastModifiedByProperty() {return lastModifiedBy;}

    /** getSelectedRMAStatus returns the {@link String} RMA status stored in selectedRMAStatus.
     * @return The {@link String} selectedRMAStatus value.
     */
    public String getSelectedRMAStatus() {return selectedRMAStatus.getValueSafe();}

    /** setSelectedRMAStatus sets the RMA status stored in selectedRMAStatus to the passed-in {@link String}
     *  newRMAStatus value selected by the user.
     * @param newRMAStatus The new {@link String} RMA Status selected by the user.
     */
    public void setSelectedRMAStatus(String newRMAStatus) {selectedRMAStatus.setValue(newRMAStatus);}

    /** selectedRMAStatusProperty returns the {@link StringProperty} selectedRMAStatus field.
     * @return The {@link StringProperty} selectedRMAStatus field.
     */
    public StringProperty selectedRMAStatusProperty() {return selectedRMAStatus;}

    /** rmaStatusesProperty returns the {@link ObservableList} of {@link String} RMA statuses.
     * @return The {@link ObservableList} rmaStatuses field.
     */
    public ObservableList<String> rmaStatusesProperty() {return rmaStatuses;}

    /** getSelectedPONumber returns the {@link String} Purchase Order number selected by the user.
     * @return The {@link String} stored in the selectedPONumber field.
     */
    public String getSelectedPONumber() {return selectedPONumber.getValueSafe();}

    /** setSelectedPONumber sets the Purchase Order number stored in selectedPONumber to the passed-in
     *  {@link String} newSelectedPONumber selected by the user.
     * @param newSelectedPONumber The new {@link String} PO number selected by the user.
     */
    public void setSelectedPONumber(String newSelectedPONumber) {selectedPONumber.setValue(newSelectedPONumber);}

    /** selectedPONumberProperty returns the {@link StringProperty} selectedPONumber field.
     * @return The {@link StringProperty} selectedPONumber field.
     */
    public StringProperty selectedPONumberProperty() {return selectedPONumber;}

    /** clearPONumbers clears the {@link ObservableList} poNumbers field.
     */
    public void clearPONumbers() {poNumbers.clear();}

    /** poNumbersProperty returns the {@link ObservableList} poNumbers field containing the list of Purchase Order
     *  numbers associated with a given business name.
     * @return The {@link ObservableList} poNumbers field.
     */
    public ObservableList<String> poNumbersProperty() {return poNumbers;}

    /** getSelectedReturnReasonCode returns the {@link String} Return Reason Code selected by the user.
     * @return The {@link String} Return Reason Code selected by the user.
     */
    public String getSelectedReturnReasonCode() {return selectedReturnReasonCode.getValueSafe();}

    /** setSelectedReturnReasonCode sets the {@link String} Return Reason Code selected by the user to
     *  selectedReturnReasonCode using the passed-in parameter.
     *  newReturnReasonCode {@link String} value.
     * @param newReturnReasonCode The new {@link String} Return Reason Code to store in selectedReturnReasonCode.
     */
    public void setSelectedReturnReasonCode(String newReturnReasonCode) {selectedReturnReasonCode.setValue(newReturnReasonCode);}

    /** selectedReturnReasonCodeProperty returns the {@link StringProperty} selectedReturnReasonCode field.
     * @return The {@link StringProperty} selectedReturnReasonCode field.
     */
    public StringProperty selectedReturnReasonCodeProperty() {return selectedReturnReasonCode;}

    /** returnReasonCodesProperty returns the {@link ObservableList} of {@link String} return reason code values
     *  stored in the returnReasonCodes field.
     * @return The {@link ObservableList} returnReasonCodes field.
     */
    public ObservableList<String> returnReasonCodesProperty() {return returnReasonCodes;}

    /** getSelectedCreditReplaceRepair returns the {@link String} Credit, Replace, or Repair value selected by the user.
     * @return The {@link String} Credit, Replace, or Repair value selected by the user.
     */
    public String getSelectedCreditReplaceRepair() {return selectedCreditReplaceRepair.getValueSafe();}

    /** setSelectedCreditReplaceRepair sets the new {@link String} creditReplaceRepair field value to the
     *  passed-in {@link String} newCreditReplaceRepair parameter value.
     * @param newCreditReplaceRepair The new {@link String} value to set.
     */
    public void setSelectedCreditReplaceRepair(String newCreditReplaceRepair) {selectedCreditReplaceRepair.setValue(newCreditReplaceRepair);}

    /** getSelectedCreditReplaceRepairProperty returns the {@link StringProperty}
     * @return selectedCreditReplaceRepair value.
     */
    public StringProperty selectedCreditReplaceRepairProperty() {return selectedCreditReplaceRepair;}

    /** creditReplaceRepairProperty returns the {@link ObservableList} of {@link String} Credit, Replace, and Repair values.
     * @return The {@link ObservableList} creditReplaceRepair field.
     */
    public ObservableList<String> creditReplaceRepairProperty() {return creditReplaceRepair;}

    /** getShippingAddress returns the {@link CustomerAddress} stored in the shippingAddress field.
     * @return The {@link CustomerAddress} stored in shippingAddress.
     */
    public CustomerAddress getShippingAddress() {return shippingAddress.get();}

    /** setShippingAddress sets the stored {@link CustomerAddress} shipping address value stored in shippingAddress to
     *  the passed-in newShippingAddress value.
     * @param newShippingAddress The new {@link CustomerAddress} to store in shippingAddress.
     */
    public void setShippingAddress(CustomerAddress newShippingAddress) {shippingAddress.set(newShippingAddress);}

    /** shippingAddressProperty returns the {@link ObjectProperty} shippingAddress field containing a
     *  {@link CustomerAddress}.
     * @return The {@link ObjectProperty} of {@link CustomerAddress} shippingAddress field.
     */
    public ObjectProperty<CustomerAddress> shippingAddressProperty() {return shippingAddress;}

    /** getAdditionalInfo returns the {@link String} stored in the additionalInfo field.
     * @return The {@link String} stored in the additionalInfo field.
     */
    public String getAdditionalInfo() {return additionalInfo.getValueSafe();}

    /** setAdditionalInfo sets the passed-in {@link String} value to the additionalInfo field.
     * @param newAdditionalInfo The new {@link String} additional info value to store in additionalInfo.
     */
    public void setAdditionalInfo(String newAdditionalInfo) {additionalInfo.setValue(newAdditionalInfo);}

    /** additionalInfoProperty returns the {@link StringProperty} additionalInfo field.
     * @return The {@link StringProperty} additionalInfo field.
     */
    public StringProperty additionalInfoProperty() {return additionalInfo;}

    /** getSelectedProduct returns the {@link PurchaseOrderProduct} stored in the selectedProduct field.
     * @return The {@link PurchaseOrderProduct} value stored in selectedProduct.
     */
    public PurchaseOrderProduct getSelectedProduct() {return selectedProduct.get();}

    /** setSelectedProduct sets the passed-in {@link PurchaseOrderProduct} newProduct value to the selectedProduct field.
     * @param newProduct The new {@link PurchaseOrderProduct} to store in selectedProduct.
     */
    public void setSelectedProduct(PurchaseOrderProduct newProduct) {selectedProduct.set(newProduct);}

    /** selectedProductProperty returns the {@link ObjectProperty} of {@link PurchaseOrderProduct} selectedProduct
     *  field variable.
     * @return The {@link ObjectProperty} selectedProduct field.
     */
    public ObjectProperty<PurchaseOrderProduct> selectedProductProperty() {return selectedProduct;}

    /** clearProducts clears the {@link ObservableList} products field.
     */
    public void clearProducts() {products.clear();}

    /** productsProperty returns the {@link ObservableList} of {@link PurchaseOrderProduct} products field.
     * @return The {@link ObservableList} products field.
     */
    public ObservableList<PurchaseOrderProduct> productsProperty() {return products;}

    /** getReturnQuantity returns the {@link Integer} value stored in the returnQuantity field.
     * @return The {@link Integer} value stored in returnQuantity.
     */
    public Integer getReturnQuantity() {return returnQuantity.getValue();}

    /** setReturnQuantity sets the {@link Integer} value stored in the returnQuantity field.
     * @param newReturnQuantity The new {@link Integer} value to store in returnQuantity.
     */
    public void setReturnQuantity(Integer newReturnQuantity) {returnQuantity.setValue(newReturnQuantity);}

    /** returnQuantityProperty returns the {@link IntegerProperty} returnQuantity field.
     * @return the {@link IntegerProperty} returnQuantity field.
     */
    public IntegerProperty returnQuantityProperty() {return returnQuantity;}

    /** getReturnLabelTracker returns the {@link String} stored in returnLabelTracker.
     * @return The {@link String} stored in the returnLabelTracker field.
     */
    public String getReturnLabelTracker() {return returnLabelTracker.getValueSafe();}

    /** setReturnLabelTracker sets the {@link String} value in the returnLabelTracker field to the passed-in
     *  {@link String} newReturnlabelTracker value.
     * @param newReturnLabelTracker The new {@link String} value to store in returnLabelTracker.
     */
    public void setReturnLabelTracker(String newReturnLabelTracker) {returnLabelTracker.setValue(newReturnLabelTracker);}

    /** returnLabelTrackerProperty returns the {@link StringProperty} returnLabelTracker field.
     * @return The {@link StringProperty} returnLabelTracker field.
     */
    public StringProperty returnLabelTrackerProperty() {return returnLabelTracker;}

    /** getInitialEvaluation returns the {@link String} value stored in the initialEvaluation field.
     * @return The {@link String} initialEvaluation field value.
     */
    public String getInitialEvaluation() {return initialEvaluation.getValueSafe();}

    /** setInitialEvaluation sets the {@link String} value stored in the initialEvaluation field variable.
     * @param newInitialEvaluation The new {@link String} value to store in initialEvaluation.
     */
    public void setInitialEvaluation(String newInitialEvaluation) {initialEvaluation.setValue(newInitialEvaluation);}

    /** initialEvaluationProperty returns the {@link StringProperty} initialEvaluation field.
     * @return The {@link StringProperty} initialEvaluation field variable.
     */
    public StringProperty initialEvaluationProperty() {return initialEvaluation;}

    /** getSelectedDisposition returns the {@link String} value stored in the selectedDisposition field.
     * @return The {@link String} value stored in selectedDisposition.
     */
    public String getSelectedDisposition() {return selectedDisposition.getValueSafe();}

    /** setSelectedDisposition sets the {@link String} value stored in the selectedDisposition field to the user-selected,
     *  passed-in {@link String} newSelectedDisposition parameter.
     * @param newSelectedDisposition The new {@link String} value to store in selectedDisposition.
     */
    public void setSelectedDisposition(String newSelectedDisposition) {selectedDisposition.setValue(newSelectedDisposition);}

    /** selectedDispositionProperty returns the {@link StringProperty} selectedDisposition field variable.
     * @return The {@link StringProperty} selectedDisposition field.
     */
    public StringProperty selectedDispositionProperty() {return selectedDisposition;}

    /** dispositionsProperty returns the {@link ObservableList} of {@link String}s of dispositions.
     * @return The {@link ObservableList} dispositions field variable.
     */
    public ObservableList<String> dispositionsProperty() {return dispositions;}

    /** getDispositionNotes returns the {@link String} value stored in the dispositionNotes field.
     * @return The {@link String} value stored in dispositionNotes.
     */
    public String getDispositionNotes() {return dispositionNotes.getValueSafe();}

    /** setDispositionNotes sets the {@link String} value stored in dispositionNotes to the user-entered {@link String}
     *  newDispositionNotes parameter.
     * @param newDispositionNotes The new {@link String} value to store in dispositionNotes.
     */
    public void setDispositionNotes(String newDispositionNotes) {dispositionNotes.setValue(newDispositionNotes);}

    /** dispositionNotesProperty returns the {@link StringProperty} dispositionNotes field.
     * @return The {@link StringProperty} dispositionNotes field.
     */
    public StringProperty dispositionNotesProperty() {return dispositionNotes;}

    /** getReplacementTrackingNumber returns the {@link String} tracking number for the replacement item(s) held in
     *  replacementTrackingNumber.
     * @return The {@link String} replacementTrackingNumber value.
     */
    public String getReplacementTrackingNumber() {return replacementTrackingNumber.getValueSafe();}

    /** setReplacementTrackingNumber sets the {@link String} tracking number for the replacement product(s) into the
     *  replacementTrackingNumber field via the passed-in {@link String} newReplacementTrackingNumber.
     * @param newReplacementTrackingNumber The new {@link String} tracking number to store in replacementTrackingNumber.
     */
    public void setReplacementTrackingNumber(String newReplacementTrackingNumber) {replacementTrackingNumber.setValue(newReplacementTrackingNumber);}

    /** replacementTrackingNumberProperty returns the {@link StringProperty} replacement tracking number field.
     * @return The {@link StringProperty} replacementTrackingNumber field.
     */
    public StringProperty replacementTrackingNumberProperty() {return replacementTrackingNumber;}

    /** getReplacementShipDate returns the {@link LocalDate} stored in the replacementShipDate field.
     * @return The {@link LocalDate} replacementShipDate field value.
     */
    public LocalDate getReplacementShipDate() {return replacementShipDate.get();}

    /** setReplacementShipDate sets the {@link LocalDate} value stored in the replacementShipDate field.
     * @param newReplacementShipDate The new {@link LocalDate} value to store for the replacementShipDate.
     */
    public void setReplacementShipDate(LocalDate newReplacementShipDate) {replacementShipDate.set(newReplacementShipDate);}

    /** replacementShipDateProperty returns the {@link ObjectProperty} of {@link LocalDate} containing the
     *  replacement ship date.
     * @return The replacementShipDate {@link ObjectProperty} field.
     */
    public ObjectProperty<LocalDate> replacementShipDateProperty() {return replacementShipDate;}

    /** getShipReplacementRepair returns the boolean value stored in the shipReplacementRepair field to indicate
     *  that we will be shipping a replacement or repair.
     * @return The boolean value stored in shipReplacementRepair.
     */
    public boolean getShipReplacementRepair() {return shipReplacementRepair.getValue();}

    /** setShipReplacementRepair sets the indicator to ship the replacement or repair stored in shipReplacementRepair
     *  using the passed-in boolean value.
     * @param newShipReplacementRepair The boolean value to store in shipReplacementRepair.
     */
    public void setShipReplacementRepair(boolean newShipReplacementRepair) {shipReplacementRepair.setValue(newShipReplacementRepair);}

    /** shipReplacementRepairProperty returns the {@link BooleanProperty} shipReplacementRepair field.
     * @return The {@link BooleanProperty} shipReplacementRepair field variable.
     */
    public BooleanProperty shipReplacementRepairProperty() {return shipReplacementRepair;}

    /** convertHashMapToArrayList converts the passed-in {@link HashMap} into an {@link ArrayList}.
     * @param map The {@link HashMap} to convert into an {@link ArrayList}.
     * @return An {@link ArrayList} containing the list of key-value pairs stored in the
     * {@link HashMap} in "key value" format.
     */
    public ArrayList<String> convertHashMapToArrayList(HashMap<String, String> map){
        ArrayList<String> converted = new ArrayList<>();
        for(String key : map.keySet())
            converted.add(key + " " + map.get(key));
        return converted;
    }
}
