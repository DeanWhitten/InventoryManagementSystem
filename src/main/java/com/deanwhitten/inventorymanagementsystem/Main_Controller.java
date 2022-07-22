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

    private ObservableList<Part> foundParts = FXCollections.observableArrayList();
    public static Part selectedPart;
    public Button addPart;
    public Button modifyPart;
    public Button deletePart;
    public  Label partsErrorLabel;

    private ObservableList<Product> foundProducts = FXCollections.observableArrayList();
    public static Product selectedProduct;
    public Button addProduct;
    public Button modifyProduct;
    public Button deleteProduct;
    public Label productsErrorLabel;

    public Button exitButton;

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

    public static void loadPage(String page, ActionEvent event)throws IOException{
        page =  page + ".fxml";
        Parent parent = FXMLLoader.load(Main.class.getResource(page));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onPartsSearchBarInput(KeyEvent keyEvent){
        uiErrorMsgRESET();
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
                partID_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
                partName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
                partInv_Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
                partPrice_Col.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }
        catch(Exception e){
            uiErrorMsgHandler(HandledObject.PARTS, ErrorType.MISSING);;
        }
    }

    @FXML
    private void onAddPartButtonClick(ActionEvent event) throws IOException {
        loadPage("Add_Part_view", event);
    }

    @FXML
    private void onModifyPartButtonClick(ActionEvent event) throws IOException {
        uiErrorMsgRESET();
        selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null){
            loadPage("Modify_Part_view", event);
        }else{
            uiErrorMsgHandler(HandledObject.PARTS, ErrorType.MODIFY_NOT_SELECTED);;
        }
    }

    @FXML
    private void onDeletePartButtonClick(ActionEvent event){
        uiErrorMsgRESET();
        selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null){
            Boolean deleted = Inventory.deletePart(selectedPart);

            partsTable.setItems(Inventory.getAllParts());
            partID_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
            partName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInv_Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPrice_Col.setCellValueFactory(new PropertyValueFactory<>("price"));
            if(!deleted){
                uiErrorMsgHandler(HandledObject.PARTS, ErrorType.DELETE_FAILED);;
            }
        }else{
            uiErrorMsgHandler(HandledObject.PARTS, ErrorType.DELETE_NOT_SELECTED);;
        }
    }

    @FXML
    private void onProductsSearchBarInput(KeyEvent keyEvent){
        uiErrorMsgRESET();
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
                productID_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
                productName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
                productInv_Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
                productPrice_Col.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }
        catch(Exception e){
            uiErrorMsgHandler(HandledObject.PRODUCTS, ErrorType.MISSING);;
        }
    }

    @FXML
    private void onAddProductButtonClick(ActionEvent event) throws IOException {
        loadPage("Add_Product_view", event);
    }

    @FXML
    private void onModifyProductButtonClick(ActionEvent event) throws IOException {
        uiErrorMsgRESET();
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if(selectedProduct != null){
            loadPage("Modify_Product_view", event);
        }else{
            uiErrorMsgHandler(HandledObject.PRODUCTS, ErrorType.MODIFY_NOT_SELECTED);;
        }
    }

    @FXML
    private void onDeleteProductButtonClick(ActionEvent event){
        uiErrorMsgRESET();
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if(selectedProduct != null){
            Boolean deleted = Inventory.deleteProduct(selectedProduct);

            productsTable.setItems(Inventory.getAllProducts());
            productID_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
            productName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
            productInv_Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPrice_Col.setCellValueFactory(new PropertyValueFactory<>("price"));
            if(!deleted){
                uiErrorMsgHandler(HandledObject.PRODUCTS, ErrorType.DELETE_FAILED);;
            }
        }else{
            uiErrorMsgHandler(HandledObject.PRODUCTS, ErrorType.DELETE_NOT_SELECTED);;
        }
    }

    @FXML
    private void onExitButtonClick(MouseEvent mouseEvent){
        Platform.exit();
    }

    enum ErrorType{
        DELETE_NOT_SELECTED, MODIFY_NOT_SELECTED, MISSING, DELETE_FAILED
    }

    enum HandledObject {
        PARTS, PRODUCTS
    }

    public void uiErrorMsgHandler(HandledObject HandledObject, ErrorType errorType){
        String modifyNotSelected = "Error: You must select a $object to modify.";
        String deleteNotSelected = "Error: You must select a $object to delete.";
        String missing = "Error: $object missing.";
        String deleteFailure = "Error: $object not deleted.";

        switch (HandledObject){
            case PARTS:
                partsErrorLabel.setOpacity(1);
                if (errorType == ErrorType.DELETE_NOT_SELECTED){
                    partsErrorLabel.setText(deleteNotSelected.replace("$object", "part"));
                } else if (errorType == ErrorType.MODIFY_NOT_SELECTED){
                    partsErrorLabel.setText(modifyNotSelected.replace("$object", "part"));
                } else if (errorType == ErrorType.MISSING) {
                    partsErrorLabel.setText(missing.replace("$object", "part"));
                } else if (errorType == ErrorType.DELETE_FAILED) {
                    partsErrorLabel.setText(deleteFailure.replace("$object", "part"));
                }
                break;
            case PRODUCTS:
                productsErrorLabel.setOpacity(1);
                if (errorType == ErrorType.DELETE_NOT_SELECTED){
                    productsErrorLabel.setText(deleteNotSelected.replace("$object", "product"));
                } else if (errorType == ErrorType.MODIFY_NOT_SELECTED){
                    productsErrorLabel.setText(modifyNotSelected.replace("$object", "product"));
                } else if (errorType == ErrorType.MISSING) {
                    productsErrorLabel.setText(missing.replace("$object", "product"));
                } else if (errorType == ErrorType.DELETE_FAILED) {
                    if(!selectedProduct.getAllAssociatedParts().isEmpty()){
                        productsErrorLabel.setText(deleteFailure.replace("$object", "Product")
                                + " This product has associated parts!");
                    }else {
                        productsErrorLabel.setText(deleteFailure.replace("$object", "Product"));
                    }
                }
                break;
        }
    }

    public void uiErrorMsgRESET(){
        partsErrorLabel.setOpacity(0);
        productsErrorLabel.setOpacity(0);
    }

}