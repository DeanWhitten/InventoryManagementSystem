package com.deanwhitten.inventorymanagementsystem;

import com.deanwhitten.inventorymanagementsystem.Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private TableView partsTable;
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
    private TableView productsTable;
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
    };

    @FXML
    protected void onAddPartButtonClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("Add_Part_view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = new Stage();
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
        
    };

    @FXML
    protected void onModifyPartButtonClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("Modify_Part_view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = new Stage();
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    };

    @FXML
    protected void onDeletePartButtonClick(MouseEvent mouseEvent){

    };

    private String productSearchText = "";
    @FXML
    protected void onProductsSearchBarInput(KeyEvent keyEvent){
        productSearchText += keyEvent.getCharacter();
        System.out.println(productSearchText);         //swap later w/ inventory search method
    };

    @FXML
    protected void onAddProductButtonClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("Add_Product_view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = new Stage();
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    };

    @FXML
    protected void onModifyProductButtonClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("Modify_Product_view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = new Stage();
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    };

    @FXML
    protected void onDeleteProductButtonClick(MouseEvent mouseEvent){

    };

    @FXML
    public void onExitButtonClick(MouseEvent mouseEvent){
        Platform.exit();
    };





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Binding part table columns
        partID_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv_Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice_Col.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Binding product table columns
        productID_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInv_Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice_Col.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    

}