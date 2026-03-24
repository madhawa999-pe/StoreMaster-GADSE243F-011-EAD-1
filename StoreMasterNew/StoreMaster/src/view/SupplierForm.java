package view;

import controller.SupplierController;
import model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SupplierForm extends JFrame {

    private JTextField txtName, txtPhone, txtEmail, txtSearch;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table;
    private SupplierController supplierController = new SupplierController();
    private int selectedSupplierId = -1;

    public SupplierForm() {
        setTitle("Suppliers - StoreMaster");
        setSize(1066, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //background image
        JLabel background = new JLabel(new ImageIcon("resources/images/Background.png"));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        //heading
        JLabel heading = new JLabel("Manage Suppliers", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        background.add(heading, BorderLayout.NORTH);

        //center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        background.add(centerPanel, BorderLayout.CENTER);
        //form + search
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        //form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        txtName = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();

        formPanel.add(new JLabel("Supplier Name:"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(txtPhone);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);

        topPanel.add(formPanel, BorderLayout.CENTER);

        //search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        searchPanel.setOpaque(false);
        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setForeground(Color.WHITE);
        txtSearch = new JTextField(20);
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);

        topPanel.add(searchPanel, BorderLayout.SOUTH);
        centerPanel.add(topPanel, BorderLayout.NORTH);

        //Button panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        //button panel
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

        // Table panel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 250));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(bottomPanel, BorderLayout.CENTER);

        //load data
        loadSuppliers();

        //Button actions
        btnAdd.addActionListener(e -> addSupplier());
        btnUpdate.addActionListener(e -> updateSupplier());
        btnDelete.addActionListener(e -> deleteSupplier());
        btnClear.addActionListener(e -> clearFields());

        //Table cilck
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow != -1){
                    selectedSupplierId = Integer.parseInt(table.getValueAt(selectedRow,0).toString());
                    txtName.setText(table.getValueAt(selectedRow,1).toString());
                    txtPhone.setText(table.getValueAt(selectedRow,2).toString());
                    txtEmail.setText(table.getValueAt(selectedRow,3).toString());
                }
            }
        });

        //search listner
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                searchSuppliers(txtSearch.getText());
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                searchSuppliers(txtSearch.getText());
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                searchSuppliers(txtSearch.getText());
            }
        });
    }

    //helper method
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(11, 52, 80));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }

    //load suppliers
    private void loadSuppliers() {
        List<Supplier> suppliers = supplierController.getAllSuppliers();
        populateTable(suppliers);
    }

    private void populateTable(List<Supplier> suppliers) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID","Name","Phone","Email"});
        for(Supplier s : suppliers){
            model.addRow(new Object[]{s.getSupplierId(), s.getSupplierName(), s.getPhone(), s.getEmail()});
        }
        table.setModel(model);
    }

    //serach method
    private void searchSuppliers(String keyword){
        List<Supplier> suppliers = supplierController.getAllSuppliers();
        if(keyword.isEmpty()){
            populateTable(suppliers);
            return;
        }
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID","Name","Phone","Email"});
        for(Supplier s : suppliers){
            if(s.getSupplierName().toLowerCase().contains(keyword.toLowerCase())){
                model.addRow(new Object[]{s.getSupplierId(), s.getSupplierName(), s.getPhone(), s.getEmail()});
            }
        }
        table.setModel(model);
    }

    //crud operations
    private void addSupplier() {
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();

        if(name.isEmpty()){
            JOptionPane.showMessageDialog(this,"Enter supplier name");
            return;
        }

        Supplier supplier = new Supplier(name, phone, email);
        if(supplierController.addSupplier(supplier)){
            JOptionPane.showMessageDialog(this,"Supplier Added");
            loadSuppliers();
            clearFields();
        }else{
            JOptionPane.showMessageDialog(this,"Insert Failed");
        }
    }

    private void updateSupplier() {
        if(selectedSupplierId == -1){
            JOptionPane.showMessageDialog(this,"Select supplier first");
            return;
        }

        Supplier supplier = new Supplier(selectedSupplierId, txtName.getText(), txtPhone.getText(), txtEmail.getText());
        if(supplierController.updateSupplier(supplier)){
            JOptionPane.showMessageDialog(this,"Supplier Updated");
            loadSuppliers();
            clearFields();
        }else{
            JOptionPane.showMessageDialog(this,"Update Failed");
        }
    }

    private void deleteSupplier() {
        if(selectedSupplierId == -1){
            JOptionPane.showMessageDialog(this,"Select supplier first");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,"Delete this supplier?","Confirm",JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
            if(supplierController.deleteSupplier(selectedSupplierId)){
                JOptionPane.showMessageDialog(this,"Supplier Deleted");
                loadSuppliers();
                clearFields();
            }else{
                JOptionPane.showMessageDialog(this,"Delete Failed");
            }
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        selectedSupplierId = -1;
        table.clearSelection();
        txtSearch.setText("");
    }

    //main
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new SupplierForm().setVisible(true));
    }
}