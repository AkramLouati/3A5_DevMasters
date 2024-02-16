package edu.esprit.tests;

import edu.esprit.entities.Tache;
import edu.esprit.services.EtatTache;
import edu.esprit.services.serviceTache;
import edu.esprit.utils.DataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainT {
    public static void main(String[] args) {
        serviceTache st = new serviceTache();
        try {
            DataSource ds = new DataSource();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
/*
        try {
            st.ajouter(new Tache("2029", "Task Title", "file.txt", new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-18 12:00"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-20 18:00"), "desc",EtatTache.TO_DO, 14));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
*/
        try {
            st.modifier(new Tache(24, "100000000", "Task Title 02", "file", new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-18 12:00"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2024-02-20 18:00"), "desc", EtatTache.TO_DO, 14));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

    }
}//uwu
