package view;

import controller.ReportController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportForm extends JFrame {

    private JButton btnSalesReport, btnProductReport;
    private ReportController controller = new ReportController(); // Controller instance

    public ReportForm() {
        setTitle("Reports - StoreMaster");
        setSize(1066, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Background iamge
        JLabel background = new JLabel(new ImageIcon("resources/images/Background.png"));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        //Heading
        JLabel heading = new JLabel("Generate Reports", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        background.add(heading, BorderLayout.NORTH);

        //Ceneter panel
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        background.add(centerPanel, BorderLayout.CENTER);

        //Buttons
        btnSalesReport = createButton("Sales Report");
        btnProductReport = createButton("Product Report");

        centerPanel.add(btnSalesReport);
        centerPanel.add(btnProductReport);


        //Button actions for both

        //Sales report button
        btnSalesReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextArea area = new JTextArea();
                area.setText(controller.getSalesReportText());

                try {
                    boolean done = area.print(); // Opens print dialog
                    if (done) {
                        JOptionPane.showMessageDialog(ReportForm.this, "Sales PDF Saved/Printed successfully!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ReportForm.this, ex.getMessage());
                }
            }
        });

        //Product report button
        btnProductReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextArea area = new JTextArea();
                area.setText(controller.getProductReportText());

                try {
                    boolean done = area.print(); // Opens print dialog
                    if (done) {
                        JOptionPane.showMessageDialog(ReportForm.this, "Product PDF Saved/Printed successfully!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ReportForm.this, ex.getMessage());
                }
            }
        });
    }

    //Helper method
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(11, 52, 80));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(200, 60));
        button.setFocusPainted(false);
        return button;
    }

    //Main
    public static void main(String[] args) {
        new ReportForm().setVisible(true);
    }
}