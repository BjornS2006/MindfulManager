package fxControllers;

import domein.Tijdslot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.BeheerderSession;
import utils.DatabaseUtil;
import utils.TijdslotSession;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

public class CoachTijdslotDetailsController {
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
    private Label tijdLabel;
    @FXML
    private Label datumLabel;
    @FXML
    private Button deleteKnop;

    public void openHome(ActionEvent event) throws IOException {
        TijdslotSession.clear(); // Clear het geselecteerde tijdslot
        Parent root = FXMLLoader.load(getClass().getResource("/coachHome.fxml"));
        setScene(event, root);
    }

    public void openMeditaties(ActionEvent event) throws IOException {
        TijdslotSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachMeditaties.fxml"));
        setScene(event, root);
    }

    public void openAfspraken(ActionEvent event) throws IOException {
        TijdslotSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachAfspraken.fxml"));
        setScene(event, root);
    }

    public void openTijdsloten(ActionEvent event) throws IOException {
        TijdslotSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachTijdsloten.fxml"));
        setScene(event, root);
    }

    public void openClienten(ActionEvent event) throws IOException {
        TijdslotSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachClienten.fxml"));
        setScene(event, root);
    }

    private void setScene(ActionEvent event, Parent root) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
        window.show();
    }

    public void initialize() {
        Tijdslot geselecteerd = TijdslotSession.getGeselecteerdTijdslot();
        if (geselecteerd != null) {

            Date datum = geselecteerd.getDatum();
            String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(datum);
            datumLabel.setText("Datum: " + formattedDate);


            Time start = geselecteerd.getStarttijd();
            Time eind = geselecteerd.getEindtijd();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String tijdTekst = timeFormat.format(start) + " - " + timeFormat.format(eind);
            tijdLabel.setText("Tijd: " + tijdTekst);
        } else {
            datumLabel.setText("Geen tijdslot geselecteerd.");
            tijdLabel.setText("");
        }
    }

    public void verwijderen(ActionEvent event) throws IOException, SQLException {
        BeheerderSession.getActieveBeheerder()
                .getTijdslotList()
                .remove(TijdslotSession.getGeselecteerdTijdslot());



        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "DELETE FROM tijdslot WHERE idTijdslot = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, TijdslotSession.getGeselecteerdTijdslot().getIdTijdslot());
            statement.executeUpdate();
        }

        TijdslotSession.clear();

        Parent root = FXMLLoader.load(getClass().getResource("/coachTijdsloten.fxml"));
        setScene(event, root);
    }
}
