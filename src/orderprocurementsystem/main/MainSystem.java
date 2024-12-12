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
            // Show Sales Manager menu when implemented
            InventoryManager inventoryManager = new InventoryManager();
            inventoryManager.showInventoryManagerMenu();
            
        }
        case "PM" -> {
            // Show Purchase Manager menu when implemented
            System.out.println("Purchase Manager menu coming soon");
        }
        case "IM" -> {
            // Show Inventory Manager menu when implemented
            System.out.println("Inventory Manager menu coming soon");
        }
        case "FM" -> {
            // Show Finance Manager menu when implemented
            System.out.println("Finance Manager menu coming soon");
        }
        default -> System.out.println("Invalid user type!");
    }
    }
    public static void main(String[] args){
        MainSystem system = new MainSystem();
        system.start();
    }
}

