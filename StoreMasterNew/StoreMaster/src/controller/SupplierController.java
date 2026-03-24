package controller;

import database.DBConnection;
import model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierController {

    //Add a new supplier
    public boolean addSupplier(Supplier supplier) {
        String sql = "INSERT INTO Suppliers (supplier_name, phone, email) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, supplier.getSupplierName());
            pst.setString(2, supplier.getPhone());
            pst.setString(3, supplier.getEmail());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Update and added supplier
    public boolean updateSupplier(Supplier supplier) {
        String sql = "UPDATE Suppliers SET supplier_name=?, phone=?, email=? WHERE supplier_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, supplier.getSupplierName());
            pst.setString(2, supplier.getPhone());
            pst.setString(3, supplier.getEmail());
            pst.setInt(4, supplier.getSupplierId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Delete an existing supplier
    public boolean deleteSupplier(int supplierId) {
        String sql = "DELETE FROM Suppliers WHERE supplier_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, supplierId);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Display all supplers as a table format
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Suppliers ORDER BY supplier_name ASC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                suppliers.add(new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("supplier_name"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    //Search suppplier by name
    public List<Supplier> searchSuppliers(String keyword) {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Suppliers WHERE supplier_name LIKE ? ORDER BY supplier_name ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%"); // search anywhere in the name
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                suppliers.add(new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("supplier_name"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }
}