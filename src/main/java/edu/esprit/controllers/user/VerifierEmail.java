package edu.esprit.controllers.user;

import edu.esprit.entities.EndUser;
import edu.esprit.services.GMailer;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.security.SecureRandom;

public class VerifierEmail {

    @FXML
    private TextField OTPField;

    ServiceUser serviceUser = new ServiceUser();

    private static final int OTP_LENGTH = 6;

    String otp;

    EndUser user;

    public void setData(String otp, EndUser user) {
        this.otp = otp;
        this.user = user;
    }

    @FXML
    void VerifierButton(ActionEvent event) {
        if(OTPField.getText().equals(otp)){
            try {
                serviceUser.ajouter(user);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/UserAccount.fxml"));
                Parent root = loader.load();

                // Show the scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Oubliez mot de passe");
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println(otp);
            showAlert("Veuillez vérifier votre code");
        }
    }

    @FXML
    void reEnvoyerButton(ActionEvent event) throws Exception {
        otp = generateOTP();
        String content = String.format("""
                Dear reader,
                
                Your OTP : %s .
                
                Best regards,
                Baladity.
                """,otp);
        new GMailer(user.getEmail()).sendMail("Récupération du mot de passe", content);
    }

    public static String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    private void showAlert(String message) {
        // Affiche une boîte de dialogue d'information avec le message donné
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
