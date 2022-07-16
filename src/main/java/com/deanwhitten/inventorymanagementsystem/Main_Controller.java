package com.deanwhitten.inventorymanagementsystem;

import com.deanwhitten.inventorymanagementsystem.Model.*;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main_Controller implements Initializable {
    //Inventory inv = new Inventory();

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


    public Button addPart;
    public Button modifyPart;
    public Button deletePart;
    public Button addProduct;
    public Button modifyProduct;
    public Button deleteProduct;
    public Button exitButton;

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();


    private String partSearchText = "";
    @FXML
    protected void onPartsSearchBarInput(KeyEvent keyEvent){
        partSearchText += keyEvent.getCharacter();
         System.out.println(partSearchText);       //swap later w/ inventory search method
    }

    @FXML
    protected void onAddPartButtonClick(ActionEvent event) throws IOException {
        loadPage("Add_Part_view", event);
    }

    @FXML
    protected void onModifyPartButtonClick(ActionEvent event) throws IOException {
        loadPage("Modify_Part_view", event);
    }

    @FXML
    protected void onDeletePartButtonClick(MouseEvent mouseEvent){

    }

    private String productSearchText = "";
    @FXML
    protected void onProductsSearchBarInput(KeyEvent keyEvent){
        productSearchText += keyEvent.getCharacter();
        System.out.println(productSearchText);         //swap later w/ inventory search method
    }

    @FXML
    protected void onAddProductButtonClick(ActionEvent event) throws IOException {
        loadPage("Add_Product_view", event);
    }

    @FXML
    protected void onModifyProductButtonClick(ActionEvent event) throws IOException {
        loadPage("Modify_Product_view", event);
    }

    @FXML
    protected void onDeleteProductButtonClick(MouseEvent mouseEvent){

    }

    @FXML
    public void onExitButtonClick(MouseEvent mouseEvent){
        Platform.exit();
    }


    public static void loadPage(String page, ActionEvent event)throws IOException{
        page =  page + ".fxml";
        Parent parent = FXMLLoader.load(Main.class.getResource(page));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        partID_Col.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
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


}