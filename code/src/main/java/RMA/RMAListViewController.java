package RMA;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/** RMAListViewController contains the logic and field references to the GUI controls on the RMAListView form.
 */
public class RMAListViewController implements Initializable {
    /** lblItemCount contains the number of items in the RMA list and the last time the list was updated.
     *  (Requirement 2.1)
     */
    @FXML
    private Label lblItemCount;
    /** txtSearch is a {@link TextField} used to filter the RMA list by an RMA ID.
     *  (Requirement 2.3)
     */
    @FXML
    private TextField txtSearch;
    /** btnNewRMA brings up a new RMAForm for creating a new RMA.
     *  (Requirement 2.4)
     */
    @FXML
    private Button btnNewRMA;
    /** btnRefresh is used to fetch the latest RMA list from the SQL Server RMA database.
     *  (Requirement 2.5)
     */
    @FXML
    private Button btnRefresh;
    /** btnDelete deletes the selected RMAs in the tblRMASummary list.
     *  (Requirement 2.7)
     */
    @FXML
    private Button btnDelete;
    /** tblRMASummary contains the list of open RMA requests available to this user's role.
     *  (Requirement 2.2, 2.2.1)
     */
    @FXML
    private TableView<RMAListViewModel> tblRMASummary;
    /** tblList is the {@link ObservableList} representing the contents of tblRMASummary.
     */
    private ObservableList<RMAListViewModel> tblList;
    /** filteredList is used to filter tblList when the user searches for an RMA ID in txtSearch.
     *  (Requirement 2.3.1)
     */
    private FilteredList<RMAListViewModel> filteredList;
    /** colShouldDelete is used to mark whether this RMA should be deleted from the database.
     *  (Requirement 2.7, 2.2.1.1)
     */
    @FXML
    private TableColumn<RMAListViewModel, Boolean> colShouldDelete;
    /** colCustomerName contains the customer name associated with the RMA request.
     *  (Requirement 2.2.1.1)
     */
    @FXML
    private TableColumn<RMAListViewModel, String> colCustomerName;
    /** colBusinessName contains the business name associated with the RMA request.
     *  (Requirement 2.2.1.1)
     */
    @FXML
    private TableColumn<RMAListViewModel, CustomerAddress> colBusinessName;
    /** colRMAId contains the RMA's ID.
     *  (Requirement 2.2.1.1)
     */
    @FXML
    private TableColumn<RMAListViewModel, String> colRMAId;
    /** colRMAStatus contains the RMA's status.
     *  (Requirement 2.2.1.1)
     */
    @FXML
    private TableColumn<RMAListViewModel, String> colRMAStatus;
    /** colProduct contains the name of the product being returned.
     *  (Requirement 2.2.1.1)
     */
    @FXML
    private TableColumn<RMAListViewModel, PurchaseOrderProduct> colProduct;
    /** colReplaceRepairIndicator is used to show whether the customer's
     *  replacement or repair has been sent.
     *  (Requirement 2.2.1.1)
     */
    @FXML
    private TableColumn<RMAListViewModel, Boolean> colReplaceRepairIndicator;
    /** colCustomerInfoShipAddress displays information about the customer and
     *  their shipping address.
     *  (Requirement 2.2.1.1)
     */
    @FXML
    private TableColumn<RMAListViewModel, CustomerAddress> colCustomerInfoShipAddress;
    /** colReturnQuantity displays the quantity of product that the customer is
     *  returning.
     *  (Requirement 2.2.1.1)
     */
    @FXML
    private TableColumn<RMAListViewModel, Integer> colReturnQuantity;
    /** lastUpdated keeps track of the last time the RMA list was updated.
     *  (Requirement 2.1)
     */
    private LocalDateTime lastUpdated;
    /** tableCount manages the text above the TableView listing the number of items and the last update date and time
     *  (Requirement 2.1)
     */
    private StringProperty tableCount;


    /** Default empty constructor, used when {@link javafx.fxml.FXMLLoader} loads the fxml file.
     */
    public RMAListViewController() {}

    /** initialize ties together the GUI's elements with the RMAListViewModel model and
     *  creates any necessary {@link ChangeListener}s on the controls.
     *  (Requirement 2.2.2, 2.4.2, 2.6, 2.7.1, 5.2.8.2)
     * @param url The {@link URL} location of the fxml file that describes
     *            the RMAListView window elements and their layout.
     * @param rb {@link ResourceBundle} that contains locale-specific data.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // First set up the ObservableList and FilteredList.
        tblList = FXCollections.observableArrayList();
        filteredList = new FilteredList<RMAListViewModel>(tblList);
        tblRMASummary.setItems(filteredList);
        tblRMASummary.setEditable(true);
        lastUpdated = LocalDateTime.now();
        tableCount = new SimpleStringProperty();
        lblItemCount.textProperty().bindBidirectional(tableCount);

        // Add ChangeListener to txtSearch to update what the RMAListViewModels FilteredList shows whenever its contents change.
        txtSearch.textProperty().addListener(
            new ChangeListener<String>() {
                /** changed listens for changes to the input of the txtSearch {@link TextField} in order to filter the
                 *  {@link TableView} list.
                 * @param observableValue The {@link ObservableValue} that changed.
                 * @param oldValue The {@link String} previous value.
                 * @param newValue The {@link String} new value.
                 */
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    // Update the TableView list filter.
                    filteredList.setPredicate(model -> {
                        if (newValue == null)
                            return true;
                        else if (newValue.isEmpty())
                            return true;
                        else return model.getRMAId().startsWith(newValue);
                    });

                    // Update the item count.
                    tableCount.setValue(filteredList.size() + " items in list. Last updated: " + lastUpdated.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")));
                }
            }
        );

        // Set CellValueFactory for each column.
        colShouldDelete.setCellValueFactory(new PropertyValueFactory<RMAListViewModel, Boolean>("shouldDelete"));
        colShouldDelete.setCellFactory(
            new Callback<TableColumn<RMAListViewModel,Boolean>,TableCell<RMAListViewModel,Boolean>>(){
                /** call handles the callback from input being sent to the cells in the colShouldDelete {@link TableColumn}.
                 * @param p The {@link TableColumn} referenced by the call.
                 * @return The format of the {@link TableCell}.
                 */
                @Override
                public TableCell<RMAListViewModel,Boolean> call(TableColumn<RMAListViewModel,Boolean> p){
                    return new CheckBoxTableCell<>();
                }
            }
        );
        colCustomerName.setCellValueFactory(new PropertyValueFactory<RMAListViewModel, String>("customerName"));
        colBusinessName.setCellValueFactory(new PropertyValueFactory<RMAListViewModel, CustomerAddress>("businessName"));
        colRMAId.setCellValueFactory(new PropertyValueFactory<RMAListViewModel, String>("rmaId"));
        colRMAStatus.setCellValueFactory(new PropertyValueFactory<RMAListViewModel, String>("status"));
        colProduct.setCellValueFactory(new PropertyValueFactory<RMAListViewModel, PurchaseOrderProduct>("product"));
        colReplaceRepairIndicator.setCellValueFactory(new PropertyValueFactory<RMAListViewModel, Boolean>("shipReplaceRepair"));
        colReplaceRepairIndicator.setCellFactory(
            new Callback<TableColumn<RMAListViewModel,Boolean>,TableCell<RMAListViewModel,Boolean>>(){
                @Override
                /** call handles the callback from input being sent to the cells in the colShouldDelete {@link TableColumn}.
                 * @param p The {@link TableColumn} referenced by the call.
                 * @return The format of the {@link TableCell}.
                 */
                public TableCell<RMAListViewModel,Boolean> call(TableColumn<RMAListViewModel,Boolean> p){
                    return new CheckBoxTableCell<>();
                }
            }
        );
        colReplaceRepairIndicator.setEditable(false);
        colCustomerInfoShipAddress.setCellValueFactory(new PropertyValueFactory<RMAListViewModel, CustomerAddress>("address"));
        colReturnQuantity.setCellValueFactory(new PropertyValueFactory<RMAListViewModel, Integer>("returnQuantity"));

        // Add listener to TableView to open the RMA Details screen for the selected RMA.
        tblRMASummary.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            /** handle handles the user selecting entries in the {@link TableView}.
             * @param event The {@link MouseEvent} passed to this event handler.
             */
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    RMAListViewModel row = tblRMASummary.getSelectionModel().getSelectedItem();
                    if (row != null) { // Open up RMA Details for the selected RMA.
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("RMADetailScreen_V6.fxml"));
                            Parent root = loader.load();
                            // Populate the initial values for the form.
                            ((RMADetailsFormController) loader.getController()).loadRMADetails(row.getRMAId());
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setTitle("RMA Details");
                            stage.setScene(scene);

                            // Make the close "X" button perform the same function as RMAForm.
                            stage.getScene().getWindow().setOnCloseRequest(
                                new EventHandler<WindowEvent>() {
                                    /** handle listens for the WINDOW_CLOSE_REQUEST in order to ask the user whether they
                                     *  want to close before continuing.
                                     * @param windowEvent The {@link WindowEvent} that was passed to this method.
                                     */
                                    @Override
                                    public void handle(WindowEvent windowEvent) {
                                        if (windowEvent.getEventType().equals(WindowEvent.WINDOW_CLOSE_REQUEST)) {
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

                                            if (result.isPresent() && result.get() != confirm)
                                                windowEvent.consume();
                                        }
                                    }
                                }
                            );

                            stage.showAndWait();
                            btnRefresh.fire();
                        } catch (IOException e) {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setTitle("RMA Details Form Load Error!");
                            a.setContentText("Error while creating the RMA Details form!" + System.lineSeparator() + e);
                            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                            // TODO Remove once working.
                            e.printStackTrace();
                            a.showAndWait();
                        } catch (SQLException e) {
                            Alert a = new Alert(Alert.AlertType.ERROR, "Error while populating details in RMA Details form! Please try launching the form again!");
                            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                            // TODO Remove once working.
                            e.printStackTrace();
                            a.showAndWait();
                        }
                    }
                }
            }
        });

        // Fetch open RMAs appropriate for this user's role and populate tblList.
        getRMAList();

        // Disable New RMA and Delete buttons if user is an Engineer.
        if (DBService.getInstance().getRole().equals("engineers")) {
            btnDelete.setDisable(true);
            btnNewRMA.setDisable(true);
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
            tblList.addAll(DBService.getInstance().getRMAs());
        } catch (SQLException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Error while fetching the list of open RMAs!" + System.lineSeparator() + e.getLocalizedMessage());
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.showAndWait();
        } finally {
            // Update the item count.
            lastUpdated = LocalDateTime.now();
            tableCount.setValue(filteredList.size() + " items in list. Last updated: " + lastUpdated.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")));
        }
    }

    /** btnNewRMAOnAction opens a new RMA form.
     * (Requirement 2.4.1, 2.6.1, 4.17, 5.2.8.2)
     * @param event The {@link ActionEvent} passed to this event handler.
     */
    @FXML
    protected void btnNewRMAOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RMAForm_V4.fxml"));
            Parent root = loader.load();
            // Populate the initial values for the form.
            ((RMAFormController) loader.getController()).getInitialRMAOptions();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("New RMA");
            stage.setScene(scene);

            // Make the close "X" button perform the same function as the Cancel button.
            stage.getScene().getWindow().setOnCloseRequest(
                new EventHandler<WindowEvent>() {
                    /** handle listens for the WINDOW_CLOSE_REQUEST in order to ask the user whether they
                     *  want to close before continuing.
                     * @param windowEvent The {@link WindowEvent} that was passed to this method.
                     */
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        if (windowEvent.getEventType().equals(WindowEvent.WINDOW_CLOSE_REQUEST)) {
                            // Ask the user if they want to close.
                            ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button text from OK to Confirm
                            Alert a = new Alert(
                                Alert.AlertType.CONFIRMATION,
                             "Are you sure you want to exit?",
                                ButtonType.NO,
                                confirm
                            );
                            a.setTitle("New RMA Close Confirmation");
                            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                            Optional<ButtonType> result = a.showAndWait();

                            if (result.isPresent() && result.get() != confirm)
                                windowEvent.consume();
                        }
                    }
                }
            );

            stage.showAndWait();
            btnRefresh.fire();
        } catch (IOException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("New RMA Form Load Error!");
            a.setContentText("Error while creating the new RMA form!" + System.lineSeparator() + e);
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.showAndWait();
        } catch (SQLException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Error while populating initial values in RMA form! Please try launching the form again!");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.showAndWait();
        }
    }

    /** btnRefreshOnAction handles refreshing the list of open RMAs available to this user's role.
     * (Requirement 2.5.1)
     * @param event The {@link ActionEvent} passed to this event handler.
     */
    @FXML
    protected void btnRefreshOnAction(ActionEvent event) {getRMAList();}

    /** btnDeleteOnAction goes through the list of selected RMAs for deletion and calls their delete method.
     * (Requirement 2.7, 2.7.2, 2.7.3, 2.7.4)
     * @param event The {@link ActionEvent} passed to this event handler.
     */
    @FXML
    protected void btnDeleteOnAction(ActionEvent event) {
        // confirmDelete is used to confirm with the user that they want to delete their selected RMAs.
        boolean confirmDelete = false;

        ArrayList<RMAListViewModel> toRemove = new ArrayList<>();
        try {
            for (RMAListViewModel model : tblList)
                if (model.getShouldDelete()) {
                    if (!confirmDelete) {
                        // Ask the user if they are sure they want to delete the RMAs.
                        ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE); // Change the button context from OK to Confirm
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure you want to permanently delete the selected RMAs?",
                            confirm, ButtonType.CANCEL
                        );

                        a.setTitle("Delete RMA(s) Confirmation");
                        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        Optional<ButtonType> result = a.showAndWait();

                        if (result.isPresent() && result.get() != confirm)
                            break;
                        else
                            confirmDelete = true;
                    }
                    model.delete();
                    toRemove.add(model);
                }
        } catch (SQLException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Error while connecting to SQL Server database!" + System.lineSeparator() + e.getLocalizedMessage());
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.show();
        } finally {
            if (confirmDelete) {
                tblList.removeAll(toRemove);
                btnRefresh.fire();
            }
        }
    }
}