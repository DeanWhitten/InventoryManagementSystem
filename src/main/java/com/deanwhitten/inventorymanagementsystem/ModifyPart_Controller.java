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

    @FXML
    protected void inHouseRadioClicked(MouseEvent mouseEvent){
        isOutsourced = false;
        toggled_label.setText("Machine ID");
        outsourcedRadio.setSelected(false);
        m_c_Toggled_input.clear();
    }

    @FXML
    protected void outsourcedRadioClicked(MouseEvent mouseEvent){
        isOutsourced = true;
        toggled_label.setText("Company Name");
        inHouseRadio.setSelected(false);
        m_c_Toggled_input.clear();
    }
    
    @FXML
    protected void saveButtonClicked(ActionEvent event) throws IOException {
        int id = Integer.parseInt(id_input.getText());
        String name = name_input.getText();
        int stock = Integer.parseInt(inv_input.getText());
        double price = Double.parseDouble(priceCost_input.getText());
        int min = Integer.parseInt(min_input.getText());
        int max = Integer.parseInt(max_input.getText());

        modifyPart_ErrorLabel.setOpacity(0);

        if(min > max){
            modifyPart_ErrorLabel.setOpacity(1);
            modifyPart_ErrorLabel.setText("Error: Min must be less than Max");
        }else {
            
            try{
                if(isOutsourced){
                    String compName = m_c_Toggled_input.getText();
                    Outsourced mod_Part = new Outsourced(id, name, price,stock,min,max,compName);
                    Inventory.updatePart(index, mod_Part);
                    returnToMainPage(event);
                }else{
                    int machineId = Integer.parseInt(m_c_Toggled_input.getText());
                    InHouse mod_part = new InHouse(id, name, price, stock, min, max,machineId);
                    Inventory.updatePart(index, mod_part);
                    returnToMainPage(event);
                }
            
            }catch (NumberFormatException e){
                modifyPart_ErrorLabel.setOpacity(1);
                modifyPart_ErrorLabel.setText("Error: NumberFormatException - SOMETHING WENT WRONG");
            }
        }
    }

    @FXML
    protected void cancelButtonClicked(ActionEvent event) throws IOException {
        returnToMainPage(event);
    }

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

    private void returnToMainPage(ActionEvent event) throws IOException {
        Main_Controller.loadPage("Main_view", event);
    }
}
