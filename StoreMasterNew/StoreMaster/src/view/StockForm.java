package view;

import controller.StockController;
import controller.ProductController;
import model.Stock;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class StockForm extends JFrame {

    private JComboBox<Product> cmbProduct;
    private JTextField txtQuantity, txtPurchasePrice;
    private JButton btnAdd, btnRefresh, btnLowStock;
    private JTable table;

    private StockController stockController = new StockController();
    private ProductController productController = new ProductController();

    private final int LOW_STOCK_THRESHOLD = 10;

    public StockForm() {
        setTitle("Stock Management - StoreMaster");
        setSize(1066, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //background image
        JLabel background = new JLabel(new ImageIcon("resources/images/Background.png"));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        //heading
        JLabel heading = new JLabel("Manage Stock", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        background.add(heading, BorderLayout.NORTH);

        //center panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        background.add(centerPanel, BorderLayout.CENTER);

        //form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 250, 20, 250));

        cmbProduct = new JComboBox<>();
        txtQuantity = new JTextField();
        txtPurchasePrice = new JTextField();

        formPanel.add(new JLabel("Product:"));
        formPanel.add(cmbProduct);
        formPanel.add(new JLabel("Quantity Added:"));
        formPanel.add(txtQuantity);
        formPanel.add(new JLabel("Purchase Price:"));
        formPanel.add(txtPurchasePrice);

        centerPanel.add(formPanel, BorderLayout.NORTH);

        //button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setOpaque(false);

        btnAdd = createButton("Add Stock");
        btnRefresh = createButton("Refresh Table");
        btnLowStock = createButton("Low Stock Alert");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnLowStock);

        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        //table panel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 250));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        //load data
        loadProducts();
        loadStockTable();

        //button action
        btnAdd.addActionListener(e -> addStock());
        btnRefresh.addActionListener(e -> loadStockTable());
        btnLowStock.addActionListener(e -> showLowStock());
    }

    //Helper button
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(11, 52, 80));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }

    //load products
    private void loadProducts() {
        cmbProduct.removeAllItems();
        List<Product> products = productController.getAllProducts();
        for(Product p : products) {
            cmbProduct.addItem(p);
        }
    }

    //load stocks
    private void loadStockTable() {
        List<Product> products = productController.getAllProducts();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Product ID","Product Name","Current Stock","Last Stock Date","Last Purchase Price"});

        for(Product p : products) {
            List<Stock> stocks = stockController.getStockByProductId(p.getProductId());
            Date lastDate = null;
            double lastPrice = 0;
            for(Stock s : stocks) {
                if(lastDate == null || s.getAddedDate().after(lastDate)) {
                    lastDate = s.getAddedDate();
                    lastPrice = s.getPurchasePrice();
                }
            }
            model.addRow(new Object[]{
                    p.getProductId(),
                    p.getProductName(),
                    p.getQuantity(),
                    lastDate,
                    lastPrice
            });
        }

        table.setModel(model);
    }

   //add new stocks
    private void addStock() {
        try {
            Product product = (Product) cmbProduct.getSelectedItem();
            if(product == null) {
                JOptionPane.showMessageDialog(this, "Select a product");
                return;
            }

            int quantity = Integer.parseInt(txtQuantity.getText());
            double price = Double.parseDouble(txtPurchasePrice.getText());

            int newQty = product.getQuantity() + quantity;
            product.setQuantity(newQty);
            boolean updated = productController.updateProduct(product);

            if(updated) {
                Stock stock = new Stock(product.getProductId(), quantity, new Date(), price);
                stockController.addStock(stock);

                JOptionPane.showMessageDialog(this, "Stock Added Successfully!");
                txtQuantity.setText("");
                txtPurchasePrice.setText("");
                loadStockTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update Product table");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity and Price must be valid numbers");
        }
    }

    //low stock alerts
    private void showLowStock() {
        List<Product> products = productController.getAllProducts();
        StringBuilder message = new StringBuilder("Low Stock Products (<= " + LOW_STOCK_THRESHOLD + "):\n");
        boolean foundLow = false;
        for(Product p : products) {
            int totalQty = p.getQuantity();
            if(totalQty <= LOW_STOCK_THRESHOLD) {
                message.append(p.getProductName()).append(" - Qty: ").append(totalQty).append("\n");
                foundLow = true;
            }
        }
        if(!foundLow) {
            message = new StringBuilder("No low stock products");
        }
        JOptionPane.showMessageDialog(this, message.toString());
    }

    //main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StockForm().setVisible(true));
    }
}