package edu.esprit.tests;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.entities.Tache;
import edu.esprit.services.EtatTache;
import edu.esprit.services.serviceCommentaireTache;
import edu.esprit.services.serviceTache;
import edu.esprit.utils.DataSource;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainT {
    public static void main(String[] args) {
        try {
            DataSource ds = new DataSource();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
/*
        serviceTache st = new serviceTache();
        try {
            st.ajouter(new Tache("2029", "Task Title", "file.txt", new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-18 12:00"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-20 18:00"), "desc",EtatTache.TO_DO, 14));

            System.out.println(st.getAll());

            st.modifier(new Tache(24, "100000000", "Task Title 02", "file", new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-18 12:00"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-20 18:00"), "desc", EtatTache.TO_DO, 14));

            st.supprimer(21);

            System.out.println(st.getAll());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
*/
        //COMMENTAIRE:
        serviceCommentaireTache sct = new serviceCommentaireTache();

        try {

            System.out.println(sct.getAll());
            /*
            sct.ajouter(new CommentaireTache(14, 18, new Date(), "555"));
            System.out.println(sct.getAll());
            sct.modifier(new CommentaireTache(8, "9999999"));
            System.out.println(sct.getAll());
            sct.supprimer(10);
            System.out.println(sct.getAll());
            */
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
