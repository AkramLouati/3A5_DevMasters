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

public class MainT {
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

            // Clear the scanner buffer
            scanner.nextLine();

            // Asking user for task start date
            System.out.println("Entrez la date de début de la tâche (format : yyyy-MM-dd HH:mm) :");
            Date dateDebut = getDateInput(scanner);

            // Asking user for task end date
            System.out.println("Entrez la date de fin de la tâche (format : yyyy-MM-dd HH:mm) :");
            Date dateFin = getDateInput(scanner);

            // Asking user for task state
            EtatTache etatInput = null;
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.println("Entrez l'état de la tâche (TO_DO | DOING | DONE) :");
                    etatInput = EtatTache.valueOf(scanner.next());
                    validInput = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Format Invalide. Veuillez saisir l'état correctement.");
                }
            }

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
            serviceTache.ajouter(nouvelleTache);

            // Retrieve all tasks and print them
            System.out.println("All Tasks:");
            serviceTache.getAll().forEach(System.out::println);

            // Example usage with CommentaireTache entity
            System.out.println("Entrer ID Tache a commenter");
            int taskId = scanner.nextInt();

            // Check if the task ID exists
            Tache existingTask = serviceTache.getOneByID(taskId);
            if (existingTask == null) {
                System.out.println("Tache Existe.");
                return; // Exit the program if the task ID does not exist
            }

            System.out.println("Commentaire :");
            scanner.nextLine(); // Consume the newline character left by nextInt()
            String commentText = scanner.nextLine();

            // Create a new comment
            CommentaireTache newComment = new CommentaireTache();
            newComment.setId_user(existingTask.getId_user()); // Use the user ID associated with the task
            newComment.setId_T(taskId); // Specify the task ID
            newComment.setText_C(commentText);
            newComment.setDate_C(new Date());

            // Validating the comment
            try {
                if (!serviceCommentaireTache.isValidC(newComment)) {
                    System.out.println("Le commentaire n'a pas pu être validé. Veuillez corriger les erreurs.");
                    return;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur de validation du commentaire : " + e.getMessage());
                return;
            }

            // Adding the new comment
            if (serviceTache.addComment(newComment)) {
                System.out.println("Commentaire ajouté avec succès.");
            } else {
                System.out.println("Erreur lors de l'ajout du commentaire.");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Date getDateInput(Scanner scanner) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String input = scanner.nextLine();
        try {
            return dateFormat.parse(input);
        } catch (ParseException e) {
            System.out.println("Entrez à nouveau la date (format : yyyy-MM-dd HH:mm) :");
            return getDateInput(scanner);
        }
    }
}