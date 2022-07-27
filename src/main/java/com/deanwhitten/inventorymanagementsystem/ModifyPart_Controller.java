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
import java.util.ResourceBundle;

/**
 * Modify part controller.
 *
 *  @author Dean F Whitten
 */
public class ModifyPart_Controller implements Initializable {
    /**
     * The Inv.
     */
    Inventory inv;
    private Part part;
    private static int index;

    /**
     * The In house radio.
     */
    public RadioButton inHouseRadio;
    /**
     * The Outsourced radio.
     */
    public RadioButton outsourcedRadio;
    /**
     * The Modify parts radios.
     */
    public ToggleGroup modifyPartsRadios;

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
     * The Modify part error label.
     */
    public Label modifyPart_ErrorLabel;

    private boolean isOutsourced;

    /**
     * Initializes Controller.
     * Loads the selected part and populates the fields with info from the part selected.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        part = Main_Controller.selectedPart;
        index = Inventory.getAllParts().indexOf(part);

        name_input.setText(part.getName());
        id_input.setText((Integer.toString(part.getId())));
        inv_input.setText((Integer.toString(part.getStock())));
        priceCost_input.setText((Double.toString(part.getPrice())));
        min_input.setText((Integer.toString(part.getMin())));
        max_input.setText((Integer.toString(part.getMax())));

        if (part instanceof InHouse) {
            inHouseRadio.setSelected(true);
            toggled_label.setText("Machine ID");
            m_c_Toggled_input.setText((Integer.toString(((InHouse) part).getMachineID())));
            isOutsourced = false;
        }

        if (part instanceof Outsourced) {
            outsourcedRadio.setSelected(true);
            toggled_label.setText("Company Name");
            m_c_Toggled_input.setText(((Outsourced) part).getCompanyName());
            isOutsourced = true;
        }
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
        m_c_Toggled_input.clear();
        uiErrorMsgRESET();
    }

    /**
     * Out Sourced radio clicked.
     * changes the boolean for determining in in-house our out-sourced, the toggled label text to "Company",
     * unselects in-house radio, clears the machine & company input, and resets the UI error messages.
     *
     * @param mouseEvent out-sourced radio clicked
     */
    @FXML
    protected void outsourcedRadioClicked(MouseEvent mouseEvent){
        isOutsourced = true;
        toggled_label.setText("Company Name");
        inHouseRadio.setSelected(false);
        m_c_Toggled_input.clear();
        uiErrorMsgRESET();
    }

    /**
     * Save button clicked.
     * Checks for any blanks in the input, checks if min is less than max, and checks if Inv is equal or less than
     * max or equal to or greater than min. If any of those conditions are not met then the UI will display an error
     * message related to the condition not met and will not save new part till conditions are met. Otherwise, the
     * part will be updated in the inventory and the program will return to the main window.
     *
     * @param event save button clicked
     */
    @FXML
    protected void saveButtonClicked(ActionEvent event) throws IOException {
        try{
            uiErrorMsgRESET();
            if( !(name_input.getText().isEmpty() || name_input.getText().isBlank() )
                    || !( inv_input.getText().isEmpty() || inv_input.getText().isBlank() )
                    || !( priceCost_input.getText().isEmpty() || priceCost_input.getText().isBlank() )
                    || !( max_input.getText().isEmpty() || max_input.getText().isBlank() )
                    || !( max_input.getText().isEmpty() || min_input.getText().isBlank() )
                    || !( m_c_Toggled_input.getText().isEmpty() || m_c_Toggled_input.getText().isBlank() )
            ){
                int id = Integer.parseInt(id_input.getText());
                String name = name_input.getText();
                int stock = Integer.parseInt(inv_input.getText());
                double price = Double.parseDouble(priceCost_input.getText());
                int min = Integer.parseInt(min_input.getText());
                int max = Integer.parseInt(max_input.getText());

                int machineId;
                String compName;

                if ( (min >= 0 &&  min <= max) && (stock >= min && stock <= max) ) {

                    if (inHouseRadio.isSelected() && !m_c_Toggled_input.getText().equals("")) {
                        machineId = Integer.parseInt(m_c_Toggled_input.getText());
                        InHouse updatedPart = new InHouse(id, name, price, stock, min, max, machineId);
                        try{
                            Inventory.updatePart(index, updatedPart);
                            returnToMainPage(event);
                        } catch (Exception e) {
                            uiErrorMsgHandler(ErrorType.UNK);
                        }
                    } else{
                        uiErrorMsgHandler(ErrorType.MISSING_INPUT);
                    }

                    if ((outsourcedRadio.isSelected() || isOutsourced) && !m_c_Toggled_input.getText().equals("")) {
                        compName = m_c_Toggled_input.getText();
                        Outsourced updatedPart = new Outsourced(id, name, price, stock, min, max, compName);
                        try{
                            Inventory.updatePart(index, updatedPart);
                            returnToMainPage(event);
                        } catch (Exception e) {
                            uiErrorMsgHandler(ErrorType.UNK);
                        }
                    }else{
                        uiErrorMsgHandler(ErrorType.MISSING_INPUT);
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
     * The enum Error type.
     */
    enum ErrorType{
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
        modifyPart_ErrorLabel.setOpacity(1);
        switch(errorType){
            case UNK -> {
                modifyPart_ErrorLabel.setText("ERROR- SOMETHING WENT WRONG, Check inputs");
            }
            case MISSING_INPUT -> {
                modifyPart_ErrorLabel.setText("ERROR- INPUTS MISSING" );
            }
            case MIN_MAX -> {
                modifyPart_ErrorLabel.setText("ERROR- MIN HAS TO BE LESS THAN MAX!");
            }
            case STOCK_RANGE -> {
                modifyPart_ErrorLabel.setText("ERROR- INV should be between min and max!");
            }
        }
    }

    /**
     * Ui error msg reset. Hides Error label.
     */
    public void uiErrorMsgRESET(){
        modifyPart_ErrorLabel.setOpacity(0);
    }
}
