package model;

import java.util.Date;

public class Sale {
    private int saleId;
    private int productId;
    private int quantity;
    private double totalPrice;
    private Date saleDate;
    private int soldBy; // userId of seller

    // Default constructor
    public Sale() {}

    // Full constructor
    public Sale(int saleId, int productId, int quantity, double totalPrice, Date saleDate, int soldBy) {
        this.saleId = saleId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.saleDate = saleDate;
        this.soldBy = soldBy;
    }

    // Constructor without ID
    public Sale(int productId, int quantity, double totalPrice, Date saleDate, int soldBy) {
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.saleDate = saleDate;
        this.soldBy = soldBy;
    }

    // Getters and Setters
    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(int soldBy) {
        this.soldBy = soldBy;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", saleDate=" + saleDate +
                ", soldBy=" + soldBy +
                '}';
    }
}