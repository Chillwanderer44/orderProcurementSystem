/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.main;

import java.util.Scanner;
import orderprocurementsystem.auth.LoginSystem;
import orderprocurementsystem.models.User;
import orderprocurementsystem.modules.inventory.InventoryManager;
import orderprocurementsystem.modules.admin.AdminManager;
import orderprocurementsystem.modules.sales.SalesManager;
import orderprocurementsystem.modules.purchase.PurchaseManager;
import orderprocurementsystem.modules.finance.FinanceManager;

/**
 *
 * @author amiry
 */
public class MainSystem {
    private final Session session;
    private final Scanner scanner;
    
    public MainSystem(){
    this.session = Session.getInstance();
    this.scanner = new Scanner(System.in);
    }
    
    public void start(){
        while(true){
            if(!session.isActive()){
                showLoginMenu();
            }else{
                showMainMenu();
            }
        }
    }

    private void showLoginMenu(){
        System.out.println("Login");
        System.out.println("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();
        
        LoginSystem loginsystem = new LoginSystem();
        if(loginsystem.authenticate(userId, password)){
            System.out.println("Login Sucessful");
            showMainMenu();
        }else{
            System.out.println("Invalid Credentials, Try Again.");
            
        }
        
        
    }
    
    private void showMainMenu() {
    String userType = session.getCurrentUser().getUserType();
    
    // Show different menus based on user type
    switch(userType) {
        case "Admin" -> {
            AdminManager adminManager = new AdminManager();
            adminManager.showAdminMenu();
        }
        case "SM" -> {
            String salesManagerId = session.getCurrentUser().getUserId();
            SalesManager salesManager = new SalesManager(salesManagerId);
            salesManager.showSalesManagerMenu();
        }
        case "PM" -> {
            String purchaseManagerId = session.getCurrentUser().getUserId();
            PurchaseManager purchaseManager = new PurchaseManager(purchaseManagerId);
            purchaseManager.showPurchaseManagerMenu();
        }
        case "IM" -> {
            InventoryManager inventoryManager = new InventoryManager();
            inventoryManager.showInventoryManagerMenu();
        }
        case "FM" -> {
            String financeManagerId = session.getCurrentUser().getUserId();
            FinanceManager financeManager = new FinanceManager(financeManagerId);
            financeManager.showFinanceManagerMenu();
        }
        default -> System.out.println("Invalid");
    }
    }
    public static void main(String[] args){
        MainSystem system = new MainSystem();
        system.start();
    }
}

