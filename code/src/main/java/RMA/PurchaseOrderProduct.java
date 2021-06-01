package RMA;

import javafx.beans.binding.ObjectBinding;

import java.sql.SQLException;
import java.time.LocalDate;

/** PurchaseOrderProduct contains the specific information about a product
 *  coming from a purchase order in the RMA database.
 */
public class PurchaseOrderProduct extends ObjectBinding<PurchaseOrderProduct> {
    /** purchaseOrderProductId contains the unique integer value identifying this PurchaseOrderProduct.
     */
    private int purchaseOrderProductId;
    /** poNumber contains the business address's PO number {@link String} tied to this PurchaseOrderProduct.
     */
    private String poNumber;
    /** productName contains the product's name as a {@link String}.
     */
    private String productName;
    /** productCategory contains the product's category name as a {@link String}.
     */
    private String categoryName;
    /** quantity contains the integer value of how many units of the product the customer is returning.
     */
    private int quantity;
    /** orderDate contains the date the customer ordered this product, stored as a {@link LocalDate}.
     */
    private LocalDate orderDate;
    /** deliverDate contains the date the customer received this product, stored as a {@link LocalDate}.
     */
    private LocalDate deliverDate;

    /** Constructor that takes the passed-in purchaseOrderProductId and fetches the product's
     *  information from the database.
     * @param purchaseOrderProductId The id to search for in the database.
     * @throws SQLException If there is an issue connecting to the SQL Server database or the SQL query.
     */
    public PurchaseOrderProduct(int purchaseOrderProductId) throws SQLException {
        // Fetch product from database.
        PurchaseOrderProduct product = DBService.getInstance().getPurchaseOrderProduct(purchaseOrderProductId);
        // Populate the fields.
        this.purchaseOrderProductId = product.getPurchaseOrderProductId();
        poNumber = product.getPONumber();
        productName = product.getProductName();
        categoryName = product.getProductCategory();
        quantity = product.getQuantity();
        orderDate = product.getOrderDate();
        deliverDate = product.getDeliverDate();
    }

    /** Constructor for PurchaseOrderProduct that manually fills in all the details.
     * @param purchaseOrderProductId The int unique id to store in this PurchaseOrderProduct.
     * @param poNumber The {@link String} PO number associated with this PurchaseOrderProduct.
     * @param productName The {@link String} product's name.
     * @param categoryName The {@link String} category name in which this product resides.
     * @param quantity The int amount being ordered.
     * @param orderDate The {@link LocalDate} of when the product was ordered by the customer.
     * @param deliverDate The {@link LocalDate} of when the product was delivered to the customer.
     */
    public PurchaseOrderProduct(
        int purchaseOrderProductId, String poNumber, String productName,
        String categoryName, int quantity, LocalDate orderDate,
        LocalDate deliverDate
    ) {
        // Populate the fields with the passed-in parameters.
        this.purchaseOrderProductId = purchaseOrderProductId;
        this.poNumber = poNumber;
        this.productName = productName;
        this.categoryName = categoryName;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.deliverDate = deliverDate;
    }

    /** computeValue returns the value representing this PurchaseOrderProduct when bound as an {@link javafx.beans.property.ObjectProperty}.
     * @return A copy of this PurchaseOrderProduct {@link String} containing the category name, then product name.
     */
    @Override
    protected PurchaseOrderProduct computeValue() {
        return new PurchaseOrderProduct(
            purchaseOrderProductId,
            poNumber,
            productName,
            categoryName,
            quantity,
            orderDate,
            deliverDate
        );
    }

    /** toString is used to output the appropriate value to represent this PurchaseOrderProduct.
     * @return A {@link String} containing the category name, a comma, and the product name.
     */
    @Override
    public String toString() {return String.format("%s, %s", categoryName, productName);}

    /** getPurchaseOrderProductId returns the id of this PurchaseOrderProduct.
     * @return An int, contained in the purchaseOrderProductId field.
     */
    public int getPurchaseOrderProductId() {return purchaseOrderProductId;}

    /** getPONumber returns the purchase order number associated with this PurchaseOrderProduct.
     * @return The {@link String} poNumber field.
     */
    public String getPONumber() {return poNumber;}

    /** getProductName returns the product name contained in the productName {@link String} field.
     * @return The {@link String} value of the productName field.
     */
    public String getProductName() {return productName;}

    /** getProductCategory returns the category name contained in the productCategory {@link String} field.
     * @return The {@link String} value of the productCategory field.
     */
    public String getProductCategory() {return categoryName;}

    /** getQuantity returns the int quantity of the amount being returned by the customer.
     * @return The int quantity field.
     */
    public int getQuantity() {return quantity;}

    /** getOrderDate returns the date the customer placed the order for the item, stored in a {@link LocalDate}.
     * @return The {@link LocalDate} orderDate field.
     */
    public LocalDate getOrderDate() {return orderDate;}

    /** getDeliverDate returns the date the product was delivered to the customer, stored in a {@link LocalDate}.
     * @return The {@link LocalDate} deliverDate field.
     */
    public LocalDate getDeliverDate() {return deliverDate;}
}