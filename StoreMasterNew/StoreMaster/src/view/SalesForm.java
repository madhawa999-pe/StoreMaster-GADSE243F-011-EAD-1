package view;

import controller.SalesController;
import controller.ProductController;
import controller.UserController;
import model.Product;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SalesForm extends JFrame {

    private JComboBox<Product> cmbProduct;
    private JComboBox<User> cmbUser;
    private JTextField txtQuantity;
    private JTextField txtTotalPrice;
    private JButton btnRecordSale;
    private JTable tblStock;

    private SalesController salesController = new SalesController();
    private ProductController productController = new ProductController();
    private UserController userController = new UserController();

    public SalesForm() {
        setTitle("Sales - StoreMaster");
        setSize(1066, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Background image
        JLabel background = new JLabel(new ImageIcon("resources/images/Background.png"));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        //heading
        JLabel heading = new JLabel("Record Sales", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        background.add(heading, BorderLayout.NORTH);

        //Center panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        background.add(centerPanel, BorderLayout.CENTER);

        //Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 300, 20, 300));

        cmbProduct = new JComboBox<>();
        txtQuantity = new JTextField();
        cmbUser = new JComboBox<>();
        txtTotalPrice = new JTextField();
        txtTotalPrice.setEditable(false);

        formPanel.add(new JLabel("Select Product:"));
        formPanel.add(cmbProduct);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(txtQuantity);
        formPanel.add(new JLabel("Sold By:"));
        formPanel.add(cmbUser);
        formPanel.add(new JLabel("Total Price:"));
        formPanel.add(txtTotalPrice);

        centerPanel.add(formPanel, BorderLayout.NORTH);

        //Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setOpaque(false);

        btnRecordSale = createButton("Record Sale");
        buttonPanel.add(btnRecordSale);

        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        //Table panel
        tblStock = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblStock);
        scrollPane.setPreferredSize(new Dimension(1000, 250));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        //Load data
        loadProducts();
        loadUsers();
        loadStockTable();

        //Action event
        txtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                calculateTotalPrice();
            }
        });

        cmbProduct.addActionListener(e -> calculateTotalPrice());

        btnRecordSale.addActionListener(e -> recordSale());
    }

    //Helper
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(11, 52, 80));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }

    private void loadProducts() {
        cmbProduct.removeAllItems();
        List<Product> products = productController.getAllProducts();
        for (Product p : products) {
            cmbProduct.addItem(p);
        }
        loadStockTable();
    }

    private void loadUsers() {
        cmbUser.removeAllItems();
        String sql = "SELECT * FROM Users";
        try (var conn = database.DBConnection.getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cmbUser.addItem(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateTotalPrice() {
        try {
            Product product = (Product) cmbProduct.getSelectedItem();
            if (product == null) return;
            int quantity = txtQuantity.getText().isEmpty() ? 0 : Integer.parseInt(txtQuantity.getText());
            double total = product.getPrice() * quantity;
            txtTotalPrice.setText(String.valueOf(total));
        } catch (NumberFormatException ignored) {}
    }

    private void recordSale() {
        try {
            Product product = (Product) cmbProduct.getSelectedItem();
            User user = (User) cmbUser.getSelectedItem();

            if (product == null || user == null) {
                JOptionPane.showMessageDialog(this, "Select product and user");
                return;
            }

            int quantity = Integer.parseInt(txtQuantity.getText());
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0");
                return;
            }

            if (quantity > product.getQuantity()) {
                JOptionPane.showMessageDialog(this, "Not enough stock available");
                return;
            }

            boolean success = salesController.recordSale(product.getProductId(), quantity, user.getUserId());
            if (success) {
                JOptionPane.showMessageDialog(this, "Sale recorded! Total Price: " + (product.getPrice() * quantity));
                loadProducts(); // refresh products & stock
                txtQuantity.setText("");
                txtTotalPrice.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to record sale");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    //load stocks in to table
    private void loadStockTable() {
        List<Product> products = productController.getAllProducts();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Product ID", "Product Name", "Price", "Current Stock"});

        for (Product p : products) {
            model.addRow(new Object[]{
                    p.getProductId(),
                    p.getProductName(),
                    p.getPrice(),
                    p.getQuantity()
            });
        }

        tblStock.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SalesForm().setVisible(true));
    }
}