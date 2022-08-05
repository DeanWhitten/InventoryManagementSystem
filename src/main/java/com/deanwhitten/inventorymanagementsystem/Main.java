package com.deanwhitten.inventorymanagementsystem;

import com.deanwhitten.inventorymanagementsystem.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage. setResizable(false);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //InHouse Sample Parts
        Part inhousePart1 = new InHouse(1, "Custom coiled USB - C cord", 1.99, 205, 5, 50, 136789);
        Part inhousePart2 = new InHouse(2, "Custom KeyCaps", 39.99, 100, 1, 20, 875372);
        Part inhousePart3 = new InHouse(3, "Custom Keyboard", 115.99, 40, 1, 10, 993883);

        //Outsourced Sample Parts
        Part outsourcedPart1 = new Outsourced(4, " SSD HardDrive", 100.99, 57, 1, 5, "Schneider");
        Part outsourcedPart2 = new Outsourced(5, "Ram Stick - 8GB", 89.99, 150, 1, 15, "Wilson");
        Part outsourcedPart3 = new Outsourced(6, "12GB Ram GPU", 299.99, 15, 1, 2, "Marco");

        //Sample Products
        Product product1 = new Product(1, "Laptop", 600.00, 43, 1, 5);
        product1.addAssociatedPart(outsourcedPart1);
        Product product2 = new Product(2, "Gaming PC", 1499.49, 39, 1, 10);
        product2.addAssociatedPart(inhousePart3);
        Product product3 = new Product(3, "3D Printer", 300.00, 20, 1, 2);

        Inventory.addPart(inhousePart1);
        Inventory.addPart(inhousePart2);
        Inventory.addPart(inhousePart3);
        Inventory.addPart(outsourcedPart1);
        Inventory.addPart(outsourcedPart2);
        Inventory.addPart(outsourcedPart3);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        launch();
    }


}