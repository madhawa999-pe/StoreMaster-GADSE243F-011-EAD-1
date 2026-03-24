package view;

import controller.CategoryController;
import model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CategoryForm extends JFrame {

    private JTextField txtCategoryName;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table;

    private CategoryController categoryController = new CategoryController();
    private int selectedCategoryId = -1;

    public CategoryForm() {
        setTitle("Category Management - StoreMaster");
        setSize(1066, 600); // updated frame size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Background image
        JLabel background = new JLabel(new ImageIcon("resources/images/Background.png"));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        //Heading
        JLabel heading = new JLabel("Manage Categories", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        background.add(heading, BorderLayout.NORTH);

        //Center panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        background.add(centerPanel, BorderLayout.CENTER);

        //Form Panel
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200)); // more spacing for large frame

        txtCategoryName = new JTextField();

        formPanel.add(new JLabel("Category Name:"));
        formPanel.add(txtCategoryName);

        centerPanel.add(formPanel, BorderLayout.NORTH);

        //Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setOpaque(false);

        btnAdd = createButton("Add");
        btnUpdate = createButton("Update");
        btnDelete = createButton("Delete");
        btnClear = createButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        //Table form
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 250)); // bigger table for larger frame
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        //Load data to the table
        loadCategories();

        //Button actions
        btnAdd.addActionListener(e -> addCategory());
        btnUpdate.addActionListener(e -> updateCategory());
        btnDelete.addActionListener(e -> deleteCategory());
        btnClear.addActionListener(e -> clearFields());

        //Table click
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    selectedCategoryId = Integer.parseInt(table.getValueAt(row, 0).toString());
                    txtCategoryName.setText(table.getValueAt(row, 1).toString());
                }
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

    //Load table
    private void loadCategories() {
        List<Category> categories = categoryController.getAllCategories();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Category Name"});
        for (Category c : categories) {
            model.addRow(new Object[]{c.getCategoryId(), c.getCategoryName()});
        }
        table.setModel(model);
    }

    //Crud operations to the category
    private void addCategory() {
        String name = txtCategoryName.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter category name");
            return;
        }

        Category category = new Category();
        category.setCategoryName(name);

        if (categoryController.addCategory(category)) {
            JOptionPane.showMessageDialog(this, "Category Added");
            loadCategories();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Insert Failed");
        }
    }

    private void updateCategory() {
        if (selectedCategoryId == -1) {
            JOptionPane.showMessageDialog(this, "Select category first");
            return;
        }

        String name = txtCategoryName.getText();
        Category category = new Category(selectedCategoryId, name);

        if (categoryController.updateCategory(category)) {
            JOptionPane.showMessageDialog(this, "Category Updated");
            loadCategories();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Update Failed");
        }
    }

    private void deleteCategory() {
        if (selectedCategoryId == -1) {
            JOptionPane.showMessageDialog(this, "Select category first");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this category?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (categoryController.deleteCategory(selectedCategoryId)) {
                JOptionPane.showMessageDialog(this, "Category Deleted");
                loadCategories();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Delete Failed");
            }
        }
    }

    private void clearFields() {
        txtCategoryName.setText("");
        selectedCategoryId = -1;
        table.clearSelection();
    }

    //Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CategoryForm().setVisible(true));
    }
}