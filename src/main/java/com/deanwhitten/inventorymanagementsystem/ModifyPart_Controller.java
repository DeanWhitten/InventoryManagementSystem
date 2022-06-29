package com.deanwhitten.inventorymanagementsystem;

import javafx.scene.control.*;

public class ModifyPart_Controller {
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
}
