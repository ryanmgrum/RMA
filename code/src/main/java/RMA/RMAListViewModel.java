package RMA;

import javafx.beans.property.*;

import java.sql.SQLException;
import java.time.LocalDateTime;

/** RMAListViewModel contains the fields that make up the model for the
 *  RMAListView form.
 */
public class RMAListViewModel {
    /** rmaId contains the identifier to the RMA request.
     */
    private StringProperty rmaId;
    /** status contains the textual description of the RMA's status.
     */
    private StringProperty status;
    /** shipReplaceRepair indicates whether this RMA's replacement or repaired part was shipped.
     */
    private BooleanProperty shipReplaceRepair;
    /** customerName contains the customer name associated with the RMA request.
     */
    private StringProperty customerName;
    /** businessName contains the business name associated with the RMA request.
     */
    private StringProperty businessName;
    /** address maintains the business address associated with this RMA request.
     */
    private ObjectProperty<CustomerAddress> address;
    /** product contains the product information associated with this RMA request.
     */
    private ObjectProperty<PurchaseOrderProduct> product;
    /** returnQuantity contains the number of items that are being returned.
     */
    private IntegerProperty returnQuantity;
    /** shouldDelete contains whether this RMA request is currently selected for deletion in RMAListView.
     */
    private BooleanProperty shouldDelete;
    /** created contains the date and time the RMA was created.
     */
    private ObjectProperty<LocalDateTime> created;

    /** This constructor for RMAListViewModel takes in an RMA ID in order to fetch the necessary related information.
     * @param newRMAId A {@link String} containing the RMA ID to reference.
     * @throws SQLException If there is an issue connecting to the SQL Server database or the SQL query.
     */
    public RMAListViewModel(String newRMAId) throws SQLException {
        // Use DBService to fetch the RMA info.
        RMAListViewModel model = DBService.getInstance().getRMA(newRMAId);
        // Initialize properties.
        rmaId = new SimpleStringProperty(model.getRMAId());
        status = new SimpleStringProperty(model.getStatus());
        shipReplaceRepair = new SimpleBooleanProperty(model.getShipReplaceRepair());
        customerName = new SimpleStringProperty(model.getCustomerName());
        businessName = new SimpleStringProperty(model.getBusinessName());
        address = new SimpleObjectProperty<CustomerAddress>(model.getAddress());
        product = new SimpleObjectProperty<PurchaseOrderProduct>(model.getProduct());
        returnQuantity = new SimpleIntegerProperty(model.getReturnQuantity());
        shouldDelete = new SimpleBooleanProperty(model.getShouldDelete());
        created = new SimpleObjectProperty<LocalDateTime>(model.getCreated());
    }

    /** Constructor for RMAListViewModel that manually fills in all the field details.
     * @param rmaId The {@link String} RMA ID to assign to this RMAListViewModel.
     * @param status The {@link String} RMA status description to use for this RMAListViewModel.
     * @param shipReplaceRepair The boolean status of shipping a replacement or repair.
     * @param customerName The {@link String} customer name to use.
     * @param businessName The {@link String} business name to use.
     * @param address The CustomerAddress to associate with this RMAListViewModel.
     * @param product The PurchaseOrderProduct to associate with this RMAListViewModel.
     * @param returnQuantity The amount of product being returned by the customer.
     * @param shouldDelete Whether this RMA should be flagged for deletion.
     * @param created The {@link LocalDateTime} to assign to the RMA in this RMAListViewModel.
     */
    public RMAListViewModel(
        String rmaId, String status, boolean shipReplaceRepair,
        String customerName, String businessName, CustomerAddress address,
        PurchaseOrderProduct product, int returnQuantity, boolean shouldDelete,
        LocalDateTime created
    ) {
        // Initialize properties.
        this.rmaId = new SimpleStringProperty(rmaId);
        this.status = new SimpleStringProperty(status);
        this.shipReplaceRepair = new SimpleBooleanProperty(shipReplaceRepair);
        this.customerName = new SimpleStringProperty(customerName);
        this.businessName = new SimpleStringProperty(businessName);
        this.address = new SimpleObjectProperty<CustomerAddress>(address);
        this.product = new SimpleObjectProperty<PurchaseOrderProduct>(product);
        this.returnQuantity = new SimpleIntegerProperty(returnQuantity);
        this.shouldDelete = new SimpleBooleanProperty(shouldDelete);
        this.created = new SimpleObjectProperty<LocalDateTime>(created);
    }

    /** getRMAId returns the {@link String} value stored in rmaId.
     * @return The {@link String} value stored in rmaId.
     */
    public String getRMAId() {return rmaId.getValueSafe();}

    /** rmaIdProperty returns the {@link StringProperty} field containing the RMA ID.
     * @return The {@link StringProperty} rmaId field.
     */
    public StringProperty rmaIdProperty() {return rmaId;}

    /** getStatus returns the {@link String} status description stored in status.
     * @return The {@link String} value stored in status.
     */
    public String getStatus() {return status.getValueSafe();}

    /** statusProperty returns the {@link StringProperty} field containing the
     *  textual description of the RMA status.
     * @return The {@link StringProperty} status field.
     */
    public StringProperty statusProperty() {return status;}

    /** getShipReplaceRepair returns the {@link Boolean} value stored in shipReplaceRepair.
     * @return The {@link Boolean} value stored in shipReplaceRepair.
     */
    public Boolean getShipReplaceRepair() {return shipReplaceRepair.get();}

    /** shipReplaceRepairProperty returns the {@link BooleanProperty} field containing
     *  whether the RMA's replacement or repair was shipped.
     * @return The {@link BooleanProperty} shipReplaceRepair field.
     */
    public BooleanProperty shipReplaceRepairProperty() {return shipReplaceRepair;}

    /** getCustomerName returns the {@link String} contained in customerName.
     * @return The {@link String} stored in customerName.
     */
    public String getCustomerName() {return customerName.getValueSafe();}

    /** customerNameProperty returns the {@link StringProperty} containing the customer's name
     *  referenced in the RMA request.
     * @return The {@link StringProperty} customerName field.
     */
    public StringProperty customerNameProperty() {return customerName;}

    /** getBusinessName returns the {@link String} value stored in businessName.
     * @return The {@link String} stored in businessName.
     */
    public String getBusinessName() {return businessName.getValueSafe();}

    /** businessNameProperty returns the {@link StringProperty} containing the customer's
     *  business name referenced in the RMA request.
     * @return The {@link StringProperty} businessName field.
     */
    public StringProperty businessNameProperty() {return businessName;}

    /** getAddress returns the CustomerAddress object stored in address.
     * @return The CustomerAddress stored in address.
     */
    public CustomerAddress getAddress() {return address.getValue();}

    /** addressProperty returns the {@link ObjectProperty} containing the
     *  customer's business address.
     * @return The {@link ObjectProperty} address field.
     */
    public ObjectProperty<CustomerAddress> addressProperty() {return address;}

    /** getProduct returns the PurchaseOrderProduct stored in product.
     * @return The PurchaseOrderProduct stored in product.
     */
    public PurchaseOrderProduct getProduct() {return product.getValue();}

    /** productProperty returns the {@link ObjectProperty} containing the
     *  RMA's referenced PurchaseOrderProduct.
     * @return The {@link ObjectProperty} product field.
     */
    public ObjectProperty<PurchaseOrderProduct> productProperty() {return product;}

    /** getReturnQuantity returns the {@link Integer} stored in returnQuantity.
     * @return The {@link Integer} stored in returnQuantity.
     */
    public Integer getReturnQuantity() {return returnQuantity.getValue();}

    /** returnQuantityProperty returns the {@link IntegerProperty} containing the number
     *  of items that are being returned.
     * @return The {@link IntegerProperty} returnQuantity field.
      */
    public IntegerProperty returnQuantityProperty() {return returnQuantity;}

    /** getShouldDelete returns the {@link Boolean} value stored in shouldDelete.
     * @return The {@link Boolean} value stored in shouldDelete.
      */
    public Boolean getShouldDelete() {return shouldDelete.getValue();}

    /** shouldDeleteProperty returns the {@link BooleanProperty} containing whether
     *  the user is planning to delete the RMA request referenced by this model.
     * @return The {@link BooleanProperty} shouldDelete field.
     */
    public BooleanProperty shouldDeleteProperty() {return shouldDelete;}

    /** delete removes this RMA request from the database.
     * @throws SQLException If there is an issue connecting to the database or processing the query.
     */
    public void delete() throws SQLException {DBService.getInstance().deleteRMA(rmaId.getValueSafe());}

    /** getCreated returns the {@link LocalDateTime} stored in created.
     * @return The {@link LocalDateTime} stored in created.
     */
    public LocalDateTime getCreated() {return created.getValue();}

    /** getCreatedProperty returns the {@link ObjectProperty} containing the
     *  creation date and time of this RMA request.
     * @return The {@link ObjectProperty} created field.
     */
    public ObjectProperty<LocalDateTime> createdProperty() {return created;}
}