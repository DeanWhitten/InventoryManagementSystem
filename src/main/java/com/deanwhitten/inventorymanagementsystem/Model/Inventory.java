package com.deanwhitten.inventorymanagementsystem.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Inventory.
 */
public class Inventory{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Gets all products.
     *
     * @return the all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Gets all parts.
     *
     * @return the all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Add part.
     *
     * @param newPart the new part
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * Add product.
     *
     * @param newProduct the new product
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Lookup part part.
     *
     * @param partId the part id
     * @return the part
     */
    public static Part lookupPart(int partId){
        Part partFound = null;

        for(Part part : allParts){
            if(part.getId() == partId){
                partFound = part;
            }
        }

       return partFound;
   }

    /**
     * Lookup product product.
     *
     * @param productId the product id
     * @return the product
     */
    public static Product lookupProduct(int productId){
        Product productFound = null;

        for(Product product: allProducts){
            if(product.getId() == productId){
                productFound = product;
            }
        }
        return productFound;
   }

    /**
     * Lookup part observable list.
     *
     * @param partName the part name
     * @return the observable list
     */
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

    /**
     * Lookup product observable list.
     *
     * @param productName the product name
     * @return the observable list
     */
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

    /**
     * Update part.
     *
     * @param index        the index
     * @param selectedPart the selected part
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index,selectedPart);
   }

    /**
     * Update product.
     *
     * @param index           the index
     * @param selectedProduct the selected product
     */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
   }

    /**
     * Delete part boolean.
     *
     * @param partSelected the part selected
     * @return the boolean
     */
    public static boolean deletePart(Part partSelected){
        if(allParts.contains(partSelected)){
            allParts.remove(partSelected);
            return true;
        }else{
            return false;
        }
   }

    /**
     * Delete product boolean.
     *
     * @param productSelected the product selected
     * @return the boolean
     */
    public static boolean deleteProduct(Product productSelected){
       if(allProducts.contains(productSelected) && productSelected.getAllAssociatedParts().isEmpty()){
           allProducts.remove(productSelected);
           return true;
       }else{
           return false;
       }
   }

    /**
     * Part list size int.
     *
     * @return the int
     */
    public int partListSize() {
        return allParts.size();
    }

    /**
     * Product list size int.
     *
     * @return the int
     */
    public int productListSize() {
        return allProducts.size();
    }
}