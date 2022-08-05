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

public class ModifyPart_Controller implements Initializable {
    Inventory inv;
    private Part part;
    private static int index;

    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public ToggleGroup modifyPartsRadios;

    public TextField id_input;
    public TextField name_input;
    public TextField inv_input;
    public TextField priceCost_input;
    public TextField max_input;
    public TextField min_input;
    public Label toggled_label;
    public TextField m_c_Toggled_input;

    public Button saveButton;
    public Button cancelButton;
    public Label modifyPart_ErrorLabel;

    private boolean isOutsourced;

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

    @FXML
    protected void inHouseRadioClicked(MouseEvent mouseEvent){
        isOutsourced = false;
        toggled_label.setText("Machine ID");
        outsourcedRadio.setSelected(false);
        m_c_Toggled_input.clear();
        uiErrorMsgRESET();
    }

    @FXML
    protected void outsourcedRadioClicked(MouseEvent mouseEvent){
        isOutsourced = true;
        toggled_label.setText("Company Name");
        inHouseRadio.setSelected(false);
        m_c_Toggled_input.clear();
        uiErrorMsgRESET();
    }

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

    @FXML
    protected void cancelButtonClicked(ActionEvent event) throws IOException {
        returnToMainPage(event);
    }


    private void returnToMainPage(ActionEvent event) throws IOException {
        Main_Controller.loadWindow("Main_view", event);
    }

    enum ErrorType{
        UNK,
        MISSING_INPUT,
        MIN_MAX,
        STOCK_RANGE
    }

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

    public void uiErrorMsgRESET(){
        modifyPart_ErrorLabel.setOpacity(0);
    }
}
