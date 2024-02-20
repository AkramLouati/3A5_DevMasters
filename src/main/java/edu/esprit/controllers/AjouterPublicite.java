package edu.esprit.controllers;
import com.sun.javafx.charts.Legend;
import edu.esprit.entities.Actualite;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import edu.esprit.entities.Publicite;
import edu.esprit.services.ServiceActualite;
import edu.esprit.services.ServicePublicite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;
import java.io.IOException;
import java.sql.SQLException;
public class AjouterPublicite {
    @FXML
    private TextField TFtitrepub;
    @FXML
    private TextField TFdescriptionpub;
    @FXML
    private TextField TFlocalisationpub;
    @FXML
    private TextField TFcontactpub;
    private final ServicePublicite sp = new ServicePublicite();
    @FXML
    void ajouterActualiteAction(ActionEvent event) {
        Muni muni = new Muni(1);
        EndUser user = new EndUser(12,muni);
        Actualite actualite = new Actualite(27,muni);

        sp.ajouter(new Publicite(TFtitrepub.getText(),TFdescriptionpub.getText(), Integer.parseInt(TFcontactpub.getText()),TFlocalisationpub.getText(),"dvd",user,actualite));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("GG");
        alert.show();

    }
}
