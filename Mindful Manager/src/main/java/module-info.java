module MindfulManager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires java.sql;


    exports domein;
    exports fxControllers;
    opens fxControllers to javafx.fxml;
}