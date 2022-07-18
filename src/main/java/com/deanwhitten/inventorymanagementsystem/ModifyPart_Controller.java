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
    Part part;

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
    }

    @FXML
    protected void outsourcedRadioClicked(MouseEvent mouseEvent){
        isOutsourced = true;
        toggled_label.setText("Company Name");
        inHouseRadio.setSelected(false);
    }
    @FXML
    protected void cancelButtonClicked(ActionEvent event) throws IOException {
        returnToMainPage(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        part = Main_Controller.selectedPart;

        setData(part);
    }

    private void setData(Part part) {
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

        }

        if (part instanceof Outsourced) {
            outsourcedRadio.setSelected(true);
            toggled_label.setText("Company Name");
            m_c_Toggled_input.setText(((Outsourced) part).getCompanyName());
        }
    }

    private void returnToMainPage(ActionEvent event) throws IOException {
        Main_Controller.loadPage("Main_view", event);
    }
}
