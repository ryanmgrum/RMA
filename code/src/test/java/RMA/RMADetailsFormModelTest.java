package RMA;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class RMADetailsFormModelTest extends RMAFormModelTest {
    private RMADetailsFormModel detailsFormTest;
    private DBService db;
    private String userPW;
    private LocalDateTime dateTimeCreatedTest, dateTimeLastModTest;

    @Before
    @Override
    public void setUp() throws Exception {
        userPW = "engineer";
        db = DBService.getInstance(userPW, userPW, "KINGKAI-HP\\SQLEXPRESS");
        super.setUp();
        detailsFormTest = new RMADetailsFormModel();
        detailsFormTest.getRMADetails("1");
        String str = "2021-04-25 10:00:25";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String strLM = "2021-04-26 18:32:17";
        dateTimeLastModTest = LocalDateTime.parse(strLM, formatter);
        dateTimeCreatedTest = LocalDateTime.parse(str, formatter);
    }
    /**Title: Return RMA Id method
     * Description: Test the get method and should return the RMAId that was initialized
     * Requirement: 5.2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = 1
     * Expected Result: 1
     * Actual Result: 1
     * Test Outcome: Passed
     */
    @Test
    public void getRmaId() {
        detailsFormTest.getRmaId();
        System.out.println("TC_83 was ran." + System.lineSeparator() +"Expected = 1 "+ System.lineSeparator() +
                "Actual = " + detailsFormTest.getRmaId());
        assertEquals("1",detailsFormTest.getRmaId());
    }
    /**Title: Set RMA ID
     * Description: RMA ID is set and returns the correct RMA ID
     * Requirement: 5.2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: RMAId = p
     * Expected Result: p
     * Actual Result: p
     * Test Outcome: Passed
     */
    @Test
    public void setRmaId() {
        detailsFormTest.setRmaId("p");
        System.out.println("TC_84 was ran." + System.lineSeparator() +"Expected = p "+ System.lineSeparator() +
                "Actual = " + detailsFormTest.getRmaId());
        assertEquals("p", detailsFormTest.getRmaId());
    }
    /**Title: Return Customer Name
     * Description: Test get method to return customer name
     * Requirement: 4.1, 4.1.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Address ID = 4
     * Expected = Everything Inc.
     * Actual = Everything Inc..
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedCustomerName() {
        detailsFormTest.getSelectedCustomerName();
        System.out.println("TC_85 was ran." + System.lineSeparator() +"Expected = Everything Inc. "+ System.lineSeparator() +
                "Actual = " + detailsFormTest.getSelectedCustomerName());
        assertEquals("Everything Inc.",detailsFormTest.getSelectedCustomerName());
    }
    /**Title: Sets New Customer Name
     * Description: Sets the customer name and returns the new Customer name
     * Requirement: 4.1, 4.1.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Address ID = 4
     * Expected = Big Food Inc.
     * Actual = Big Food Inc..
     * Test Outcome: Passed
     */
    @Test
    public void setSelectedCustomerName() {
        detailsFormTest.setSelectedCustomerName("Big Food Inc.");
        System.out.println("TC_86 was ran." + System.lineSeparator() +"Expected = Big Food Inc. "+ System.lineSeparator() +
                "Actual = " + detailsFormTest.getSelectedCustomerName());
        assertEquals("Big Food Inc.",detailsFormTest.getSelectedCustomerName());
    }
    /**Title: Return Business Name
     * Description: Test get method to return Business name
     * Requirement: 4.2,4.2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Five Finger Discounts
     * Expected =Five Finger Discounts
     * Actual = Five Finger Discounts
     */
    @Test
    public void getSelectedBusinessName() throws SQLException {
        detailsFormTest.getSelectedBusinessName();
        System.out.println("TC_87 was ran." + System.lineSeparator() +"Expected = Five Finger Discounts" + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getSelectedBusinessName());
        assertEquals("Five Finger Discounts", detailsFormTest.getSelectedBusinessName().toString());
    }
    /**Title: Sets Business Name
     * Description: Test Sets Business Name return Business name
     * Requirement: 4.2,4.2.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: address Id = 1
     * Expected = Big Food Inc.
     * Matt's Mini-Mart
     * 2263 Gage Hill Rd
     * Door 2
     * Hopkinton
     * Merrimack
     * NH
     * 03229
     * United States of America
     * Phone: 603-584-8662
     * Fax: 487-568-9855
     *
     * Actual = Big Food Inc.
     * Matt's Mini-Mart
     * 2263 Gage Hill Rd
     * Door 2
     * Hopkinton
     * Merrimack
     * NH
     * 03229
     * United States of America
     * Phone: 603-584-8662
     * Fax: 487-568-9855
     */
    @Test
    public void setSelectedBusinessName() throws SQLException {
        detailsFormTest.setSelectedBusinessName(db.getCustomerAddress(1));
        System.out.println("TC_88 was ran." + System.lineSeparator() +"Expected = " + db.getCustomerAddress(1).toString()  + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getSelectedBusinessName());
        assertEquals(db.getCustomerAddress(1).toString(), detailsFormTest.getSelectedBusinessName().toString());
    }

    @Test
    public void clearBusinessNames() {
    }
    /**Title: Return Created Date
     * Description: Test the get method for created date retrieval
     * Requirement: 5.2.5
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: 2021-04-25T10:00:25
     * Expected = 2021-04-25T10:00:25.967
     * Actual = 2021-04-25T10:00:25.967
     * Test Outcome: Passed
     */
    @Test
    public void getCreated() {
        detailsFormTest.getCreated();
        System.out.println("TC_89 was ran." + System.lineSeparator() +"Expected = " + dateTimeCreatedTest + ".967" + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getCreated());
        assertEquals(dateTimeCreatedTest.toString() + ".967", detailsFormTest.getCreated().toString());
    }
    /**Title: Return Created Date
     * Description: Test the set method for created date retrieval
     * Requirement: 5.2.5
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: 2021-04-25T10:00:25
     * Expected = 2021-04-25T10:00:25.967
     * Actual = 2021-04-25T10:00:25.967
     * Test Outcome: Passed
     */
    @Test
    public void setCreated() {
        LocalDateTime newDT = LocalDateTime.now();
        detailsFormTest.setCreated(newDT);
        System.out.println("TC_90 was ran." + System.lineSeparator() +"Expected = " + newDT  + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getCreated().toString());
        assertEquals(newDT , detailsFormTest.getCreated());
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
        detailsFormTest.getCreatedBy();
        System.out.println("TC_91 was ran." + System.lineSeparator() +"Expected = analyst" + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getCreatedBy());
        assertEquals("analyst", detailsFormTest.getCreatedBy());
    }
    /**Title: Set Created By
     * Description: Test the set method for created by retrieval
     * Requirement: 5.2.5
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: engineer
     * Expected = engineer
     * Actual = engineer
     * Test Outcome: Passed
     */
    @Test
    public void setCreatedBy() {
        detailsFormTest.setCreatedBy(userPW);
        System.out.println("TC_92 was ran." + System.lineSeparator() +"Expected = " + userPW + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getCreatedBy());
        assertEquals(userPW, detailsFormTest.getCreatedBy());
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
        detailsFormTest.getLastModified();
        System.out.println("TC_93 was ran." + System.lineSeparator() +"Expected = " + dateTimeLastModTest + ".643" + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getLastModified());
        assertEquals(dateTimeLastModTest + ".643", detailsFormTest.getLastModified().toString());
    }
    /**Title: Set Last Modified Date
     * Description: Test the Set method for Last Modified Date
     * Requirement: 5.2, 5.2.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input:  Local Date and time right now
     * Expected =  Local Date and time right now
     * Actual =  Local Date and time right now
     * Test Outcome: Passed
     */
    @Test
    public void setLastModified() {
        LocalDateTime newLM = LocalDateTime.now();
        detailsFormTest.setLastModified(newLM);
        System.out.println("TC_93 was ran." + System.lineSeparator() +"Expected = " + newLM + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getLastModified());
        assertEquals(newLM , detailsFormTest.getLastModified());
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
        detailsFormTest.getLastModifiedBy();
        System.out.println("TC_94 was ran." + System.lineSeparator() +"Expected = analyst" + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getLastModifiedBy());
        assertEquals("analyst", detailsFormTest.getLastModifiedBy());
    }
    /**Title: Set Last Modified By
     * Description: Test the Set method for Last Modified By retrieval
     * Requirement: 5.2, 5.2.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: engineer
     * Expected = engineer
     * Actual = engineer
     * Test Outcome: Passed
     */
    @Test
    public void setLastModifiedBy() {
        detailsFormTest.setLastModifiedBy(userPW);
        System.out.println("TC_95 was ran." + System.lineSeparator() +"Expected = engineer" + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getLastModifiedBy());
        assertEquals("engineer", detailsFormTest.getLastModifiedBy());
    }
    /**Title: Return RMA Status
     * Description: Test the get method for RMA Status retrieval
     * Requirement: 5.2, 5.2.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Dropped at Shop
     * Expected = Dropped at Shop
     * Actual = Dropped at Shop
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedRMAStatus() {
        detailsFormTest.getSelectedRMAStatus();
        System.out.println("TC_96 was ran." + System.lineSeparator() +"Expected = Dropped at Shop" + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getSelectedRMAStatus());
        assertEquals("Dropped at Shop", detailsFormTest.getSelectedRMAStatus());
    }
    /**Title: Set RMA Status
     * Description: Test the set method for RMA Status and then retrieve it
     * Requirement: 5.2, 5.2.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Diagnose
     * Expected = Diagnose
     * Actual = Diagnose
     * Test Outcome: Passed
     */
    @Test
    public void setSelectedRMAStatus() {
        detailsFormTest.setSelectedRMAStatus("Diagnose");
        System.out.println("TC_97 was ran." + System.lineSeparator() +"Expected = Diagnose" + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getSelectedRMAStatus());
        assertEquals("Diagnose", detailsFormTest.getSelectedRMAStatus());
    }
    /**Title: Return PO Number
     * Description: Test the get method for PO number retrieval
     * Requirement: 4.11, 4.11.1 4.11.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: ZEIETJ39483
     * Expected = ZEIETJ39483
     * Actual = ZEIETJ39483
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedPONumber() {
        detailsFormTest.getSelectedPONumber();
        System.out.println("TC_98 was ran." + System.lineSeparator() +"Expected = ZEIETJ39483" + System.lineSeparator() +
                "Actual = " +   detailsFormTest.getSelectedPONumber());
        assertEquals("ZEIETJ39483",  detailsFormTest.getSelectedPONumber());
    }
    /**Title: Set PO Number
     * Description: Test the set method for PO number then retrieve it
     * Requirement: 4.11, 4.11.1 4.11.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: 458695345
     * Expected = 458695345
     * Actual = 458695345
     * Test Outcome: Passed
     */
    @Test
    public void setSelectedPONumber() {
        detailsFormTest.setSelectedPONumber("458695345");
        System.out.println("TC_99 was ran." + System.lineSeparator() +"Expected = 458695345" + System.lineSeparator() +
                "Actual = " +   detailsFormTest.getSelectedPONumber());
        assertEquals("458695345",  detailsFormTest.getSelectedPONumber());
    }

    @Test
    public void clearPONumbers() {
    }
    /**Title: Return Return Reason Code
     * Description: Test the get method for Return Reason Code retrieval
     * Requirement: 4.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: BROKEN-XSHIP Damaged-Replace Immediately
     * Expected = BROKEN-XSHIP Damaged-Replace Immediately
     * Actual = BROKEN-XSHIP Damaged-Replace Immediately
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedReturnReasonCode() {
        detailsFormTest.getSelectedReturnReasonCode();
        System.out.println("TC_100 was ran." + System.lineSeparator() +"Expected = BROKEN-XSHIP Damaged-Replace Immediately" + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getSelectedReturnReasonCode());
        assertEquals("BROKEN-XSHIP Damaged-Replace Immediately",  detailsFormTest.getSelectedReturnReasonCode());
    }
    /**Title: Set Return Reason Code
     * Description: Test the set method for Return Reason Code then retieves it
     * Requirement: 4.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: ERROR
     * Expected = ERROR
     * Actual = ERROR
     * Test Outcome: Passed
     */
    @Test
    public void setSelectedReturnReasonCode() {
        detailsFormTest.setSelectedReturnReasonCode("ERROR");
        System.out.println("TC_101 was ran." + System.lineSeparator() +"Expected = ERROR" + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getSelectedReturnReasonCode());
        assertEquals("ERROR",  detailsFormTest.getSelectedReturnReasonCode());
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
    public void getSelectedCreditReplaceRepair() {
        detailsFormTest.getSelectedCreditReplaceRepair();
        System.out.println("TC_102 was ran." + System.lineSeparator() +"Expected = Replace" + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getSelectedCreditReplaceRepair());
        assertEquals("Replace",  detailsFormTest.getSelectedCreditReplaceRepair());
    }
    /**Title: Set Credit Replace Repair
     * Description: Test the Set method for Credit Replace Repair retrieval
     * Requirement: 4.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: credit
     * Expected = credit
     * Actual = credit
     * Test Outcome: Passed
     */
    @Test
    public void setSelectedCreditReplaceRepair() {
        detailsFormTest.setSelectedCreditReplaceRepair("credit");
        System.out.println("TC_102 was ran." + System.lineSeparator() +"Expected = credit" + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getSelectedCreditReplaceRepair());
        assertEquals("credit",  detailsFormTest.getSelectedCreditReplaceRepair());
    }
    /**Title: Return Shipping Address
     * Description: Test the get method for Shipping Address retrieval
     * Requirement: 4.12, 4.12.1
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: addressId 2
     * Expected = Everything Inc.
     * Five Finger Discounts
     * 18 Burnette St
     * Chattanooga
     * Hamilton
     * TN
     * 37408
     * United States of America
     * Phone: 423-879-6856
     * Fax: 423-555-5556
     * Actual = Everything Inc.
     * Five Finger Discounts
     * 18 Burnette St
     * Chattanooga
     * Hamilton
     * TN
     * 37408
     * United States of America
     * Phone: 423-879-6856
     * Fax: 423-555-5556
     */
    @Test
    public void getShippingAddress() throws SQLException {
        detailsFormTest.getShippingAddress();
        System.out.println("TC_103 was ran." + System.lineSeparator() +"Expected = Everything Inc.\n" +
                " Five Finger Discounts\n" +
                " 18 Burnette St\n" +
                " Chattanooga\n" +
                " Hamilton\n" +
                " TN\n" +
                " 37408\n" +
                " United States of America\n" +
                " Phone: 423-879-6856\n" +
                " Fax: 423-555-5556" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getShippingAddress());
        assertEquals(db.getCustomerAddress(5).toString(),   detailsFormTest.getShippingAddress().toString());
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
     */
    @Test
    public void setShippingAddress() throws SQLException {
        detailsFormTest.setShippingAddress(db.getCustomerAddress(3));
        System.out.println("TC_104 was ran." + System.lineSeparator() +"Expected = Electronia Inc.\n" +
                "Solid State Store\n" +
                "250 Industrial Park St\n" +
                "Building #6\n" +
                "Pittsfield\n" +
                "Somerset\n" +
                "ME\n" +
                "04967\n" +
                "United States of America\n" +
                "Phone: 207-689-7885" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getShippingAddress());
        assertEquals(db.getCustomerAddress(3).toString(),   detailsFormTest.getShippingAddress().toString());
    }
    /**Title: Return Additional Info
     * Description: Test the get method for Additional Info retrieval
     * Requirement: 4.13
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: ""
     * Expected =
     * Actual =
     * Test Outcome: Passed
     */
    @Test
    public void getAdditionalInfo() {
        detailsFormTest.getAdditionalInfo();
        System.out.println("TC_105 was ran." + System.lineSeparator() +"Expected = " + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getAdditionalInfo());
        assertEquals("",   detailsFormTest.getAdditionalInfo());
    }
    /**Title: Set Additional Info
     * Description: Test the set method for Additional Info and retrieval
     * Requirement: 4.13
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Testing White Box
     * Expected = Testing White Box
     * Actual = Testing White Box
     * Test Outcome: Passed
     */
    @Test
    public void setAdditionalInfo() {
        detailsFormTest.setAdditionalInfo("Testing White Box");
        System.out.println("TC_105 was ran." + System.lineSeparator() +"Expected = " + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getAdditionalInfo());
        assertEquals("Testing White Box",   detailsFormTest.getAdditionalInfo());
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
    public void getSelectedProduct() {
        detailsFormTest.getSelectedProduct();
        System.out.println("TC_106 was ran." + System.lineSeparator() +"Expected = Electronics, Flip Phone" + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getSelectedProduct());
        assertEquals("Electronics, Flip Phone",   detailsFormTest.getSelectedProduct().toString());
    }
    /**Title: Set Product
     * Description: Test the set method for Product and retrieval
     * Requirement: 4.7, 4.7.1
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Product ID 6
     * Expected = Electronics, Hard Drive
     * Actual = Electronics, Hard Drive
     * Test Outcome: Passed
     */
    @Test
    public void setSelectedProduct() throws SQLException {
        detailsFormTest.setSelectedProduct(db.getPurchaseOrderProduct(6));
        System.out.println("TC_107 was ran." + System.lineSeparator() +"Expected = Food, Pear" + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getSelectedProduct());
        assertEquals("Food, Pear",   detailsFormTest.getSelectedProduct().toString());
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
    public void getReturnQuantity() {
        detailsFormTest.getReturnQuantity();
        System.out.println("TC_108 was ran." + System.lineSeparator() +"Expected = 40" + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getReturnQuantity());
        assertEquals("40",   detailsFormTest.getReturnQuantity().toString());
    }
    /**Title: Set Quality
     * Description: Test the set method for Quality retrieval
     * Requirement: 4.7, 4.7.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: 7
     * Expected = 7
     * Actual = 7
     * Test Outcome: Passed
     */
    @Test
    public void setReturnQuantity() {
        detailsFormTest.setReturnQuantity(7);
        System.out.println("TC_109 was ran." + System.lineSeparator() +"Expected = 7" + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getReturnQuantity());
        assertEquals("7",   detailsFormTest.getReturnQuantity().toString());
    }
    /**Title: Return Return Label Tracker
     * Description: Test the get method for Return Label Tracker retrieval
     * Requirement: 4.7, 4.7.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input:
     * Expected =
     * Actual =
     * Test Outcome: Passed
     */
    @Test
    public void getReturnLabelTracker() {
        detailsFormTest.getReturnLabelTracker();
        System.out.println("TC_110 was ran." + System.lineSeparator() +"Expected = " + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getReturnLabelTracker());
        assertEquals("",  detailsFormTest.getReturnLabelTracker());
    }
    /**Title: Set Return Label Tracker
     * Description: Test the get method for set Label Tracker retrieval
     * Requirement: 4.7, 4.7.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: 123456track
     * Expected = 123456track
     * Actual = 123456track
     * Test Outcome: Passed
     */
    @Test
    public void setReturnLabelTracker() {
        detailsFormTest.setReturnLabelTracker("123456track");
        System.out.println("TC_110 was ran." + System.lineSeparator() +"Expected = 123456track" + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getReturnLabelTracker());
        assertEquals("123456track",  detailsFormTest.getReturnLabelTracker());
    }
    /**Title: Return Initial Evaluation
     * Description: Test the get method for Return Initial Evaluation retrieval
     * Requirement: 4.8
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: empty
     * Expected =
     * Actual =
     * Test Outcome: Passed
     */
    @Test
    public void getInitialEvaluation() {
        detailsFormTest.getInitialEvaluation();
        System.out.println("TC_111 was ran." + System.lineSeparator() +"Expected = " + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getInitialEvaluation());
        assertEquals("",   detailsFormTest.getInitialEvaluation());
    }
    /**Title: Set Initial Evaluation
     * Description: Test the set method for Return Initial Evaluation and retrieval
     * Requirement: 4.8
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: empty
     * Expected =
     * Actual =
     * Test Outcome: Passed
     */
    @Test
    public void setInitialEvaluation() {
        detailsFormTest.setInitialEvaluation("TEst worked");
        System.out.println("TC_112 was ran." + System.lineSeparator() +"Expected = TEst worked" + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getInitialEvaluation());
        assertEquals("TEst worked",   detailsFormTest.getInitialEvaluation());
    }
    /**Title: Return Selected Disposition
     * Description: Test the get method for Return Selected Disposition retrieval
     * Requirement: 4.9, 4.9.1
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: empty
     * Expected =
     * Actual =
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedDisposition() {
        detailsFormTest.getSelectedDisposition();
        System.out.println("TC_113 was ran." + System.lineSeparator() +"Expected = " + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getSelectedDisposition());
        assertEquals("",    detailsFormTest.getSelectedDisposition());
    }
    /**Title: Set Selected Disposition
     * Description: Test the set method for Return Set Disposition and retrieval
     * Requirement: 4.9, 4.9.1
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Repair and add to inventory, credit customer
     * Expected = Repair and add to inventory, credit customer
     * Actual =Repair and add to inventory, credit customer
     * Test Outcome: Passed
     */
    @Test
    public void setSelectedDisposition() {
        detailsFormTest.setSelectedDisposition("Repair and add to inventory, credit customer");
        System.out.println("TC_114 was ran." + System.lineSeparator() +"Expected = Repair and add to inventory, credit customer" + System.lineSeparator() +
                "Actual = " +    detailsFormTest.getSelectedDisposition());
        assertEquals("Repair and add to inventory, credit customer",    detailsFormTest.getSelectedDisposition());
    }
    /**Title: Return Disposition Notes
     * Description: Test the get method for Return Disposition Notes retrieval
     * Requirement: 4.9, 4.9.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Empty
     * Expected =
     * Actual =
     * Test Outcome: Passed
     */
    @Test
    public void getDispositionNotes() {
        detailsFormTest.getDispositionNotes();
        System.out.println("TC_115 was ran." + System.lineSeparator() +"Expected = " + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getDispositionNotes());
        assertEquals("",  detailsFormTest.getDispositionNotes());
    }
    /**Title: Set Disposition Notes
     * Description: Test the set method for Return Disposition Notes and retrieval
     * Requirement: 4.9, 4.9.2
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: This test worked as expected
     * Expected = This test worked as expected
     * Actual = This test worked as expected
     * Test Outcome: Passed
     */
    @Test
    public void setDispositionNotes() {
        detailsFormTest.setDispositionNotes("This test worked as expected");
        System.out.println("TC_116 was ran." + System.lineSeparator() +"Expected = This test worked as expected" + System.lineSeparator() +
                "Actual = " +  detailsFormTest.getDispositionNotes());
        assertEquals("This test worked as expected",  detailsFormTest.getDispositionNotes());
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
    public void getReplacementTrackingNumber() {
        detailsFormTest.getReplacementTrackingNumber();
        System.out.println("TC_117 was ran." + System.lineSeparator() +"Expected = " + System.lineSeparator() +
                "Actual = " + detailsFormTest.getReplacementTrackingNumber());
        assertEquals("",  detailsFormTest.getReplacementTrackingNumber());
    }
    /**Title: Return Replacement Tracking number
     * Description: Test the get method for Return Replacement Tracking number retrieval
     * Requirement: 4.14
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: 074otworked
     * Expected = 074otworked
     * Actual = 074otworked
     * Test Outcome: Passed
     */
    @Test
    public void setReplacementTrackingNumber() {
        detailsFormTest.setReplacementTrackingNumber("074otworked");
        System.out.println("TC_118 was ran." + System.lineSeparator() +"Expected = 074otworked" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getReplacementTrackingNumber());
        assertEquals("074otworked",  detailsFormTest.getReplacementTrackingNumber());
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
    public void getReplacementShipDate() {
        detailsFormTest.getReplacementShipDate();
        System.out.println("TC_119 was ran." + System.lineSeparator() +"Expected = null" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getReplacementShipDate());
        assertEquals(null,  detailsFormTest.getReplacementShipDate());
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
    public void setReplacementShipDate() {
        LocalDate newLM = LocalDate.now();
        detailsFormTest.setReplacementShipDate(newLM);
        System.out.println("TC_120 was ran." + System.lineSeparator() +"Expected = " + newLM + System.lineSeparator() +
                "Actual = " + detailsFormTest.getReplacementShipDate());
        assertEquals( newLM,  detailsFormTest.getReplacementShipDate());
    }
    /**Title: Return Ship Replacement Repair
     * Description: Test the get method for Return Ship Replacement Repair retrieval
     * Requirement: 4.16
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: false
     * Expected = false
     * Actual = false
     * Test Outcome: Passed
     */
    @Test
    public void getShipReplacementRepair() {
        detailsFormTest.getShipReplacementRepair();
        System.out.println("TC_121 was ran." + System.lineSeparator() +"Expected = false" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getShipReplacementRepair());
        assertEquals(false,  detailsFormTest.getShipReplacementRepair());
    }
    /**Title: Set Ship Replacement Repair
     * Description: Test the set method for Return Ship Replacement Repair retrieval
     * Requirement: 4.16
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: true
     * Expected = true
     * Actual = true
     * Test Outcome: Passed
     */
    @Test
    public void setShipReplacementRepair() {
        detailsFormTest.setShipReplacementRepair(true);
        System.out.println("TC_122 was ran." + System.lineSeparator() +"Expected = false" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getShipReplacementRepair());
        assertEquals(true,  detailsFormTest.getShipReplacementRepair());
    }
    /**Title: Get RMA Detail
     * Description: Test the set method for Return Ship Replacement Repair retrieval
     * Requirement: 5.0
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: p
     * Expected = RMAId is set on details to input
     * Actual = RMAId is set on details to input
     * Test Outcome: Passed
     */
    @Test
    public void getRMADetails() throws SQLException {
        detailsFormTest.getRMADetails("p");
        System.out.println("TC_123 was ran." + System.lineSeparator() +"Expected = p" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getRmaId());
    }
    /**Title: Get RMA Progress
     * Description: Test the get method for Progress
     * Requirement: 4.16
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: 0.0
     * Expected = 0.0
     * Actual = 0.0
     * Test Outcome: Passed
     */
    @Test
    public void getRMAProgress() {
        System.out.println("TC_124 was ran." + System.lineSeparator() +"Expected = 0.0" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getRMAProgress());
        assertEquals("0.0",String.valueOf(detailsFormTest.getRMAProgress()));

    }
    /**Title: Get Age
     * Description: Test the get method for Age
     * Requirement: 4.16
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Date.now - creation date
     * Expected = 0.0
     * Actual = 0.0
     * Test Outcome: Passed
     */
    @Test
    public void getAge() {
        detailsFormTest.getAge();
        System.out.println("TC_126 was ran." + System.lineSeparator() +"Expected = 0" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getAge());
        assertEquals(0,detailsFormTest.getAge());
    }
    /**Title: Update Age
     * Description: Test the get method for Update Age
     * Requirement: 4.16
     * Pre Condition = the actual number would need to meet the input therefore this test would fail if Expected is not updated
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: Date.now - creation date
     * Expected = 5
     * Actual = 5
     * Test Outcome: Passed
     */
    @Test
    public void updateAge() {
        detailsFormTest.updateAge();
        System.out.println("TC_126 was ran." + System.lineSeparator() +"Expected = 5" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getAge());
        assertEquals(5,detailsFormTest.getAge());
    }
    /**Title: Get Owner
     * Description: Test the get method for Owner
     * Requirement: 5.2.6
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: analyst
     * Expected = analyst
     * Actual = analyst
     * Test Outcome: Passed
     */
    @Test
    public void getSelectedOwner() {
        detailsFormTest.getSelectedOwner();
        System.out.println("TC_127 was ran." + System.lineSeparator() +"Expected = analyst" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getSelectedOwner());
        assertEquals("analyst",detailsFormTest.getSelectedOwner());
    }
    /**Title: Set Owner
     * Description: Test the Set method for Owner
     * Requirement: 5.2.6
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: engineer
     * Expected = engineer
     * Actual = engineer
     * Test Outcome: Passed
     */
    @Test
    public void setSelectedOwner() {
        detailsFormTest.setSelectedOwner("engineer");
        System.out.println("TC_128 was ran." + System.lineSeparator() +"Expected = engineer" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getSelectedOwner());
        assertEquals("engineer",detailsFormTest.getSelectedOwner());
    }
    /**Title: Get Engineering Eval
     * Description: Test the get Engineering Evaluation Method
     * Requirement: 5.6.2, 5.6.2.1, 5.6.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: engineer
     * Expected = engineer
     * Actual = engineer
     * Test Outcome: Passed
     */
    @Test
    public void getEngineeringEvaluation() {
        detailsFormTest.getEngineeringEvaluation();
        System.out.println("TC_129 was ran." + System.lineSeparator() +"Expected = " + System.lineSeparator() +
                "Actual = " + detailsFormTest.getEngineeringEvaluation());
        assertEquals("",detailsFormTest.getEngineeringEvaluation());
    }
    /**Title: Set Engineering Eval
     * Description: Test the set Engineering Evaluation Method
     * Requirement: 5.6.2, 5.6.2.1, 5.6.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: engineer evaluated this
     * Expected = engineer evaluated this
     * Actual = engineer evaluated this
     * Test Outcome: Passed
     */
    @Test
    public void setEngineeringEvaluation() {
        detailsFormTest.setEngineeringEvaluation("engineer evaluated this");
        System.out.println("TC_130 was ran." + System.lineSeparator() +"Expected = engineer evaluated this" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getEngineeringEvaluation());
        assertEquals("engineer evaluated this",detailsFormTest.getEngineeringEvaluation());
    }
    /**Title: Get Details Window
     * Description: Test the get Details Window Method
     * Requirement: 5.6.E2, 5.6.2.1, 5.6.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: empty
     * Expected = ""
     * Actual = ""
     * Test Outcome: Passed
     */
    @Test
    public void getDetailsWindowText() {
        detailsFormTest.getDetailsWindowText();
        System.out.println("TC_131 was ran." + System.lineSeparator() +"Expected = " + System.lineSeparator() +
                "Actual = " + detailsFormTest.getDetailsWindowText());
        assertEquals("",detailsFormTest.getDetailsWindowText());
    }
    /**Title: Get Details Window
     * Description: Test the get Details Window Method
     * Requirement: 5.6.E2, 5.6.2.1, 5.6.3
     * Assumptions: No issue connecting to the Database, RMA already exist
     * Input: empty
     * Expected = ""
     * Actual = ""
     * Test Outcome: Passed
     */
    @Test
    public void setDetailsWindowText() {
        detailsFormTest.setDetailsWindowText(detailsFormTest.getSelectedBusinessName().toString());
        System.out.println("TC_132 was ran." + System.lineSeparator() +"Expected = Five Finger Discounts" + System.lineSeparator() +
                "Actual = " + detailsFormTest.getDetailsWindowText());
        assertEquals("Five Finger Discounts",detailsFormTest.getDetailsWindowText());
    }
}