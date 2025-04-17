package fxControllers;

import domein.Client;
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
import utils.BeheerderSession;
import utils.GeselecteerdeClientSession;

import java.io.IOException;
import java.util.ArrayList;

public class CoachClientenController {
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
    private VBox clientenVbox;
    @FXML
    private Button zoekKnop;
    @FXML
    private TextField zoekField;

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
        ArrayList<Client> clienten = BeheerderSession.getActieveBeheerder().getClientenList();

        for (Client client : clienten) {
            Button button = new Button(client.getNaam());
            button.setPrefWidth(200);
            button.setStyle("-fx-font-size: 14px;");
            button.setStyle("-fx-background-color: rgba(104,151,149,0.5);");

            button.setOnAction(event -> {
                try {
                    GeselecteerdeClientSession.setGeselecteerdeClient(client);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/coachClientDetails.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) button.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            clientenVbox.getChildren().add(button);
        }
    }
    public void zoek (ActionEvent event) throws IOException {
        ArrayList<Client> clienten = BeheerderSession.getActieveBeheerder().getClientenList();
        clientenVbox.getChildren().clear();
        for (Client client : clienten) {
            if (BeheerderSession.getActieveBeheerder().zoekClient(zoekField.getText(), client)) {
                Button button = new Button(client.getNaam());
                button.setPrefWidth(200);
                button.setStyle("-fx-font-size: 14px;");
                button.setStyle("-fx-background-color: rgba(104,151,149,0.5);");
                button.setOnAction(event1 -> {
                    try {
                        GeselecteerdeClientSession.setGeselecteerdeClient(client);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/coachClientDetails.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) button.getScene().getWindow();
                        stage.setScene(new Scene(root));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                clientenVbox.getChildren().add(button);
            }
        }
    }
}
