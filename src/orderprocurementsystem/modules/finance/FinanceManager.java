/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.modules.finance;

import com.google.gson.reflect.TypeToken;
import orderprocurementsystem.models.PurchaseOrder;
import orderprocurementsystem.models.Item;
import orderprocurementsystem.utils.FileHandler;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author amiry
 */
public class FinanceManager {

    private static final String PO_FILE = "purchase_orders.json";
    private static final String ITEMS_FILE = "items.json";
    private List<PurchaseOrder> purchaseOrders;
    private List<Item> items;
    private final Scanner scanner;
    private final String financeManagerId;

    public FinanceManager(String financeManagerId) {
        this.scanner = new Scanner(System.in);
        this.financeManagerId = financeManagerId;
        loadData();
    }

    private void loadData() {
        Type poListType = new TypeToken<ArrayList<PurchaseOrder>>(){}.getType();
        Type itemListType = new TypeToken<ArrayList<Item>>(){}.getType();
        
        purchaseOrders = FileHandler.readFromJson(PO_FILE, poListType);
        items = FileHandler.readFromJson(ITEMS_FILE, itemListType);
        
        if (purchaseOrders == null) purchaseOrders = new ArrayList<>();
        if (items == null) items = new ArrayList<>();
    }

    private void savePurchaseOrders() {
        FileHandler.writeToJson(purchaseOrders, PO_FILE);
    }

    public void showFinanceManagerMenu() {
        while (true) {
            System.out.println("\n=== Finance Manager Menu ===");
            System.out.println("1. View Pending Purchase Orders");
            System.out.println("2. Approve Purchase Order");
            System.out.println("3. Reject Purchase Order");
            System.out.println("4. Check Stock Status");
            System.out.println("5. Payment Management");
            System.out.println("6. Exit Program");
            
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1" -> viewPendingPurchaseOrders();
                case "2" -> approvePurchaseOrder();
                case "3" -> rejectPurchaseOrder();
                case "4" -> checkStockStatus();
                case "5" -> showPaymentMenu();
                case "6" -> {
                    System.out.println("Exiting Program...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewPendingPurchaseOrders() {
        System.out.println("\n=== Pending Purchase Orders ===");
        boolean found = false;
        
        for (PurchaseOrder po : purchaseOrders) {
            if (po.getStatus().equalsIgnoreCase("pending")) {
                System.out.println(po);
                System.out.println("------------------------");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No pending purchase orders found.");
        }
    }

    private void approvePurchaseOrder() {
        System.out.print("Enter PO ID to approve: ");
        String poId = scanner.nextLine();
        
        PurchaseOrder po = purchaseOrders.stream()
                .filter(p -> p.getPoId().equals(poId) && p.getStatus().equals("pending"))
                .findFirst()
                .orElse(null);
                
        if (po == null) {
            System.out.println("PO not found or not pending!");
            return;
        }

        po.setStatus("approved");
        savePurchaseOrders();
        System.out.println("Purchase Order approved successfully!");
    }

    private void rejectPurchaseOrder() {
        System.out.print("Enter PO ID to reject: ");
        String poId = scanner.nextLine();
        
        PurchaseOrder po = purchaseOrders.stream()
                .filter(p -> p.getPoId().equals(poId) && p.getStatus().equals("pending"))
                .findFirst()
                .orElse(null);
                
        if (po == null) {
            System.out.println("PO not found or not pending!");
            return;
        }

        po.setStatus("rejected");
        savePurchaseOrders();
        System.out.println("Purchase Order rejected successfully!");
    }

    private void checkStockStatus() {
        System.out.println("\n=== Stock Status ===");
        System.out.println("Code\tName\tCurrent Stock\tReorder Level");
        System.out.println("----------------------------------------");
        
        for (Item item : items) {
            System.out.printf("%s\t%s\t%d\t%d%n",
                item.getItemCode(),
                item.getItemName(),
                item.getCurrentStock(),
                item.getReorderLevel());
        }
    }

    private void showPaymentMenu() {
        while (true) {
            System.out.println("\n=== Payment Management ===");
            System.out.println("1. View Approved Purchase Orders");
            System.out.println("2. Make Payment");
            System.out.println("3. View Payment History");
            System.out.println("4. Back");
            
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1" -> viewApprovedPurchaseOrders();
                case "2" -> makePayment();
                case "3" -> viewPaymentHistory();
                case "4" -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewApprovedPurchaseOrders() {
        System.out.println("\n=== Approved Purchase Orders ===");
        boolean found = false;
        
        for (PurchaseOrder po : purchaseOrders) {
            if (po.getStatus().equalsIgnoreCase("approved")) {
                System.out.println(po);
                System.out.println("------------------------");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No approved purchase orders found.");
        }
    }

    private void makePayment() {
        System.out.print("Enter PO ID to process payment: ");
        String poId = scanner.nextLine();
        
        PurchaseOrder po = purchaseOrders.stream()
                .filter(p -> p.getPoId().equals(poId) && p.getStatus().equals("approved"))
                .findFirst()
                .orElse(null);
                
        if (po == null) {
            System.out.println("PO not found or not approved!");
            return;
        }

        po.setStatus("paid");
        savePurchaseOrders();
        System.out.println("Payment processed successfully!");
    }

    private void viewPaymentHistory() {
        System.out.println("\n=== Payment History ===");
        for (PurchaseOrder po : purchaseOrders) {
            if (po.getStatus().equalsIgnoreCase("paid")) {
                System.out.println(po);
                System.out.println("------------------------");
            }
        }
    }
}
    
