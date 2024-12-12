/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amiry
 */
public class Supplier {
    private String supplierCode;
    private String supplierName;
    private List<String> itemCodes;
    private String supplierAddress;
    
    public Supplier(String supplierCode, String supplierName, String supplierAddress){
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.itemCodes = new ArrayList<>();
        
    }
    
    // getter
    
    public String getSupplierCode(){
        return supplierCode;
    }
    public String getSupplierName(){
        return supplierName;
    }
    public String getSupplierAddress(){
        return supplierAddress;
    }
    public List<String> getItemCodes(){
        return itemCodes;
    }
    
    // setter
    
    public void setSupplierCode(String supplierCode){
        this.supplierCode = supplierCode;
    }
    public void setSupplierName(String supplierName){
        this.supplierName = supplierName;
    }
    public void setSupplierAddress(String supplierAddress){
        this.supplierAddress = supplierAddress;
    }
    public void setItemCodes(List<String> itemCodes){
        this.itemCodes = itemCodes;
    }
    
    public void addItemCode(String itemCode){
        if(!itemCodes.contains(itemCode)){
            itemCodes.add(itemCode);
        }
    }
    public void removeItemCode(String itemCode){
        itemCodes.remove(itemCode);
    }
    
    // override for displays
    @Override
    public String toString(){
        return String.format("Supplier Code: %s\n" +
                           "Name: %s\n" +
                           "Address: %s\n" +
                           "Items Supplied: %s",
                           supplierCode, supplierName, supplierAddress, 
                           String.join(", ", itemCodes));
    }
    }
