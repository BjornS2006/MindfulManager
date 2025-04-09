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

public class BeginSchermController {
    @FXML
    private Button coachKnop;
    @FXML
    private Button clientKnop;

    public void naarCoachLogin (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/coachLogin.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void naarClientKeuze (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/clientKeuze.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
