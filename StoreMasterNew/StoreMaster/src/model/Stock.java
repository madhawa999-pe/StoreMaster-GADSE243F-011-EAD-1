package model;

import java.util.Date;

public class Stock {
    private int stockId;
    private int productId;
    private int quantityAdded;
    private Date addedDate;
    private double purchasePrice;

    // Default constructor
    public Stock() {}

    // Full constructor
    public Stock(int stockId, int productId, int quantityAdded, Date addedDate, double purchasePrice) {
        this.stockId = stockId;
        this.productId = productId;
        this.quantityAdded = quantityAdded;
        this.addedDate = addedDate;
        this.purchasePrice = purchasePrice;
    }

    // Constructor without ID
    public Stock(int productId, int quantityAdded, Date addedDate, double purchasePrice) {
        this.productId = productId;
        this.quantityAdded = quantityAdded;
        this.addedDate = addedDate;
        this.purchasePrice = purchasePrice;
    }

    // Getters and Setters
    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantityAdded() {
        return quantityAdded;
    }

    public void setQuantityAdded(int quantityAdded) {
        this.quantityAdded = quantityAdded;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", productId=" + productId +
                ", quantityAdded=" + quantityAdded +
                ", addedDate=" + addedDate +
                ", purchasePrice=" + purchasePrice +
                '}';
    }
}