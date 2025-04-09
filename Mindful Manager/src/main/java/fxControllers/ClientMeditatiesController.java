package fxControllers;

import domein.Meditatie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ClientSession;
import utils.MeditatieSession;

import java.io.IOException;
import java.util.ArrayList;

public class ClientMeditatiesController {
    @FXML
    private Button meditatiesKnop;
    @FXML
    private Button afsprakenKnop;
    @FXML
    private Button tijdslotenKnop;
    @FXML
    private Button homeKnop;
    @FXML
    private VBox meditatiesVbox;
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
        ArrayList<Meditatie> meditaties = ClientSession.getActieveClient().getMeditatieList();

        for (Meditatie meditatie : meditaties) {
            Button button = new Button(meditatie.getMeditatieNaam());
            button.setPrefWidth(200);
            button.setStyle("-fx-font-size: 14px;");
            button.setStyle("-fx-background-color: rgba(104,151,149,0.5);");

            button.setOnAction(event -> {
                try {

                    MeditatieSession.setGeselecteerdeMeditatie(meditatie);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientMeditatieDetails.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) button.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            meditatiesVbox.getChildren().add(button);
        }
    }
    public void zoek (ActionEvent event) {
        String zoekterm = zoekField.getText();
        meditatiesVbox.getChildren().clear();
        for (Meditatie meditatie : ClientSession.getActieveClient().getMeditatieList()) {
            if (ClientSession.getActieveClient().zoekMeditatie(zoekterm, meditatie)) {
                Button button = new Button(meditatie.getMeditatieNaam());
                button.setPrefWidth(200);
                button.setStyle("-fx-font-size: 14px;");
                button.setStyle("-fx-background-color: rgba(104,151,149,0.5);");

                button.setOnAction(event2 -> {
                    try {

                        MeditatieSession.setGeselecteerdeMeditatie(meditatie);


                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/coachMeditatieDetails.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) button.getScene().getWindow();
                        stage.setScene(new Scene(root));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                meditatiesVbox.getChildren().add(button);

            }
        }
    }
}
