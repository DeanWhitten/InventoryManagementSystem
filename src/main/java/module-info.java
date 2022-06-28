module com.deanwhitten.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.deanwhitten.inventorymanagementsystem to javafx.fxml;
    exports com.deanwhitten.inventorymanagementsystem;
}