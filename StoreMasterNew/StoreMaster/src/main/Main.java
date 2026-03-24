package main;

import view.LoginForm;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new IntroPage().setVisible(true);
        });
    }
}

class IntroPage extends JFrame {

    public IntroPage() {
        setTitle("Welcome - StoreMaster");
        setSize(1066, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== BACKGROUND =====
        JLabel background = new JLabel(new ImageIcon("resources/images/AnimatedBg.gif")); // animated gif
        background.setLayout(new BorderLayout());
        setContentPane(background);

        //Enter Button
        JButton btnEnter = new JButton("Enter StoreMaster");
        btnEnter.setFont(new Font("Arial", Font.BOLD, 24));
        btnEnter.setBackground(new Color(11, 52, 80));
        btnEnter.setForeground(Color.WHITE);
        btnEnter.setFocusPainted(false);
        btnEnter.setPreferredSize(new Dimension(300, 80));

        //panel with vertical spacing
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalStrut(400)); //Button up and down adjustments
        btnEnter.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btnEnter);

        background.add(panel, BorderLayout.CENTER);

        //button action to go to form
        btnEnter.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }
}