package view;

import javax.swing.*;
import java.awt.*;

public class DashboardForm extends JFrame {

    private JButton btnProducts, btnSuppliers, btnCategories, btnStock, btnSales, btnReports;

    public DashboardForm() {
        setTitle("Dashboard - StoreMaster");
        setSize(1066, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Left side bar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Color.decode("#0b3450"));
        sidebar.setPreferredSize(new Dimension(220, 0));

        //Left side bar Buttons
        btnProducts = createSidebarButton("Products");
        btnSuppliers = createSidebarButton("Suppliers");
        btnCategories = createSidebarButton("Categories");
        btnStock = createSidebarButton("Stock");
        btnSales = createSidebarButton("Sales");
        btnReports = createSidebarButton("Reports");

        //Add button with spacing
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebar.add(btnProducts);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnSuppliers);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnCategories);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnStock);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnSales);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnReports);
        sidebar.add(Box.createVerticalGlue());

        //Main Dashbaord area
        JLabel dashboardBackground = new JLabel(new ImageIcon("resources/images/MainDashboard.png"));
        dashboardBackground.setLayout(new GridBagLayout()); // centers the content

        //Text panel
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Vertical spacing
        textPanel.add(Box.createRigidArea(new Dimension(0, 100)));

        //Main Title
        JLabel mainTitle = new JLabel("Everything you need, all in one place");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        mainTitle.setForeground(Color.WHITE);
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainTitle.setHorizontalAlignment(SwingConstants.CENTER);

        //subtitle
        JLabel subTitle = new JLabel("Track sales, manage stock, and grow your business");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        subTitle.setForeground(Color.LIGHT_GRAY);
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitle.setHorizontalAlignment(SwingConstants.CENTER);
        subTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        //Gif image
        JLabel gifLabel = new JLabel(new ImageIcon("resources/images/little-pencil.gif")); // your GIF
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gifLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        //Seperator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(400, 2));
        separator.setForeground(new Color(200, 200, 200, 150));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        separator.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        //Add Components to text panel
        textPanel.add(mainTitle);
        textPanel.add(subTitle);
        textPanel.add(gifLabel);
        textPanel.add(separator);

        //Add text panel to dashbaoard
        dashboardBackground.add(textPanel);

        //Add panel to frame
        add(sidebar, BorderLayout.WEST);
        add(dashboardBackground, BorderLayout.CENTER);

        //Button actions
        btnProducts.addActionListener(e -> new ProductForm().setVisible(true));
        btnSuppliers.addActionListener(e -> new SupplierForm().setVisible(true));
        btnCategories.addActionListener(e -> new CategoryForm().setVisible(true));
        btnStock.addActionListener(e -> new StockForm().setVisible(true));
        btnSales.addActionListener(e -> new SalesForm().setVisible(true));
        btnReports.addActionListener(e -> new ReportForm().setVisible(true));
    }

    //helper method
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#0b3450"));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    //Main
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new DashboardForm().setVisible(true));
    }
}