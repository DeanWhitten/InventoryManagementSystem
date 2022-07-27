package com.deanwhitten.inventorymanagementsystem;

import com.deanwhitten.inventorymanagementsystem.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * The type Add product controller.
 *  @author Dean F Whitten
 */
public class AddProduct_Controller implements Initializable {
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
    private TableView<Part> associatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> associatedPartID_Col;
    @FXML
    private TableColumn<Part, String> associatedPartName_Col;
    @FXML
    private TableColumn<Part, Integer> associatedPartInv_Col;
    @FXML
    private TableColumn<Part, Double> associatedPartPrice_Col;

    /**
     * The Id input.
     */
    public TextField id_input;
    /**
     * The Name input.
     */
    public TextField name_input;
    /**
     * The Inv input.
     */
    public TextField inv_input;
    /**
     * The Price cost input.
     */
    public TextField priceCost_input;
    /**
     * The Max input.
     */
    public TextField max_input;
    /**
     * The Min input.
     */
    public TextField min_input;

    /**
     * The Add associated part button.
     */
    public Button addAssociatedPart_btn;
    /**
     * The Remove associated part button.
     */
    public Button removeAssociatedPart_btn;
    /**
     * The Save button.
     */
    public Button saveBtn;
    /**
     * The Cancel button.
     */
    public Button cancelBtn;

    /**
     * The Add product error label.
     */
    public Label addProduct_ErrorLabel;
    /**
     * The Yes button.
     */
    public Button yes_btn;
    /**
     * The No button.
     */
    public Button no_btn;

    private int generatedIdNum;
    /**
     * The constant selectedPart.
     */
    public static Part selectedPart;
    private ObservableList<Part> foundParts = FXCollections.observableArrayList();
    /**
     * The Temp associated.
     */
    ObservableList<Part> tempAssociated = FXCollections.observableArrayList();


    /**
     * Initializes the add product controller.
     * Generates product ID number and populates its field, Populates the available parts, and the associated parts
     * tables.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        partID_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv_Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice_Col.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(tempAssociated);
        associatedPartID_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInv_Col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice_Col.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        generatedIdNum = generateProductID();
        id_input.setText("Auto-Gen: " + generatedIdNum);
    }

    /**
     *Searches for parts by Part ID or by Part Name.
     *a UI error is called and displayed if no results are found.
     *
     * @param keyEvent typing text in the Part search field.
     * */
    @FXML
    protected void onSearchInput( KeyEvent keyEvent) throws IOException {
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
                uiErrorMsgHandler(ErrorType.MISSING_PART);
            }
            else {
                partsTable.setItems(foundParts);
            }
        }
        catch(Exception e){
            uiErrorMsgHandler(ErrorType.MISSING_PART);
        }
    }

    /**
     * On add clicked.
     * Resets error and conformation UI, assigns the selected part to a variable, and checks if a part is selected.
     * If a part is selected then it is added to an Observable array list that stores associated parts and the
     * associated parts table is updated and the selected part is set back to null. If a part was not selected and UI
     * error message is shown.
     *
     * @param event add button clicked
     * @throws IOException the io exception
     */
    @FXML
    protected void onAddClicked(ActionEvent event) throws IOException {
        uiRESET();
        selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            tempAssociated.add(selectedPart);
            associatedPartsTable.setItems(tempAssociated);
            selectedPart = null;
        }else{
            uiErrorMsgHandler(ErrorType.ADD_NO_SELECTION);
        }
    }

    /**
     * On remove clicked.
     * Resets error and conformation UI, assigns the selected part to a variable, and checks if a part is selected.
     * If a part is selected then the deletion confirmation UI method is called. If a part was not selected and UI
     * error message is shown.
     *
     * @param event on remove associated button clicked
     * @throws IOException the io exception
     */
    @FXML
    protected void onRemoveClicked(ActionEvent event) throws IOException {
        uiRESET();
        selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        if(selectedPart != null){
            getConformationUI();
        }else{
            uiErrorMsgHandler(ErrorType.REMOVE_NO_SELECTION);
        }

    }

    /**
     * Yes is clicked in the associated parts deletion confirmation. Resets UI, part IS deleted from the association
     * table and selected part is set back to null.
     * @param event the yes button clicked
     * @throws IOException the io exception
     */
    @FXML
    protected void yesClicked(ActionEvent event) throws IOException {
        uiRESET();
        tempAssociated.remove(selectedPart);
        selectedPart = null;
    }

    /**
     * No is clicked in the associated parts deletion confirmation. The UI is reset.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    protected void noClicked(ActionEvent event) throws IOException {
        uiRESET();
    }


    /**
     * Save button clicked.
     * Checks for any blanks in the input, checks if min is less than max, and checks if Inv is equal or less than
     * max or equal to or greater than min. If any of those conditions are not met then the UI will display an error
     * message related to the condition not met and will not save new product till conditions are met. Otherwise, the
     * product will be saved to the inventory and the program will return to the main window.
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    protected void onSaveClicked(ActionEvent event) throws IOException {
        try{
            uiRESET();
            if( !(name_input.getText().isEmpty() || name_input.getText().isBlank() )
                    || !( inv_input.getText().isEmpty() || inv_input.getText().isBlank() )
                    || !( priceCost_input.getText().isEmpty() || priceCost_input.getText().isBlank() )
                    || !( max_input.getText().isEmpty() || max_input.getText().isBlank() )
                    || !( max_input.getText().isEmpty() || min_input.getText().isBlank() )
            ){
                int id = generatedIdNum;
                String name = name_input.getText();
                int stock = Integer.parseInt(inv_input.getText());
                double price = Double.parseDouble(priceCost_input.getText());
                int min = Integer.parseInt(min_input.getText());
                int max = Integer.parseInt(max_input.getText());

                if ( (min >= 0 &&  min <= max) && (stock >= min && stock <= max) ) {
                    try{
                        Product product = new Product(id, name, price, stock, min, max);
                        for(Part part : tempAssociated) {
                            product.addAssociatedPart(part);
                        }
                        Inventory.addProduct(product);
                        returnToMainPage(event);
                    } catch (Exception e) {
                        uiErrorMsgHandler(ErrorType.UNK);
                    }
                }  else{
                    if(!(stock >= min && stock <= max)){
                        uiErrorMsgHandler(ErrorType.STOCK_RANGE);
                    }
                    if(!(min >= 0 &&  min <= max)){
                        uiErrorMsgHandler(ErrorType.MIN_MAX);
                    }
                }

            } else{
                uiErrorMsgHandler(ErrorType.MISSING_INPUT);
            }

        } catch (Exception e){
            uiErrorMsgHandler(ErrorType.UNK);
        }
    }


    /**
     * Cancel button clicked. Returns program back to the main window.
     *
     * @param event cancel button clicked
     * @throws IOException the io exception
     */
    @FXML
    protected void cancelButtonClicked(ActionEvent event) throws IOException {
        returnToMainPage(event);
    }

    /**
     * returns program back to main window
     * @param event
     * @throws IOException
     */
    private void returnToMainPage(ActionEvent event) throws IOException {
        Main_Controller.loadWindow("Main_view", event);
    }

    /**
     * Generates Unique Product ID number  and checks if the generated already is used.
     * @return generated product ID
     */
    private int generateProductID() {
        boolean match;
        Random randomNum = new Random();
        int num = randomNum.nextInt(10003);

        match = verifyIfTaken(num);

        if (!match) {
            id_input.setText(Integer.toString(num));
        } else {
            generateProductID();
        }
        return num;
    }

    /**
     * Checks to make sure that generated ID Number is not already assigned to another product.
     * @param num number generated
     * @return true or false based on if the number is already assigned
     */
    private boolean verifyIfTaken(Integer num) {
        Product match = Inventory.lookupProduct(num);
        return match != null;
    }

    /**
     * The enum Error type.
     */
    enum ErrorType{
        /**
         * Missing part during search error type.
         */
        MISSING_PART,
        /**
         * Nothing is selected to be removed from associated parts error type
         */
        REMOVE_NO_SELECTION,
        /**
         * NO part is selected to be added to associated parts error type
         */
        ADD_NO_SELECTION,
        /**
         * Unk error type.
         */
        UNK,
        /**
         * Missing input error type.
         */
        MISSING_INPUT,
        /**
         * Min is greater than max error type.
         */
        MIN_MAX,
        /**
         * Stock out of range error type.
         */
        STOCK_RANGE
    }

    /**
     * UI error msg handler that shows the appropriate error message based on the ErrorType.
     *
     * @param errorType the error type
     */
    public void uiErrorMsgHandler(ErrorType errorType){
        addProduct_ErrorLabel.setOpacity(1);
       switch(errorType){
           case MISSING_PART -> {
               addProduct_ErrorLabel.setText("Error: Part Missing");
           }
           case REMOVE_NO_SELECTION ->{
               addProduct_ErrorLabel.setText("Error: MUST SELECT A PART TO REMOVE.");
           }
           case ADD_NO_SELECTION ->{
               addProduct_ErrorLabel.setText("Error: MUST SELECT A PART TO ADD.");
           }
           case UNK -> {
               addProduct_ErrorLabel.setText("ERROR- SOMETHING WENT WRONG, Check Inputs");
           }
           case MISSING_INPUT -> {
               addProduct_ErrorLabel.setText("ERROR- INPUTS MISSING" );
           }
           case MIN_MAX -> {
               addProduct_ErrorLabel.setText("ERROR- MIN HAS TO BE LESS THAN MAX!");
           }
           case STOCK_RANGE -> {
               addProduct_ErrorLabel.setText("ERROR- INV should be between min and max!");
           }
       }
    }

    /**
     * Enables and displays the UI elements needed for confirming the removal of selected associated part.
     *
     */
    private void getConformationUI() {
        addProduct_ErrorLabel.setOpacity(1);
        addProduct_ErrorLabel.setText("Are you sure you would like to remove association?");

        yes_btn.setOpacity(1);
        no_btn.setOpacity(1);

        yes_btn.setDisable(false);
        no_btn.setDisable(false);
    }

    /**
     * Resets the UI for all error labels and conformations buttons back to disabled and invisible
     */
    public void uiRESET(){
        addProduct_ErrorLabel.setOpacity(0);

        yes_btn.setOpacity(0);
        no_btn.setOpacity(0);

        yes_btn.setDisable(true);
        no_btn.setDisable(true);
    }
}
