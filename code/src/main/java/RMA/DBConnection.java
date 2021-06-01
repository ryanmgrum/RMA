package RMA;

import java.sql.*;

/** DBConnection encapsulates the underlying connectivity and query properties that
 *  make up the application's connection to the SQL Server database.
 */
class DBConnection {
    /** dbURL contains the URL connection {@link String} to connect to the database.
     */
    private static String dbURL;
    /** conn holds a reference to the {@link Connection} object that will be used in
     *  database calls.
     */
    private static Connection conn;
    /** stmt maintains a {@link Statement} object reference to use in database queries.
     */
    private static Statement stmt;
    /** cStmt maintains a {@link CallableStatement} object reference to use when executing
     *  stored procedures.
     */
    protected static CallableStatement cStmt;
    /** pStmt maintains a {@link PreparedStatement} object reference to use when executing
     *  insert, update, or delete statements.
     */
    protected static PreparedStatement pStmt;
    /** rs maintains a {@link ResultSet} object reference to use in database queries.
     */
    protected static ResultSet rs;


    /** Constructor that takes in a username, password, and instance name, and attempts
     *  to create a connection to the database.
     * @param username The {@link String} username to use to connect to the database.
     * @param password The {@link String} password to use to connect to the database.
     * @param instance The {@link String} SQL Server instance name to connect.
     * @throws SQLException If there is an issue connecting to the database.
     */
    protected DBConnection(String username, String password, String instance) throws SQLException {
        dbURL = "jdbc:sqlserver://" + instance + ";" +
                "database=rma;user=" + username + ";password=" + password + ";";
        // Create a new JDBC connection.
        connect();
    }

    /** connect creates the database connection.
     * @throws SQLException If a database access error occurs.
     */
    protected void connect() throws SQLException {
        // Create a new JDBC connection.
        if (conn == null)
            conn = DriverManager.getConnection(dbURL);
        else if (conn.isClosed())
            conn = DriverManager.getConnection(dbURL);
    }

    /** createCallableStatement prepares the cStmt field with the passed-in executable statement.
     * @param sql The query to use to create the {@link CallableStatement}.
     * @throws SQLException If a database access error occurs or there is an issue with
     * the SQL query.
     */
    protected void createCallableStatement(String sql) throws SQLException {
        if (cStmt != null)
            if (!cStmt.isClosed())
                cStmt.close();
        if (!isConnected())
            connect();
        cStmt = conn.prepareCall(sql);
    }

    /** createPreparedStatement prepares the pStmt field with the passed-in prepared SQL statement.
     * @param sql The query to use to create the {@link PreparedStatement}.
     * @throws SQLException If a database access error occurs or there is an issue with
     * the SQL query.
     */
    protected void createPreparedStatement(String sql) throws SQLException {
        if (pStmt != null)
            if (!pStmt.isClosed())
                pStmt.close();
        if (!isConnected())
            connect();
        pStmt = conn.prepareStatement(sql);
    }

    /** isConnected checks that we have a connection to the database.
     * @return True if the connection exists, otherwise false.
     */
    protected boolean isConnected() {
        try {
            return !conn.isClosed() && (
                (stmt == null ? false : !stmt.isClosed()) ||
                (cStmt == null ? false : !cStmt.isClosed()) ||
                (pStmt == null ? false : !pStmt.isClosed())
            );
        } catch (SQLException e) {
            return false;
        }
    }

    /** executeQuery loads the {@link Statement} instance with the passed-in SQL query
     *  and sets the local {@link ResultSet} rs variable with its results.
     * @param sql The {@link String} SQL query to parse.
     * @throws SQLException If a database access error occurs or there is an issue with
     * the SQL query.
     */
    protected void executeQuery(String sql) throws SQLException {
        if (stmt != null)
            if (!stmt.isClosed())
                stmt.close();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
    }

    /** executePreparedStatementQuery executes the user-modified pStmt {@link PreparedStatement}
     *  and sets the local {@link ResultSet} rs variable with its results.
     *  @throws SQLException If a database access error occurs or there is an issue with
     *  the SQL query.
     */
    protected void executePreparedStatementQuery() throws SQLException {
        rs = pStmt.executeQuery();
    }

    /** closeConnection closes the database and statement connections.
     * @throws SQLException If a database access error occurs.
     */
    protected void closeConnection() throws SQLException {
        if (stmt != null)
            if (!stmt.isClosed())
                stmt.close();
        if (cStmt != null)
            if (!cStmt.isClosed())
                cStmt.close();
        if (pStmt != null)
            if (!pStmt.isClosed())
                pStmt.close();
        if (conn != null)
            if (!conn.isClosed())
                conn.close();
    }
}