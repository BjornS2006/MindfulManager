package fxControllers;

import domein.Tijdslot;
import javafx.scene.control.TextField;
import utils.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CoachTijdslotenController {
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
    private VBox tijdslotenVbox;
    @FXML
    private Button nieuwTijdslotKnop;
    @FXML
    private TextField zoekField;
    @FXML
    private Button zoekKnop;

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
        ArrayList<Tijdslot> tijdsloten = BeheerderSession.getActieveBeheerder().getTijdslotList();


        DateTimeFormatter datumFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter tijdFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Tijdslot tijdslot : tijdsloten) {

            LocalDate datum = tijdslot.getDatum().toLocalDate();
            LocalTime start = tijdslot.getStarttijd().toLocalTime();
            LocalTime eind = tijdslot.getEindtijd().toLocalTime();

            String buttonText = datum.format(datumFormatter) + " | " +
                    start.format(tijdFormatter) + " - " +
                    eind.format(tijdFormatter);

            Button button = new Button(buttonText);
            button.setPrefWidth(250);
            button.setStyle("-fx-font-size: 14px;");
            button.setStyle("-fx-background-color: rgba(104,151,149,0.5);");

            button.setOnAction(event -> {
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
    public void openNieuwTijdslot (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/coachNieuwTijdslot.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void zoek (ActionEvent event) throws IOException {
        String zoekterm = zoekField.getText();
        tijdslotenVbox.getChildren().clear();
        for (Tijdslot tijdslot : BeheerderSession.getActieveBeheerder().getTijdslotList()) {
            if (BeheerderSession.getActieveBeheerder().zoekTijdslot(zoekterm, tijdslot)) {
                DateTimeFormatter datumFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter tijdFormatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalDate datum = tijdslot.getDatum().toLocalDate();
                LocalTime start = tijdslot.getStarttijd().toLocalTime();
                LocalTime eind = tijdslot.getEindtijd().toLocalTime();


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



