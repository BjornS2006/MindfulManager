package fxControllers;

import domein.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DatabaseUtil;
import utils.GeselecteerdeClientSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CoachClientDetailsController {
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
    private Label naamLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label telnrLabel;
    @FXML
    private Label geboortedatumLabel;
    @FXML
    private Label niveauLabel;
    @FXML
    private Label woonplaatsLabel;
    @FXML
    private Label adresLabel;
    @FXML
    private Label notitiesLabel;
    @FXML
    private TextField nieuweNotitieField;
    @FXML
    private Button nieuweNotitieKnop;
    @FXML
    private Button verhogenKnop;
    @FXML
    private TextField niveauVerhogenField;


    public void openHome (ActionEvent event) throws IOException {
        GeselecteerdeClientSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachHome.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void openMeditaties (ActionEvent event) throws IOException {
        GeselecteerdeClientSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachMeditaties.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void openAfspraken (ActionEvent event) throws IOException {
        GeselecteerdeClientSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachAfspraken.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void openTijdsloten (ActionEvent event) throws IOException {
        GeselecteerdeClientSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachTijdsloten.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void openClienten (ActionEvent event) throws IOException {
        GeselecteerdeClientSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachClienten.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void initialize() {
        Client geselecteerdeClient = GeselecteerdeClientSession.getGeselecteerdeClient();

        if (geselecteerdeClient != null) {
            naamLabel.setText("Naam: " + geselecteerdeClient.getNaam());
            emailLabel.setText("Email adres: " + geselecteerdeClient.getEmail());
            telnrLabel.setText("Telefoonnummer: " + geselecteerdeClient.getTelnr());
            adresLabel.setText("Adres: " + geselecteerdeClient.getAdres());
            woonplaatsLabel.setText("Woonplaats: " + geselecteerdeClient.getWoonplaats());

            Date geboortedatum = geselecteerdeClient.getGeboortedatum();
            if (geboortedatum != null) {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                geboortedatumLabel.setText("Geboortedatum: " + dateFormat.format(geboortedatum));
            } else {
                geboortedatumLabel.setText("Onbekend");
            }

            niveauLabel.setText("Niveau: " + geselecteerdeClient.getNiveau());

            String notities = geselecteerdeClient.getNotities();
            notitiesLabel.setText((notities != null && !notities.isEmpty()) ? notities : "Geen notities");
        }
    }
    public void nieuweNotitieAanmaken (ActionEvent event) throws IOException, SQLException {
        String nieuweNotitie = nieuweNotitieField.getText();
        GeselecteerdeClientSession.getGeselecteerdeClient().setNotities(nieuweNotitie);

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "UPDATE client SET notities = ? WHERE idClient = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nieuweNotitie);
            statement.setInt(2, GeselecteerdeClientSession.getGeselecteerdeClient().getIdClient());
            statement.executeUpdate();
        }
        notitiesLabel.setText(nieuweNotitie);

    }
    public void niveauVerhogen (ActionEvent event) throws IOException, SQLException {
        int nieuwNiveau = Integer.parseInt(niveauVerhogenField.getText());
        GeselecteerdeClientSession.getGeselecteerdeClient().setNiveau(nieuwNiveau);
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "UPDATE client SET niveau = ? WHERE idClient = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, nieuwNiveau);
            statement.setInt(2, GeselecteerdeClientSession.getGeselecteerdeClient().getIdClient());
            statement.executeUpdate();
        }
    }
}
