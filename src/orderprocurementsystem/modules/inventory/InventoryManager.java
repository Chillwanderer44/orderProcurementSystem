/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.modules.inventory;

import com.google.gson.reflect.TypeToken;
import orderprocurementsystem.models.Item;
import orderprocurementsystem.utils.FileHandler;
import orderprocurementsystem.models.Supplier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author amiry
 */
public class InventoryManager {
    private static final String ITEMS_FILE = "items.json";
    private List<Item> items;
    private static final String SUPPLIERS_FILE = "suppliers.json";
    private List<Supplier> suppliers;
    private final Scanner scanner;
    
    public InventoryManager(){
        this.scanner = new Scanner(System.in);
        loadItems();
        loadSuppliers();
    }
    private void loadSuppliers() {
    Type supplierListType = new TypeToken<ArrayList<Supplier>>(){}.getType();
    suppliers = FileHandler.readFromJson(SUPPLIERS_FILE, supplierListType);
    if (suppliers == null) {
        suppliers = new ArrayList<>();
    }
}

private void saveSuppliers() {
    FileHandler.writeToJson(suppliers, SUPPLIERS_FILE);
}

private void addSupplier() {
    System.out.println("\n=== Add New Supplier ===");
    
    System.out.print("Enter Supplier Code: ");
    String supplierCode = scanner.nextLine();
    
    if (suppliers.stream().anyMatch(s -> s.getSupplierCode().equals(supplierCode))) {
        System.out.println("Supplier code already exists!");
        return;
    }

    System.out.print("Enter Supplier Name: ");
    String supplierName = scanner.nextLine();
    
    System.out.print("Enter Supplier Address: ");
    String supplierAddress = scanner.nextLine();

    Supplier newSupplier = new Supplier(supplierCode, supplierName, supplierAddress);
    suppliers.add(newSupplier);
    saveSuppliers();
    System.out.println("Supplier added successfully!");
}

private void listSuppliers() {
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

private void modifySupplier() {
    System.out.print("Enter Supplier Code to modify: ");
    String supplierCode = scanner.nextLine();

    Supplier supplierToModify = suppliers.stream()
            .filter(s -> s.getSupplierCode().equals(supplierCode))
            .findFirst()
            .orElse(null);

    if (supplierToModify == null) {
        System.out.println("Supplier not found!");
        return;
    }

    System.out.println("\nCurrent Supplier Details:");
    System.out.println(supplierToModify);

    System.out.println("\nEnter new details (press Enter to keep current value):");
    
    System.out.print("Enter new Supplier Name [" + supplierToModify.getSupplierName() + "]: ");
    String newName = scanner.nextLine();
    if (!newName.isEmpty()) {
        supplierToModify.setSupplierName(newName);
    }

    System.out.print("Enter new Address [" + supplierToModify.getSupplierAddress() + "]: ");
    String newAddress = scanner.nextLine();
    if (!newAddress.isEmpty()) {
        supplierToModify.setSupplierAddress(newAddress);
    }

    saveSuppliers();
    System.out.println("Supplier updated successfully!");
}

private void deleteSupplier() {
    System.out.print("Enter Supplier Code to delete: ");
    String supplierCode = scanner.nextLine();

    Supplier supplierToDelete = suppliers.stream()
            .filter(s -> s.getSupplierCode().equals(supplierCode))
            .findFirst()
            .orElse(null);

    if (supplierToDelete == null) {
        System.out.println("Supplier not found!");
        return;
    }

    System.out.println("\nSupplier to delete:");
    System.out.println(supplierToDelete);
    System.out.print("Are you sure you want to delete this supplier? (y/n): ");
    
    if (scanner.nextLine().equalsIgnoreCase("y")) {
        suppliers.remove(supplierToDelete);
        saveSuppliers();
        System.out.println("Supplier deleted successfully!");
    } else {
        System.out.println("Deletion cancelled.");
    }
}
    
    private void loadItems() {
        Type itemListType = new TypeToken<ArrayList<Item>>(){}.getType();
        items = FileHandler.readFromJson(ITEMS_FILE, itemListType);
    }
    private void saveItems() {
        FileHandler.writeToJson(items, ITEMS_FILE);
    }
    public void showInventoryManagerMenu(){
        while (true) {
        System.out.println("\n=== Inventory Management Menu ===");
        System.out.println("1. Item Entry");
        System.out.println("2. Supplier Entry");
        System.out.println("3. Stock Management");
        System.out.println("4. Exit Program");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> showItemMenu();
            case "2" -> showSupplierMenu();
            case "3" -> showStockMenu();
            case "4" -> {
                System.out.println("Exiting Program...");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
    }
    public void showItemMenu(){
        while(true){
            System.out.println("\n=== Item Entry Menu ===");
            System.out.println("1. Add Iem");
            System.out.println("2. List All Items");
            System.out.println("3. Modify Item ");
            System.out.println("4. Delete Item");
            System.out.println("5. Back");
        
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
        
        switch (choice){
            case "1" -> addItem();
            case "2" -> listItems();
            case "3" -> modifyItem();
            case "4" -> deleteItem();
            case "5" -> { return; }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
    }
    public void showSupplierMenu(){
        while (true) {
        System.out.println("\n=== Supplier Management ===");
        System.out.println("1. Add New Supplier");
        System.out.println("2. List All Suppliers");
        System.out.println("3. Modify Supplier");
        System.out.println("4. Delete Supplier");
        System.out.println("5. Back");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> addSupplier();
            case "2" -> listSuppliers();
            case "3" -> modifySupplier();
            case "4" -> deleteSupplier();
            case "5" -> { return; }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
    }
    public void showStockMenu(){
        while (true) {
        System.out.println("\n=== Stock Management ===");
        System.out.println("1. Update Stock Level");
        System.out.println("2. Check Low Stock Items");
        System.out.println("3. Back");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1" -> updateStockLevel();
            case "2" -> checkLowStockItems();
            case "3" -> { return; }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
    }

    
    private void addItem() {
        System.out.println("\n=== Add New Item ===");
        
        System.out.print("Enter Item Code: ");
        String itemCode = scanner.nextLine();
        
        // Check if item code already exists
        if (items.stream().anyMatch(i -> i.getItemCode().equals(itemCode))) {
            System.out.println("Item code already exists!");
            return;
        }

        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        
        System.out.print("Enter Supplier Code: ");
        String supplierCode = scanner.nextLine();
        
        System.out.print("Enter Current Stock: ");
        int currentStock = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter Reorder Level: ");
        int reorderLevel = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter Unit Price: ");
        double unitPrice = Double.parseDouble(scanner.nextLine());

        Item newItem = new Item(itemCode, itemName, supplierCode, currentStock, reorderLevel, unitPrice);
        items.add(newItem);
        saveItems();
        System.out.println("Item added successfully!");
    }

    private void listItems() {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            return;
        }

        System.out.println("\n=== Item List ===");
        System.out.println("Code\tName\tSupplier\tStock\tReorder Level\tPrice");
        System.out.println("------------------------------------------------------------");
        for (Item item : items) {
            System.out.printf("%s\t%s\t%s\t\t%d\t%d\t\t%.2f%n", 
                item.getItemCode(), 
                item.getItemName(), 
                item.getSupplierCode(),
                item.getCurrentStock(),
                item.getReorderLevel(),
                item.getUnitPrice());
        }
    }

    private void modifyItem() {
        System.out.print("Enter Item Code to modify: ");
        String itemCode = scanner.nextLine();

        Item itemToModify = items.stream()
                .filter(i -> i.getItemCode().equals(itemCode))
                .findFirst()
                .orElse(null);

        if (itemToModify == null) {
            System.out.println("Item not found!");
            return;
        }

        System.out.println("\nCurrent Item Details:");
        System.out.println(itemToModify);

        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("Enter new Item Name [" + itemToModify.getItemName() + "]: ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            itemToModify.setItemName(newName);
        }

        System.out.print("Enter new Current Stock [" + itemToModify.getCurrentStock() + "]: ");
        String newStock = scanner.nextLine();
        if (!newStock.isEmpty()) {
            itemToModify.setCurrentStock(Integer.parseInt(newStock));
        }

        System.out.print("Enter new Reorder Level [" + itemToModify.getReorderLevel() + "]: ");
        String newReorderLevel = scanner.nextLine();
        if (!newReorderLevel.isEmpty()) {
            itemToModify.setReorderLevel(Integer.parseInt(newReorderLevel));
        }

        System.out.print("Enter new Unit Price [" + itemToModify.getUnitPrice() + "]: ");
        String newPrice = scanner.nextLine();
        if (!newPrice.isEmpty()) {
            itemToModify.setUnitPrice(Double.parseDouble(newPrice));
        }

        saveItems();
        System.out.println("Item updated successfully!");
    }

    private void deleteItem() {
        System.out.print("Enter Item Code to delete: ");
        String itemCode = scanner.nextLine();

        Item itemToDelete = items.stream()
                .filter(i -> i.getItemCode().equals(itemCode))
                .findFirst()
                .orElse(null);

        if (itemToDelete == null) {
            System.out.println("Item not found!");
            return;
        }

        System.out.println("\nItem to delete:");
        System.out.println(itemToDelete);
        System.out.print("Are you sure you want to delete this item? (y/n): ");
        
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            items.remove(itemToDelete);
            saveItems();
            System.out.println("Item deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void updateStockLevel() {
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

        System.out.println("Current stock level: " + item.getCurrentStock());
        System.out.print("Enter quantity to add (use negative for removal): ");
        int quantity = Integer.parseInt(scanner.nextLine());

        item.setCurrentStock(item.getCurrentStock() + quantity);
        saveItems();
        System.out.println("Stock level updated successfully!");
    }

    private void checkLowStockItems() {
        System.out.println("\n=== Low Stock Items ===");
        boolean foundLowStock = false;
        
        for (Item item : items) {
            if (item.getCurrentStock() <= item.getReorderLevel()) {
                System.out.println(item);
                System.out.println("------------------------");
                foundLowStock = true;
            }
        }
        
        if (!foundLowStock) {
            System.out.println("No items are low in stock.");
        }
    }
}
