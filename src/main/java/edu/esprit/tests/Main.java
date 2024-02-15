package edu.esprit.tests;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.entities.Tache;
import edu.esprit.services.EtatTache;
import edu.esprit.services.serviceCommentaireTache;
import edu.esprit.services.serviceTache;
import edu.esprit.utils.DataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             DataSource ds = new DataSource()) {
            serviceTache serviceTache = new serviceTache();

            // Input from user for task details
            System.out.println("Entrez la catégorie de la tâche :");
            String categorie = scanner.nextLine();

            System.out.println("Entrez le titre de la tâche :");
            String titre = scanner.nextLine();

            System.out.println("Entrez le nom du fichier joint (s'il y en a) :");
            String pieceJointe = scanner.nextLine();

            System.out.println("Entrez la description de la tâche :");
            String description = scanner.nextLine();

            System.out.println("Entrez l'identifiant de l'utilisateur :");
            int idUtilisateur = scanner.nextInt();

            // Asking user for task start date
            System.out.println("Entrez la date de début de la tâche (format : yyyy-MM-dd) :");
            Date dateDebut = getDateInput(scanner);

            // Asking user for task end date
            System.out.println("Entrez la date de fin de la tâche (format : yyyy-MM-dd) :");
            Date dateFin = getDateInput(scanner);

            // Asking user for task state
            System.out.println("Entrez l'état de la tâche (TO_DO | DOING | DONE) :");
            EtatTache etatInput = EtatTache.valueOf(scanner.next());

            // Creating a new task
            Tache nouvelleTache = new Tache();
            nouvelleTache.setCategorie_T(categorie);
            nouvelleTache.setTitre_T(titre);
            nouvelleTache.setPieceJointe_T(pieceJointe);
            nouvelleTache.setDesc_T(description);
            nouvelleTache.setDate_DT(dateDebut);
            nouvelleTache.setDate_FT(dateFin);
            nouvelleTache.setId_user(idUtilisateur);
            nouvelleTache.setEtat_T(etatInput);

            // Validating the task
            try {
                if (!serviceTache.isValidT(nouvelleTache)) {
                    System.out.println("La tâche n'a pas pu être validée. Veuillez corriger les erreurs.");
                    return;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur de validation de la tâche : " + e.getMessage());
                return;
            }

            // Adding the new task
            if (serviceTache.ajouter(nouvelleTache)) {
                System.out.println("Tâche ajoutée avec succès.");
            } else {
                System.out.println("Erreur lors de l'ajout de la tâche.");
                return;
            }

            // Asking user for comment details
            System.out.println("Entrez l'identifiant de l'utilisateur pour le commentaire :");
            int idUtilisateurCommentaire = scanner.nextInt();

            System.out.println("Entrez l'identifiant de la tâche pour le commentaire :");
            int idTacheCommentaire = scanner.nextInt();

            System.out.println("Entrez le texte du commentaire :");
            String texteCommentaire = scanner.nextLine(); // clear buffer
            texteCommentaire = scanner.nextLine(); // take input

            // Creating a new comment
            CommentaireTache nouveauCommentaire = new CommentaireTache();
            nouveauCommentaire.setId_user(idUtilisateurCommentaire);
            nouveauCommentaire.setId_T(idTacheCommentaire);
            nouveauCommentaire.setDate_C(new Date());
            nouveauCommentaire.setText_C(texteCommentaire);

            // Validating the comment
            try {
                if (!serviceCommentaireTache.isValidC(nouveauCommentaire)) {
                    System.out.println("Le commentaire n'a pas pu être validé. Veuillez corriger les erreurs.");
                    return;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur de validation du commentaire : " + e.getMessage());
                return;
            }

            // Adding the new comment
            if (serviceTache.addComment(nouveauCommentaire)) {
                System.out.println("Commentaire ajouté avec succès.");
            } else {
                System.out.println("Erreur lors de l'ajout du commentaire.");
                return;
            }

            // Rest of the code remains the same

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Date getDateInput(Scanner scanner) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String input = scanner.next();
        try {
            return dateFormat.parse(input);
        } catch (ParseException e) {
            System.out.println("Format de date invalide. Veuillez utiliser le format : yyyy-MM-dd");
            System.out.println("Entrez à nouveau la date (format : yyyy-MM-dd) :");
            return getDateInput(scanner); // Recursively call the method until a valid date is entered
        }
    }
}
