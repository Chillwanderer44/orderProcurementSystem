/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.modules.purchase;

import com.google.gson.reflect.TypeToken;
import orderprocurementsystem.models.PurchaseOrder;
import orderprocurementsystem.models.PurchaseRequest;
import orderprocurementsystem.utils.FileHandler;
import orderprocurementsystem.models.Supplier;
import orderprocurementsystem.models.Item;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author amiry
 */
public class PurchaseManager {
    private static final String PR_FILE = "purchase_requests.json";
    private static final String PO_FILE = "purchase_orders.json";
    private static final String ITEMS_FILE = "items.json";
    private static final String SUPPLIERS_FILE = "suppliers.json";
    
    private List<PurchaseRequest> purchaseRequests;
    private List<PurchaseOrder> purchaseOrders;
    private List<Item> items;
    private List<Supplier> suppliers;
    private final Scanner scanner;
    private final String purchaseManagerId;

    public PurchaseManager(String purchaseManagerId) {
        this.scanner = new Scanner(System.in);
        this.purchaseManagerId = purchaseManagerId;
        loadData();
    }

    private void loadData() {
        Type prListType = new TypeToken<ArrayList<PurchaseRequest>>(){}.getType();
        Type poListType = new TypeToken<ArrayList<PurchaseOrder>>(){}.getType();
        Type itemListType = new TypeToken<ArrayList<Item>>(){}.getType();
        Type supplierListType = new TypeToken<ArrayList<Supplier>>(){}.getType();
        
        purchaseRequests = FileHandler.readFromJson(PR_FILE, prListType);
        purchaseOrders = FileHandler.readFromJson(PO_FILE, poListType);
        items = FileHandler.readFromJson(ITEMS_FILE, itemListType);
        suppliers = FileHandler.readFromJson(SUPPLIERS_FILE, supplierListType);
        
        if (purchaseRequests == null) purchaseRequests = new ArrayList<>();
        if (purchaseOrders == null) purchaseOrders = new ArrayList<>();
        if (items == null) items = new ArrayList<>();
        if (suppliers == null) suppliers = new ArrayList<>();
    }
    
    private void savePurchaseOrders() {
        FileHandler.writeToJson(purchaseOrders, PO_FILE);
    }

    private void savePurchaseRequests() {
        FileHandler.writeToJson(purchaseRequests, PR_FILE);
    }

    public void showPurchaseManagerMenu() {
        while (true) {
            System.out.println("\n=== Purchase Manager Menu ===");
            System.out.println("1. View Purchase Requisitions");
            System.out.println("2. Purchase Order Management");
            System.out.println("3. View Items");         
            System.out.println("4. View Suppliers");     
            System.out.println("5. Exit Program");
            
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1" -> viewPurchaseRequisitions();
                case "2" -> showPurchaseOrderMenu();
                case "3" -> viewItems();
                case "4" -> viewSuppliers();
                case "5" -> {
                    System.out.println("Exiting Program...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void showPurchaseOrderMenu(){
        while (true) {
        System.out.println("\n=== Purchase Order Management ===");
        System.out.println("1. Create Purchase Order");
        System.out.println("2. View My Purchase Order");
        System.out.println("3. Modify Purchase Order");
        System.out.println("4. Delete Purchase Order");
        System.out.println("5. Back");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> generatePurchaseOrder();
            case "2" -> viewPurchaseOrders();
            case "3" -> modifyPurchaseOrder();
            case "4" -> deletePurchaseOrder();
            case "5" -> { return; }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
    }
    
    private void viewItems() {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            return;
        }

        System.out.println("\n=== Item List ===");
        System.out.println("Code\tName\tStock\tReorder Level\tUnit Price");
        System.out.println("----------------------------------------");
        for (Item item : items) {
            System.out.printf("%s\t%s\t%d\t%d\t%.2f%n", 
                item.getItemCode(), 
                item.getItemName(), 
                item.getCurrentStock(),
                item.getReorderLevel(),
                item.getUnitPrice());
        }
    }

    private void viewSuppliers() {
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers found.");
            return;
        }

        System.out.println("\n=== Supplier List ===");
        System.out.println("Code\tName\tAddress");
        System.out.println("------------------------");
        for (Supplier supplier : suppliers) {
            System.out.printf("%s\t%s\t%s%n", 
                supplier.getSupplierCode(), 
                supplier.getSupplierName(), 
                supplier.getSupplierAddress());
        }
    }

    private void viewPurchaseRequisitions() {
        System.out.println("\n=== Purchase Requisitions ===");
        boolean found = false;
        
        for (PurchaseRequest pr : purchaseRequests) {
            if (pr.getStatus().equalsIgnoreCase("pending")) {
                System.out.println(pr);
                System.out.println("------------------------");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No pending purchase requisitions found.");
        }
    }

    private void generatePurchaseOrder() {
        System.out.println("\n=== Generate Purchase Order ===");
        System.out.print("Enter PR ID: ");
        String prId = scanner.nextLine();
        
        PurchaseRequest pr = purchaseRequests.stream()
                .filter(p -> p.getPrId().equals(prId) && p.getStatus().equals("pending"))
                .findFirst()
                .orElse(null);
        
        if (pr == null) {
            System.out.println("Purchase Requisition not found or already processed!");
            return;
        }

        String poId = generatePOId();
        PurchaseOrder po = new PurchaseOrder(
            poId,
            pr.getItemCode(),
            pr.getSupplierCode(),
            pr.getQuantity(),
            purchaseManagerId
        );
        
        purchaseOrders.add(po);
        pr.setStatus("converted");
        
        savePurchaseOrders();
        savePurchaseRequests();
        
        System.out.println("Purchase Order generated successfully with PO ID: " + poId);
    }

    private String generatePOId() {
        int nextId = purchaseOrders.size() + 1;
        return "PO" + String.format("%03d", nextId);
    }

    private void viewPurchaseOrders() {
        if (purchaseOrders.isEmpty()) {
            System.out.println("No purchase orders found.");
            return;
        }

        System.out.println("\n=== Purchase Orders ===");
        for (PurchaseOrder po : purchaseOrders) {
            System.out.println(po);
            System.out.println("------------------------");
        }
    }

    private void modifyPurchaseOrder() {
        System.out.print("Enter PO ID to modify: ");
        String poId = scanner.nextLine();
        
        PurchaseOrder po = purchaseOrders.stream()
                .filter(p -> p.getPoId().equals(poId) && 
                           p.getStatus().equals("pending"))
                .findFirst()
                .orElse(null);
                
        if (po == null) {
            System.out.println("PO not found or cannot be modified!");
            return;
        }

        System.out.println("\nCurrent PO Details:");
        System.out.println(po);

        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("Enter new quantity [" + po.getQuantity() + "]: ");
        String newQuantity = scanner.nextLine();
        if (!newQuantity.isEmpty()) {
            po.setQuantity(Integer.parseInt(newQuantity));
        }

        savePurchaseOrders();
        System.out.println("Purchase Order updated successfully!");
    }

    private void deletePurchaseOrder() {
        System.out.print("Enter PO ID to delete: ");
        String poId = scanner.nextLine();
        
        PurchaseOrder po = purchaseOrders.stream()
                .filter(p -> p.getPoId().equals(poId) && 
                           p.getStatus().equals("pending"))
                .findFirst()
                .orElse(null);
                
        if (po == null) {
            System.out.println("PO not found or cannot be deleted!");
            return;
        }

        System.out.println("\nPO to delete:");
        System.out.println(po);
        System.out.print("Are you sure you want to delete this PO? (y/n): ");
        
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            purchaseOrders.remove(po);
            savePurchaseOrders();
            System.out.println("Purchase Order deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
}
