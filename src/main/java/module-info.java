module com.example.testjav {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.testjav to javafx.fxml;
    exports com.example.testjav;
}