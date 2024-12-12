/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.main;

import orderprocurementsystem.models.User;
import java.time.LocalDateTime;

/**
 *
 * @author amiry
 */

public class Session {
    private static Session instance;
    private User currentUser;
    private LocalDateTime loginTime;
    private boolean isActive;
    
    private Session(){
        // private constructor singleton
        this.currentUser = null;
        this.isActive = false;
        this.loginTime = null;
    }
    
    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
        }
        return instance;
        
    }
    
    public void startSession(User user){
        this.currentUser = user;
        this.loginTime = LocalDateTime.now();
        this.isActive = true;
    }
    
    public void endSession(){
        this.currentUser = null;
        this.isActive = false;
    }
    
    public User getCurrentUser(){
        return currentUser;
    }
    
    public boolean isActive(){
        return isActive;
    }
    
}
