package com.deanwhitten.inventorymanagementsystem;

import com.deanwhitten.inventorymanagementsystem.Model.Inventory;
import com.deanwhitten.inventorymanagementsystem.Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class AddPart_Controller implements Initializable {
    Inventory inv;
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
    public Label errorLabel;

    private boolean isOutsourced;

    private int generatedIDnum;
    

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
    protected void saveButtonClicked(MouseEvent mouseEvent){

    }
    @FXML
    protected void cancelButtonClicked(MouseEvent mouseEvent){

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generatedIDnum = generatePartID();

        id_input.setText("Auto-Gen: " + generatedIDnum);
    }

    private int generatePartID() {
        boolean match;
        Random randomNum = new Random();
        Integer num = randomNum.nextInt(1000);

       
           //if (inv.partListSize() == 0) {
           //          id_input.setText(num.toString());

           //}



        match = verifyIfTaken(num);

        if (match == false) {
            id_input.setText(num.toString());
        } else {
            generatePartID();
        }

        return num;
    }

    private boolean verifyIfTaken(Integer num) {
        Part match = inv.lookupPart(num);
        return match != null;
    }
}
