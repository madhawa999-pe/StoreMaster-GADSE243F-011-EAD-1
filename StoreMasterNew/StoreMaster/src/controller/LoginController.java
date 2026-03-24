// ⚠️ this is not used anymore -- replaced by the UserCotroller. -- Because it User Controll do every thing better than this.
package controller;

import database.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    //Check login credentials
    public boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true; //User Exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; //Invalid Credentials
    }

    //get user obj after login
    public User getUser(String username) {
        String sql = "SELECT * FROM Users WHERE username=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}