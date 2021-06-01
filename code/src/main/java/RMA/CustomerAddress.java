package RMA;

import javafx.beans.binding.ObjectBinding;

import java.sql.SQLException;

/** CustomerAddress contains all the necessary details to fully describe a customer's business address.
 */
public class CustomerAddress extends ObjectBinding<CustomerAddress> {
    /** addressId contains the {@link Integer} id for this CustomerAddress.
     */
    private int addressId;
    /** customerName contains the customer name {@link String} associated with this CustomerAddress.
     */
    private String customerName;
    /** businessName contains the business name {@link String} associated with this CustomerAddress.
     */
    private String businessName;
    /** address1 contains the first part of the street address as a {@link String}.
     */
    private String address1;
    /** address2 is optional and contains the second part of the street address as a {@link String}.
     */
    private String address2;
    /** city contains the city {@link String} associated with this CustomerAddress.
     */
     private String city;
    /** county contains the optional county {@link String} associated with this CustomerAddress.
     */
    private String county;
    /** stateOrProvince contains the {@link String} state or province initials associated with this CustomerName.
     */
    private String stateOrProvince;
    /** zip contains the zip or postal code as a {@link String} for this CustomerAddress.
     */
    private String zip;
    /** country contains the name of the country as a {@link String} for this CustomerAddress.
     */
    private String country;
    /** phone contains the phone number for this business, stored as a {@link String} for flexibility.
     */
    private String phone;
    /** fax contains the fax number for this business, stored as a {@link String} for flexibility.
     */
    private String fax;
    /** showBusinessName is used to only output the business name in the computeValue function.
     */
    private boolean showBusinessName;
    /** EMPTY_ADDRESS is used to output an empty CustomerAddress when needed.
     */
    public static final CustomerAddress EMPTY_ADDRESS = new CustomerAddress(0,"","","","","","","","","","","");

    /** Constructor for CustomerAddress that takes the passed-in addressId and fetches the address
     *  information from the database.
     * @param addressId The address ID to search for in the database.
     * @throws SQLException If there is an issue connecting to the SQL Server database or the SQL query.
     */
    public CustomerAddress(int addressId) throws SQLException {
        // Use DBService to fetch the CustomerAddress info.
        CustomerAddress address = DBService.getInstance().getCustomerAddress(addressId);
        // Initialize properties.
        this.addressId = address.getAddressId();
        customerName = address.getCustomerName();
        businessName = address.getBusinessName();
        address1 = address.getAddress1();
        address2 = address.getAddress2();
        city = address.getCity();
        county = address.getCounty();
        stateOrProvince = address.getStateOrProvince();
        zip = address.getZip();
        country = address.getCountry();
        phone = address.getPhone();
        fax = address.getFax();
        showBusinessName = false;
    }

    /** Constructor for CustomerAddress that manually fills in all the details.
     * @param addressId The int address identifier to assign to this CustomerAddress.
     * @param customerName The {@link String} customer name associated with this CustomerAddress.
     * @param businessName The {@link String} name of the business at this address.
     * @param address1 The {@link String} first part of the street address.
     * @param address2 The optional {@link String} second part of the street address.
     * @param city The {@link String} name of the city.
     * @param county The {@link String} name of the county.
     * @param stateOrProvince The {@link String} state or province initials.
     * @param zip The {@link String} zip or postal code for this address.
     * @param country The optional {@link String} name of the country.
     * @param phone The {@link String} main phone number for this address.
     * @param fax The optional {@link String} fax number for this address.
     */
    public CustomerAddress(
        int addressId, String customerName, String businessName, String address1, String address2,
        String city, String county, String stateOrProvince, String zip, String country, String phone,
        String fax
    ) {
        this.addressId = addressId;
        this.customerName = customerName;
        this.businessName = businessName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.county = county;
        this.stateOrProvince = stateOrProvince;
        this.zip = zip;
        this.country = country;
        this.phone = phone;
        this.fax = fax;
        showBusinessName = false;
    }

    /** computeValue returns the value representing this CustomerAddress when bound as an {@link javafx.beans.property.ObjectProperty}.
     * @return A {@link String} containing either just the business name (if showBusinessName is true), or the customer
     * name, business name, street, city, county, state, zip, country, phone, and fax (if showBusinessName is false).
     */
    @Override
    protected CustomerAddress computeValue() {
        CustomerAddress result = new CustomerAddress(addressId, customerName, businessName, address1, address2, city,
            county, stateOrProvince, zip, country, phone, fax);
        result.setShowBusinessName(showBusinessName);
        return result;
    }

    /** toString is used to output the content of this CustomerAddress in the appropriate form depending on the value of
     *  showBusinessName.
     * @return A {@link String} either containing the businessName if showBusinessName is true, otherwise the entire
     * address if it is false.
     */
    @Override
    public String toString() {
        return showBusinessName ? businessName :
            (customerName.isEmpty() ? "" : customerName + System.lineSeparator()) +
            (businessName.isEmpty() ? "" : businessName + System.lineSeparator()) +
            (address1.isEmpty() ? "" : address1 + System.lineSeparator()) +
            (address2.isEmpty() ? "" : address2 + System.lineSeparator()) +
            (city.isEmpty() ? "" : city + System.lineSeparator()) +
            (county.isEmpty() ? "" : county + System.lineSeparator()) +
            (stateOrProvince.isEmpty() ? "" : stateOrProvince + System.lineSeparator()) +
            (zip.isEmpty() ? "" : zip + System.lineSeparator()) +
            (country.isEmpty() ? "" : country + System.lineSeparator()) +
            (phone.isEmpty() ? "" : "Phone: " + phone + System.lineSeparator()) +
            (fax.isEmpty() ? "" : "Fax: " + fax + System.lineSeparator())
        ;
    }

    /** getAddressId returns the address ID stored in this CustomerAddress.
     * @return The int addressId field.
     */
    public int getAddressId() {return addressId;}

    /** getCustomerName returns the customer name stored in this CustomerAddress.
     * @return The {@link String} customerName field.
     */
    public String getCustomerName() {return customerName;}

    /** getBusinessName returns the business name stored in this CustomerAddress.
     * @return The {@link String} businessName field.
     */
    public String getBusinessName() {return businessName;}

    /** getAddress1 returns the first part of the street address stored in this CustomerAddress.
     * @return The {@link String} address1 field.
     */
    public String getAddress1() {return address1;}

    /** getAddress2 returns the second, optional part of the street address stored in this CustomerAddress.
     * @return The {@link String} address2 field.
     */
    public String getAddress2() {return address2;}

    /** getCity returns the name of the city stored in this CustomerAddress.
     * @return The {@link String} city field.
     */
    public String getCity() {return city;}

    /** getCounty returns the name of the county stored in this CustomerAddress.
     * @return The {@link String} county field.
     */
    public String getCounty() {return county;}

    /** getStateOrProvince returns the initials for the state or province stored in this CustomerAddress.
     * @return The {@link String} stateOrProvince field.
     */
    public String getStateOrProvince() {return stateOrProvince;}

    /** getZip returns the zip or postal code stored in this CustomerName.
     * @return The {@link String} zip field.
     */
    public String getZip() {return zip;}

    /** getCountry returns the country name stored in this CustomerAddress.
     * @return The {@link String} country field.
     */
    public String getCountry() {return country;}

    /** getPhone returns the phone number stored in this CustomerAddress.
     * @return The {@link String} phone field.
     */
    public String getPhone() {return phone;}

    /** getFax returns the fax number stored in this CustomerAddress.
     * @return The {@link String} fax field.
      */
    public String getFax() {return fax;}

    /** setShowBusinessName is only used if the programmer wants only the business name to show in the computeValue function.
     * @param newShowBusinessName The new boolean to set for showBusinessName (whether we want only the name showing or
     *                            the entire address).
     */
    public void setShowBusinessName(boolean newShowBusinessName) {showBusinessName = newShowBusinessName;}

    /** createShippingAddress is used to create a copy of this CustomerAddress, set to show the entire address, for the
     *  shippingAddress field.
     * @return A new instance of this {@link CustomerAddress} with showBusinessName set to false.
     */
    public CustomerAddress createShippingAddress() {
        return new CustomerAddress(addressId, customerName, businessName, address1, address2, city, county,
            stateOrProvince, zip, country, phone, fax);
    }
}