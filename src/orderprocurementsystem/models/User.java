/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.models;

/**
 *
 * @author amiry
 */
public class User {
    
    private String userId;
    private String userName;
    private String password;
    private String userType; //SM, FM, PM, IM, Admin
    
    // constructor
    
    public User(String userId, String userName, String password, String userType){
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.userType = userType;
    }
    
    // getter and setter
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
}
