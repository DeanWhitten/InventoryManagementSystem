package com.deanwhitten.inventorymanagementsystem;

import com.deanwhitten.inventorymanagementsystem.Model.Inventory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main_Controller {
    Inventory inv;

    @FXML
    private TextField partSearchBar;
    @FXML
    private TableView partsTable;

    @FXML
    private TextField productSearchBar;
    @FXML
    private TableView productsTable;

    public Button addPart;
    public Button modifyPart;
    public Button deletePart;
    public Button addProduct;
    public Button modifyProduct;
    public Button deleteProduct;
    public Button exitButton;


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
}