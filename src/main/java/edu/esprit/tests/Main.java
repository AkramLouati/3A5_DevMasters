package edu.esprit.tests;

import edu.esprit.entities.EndUser;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceMessagerie;
import edu.esprit.services.ServiceReclamation;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ServiceReclamation sr = new ServiceReclamation();
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
        //Muni muni = new Muni(5);
       // EndUser user = new EndUser(12,muni);
       // EndUser user1 = new EndUser(13,muni);
        //sr.ajouter(new Reclamation(user,muni,"aaaa",sqlDate,"urgent","bababba","","test0"));
        //Reclamation reclamation = new Reclamation(21, user, muni,"bbbbb", sqlDate, "non urgent", "test1", "image.png", "tes0");
        //sr.modifier(reclamation);
        //sr.supprimer(21);
        //System.out.println(sr.getAll());
        //System.out.println(sr.getOneByID(23));
        ServiceMessagerie sm = new ServiceMessagerie();
        //sm.ajouter(new Messagerie(sqlDate,"hihihihi",user,user1,"test"));
        //Messagerie messagerie= new Messagerie(5,sqlDate,"mrigl",user1,user,"test1");
        //sm.modifier(messagerie);
        //sm.supprimer(5);
        //System.out.println(sm.getAll());
        //System.out.println(sm.getOneByID(4));
        System.out.println(sm.getAllMessagesByReciverAndSender(37,36));;

    }
}
