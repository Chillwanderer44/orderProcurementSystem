/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.auth;

import com.google.gson.reflect.TypeToken;
import orderprocurementsystem.main.Session;
import orderprocurementsystem.models.User;
import orderprocurementsystem.utils.FileHandler;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author amiry
 */
public class LoginSystem {
    private static final String USERS_FILE = "users.json";
    private final Session session;
    private List<User> users;
    
    public LoginSystem(){
        this.session = Session.getInstance();
        loadUsers();
    }
    
    private void loadUsers() {
        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
        users = FileHandler.readFromJson(USERS_FILE, userListType);
        
        // If no users exist, create default admin
        if (users.isEmpty()) {
            users.add(new User("admin", "Administrator", "admin123", "Admin"));
            saveUsers();
        }
    }
    
    private void saveUsers() {
        FileHandler.writeToJson(users, USERS_FILE);
    }
    
    public boolean authenticate(String userId, String password) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                session.startSession(user);
                return true;
            }
        }
        return false;
    }
    
    public boolean registerUser(String userId, String userName, String password, String userType) {
        // Check if user already exists
        if (users.stream().anyMatch(u -> u.getUserId().equals(userId))) {
            return false;
        }
        
        // Validate user type
        if (!isValidUserType(userType)) {
            return false;
        }
        
        // Create and save new user
        User newUser = new User(userId, userName, password, userType);
        users.add(newUser);
        saveUsers();
        return true;
    }
    
    private boolean isValidUserType(String userType) {
        return userType.equals("SM") || 
               userType.equals("PM") || 
               userType.equals("IM") || 
               userType.equals("FM") || 
               userType.equals("Admin");
    }
    
    public void logout() {
        session.endSession();
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users); // Return a copy to prevent direct modification
    }
}
