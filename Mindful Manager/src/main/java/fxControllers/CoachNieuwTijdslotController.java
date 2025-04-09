package fxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.BeheerderSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CoachNieuwTijdslotController {
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
    private DatePicker datumPicker;
    @FXML
    private TextField starttijdField;
    @FXML
    private TextField eindtijdField;
    @FXML
    private Button aanmakenKnop;
    @FXML
    private Label aangemaaktLabel;

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
    public void nieuwTijdslotAanmaken(ActionEvent event) {
        try {

            LocalDate geselecteerdeDatum = datumPicker.getValue();
            if (geselecteerdeDatum == null) {
                throw new IllegalArgumentException("Geen datum geselecteerd.");
            }
            java.sql.Date sqlDatum = java.sql.Date.valueOf(geselecteerdeDatum);

            String starttijdString = starttijdField.getText();
            String eindtijdString = eindtijdField.getText();

            if (starttijdString.isEmpty() || eindtijdString.isEmpty()) {
                throw new IllegalArgumentException("Start- of eindtijd is leeg.");
            }


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startLocalTime = LocalTime.parse(starttijdString, formatter);
            LocalTime eindLocalTime = LocalTime.parse(eindtijdString, formatter);
            java.sql.Time sqlStarttijd = java.sql.Time.valueOf(startLocalTime);
            java.sql.Time sqlEindtijd = java.sql.Time.valueOf(eindLocalTime);


            BeheerderSession.getActieveBeheerder().tijdslotAanmaken(sqlDatum, sqlStarttijd, sqlEindtijd);
            aangemaaktLabel.setText("Tijdslot aangemaakt!");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
