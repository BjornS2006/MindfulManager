package fxControllers;

import domein.Meditatie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.media.MediaView;
import utils.BeheerderSession;
import utils.DatabaseUtil;
import utils.MeditatieSession;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoachMeditatieDetailsController {
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
    private Label lengteLabel;
    @FXML
    private Label naamLabel;
    @FXML
    private Label niveauLabel;
    @FXML
    private MediaView meditatieView;
    @FXML
    private Button deleteKnop;
    @FXML
    private Button afspelenKnop;
    @FXML
    private MediaPlayer mediaPlayer;

    public void openHome(ActionEvent event) throws IOException {
        MeditatieSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachHome.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void openMeditaties(ActionEvent event) throws IOException {
        MeditatieSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachMeditaties.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void openAfspraken(ActionEvent event) throws IOException {
        MeditatieSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachAfspraken.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void openTijdsloten(ActionEvent event) throws IOException {
        MeditatieSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachTijdsloten.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void openClienten(ActionEvent event) throws IOException {
        MeditatieSession.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/coachClienten.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void initialize() {
        Meditatie geselecteerd = MeditatieSession.getGeselecteerdeMeditatie();

        naamLabel.setText("Naam: " + geselecteerd.getMeditatieNaam());
        lengteLabel.setText("Lengte in minuten: " + geselecteerd.getLengte());
        niveauLabel.setText("Niveau: " + geselecteerd.getNiveau());
    }

    public void verwijderen(ActionEvent event) throws IOException, SQLException {
        BeheerderSession.getActieveBeheerder().getMeditatieList()
                .remove(MeditatieSession.getGeselecteerdeMeditatie());

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "DELETE FROM meditatie WHERE idMeditatie = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, MeditatieSession.getGeselecteerdeMeditatie().getIdMeditatie());
            statement.executeUpdate();

            MeditatieSession.clear();
            Parent root = FXMLLoader.load(getClass().getResource("/coachMeditaties.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    public File getSpraakBestandVoorMeditatie(int idMeditatie) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT spraakBestand FROM meditatie WHERE idMeditatie = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idMeditatie);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                InputStream blobStream = rs.getBlob("spraakBestand").getBinaryStream();
                File tempFile = File.createTempFile("spraak", ".mp3");
                tempFile.deleteOnExit();

                try (FileOutputStream out = new FileOutputStream(tempFile)) {
                    blobStream.transferTo(out);
                }

                return tempFile;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void speelAf(ActionEvent event) throws IOException{
        File mp3 = getSpraakBestandVoorMeditatie(MeditatieSession.getGeselecteerdeMeditatie().getIdMeditatie());
        if (mp3 != null) {
            Media media = new Media(mp3.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            meditatieView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        }
    }
}