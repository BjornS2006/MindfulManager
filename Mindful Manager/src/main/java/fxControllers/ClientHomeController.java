package fxControllers;

import domein.Afspraak;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.ClientSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ClientHomeController {
    @FXML
    private Button meditatiesKnop;
    @FXML
    private Button afsprakenKnop;
    @FXML
    private Button tijdslotenKnop;
    @FXML
    private Button homeKnop;
    @FXML
    private Label datumLabel;
    @FXML
    private Label tijdLabel;
    @FXML
    private Label welkomLabel;

    public void openHome (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/clientHome.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void openMeditaties (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/clientMeditaties.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void openAfspraken (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/clientAfspraken.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void openTijdsloten (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/clientTijdsloten.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void initialize() {
        String naam = ClientSession.getActieveClient().getNaam();
        welkomLabel.setText("Welkom " + naam + "!");
        ArrayList<Afspraak> afspraken = ClientSession.getActieveClient().getAfspraakList();

        if (afspraken != null && !afspraken.isEmpty()) {
            Afspraak eerstVolgende = afspraken.get(0);


            Date sqlDatum = eerstVolgende.getDatum();
            Time sqlStarttijd = eerstVolgende.getStarttijd();
            Time sqlEindtijd = eerstVolgende.getEindtijd();


            LocalDate datum = sqlDatum.toLocalDate();
            LocalTime starttijd = sqlStarttijd.toLocalTime();
            LocalTime eindtijd = sqlEindtijd.toLocalTime();


            DateTimeFormatter datumFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter tijdFormatter = DateTimeFormatter.ofPattern("HH:mm");


            datumLabel.setText("Datum: " + datum.format(datumFormatter));
            tijdLabel.setText("Tijd: " + starttijd.format(tijdFormatter) + " - " + eindtijd.format(tijdFormatter));
        } else {
            datumLabel.setText("Geen afspraken gepland.");
            tijdLabel.setText("");
        }
    }
}
