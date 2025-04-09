package fxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.BeheerderSession;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class CoachNieuweMeditatieController {
    @FXML
    private Button meditatiesKnop;
    @FXML
    private Button afsprakenKnop;
    @FXML
    private Button tijdslotenKnop;
    @FXML
    private Button clientenKnop;
    @FXML
    private Button homeKnop;
    @FXML
    private VBox dropBox;
    @FXML
    private Button aanmakenKnop;
    @FXML
    private TextField naamField;
    @FXML
    private TextField niveauField;
    @FXML
    private TextField lengteField;
    @FXML
    private Label dropLabel;
    @FXML
    private Label aangemaaktLabel;

    private File droppedFile;


    public void openHome (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/coachHome.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void openMeditaties (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/coachMeditaties.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void openAfspraken (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/coachAfspraken.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void openTijdsloten (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/coachTijdsloten.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void openClienten (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/coachClienten.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void nieuweMeditatieAanmaken(ActionEvent event) throws IOException, SQLException {
        int niveau = Integer.parseInt(niveauField.getText());
        int lengte = Integer.parseInt(lengteField.getText());

        if (droppedFile != null) {
            BeheerderSession.getActieveBeheerder().meditatieAanmaken(naamField.getText(), niveau, lengte, droppedFile);
        } else {
            dropLabel.setText("Geen geldig bestand geselecteerd!");
        }
        aangemaaktLabel.setText("Meditatie aangemaakt!");
    }
    @FXML
    public void onDragOver(DragEvent event) {
        if (event.getGestureSource() != dropBox && event.getDragboard().hasFiles()) {
            for (File file : event.getDragboard().getFiles()) {
                if (file.getName().toLowerCase().endsWith(".mp3")) {
                    event.acceptTransferModes(TransferMode.COPY);
                    break;
                }
            }
        }
        event.consume();
    }

    @FXML
    public void onDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasFiles()) {
            for (File file : db.getFiles()) {
                if (file.getName().toLowerCase().endsWith(".mp3")) {
                    droppedFile = file;
                    dropLabel.setText("Bestand geselecteerd: " + file.getName());
                    success = true;
                    break;
                }
            }
        }

        event.setDropCompleted(success);
        event.consume();
    }

}
