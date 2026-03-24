package controller;

import database.DBConnection;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryController {

    //Add a new category to system
    public boolean addCategory(Category category) {
        String sql = "INSERT INTO Categories (category_name) Values (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, category.getCategoryName());
            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Update an exixting category in the system
    public boolean updateCategory(Category category) {
        String sql = "UPDATE Categories SET category_name=? WHERE category_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, category.getCategoryName());
            pst.setInt(2, category.getCategoryId());
            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Delete a category in a system
    public boolean deleteCategory(int categoryId) {
        String sql = "DELETE FROM Categories WHERE category_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, categoryId);
            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Fetch all categories to the system
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
}