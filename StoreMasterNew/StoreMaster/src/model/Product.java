package model;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int quantity;
    private int supplierId;
    private int categoryId;

    // Default constructor
    public Product() {}

    // Constructor without ID (for adding new product)
    public Product(String productName, double price, int quantity, int supplierId, int categoryId) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.supplierId = supplierId;
        this.categoryId = categoryId;
    }

    // Constructor with ID (for fetching from DB)
    public Product(int productId, String productName, double price, int quantity, int supplierId, int categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.supplierId = supplierId;
        this.categoryId = categoryId;
    }

    // Getters and setters
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return productName;
    }
}