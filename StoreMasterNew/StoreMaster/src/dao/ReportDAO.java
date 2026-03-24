package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO {

    //Get all sales
    public ResultSet getSales() {
        String sql = "SELECT p.product_name, s.quantity, s.total_price, s.sale_date, u.username " +
                "FROM Sales s " +
                "JOIN Products p ON s.product_id = p.product_id " +
                "JOIN Users u ON s.sold_by = u.user_id";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Get all products
    public ResultSet getProducts() {
        String sql = "SELECT p.product_name, p.price, p.quantity, s.supplier_name, c.category_name " +
                "FROM Products p " +
                "JOIN Suppliers s ON p.supplier_id = s.supplier_id " +
                "LEFT JOIN Categories c ON p.category_id = c.category_id";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}