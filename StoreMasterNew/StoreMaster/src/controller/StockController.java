package controller;

import database.DBConnection;
import model.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockController {

    // Add new stock to the system
    public boolean addStock(Stock stock) {
        String sql = "INSERT INTO Stock (product_id, quantity_added, added_date, purchase_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, stock.getProductId());
            pst.setInt(2, stock.getQuantityAdded());
            pst.setTimestamp(3, new Timestamp(stock.getAddedDate().getTime()));
            pst.setDouble(4, stock.getPurchasePrice());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all stock entries
    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM Stock";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                stocks.add(new Stock(
                        rs.getInt("stock_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity_added"),
                        rs.getTimestamp("added_date"),
                        rs.getDouble("purchase_price")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    // Get stock entry by the Id
    public List<Stock> getStockByProductId(int productId) {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM Stock WHERE product_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, productId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                stocks.add(new Stock(
                        rs.getInt("stock_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity_added"),
                        rs.getTimestamp("added_date"),
                        rs.getDouble("purchase_price")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }
}