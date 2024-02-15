package edu.esprit.tests;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.entities.Tache;
import edu.esprit.services.EtatTache;
import edu.esprit.services.serviceTache;
import edu.esprit.utils.DataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Instanciation de DataSource pour établir une connexion
        DataSource ds = new DataSource();

        // Exemple d'utilisation avec l'entité Tache
        serviceTache serviceTache = new serviceTache();

        Scanner scanner = new Scanner(System.in);

        // Demander les détails de la tâche à l'utilisateur
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

        // Demander à l'utilisateur la date de début (date_d) de la tâche
        System.out.println("Entrez la date de début de la tâche (format : yyyy-MM-dd) :");
        Date dateDebut = getDateInput(scanner);
        if (dateDebut == null) {
            return; // Sortie si la date de début est invalide
        }

        // Demander à l'utilisateur la date de fin (date_f) de la tâche
        System.out.println("Entrez la date de fin de la tâche (format : yyyy-MM-dd) :");
        Date dateFin = getDateInput(scanner);
        if (dateFin == null) {
            return; // Sortie si la date de fin est invalide
        }

        // Demander à l'utilisateur l'état de la tâche
        System.out.println("Entrez l'état de la tâche (TO_DO,DOING,DONE) :");
        String etatInput = scanner.next();
        EtatTache etat = EtatTache.valueOf(etatInput);

        // Créer une nouvelle tâche avec les entrées de l'utilisateur
        Tache nouvelleTache = new Tache();
        nouvelleTache.setCategorie_T(categorie);
        nouvelleTache.setTitre_T(titre);
        nouvelleTache.setPieceJointe_T(pieceJointe);
        nouvelleTache.setDesc_T(description);
        nouvelleTache.setDate_DT(dateDebut);
        nouvelleTache.setDate_FT(dateFin);
        nouvelleTache.setEtat_T(etat);
        nouvelleTache.setId_user(idUtilisateur);

        // Ajouter la nouvelle tâche
        if (serviceTache.ajouter(nouvelleTache)) {
            System.out.println("Tâche ajoutée avec succès.");
        } else {
            System.out.println("Erreur lors de l'ajout de la tâche.");
            return; // Sortir du programme si l'ajout de la tâche échoue
        }

        // Récupérer toutes les tâches et les afficher
        System.out.println("Toutes les tâches :");
        serviceTache.getAll().forEach(System.out::println);

        // Exemple d'utilisation avec l'entité CommentaireTache
        System.out.println("Entrez l'identifiant de la tâche pour ajouter un commentaire :");
        int idTache = scanner.nextInt();

        // Vérifier si l'identifiant de la tâche existe
        Tache tacheExistante = serviceTache.getOneByID(idTache);
        if (tacheExistante == null) {
            System.out.println("La tâche avec l'identifiant donné n'existe pas.");
            return; // Sortir du programme si l'identifiant de la tâche n'existe pas
        }

        System.out.println("Entrez votre commentaire :");
        scanner.nextLine(); // Consommer le caractère de nouvelle ligne laissé par nextInt()
        String texteCommentaire = scanner.nextLine();

        // Créer un nouveau commentaire
        CommentaireTache nouveauCommentaire = new CommentaireTache();
        nouveauCommentaire.setId_user(tacheExistante.getId_user()); // Utiliser l'identifiant de l'utilisateur associé à la tâche
        nouveauCommentaire.setId_T(idTache); // Spécifier l'identifiant de la tâche
        nouveauCommentaire.setText_C(texteCommentaire);
        nouveauCommentaire.setDate_C(new Date());

        // Ajouter le commentaire à la tâche
        if (serviceTache.addComment(nouveauCommentaire)) {
            System.out.println("Commentaire ajouté avec succès.");
        } else {
            System.out.println("Erreur lors de l'ajout du commentaire.");
        }
    }

    // Method to read date input from the user
    private static Date getDateInput(Scanner scanner) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String input = scanner.next();
        try {
            return dateFormat.parse(input);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use format: yyyy-MM-dd");
            return null;
        }
    }
}
