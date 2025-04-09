package fxControllers;

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
import utils.DatabaseUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientRegisterController {
    @FXML
    private TextField voornaamFieldR;
    @FXML
    private TextField achternaamFieldR;
    @FXML
    private TextField emailFieldR;
    @FXML
    private TextField telefoonnummerFieldR;
    @FXML
    private TextField adresFieldR;
    @FXML
    private TextField woonplaatsFieldR;
    @FXML
    private PasswordField wachtwoordFieldR;
    @FXML
    private PasswordField wachtwoordFieldR2;
    @FXML
    private TextField geboortedatumField;
    @FXML
    private Button registerKnop;
    @FXML
    private Label errorLabel;


    public void registreren(ActionEvent event) throws IOException {
        String voornaam = voornaamFieldR.getText().trim();
        String achternaam = achternaamFieldR.getText().trim();
        String email = emailFieldR.getText().trim();
        String telefoon = telefoonnummerFieldR.getText().trim();
        String adres = adresFieldR.getText().trim();
        String woonplaats = woonplaatsFieldR.getText().trim();
        String wachtwoord1 = wachtwoordFieldR.getText();
        String wachtwoord2 = wachtwoordFieldR2.getText();
        String geboortedatumStr = geboortedatumField.getText().trim();

        // Check of alle velden zijn ingevuld
        if (voornaam.isEmpty() || achternaam.isEmpty() || email.isEmpty() || telefoon.isEmpty()
                || adres.isEmpty() || woonplaats.isEmpty() || wachtwoord1.isEmpty()
                || wachtwoord2.isEmpty() || geboortedatumStr.isEmpty()) {
            errorLabel.setText("Vul alle velden in a.u.b");
            return;
        }


        if (!wachtwoord1.equals(wachtwoord2)) {
            errorLabel.setText("Wachtwoorden komen niet overeen.");
            return;
        }


        java.sql.Date geboortedatum;
        try {
            geboortedatum = java.sql.Date.valueOf(geboortedatumStr); // Verwacht formaat: yyyy-MM-dd
        } catch (IllegalArgumentException e) {
            errorLabel.setText("Ongeldige geboortedatum. Gebruik het formaat yyyy-MM-dd.");
            return;
        }


        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO client (Voornaam, Achternaam, email, telnr, wachtwoord, niveau, adres, Woonplaats, notities, geboortedatum, Beheerder_idBeheerder) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, voornaam);
                stmt.setString(2, achternaam);
                stmt.setString(3, email);
                stmt.setString(4, telefoon);
                stmt.setString(5, wachtwoord1); // In echte app: eerst hashen!
                stmt.setInt(6, 1); // niveau, bv. standaard op 1
                stmt.setString(7, adres);
                stmt.setString(8, woonplaats);
                stmt.setString(9, ""); // notities leeg
                stmt.setDate(10, geboortedatum);
                stmt.setInt(11, 1);

                stmt.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Parent root = FXMLLoader.load(getClass().getResource("/clientLogin.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}

