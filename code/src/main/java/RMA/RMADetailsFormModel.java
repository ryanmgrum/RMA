package RMA;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

/** RMADetailsFormModel contains extended fields from RMAFormModel that display in RMADetailScreen.
 */
public class RMADetailsFormModel extends RMAFormModel {
    /** rmaProgress contains the {@link Double} value of the progress bar.
     */
    private DoubleProperty rmaProgress;
    /** age contains the int number of days this RMA has been open.
      */
    private IntegerProperty age;
    /** selectedOwner contains the user-selected {@link String} Owner for the RMA.
     */
    private StringProperty selectedOwner;
    /** owners is an {@link ObservableList} containing the list of {@link String} usernames in the RMA
     *  database for reassigning the RMA.
     */
    private ObservableList<String> owners;
    /** engineeringEvaluation contains the {@link String} engineer's evaluation for this RMA.
     */
    private StringProperty engineeringEvaluation;
    /** detailsWindowText contains the {@link String} value for the details {@link javafx.scene.control.TextArea}
     *  on the right-hand side.
     */
    private StringProperty detailsWindowText;


    /** Constructor that initializes fields to empty values.
     */
    public RMADetailsFormModel() {
        // Initialize properties.
        super();
        rmaProgress = new SimpleDoubleProperty();
        age = new SimpleIntegerProperty();
        selectedOwner = new SimpleStringProperty();
        owners = FXCollections.observableArrayList();
        engineeringEvaluation = new SimpleStringProperty();
        detailsWindowText = new SimpleStringProperty();
    }

    /** getRMADetails fetches and displays the RMA details based on the passed-in RMA ID
     *  @param rmaId The user-provided RMA ID whose details we need to fetch from the database
     *  @throws SQLException When connection to database is not established
     */
    public void getRMADetails(String rmaId) throws SQLException {
        // First fetch an instance of DBService
        DBService dbs = DBService.getInstance();

        // Now populate the fields using DBService
        setRmaId(rmaId);
        owners.setAll(dbs.getOwners());
        selectedOwner.set(dbs.getRMAOwner(rmaId));
        setCreated(dbs.getRMACreated(rmaId));
        setCreatedBy(dbs.getRMACreatedBy(rmaId));
        setLastModified(dbs.getRMALastModified(rmaId));
        setLastModifiedBy(dbs.getRMALastModifiedBy(rmaId));
        customerNamesProperty().setAll(dbs.getCustomerNames());
        setSelectedCustomerName(dbs.getRMACustomerName(rmaId));
        businessNamesProperty().setAll(dbs.getCustomerBusinessNames(getSelectedCustomerName()));
        setSelectedBusinessName(dbs.getRMABusinessName(rmaId));
        setShippingAddress(dbs.getRMAAddress(rmaId));
        rmaStatusesProperty().setAll(dbs.getRMAStatuses());
        setSelectedRMAStatus(dbs.getRMAStatus(rmaId));
        poNumbersProperty().setAll(dbs.getCustomerAddressPONumbers(getSelectedBusinessName()));
        setSelectedPONumber(dbs.getRMAPONumber(rmaId));
        returnReasonCodesProperty().setAll(convertHashMapToArrayList(dbs.getReturnReasonCodes()));
        setSelectedReturnReasonCode(dbs.getRMAReturnReasonCode(rmaId));
        setSelectedCreditReplaceRepair(dbs.getRMACreditReplaceRepair(rmaId));
        setAdditionalInfo(dbs.getRMAAdditionalInfo(rmaId));
        productsProperty().setAll(dbs.getPurchaseOrderProducts(getSelectedPONumber()));
        setSelectedProduct(dbs.getRMAProduct(rmaId));
        setReturnQuantity(dbs.getRMAReturnQuantity(rmaId));
        setReturnLabelTracker(dbs.getRMAReturnLabelTracker(rmaId));
        setInitialEvaluation(dbs.getRMAInitialEvaluation(rmaId));
        setEngineeringEvaluation(dbs.getRMAEngineeringEvaluation(rmaId));
        dispositionsProperty().setAll(dbs.getDispositions());
        setSelectedDisposition(dbs.getRMADisposition(rmaId));
        setDispositionNotes(dbs.getRMADispositionNotes(rmaId));
        setReplacementTrackingNumber(dbs.getRMAReplacementTrackingNumber(rmaId));
        setReplacementShipDate(dbs.getRMAReplacementShipDate(rmaId));
        setShipReplacementRepair(dbs.getRMAShipReplacementRepair(rmaId));
    }

    /** getRMAProgress returns the current double value of total progress in completing the RMA request, stored in the
     *  {@link DoubleProperty} rmaProgress field.
     * @return A double representing the total RMA progress.
     */
    public double getRMAProgress() {return rmaProgress.getValue();}

    /** updateRMAProgress calculates the RMA progress stored in the rmaProgress field.
     *  (Requirement 5.1.1)
     */
    public void updateRMAProgress() {
        if (!getSelectedCreditReplaceRepair().equals("Credit")) {
            double progress = 0.0;
            if (!getSelectedCustomerName().isEmpty() &&
                !getSelectedCustomerName().isBlank() &&
                 getSelectedBusinessName() != null &&
                !getSelectedPONumber().isEmpty() &&
                !getSelectedPONumber().isBlank() &&
                !getSelectedOwner().isEmpty() &&
                !getSelectedOwner().isBlank() &&
                !getSelectedRMAStatus().isEmpty() &&
                !getSelectedRMAStatus().isBlank() &&
                !getSelectedCreditReplaceRepair().isEmpty() &&
                !getSelectedCreditReplaceRepair().isBlank() &&
                !getSelectedReturnReasonCode().isEmpty() &&
                !getSelectedReturnReasonCode().isBlank()
            )
                progress += 20.0;
            if (getSelectedProduct() != null &&
               !getReturnLabelTracker().isBlank() &&
               !getReturnLabelTracker().isEmpty() &&
                getReturnQuantity() > 0
            )
                progress += 20.0;
            if (!getInitialEvaluation().isBlank() &&
                !getInitialEvaluation().isEmpty() &&
                !getEngineeringEvaluation().isBlank() &&
                !getEngineeringEvaluation().isEmpty()
            )
                progress += 20.0;
            if (!getSelectedDisposition().isEmpty() &&
                !getSelectedDisposition().isBlank() &&
                !getDispositionNotes().isEmpty() &&
                !getDispositionNotes().isBlank()
            )
                progress += 20.0;
            if (!getReplacementTrackingNumber().isBlank() &&
                !getReplacementTrackingNumber().isEmpty() &&
                 getReplacementShipDate() != null &&
                 getShipReplacementRepair()
            )
                progress += 20.0;
            rmaProgress.set(progress / 100.0);
        } else {
            double progress = 0.0;
            if(!getSelectedCustomerName().isEmpty() &&
                !getSelectedCustomerName().isBlank() &&
                 getSelectedBusinessName() != null &&
                !getSelectedPONumber().isEmpty() &&
                !getSelectedPONumber().isBlank() &&
                !getSelectedOwner().isEmpty() &&
                !getSelectedOwner().isBlank() &&
                !getSelectedRMAStatus().isEmpty() &&
                !getSelectedRMAStatus().isBlank() &&
                !getSelectedCreditReplaceRepair().isEmpty() &&
                !getSelectedCreditReplaceRepair().isBlank() &&
                !getSelectedReturnReasonCode().isEmpty() &&
                !getSelectedReturnReasonCode().isBlank()
            )
                progress += 25.0;
            if(getSelectedProduct() != null &&
              !getReturnLabelTracker().isBlank() &&
              !getReturnLabelTracker().isEmpty() &&
               getReturnQuantity() > 0
            )
                progress = progress + 25.0;
            if(!getInitialEvaluation().isBlank() &&
               !getInitialEvaluation().isEmpty() &&
               !getEngineeringEvaluation().isBlank() &&
               !getEngineeringEvaluation().isEmpty()
            )
                progress = progress + 25.0;
            if(!getSelectedDisposition().isEmpty() &&
               !getSelectedDisposition().isBlank() &&
               !getDispositionNotes().isEmpty() &&
               !getDispositionNotes().isBlank()
            )
                progress = progress + 25.0;
            rmaProgress.set(progress / 100.0);
        }
    }

    /** rmaProgressProperty returns the {@link DoubleProperty} rmaProgress field storing the current value of
     *  the progress bar in the RMA details.
     * @return The {@link DoubleProperty} rmaProgress field.
     */
    public DoubleProperty rmaProgressProperty() {return rmaProgress;}

    /** getAge returns the integer number of days this RMA request has been open.
     * @return An int containing the number of days this RNA request has been open.
     */
    public int getAge() {return age.getValue();}

    /** updateAge calculates the value for age depending on whether the RMA request is still open
     *  (if Status is not Closed, used created field, otherwise use lastModified field).
     */
    public void updateAge() {
        if (!getSelectedRMAStatus().equals("Closed"))
            age.set(Period.between(getCreated().toLocalDate(), LocalDate.now()).getDays());
        else
            age.set(Period.between(getCreated().toLocalDate(), getLastModified().toLocalDate()).getDays());
    }

    /** ageProperty returns the {@link IntegerProperty} age field.
     * @return the {@link IntegerProperty} age field.
     */
    public IntegerProperty ageProperty() {return age;}

    /** getSelectedOwner returns the stored {@link String} username of the currently assigned owner, stored in the
     *  selectedOwner field.
     * @return The {@link String} username of the owner of this RMA stored in selectedOwner.
     */
    public String getSelectedOwner() {return selectedOwner.getValueSafe();}

    /** setSelectedOwner stores the user-selected new {@link String} owner username into the selectedOwner field.
     * @param newSelectedOwner The new {@link String} owner's username to store in selectedOwner.
     */
    public void setSelectedOwner(String newSelectedOwner) {selectedOwner.set(newSelectedOwner);}

    /** selectedOwnerProperty returns the {@link StringProperty} owner field.
     * @return The {@link StringProperty} owner field.
     */
    public StringProperty selectedOwnerProperty() {return selectedOwner;}

    /** ownersProperty returns the {@link ObservableList} backing the Owner dropdown.
     * @return The {@link ObservableList} owners field.
     */
    public ObservableList<String> ownersProperty() {return owners;}

    /** getEngineeringEvaluation returns the {@link String} stored in engineeringEvaluation.
     * @return The {@link String} text of the Engineering Evaluation.
     */
    public String getEngineeringEvaluation() {return engineeringEvaluation.getValueSafe();}

    /** setEngineeringEvaluation sets the {@link String} Engineering Evaluation value stored in the
     *  engineeringEvaluation field to the passed-in newEngineeringEvaluation value.
     * @param  newEngineeringEvaluation The new {@link String} Engineering Evaluation to store in engineeringEvaluation.
     */
    public void setEngineeringEvaluation(String newEngineeringEvaluation) {engineeringEvaluation.setValue(newEngineeringEvaluation);}

    /** engineeringEvaluationProperty returns the {@link StringProperty} backing the Engineering Evaluation {@link javafx.scene.control.TextArea}.
     * @return The {@link StringProperty} engineeringEvaluation field.
     */
    public StringProperty engineeringEvaluationProperty() {return engineeringEvaluation;}

    /** getDetailsWindowText returns the details text {@link String} stored in the detailsWindowText field.
     * @return The {@link String} stored in detailsWindowText.
     */
    public String getDetailsWindowText() {return detailsWindowText.getValueSafe();}


    /** setDetailsWindowText sets the {@link String} value to be shown in the Details Window.
     * @param newDetailsWindowText The {@link String} value to display in this Details Window.
     */
    public void setDetailsWindowText(String newDetailsWindowText) {detailsWindowText.setValue(newDetailsWindowText);}

    /** detailsWindowTextProperty returns the {@link StringProperty} backing the Details Window.
     * @return The {@link StringProperty} detailsWindowText field.
     */
    public StringProperty detailsWindowTextProperty() {return detailsWindowText;}
}