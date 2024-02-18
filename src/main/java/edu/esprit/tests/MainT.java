package edu.esprit.tests;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.entities.Tache;
import edu.esprit.services.EtatTache;
import edu.esprit.services.ServiceCommentaireTache;
import edu.esprit.services.ServiceTache;
import edu.esprit.utils.DataSource;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainT {
    public static void main(String[] args) {
        ServiceTache st = new ServiceTache();
        ServiceCommentaireTache sct = new ServiceCommentaireTache();
        try {
            DataSource ds = new DataSource();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            // Ajout d'une tâche
            Tache nouvelleTache = new Tache("2029", "Titre de la tâche", "fichier.txt", new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-12 12:00"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-20 18:00"), "description", EtatTache.TO_DO, 14);
            if (st.isValidT(nouvelleTache)) {
                st.ajouter(nouvelleTache);
                System.out.println(st.getAll());
            }

            // Modification d'une tâche
            Tache tacheModifiee = new Tache(24, "100000000", "Titre de la tâche 02", "fichier", new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-18 20:00"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-20 18:00"), "description", EtatTache.TO_DO, 14);
            if (st.isValidT(tacheModifiee)) {
                st.modifier(tacheModifiee);
                System.out.println(st.getAll());
            }

            st.supprimer(21);
            System.out.println(st.getAll());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        // COMMENTAIRE :
        try {
            // Ajout d'un commentaire
            CommentaireTache nouveauCommentaire = new CommentaireTache(14, 18, new Date(), "555");
            if (sct.isValidC(nouveauCommentaire)) {
                sct.ajouter(nouveauCommentaire);
                System.out.println(sct.getAll());
            }

            // Modification d'un commentaire
            CommentaireTache commentaireModifie = new CommentaireTache(25, "9999999");
            if (sct.isValidC(commentaireModifie)) {
                sct.modifier(commentaireModifie);
                System.out.println(sct.getAll());
            }

            sct.supprimer(14);
            System.out.println(sct.getAll());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
}
}
