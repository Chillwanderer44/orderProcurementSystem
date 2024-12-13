/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.modules.sales;

import com.google.gson.reflect.TypeToken;
import orderprocurementsystem.models.Item;
import orderprocurementsystem.models.PurchaseRequest;
import orderprocurementsystem.utils.FileHandler;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author amiry
 */
public class SalesManager {
    private static final String PR_FILE = "purchase_requests.json";
    private static final String ITEMS_FILE = "items.json";
    private List<PurchaseRequest> purchaseRequests;
    private List<Item> items;
    private final Scanner scanner;
    private final String salesManagerId;  // Current logged-in SM's ID

    public SalesManager(String salesManagerId) {
        this.scanner = new Scanner(System.in);
        this.salesManagerId = salesManagerId;
        loadData();
    }

    private void loadData() {
        Type prListType = new TypeToken<ArrayList<PurchaseRequest>>(){}.getType();
        Type itemListType = new TypeToken<ArrayList<Item>>(){}.getType();
        
        purchaseRequests = FileHandler.readFromJson(PR_FILE, prListType);
        items = FileHandler.readFromJson(ITEMS_FILE, itemListType);
        
        if (purchaseRequests == null) {
            purchaseRequests = new ArrayList<>();
        }
    }

    private void savePurchaseRequests() {
        FileHandler.writeToJson(purchaseRequests, PR_FILE);
    }

    public void showSalesManagerMenu() {
        while (true) {
            System.out.println("\n=== Sales Manager Menu ===");
            System.out.println("1. View Items");
            System.out.println("2. Enter Daily Sales");
            System.out.println("3. Purchase Requisition Management");
            System.out.println("4. Exit Program");
            
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1" -> viewItems();
                case "2" -> enterDailySales();
                case "3" -> showPrMenu();
                case "4" -> {
                    System.out.println("Exiting Program...");
                    System.exit(0);
                }
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
        System.out.println("Code\tName\tStock\tReorder Level");
        System.out.println("----------------------------------------");
        for (Item item : items) {
            System.out.printf("%s\t%s\t%d\t%d%n", 
                item.getItemCode(), 
                item.getItemName(), 
                item.getCurrentStock(),
                item.getReorderLevel());
        }
    }

    private void enterDailySales() {
        System.out.println("\n=== Enter Daily Sales ===");
        
        System.out.print("Enter Item Code: ");
        String itemCode = scanner.nextLine();
        
        Item item = items.stream()
                .filter(i -> i.getItemCode().equals(itemCode))
                .findFirst()
                .orElse(null);
                
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }

        System.out.println("Current stock: " + item.getCurrentStock());
        System.out.print("Enter quantity sold: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        
        if (quantity > item.getCurrentStock()) {
            System.out.println("Error: Not enough stock!");
            return;
        }

        item.setCurrentStock(item.getCurrentStock() - quantity);
        FileHandler.writeToJson(items, ITEMS_FILE);
        System.out.println("Sales recorded successfully!");
        
        // Check if stock is below reorder level
        if (item.getCurrentStock() <= item.getReorderLevel()) {
            System.out.println("Warning: Stock is below reorder level!");
            System.out.print("Would you like to create a Purchase Requisition? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                createPRForItem(item);
            }
        }
    }

    private void createPurchaseRequisition() {
        System.out.println("\n=== Create Purchase Requisition ===");
        
        System.out.print("Enter Item Code: ");
        String itemCode = scanner.nextLine();
        
        Item item = items.stream()
                .filter(i -> i.getItemCode().equals(itemCode))
                .findFirst()
                .orElse(null);
                
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }

        createPRForItem(item);
    }

    private void createPRForItem(Item item) {
        System.out.print("Enter quantity needed: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        
        String prId = generatePRId();
        
        PurchaseRequest pr = new PurchaseRequest(
            prId, 
            item.getItemCode(), 
            quantity,
            item.getSupplierCode(),
            salesManagerId
        );
        
        purchaseRequests.add(pr);
        savePurchaseRequests();
        System.out.println("Purchase Requisition created successfully with PR ID: " + prId);
    }

    private String generatePRId() {
        int nextId = purchaseRequests.size() + 1;
        return "PR" + String.format("%03d", nextId);
    }

    private void viewMyPurchaseRequisitions() {
        System.out.println("\n=== My Purchase Requisitions ===");
        boolean found = false;
        
        for (PurchaseRequest pr : purchaseRequests) {
            if (pr.getSalesManagerId().equals(salesManagerId)) {
                System.out.println(pr);
                System.out.println("------------------------");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No purchase requisitions found.");
        }
    }
    
    private void showPrMenu() {
    while (true) {
        System.out.println("\n=== Purchase Requisition Management ===");
        System.out.println("1. Create Purchase Requisition");
        System.out.println("2. View My Purchase Requisitions");
        System.out.println("3. Modify Purchase Requisition");
        System.out.println("4. Delete Purchase Requisition");
        System.out.println("5. Back");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> createPurchaseRequisition();
            case "2" -> viewMyPurchaseRequisitions();
            case "3" -> modifyPurchaseRequisition();
            case "4" -> deletePurchaseRequisition();
            case "5" -> { return; }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
}

private void modifyPurchaseRequisition() {
    System.out.print("Enter PR ID to modify: ");
    String prId = scanner.nextLine();
    
    PurchaseRequest pr = purchaseRequests.stream()
            .filter(p -> p.getPrId().equals(prId) && 
                        p.getSalesManagerId().equals(salesManagerId) &&
                        p.getStatus().equals("pending"))
            .findFirst()
            .orElse(null);
            
    if (pr == null) {
        System.out.println("PR not found or cannot be modified!");
        return;
    }

    System.out.println("\nCurrent PR Details:");
    System.out.println(pr);

    System.out.println("\nEnter new details (press Enter to keep current value):");
    
    System.out.print("Enter new quantity [" + pr.getQuantity() + "]: ");
    String newQuantity = scanner.nextLine();
    if (!newQuantity.isEmpty()) {
        pr.setQuantity(Integer.parseInt(newQuantity));
    }

    savePurchaseRequests();
    System.out.println("Purchase Requisition updated successfully!");
}

private void deletePurchaseRequisition() {
    System.out.print("Enter PR ID to delete: ");
    String prId = scanner.nextLine();
    
    PurchaseRequest pr = purchaseRequests.stream()
            .filter(p -> p.getPrId().equals(prId) && 
                        p.getSalesManagerId().equals(salesManagerId) &&
                        p.getStatus().equals("pending"))
            .findFirst()
            .orElse(null);
            
    if (pr == null) {
        System.out.println("PR not found or cannot be deleted!");
        return;
    }

    System.out.println("\nPR to delete:");
    System.out.println(pr);
    System.out.print("Are you sure you want to delete this PR? (y/n): ");
    
    if (scanner.nextLine().equalsIgnoreCase("y")) {
        purchaseRequests.remove(pr);
        savePurchaseRequests();
        System.out.println("Purchase Requisition deleted successfully!");
    } else {
        System.out.println("Deletion cancelled.");
        }
    }
}
