/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.models;

/**
 *
 * @author amiry
 */
public class PurchaseRequest {
    private String prId;
    private String itemCode;
    private int quantity;
    private String supplierCode;
    private String salesManagerId;
    private String status;
    
    public PurchaseRequest(String prId, String itemCode, int quantity, String supplierCode,
            String salesManagerId){
        this.prId = prId;
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.supplierCode = supplierCode;
        this.salesManagerId = salesManagerId;
        this.status = "pending";
    }
    
    // getter
    
    public String getPrId(){
        return prId;
    }
    public String getItemCode(){
        return itemCode;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getSupplierCode(){
        return supplierCode;
    }
    public String getSalesManagerId(){
        return salesManagerId;
    }
    public String getStatus(){
        return status;
    }
    
    // setter
    
    public void setPrId(String prId){
        this.prId = prId;
    }
    public void setItemCode(String itemCode){
        this.itemCode = itemCode;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setSupplierCode(String supplierCode){
        this.supplierCode = supplierCode;
    }
    public void setSalesManagerId(String salesManagerId){
        this.salesManagerId = salesManagerId;
    }
    public void setStatus(String status){
        this.status = status;
    }
    
    @Override
    public String toString() {
        return String.format("PR ID: %s\n" +
                           "Item Code: %s\n" +
                           "Quantity: %d\n" +
                           "Supplier Code: %s\n" +
                           "Sales Manager ID: %s\n" +
                           "Status: %s",
                           prId, itemCode, quantity,supplierCode,
                           salesManagerId, status);
    }
}
