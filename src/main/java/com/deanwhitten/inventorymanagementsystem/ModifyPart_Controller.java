package com.deanwhitten.inventorymanagementsystem;

import com.deanwhitten.inventorymanagementsystem.Model.InHouse;
import com.deanwhitten.inventorymanagementsystem.Model.Inventory;
import com.deanwhitten.inventorymanagementsystem.Model.Outsourced;
import com.deanwhitten.inventorymanagementsystem.Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setData();
    }

    private void setData() {

        if (part instanceof InHouse) {

            InHouse part1 = (InHouse) part;
            inHouseRadio.setSelected(true);
            toggled_label.setText("Machine ID");
            this.name_input.setText(part1.getName());
            this.id_input.setText((Integer.toString(part1.getId())));
            this.inv_input.setText((Integer.toString(part1.getStock())));
            this.priceCost_input.setText((Double.toString(part1.getPrice())));
            this.min_input.setText((Integer.toString(part1.getMin())));
            this.max_input.setText((Integer.toString(part1.getMax())));
            this.m_c_Toggled_input.setText((Integer.toString(part1.getMachineID())));

        }

        if (part instanceof Outsourced) {

            Outsourced part2 = (Outsourced) part;
            outsourcedRadio.setSelected(true);
            toggled_label.setText("Company Name");
            this.name_input.setText(part2.getName());
            this.id_input.setText((Integer.toString(part2.getId())));
            this.inv_input.setText((Integer.toString(part2.getStock())));
            this.priceCost_input.setText((Double.toString(part2.getPrice())));
            this.min_input.setText((Integer.toString(part2.getMin())));
            this.max_input.setText((Integer.toString(part2.getMax())));
            this.m_c_Toggled_input.setText(part2.getCompanyName());
        }
    }
}
