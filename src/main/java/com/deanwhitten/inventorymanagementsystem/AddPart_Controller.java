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

public class AddPart_Controller implements Initializable {
    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public ToggleGroup AddPartsRadios;

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

    private boolean isOutsourced;

    private int generatedIdNum;
    

   @FXML
    protected void inHouseRadioClicked(MouseEvent mouseEvent){
         isOutsourced = false;
         toggled_label.setText("Machine ID");
         outsourcedRadio.setSelected(false);
    }

    @FXML
    protected void outsourcedRadioClicked(MouseEvent mouseEvent){
        isOutsourced = true;
        toggled_label.setText("Company Name");
        inHouseRadio.setSelected(false);
    }

    @FXML
    void saveButtonClicked(ActionEvent event) {
       try{
        int id = generatedIdNum;
        String name = name_input.getText();
        double price = Double.parseDouble(priceCost_input.getText());
        int stock = Integer.parseInt(inv_input.getText());
        int min = Integer.parseInt(min_input.getText());
        int max = Integer.parseInt(max_input.getText());
        int machineId;
        String compName;

        if (!(name.isEmpty() || name.isBlank()) ) {
            if (!(min <= 0 || min >= max) && (stock < min || stock > max)) {
                if (inHouseRadio.isSelected()) {
                    machineId = Integer.parseInt(m_c_Toggled_input.getText());
                    InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                    try{
                        Inventory.addPart(newPart);
                        returnToMainPage(event);
                    } catch (Exception e) {
                        System.out.println("PART ADD FAIL");
                    }
                } else if (outsourcedRadio.isSelected() || isOutsourced) {
                    compName = m_c_Toggled_input.getText();
                    Outsourced newPart = new Outsourced(id, name, price, stock, min, max, compName);
                    try{
                        Inventory.addPart(newPart);
                        returnToMainPage(event);
                    } catch (Exception e) {
                        System.out.println("PART ADD FAIL");
                    }
                }
            }
        } } catch (Exception e){
           System.out.println("PART ADD FAIL");
       }
    }
    
    @FXML
    protected void cancelButtonClicked(ActionEvent event) throws IOException {
        returnToMainPage(event);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generatedIdNum = generatePartID();

        id_input.setText("Auto-Gen: " + generatedIdNum);
    }

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

    private boolean verifyIfTaken(Integer num) {
        Part match = Inventory.lookupPart(num);
        return match != null;
    }

    private void returnToMainPage(ActionEvent event) throws IOException {

        Main_Controller.loadPage("Main_view", event);
    }
}
