module MindfulManager {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    exports domein;
    exports fxControllers;
    opens fxControllers to javafx.fxml;
}