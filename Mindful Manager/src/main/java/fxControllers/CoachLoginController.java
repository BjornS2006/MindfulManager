package fxControllers;

import domein.Beheerder;
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
import utils.BeheerderSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoachLoginController {
    @FXML
    private TextField coachEmailField;
    @FXML
    private PasswordField coachWachtwoordField;
    @FXML
    private Button coachInlogKnop;
    @FXML
    private Label errorLabel;

    public void coachInloggen (ActionEvent event) throws IOException {
        String coachEmail = coachEmailField.getText();
        String coachWachtwoord = coachWachtwoordField.getText();
        if (isLoginValid(coachEmail, coachWachtwoord)) {
            Parent root = FXMLLoader.load(getClass().getResource("/coachHome.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        else {
            errorLabel.setText("Onjuist email adres of wachtwoord.");
        }
    }

    private boolean isLoginValid(String coachEmail, String coachWachtwoord) {
        boolean isValid = false;
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM beheerder WHERE email = ? AND wachtwoord = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, coachEmail);
            statement.setString(2, coachWachtwoord);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Beheerder actieveBeheerder = new Beheerder(resultSet.getString("Voornaam"), resultSet.getString("Achternaam"), resultSet.getString("email"), resultSet.getString("telnr"), resultSet.getString("wachtwoord"));
                BeheerderSession.setActieveBeheerder(actieveBeheerder);
                isValid = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isValid;
    }

}
