package com.deanwhitten.inventorymanagementsystem;

import com.deanwhitten.inventorymanagementsystem.Model.Inventory;
import com.deanwhitten.inventorymanagementsystem.Model.Part;
import com.deanwhitten.inventorymanagementsystem.Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Main screen controller. This is the initial screen launched when starting the program. This screen has two
 * tables, one for parts and one for products. The tables can both be searched for parts and products in addition
 * to allowing adding, modifying, and deleting of parts and products.
 *
 * @author Dean F Whitten
 */
public class Main_Controller implements Initializable {
    @FXML
    private TextField partSearchBar;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partID_Col;
    @FXML
    private TableColumn<Part, String> partName_Col;
    @FXML
    private TableColumn<Part, Integer> partInv_Col;
    @FXML
    private TableColumn<Part, Double> partPrice_Col;

    @FXML
    private TextField productSearchBar;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productID_Col;
    @FXML
    private TableColumn<Product, String> productName_Col;
    @FXML
    private TableColumn<Product, Integer> productInv_Col;
    @FXML
    private TableColumn<Product, Double> productPrice_Col;

    /** Observable Array List to store Parts search results */
    private ObservableList<Part> foundParts = FXCollections.observableArrayList();
    /** Storage object for a selected Part */
    public static Part selectedPart;
    /** Add Part button */
    public Button addPart;
    /** Modify Part button */
    public Button modifyPart;
    /** Delete Part button */
    public Button deletePart;
    /** Error label for Parts table*/
    public  Label partsErrorLabel;
    /** Parts Delete conformation yes button*/
    public Button parts_yes_btn;
    /** Parts Delete conformation no button*/
    public Button parts_no_btn;

    /** Observable Array List to store Products search results */
    private ObservableList<Product> foundProducts = FXCollections.observableArrayList();
    /** Storage object for a selected Product */
    public static Product selectedProduct;
    /** Add Product button */
    public Button addProduct;
    /** Modify Product button */
    public Button modifyProduct;
    /** Delete Product button */
    public Button deleteProduct;
    /** Error label for Products table*/
    public Label productsErrorLabel;
    /** Product Delete conformation yes button*/
    public Button products_yes_btn;
    /** Product Delete conformation no button*/
    public Button products_no_btn;

    /** Program Exit Button*/
    public Button exitButton;

    /**
     * Initializes the Main Screen controller.
     * Populates the parts and product tables with inventory of parts and products.
     * @param location
     * @param resources
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partID_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv_Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice_Col.setCellValueFactory(new PropertyValueFactory<>("price"));

        productID_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInv_Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice_Col.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());
    }

    /**
     *Searches for parts by Part ID or by Part Name.
     *a UI error is called and displayed if no results are found.
     *
     * @param keyEvent typing text in the Part search field.
     * */
    @FXML
    private void onPartsSearchBarInput(KeyEvent keyEvent){
        uiRESET();
        foundParts.clear();
        try {
            try{
                int id = Integer.parseInt(partSearchBar.getText());
                foundParts.add(Inventory.lookupPart(id));
            }
            catch(Exception e){
                String name = partSearchBar.getText();
                foundParts = Inventory.lookupPart(name);
            }
            if(foundParts.size() == 0){
                uiErrorMsgHandler(HandledObject.PARTS, ErrorType.MISSING);
            }
            else {
                partsTable.setItems(foundParts);
                
            }
        }
        catch(Exception e){
            uiErrorMsgHandler(HandledObject.PARTS, ErrorType.MISSING);;
        }
    }

    /**
     * Opens window to add Part.
     *
     *  @param event add button is clicked for Parts table
     */
    @FXML
    private void onAddPartButtonClick(ActionEvent event) throws IOException {
        loadWindow("Add_Part_view", event);
    }

    /**
     * Opens modify part for a selected part.
     * The UI is cleared. The selected part is assigned to selectedPart.
     * If a part is not selected an error will be displayed in the UI, otherwise the part modification window
     * opens.
     *
     *  @param event modify button is clicked for parts table
     */
    @FXML
    private void onModifyPartButtonClick(ActionEvent event) throws IOException {
        uiRESET();
        selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null){
            loadWindow("Modify_Part_view", event);
        }else{
            uiErrorMsgHandler(HandledObject.PARTS, ErrorType.MODIFY_NOT_SELECTED);;
        }
    }

    /**
     * Deletes a selected part.
     * The UI is cleared. The selected part is assigned to selectedPart.
     * If a part is not selected an error will be displayed in the UI, otherwise the deletion conformation UI
     * elements will be shown and enabled.
     *
     *  @param event delete button is clicked for parts table
     */
    @FXML
    private void onDeletePartButtonClick(ActionEvent event){
        uiRESET();
        selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null){
            getConformationUI(HandledObject.PARTS);
        }else{
            uiErrorMsgHandler(HandledObject.PARTS, ErrorType.DELETE_NOT_SELECTED);;
        }
    }
    /**
     * Yes is selected in part deletion confirmation. Resets UI, part IS deleted from the inventory, table is
     * updated, and then checks to see if the deletion occurred. IF delete fails, an error is displayed in the UI.
     *
     *  @param event yes button is clicked for deletion conformation for a selected part
     */
    @FXML
    private void partsYES_clicked(ActionEvent event){
        uiRESET();
        Boolean deleted = Inventory.deletePart(selectedPart);
        partsTable.setItems(Inventory.getAllParts());

        if(!deleted){
            uiErrorMsgHandler(HandledObject.PARTS, ErrorType.DELETE_FAILED);;
        }
    }

    /**
     * No is selected in part deletion confirmation. Resets UI and part is NOT deleted from the inventory.
     *
     *  @param event no button is clicked for deletion conformation for a selected part
     */
    @FXML
    private void partsNO_clicked(ActionEvent event){
        uiRESET();
    }


    /**
     *Searches for products by product ID or by product Name.
     *a UI error is called and displayed if no results are found.
     *
     * @param keyEvent typing text in the product search field.
     * */
    @FXML
    private void onProductsSearchBarInput(KeyEvent keyEvent){
        uiRESET();
        foundProducts.clear();
        try {
            try{
                int id = Integer.parseInt(productSearchBar.getText());
                foundProducts.add(Inventory.lookupProduct(id));
            }
            catch(Exception e){
                String name = productSearchBar.getText();
                foundProducts = Inventory.lookupProduct(name);    
            }
            if(foundProducts.size() == 0){
                uiErrorMsgHandler(HandledObject.PRODUCTS, ErrorType.MISSING);
            }
            else {
                productsTable.setItems(foundProducts);
            }
        }
        catch(Exception e){
            uiErrorMsgHandler(HandledObject.PRODUCTS, ErrorType.MISSING);;
        }
    }

    /**
     * Opens  window to add product.
     *
     *  @param event add button is clicked for products table
     */
    @FXML
    private void onAddProductButtonClick(ActionEvent event) throws IOException {
        loadWindow("Add_Product_view", event);
    }

    /**
     * Opens modify product for a selected product.
     * The UI is cleared. The selected product is assigned to selectedProduct.
     * If a product is not selected an error will be displayed in the UI, otherwise the product modification window
     * opens.
     *
     *  @param event modify button is clicked for products table
     */
    @FXML
    private void onModifyProductButtonClick(ActionEvent event) throws IOException {
        uiRESET();
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if(selectedProduct != null){
            loadWindow("Modify_Product_view", event);
        }else{
            uiErrorMsgHandler(HandledObject.PRODUCTS, ErrorType.MODIFY_NOT_SELECTED);;
        }
    }

    /**
     * Deletes a selected product.
     * The UI is cleared. The selected product is assigned to selectedProduct.
     * If a product is not selected an error will be displayed in the UI, otherwise the deletion conformation UI
     * elements will be shown and enabled.
     *
     *  @param event delete button is clicked for products table
     */
    @FXML
    private void onDeleteProductButtonClick(ActionEvent event){
        uiRESET();
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if(selectedProduct != null){
             getConformationUI(HandledObject.PRODUCTS);
        }else{
            uiErrorMsgHandler(HandledObject.PRODUCTS, ErrorType.DELETE_NOT_SELECTED);;
        }
    }

    /**
     * Yes is selected in Product deletion confirmation. Resets UI, product IS deleted from the inventory, table is
     * updated, and then checks to see if the deletion occurred. IF delete fails, an error is displayed in the UI.
     * Deletion will fail if the product has associated parts.
     *
     *  @param event product yes button is clicked
     */
    @FXML
    private void productsYES_clicked(ActionEvent event){
        uiRESET();
        Boolean deleted = Inventory.deleteProduct(selectedProduct);

        productsTable.setItems(Inventory.getAllProducts());
        if(!deleted){
            uiErrorMsgHandler(HandledObject.PRODUCTS, ErrorType.DELETE_FAILED);;
        }
    }

    /**
     * No is selected in Product deletion confirmation. Resets UI and product is NOT deleted from the inventory.
     *
     *  @param event  no button is clicked for deletion conformation
     */
    @FXML
    private void productsNO_clicked(ActionEvent event){
        uiRESET();
    }

    /**
     * Exits the program.
     *
     *  @param mouseEvent exist button is clicked
     */
    @FXML
    private void onExitButtonClick(MouseEvent mouseEvent){
        Platform.exit();
    }

    /**
     * The enum Error type. Used to label the kind on error
     */
    enum ErrorType{
        /**
         * an object was not selected to be deleted
         */
        DELETE_NOT_SELECTED,
        /**
         * an object was not selected to be modified
         */
        MODIFY_NOT_SELECTED,
        /**
         * an object being searched is missing
         */
        MISSING,
        /**
         * an object failed to be deleted
         */
        DELETE_FAILED
    }

    /**
     * The enum Handled object.  Used to label the object type with an error
     */
    enum HandledObject {
        /**
         * Parts Table objects
         */
        PARTS,
        /**
         * Products Table objects
         */
        PRODUCTS
    }

    /**
     * UI error msg handler that shows the appropriate error message based on the ErrorType and Handled Object.
     *
     * @param HandledObject the handled object
     * @param errorType     the error type
     */
    public void uiErrorMsgHandler(HandledObject HandledObject, ErrorType errorType){
        String modifyNotSelected = "Error: You must select a $object to modify.";
        String deleteNotSelected = "Error: You must select a $object to delete.";
        String missing = "Error: $object missing.";
        String deleteFailure = "Error: $object not deleted.";

        switch (HandledObject) {
            case PARTS -> {
                partsErrorLabel.setOpacity(1);
                if (errorType == ErrorType.DELETE_NOT_SELECTED) {
                    partsErrorLabel.setText(deleteNotSelected.replace("$object", "part"));
                } else if (errorType == ErrorType.MODIFY_NOT_SELECTED) {
                    partsErrorLabel.setText(modifyNotSelected.replace("$object", "part"));
                } else if (errorType == ErrorType.MISSING) {
                    partsErrorLabel.setText(missing.replace("$object", "part"));
                } else if (errorType == ErrorType.DELETE_FAILED) {
                    partsErrorLabel.setText(deleteFailure.replace("$object", "part"));
                }
            }
            case PRODUCTS -> {
                productsErrorLabel.setOpacity(1);
                if (errorType == ErrorType.DELETE_NOT_SELECTED) {
                    productsErrorLabel.setText(deleteNotSelected.replace("$object", "product"));
                } else if (errorType == ErrorType.MODIFY_NOT_SELECTED) {
                    productsErrorLabel.setText(modifyNotSelected.replace("$object", "product"));
                } else if (errorType == ErrorType.MISSING) {
                    productsErrorLabel.setText(missing.replace("$object", "product"));
                } else if (errorType == ErrorType.DELETE_FAILED) {
                    if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
                        productsErrorLabel.setText(deleteFailure.replace("$object", "Product")
                                + " This product has associated parts!");
                    } else {
                        productsErrorLabel.setText(deleteFailure.replace("$object", "Product"));
                    }
                }
            }
        }
    }

    /**
     * Gets the appropriate UI elements for the confirmation of the deletion for a product or part.
     *
     * @param HandledObject the handled object being confirmed for deletion
     */
    public void getConformationUI(HandledObject HandledObject){
        switch(HandledObject){
            case PARTS -> {
                partsErrorLabel.setOpacity(1);
                partsErrorLabel.setText("Are You Sure You would Like to Delete Selected Part?");

                parts_yes_btn.setOpacity(1);
                parts_yes_btn.setDisable(false);

                parts_no_btn.setOpacity(1);
                parts_no_btn.setDisable(false);
            }
            case PRODUCTS -> {
                productsErrorLabel.setOpacity(1);
                productsErrorLabel.setText("Are You Sure You would Like to Delete Selected Part?");

                products_yes_btn.setOpacity(1);
                products_yes_btn.setDisable(false);

                products_no_btn.setOpacity(1);
                products_no_btn.setDisable(false);
            }
        }
    }

    /**
     * Resets the UI for all error labels and conformations buttons back to disabled and invisible
     */
    public void uiRESET(){
        partsErrorLabel.setOpacity(0);
        productsErrorLabel.setOpacity(0);

        parts_yes_btn.setOpacity(0);
        parts_no_btn.setOpacity(0);
        products_yes_btn.setOpacity(0);
        products_no_btn.setOpacity(0);

        parts_yes_btn.setDisable(true);
        parts_no_btn.setDisable(true);
        products_yes_btn.setDisable(true);
        products_no_btn.setDisable(true);
    }

    /**
     * Loads Window of another view  based on the "page" specified.
     *
     * @param page  the page or view to be loaded
     * @param event the event
     * @throws IOException the io exception
     */
    public static void loadWindow(String page, ActionEvent event)throws IOException{
        page =  page + ".fxml";
        Parent parent = FXMLLoader.load(Main.class.getResource(page));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}