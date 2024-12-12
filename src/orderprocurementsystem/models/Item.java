/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.models;

/**
 *
 * @author amiry
 */
public class Item {
    private String itemCode;
    private String itemName;
    private String supplierCode;
    private int currentStock;
    private int reorderLevel;
    private double unitPrice;
    
    //constructor
    
    public Item(String itemCode, String itemName, String supplierCode, int currentStock, int reorderLevel, double unitPrice){
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.supplierCode = supplierCode;
        this.currentStock = currentStock;
        this.reorderLevel = reorderLevel;
        this.unitPrice = unitPrice;
    }
    
    // getter 
    
    public String getItemCode(){
        return itemCode;
    }
    public String getItemName(){
        return itemName;
    }
    public String getSupplierCode(){
        return supplierCode;
    }
    public int getCurrentStock(){
        return currentStock;
    }
    public int getReorderLevel(){
        return reorderLevel;
    }
    public double getUnitPrice(){
        return unitPrice;
    }
    
    //setter
    
    public void setItemCode(String itemCode){
        this.itemCode = itemCode;
    }
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    public void setCurrentStock(int currentStock){
        this.currentStock = currentStock;
    }
    public void setReorderLevel(int reorderLevel){
        this.reorderLevel = reorderLevel;
    }
    public void setUnitPrice(double unitPrice){
        this.unitPrice = unitPrice;
    }
    
    //@Overide for display purposes
    
    @Override
    public String toString(){
        return String.format("Item Code: %s\n" +
                           "Name: %s\n" +
                           "Supplier Code: %s\n" +
                           "Current Stock: %d\n" +
                           "Reorder Level: %d\n" +
                           "Unit Price: %.2f\n",
                           itemCode, itemName, supplierCode, currentStock, 
                           reorderLevel, unitPrice);
    }
}
