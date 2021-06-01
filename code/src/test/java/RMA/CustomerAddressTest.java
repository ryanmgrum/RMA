package RMA;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/** Test CustomerAddress Class and all methods
 * (Requirement: 3.0, 3.4)
 */
public class CustomerAddressTest {
    private CustomerAddress caTest;
    private int addressIdTest;
    private String customerNameTest, businessNameTest, address1Test, address2Test, cityTest, countyTest, stateOrProvinceTest,
    zipTest, countryTest,phoneTest,faxTest;
    private boolean showBusinessNameTest;

    @Before
    public void setUp() throws Exception {

        customerNameTest = "Big Food Inc.";
        businessNameTest = "George's Corner Store";
        address1Test = "63 S Mission Rd";
        address2Test = "";
        cityTest = "Eastborough";
        countyTest = "Sedgwick";
        stateOrProvinceTest = "KS";
        zipTest = "67207";
        countryTest = "United States of America";
        phoneTest = "621-187-3888";
        faxTest = "";
        DBService db = DBService.getInstance("admin","admin","KINGKAI-HP\\SQLEXPRESS");
        caTest = db.getCustomerAddress(0);
        showBusinessNameTest = false;
    }

    /**Title: Test Retrieval of Customer Name
     * Description: Test the function to get the customer name from database
     * Requirement: 4.1
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 0
     * Expected Result: Big Food Inc.
     * Actual Result: Big Food Inc
     * Test Outcome: Passed
     */
    @Test
    public void getCustomerName() {
        System.out.println("TC_01 was ran." + System.lineSeparator() +"Expected = " + customerNameTest + System.lineSeparator() +
                "Actual = " + caTest.getCustomerName());
        assertEquals(customerNameTest, caTest.getCustomerName());
    }

    /**Title: Test Retrieval Business Name
     * Description: Test the function to get the business name from database
     * Requirement: 4.2
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 0
     * Expected Result: George's Corner Store
     * Actual Result: George's Corner Store
     * Test Outcome: Passed
     */
    @Test
    public void getBusinessName() throws SQLException {
        System.out.println("TC_02 was ran." + System.lineSeparator() +"Expected = " + businessNameTest + System.lineSeparator() +
                "Actual = " + caTest.getBusinessName());
        assertEquals(businessNameTest, caTest.getBusinessName());
    }
    /**Title: Test Retrieval Address Line 1
     * Description: Test the function to get the address line 1 from database
     * Requirement: 3.4, 4.12
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 0
     * Expected Result: 63 S Mission Rd
     * Actual Result: 63 S Mission Rd
     * Test Outcome: Passed
     */
    @Test
    public void getAddress1() {
        System.out.println("TC_03 was ran." + System.lineSeparator() +"Expected = " + address1Test + System.lineSeparator() +
                "Actual = " + caTest.getAddress1());
        assertEquals(address1Test, caTest.getAddress1());
    }
    /**Title: Test Retrieval Address Line 2
     * Description: Test the function to get the address line 2 from database
     * Requirement: 3.4, 4.12
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 0
     * Expected Result: empty
     * Actual Result: empty
     * Test Outcome: Passed
     */
    @Test
    public void getAddress2() {
        System.out.println("TC_04 was ran." + System.lineSeparator() +"Expected = " + address2Test + System.lineSeparator() +
                "Actual = " + caTest.getAddress2());
        assertEquals(address2Test, caTest.getAddress2());
    }
    /**Title: Test Retrieval City
     * Description: Test the function to get the city from database
     * Requirement: 3.4, 4.12
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 0
     * Expected Result: Eastborough
     * Actual Result: Eastborough
     * Test Outcome: Passed
     */
    @Test
    public void getCity() {
        System.out.println("TC_05 was ran." + System.lineSeparator() +"Expected = " + cityTest + System.lineSeparator() +
                "Actual = " + caTest.getCity());
        assertEquals(cityTest, caTest.getCity());
    }
    /**Title: Test Retrieval County
     * Description: Test the function to get the city from database
     * Requirement: 3.4, 4.12
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 0
     * Expected Result: Sedgwick
     * Actual Result: Sedgwick
     * Test Outcome: Passed
     */
    @Test
    public void getCounty() {
        System.out.println("TC_06 was ran." + System.lineSeparator() +"Expected = " + countyTest + System.lineSeparator() +
                "Actual = " + caTest.getCounty());
        assertEquals(countyTest, caTest.getCounty());
    }
    /**Title: Test Retrieval State or Province
     * Description: Test the function to get the state or province from database
     * Requirement: 3.4, 4.12
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 0
     * Expected Result: KS
     * Actual Result: KS
     * Test Outcome: Passed
     */
    @Test
    public void getStateOrProvince() {
        System.out.println("TC_07 was ran." + System.lineSeparator() +"Expected = " + stateOrProvinceTest + System.lineSeparator() +
                "Actual = " + caTest.getStateOrProvince());
        assertEquals(stateOrProvinceTest, caTest.getStateOrProvince());
    }
    /**Title: Test Retrieval Zip
     * Description: Test the function to get the zip code from database
     * Requirement: 3.4, 4.12
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 0
     * Expected Result: 67207
     * Actual Result: 67207
     * Test Outcome: Passed
     */
    @Test
    public void getZip() {
        System.out.println("TC_08 was ran." + System.lineSeparator() +"Expected = " + zipTest + System.lineSeparator() +
                "Actual = " + caTest.getZip());
        assertEquals(zipTest, caTest.getZip());
    }
    /**Title: Test Retrieval Country
     * Description: Test the function to get the Country from database
     * Requirement: 3.4, 4.12
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 0
     * Expected Result: United States of America
     * Actual Result: United States of America
     * Test Outcome: Passed
     */
    @Test
    public void getCountry() {
        System.out.println("TC_09 was ran." + System.lineSeparator() +"Expected = " + countryTest + System.lineSeparator() +
                "Actual = " + caTest.getCountry());
        assertEquals(countryTest, caTest.getCountry());
    }
    /**Title: Test Retrieval Phone
     * Description: Test the function to get the phone from database
     * Requirement: 3.4, 4.12
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 0
     * Expected Result: 621-187-3888
     * Actual Result: 621-187-3888
     * Test Outcome: Passed
     */
    @Test
    public void getPhone() {
        System.out.println("TC_10 was ran." + System.lineSeparator() +"Expected = " + phoneTest + System.lineSeparator() +
                "Actual = " + caTest.getPhone());
        assertEquals(phoneTest, caTest.getPhone());
    }
    /**Title: Test Retrieval Fax
     * Description: Test the function to get the fax from database
     * Requirement: 3.4, 4.12
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 0
     * Expected Result: empty
     * Actual Result: empty
     * Test Outcome: Passed
     */
    @Test
    public void getFax() {
        System.out.println("TC_11 was ran." + System.lineSeparator() +"Expected = " + faxTest + System.lineSeparator() +
                "Actual = " + caTest.getFax());
        assertEquals(faxTest, caTest.getFax());
    }
    /**Title: Test Customer Full Address
     * Description: Test the function to put all Customer information together
     * Requirement: 3.3, 3.4
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 0
     * Expected Result:
     * Big Food Inc.
     * George's Corner Store
     * 63 S Mission Rd
     * Eastborough
     * Sedgwick
     * KS
     * 67207
     * United States of America
     * Phone: 621-187-3888
     *
     * Actual Result:
     * Big Food Inc.
     * George's Corner Store
     * 63 S Mission Rd
     * Eastborough
     * Sedgwick
     * KS
     * 67207
     * United States of America
     * Phone: 621-187-3888
     * Test Outcome: Passed
     */
    @Test
    public void testToString() {
        String addressTest;
        addressTest = showBusinessNameTest ? businessNameTest: (customerNameTest.isEmpty() ? "" : customerNameTest + System.lineSeparator()) +
                (businessNameTest.isEmpty() ? "" : businessNameTest + System.lineSeparator()) +
                (address1Test.isEmpty() ? "" : address1Test + System.lineSeparator()) +
                (address2Test.isEmpty() ? "" : address2Test + System.lineSeparator()) +
                (cityTest.isEmpty() ? "" : cityTest + System.lineSeparator()) +
                (countyTest.isEmpty() ? "" : countyTest + System.lineSeparator()) +
                (stateOrProvinceTest.isEmpty() ? "" : stateOrProvinceTest + System.lineSeparator()) +
                (zipTest.isEmpty() ? "" : zipTest + System.lineSeparator()) +
                (countryTest.isEmpty() ? "" : countryTest + System.lineSeparator()) +
                (phoneTest.isEmpty() ? "" : "Phone: " + phoneTest + System.lineSeparator()) +
                (faxTest.isEmpty() ? "" : "Fax: " + faxTest + System.lineSeparator())
        ;
        assertEquals(addressTest,caTest.toString());
        System.out.println("TC_12 was ran." + System.lineSeparator() + "Expected = " + addressTest +
                "Actual = " + caTest.toString());
    }

    /**Title: Test Creating Shipping Address
     * Description: Test the function to create shipping address using a new address ID to very the change
     * Requirement: 3.4, 4.12
     * Assumptions: No issue connecting to the Database
     * Input: addressID = 1
     * Expected Result:
     * Big Food Inc.
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
     * Actual Result:
     * Big Food Inc.
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
     * Test Outcome: Passed
     * @throws SQLException
     */
    @Test
    public void createShippingAddress() throws SQLException {
        DBService db = DBService.getInstance("admin","admin","KINGKAI-HP\\SQLEXPRESS");
        caTest = db.getCustomerAddress(1);;
        System.out.println("TC_11 was ran. New address result: " + caTest.createShippingAddress());

    }
}