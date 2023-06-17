module com.example.theproject_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.theproject_1 to javafx.fxml;
    exports com.example.theproject_1;
}