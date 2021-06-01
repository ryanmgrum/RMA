package RMA;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
/** Test PurchaseOrderProduct Class and all methods
 * (Requirement: 3.0, 3.5, 3.6, 4.7, 4.7.1, 4.11, 4.11.1)
 */
public class PurchaseOrderProductTest {

    private PurchaseOrderProduct poTest;
    private int purchaseOrderProductId, quantity;
    private String poNumber, productName, categoryName;
    private LocalDate orderDate, deliverDate;

    @Before
    public void setUp() throws Exception {

        this.purchaseOrderProductId = 2;
        this.poNumber = "3C2218QN";
        this.productName = "Blueberry";
        this.categoryName = "Food";
        this.quantity = 1000;
        this.orderDate = LocalDate.of(2021,01,01);
        this.deliverDate = LocalDate.of(2021,01,07);
        DBService db = DBService.getInstance("engineer","engineer","KINGKAI-HP\\SQLEXPRESS");
        poTest = db.getPurchaseOrderProduct(2);//TODO need to fix

    }
    /**Title: Test Product and Category Name to string
     * Description: Test the function to change the product and category name to string
     * Requirement: 3.6
     * Assumptions: No issue connecting to the Database
     * Input: purchaseOrderProductId = 2
     * Expected Result: Food, Blueberry
     * Actual Result: Food, Blueberry
     * Test Outcome: Passed
     */
    @Test
    public void testToString() {

        System.out.println("TC_27 was ran." + System.lineSeparator() +"Expected = " + String.format("%s, %s", categoryName, productName) + System.lineSeparator() +
                "Actual = " + poTest.toString());
        assertEquals(String.format("%s, %s", categoryName, productName), poTest.toString());
    }
    /**Title: Test Retrieval Purchase Order ID
     * Description: Test the function to get the PO Id from database
     * Requirement: 3.0
     * Assumptions: No issue connecting to the Database
     * Input: purchaseOrderProductId = 2
     * Expected Result: 2
     * Actual Result: 2
     * Test Outcome: Passed
     */
    @Test
    public void getPurchaseOrderProductId() {
        System.out.println("TC_28 was ran." + System.lineSeparator() +"Expected = " + purchaseOrderProductId + System.lineSeparator() +
                "Actual = " + poTest.getPurchaseOrderProductId());
        assertEquals(purchaseOrderProductId, poTest.getPurchaseOrderProductId());
    }
    /**Title: Test Retrieval PO Number
     * Description: Test the function to get the PO Number from database
     * Requirement: 3.0, 3.5, 4.11, 4.11.1
     * Assumptions: No issue connecting to the Database
     * Input: purchaseOrderProductId = 2
     * Expected Result: 3C2218QN
     * Actual Result: 3C2218QN
     * Test Outcome: Passed
     */
    @Test
    public void getPONumber() {
        System.out.println("TC_28 was ran." + System.lineSeparator() +"Expected = " + poNumber + System.lineSeparator() +
                "Actual = " + poTest.getPONumber());
        assertEquals(poNumber, poTest.getPONumber());
    }
    /**Title: Test Retrieval Product Name
     * Description: Test the function to get the Product Name from database
     * Requirement: 3.0, 3.2, 4.7, 4.7.1
     * Assumptions: No issue connecting to the Database
     * Input: purchaseOrderProductId = 2
     * Expected Result: Blueberry
     * Actual Result: Blueberry
     * Test Outcome: Passed
     */
    @Test
    public void getProductName() {
        System.out.println("TC_29 was ran." + System.lineSeparator() +"Expected = " + productName + System.lineSeparator() +
                "Actual = " + poTest.getProductName());
        assertEquals(productName, poTest.getProductName());
    }
   /**Title: Test Retrieval Product Category
     * Description: Test the function to get the Product Category from database
     * Requirement: 3.0, 3.1, 4.7, 4.7.1
     * Assumptions: No issue connecting to the Database
     * Input: purchaseOrderProductId = 2
     * Expected Result: Food
     * Actual Result: Food
     * Test Outcome: Passed
     */
    @Test
    public void getProductCategory() {
        System.out.println("TC_30 was ran." + System.lineSeparator() +"Expected = " + categoryName + System.lineSeparator() +
                "Actual = " + poTest.getProductCategory());
        assertEquals(categoryName, poTest.getProductCategory());
    }
    /**Title: Test Retrieval Quantity
     * Description: Test the function to get the Quantity from database
     * Requirement: 3.0
     * Assumptions: No issue connecting to the Database
     * Input: purchaseOrderProductId = 2
     * Expected Result: 1000
     * Actual Result: 1000
     * Test Outcome: Passed
     */
    @Test
    public void getQuantity() {
        System.out.println("TC_31 was ran." + System.lineSeparator() +"Expected = " + quantity + System.lineSeparator() +
                "Actual = " + poTest.getQuantity());
        assertEquals(quantity, poTest.getQuantity());
    }
    /**Title: Test Retrieval Order Date
     * Description: Test the function to get the Order Date from database
     * Requirement: 3.0
     * Assumptions: No issue connecting to the Database
     * Input: purchaseOrderProductId = 2
     * Expected Result: 2021,01,01
     * Actual Result: 2021,01,01
     * Test Outcome: Passed
     */
    @Test
    public void getOrderDate() {
        System.out.println("TC_32 was ran." + System.lineSeparator() +"Expected = " + orderDate + System.lineSeparator() +
                "Actual = " + poTest.getOrderDate());
        assertEquals(orderDate, poTest.getOrderDate());
    }
    /**Title: Test Retrieval Delivery Date
     * Description: Test the function to get the Delivery Date from database
     * Requirement: 3.0
     * Assumptions: No issue connecting to the Database
     * Input: purchaseOrderProductId = 2
     * Expected Result: 2021,01,07
     * Actual Result: 2021,01,07
     * Test Outcome: Passed
     */
    @Test
    public void getDeliverDate() {
        System.out.println("TC_33 was ran." + System.lineSeparator() +"Expected = " + deliverDate + System.lineSeparator() +
                "Actual = " + poTest.getDeliverDate());
        assertEquals(deliverDate, poTest.getDeliverDate());
    }
}