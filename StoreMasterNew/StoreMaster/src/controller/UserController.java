package controller;

import database.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    // Login method
    public boolean login(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            return rs.next(); // true if user exists

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Register method
    public boolean register(User user) {
        String checkSql = "SELECT * FROM Users WHERE username=? OR email=?";
        String insertSql = "INSERT INTO Users (username, password, email) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            // Check if username or email already exists
            try (PreparedStatement pstCheck = conn.prepareStatement(checkSql)) {
                pstCheck.setString(1, user.getUsername());
                pstCheck.setString(2, user.getEmail());
                ResultSet rs = pstCheck.executeQuery();
                if (rs.next()) {
                    return false; // username or email already exists
                }
            }

            // Insert new user
            try (PreparedStatement pstInsert = conn.prepareStatement(insertSql)) {
                pstInsert.setString(1, user.getUsername());
                pstInsert.setString(2, user.getPassword());
                pstInsert.setString(3, user.getEmail());

                int rows = pstInsert.executeUpdate();
                return rows > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}