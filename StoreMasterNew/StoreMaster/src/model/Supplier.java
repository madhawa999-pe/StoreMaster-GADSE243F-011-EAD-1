package model;

public class Supplier {
    private  int supplierId;
    private String supplierName;
    private String phone;
    private String email;

    public Supplier(){

    }
    public Supplier(int supplierId, String supplierName, String phone, String email) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.phone = phone;
        this.email = email;
    }
    public Supplier(String supplierName, String phone, String email) {
        this.supplierName = supplierName;
        this.phone = phone;
        this.email = email;
    }
    public int getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return supplierName;
    }

}
