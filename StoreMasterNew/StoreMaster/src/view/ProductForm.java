package view;

import controller.ProductController;
import controller.SupplierController;
import controller.CategoryController;
import model.Product;
import model.Supplier;
import model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ProductForm extends JFrame {

    private JTextField txtName, txtPrice, txtQuantity, txtSearch;
    private JComboBox<Supplier> cmbSupplier;
    private JComboBox<Category> cmbCategory;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table;

    private ProductController productController = new ProductController();
    private SupplierController supplierController = new SupplierController();
    private CategoryController categoryController = new CategoryController();

    private int selectedProductId = -1;

    public ProductForm() {
        setTitle("Products - StoreMaster");
        setSize(1066, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Background image
        JLabel background = new JLabel(new ImageIcon("resources/images/Background.png"));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        //Heading
        JLabel heading = new JLabel("Manage Products", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        background.add(heading, BorderLayout.NORTH);

        //Center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        background.add(centerPanel, BorderLayout.CENTER);

        //Search panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        //Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        txtName = new JTextField();
        txtPrice = new JTextField();
        txtQuantity = new JTextField();
        cmbSupplier = new JComboBox<>();
        cmbCategory = new JComboBox<>();

        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(txtPrice);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(txtQuantity);
        formPanel.add(new JLabel("Supplier:"));
        formPanel.add(cmbSupplier);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(cmbCategory);

        topPanel.add(formPanel, BorderLayout.CENTER);

        //Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        searchPanel.setOpaque(false);
        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setForeground(Color.WHITE);
        txtSearch = new JTextField(20);
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);

        topPanel.add(searchPanel, BorderLayout.SOUTH);
        centerPanel.add(topPanel, BorderLayout.NORTH);

        //Bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        //Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        btnAdd = createButton("Add");
        btnUpdate = createButton("Update");
        btnDelete = createButton("Delete");
        btnClear = createButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        //table panel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 200));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(bottomPanel, BorderLayout.CENTER);

        //Load data
        loadSuppliers();
        loadCategories();
        loadProducts();

        //Button actions
        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnClear.addActionListener(e -> clearFields());

        //Table click
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    selectedProductId = Integer.parseInt(table.getValueAt(row, 0).toString());
                    txtName.setText(table.getValueAt(row, 1).toString());
                    txtPrice.setText(table.getValueAt(row, 2).toString());
                    txtQuantity.setText(table.getValueAt(row, 3).toString());
                    for (int i = 0; i < cmbSupplier.getItemCount(); i++) {
                        if (cmbSupplier.getItemAt(i).getSupplierId() == Integer.parseInt(table.getValueAt(row, 4).toString())) {
                            cmbSupplier.setSelectedIndex(i);
                            break;
                        }
                    }
                    for (int i = 0; i < cmbCategory.getItemCount(); i++) {
                        if (cmbCategory.getItemAt(i).getCategoryId() == Integer.parseInt(table.getValueAt(row, 5).toString())) {
                            cmbCategory.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

        //Search
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                searchProducts(txtSearch.getText());
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                searchProducts(txtSearch.getText());
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                searchProducts(txtSearch.getText());
            }
        });
    }

    //Helper method
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(11, 52, 80));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }

    //load data
    private void loadSuppliers() {
        cmbSupplier.removeAllItems();
        List<Supplier> suppliers = supplierController.getAllSuppliers();
        for (Supplier s : suppliers) cmbSupplier.addItem(s);
    }

    private void loadCategories() {
        cmbCategory.removeAllItems();
        List<Category> categories = categoryController.getAllCategories();
        for (Category c : categories) cmbCategory.addItem(c);
    }

    private void loadProducts() {
        List<Product> products = productController.getAllProducts();
        populateTable(products);
    }

    private void populateTable(List<Product> products) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Price", "Quantity", "Supplier ID", "Category ID"});
        for (Product p : products) {
            model.addRow(new Object[]{
                    p.getProductId(),
                    p.getProductName(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getSupplierId(),
                    p.getCategoryId()
            });
        }
        table.setModel(model);
    }

    //search
    private void searchProducts(String keyword) {
        if (keyword.isEmpty()) {
            populateTable(productController.getAllProducts());
        } else {
            populateTable(productController.searchProducts(keyword));
        }
    }

    //Crud operations
    private void addProduct() {
        try {
            String name = txtName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());
            Supplier supplier = (Supplier) cmbSupplier.getSelectedItem();
            Category category = (Category) cmbCategory.getSelectedItem();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter product name");
                return;
            }
            Product product = new Product(name, price, quantity, supplier.getSupplierId(), category.getCategoryId());
            if (productController.addProduct(product)) {
                JOptionPane.showMessageDialog(this, "Product Added Successfully");
                loadProducts();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Insert Failed");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Data");
        }
    }

    private void updateProduct() {
        if (selectedProductId == -1) {
            JOptionPane.showMessageDialog(this, "Select product first");
            return;
        }
        try {
            String name = txtName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());
            Supplier supplier = (Supplier) cmbSupplier.getSelectedItem();
            Category category = (Category) cmbCategory.getSelectedItem();
            Product product = new Product(selectedProductId, name, price, quantity, supplier.getSupplierId(), category.getCategoryId());
            if (productController.updateProduct(product)) {
                JOptionPane.showMessageDialog(this, "Product Updated");
                loadProducts();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Update Failed");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Data");
        }
    }

    private void deleteProduct() {
        if (selectedProductId == -1) {
            JOptionPane.showMessageDialog(this, "Select product first");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this product?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (productController.deleteProduct(selectedProductId)) {
                JOptionPane.showMessageDialog(this, "Product Deleted");
                loadProducts();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Delete Failed");
            }
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        selectedProductId = -1;
        table.clearSelection();
        cmbSupplier.setSelectedIndex(-1);
        cmbCategory.setSelectedIndex(-1);
        txtSearch.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductForm().setVisible(true));
    }
}