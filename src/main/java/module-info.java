module com.deanwhitten.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.deanwhitten.inventorymanagementsystem to javafx.fxml;
    opens com.deanwhitten.inventorymanagementsystem.Model to javafx.fxml;
    exports com.deanwhitten.inventorymanagementsystem;
    exports com.deanwhitten.inventorymanagementsystem.Model;
}