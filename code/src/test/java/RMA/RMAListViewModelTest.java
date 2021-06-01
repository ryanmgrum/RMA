package RMA;


import javafx.beans.property.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

/** Testing Home Screen model.
 * Requirement 2.0
 * Pre Condition ensure there is another RMA that can be delete to test and the
 * RMA that is used to test the other methods
 */
public class RMAListViewModelTest {
    private StringProperty rmaId,status,businessName, customerName;
    private BooleanProperty shipReplaceRepair, shouldDelete;
    private IntegerProperty returnQuantity;
    private ObjectProperty<CustomerAddress> address;
    private ObjectProperty<PurchaseOrderProduct> product;
    private ObjectProperty<LocalDateTime> created;
    private RMAListViewModel lsTest ,RMAToDeleteTEst;
    @Before
    public void setUp() throws Exception {
       DBService db = DBService.getInstance("admin","admin","KINGKAI-HP\\SQLEXPRESS");
        // Initialize properties.
        rmaId = new SimpleStringProperty("1");
        status = new SimpleStringProperty("Dropped at Shop");
        shipReplaceRepair = new SimpleBooleanProperty(false);
        customerName = new SimpleStringProperty("Everything Inc.");
        businessName = new SimpleStringProperty("Five Finger Discounts");
        address = new SimpleObjectProperty<CustomerAddress>(db.getCustomerAddress(5));
        product = new SimpleObjectProperty<PurchaseOrderProduct>(db.getPurchaseOrderProduct(31));
        returnQuantity = new SimpleIntegerProperty(40);
        shouldDelete = new SimpleBooleanProperty(true);
        String str = "2021-04-25 10:00:25";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        created = new SimpleObjectProperty<LocalDateTime>(dateTime);
        lsTest = db.getRMA("1");
        RMAToDeleteTEst = db.getRMA("2");
    }
    /**Title: Test Retrieval of RMAId
     * Description: Test the function to get the RMAId of the RMA on the home screen
     * Requirement: 2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1
     * Expected Result: 1
     * Actual Result: 1
     * Test Outcome: Passed
     */
    @Test
    public void getRMAId() {
        System.out.println("TC_34 was ran." + System.lineSeparator() +"Expected = " + rmaId.getValueSafe() + System.lineSeparator() +
                "Actual = " + lsTest.getRMAId());
        assertEquals(rmaId.getValueSafe(),lsTest.getRMAId());

    }
    /**Title: Test Retrieval of RMA Status
     * Description: Test the function to get the  RMA Status of the RMA
     * Requirement: 3.7
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 2.2
     * Expected Result: Dropped at Shop
     * Actual Result: Dropped at Shop
     * Test Outcome: Passed
     */
    @Test
    public void getStatus() {
        System.out.println("TC_35 was ran." + System.lineSeparator() +"Expected = " + status.getValueSafe() + System.lineSeparator() +
                "Actual = " + lsTest.getStatus());
        assertEquals(status.getValueSafe(),lsTest.getStatus());
    }

    /**Title: Test Retrieval of ShipReplaceRepair
     * Description: Test the function to get the ShipReplaceRepair of the RMA
     * Requirement: 2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1
     * Expected Result: False
     * Actual Result: False
     * Test Outcome: Passed
     */
    @Test
    public void getShipReplaceRepair() {
        System.out.println("TC_36 was ran." + System.lineSeparator() +"Expected = " + shipReplaceRepair.getValue() + System.lineSeparator() +
                "Actual = " + lsTest.getShipReplaceRepair());
        assertEquals(shipReplaceRepair.getValue(),lsTest.getShipReplaceRepair());
    }
    /**Title: Test Retrieval of Customer Name
     * Description: Test the function to get the Customer Name of the RMA
     * Requirement: 2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1
     * Expected Result: Everything Inc.
     * Actual Result: Everything Inc.
     * Test Outcome: Passed
     */
    @Test
    public void getCustomerName() {
        System.out.println("TC_37 was ran." + System.lineSeparator() +"Expected = " + customerName.getValueSafe() + System.lineSeparator() +
                "Actual = " + lsTest.getCustomerName());
        assertEquals(customerName.getValueSafe(),lsTest.getCustomerName());
    }
    /**Title: Test Retrieval of Business Name
     * Description: Test the function to get the Business Name of the RMA
     * Requirement: 2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1
     * Expected Result: Five Finger Discounts
     * Actual Result: Five Finger Discounts.
     * Test Outcome: Passed
     */
    @Test
    public void getBusinessName() {
        System.out.println("TC_38 was ran." + System.lineSeparator() +"Expected = " + businessName.getValueSafe() + System.lineSeparator() +
                "Actual = " + lsTest.getBusinessName());
        assertEquals(businessName.getValueSafe(),lsTest.getBusinessName());
    }
    /**Title: Test Retrieval of Address
     * Description: Test the function to get the Address of the RMA
     * change both to string because object comparison compares the memory address in junit
     * Requirement: 2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1
     * Expected Result: Everything Inc.
     * Five Finger Discounts
     * 18 Burnette St
     * Chattanooga
     * Hamilton
     * TN
     * 37408
     * United States of America
     * Phone: 423-879-6856
     * Fax: 423-555-5556
     * Actual Result: Everything Inc.
     * Five Finger Discounts
     * 18 Burnette St
     * Chattanooga
     * Hamilton
     * TN
     * 37408
     * United States of America
     * Phone: 423-879-6856
     * Fax: 423-555-5556
     * Test Outcome: Passed
     */
    @Test
    public void getAddress() {
        System.out.println("TC_39 was ran." + System.lineSeparator() +"Expected = " + address.get() + System.lineSeparator() +
                "Actual = " + lsTest.getAddress());
        assertEquals(address.get().toString(),lsTest.getAddress().toString());
    }
    /**Title: Test Retrieval of Product
     * Description: Test the function to get the Product of the RMA
     * change both to string because object comparison compares the memory address in junit
     * Requirement: 2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1
     * Expected Result:Electronics, Hard Drive
     * Actual Result:Electronics, Hard Drive
     * Test Outcome: Passed
     */
    @Test
    public void getProduct() {
        System.out.println("TC_40 was ran." + System.lineSeparator() +"Expected = " + product.get() + System.lineSeparator() +
                "Actual = " + lsTest.getProduct());
        assertEquals(product.get().toString(),lsTest.getProduct().toString());
    }
    /**Title: Test Retrieval of Quantity
     * Description: Test the function to get the Quantity of the RMA
     * Requirement: 2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1
     * Expected Result:40
     * Actual Result:40
     * Test Outcome: Passed
     */
    @Test
    public void getReturnQuantity() {
        System.out.println("TC_41 was ran." + System.lineSeparator() +"Expected = " + returnQuantity.getValue() + System.lineSeparator() +
                "Actual = " + lsTest.getReturnQuantity());
        assertEquals(returnQuantity.getValue(),lsTest.getReturnQuantity());
    }
    /**Title: Test Retrieval of Should Delete Boolean
     * Description: Test the function to get the Should Delete Booleanof the RMA
     * Requirement: 2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1
     * Expected Result: false
     * Actual Result: false
     * Test Outcome: Passed
     */
    @Test
    public void getShouldDelete() {
        System.out.println("TC_42 was ran." + System.lineSeparator() +"Expected = " + shouldDelete.getValue() + System.lineSeparator() +
                "Actual = " + lsTest.getShouldDelete());
        assertEquals(shouldDelete.getValue(),lsTest.getShouldDelete());
    }
    /**Title: Test Delete Method
     * Description: Test the function to delete an RMA
     * Requirement: 2.7
     * Assumptions: The RMAToDeleteTEst exist and no issue connecting to the database
     * Input: RMAId = 0 (Or Id that need to be deleted
     * Expected Result: RMA Exist and then RMA is deleted
     * Actual Result: RMA Exist and then RMA is deleted
     * Test Outcome: Passed
     */
    @Test
    public void delete() throws SQLException {
        DBService db = DBService.getInstance("admin","admin","KINGKAI-HP\\SQLEXPRESS");
      if(db.getRMA("6") != null){
          System.out.println("RMA Still Exist");
          RMAToDeleteTEst.delete(); //delete the RMA
      }{
            System.out.println("RMA does not exist, created another RMA and update the RMAid. Run the test again.");
        }
        if(db.getRMA("0") == null){
            System.out.println("RMA was deleted");
        }else {
            System.out.println("RMA Still Exist");
        }

    }
    /**Title: Test Retrieval of Created Date
     * Description: Test the function to get the created date of the RMA
     * Requirement: 2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1
     * Expected Result: 2021-04-25T10:00:25.967
     * Actual Result: 2021-04-25T10:00:25.967
     * Test Outcome: Passed
     */
    @Test
    public void getCreated() {
        System.out.println("TC_42 was ran." + System.lineSeparator() +"Expected = " + created.getValue() + ".967" + System.lineSeparator() +
                "Actual = " + lsTest.getCreated());
        assertEquals(created.getValue() + ".967",lsTest.getCreated().toString());
    }

}