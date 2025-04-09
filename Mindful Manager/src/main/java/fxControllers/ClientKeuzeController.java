package fxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientKeuzeController {
    @FXML
    private Button jaKnop;
    @FXML
    private Button neeKnop;

    public void openClientLogin (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/clientLogin.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void openClientRegister (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/clientRegister.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
