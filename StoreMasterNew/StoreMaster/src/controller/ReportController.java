package controller;

import dao.ReportDAO;

import java.sql.ResultSet;

public class ReportController {

    private ReportDAO dao = new ReportDAO();

    //Sales report Text
    public String getSalesReportText() {
        StringBuilder report = new StringBuilder();
        report.append("==== SALES REPORT ====\n\n");

        try {
            ResultSet rs = dao.getSales(); //get data from the DB
            while (rs != null && rs.next()) {
                report.append("Product: ").append(rs.getString("product_name")).append("\n");
                report.append("Quantity: ").append(rs.getInt("quantity")).append("\n");
                report.append("Total: Rs. ").append(rs.getDouble("total_price")).append("\n");
                report.append("Date: ").append(rs.getString("sale_date")).append("\n");
                report.append("User: ").append(rs.getString("username")).append("\n");
                report.append("----------------------------\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return report.toString();
    }

    //Product report text
    public String getProductReportText() {
        StringBuilder report = new StringBuilder();
        report.append("==== PRODUCT REPORT ====\n\n");

        try {
            ResultSet rs = dao.getProducts(); //getting data from the DB
            while (rs != null && rs.next()) {
                report.append("Product: ").append(rs.getString("product_name")).append("\n");
                report.append("Price: Rs. ").append(rs.getDouble("price")).append("\n");
                report.append("Quantity: ").append(rs.getInt("quantity")).append("\n");
                report.append("Supplier: ").append(rs.getString("supplier_name")).append("\n");
                report.append("Category: ").append(rs.getString("category_name")).append("\n");
                report.append("---------------------------------------\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return report.toString();
    }
}