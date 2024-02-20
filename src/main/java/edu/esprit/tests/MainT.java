package edu.esprit.tests;

import edu.esprit.entities.*;
import edu.esprit.services.*;
import edu.esprit.utils.DataSource;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainT {
    public static void main(String[] args) {

        Muni muni = new Muni(5);
        ServiceUser serviceUser = new ServiceUser();
        ServiceTache st = new ServiceTache();
        ServiceCommentaireTache sct = new ServiceCommentaireTache();
        ServiceCategorieT scat = new ServiceCategorieT();
        EndUser user01 = serviceUser.getOneByID(14);
        CategorieT categ01 = scat.getOneByID(13);

        try {
            DataSource ds = new DataSource();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
/*
        try {

            // Ajout d'une categorie
            CategorieT cat = new CategorieT("Employee") ;
            scat.ajouter(cat);
               // System.out.println(scat.getAll());


            // Modification d'une categorie
            CategorieT catmodifier = new CategorieT(8,"RH");
            scat.modifier(catmodifier);
               // System.out.println(scat.getAll());


            scat.supprimer(10);
            System.out.println(scat.getAll());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
*/
        try {
            // Ajout d'une tâche
            Tache nouvelleTache = new Tache(categ01, "Titre de la tâche", "fichier.txt", new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-12 12:00"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-20 18:00"), "description", EtatTache.TO_DO, user01);
            if (st.isValidT(nouvelleTache)) {
                st.ajouter(nouvelleTache);
                //System.out.println(st.getAll());
            }

            // Modification d'une tâche
            Tache tacheModifiee = new Tache(59, categ01, "Titre de la tâche 02", "fichier", new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-18 20:00"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-20 18:00"), "description", EtatTache.TO_DO, user01);
            if (st.isValidT(tacheModifiee)) {
                st.modifier(tacheModifiee);
                //System.out.println(st.getAll());
            }

            //st.supprimer(20);
            System.out.println(st.getAll());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        /*
        // COMMENTAIRE :
        try {
            // Ajout d'un commentaire
            CommentaireTache nouveauCommentaire = new CommentaireTache(user01, 2, new Date(), "555");
            if (sct.isValidC(nouveauCommentaire)) {
                sct.ajouter(nouveauCommentaire);
               // System.out.println(sct.getAll());
            }

            // Modification d'un commentaire
            CommentaireTache commentaireModifie = new CommentaireTache(2, "9999999");
            if (sct.isValidC(commentaireModifie)) {
                sct.modifier(commentaireModifie);
                //System.out.println(sct.getAll());
            }

            //sct.supprimer(14);
            System.out.println(sct.getAll());


        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
*/
}
}
