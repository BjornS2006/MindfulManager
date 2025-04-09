package fxControllers;

import domein.Client;
import domein.Tijdslot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ClientSession;
import utils.TijdslotSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class ClientTijdslotenController {
    @FXML
    private Button meditatiesKnop;
    @FXML
    private Button afsprakenKnop;
    @FXML
    private Button tijdslotenKnop;
    @FXML
    private Button homeKnop;
    @FXML
    private VBox tijdslotenVbox;
    @FXML
    private TextField zoekField;
    @FXML
    private Button zoekKnop;

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
        ArrayList<Tijdslot> tijdsloten = ClientSession.getActieveClient().getTijdslotList();

        DateTimeFormatter datumFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter tijdFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Tijdslot tijdslot : tijdsloten) {
            String label = tijdslot.getDatum().toLocalDate().format(datumFormatter)
                    + " | " + tijdslot.getStarttijd().toLocalTime().format(tijdFormatter)
                    + " - " + tijdslot.getEindtijd().toLocalTime().format(tijdFormatter);

            Button tijdslotKnop = new Button(label);
            tijdslotKnop.setPrefWidth(250);
            tijdslotKnop.setStyle("-fx-background-color: #689797; -fx-text-fill: white; -fx-font-size: 14px;");

            tijdslotKnop.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Afspraak bevestigen");
                alert.setHeaderText("Weet je zeker dat je een afspraak wil plannen?");
                alert.setContentText("Op: " + label);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Tijdslot doorgeven aan client
                    Client actieveClient = ClientSession.getActieveClient();
                    try {
                        actieveClient.afspraakAanmaken(tijdslot);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("Afspraak ingepland");
                    success.setHeaderText(null);
                    success.setContentText("Je afspraak is succesvol ingepland!");
                    success.showAndWait();


                }
            });

            tijdslotenVbox.getChildren().add(tijdslotKnop);
        }
    }
    public void zoek (ActionEvent event) throws IOException {
        String zoekterm = zoekField.getText();
        tijdslotenVbox.getChildren().clear();
        for (Tijdslot tijdslot : ClientSession.getActieveClient().getTijdslotList()) {
            if (ClientSession.getActieveClient().zoekTijdslot(zoekterm, tijdslot)) {
                DateTimeFormatter datumFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter tijdFormatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalDate datum = tijdslot.getDatum().toLocalDate();
                LocalTime start = tijdslot.getStarttijd().toLocalTime();
                LocalTime eind = tijdslot.getEindtijd().toLocalTime();

                // Geformatteerde tekst
                String buttonText = datum.format(datumFormatter) + " | " +
                        start.format(tijdFormatter) + " - " +
                        eind.format(tijdFormatter);

                Button button = new Button(buttonText);
                button.setPrefWidth(250);
                button.setStyle("-fx-font-size: 14px;");
                button.setStyle("-fx-background-color: rgba(104,151,149,0.5);");

                button.setOnAction(event2 -> {
                    try {
                        TijdslotSession.setGeselecteerdTijdslot(tijdslot);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/coachTijdslotDetails.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) button.getScene().getWindow();
                        stage.setScene(new Scene(root));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                tijdslotenVbox.getChildren().add(button);
            }
        }
    }

}
