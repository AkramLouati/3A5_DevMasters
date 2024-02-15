package edu.esprit.tests;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ServiceReclamation sr = new ServiceReclamation();
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
        //sr.ajouter(new Reclamation(2, 12, 5, sqlDate, "non urgent", "test0", "test0", "test0", "tes0"));
        //Reclamation reclamation = new Reclamation(2, 12, 5, sqlDate, "non urgent", "test0", "test0", "test0", "tes0");
        //sr.modifier(reclamation);
        //sr.supprimer(2);
        //System.out.println(sr.getOneByID(3));
    }
}
