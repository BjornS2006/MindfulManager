package fxControllers;

import domein.Afspraak;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.text.Text;
import utils.BeheerderSession;

import java.io.IOException;
import java.util.ArrayList;

public class CoachAfsprakenController {
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
    private VBox afsprakenVbox;

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


    public void initialize() {
        ArrayList<Afspraak> afspraken = BeheerderSession.getActieveBeheerder().getAfspraakList();


        DateTimeFormatter datumFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter tijdFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Afspraak afspraak : afspraken) {

            LocalDate datum = afspraak.getDatum().toLocalDate();
            LocalTime start = afspraak.getStarttijd().toLocalTime();
            LocalTime eind = afspraak.getEindtijd().toLocalTime();


            String tekst = "📅 " + datum.format(datumFormatter) +
                    "  🕒 " + start.format(tijdFormatter) + " - " + eind.format(tijdFormatter) +
                    "  👤 " + afspraak.getClientNaam();

            Text afspraakText = new Text(tekst);
            afspraakText.setStyle("-fx-font-size: 14px; -fx-fill: #333;");

            afsprakenVbox.getChildren().add(afspraakText);
        }
    }

}
