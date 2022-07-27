package com.deanwhitten.inventorymanagementsystem;

import com.deanwhitten.inventorymanagementsystem.Model.InHouse;
import com.deanwhitten.inventorymanagementsystem.Model.Inventory;
import com.deanwhitten.inventorymanagementsystem.Model.Outsourced;
import com.deanwhitten.inventorymanagementsystem.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 *  Add part controller.
 *
 *   @author Dean F Whitten
 */
public class AddPart_Controller implements Initializable {
    /**
     * The In house radio.
     */
    public RadioButton inHouseRadio;
    /**
     * The Outsourced radio.
     */
    public RadioButton outsourcedRadio;
    /**
     * The Add parts radios.
     */
    public ToggleGroup AddPartsRadios;

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
     * The Toggled label.
     */
    public Label toggled_label;
    /**
     * The Machine and Company toggled input.
     */
    public TextField m_c_Toggled_input;

    /**
     * The Save button.
     */
    public Button saveButton;
    /**
     * The Cancel button.
     */
    public Button cancelButton;
    /**
     * The Add part error label.
     */
    public Label addPart_ErrorLabel;

    /**boolean to determine if the part being created is in-house or outsourced*/
    private boolean isOutsourced;

    /**Stores generated ID number*/
    private int generatedIdNum;

    /**
     * Initilizes window and generates a part ID and autofills ID field.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generatedIdNum = generatePartID();

        id_input.setText("Auto-Gen: " + generatedIdNum);
    }

    /**
     * In house radio clicked.
     * changes the boolean for determining in in-house our out-sourced, the toggled label text to "Machine ID",
     * unselects out-sourced radio, clears the machine & company input, and resets the UI error messages.
     *
     * @param mouseEvent in-house radio clicked
     */
    @FXML
    protected void inHouseRadioClicked(MouseEvent mouseEvent){
         isOutsourced = false;
         toggled_label.setText("Machine ID");
         outsourcedRadio.setSelected(false);
         uiErrorMsgRESET();
    }

    /**
     * Outsourced radio clicked.
     *
     * @param mouseEvent the mouse event
     */
    @FXML
    protected void outsourcedRadioClicked(MouseEvent mouseEvent){
        isOutsourced = true;
        toggled_label.setText("Company Name");
        inHouseRadio.setSelected(false);
        uiErrorMsgRESET();
    }

    /**
     * Save button clicked.
     * Checks for any blanks in the input, checks if min is less than max, and checks if Inv is equal or less than
     * max or equal to or greater than min. If any of those conditions are not met then the UI will display an error
     * message related to the condition not met and will not save new part till conditions are met. Otherwise, the
     * part will be saved to the inventory and the program will return to the main window.
     *
     * @param event save button clicked
     */
    @FXML
    void saveButtonClicked(ActionEvent event) {
       try{
           uiErrorMsgRESET();
           if( !(name_input.getText().isEmpty() || name_input.getText().isBlank() )
                   || !( inv_input.getText().isEmpty() || inv_input.getText().isBlank() )
                   || !( priceCost_input.getText().isEmpty() || priceCost_input.getText().isBlank() )
                   || !( max_input.getText().isEmpty() || max_input.getText().isBlank() )
                   || !( max_input.getText().isEmpty() || min_input.getText().isBlank() )
                   || !( m_c_Toggled_input.getText().isEmpty() || m_c_Toggled_input.getText().isBlank() )
           ){
               int id = generatedIdNum;
               String name = name_input.getText();
               int stock = Integer.parseInt(inv_input.getText());
               double price = Double.parseDouble(priceCost_input.getText());
               int min = Integer.parseInt(min_input.getText());
               int max = Integer.parseInt(max_input.getText());

               int machineId;
               String compName;
                   if ( (min >= 0 &&  min <= max) && (stock >= min && stock <= max) ) {
                       if (inHouseRadio.isSelected()) {
                           machineId = Integer.parseInt(m_c_Toggled_input.getText());
                           InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                           try{
                               Inventory.addPart(newPart);
                               returnToMainPage(event);
                           } catch (Exception e) {
                               uiErrorMsgHandler(ErrorType.UNK);
                           }
                       } else if (outsourcedRadio.isSelected() || isOutsourced) {
                           compName = m_c_Toggled_input.getText();
                           Outsourced newPart = new Outsourced(id, name, price, stock, min, max, compName);
                           try{
                               Inventory.addPart(newPart);
                               returnToMainPage(event);
                           } catch (Exception e) {
                               uiErrorMsgHandler(ErrorType.UNK);
                           }
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
     * Generates Unique Part ID number  and checks if the generated already is used.
     * @return generated part ID
     */
    private int generatePartID() {
        boolean match;
        Random randomNum = new Random();
        int num = randomNum.nextInt(10003);

        match = verifyIfTaken(num);

        if (!match) {
            id_input.setText(Integer.toString(num));
        } else {
            generatePartID();
        }
        return num;
    }

    /**
     * Checks to make sure that generated ID Number is not already assigned to another part.
     * @param num number generated
     * @return true or false based on if the number is already assigned
     */
    private boolean verifyIfTaken(Integer num) {
        Part match = Inventory.lookupPart(num);
        return match != null;
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
     * The enum Error type.
     */
    enum ErrorType{
        /**
         * Unk error type.
         */
        UNK,
        /**
         * Missing input during search error type.
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
        addPart_ErrorLabel.setOpacity(1);
        switch(errorType){
            case UNK -> {
                addPart_ErrorLabel.setText("ERROR- SOMETHING WENT WRONG, Check Inputs");
            }
            case MISSING_INPUT -> {
                addPart_ErrorLabel.setText("ERROR- INPUTS MISSING" );
            }
            case MIN_MAX -> {
            addPart_ErrorLabel.setText("ERROR- MIN HAS TO BE LESS THAN MAX!");
            }
            case STOCK_RANGE -> {
                addPart_ErrorLabel.setText("ERROR- INV should be between min and max!");
            }
        }
    }


    /**
     * Ui error msg reset. Hides Error label.
     */
    public void uiErrorMsgRESET(){
        addPart_ErrorLabel.setOpacity(0);
    }
}
