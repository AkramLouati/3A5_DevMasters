package edu.esprit.controllers;

import edu.esprit.entities.EndUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserItem implements Initializable {

    @FXML
    private Label email;

    @FXML
    private ImageView img;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label type;

    public void setData(EndUser user){
//        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(user.getImage())));
//        img.setImage(image);
        String imageUrl = user.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                // Créer une instance de File à partir du chemin d'accès à l'image
                File file = new File(imageUrl);
                // Convertir le chemin de fichier en URL
                String fileUrl = file.toURI().toURL().toString();
                // Créer une instance d'Image à partir de l'URL de fichier
                Image image = new Image(fileUrl);
                // Définir l'image dans l'ImageView
                img.setImage(image);
            } catch (MalformedURLException e) {
                // Gérer l'exception si le chemin d'accès à l'image n'est pas valide
                e.printStackTrace();
            }
        } else {
            // Si l'URL de l'image est vide ou null, afficher une image par défaut
            // Par exemple, si vous avez une image "imageblanche.png" dans votre dossier src/main/resources
            // Vous pouvez utiliser getClass().getResource() pour obtenir son URL
            URL defaultImageUrl = getClass().getResource("/assets/man.png");
            if (defaultImageUrl != null) {
                Image defaultImage = new Image(defaultImageUrl.toString());
                img.setImage(defaultImage);
            } else {
                System.err.println("L'image par défaut n'a pas été trouvée !");
            }
        }

//        if (imagePath != null && !imagePath.isEmpty()) {
//            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(user.getImage())));
//            img.setImage(image);
//        } else {
//            // Provide a default image or handle the case where the image path is empty
//            // For example, you can set a placeholder image
//            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/man.png")));
//            img.setImage(image);
//        }

        name.setText(user.getNom());
        phone.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        type.setText(user.getType());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
