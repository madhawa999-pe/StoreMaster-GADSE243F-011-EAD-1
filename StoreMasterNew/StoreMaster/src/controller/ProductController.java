package controller;

import database.DBConnection;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductController {

    //Add a new product to the system
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO Products (product_name, price, quantity, supplier_id, category_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, product.getProductName());
            pst.setDouble(2, product.getPrice());
            pst.setInt(3, product.getQuantity());
            pst.setInt(4, product.getSupplierId());
            pst.setInt(5, product.getCategoryId());

            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Update an added Product
    public boolean updateProduct(Product product) {
        String sql = "UPDATE Products SET product_name=?, price=?, quantity=?, supplier_id=?, category_id=? WHERE product_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, product.getProductName());
            pst.setDouble(2, product.getPrice());
            pst.setInt(3, product.getQuantity());
            pst.setInt(4, product.getSupplierId());
            pst.setInt(5, product.getCategoryId());
            pst.setInt(6, product.getProductId());

            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Delete an existing product
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM Products WHERE product_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, productId);
            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //diaplay all the data in a table format
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("supplier_id"),
                        rs.getInt("category_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    //Search all product by the product name
    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE product_name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%"); // partial match
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("supplier_id"),
                        rs.getInt("category_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}