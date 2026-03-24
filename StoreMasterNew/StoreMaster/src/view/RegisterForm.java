package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterForm extends JFrame {

    private JTextField txtUsername, txtEmail;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnRegister;

    private UserController userController = new UserController();

    public RegisterForm() {
        setTitle("Register - StoreMaster");
        setSize(1066, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Background image
        JLabel background = new JLabel(new ImageIcon("resources/images/RegisterForm.png"));
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        //Transparent panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(420, 360));
        formPanel.setOpaque(false);

        background.add(formPanel); // center

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Input fields
        Dimension fieldSize = new Dimension(240, 35);

        txtUsername = new JTextField();
        txtEmail = new JTextField();
        txtPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();

        txtUsername.setPreferredSize(fieldSize);
        txtEmail.setPreferredSize(fieldSize);
        txtPassword.setPreferredSize(fieldSize);
        txtConfirmPassword.setPreferredSize(fieldSize);

        btnRegister = new JButton("Register");
        btnRegister.setPreferredSize(new Dimension(240, 42));

        //Add fields
        addRow(formPanel, gbc, 0, "Username:", txtUsername);
        addRow(formPanel, gbc, 1, "Email:", txtEmail);
        addRow(formPanel, gbc, 2, "Password:", txtPassword);
        addRow(formPanel, gbc, 3, "Confirm Password:", txtConfirmPassword);

        //Button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(btnRegister, gbc);

        //Action events
        btnRegister.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!");
                return;
            }

            User newUser = new User(username, password, email);

            if (userController.register(newUser)) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!");
            }
        });
    }

    //Helper method
    private void addRow(JPanel panel, GridBagConstraints gbc, int y, String labelText, JComponent field) {

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }
}