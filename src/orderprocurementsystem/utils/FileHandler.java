/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderprocurementsystem.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amiry
 */
public class FileHandler {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public static <T> void writeToJson(List<T> data, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    public static <T> List<T> readFromJson(String fileName, Type typeToken) {
        try (Reader reader = new FileReader(fileName)) {
            return gson.fromJson(reader, typeToken);
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    
    
}
