package view;

import controller.UserController;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;

    private UserController userController = new UserController();

    public LoginForm() {
        setTitle("Login - StoreMaster");
        setSize(1066, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Background image
        JLabel background = new JLabel(new ImageIcon("resources/images/LoginPage.png"));
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setBorder(BorderFactory.createEmptyBorder(120, 350, 120, 350)); // top, left, bottom, right
        add(background);

        //Username Field
        txtUsername = new JTextField();
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        addLabelAndField(background, "Username:", txtUsername);

        //Password Field
        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        addLabelAndField(background, "Password:", txtPassword);

        //Login button
        btnLogin = new JButton("Login");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(220, 40));
        background.add(Box.createRigidArea(new Dimension(0, 20)));
        background.add(btnLogin);

        //Register Button
        btnRegister = new JButton("Register");
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setMaximumSize(new Dimension(220, 40));
        background.add(Box.createRigidArea(new Dimension(0, 10)));
        background.add(btnRegister);

        //Button Actions for login and register
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (userController.login(username, password)) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                new DashboardForm().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password!");
            }
        });

        btnRegister.addActionListener(e -> {
            new RegisterForm().setVisible(true);
        });
    }

    //Helper method
    private void addLabelAndField(JLabel background, String text, JTextField field) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE); // show text on image
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        background.add(label);
        background.add(Box.createRigidArea(new Dimension(0, 5)));
        background.add(field);
        background.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    //Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}