package RMA;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/** LoginModel contains the fields necessary to represent the model for the Login form.
 */
public class LoginModel {
    /** user contains the user's entered username.
     *  (Requirement 1.1)
     */
    private final StringProperty user;
    /** pass contains the user's entered password.
     *  (Requirement 1.2)
     */
    private final StringProperty pass;
    /** role contains the user's resulting role in the RMA database (admins, analysts, or engineers).
     *  (Requirement 1.3)
     */
    private final StringProperty role;
    /** selectedInstance contains the user's currently selected instance on the Login form.
     *  (Requirement 1.4)
     */
    private final StringProperty selectedInstance;
    /** instances contains the SQL Server instances found on the network.
     */
    private final ObservableList<String> instances;
    /** db contains the value for the RMA database name.
     */
    private final StringProperty db;

    /** The default constructor for LoginModel initializes the fields with empty values,
     *  and then searches the network for listening SQL Server instances.
     */
    public LoginModel() {
        user = new SimpleStringProperty();
        pass = new SimpleStringProperty();
        role = new SimpleStringProperty();
        instances = FXCollections.observableArrayList();
        selectedInstance = new SimpleStringProperty();
        db = new SimpleStringProperty("rma");
        getInstances();
    }

    /** getInstances issues a request to any listening SQL Server Browser Service
     *  on default UDP port 1434 for their information of running instances, and any
     *  that respond are added to the list of instances in the format HOSTNAME\INSTANCE_NAME.
     *
     *  If no hosts respond, an error message is shown and the program exits.
     *  (Requirement 1.4.3)
     */
    private void getInstances() {
        try {
            // Broadcast to SQL Server Browsers on network.
            DatagramSocket socket = new DatagramSocket();
            InetAddress ip = InetAddress.getByName("255.255.255.255");
            DatagramPacket packet = new DatagramPacket(new byte[]{2}, 1, ip, 1434);
            socket.send(packet);

            // Receive response
            byte[] buff = new byte[1024 * 1024];
            DatagramPacket recv = new DatagramPacket(buff, buff.length);
            socket.setSoTimeout(5 * 1000); // in milliseconds
            socket.receive(recv);

            // Parse response
            String response = new String(recv.getData(), 0, recv.getLength());
            String[] tokens = response.split(";");

            String host = "", instance = "";
            for (int i = 0; i < tokens.length; i++)
                if (tokens[i].contains("ServerName"))
                    host = tokens[i + 1];
                else if (tokens[i].contentEquals("InstanceName")) {
                    instance = tokens[i + 1];
                    instances.add(host + "\\" + instance);
                }

            // Close socket
            socket.close();
        } catch (IOException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(
                "Error while fetching SQL Server instances! No SQL Server Browser instances responding." +
                System.lineSeparator() + "Please make sure that SQL Server Browser service is running and that UDP port 1434 is not being blocked." +
                System.lineSeparator() + e.getLocalizedMessage()
            );
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.showAndWait();
            System.exit(1);
        }
    }

    /** getUser returns the String stored in user.
     * @return The {@link String} value stored in the user field.
     */
    public String getUser() {return user.getValueSafe();}

    /** userProperty returns the StringProperty field containing the user's username.
     * @return The {@link StringProperty} user field.
     */
    public StringProperty userProperty() {return user;}

    /** getPass returns the String stored in pass.
     * @return The {@link String} stored in pass.
     */
    public String getPass() {return pass.getValueSafe();}

    /** passProperty returns the StringProperty field containing the user's password.
     * @return The {@link StringProperty} pass field.
     */
    public StringProperty passProperty() {return pass;}

    /** setRole sets the value of role to the user-provided newRole parameter.
     *
     * @param newRole The role (admin, analyst, or engineer) to store.
     */
    public void setRole(String newRole) {role.setValue(newRole);}

    /** instancesProperty returns the {@link ObservableList} containing the list of instances found on the network.
     * @return The {@link ObservableList} instances field.
     */
    public ObservableList<String> instancesProperty() {return instances;}

    /** getSelectedInstance returns the String that the user selected from instances.
     * @return The {@link String} stored in selectedInstance.
     */
    public String getSelectedInstance() {return selectedInstance.getValueSafe();}

    /** selectedInstanceProperty returns the StringProperty containing the user's
     *  selected instance.
     * @return The {@link StringProperty} selectedInstance field.
     */
    public StringProperty selectedInstanceProperty() {return selectedInstance;}

    /** getDB returns the {@link String} stored in db.
     * @return The {@link String} stored in db.
     */
    public String getDB() {return db.getValueSafe();}
}