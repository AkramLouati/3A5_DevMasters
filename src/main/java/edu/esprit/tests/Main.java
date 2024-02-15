package edu.esprit.tests;

import edu.esprit.entities.Messagerie;
import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceMessagerie;
import edu.esprit.services.ServiceReclamation;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ServiceReclamation sr = new ServiceReclamation();
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
        //sr.ajouter(new Reclamation(12, 5, sqlDate, "non urgent", "test2", "test2", "tes2"));
        //Reclamation reclamation = new Reclamation(3, 12, 5, sqlDate, "non urgent", "test0", "traité", "test0", "tes0");
        //sr.modifier(reclamation);
        //sr.supprimer(3);
        //System.out.println(sr.getOneByID(4));
        ServiceMessagerie sm = new ServiceMessagerie();
        //sm.ajouter(new Messagerie(sqlDate,"aaaaaa",12,13,"test"));
        //Messagerie messagerie= new Messagerie(1,sqlDate,"bbbbbb",12,13,"test1");
        //sm.modifier(messagerie);
        //sm.supprimer(1);
        //System.out.println(sm.getAll());
        //System.out.println(sm.getOneByID(2));
    }
}
