/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.models;

/**
 *
 * @author amiry
 */
public class PurchaseOrder {
    private String poId;
    private String itemCode;
    private String supplierCode;
    private int quantity;
    private String purchaseManagerId;
    private String status;  // pending, approved, rejected

    // Constructor
    public PurchaseOrder(String poId, String itemCode, String supplierCode, int quantity,
                        String purchaseManagerId) {
        this.poId = poId;
        this.itemCode = itemCode;
        this.supplierCode = supplierCode;
        this.quantity = quantity;
        this.purchaseManagerId = purchaseManagerId;
        this.status = "pending";
    }

    // Getters
    public String getPoId() {
        return poId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPurchaseManagerId() {
        return purchaseManagerId;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setPoId(String poId) {
        this.poId = poId;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPurchaseManagerId(String purchaseManagerId) {
        this.purchaseManagerId = purchaseManagerId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("PO ID: %s\n" +
                           "Item Code: %s\n" +
                           "Supplier Code: %s\n" +
                           "Quantity: %d\n" +
                           "Purchase Manager ID: %s\n" +
                           "Status: %s\n",
                           poId, itemCode, supplierCode, quantity,
                           purchaseManagerId, status);
    }
}
