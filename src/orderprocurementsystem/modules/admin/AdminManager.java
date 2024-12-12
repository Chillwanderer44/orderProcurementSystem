/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.modules.admin;

import com.google.gson.reflect.TypeToken;
import orderprocurementsystem.models.User;
import orderprocurementsystem.utils.FileHandler;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author amiry
 */
public class AdminManager {
    private static final String USERS_FILE = "users.json";
    private List<User> users;
    private final Scanner scanner;

    public AdminManager() {
        this.scanner = new Scanner(System.in);
        loadUsers();
    }

    private void loadUsers() {
        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
        users = FileHandler.readFromJson(USERS_FILE, userListType);
    }

    private void saveUsers() {
        FileHandler.writeToJson(users, USERS_FILE);
    }

    public void showAdminMenu() {
        while (true) {
            System.out.println("\n=== Admin Management Menu ===");
            System.out.println("1. Create New User");
            System.out.println("2. List All Users");
            System.out.println("3. Modify User");
            System.out.println("4. Delete User");
            System.out.println("5. Exit");
            
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1" -> createUser();
                case "2" -> listUsers();
                case "3" -> modifyUser();
                case "4" -> deleteUser();
                case "5" -> {
                    System.out.println("Exiting Program...");
                    System.exit(0);} 
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createUser() {
        System.out.println("\n=== Create New User ===");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        // Check if user already exists
        if (users.stream().anyMatch(u -> u.getUserId().equals(userId))) {
            System.out.println("User ID already exists!");
            return;
        }

        System.out.print("Enter User Name: ");
        String userName = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        System.out.println("Select User Type:");
        System.out.println("1. Sales Manager (SM)");
        System.out.println("2. Purchase Manager (PM)");
        System.out.println("3. Inventory Manager (IM)");
        System.out.println("4. Finance Manager (FM)");
        System.out.println("5. Admin");
        
        System.out.print("Enter choice: ");
        String typeChoice = scanner.nextLine();
        
        String userType = switch (typeChoice) {
            case "1" -> "SM";
            case "2" -> "PM";
            case "3" -> "IM";
            case "4" -> "FM";
            case "5" -> "Admin";
            default -> {
                System.out.println("Invalid user type!");
                yield null;
            }
        };

        if (userType != null) {
            users.add(new User(userId, userName, password, userType));
            saveUsers();
            System.out.println("User created successfully!");
        }
    }

    private void listUsers() {
        System.out.println("\n=== User List ===");
        System.out.println("ID\tName\tType");
        System.out.println("------------------------");
        for (User user : users) {
            System.out.printf("%s\t%s\t%s%n", 
                user.getUserId(), 
                user.getUserName(), 
                user.getUserType());
        }
    }

    private void modifyUser() {
        System.out.print("Enter User ID to modify: ");
        String userId = scanner.nextLine();

        User userToModify = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);

        if (userToModify == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.println("\n=== Modify User ===");
        System.out.println("1. Modify Username");
        System.out.println("2. Reset Password");
        System.out.println("3. Change User Type");
        System.out.println("4. Cancel");
        
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> {
                System.out.print("Enter new username: ");
                String newName = scanner.nextLine();
                userToModify.setUserName(newName);
                System.out.println("Username updated successfully!");
            }
            case "2" -> {
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                userToModify.setPassword(newPassword);
                System.out.println("Password reset successfully!");
            }
            case "3" -> {
                System.out.println("Select new user type (SM/PM/IM/FM/Admin): ");
                String newType = scanner.nextLine().toUpperCase();
                if (newType.matches("SM|PM|IM|FM|ADMIN")) {
                    userToModify.setUserType(newType);
                    System.out.println("User type updated successfully!");
                } else {
                    System.out.println("Invalid user type!");
                }
            }
            case "4" -> System.out.println("Modification cancelled.");
            default -> System.out.println("Invalid choice!");
        }
        saveUsers();
    }

    private void deleteUser() {
        System.out.print("Enter User ID to delete: ");
        String userId = scanner.nextLine();

        User userToDelete = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);

        if (userToDelete == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.print("Are you sure you want to delete this user? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            users.remove(userToDelete);
            saveUsers();
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
}
