package controller;

import database.DBConnection;
import model.Product;

import java.sql.*;

public class SalesController {

    //Record sales to the DB
    public boolean recordSale(int productId, int quantity, int soldBy) {
        String fetchProduct = "SELECT price, quantity FROM Products WHERE product_id=?";
        String insertSale = "INSERT INTO Sales (product_id, quantity, total_price, sale_date, sold_by) VALUES (?, ?, ?, ?, ?)";
        String updateStock = "UPDATE Products SET quantity = ? WHERE product_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstFetch = conn.prepareStatement(fetchProduct);
             PreparedStatement pstInsert = conn.prepareStatement(insertSale);
             PreparedStatement pstUpdate = conn.prepareStatement(updateStock)) {

            conn.setAutoCommit(false);

            //Get Product Details
            pstFetch.setInt(1, productId);
            ResultSet rs = pstFetch.executeQuery();
            if (!rs.next()) return false;

            double price = rs.getDouble("price");
            int availableStock = rs.getInt("quantity");

            if (quantity > availableStock) return false;

            double totalPrice = price * quantity;

            // Insert sale record
            pstInsert.setInt(1, productId);
            pstInsert.setInt(2, quantity);
            pstInsert.setDouble(3, totalPrice);
            pstInsert.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstInsert.setInt(5, soldBy);
            pstInsert.executeUpdate();

            // Update product stock (set new quantity)
            pstUpdate.setInt(1, availableStock - quantity);
            pstUpdate.setInt(2, productId);
            pstUpdate.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}