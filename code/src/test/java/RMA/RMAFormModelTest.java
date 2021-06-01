package RMA;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RMAFormModelTest {

    private RMAFormModel rfModel;
    private RMAFormControllerTest contTest;
    private DBService db;
    private String rmaID;
    private LocalDateTime dateTimeCreatedTest, dateTimeLastModTest;

    @Before
    public void setUp() throws Exception {
        db = DBService.getInstance("admin","admin","KINGKAI-HP\\SQLEXPRESS");
        rfModel = new RMAFormModel();

        rfModel.setRmaId("1");
        rfModel.setSelectedCustomerName("Electronia Inc.");
        String str = "2021-04-25 10:00:25";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTimeCreatedTest = LocalDateTime.parse(str, formatter);
        rfModel.setCreated(dateTimeCreatedTest);
        rfModel.setCreatedBy("analyst");
        dateTimeLastModTest = LocalDateTime.parse(str, formatter);
        rfModel.setLastModified(dateTimeLastModTest);
        rfModel.setLastModifiedBy("analyst");
        rfModel.setSelectedBusinessName(db.getCustomerAddress(3));
        rfModel.setShippingAddress(db.getCustomerAddress(3));
        rfModel.setSelectedRMAStatus("Ready for installation");
        rfModel.setSelectedPONumber("219389VF3");
        rfModel.setSelectedReturnReasonCode("ERROR");
        rfModel.setSelectedCreditReplaceRepair("Replace");
        rfModel.setAdditionalInfo("Testing White Box");
        rfModel.setSelectedProduct(db.getPurchaseOrderProduct(15));
        rfModel.setReturnQuantity(5);
        rfModel.setReturnLabelTracker("123456track");
        rfModel.setInitialEvaluation("Test worked");
        rfModel.setSelectedDisposition("Repair and add to inventory, credit customer");
        rfModel.setDispositionNotes("White Box Testing");
        rfModel.setReplacementTrackingNumber("");
        rfModel.setReplacementShipDate(null);
        rfModel.setShipReplacementRepair(false);

    }
    /**Title: Return RMA Id method
     * Description: Test the get method and should return the RMAId that was initialized
     * Requirement: 5.2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1, Admin Role
     * Expected Result: 1
     * Actual Result: 1
     * Test Outcome: Passed
     */
    @Test
    public void getRmaId()  { this.rmaID = rfModel.getRmaId();
        System.out.println("TC_60 was ran." + System.lineSeparator() +"Expected = 1 "+ System.lineSeparator() +
                "Actual = " + rfModel.getRmaId());
        assertEquals("1",rfModel.getRmaId());
    }
    /**Title: Return Customer Name
     * Description: Test get method to return customer name
     * Requirement: 4.1, 4.1.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Address ID = 3
     * Expected = Electronia Inc.
     * Actual = Electronia Inc.
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedCustomerName() throws SQLException {
     rfModel.getSelectedCustomerName();
     System.out.println("TC_61 was ran." + System.lineSeparator() +"Expected = Electronia Inc. "+ System.lineSeparator() +
                "Actual = " + rfModel.getSelectedCustomerName());
        assertEquals("Electronia Inc.",rfModel.getSelectedCustomerName());
    }
    /**Title: Return Business Name
     * Description: Test get method to return Business name
     * Requirement: 4.2,4.2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Solid State Store
     * 250 Industrial Park St
     * Building #6
     * Pittsfield
     * Somerset
     * ME
     * 04967
     * United States of America
     * Phone: 207-689-7885
     * Expected =Solid State Store
     * 250 Industrial Park St
     * Building #6
     * Pittsfield
     * Somerset
     * ME
     * 04967
     * United States of America
     * Phone: 207-689-7885
     * Actual = Solid State Store
     * 250 Industrial Park St
     * Building #6
     * Pittsfield
     * Somerset
     * ME
     * 04967
     * United States of America
     * Phone: 207-689-7885
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedBusinessName() throws SQLException {
      rfModel.getSelectedBusinessName();
        System.out.println("TC_62 was ran." + System.lineSeparator() +"Expected = " + db.getCustomerAddress(3) + System.lineSeparator() +
                "Actual = " +  rfModel.getSelectedBusinessName());
        assertEquals(db.getCustomerAddress(3).toString(), rfModel.getSelectedBusinessName().toString());
    }
    @Test
    public void clearBusinessNames() {//TODO still need to implement this test
    }
    /**Title: Return Created Date
     * Description: Test the get method for created date retrieval
     * Requirement: 5.2.5
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Now
     * Expected = 2021-04-25T10:00:25
     * Actual = 2021-04-25T10:00:25
     * Test Outcome: Passed
     */
    @Test
    public void getCreated() {
        rfModel.getCreated();
        System.out.println("TC_63 was ran." + System.lineSeparator() +"Expected = " + dateTimeCreatedTest + System.lineSeparator() +
                "Actual = " +  rfModel.getCreated());
        assertEquals(dateTimeCreatedTest, rfModel.getCreated());
    }
    /**Title: Return Created By
     * Description: Test the get method for created by retrieval
     * Requirement: 5.2.5
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: analyst
     * Expected = analyst
     * Actual = analyst
     * Test Outcome: Passed
     */
    @Test
    public void getCreatedBy() {
        rfModel.getCreatedBy();
        System.out.println("TC_64 was ran." + System.lineSeparator() +"Expected =analyst" + System.lineSeparator() +
                "Actual = " +  rfModel.getCreatedBy());
        assertEquals("analyst", rfModel.getCreatedBy());
    }
    /**Title: Return Last Modified Date
     * Description: Test the get method for Last Modified Date retrieval
     * Requirement: 5.2, 5.2.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input:  2021-04-25 10:00:25
     * Expected =  2021-04-25T10:00:25
     * Actual =  2021-04-25T10:00:25
     * Test Outcome: Passed
     */
    @Test
    public void getLastModified() {
        rfModel.getLastModified();
        System.out.println("TC_65 was ran." + System.lineSeparator() +"Expected = " + dateTimeLastModTest + System.lineSeparator() +
                "Actual = " +  rfModel.getLastModified());
        assertEquals(dateTimeLastModTest, rfModel.getLastModified());
    }
    /**Title: Return Last Modified By
     * Description: Test the get method for Last Modified By retrieval
     * Requirement: 5.2, 5.2.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: analyst
     * Expected = analyst
     * Actual = analyst
     * Test Outcome: Passed
     */
    @Test
    public void getLastModifiedBy() {
        rfModel.getLastModifiedBy();
        System.out.println("TC_66 was ran." + System.lineSeparator() +"Expected = analyst" + System.lineSeparator() +
                "Actual = " +  rfModel.getLastModifiedBy());
        assertEquals("analyst", rfModel.getLastModifiedBy());
    }
    /**Title: Return RMA Status
     * Description: Test the get method for RMA Status retrieval
     * Requirement: 5.2, 5.2.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Ready for installation
     * Expected = Ready for installation
     * Actual = Ready for installation
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedRMAStatus() {
        rfModel.getSelectedRMAStatus();
        System.out.println("TC_67 was ran." + System.lineSeparator() +"Expected = Ready for installation" + System.lineSeparator() +
                "Actual = " +  rfModel.getSelectedRMAStatus());
        assertEquals("Ready for installation", rfModel.getSelectedRMAStatus());
    }
    /**Title: Return PO Number
     * Description: Test the get method for PO number retrieval
     * Requirement: 4.11, 4.11.1 4.11.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: 219389VF3
     * Expected = 219389VF3
     * Actual = 219389VF3
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedPONumber() {
        rfModel.getSelectedPONumber();
        System.out.println("TC_68 was ran." + System.lineSeparator() +"Expected = 219389VF3" + System.lineSeparator() +
                "Actual = " +    rfModel.getSelectedPONumber());
        assertEquals("219389VF3",   rfModel.getSelectedPONumber());
    }

    @Test
    public void clearPONumbers() {
    }
    /**Title: Return Return Reason Code
     * Description: Test the get method for Return Reason Code retrieval
     * Requirement: 4.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: ERROR
     * Expected = ERROR
     * Actual = ERROR
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedReturnReasonCode() { rfModel.getSelectedReturnReasonCode();
        System.out.println("TC_68 was ran." + System.lineSeparator() +"Expected = ERROR" + System.lineSeparator() +
                "Actual = " +    rfModel.getSelectedReturnReasonCode());
        assertEquals("ERROR",   rfModel.getSelectedReturnReasonCode());
    }
    /**Title: Return Credit Replace Repair
     * Description: Test the get method for Credit Replace Repair retrieval
     * Requirement: 4.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Replace
     * Expected = Replace
     * Actual = Replace
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedCreditReplaceRepair() {rfModel.getSelectedCreditReplaceRepair();
        System.out.println("TC_69 was ran." + System.lineSeparator() +"Expected = Replace" + System.lineSeparator() +
                "Actual = " +    rfModel.getSelectedCreditReplaceRepair());
        assertEquals("Replace",   rfModel.getSelectedCreditReplaceRepair());
    }
    /**Title: Return Shipping Address
     * Description: Test the get method for Shipping Address retrieval
     * Requirement: 4.12, 4.12.1
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: addressId 3
     * Expected = Electronia Inc.
     * Solid State Store
     * 250 Industrial Park St
     * Building #6
     * Pittsfield
     * Somerset
     * ME
     * 04967
     * United States of America
     * Phone: 207-689-7885
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
     * Test Outcome: Passed
     */
    @Test
    public void getShippingAddress() throws SQLException {
      rfModel.getShippingAddress();
        System.out.println("TC_70 was ran." + System.lineSeparator() +"Expected = Electronia Inc.\n" +
                "Solid State Store\n" +
                "250 Industrial Park St\n" +
                "Building #6\n" +
                "Pittsfield\n" +
                "Somerset\n" +
                "ME\n" +
                "04967\n" +
                "United States of America\n" +
                "Phone: 207-689-7885" + System.lineSeparator() +
                "Actual = " +    rfModel.getShippingAddress());
        assertEquals(db.getCustomerAddress(3).toString(),   rfModel.getShippingAddress().toString());
    }
    /**Title: Return Additional Info
     * Description: Test the get method for Additional Info retrieval
     * Requirement: 4.13
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Testing White Box
     * Expected = Testing White Box
     * Actual = Testing White Box
     * Test Outcome: Passed
     */
    @Test
    public void getAdditionalInfo() {rfModel.getAdditionalInfo();
        System.out.println("TC_71 was ran." + System.lineSeparator() +"Expected = Testing White Box" + System.lineSeparator() +
                "Actual = " +    rfModel.getAdditionalInfo());
        assertEquals("Testing White Box",   rfModel.getAdditionalInfo());
    }
    /**Title: Return Product
     * Description: Test the get method for Product retrieval
     * Requirement: 4.7, 4.7.1
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Electronics, Hard Drive
     * Expected = Electronics, Hard Drive
     * Actual = Electronics, Hard Drive
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedProduct() {rfModel.getSelectedProduct();
        System.out.println("TC_72 was ran." + System.lineSeparator() +"Expected = Electronics, Hard Drive" + System.lineSeparator() +
                "Actual = " +    rfModel.getSelectedProduct());
        assertEquals("Electronics, Hard Drive",   rfModel.getSelectedProduct().toString());
    }

    @Test
    public void clearProducts() {
    }
    /**Title: Return Quality
     * Description: Test the get method for Quality retrieval
     * Requirement: 4.7, 4.7.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: 5
     * Expected = 5
     * Actual = 5
     * Test Outcome: Passed
     */
    @Test
    public void getReturnQuantity() { rfModel.getReturnQuantity();
        System.out.println("TC_73 was ran." + System.lineSeparator() +"Expected = 5" + System.lineSeparator() +
                "Actual = " +    rfModel.getReturnQuantity());
        assertEquals("5",   rfModel.getReturnQuantity().toString());
    }
    /**Title: Return Return Label Tracker
     * Description: Test the get method for Return Label Tracker retrieval
     * Requirement: 4.7, 4.7.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: 123456track
     * Expected = 123456track
     * Actual = 123456track
     * Test Outcome: Passed
     */
    @Test
    public void getReturnLabelTracker() {rfModel.getReturnLabelTracker();
        System.out.println("TC_74 was ran." + System.lineSeparator() +"Expected = 123456track" + System.lineSeparator() +
                "Actual = " +    rfModel.getReturnLabelTracker());
        assertEquals("123456track",   rfModel.getReturnLabelTracker());
    }
    /**Title: Return Initial Evaluation
     * Description: Test the get method for Return Initial Evaluation retrieval
     * Requirement: 4.8
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Test worked
     * Expected = Test worked
     * Actual = Test worked
     * Test Outcome: Passed
     */
    @Test
    public void getInitialEvaluation() {
        rfModel.getInitialEvaluation();
        System.out.println("TC_76 was ran." + System.lineSeparator() +"Expected = Test worked" + System.lineSeparator() +
                "Actual = " +     rfModel.getInitialEvaluation());
        assertEquals("Test worked",    rfModel.getInitialEvaluation());
    }
    /**Title: Return Selected Disposition
     * Description: Test the get method for Return Selected Disposition retrieval
     * Requirement: 4.9, 4.9.1
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Repair and add to inventory, credit customer
     * Expected = Repair and add to inventory, credit customer
     * Actual = Repair and add to inventory, credit customer
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedDisposition() {rfModel.getSelectedDisposition();
        System.out.println("TC_77 was ran." + System.lineSeparator() +"Expected = Repair and add to inventory, credit customer" + System.lineSeparator() +
                "Actual = " +     rfModel.getSelectedDisposition());
        assertEquals("Repair and add to inventory, credit customer",    rfModel.getSelectedDisposition());
    }
    /**Title: Return Disposition Notes
     * Description: Test the get method for Return Disposition Notes retrieval
     * Requirement: 4.9, 4.9.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: White Box Testing
     * Expected = White Box Testing
     * Actual = White Box Testing
     * Test Outcome: Passed
     */
    @Test
    public void getDispositionNotes() {rfModel.getDispositionNotes();
        System.out.println("TC_78 was ran." + System.lineSeparator() +"Expected = White Box Testing" + System.lineSeparator() +
                "Actual = " +  rfModel.getDispositionNotes());
        assertEquals("White Box Testing",  rfModel.getDispositionNotes());
    }
    /**Title: Return Replacement Tracking number
     * Description: Test the get method for Return Replacement Tracking number retrieval
     * Requirement: 4.14
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: null
     * Expected = empty
     * Actual = empty
     * Test Outcome: Passed
     */
    @Test
    public void getReplacementTrackingNumber() {rfModel.getReplacementTrackingNumber();
        System.out.println("TC_79 was ran." + System.lineSeparator() +"Expected = empty" + System.lineSeparator() +
                "Actual = " + rfModel.getReplacementTrackingNumber());
        assertEquals("",  rfModel.getReplacementTrackingNumber());
    }
    /**Title: Return Replacement Ship Date
     * Description: Test the get method for Return Replacement Ship Date retrieval
     * Requirement: 4.15
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: null
     * Expected = null
     * Actual = null
     * Test Outcome: Passed
     */
    @Test
    public void getReplacementShipDate() {rfModel.getReplacementShipDate();
        System.out.println("TC_80 was ran." + System.lineSeparator() +"Expected = null" + System.lineSeparator() +
                "Actual = " + rfModel.getReplacementShipDate());
        assertEquals(null,  rfModel.getReplacementShipDate());
    }
    /**Title: Return Ship Replacement Repair
     * Description: Test the get method for Return Ship Replacement Repairretrieval
     * Requirement: 4.16
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: false
     * Expected = false
     * Actual = false
     * Test Outcome: Passed
     */
    @Test
    public void getShipReplacementRepair() {rfModel.getShipReplacementRepair();
        System.out.println("TC_81 was ran." + System.lineSeparator() +"Expected = false" + System.lineSeparator() +
                "Actual = " + rfModel.getShipReplacementRepair());
        assertEquals(false,  rfModel.getShipReplacementRepair());
    }
    /**Title: HashMap Arraylist converter
     * Description: Test the get method that converts a hashmap to an arraylist
     * Requirement: N/A
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: {SHP_ERROR-STK=Wrong Item shipped in error, BROKEN-RET=Damaged-Replace when returned, " +
     *                 "BROKEN-XSHIP=Damaged-Replace Immediately, ERROR=Customer ordered wrong item, BROKEN-FLD=Scrapped by Customer}
     * Expected = {SHP_ERROR-STK=Wrong Item shipped in error, BROKEN-RET=Damaged-Replace when returned, " +
     *                 "BROKEN-XSHIP=Damaged-Replace Immediately, ERROR=Customer ordered wrong item, BROKEN-FLD=Scrapped by Customer}
     * Actual = {SHP_ERROR-STK=Wrong Item shipped in error, BROKEN-RET=Damaged-Replace when returned, " +
     *                 "BROKEN-XSHIP=Damaged-Replace Immediately, ERROR=Customer ordered wrong item, BROKEN-FLD=Scrapped by Customer}
     * Test Outcome: Passed
     */
    @Test
    public void convertHashMapToArrayList() throws SQLException {
        ArrayList<String> testString = new ArrayList<>();
        testString.add("SHP_ERROR-STK Wrong Item shipped in error");
        testString.add("BROKEN-RET Damaged-Replace when returned");
        testString.add("BROKEN-XSHIP Damaged-Replace Immediately");
        testString.add("ERROR Customer ordered wrong item");
        testString.add("BROKEN-FLD Scrapped by Customer");
        System.out.println("TC_82 was ran." + System.lineSeparator() +"Expected = {SHP_ERROR-STK=Wrong Item shipped in error, BROKEN-RET=Damaged-Replace when returned, " +
                "BROKEN-XSHIP=Damaged-Replace Immediately, ERROR=Customer ordered wrong item, BROKEN-FLD=Scrapped by Customer}" + System.lineSeparator() +
                "Actual = " + db.getReturnReasonCodes());
        assertEquals(testString.toString(), rfModel.convertHashMapToArrayList(db.getReturnReasonCodes()).toString());

    }
}