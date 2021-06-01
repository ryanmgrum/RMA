package RMA;

import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/** DBService extends the initial core {@link DBConnection} class and
 *  implements all logic and functionality for accessing the SQL Server
 *  back-end database.
 */
class DBService extends DBConnection {
    /** query contains the most recent {@link String} query used on the database.
     */
    private static String query;
    /** user contains the {@link String} username that was used to log into the database.
     */
    private static String user;
    /** role contains the {@link String} role assigned to the username and password credentials used
     *  to initially login to the database.
     *  (Requirement 1.3)
     */
    private static String role;
    /** instance is a private static instance of this class used to maintain a single point of access throughout
     *  the application.
     */
    private static DBService instance;

    /** Constructor that takes in a username, password, and instance name, and attempts
     *  to create a connection to the database.
     * @param username The {@link String} username to use to connect to the database.
     * @param password The {@link String} password to use to connect to the database.
     * @param instanceName The {@link String} SQL Server instance name to connect.
     * @throws SQLException If there is an issue connecting to the database.
     */
    private DBService(String username, String password, String instanceName) throws SQLException {
        // First instantiate the superclass, DBConnection.
        super(username, password, instanceName);

        user = username;
        role = getDBRole();
    }

    /** overloaded getInstance is used to setup the static DBService instance and then return it.
     * @param username The {@link String} username to use to connect to the database.
     * @param password The {@link String} password to use to connect to the database.
     * @param instanceName The {@link String} SQL Server instance name to connect.
     * @return The static instance of {@link DBService} stored in the {@link DBService} class.
     * @throws SQLException If there is an issue connecting to the database.
     */
    public static DBService getInstance(String username, String password, String instanceName) throws SQLException {
        if (instance == null)
            instance = new DBService(username, password, instanceName);
        return instance;
    }

    /** getInstance returns the static instance stored in this class.
     * @return A static instance of DBService.
     */
    public static DBService getInstance() {return instance;}

    /** getUser returns the {@link String} username stored in this DBService.
     * @return The {@link String} user field.
     */
    public String getUser() {return user;}

    /** getRole returns the {@link String} role stored in this DBService.
     * @return The {@link String} role field.
     */
    public String getRole() {return role;}

    /** getDBRole returns the logged-in user's assigned role in the database as a {@link String}.
     * @return A {@link String} containing the logged-in user's role.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    private String getDBRole() throws SQLException {
        // Set up the SQL query.
        query = "SELECT DP1.name AS DatabaseRoleName, DP2.name AS DatabaseUserName " +
                "FROM sys.database_role_members AS DRM " +
                "RIGHT OUTER JOIN sys.database_principals AS DP1 " +
                "ON DRM.role_principal_id = DP1.principal_id " +
                "LEFT OUTER JOIN sys.database_principals AS DP2 " +
                "ON DRM.member_principal_id = DP2.principal_id " +
                "WHERE DP2.name = CURRENT_USER " +
                "ORDER BY DatabaseRoleName;";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("DatabaseRoleName");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getCustomerId fetches the customer ID associated with the passed-in customer name.
     * @param customerName The {@link String} name of the customer to search for in the database.
     * @return An int containing the customer's unique id; returns a negative value if the customer name was not found.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public int getCustomerId(String customerName) throws SQLException {
        // Set up the SQL query.
        query = "select customerId from customer where customerName ='" + customerName + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        int result = -1;
        if (rs.next())
            result = rs.getInt("customerId");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getCustomerNames returns an {@link ArrayList} of {@link String} customer names stored in the database.
     * @return An {@link ArrayList} of {@link String} customer names.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public ArrayList<String> getCustomerNames() throws SQLException {
        // Set up the SQL query.
        query = "select customerName from customers order by customerName;";

        // Fetch the results.
        connect();
        executeQuery(query);

        ArrayList<String> result = new ArrayList<>();
        while(rs.next())
            result.add(rs.getString("customerName"));

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getCustomerBusinessNames returns the list of business names associated with a customer name through
     *  a list of CustomerAddress with showBusinessName explicitly set to true.
     * @param customerName The {@link String} name of the customer to search for in the database.
     * @return An {@link ArrayList} containing {@link CustomerAddress}es, set to only output the business name.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public ArrayList<CustomerAddress> getCustomerBusinessNames(String customerName) throws SQLException {
        // Set up the SQL query.
        query = "select ca.addressId, c.customerName, ca.businessName, ca.address1, ca.address2," +
                "ca.city, ca.county, ca.stateOrProvince, ca.zip, ca.country, ca.phone, ca.fax " +
                "from customerAddresses ca, customers c " +
                "where c.customerId = ca.customerId " +
                "and c.customerName = '" + customerName + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        ArrayList<CustomerAddress> result = new ArrayList<>();
        while (rs.next()) {
            CustomerAddress address = new CustomerAddress(
                    rs.getInt("addressId"),
                    rs.getString("customerName"),
                    rs.getString("businessName"),
                    rs.getString("address1"),
                    rs.getString("address2"),
                    rs.getString("city"),
                    rs.getString("county"),
                    rs.getString("stateOrProvince"),
                    rs.getString("zip"),
                    rs.getString("country"),
                    rs.getString("phone"),
                    rs.getString("fax")
            );
            address.setShowBusinessName(true);
            result.add(address);
        }

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getCustomerAddress returns a CustomerAddress containing the address details for the given int addressId.
     * @param addressId The int ID of the customer address to find.
     * @return A CustomerAddress containing the address details.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public CustomerAddress getCustomerAddress(int addressId) throws SQLException {
        // Set up the SQL query.
        query = "select ca.addressId, c.customerName, ca.businessName, ca.address1, ca.address2," +
                "ca.city, ca.county, ca.stateOrProvince, ca.zip, ca.country, ca.phone, ca.fax " +
                "from customerAddresses ca, customers c " +
                "where ca.addressId = " + addressId + " " +
                "and c.customerId = ca.customerId;";

        // Fetch the results.
        connect();
        executeQuery(query);

        CustomerAddress result = null;
        if (rs.next())
            result = new CustomerAddress(
                rs.getInt("addressId"),
                rs.getString("customerName"),
                rs.getString("businessName"),
                rs.getString("address1"),
                rs.getString("address2"),
                rs.getString("city"),
                rs.getString("county"),
                rs.getString("stateOrProvince"),
                rs.getString("zip"),
                rs.getString("country"),
                rs.getString("phone"),
                rs.getString("fax")
            );

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getCustomerAddresses returns an {@link ArrayList} of CustomerAddress instances containing the
     *  details of each address associated with the specific customer name.
     * @param customerName The {@link String} name of the customer to search for in the database.
     * @return An {@link ArrayList} of type CustomerAddress.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public ArrayList<CustomerAddress> getCustomerAddresses(String customerName) throws SQLException {
        // Set up the SQL query.
        query = "select ca.addressId, c.customerName, ca.businessName, ca.address1, ca.address2," +
                "ca.city, ca.county, ca.stateOrProvince, ca.zip, ca.country, ca.phone, ca.fax " +
                "from customerAddresses ca, customers c " +
                "where c.customerName = '" + customerName + "' " +
                "and c.customerId = ca.customerId;";

        // Fetch the results.
        connect();
        executeQuery(query);

        ArrayList<CustomerAddress> result = new ArrayList<>();
        while(rs.next())
            result.add(
                new CustomerAddress(
                    rs.getInt("addressId"),
                    rs.getString("customerName"),
                    rs.getString("businessName"),
                    rs.getString("address1"),
                    rs.getString("address2"),
                    rs.getString("city"),
                    rs.getString("county"),
                    rs.getString("stateOrProvince"),
                    rs.getString("zip"),
                    rs.getString("country"),
                    rs.getString("phone"),
                    rs.getString("fax")
                )
            );

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getCustomerAddressPONumbers returns the list of PO numbers associated with the given CustomerAddress.
     * @param address The CustomerAddress to search for in the database.
     * @return An {@link ArrayList} of type {@link String} PO numbers.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public ArrayList<String> getCustomerAddressPONumbers(CustomerAddress address) throws SQLException {
        // Set up the SQL query.
        query = "select poNumber from purchaseOrders where addressId = '" +
                address.getAddressId()  + "' order by poNumber;";

        // Fetch the results.
        connect();
        executeQuery(query);

        ArrayList<String> result = new ArrayList<>();
        while(rs.next())
            result.add(rs.getString("poNumber"));

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getPurchaseOrderProduct returns the requested PurchaseOrderProduct whose id is the passed-in purchaseOrderProductId.
     * @param purchaseOrderProductId The purchaseOrderProductId of the PurchaseOrderProduct we wish to fetch.
     * @return A PurchaseOrderProduct containing the information for the specified purchaseOrderProductId.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public PurchaseOrderProduct getPurchaseOrderProduct(int purchaseOrderProductId) throws SQLException {
        // Set up the SQL query.
        query = "select pop.purchaseOrderProductId, pop.poNumber, p.productName, pc.categoryName, " +
                "pop.quantity, pop.orderDate, pop.deliverDate " +
                "from purchaseOrderProducts pop, products p, productCategories pc " +
                "where pop.purchaseOrderProductId = " + purchaseOrderProductId + " " +
                "and pc.categoryId = p.categoryId " +
                "and p.productId = pop.productId " +
                "and p.categoryId = pop.categoryId;";

        // Fetch the results.
        connect();
        executeQuery(query);

        PurchaseOrderProduct result = null;
        if (rs.next())
            result = new PurchaseOrderProduct(
                rs.getInt("purchaseOrderProductId"),
                rs.getString("poNumber"),
                rs.getString("productName"),
                rs.getString("categoryName"),
                rs.getInt("quantity"),
                rs.getObject("orderDate", LocalDate.class),
                rs.getObject("deliverDate", LocalDate.class)
            );

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getPurchaseOrderProducts returns the {@link ArrayList} of PurchaseOrderProducts associated with a
     *  given PO number.
     * @param poNumber The {@link String} PO number to search for in the database.
     * @return An {@link ArrayList} of type PurchaseOrderProduct containing the list of products associated
     * with the purchase order.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public ArrayList<PurchaseOrderProduct> getPurchaseOrderProducts(String poNumber) throws SQLException {
        // Set up the SQL query.
        query = "select pop.purchaseOrderProductId, pop.poNumber, p.productName, pc.categoryName, " +
                "pop.quantity, pop.orderDate, pop.deliverDate " +
                "from purchaseOrderProducts pop, products p, productCategories pc " +
                "where pc.categoryId = p.categoryId " +
                "and p.productId = pop.productId " +
                "and p.categoryId = pop.categoryId " +
                "and pop.poNumber = '" + poNumber + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        ArrayList<PurchaseOrderProduct> result = new ArrayList<>();
        while (rs.next())
            result.add(
                new PurchaseOrderProduct(
                    rs.getInt("purchaseOrderProductId"),
                    rs.getString("poNumber"),
                    rs.getString("productName"),
                    rs.getString("categoryName"),
                    rs.getInt("quantity"),
                    rs.getObject("orderDate", LocalDate.class),
                    rs.getObject("deliverDate", LocalDate.class)
                )
            );

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getRMAStatusId returns the statusId associated with the passed-in status description.
     * @param description The description of the status for which we want the identifier.
     * @return A int containing the value of the statusId; returns a negative value if the status was not found.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public int getRMAStatusId(String description) throws SQLException {
        // Set up the SQL query.
        query = "select statusId from rmaStatuses where description ='" + description + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        int result = -1;
        if (rs.next())
            result = rs.getInt("statusId");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getRMAStatusDescription returns the description associated with an RMA statusId.
     * @param statusId The RMA status id int to look up in the database.
     * @return The {@link String} description.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMAStatusDescription(int statusId) throws SQLException {
        // Set up the SQL query.
        query = "select description from rmaStatuses where statusId =" + statusId + ";";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("description");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getRMAStatuses returns an {@link ArrayList} of type {@link String} containing the RMA status
     *  descriptions in the database.
     * @return An {@link ArrayList} of type {@link String} containing the RMA status descriptions.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public ArrayList<String> getRMAStatuses() throws SQLException {
        // Set up the SQL query.
        query = "select description from rmaStatuses order by description;";

        // Fetch the results.
        connect();
        executeQuery(query);

        ArrayList<String> result = new ArrayList<>();
        while (rs.next())
            result.add(rs.getString("description"));

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getReturnReasonCodes returns a {@link HashMap} containing types {@link String}, whose contents
     *  are the return reason code initials and the associated description.
     * @return A {@link HashMap} containing types {@link String}, whose contents are
     * the return reason code initials and the associated description.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public HashMap<String, String> getReturnReasonCodes() throws SQLException {
        // Set up the SQL query.
        query = "select returnReasonCode, description from returnReasonCodes order by returnReasonCode;";

        // Fetch the results.
        connect();
        executeQuery(query);

        HashMap<String, String> result = new HashMap<>();
        while (rs.next())
            result.put(
                rs.getString("returnReasonCode"),
                rs.getString("description")
            );

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getDispositionId returns the int identifier for the passed-in disposition {@link String} description.
     * @param disposition The {@link String} disposition description to search for in the database.
     * @return A int containing the value of the dispositionId; returns a negative value if the disposition was not found.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public int getDispositionId(String disposition) throws SQLException {
        // Set up the SQL query.
        query = "select dispositionId from dispositions where disposition ='" + disposition + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        int result = -1;
        if (rs.next())
            result = rs.getInt("dispositionId");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getDispositions returns an {@link ArrayList} of type {@link String}, containing the list of available
     *  disposition descriptions in the database.
     * @return An {@link ArrayList} of type {@link String}, containing the list of available disposition
     * descriptions in the database.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public ArrayList<String> getDispositions() throws SQLException {
        // Set up the SQL query.
        query = "select disposition from dispositions order by disposition;";

        // Fetch the results.
        connect();
        executeQuery(query);

        ArrayList<String> result = new ArrayList<>();
        while (rs.next())
            result.add(rs.getString("disposition"));

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** createRMA creates the initial RMA record in the database using the passed-in information.
     * @param statusDescription The {@link String} status description to use for this RMA.
     * @return The resulting {@link String} RMA ID for this request.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String createRMA(String statusDescription) throws SQLException {
        // First fetch the next RMA ID to use.
        query = "{call GetNextRMAId(?)}";

        createCallableStatement(query);
        cStmt.registerOutParameter(1, Types.VARCHAR);
        cStmt.execute();
        String rmaId = cStmt.getString(1);
        closeConnection();

        // Next fetch the statusId to use.
        int statusId = getRMAStatusId(statusDescription);

        // Now execute the insert statement.
        query = "insert into rma" +
                "(rmaId, owner, lastModified, lastModifiedBy, statusId)" +
                "values" +
                "(?, ?, ?, ?, ?);";
        connect();
        createPreparedStatement(query);
        pStmt.setString(1, rmaId);
        pStmt.setString(2, getUser());
        pStmt.setObject(3, LocalDateTime.now());
        pStmt.setString(4, getUser());
        pStmt.setInt(5, statusId);
        pStmt.executeUpdate();
        closeConnection();

        return rmaId;
    }

    /** getRMA fetches the details for the given {@link String} RMA ID and returns it as an RMAListViewModel.
     * @param rmaId The {@link String} ID of the RMA to fetch.
     * @return An RMAListViewModel containing the details of the RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public RMAListViewModel getRMA(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select r.rmaId, rs.description, rd.shipReplacementRepair, c.customerName, " +
                "ca.businessName, ca.addressId, ca.address1, ca.address2, ca.city, ca.county, " +
                "ca.stateOrProvince, ca.zip, ca.country, ca.phone, ca.fax, pop.purchaseOrderProductId, " +
                "pop.poNumber, p.productName, pc.categoryName, pop.quantity, pop.orderDate, pop.deliverDate, " +
                "rd.returnQuantity, rd.created " +
                "from rma r, rmaDetails rd, rmaStatuses rs, purchaseOrders po, purchaseOrderProducts pop, " +
                "products p, productCategories pc, customerAddresses ca, customers c " +
                "where r.rmaId = '" + rmaId + "' " +
                "and r.statusId = rs.statusId " +
                "and r.rmaId = rd.rmaId " +
                "and rd.poNumber = po.poNumber " +
                "and po.poNumber = pop.poNumber " +
                "and pop.productId = p.productId " +
                "and pop.categoryId = p.categoryId " +
                "and p.categoryId = pc.categoryId " +
                "and po.addressId = ca.addressId " +
                "and ca.customerId = c.customerId;";

        // Fetch the results.
        connect();
        executeQuery(query);

        RMAListViewModel result = null;
        if (rs.next())
            result = new RMAListViewModel(
                rs.getString("rmaId"),
                rs.getString("description"),
                rs.getBoolean("shipReplacementRepair"),
                rs.getString("customerName"),
                rs.getString("businessName"),
                new CustomerAddress(
                    rs.getInt("addressId"),
                    rs.getString("customerName"),
                    rs.getString("businessName"),
                    rs.getString("address1"),
                    rs.getString("address2"),
                    rs.getString("city"),
                    rs.getString("county"),
                    rs.getString("stateOrProvince"),
                    rs.getString("zip"),
                    rs.getString("country"),
                    rs.getString("phone"),
                    rs.getString("fax")
                ),
                new PurchaseOrderProduct(
                    rs.getInt("purchaseOrderProductId"),
                    rs.getString("poNumber"),
                    rs.getString("productName"),
                    rs.getString("categoryName"),
                    rs.getInt("quantity"),
                    rs.getObject("orderDate", LocalDate.class),
                    rs.getObject("deliverDate", LocalDate.class)
                ),
                rs.getInt("returnQuantity"),
                false, // shouldDelete
                rs.getObject("created", LocalDateTime.class)
            );

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getRMAs fetches the list of open RMAs in the database. If the user is an engineer,
     *  then the list of RMAs will be all those with an empty engineeringEvaluation in RMADetails.
     *  In both cases, if a critical RMA exists (RMA that has not been modified for five or more days),
     *  then the list will only show critical RMAs.
     * @return An {@link ArrayList} of RMAListViewModel containing the RMAs' details.
     * @throws SQLException If there is an issue connecting to the database.
     */
    public ArrayList<RMAListViewModel> getRMAs() throws SQLException {
        // Create the result list for later use.
        ArrayList<RMAListViewModel> result = new ArrayList<>();

        // First check if there are any critical RMAs.
        if (!getRole().equals("engineer"))
            query = "select r.rmaId, rs.description, rd.shipReplacementRepair, c.customerName, " +
                        "ca.businessName, ca.addressId, ca.address1, ca.address2, ca.city, ca.county, " +
                        "ca.stateOrProvince, ca.zip, ca.country, ca.phone, ca.fax, pop.purchaseOrderProductId, " +
                        "pop.poNumber, p.productName, pc.categoryName, pop.quantity, pop.orderDate, pop.deliverDate, " +
                        "rd.returnQuantity, rd.created " +
                    "from rma r, rmaDetails rd, rmaStatuses rs, purchaseOrders po, purchaseOrderProducts pop, " +
                         "products p, productCategories pc, customerAddresses ca, customers c " +
                    "where rs.description != 'Closed' " +
                        "and datediff(day, r.lastModified, ?) >= 5 " +
                        "and rd.purchaseOrderProductId = pop.purchaseOrderProductId " +
                        "and r.statusId = rs.statusId " +
                        "and r.rmaId = rd.rmaId " +
                        "and rd.poNumber = po.poNumber " +
                        "and po.poNumber = pop.poNumber " +
                        "and pop.productId = p.productId " +
                        "and pop.categoryId = p.categoryId " +
                        "and p.categoryId = pc.categoryId " +
                        "and po.addressId = ca.addressId " +
                        "and ca.customerId = c.customerId " +
                    "order by r.rmaId;";
        else
            query = "select r.rmaId, rs.description, rd.shipReplacementRepair, c.customerName, " +
                        "ca.businessName, ca.addressId, ca.address1, ca.address2, ca.city, ca.county, " +
                        "ca.stateOrProvince, ca.zip, ca.country, ca.phone, ca.fax, pop.purchaseOrderProductId, " +
                        "pop.poNumber, p.productName, pc.categoryName, pop.quantity, pop.orderDate, pop.deliverDate, " +
                        "rd.returnQuantity, rd.created " +
                    "from rma r, rmaDetails rd, rmaStatuses rs, purchaseOrders po, purchaseOrderProducts pop, " +
                    "products p, productCategories pc, customerAddresses ca, customers c " +
                    "where rs.description != 'Closed' " +
                        "and datediff(day, r.lastModified, ?) >= 5 " +
                        "and rd.purchaseOrderProductId = pop.purchaseOrderProductId " +
                        "and rd.engineeringEvaluation = '' " +
                        "and r.statusId = rs.statusId " +
                        "and r.rmaId = rd.rmaId " +
                        "and rd.poNumber = po.poNumber " +
                        "and po.poNumber = pop.poNumber " +
                        "and pop.productId = p.productId " +
                        "and pop.categoryId = p.categoryId " +
                        "and p.categoryId = pc.categoryId " +
                        "and po.addressId = ca.addressId " +
                        "and ca.customerId = c.customerId " +
                    "order by r.rmaId;";
        connect();
        createPreparedStatement(query);
        pStmt.setObject(1, LocalDateTime.now());
        executePreparedStatementQuery();
        if (rs.next()) { // We have critical RMAs to process.
            do {
                result.add(
                    new RMAListViewModel(
                        rs.getString("rmaId"),
                        rs.getString("description"),
                        rs.getBoolean("shipReplacementRepair"),
                        rs.getString("customerName"),
                        rs.getString("businessName"),
                        new CustomerAddress(
                            rs.getInt("addressId"),
                            rs.getString("customerName"),
                            rs.getString("businessName"),
                            rs.getString("address1"),
                            rs.getString("address2"),
                            rs.getString("city"),
                            rs.getString("county"),
                            rs.getString("stateOrProvince"),
                            rs.getString("zip"),
                            rs.getString("country"),
                            rs.getString("phone"),
                            rs.getString("fax")
                        ),
                        new PurchaseOrderProduct(
                            rs.getInt("purchaseOrderProductId"),
                            rs.getString("poNumber"),
                            rs.getString("productName"),
                            rs.getString("categoryName"),
                            rs.getInt("quantity"),
                            rs.getObject("orderDate", LocalDate.class),
                            rs.getObject("deliverDate", LocalDate.class)
                        ),
                        rs.getInt("returnQuantity"),
             false, // shouldDelete
                        rs.getObject("created", LocalDateTime.class)
                    )
                );
            } while (rs.next());

        } else { // Check if we have non-critical RMAs to process.
            // Reset the connection and statements.
            closeConnection();

            // Queries for non-critical RMAs.
            if (!getRole().equals("engineer"))
                query = "select r.rmaId, rs.description, rd.shipReplacementRepair, c.customerName, " +
                        "ca.businessName, ca.addressId, ca.address1, ca.address2, ca.city, ca.county, " +
                        "ca.stateOrProvince, ca.zip, ca.country, ca.phone, ca.fax, pop.purchaseOrderProductId, " +
                        "pop.poNumber, p.productName, pc.categoryName, pop.quantity, pop.orderDate, pop.deliverDate, " +
                        "rd.returnQuantity, rd.created " +
                        "from rma r, rmaDetails rd, rmaStatuses rs, purchaseOrders po, purchaseOrderProducts pop, " +
                        "products p, productCategories pc, customerAddresses ca, customers c " +
                        "where rs.description != 'Closed' " +
                        "and datediff(day, r.lastModified, ?) < 5 " +
                        "and rd.purchaseOrderProductId = pop.purchaseOrderProductId " +
                        "and r.statusId = rs.statusId " +
                        "and r.rmaId = rd.rmaId " +
                        "and rd.poNumber = po.poNumber " +
                        "and po.poNumber = pop.poNumber " +
                        "and pop.productId = p.productId " +
                        "and pop.categoryId = p.categoryId " +
                        "and p.categoryId = pc.categoryId " +
                        "and po.addressId = ca.addressId " +
                        "and ca.customerId = c.customerId " +
                        "order by r.rmaId;";
            else
                query = "select r.rmaId, rs.description, rd.shipReplacementRepair, c.customerName, " +
                        "ca.businessName, ca.addressId, ca.address1, ca.address2, ca.city, ca.county, " +
                        "ca.stateOrProvince, ca.zip, ca.country, ca.phone, ca.fax, pop.purchaseOrderProductId, " +
                        "pop.poNumber, p.productName, pc.categoryName, pop.quantity, pop.orderDate, pop.deliverDate, " +
                        "rd.returnQuantity, rd.created " +
                        "from rma r, rmaDetails rd, rmaStatuses rs, purchaseOrders po, purchaseOrderProducts pop, " +
                        "products p, productCategories pc, customerAddresses ca, customers c " +
                        "where rs.description != 'Closed' " +
                        "and datediff(day, r.lastModified, ?) < 5 " +
                        "and rd.purchaseOrderProductId = pop.purchaseOrderProductId " +
                        "and rd.engineeringEvaluation = '' " +
                        "and r.statusId = rs.statusId " +
                        "and r.rmaId = rd.rmaId " +
                        "and rd.poNumber = po.poNumber " +
                        "and po.poNumber = pop.poNumber " +
                        "and pop.productId = p.productId " +
                        "and pop.categoryId = p.categoryId " +
                        "and p.categoryId = pc.categoryId " +
                        "and po.addressId = ca.addressId " +
                        "and ca.customerId = c.customerId " +
                        "order by r.rmaId;";

            connect();
            createPreparedStatement(query);
            pStmt.setObject(1, LocalDateTime.now());
            executePreparedStatementQuery();
            if (rs.next()) // We have non-critical RMAs to process.
                do {
                    result.add(
                        new RMAListViewModel(
                            rs.getString("rmaId"),
                            rs.getString("description"),
                            rs.getBoolean("shipReplacementRepair"),
                            rs.getString("customerName"),
                            rs.getString("businessName"),
                            new CustomerAddress(
                                rs.getInt("addressId"),
                                rs.getString("customerName"),
                                rs.getString("businessName"),
                                rs.getString("address1"),
                                rs.getString("address2"),
                                rs.getString("city"),
                                rs.getString("county"),
                                rs.getString("stateOrProvince"),
                                rs.getString("zip"),
                                rs.getString("country"),
                                rs.getString("phone"),
                                rs.getString("fax")
                            ),
                            new PurchaseOrderProduct(
                                rs.getInt("purchaseOrderProductId"),
                                rs.getString("poNumber"),
                                rs.getString("productName"),
                                rs.getString("categoryName"),
                                rs.getInt("quantity"),
                                rs.getObject("orderDate", LocalDate.class),
                                rs.getObject("deliverDate", LocalDate.class)
                            ),
                            rs.getInt("returnQuantity"),
                            false, // shouldDelete
                            rs.getObject("created", LocalDateTime.class)
                        )
                    );
                } while (rs.next());
        }

        // Clean up connection.
        closeConnection();

        // Return the RMAs.
        return result;
    }

    /** createRMADetails creates the rmaDetails record associated with the initial RMA record using the passed-in information.
     * @param rmaId The {@link String} ID of the RMA whose details we are creating.
     * @param returnReasonCode The {@link String} Return Reason Code we are assigning to this RMA.
     * @param creditReplaceRepair Whether we are deciding to credit, replace, or repair the returned items, saved as a {@link String}.
     * @param purchaseOrderProductId The unique int identifier of the product being returned by the customer.
     * @param returnQuantity The int amount of product being returned by the customer.
     * @param returnLabelTracker The {@link String} return label tracking number used to ship the product to us.
     * @param additionalInfo Any additional info needed to process the RMA, entered in by an Analyst. Stored as a {@link String}.
     * @param poNumber The {@link String} purchase order number being referenced in this RMA.
     * @param initialEvaluation The {@link String} initial evaluation by an Analyst of the product's condition and the RMA request.
     * @param engineeringEvaluation The {@link String} evaluation of the returned product by an Engineer.
     * @param disposition The {@link String} disposition to assign to the returned product by an Analyst.
     * @param dispositionNotes Any additional notes on the disposition written by an Analyst. Stored as a {@link String}.
     * @param replacementTrackingNumber The {@link String} tracking number for the replacement being sent back to the customer.
     * @param replacementShipDate The {@link LocalDate} date the replacement was or will be shipped back to the customer.
     * @param shipReplacementRepair A boolean indicating whether or not we will be shipping back a replacement or repair to the customer.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void createRMADetails (
            String rmaId, String returnReasonCode, String creditReplaceRepair, int purchaseOrderProductId, int returnQuantity,
            String returnLabelTracker, String additionalInfo, String poNumber, String initialEvaluation, String engineeringEvaluation,
            String disposition, String dispositionNotes, String replacementTrackingNumber, LocalDate replacementShipDate,
            boolean shipReplacementRepair
    ) throws SQLException {
        // First setup the SQL query to fetch the dispositionId from the database.
        query = "select dispositionId from dispositions where disposition = '" + disposition + "';";
        // Connect to the database and execute the query.
        connect();
        executeQuery(query);

        Integer dispositionId = null;
        if (rs.next())
            dispositionId = rs.getInt("dispositionId");

        // Setup the SQL query to insert a new RMADetails record into the database.
        query = "insert into rmaDetails(" +
                "rmaId, created, createdBy, returnReasonCode, creditReplaceRepair, purchaseOrderProductId, returnQuantity," +
                "returnLabelTracker, additionalInfo, poNumber, initialEvaluation, engineeringEvaluation, dispositionId, " +
                "dispositionNotes, replacementTrackingNumber, replacementShipDate, shipReplacementRepair" +
                ") values (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                ");";

        // Create a new PreparedStatement and execute the query.
        createPreparedStatement(query);
        pStmt.setString(1, rmaId);
        pStmt.setObject(2, LocalDateTime.now());
        pStmt.setString(3, getUser());
        pStmt.setString(4, returnReasonCode);
        pStmt.setString(5, creditReplaceRepair);
        pStmt.setInt(6, purchaseOrderProductId);
        pStmt.setInt(7, returnQuantity);
        pStmt.setString(8, returnLabelTracker);
        pStmt.setString(9, additionalInfo);
        pStmt.setString(10, poNumber);
        pStmt.setString(11, initialEvaluation);
        pStmt.setString(12, engineeringEvaluation);
        if (dispositionId != null)
            pStmt.setInt(13, dispositionId);
        else
            pStmt.setNull(13, Types.INTEGER);
        pStmt.setString(14, dispositionNotes);
        pStmt.setString(15, replacementTrackingNumber);
        pStmt.setObject(16, replacementShipDate);
        pStmt.setBoolean(17, shipReplacementRepair);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMACustomerName returns the customer name associated with an RMA ID, through its PO number and the PO number's
     *  associated address ID.
     * @param rmaId The RMA whose customer name we wish to look up.
     * @return A {@link String} containing the name of the customer associated with the PO and address in the RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMACustomerName(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select c.customerName " +
                "from customers c, customerAddresses ca, purchaseOrders po, rmaDetails rd " +
                "where rd.rmaId ='" + rmaId + "' " +
                "and rd.poNumber = po.poNumber " +
                "and po.addressId = ca.addressId " +
                "and ca.customerId = c.customerId;";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("customerName");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getRMABusinessName returns a {@link CustomerAddress} set to show the business name associated with the RMA whose
     *  ID we are searching, through its PO number and the PO number's associated address ID.
     *  @param rmaId The RMA whose business name we wish to look up.
     *  @return A {@link CustomerAddress} set to show the name of the business associated with the PO and address in the RMA.
     *  @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public CustomerAddress getRMABusinessName(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select ca.addressId, c.customerName, ca.businessName, ca.address1, ca.address2, " +
                "ca.city, ca.county, ca.stateOrProvince, ca.zip, ca.country, ca.phone, ca.fax " +
                "from rmaDetails rd, purchaseOrders PO, customerAddresses ca, customers c " +
                "where rd.rmaId = '" + rmaId + "' " +
                "and rd.poNumber = po.poNumber " +
                "and po.addressId = ca.addressId " +
                "and ca.customerId = c.customerId;";

        // Fetch the results.
        connect();
        executeQuery(query);

        CustomerAddress result = null;
        if (rs.next()) {
            result = new CustomerAddress(
                rs.getInt("addressId"),
                rs.getString("customerName"),
                rs.getString("businessName"),
                rs.getString("address1"),
                rs.getString("address2"),
                rs.getString("city"),
                rs.getString("county"),
                rs.getString("stateOrProvince"),
                rs.getString("zip"),
                rs.getString("country"),
                rs.getString("phone"),
                rs.getString("fax")
            );
            result.setShowBusinessName(true);
        }

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getRMAPONumber returns the PO number associated with the given {@link String} RMA ID.
     *  @param rmaId The RMA whose PO number we wish to look up.
     *  @return A {@link String} containing the PO number associated with the RMA.
     *  @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMAPONumber(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select poNumber from rmaDetails where rmaId ='" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("poNumber");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getRMAAddress returns the address information associated with the {@link String} RMA ID's PO number in a CustomerAddress.
     * @param rmaId The RMA whose address information we wish to look up.
     * @return A CustomerAddress containing all the address information associated with the RMA's PO number.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public CustomerAddress getRMAAddress(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select ca.addressId, c.customerName, ca.businessName, ca.address1, ca.address2, " +
                "ca.city, ca.county, ca.stateOrProvince, ca.zip, ca.country, ca.phone, ca.fax " +
                "from rmaDetails rd, purchaseOrders PO, customerAddresses ca, customers c " +
                "where rd.rmaId = '" + rmaId + "' " +
                "and rd.poNumber = po.poNumber " +
                "and po.addressId = ca.addressId " +
                "and ca.customerId = c.customerId;";

        // Fetch the results.
        connect();
        executeQuery(query);

        CustomerAddress result = null;
        if (rs.next())
            result = new CustomerAddress(
                rs.getInt("addressId"),
                rs.getString("customerName"),
                rs.getString("businessName"),
                rs.getString("address1"),
                rs.getString("address2"),
                rs.getString("city"),
                rs.getString("county"),
                rs.getString("stateOrProvince"),
                rs.getString("zip"),
                rs.getString("country"),
                rs.getString("phone"),
                rs.getString("fax")
            );

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getOwners returns an {@link ArrayList} of usernames stored in the database.
     * @return An {@link ArrayList} of type {@link String} containing the list of usernames in the RMA database.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public ArrayList<String> getOwners() throws SQLException {
        // Set up the SQL query.
        query = "select name " +
                "from sys.database_principals " +
                "where type not in ('A', 'G', 'R', 'X') " +
                "and sid is not null " +
                "and name not in ('guest', 'dbo') " +
                "order by name;";

        // Fetch the results.
        connect();
        executeQuery(query);

        ArrayList<String> result = new ArrayList<>();
        while (rs.next())
            result.add(rs.getString("name"));

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getRMAOwner returns the {@link String} username of the owner stored in the database for the specified {@link String} RMA ID.
     * @param rmaId The {@link String} whose owner we wish to fetch.
     * @return A {@link String} containing the name of the current owner.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMAOwner(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select owner from [rma] where rmaId ='" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("owner");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMAOwner updates the RMA owner in the database for the given RMA ID with the passed-in username.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param newOwner The {@link String} new owner name to associate with the RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMAOwner(String rmaId, String newOwner) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rma " +
                "set owner = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setString(1, newOwner);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMALastModified fetches the {@link LocalDateTime} datetime stored in the database for the given {@link String} RMA ID.
     * @param rmaId The {@link String} ID of the RMA whose last modified datetime we wish to fetch.
     * @return A {@link LocalDateTime} containing the date and time of the last time the RMA was modified.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public LocalDateTime getRMALastModified(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select lastModified from rma where rmaId ='" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        LocalDateTime result = null;
        if (rs.next())
            result = rs.getObject("lastModified", LocalDateTime.class);

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMALastModified updates the {@link LocalDateTime} date and time of the RMA's lastModified column to now.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMALastModified(String rmaId) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rma " +
                "set lastModified = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setObject(1, LocalDateTime.now());
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMALastModifiedBy returns the {@link String} username of the user that last modified the RMA with the given {@link String} ID.
     * @param rmaId The {@link String} ID of the RMA whose last modified username we wish to fetch.
     * @return A {@link String} containing the username that last modified the RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMALastModifiedBy(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select lastModifiedBy from rma where rmaId ='" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("lastModifiedBy");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMALastModifiedBy updates the {@link String} username in the lastModifiedBy column of the RMA with the given ID to
     *  the current logged-in user.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMALastModifiedBy(String rmaId) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rma " +
                "set lastModifiedBy = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setString(1, getUser());
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMAStatus returns the {@link String} status description stored in the database for the given {@link String} RMA ID.
     * @param rmaId The {@link String} ID of the RMA whose status description we wish to fetch.
     * @return A {@link String} containing the RMA's status description.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMAStatus(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select rs.description " +
                "from rma r, rmaStatuses rs " +
                "where r.rmaId = '" + rmaId + "' " +
                "and r.statusId = rs.statusId;";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("description");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMAStatus updates the status assigned to the RMA with the given {@link String} ID to the passed-in
     *  {@link String} description.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param description The {@link String} status description whose identifier we want to use to update the RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMAStatus(String rmaId, String description) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rma " +
                "set statusId = (select statusId from rmaStatuses where description = ?) " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setString(1, description);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMACreditReplaceRepair returns the current value (credit, replace, or repair) stored in the database for the
     *  given {@link String} RMA ID.
     *  @param rmaId The {@link String} ID of the RMA whose creditReplaceRepair value we wish to fetch.
     *  @return A {@link String} containing the RMA's creditReplaceRepair value.
     *  @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMACreditReplaceRepair(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select creditReplaceRepair from rmaDetails where rmaId =  '" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("creditReplaceRepair");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMACreditReplaceRepair updates the specified RMA with the passed-in credit, replace, or repair value.
     * @param rmaId The {@link String} ID of the RMA whose details we want to update.
     * @param creditReplaceRepair The {@link String} new value of credit, replace, or repair that we want to save in the database.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMACreditReplaceRepair(String rmaId, String creditReplaceRepair) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set creditReplaceRepair = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setString(1, creditReplaceRepair);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMAReturnReasonCode returns the current return reason code and its description associated with the given
     *  {@link String} RMA ID.
     * @param rmaId The {@link String} ID of the RMA whose return reason code description we wish to fetch.
     * @return A {@link String} containing the return reason code and its description, separated by a space, and
     *         associated with the given RMA ID.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMAReturnReasonCode(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select rrc.returnReasonCode, rrc.description " +
                "from rmaDetails rd, returnReasonCodes rrc " +
                "where rd.rmaId = '" + rmaId + "' " +
                "and rd.returnReasonCode = rrc.returnReasonCode;";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("returnReasonCode") + " " + rs.getString("description");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMAReturnReasonCode updates the specified RMA with the passed-in returnReasonCodeDescription ("Code Description").
     * @param rmaId The {@link String} ID of the RMA whose details we want to update.
     * @param returnReasonCodeDescription The {@link String} ("Code Description") for the return reason code we want to use in RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMAReturnReasonCode(String rmaId, String returnReasonCodeDescription) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set returnReasonCode = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setString(1, returnReasonCodeDescription);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMAAdditionalInfo returns a {@link String} containing the additional info notes stored in the RMA Details for
     *  the given {@link String} RMA ID.
     *  @param rmaId The {@link String} ID of the RMA whose return additional info notes we wish to fetch.
     *  @return A {@link String} containing the additional info notes associated with the given RMA ID.
     *  @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMAAdditionalInfo(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select additionalInfo from rmaDetails where rmaId =  '" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("additionalInfo");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMAAdditionalInfo updates the requested RMA's details with the given {@link String} ID with the updated
     *  information passed-in through the {@link String }additionalInfo parameter.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param additionalInfo The updated {@link String} Additional Info value to store in the database.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMAAdditionalInfo(String rmaId, String additionalInfo) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set additionalInfo = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setString(1, additionalInfo);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMACreated returns a {@link LocalDateTime} object containing the creation date and time of the given RMA.
     * @param rmaId The {@link String} ID of the RMA whose created datetime we wish to fetch.
     * @return A {@link LocalDateTime} containing the creation date and time of the given RMA ID.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public LocalDateTime getRMACreated(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select created from rmaDetails where rmaId =  '" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        LocalDateTime result = null;
        if (rs.next())
            result = rs.getObject("created", LocalDateTime.class);

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getRMACreatedBy returns a {@link String} containing the username that created the given RMA request.
     * @param rmaId The {@link String} ID of the RMA whose created username we wish to fetch.
     * @return A {@link String} containing the username that created the RMA request.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMACreatedBy(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select createdBy from rmaDetails where rmaId =  '" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("createdBy");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** getRMAProduct returns a PurchaseOrderProduct containing the details of the product that is being returned
     *  in the given RMA.
     * @param rmaId The {@link String} ID of the RMA whose created username we wish to fetch.
     * @return A PurchaseOrderProduct containing the purchase order product information of the item being returned
     * in the requested RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public PurchaseOrderProduct getRMAProduct(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select pop.purchaseOrderProductId, pop.poNumber, p.productName, pc.categoryName, pop.quantity, " +
                "pop.orderDate, pop.deliverDate " +
                "from rmaDetails rd, purchaseOrderProducts pop, products p, productCategories pc " +
                "where rd.rmaId =  '" + rmaId + "' " +
                "and rd.purchaseOrderProductId = pop.purchaseOrderProductId " +
                "and pop.categoryId = p.categoryId " +
                "and pop.productId = p.productId " +
                "and p.categoryId = pc.categoryId;";

        // Fetch the results.
        connect();
        executeQuery(query);

        PurchaseOrderProduct result = null;
        if (rs.next())
            result = new PurchaseOrderProduct(
                rs.getInt("purchaseOrderProductId"),
                rs.getString("poNumber"),
                rs.getString("productName"),
                rs.getString("categoryName"),
                rs.getInt("quantity"),
                rs.getObject("orderDate", LocalDate.class),
                rs.getObject("deliverDate", LocalDate.class)
            );

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMAProduct updates the product being returned in the RMA with the passed-in int id.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param purchaseOrderProductId The new unique int value to set for the new product.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMAProduct(String rmaId, int purchaseOrderProductId) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set purchaseOrderProductId = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setInt(1, purchaseOrderProductId);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMAReturnQuantity fetches the int number of products being returned by the customer for the given {@link String}
     *  RMA ID.
     * @param rmaId The {@link String} ID of the RMA whose return quantity we wish to fetch.
     * @return An int containing the return quantity for the item in the given RMA. Returns a negative value if the
     * return quantity is not found.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public int getRMAReturnQuantity(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select returnQuantity from rmaDetails where rmaId =  '" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        int result = -1;
        if (rs.next())
            result = rs.getInt("returnQuantity");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMAReturnQuantity updates the int value for the amount of product being returned by the customer
     *  for the given {@link String} RMA ID.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param returnQuantity The new int value to set for the product in the RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMAReturnQuantity(String rmaId, int returnQuantity) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set returnQuantity = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setInt(1, returnQuantity);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMAReturnLabelTracker fetches the {@link String} return label tracking number stored in the referenced RMA.
     * @param rmaId The {@link String} ID of the RMA whose return label tracking number we wish to fetch.
     * @return A {@link String} containing the return label tracking number for the item in the given RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMAReturnLabelTracker(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select returnLabelTracker from rmaDetails where rmaId =  '" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("returnLabelTracker");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMAReturnLabelTracker updates the requested RMA's details with the new passed-in {@link String} return label tracking ID.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param returnLabelTracker The new tracking ID to assign to the RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMAReturnLabelTracker(String rmaId, String returnLabelTracker) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set returnLabelTracker = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setString(1, returnLabelTracker);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMAInitialEvaluation fetches the {@link String} initial evaluation done by an Analyst for the requested RMA.
     * @param rmaId The {@link String} ID of the RMA whose initial evaluation we wish to fetch.
     * @return A {@link String} containing the initial evaluation of the returned product for the given RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMAInitialEvaluation(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select initialEvaluation from rmaDetails where rmaId =  '" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("initialEvaluation");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMAInitialEvaluation updates the requested RMA's initial evaluation from an Analyst with the new
     *  {@link String} passed-in initial evaluation text.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param initialEvaluation The updated {@link String} Initial Evaluation value to store in the database.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMAInitialEvaluation(String rmaId, String initialEvaluation) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set initialEvaluation = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setString(1, initialEvaluation);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMAEngineeringEvaluation fetches the {@link String} engineering evaluation done by an Engineer for the requested RMA.
     * @param rmaId The {@link String} ID of the RMA whose engineering evaluation we wish to fetch.
     * @return A {@link String} containing the engineering evaluation of the returned product for the given RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMAEngineeringEvaluation(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select engineeringEvaluation from rmaDetails where rmaId =  '" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("engineeringEvaluation");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMAEngineeringEvaluation updates the requested RMA's engineering evaluation from an Engineer with the new {@link String}
     *  passed-in engineering evaluation text.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param engineeringEvaluation The new {@link String} engineering evaluation to save in the database.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMAEngineeringEvaluation(String rmaId, String engineeringEvaluation) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set engineeringEvaluation = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setString(1, engineeringEvaluation);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMADisposition fetches the {@link String} disposition for the requested RMA.
     * @param rmaId The {@link String} ID of the RMA whose disposition we wish to fetch.
     * @return A {@link String} containing the disposition of the returned product for the given RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMADisposition(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select d.disposition " +
                "from rmaDetails rd, dispositions d " +
                "where rd.rmaId =  '" + rmaId + "' " +
                "and rd.dispositionId = d.dispositionId;";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("disposition");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMADisposition updates the disposition ID stored in the specified RMA using the passed-in {@link String} disposition.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param disposition The {@link String} disposition description to look up and then use to update.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMADisposition(String rmaId, String disposition) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set dispositionId = (select dispositionId from dispositions where disposition = ?) " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setString(1, disposition);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMADispositionNotes fetches the {@link String} disposition notes for the requested RMA.
     * @param rmaId The {@link String} ID of the RMA whose disposition notes we wish to fetch.
     * @return A {@link String} containing the disposition notes for the returned product in the given RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMADispositionNotes(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select dispositionNotes from rmaDetails where rmaId =  '" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("dispositionNotes");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMADispositionNotes updates the disposition notes stored in the specified RMA using the passed-in {@link String} notes.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param dispositionNotes The new {@link String} dispositionNotes to save to the database.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMADispositionNotes(String rmaId, String dispositionNotes) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set dispositionNotes = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setString(1, dispositionNotes);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMAReplacementTrackingNumber fetches the {@link String} tracking number that will be used to ship the replacement
     *  back to the customer in the requested RMA.
     * @param rmaId The {@link String} ID of the RMA whose replacement tracking number we wish to fetch.
     * @return A {@link String} containing the replacement tracking number for the replacement or repair for the given RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public String getRMAReplacementTrackingNumber(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select replacementTrackingNumber from rmaDetails where rmaId =  '" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        String result = "";
        if (rs.next())
            result = rs.getString("replacementTrackingNumber");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMAReplacementTrackingNumber updates the specified RMA's tracking number used to ship the replacement product.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param replacementTrackingNumber The {@link String} new replacement tracking number to store in the database.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMAReplacementTrackingNumber(String rmaId, String replacementTrackingNumber) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set replacementTrackingNumber = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setString(1, replacementTrackingNumber);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMAReplacementShipDate fetches the {@link LocalDate} of the date that the replacement will be shipped to the customer
     *  for the given RMA.
     * @param rmaId The {@link String} ID of the RMA whose replacement ship date we wish to fetch.
     * @return A {@link LocalDate} containing the replacement ship date of the replacement or repair for the given RMA.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public LocalDate getRMAReplacementShipDate(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select replacementShipDate from rmaDetails where rmaId =  '" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        LocalDate result = null;
        if (rs.next())
            result = rs.getObject("replacementShipDate", LocalDate.class);

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMAReplacementShipDate updates the {@link LocalDate} date with the passed-in date the replacement will be shipped to
     *  the customer in the RMA with the specified {@link String} ID.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param replacementShipDate The new {@link LocalDate} date to store in the database.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMAReplacementShipDate(String rmaId, LocalDate replacementShipDate) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set replacementShipDate = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setObject(1, replacementShipDate);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** getRMAShipReplacementRepair fetches the boolean value of whether we will be shipping a replacement or repair to
     *  the customer referenced in the RMA with the given {@link String} ID.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @return A boolean containing the shipReplacementRepair value.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public boolean getRMAShipReplacementRepair(String rmaId) throws SQLException {
        // Set up the SQL query.
        query = "select shipReplacementRepair from rmaDetails where rmaId =  '" + rmaId + "';";

        // Fetch the results.
        connect();
        executeQuery(query);

        boolean result = false;
        if (rs.next())
            result = rs.getBoolean("shipReplacementRepair");

        // Clean up the connection.
        closeConnection();

        // Return the result.
        return result;
    }

    /** updateRMAShipReplacementRepair updates the RMA with the given {@link String} ID with the new shipReplacementRepair
     *  boolean value.
     * @param rmaId The {@link String} ID of the RMA to update.
     * @param shipReplacementRepair The new boolean value to store in the database.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void updateRMAShipReplacementRepair(String rmaId, boolean shipReplacementRepair) throws SQLException {
        // Setup the SQL query to update the database.
        query = "update rmaDetails " +
                "set shipReplacementRepair = ? " +
                "where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(2, rmaId);
        pStmt.setBoolean(1, shipReplacementRepair);
        pStmt.executeUpdate();
        closeConnection();
    }

    /** deleteRMA deletes the RMA with the given {@link String} ID.
     * @param rmaId The {@link String} ID of the RMA to delete.
     * @throws SQLException If there is an issue connecting to the database or an issue with the SQL query.
     */
    public void deleteRMA(String rmaId) throws SQLException {
        // Setup the SQL query to update the database.
        query = "delete from rma where rmaId = ?;";

        // Connect to the database and execute the query.
        connect();
        createPreparedStatement(query);
        pStmt.setString(1, rmaId);
        pStmt.executeUpdate();
        closeConnection();
    }
}