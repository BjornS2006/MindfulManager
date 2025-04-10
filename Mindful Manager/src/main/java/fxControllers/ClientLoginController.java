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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.ClientSession;
import utils.DatabaseUtil;

import java.io.IOException;
import java.sql.*;

public class ClientLoginController {
    @FXML
    private TextField clientInlogEmailField;
    @FXML
    private PasswordField clientInlogWachtwoordField;
    @FXML
    private Button clientInlogKnop;
    @FXML
    private Label errorLabel;

    public void clientInloggen(ActionEvent event) throws IOException {
        String email = clientInlogEmailField.getText();
        String wachtwoord = clientInlogWachtwoordField.getText();
        if (isLoginValid(email, wachtwoord)) {
            Parent root = FXMLLoader.load(getClass().getResource("/clientHome.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        else {
            errorLabel.setText("Onjuist email adres of wachtwoord.");
        }
    }


    private boolean isLoginValid(String email, String wachtwoord) {
        boolean isValid = false;

        String query = "SELECT * FROM client WHERE email = ? AND wachtwoord = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, wachtwoord);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {

                    int idClient = rs.getInt("idClient");
                    String voornaam = rs.getString("voornaam");
                    String achternaam = rs.getString("achternaam");
                    String telnr = rs.getString("telnr");
                    int niveau = rs.getInt("niveau");
                    String adres = rs.getString("adres");
                    String woonplaats = rs.getString("woonplaats");
                    String notities = rs.getString("notities");
                    Date geboortedatum = rs.getDate("geboortedatum");


                    Client actieveClient = new Client(
                            voornaam,
                            achternaam,
                            email,
                            telnr,
                            wachtwoord,
                            idClient,
                            niveau,
                            adres,
                            woonplaats,
                            notities,
                            geboortedatum
                    );
                    ClientSession.setActieveClient(actieveClient);
                    ClientSession.getActieveClient().fillLists();
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }
}

