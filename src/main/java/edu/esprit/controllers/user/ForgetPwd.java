package edu.esprit.controllers.user;

import edu.esprit.services.GMailer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;

public class ForgetPwd implements Initializable {

    String email;
    private static final int OTP_LENGTH = 6;

    String otp;

    @FXML
    private TextField OTPField;


    public void setData(String email) throws Exception {
        this.email = email;
        otp = generateOTP();
        String content = String.format("""
                Dear reader,

                Your OTP : %s .

                Best regards,
                Fadi
                """,otp);
        new GMailer(email).sendMail("Récupération du mot de passe", content);
    }

    @FXML
    void VerifierOTPButton(ActionEvent event) {

        if(OTPField.getText().equals(otp)){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/modifierPassword.fxml"));
                Parent root = loader.load();

                // Access the controller
                ModifierMdp modifierMdpController = loader.getController();

                // Set the email
                modifierMdpController.setData(email);

                // Show the scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Oubliez mot de passe");
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            showAlert("Veuillez vérifier votre code");
        }
    }

    @FXML
    void reEnvoyerOTP(ActionEvent event) throws Exception {
        otp = generateOTP();
        String content = String.format("""
                Dear reader,
                
                Your OTP : %s .
                
                Best regards,
                Baladity.
                """,otp);
        new GMailer(email).sendMail("Récupération du mot de passe", content);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        System.out.println(email);
//        try {
//            new GMailer( "wertatanifadi@gmail.com").sendMail("My First Message", """
//                    Dear reader,
//
//                    Nshallah yakhtef l mail.
//
//                    Best regards,
//                    Fadi
//                    """);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}
