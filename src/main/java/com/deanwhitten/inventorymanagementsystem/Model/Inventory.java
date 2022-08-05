package com.deanwhitten.inventorymanagementsystem.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId){
        Part partFound = null;

        for(Part part : allParts){
            if(part.getId() == partId){
                partFound = part;
            }
        }

       return partFound;
   }

    public static Product lookupProduct(int productId){
        Product productFound = null;

        for(Product product: allProducts){
            if(product.getId() == productId){
                productFound = product;
            }
        }
        return productFound;
   }

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
         partsFound.clear();
        for(Part part: allParts){
            if(part.getName().toLowerCase().contains(partName.toLowerCase())){
                partsFound.add(part);
            }
        }
        return partsFound;
   }

    public static ObservableList<Product> lookupProduct(String productName){
       ObservableList<Product> productsFound = FXCollections.observableArrayList();
       productsFound.clear();
       for(Product product: allProducts){
           if(product.getName().toLowerCase().contains(productName.toLowerCase())){
               productsFound.add(product);
           }
       }
       return productsFound;
   }

    public static void updatePart(int index, Part selectedPart){
        allParts.set(index,selectedPart);
   }

    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
   }

    public static boolean deletePart(Part partSelected){
        if(allParts.contains(partSelected)){
            allParts.remove(partSelected);
            return true;
        }else{
            return false;
        }
   }

    public static boolean deleteProduct(Product productSelected){
       if(allProducts.contains(productSelected) && productSelected.getAllAssociatedParts().isEmpty()){
           allProducts.remove(productSelected);
           return true;
       }else{
           return false;
       }
   }

    public int partListSize() {
        return allParts.size();
    }

    public int productListSize() {
        return allProducts.size();
    }
}