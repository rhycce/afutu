
package com.vangh.backbone.dzorwulu.connector;

import com.vangh.backbone.dzorwulu.datatype.Order;
import com.vangh.backbone.dzorwulu.datatype.Package;
import com.vangh.backbone.dzorwulu.utils.Utils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by janet on 10/5/15.
 */
public class MysqlConnector {
    private static final Logger LOGGER = Logger.getLogger(MysqlConnector.class.getName());
    private final Properties properties;
    private Connection conn;
    private PreparedStatement addNewOrderStatement;
    private PreparedStatement addNewPackageStatement;
    private PreparedStatement updateOrderCommentsStatement;
    private PreparedStatement updateassignedToStatement;
    private PreparedStatement updateOrderStatusStatement;
    private PreparedStatement updateOrderSetDeliveredStatement;
    private PreparedStatement selectOrderByIdStatement;
    private PreparedStatement selectPackageByIdStatement;
    private PreparedStatement selectPackageByOrderStatement;

    public MysqlConnector(Properties props) {
        this.properties = props;


    }

    public boolean loadDriver(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("jdbc:mysql://");
            stringBuilder.append(properties.getProperty("HOST"));
            stringBuilder.append("/");
            stringBuilder.append(properties.getProperty("DATABASE"));
            stringBuilder.append("?user=");
            stringBuilder.append(properties.getProperty("USER"));
            stringBuilder.append("&password=");
            stringBuilder.append(properties.getProperty("PASSWORD"));
            LOGGER.info("Connecting to database");
            conn = DriverManager.getConnection(stringBuilder.toString());
            addNewOrderStatement = conn.prepareStatement("Insert into orders (source, destination, client, recipient, comments, orderdate) values(?, ?, ?, ?, ?, ?)");
            addNewPackageStatement = conn.prepareStatement("Insert into packages (weight, ordernumber, content, perishable, fagile, insured) values(?, ?, ?, ?, ?, ?)");
            updateassignedToStatement = conn.prepareStatement("Update orders set assignedto = ? where id = ?");
            updateOrderCommentsStatement = conn.prepareStatement("Update orders set comments = ? where id = ?");
            updateOrderStatusStatement = conn.prepareStatement("Update orders set status = ? where id = ?");
            updateOrderSetDeliveredStatement = conn.prepareStatement("Update orders set status = DELIVERED, delivereddate = ? where id = ?");
            selectOrderByIdStatement = conn.prepareStatement("Select * from orders where id = ?");
            selectPackageByIdStatement = conn.prepareStatement("Select * from package where id = ?");
            selectPackageByOrderStatement = conn.prepareStatement("Select * from package where ordernumber = ?");
            return true;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            LOGGER.severe("SQLException: " + e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe("VendorError: " + e.getErrorCode());
        }
        return false;
    }

    public void addNewOrder(Order newOrder){
        try {
            addNewOrderStatement.setString(1, newOrder.getSource());
            addNewOrderStatement.setString(2, newOrder.getDestination());
            addNewOrderStatement.setInt(3, newOrder.getClient());
            addNewOrderStatement.setInt(4, newOrder.getRecipient());
            addNewOrderStatement.setString(5, newOrder.getComments());
            addNewOrderStatement.setTimestamp(6, newOrder.getOrderdate());
            addNewOrderStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public void updateStatus(Utils.OrderStatus status, int orderID){
        try {
            if(status.equals(Utils.OrderStatus.DELIVERED)) {
                updateOrderSetDeliveredStatement.setTimestamp(1, Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())));
                updateOrderSetDeliveredStatement.setInt(2, orderID);
            }else {
                    updateOrderStatusStatement.setString(1, status.toString());
                    updateOrderStatusStatement.setInt(2, orderID);
                    updateOrderStatusStatement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
        }

    }

    public List<Package> selectPackageByOrderNumber(int orderID){
        try {
            selectPackageByOrderStatement.setInt(1, orderID);
            ResultSet resultset = selectPackageByOrderStatement.executeQuery();
            List<Package> results = new ArrayList<Package>();
            while(resultset.next()){
                Package aPackage = new Package(resultset.getInt("id"), resultset.getDouble("weight"), resultset.getInt("ordernumber"), resultset.getString("content"), resultset.getBoolean("perishable"), resultset.getBoolean("fragile"), resultset.getBoolean("insured"));
                results.add(aPackage);
            }
            return results;
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
        }
        return null;
    }

    public void terminateSession() throws SQLException {
        conn.close();
    }
}
