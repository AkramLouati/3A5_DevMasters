package edu.esprit.tests;

import edu.esprit.entities.Avis;
import edu.esprit.entities.EndUser;
import edu.esprit.entities.Equipement;
import edu.esprit.entities.Muni;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServiceEquipement;
import edu.esprit.utils.DataSource;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Muni muni = new Muni(1);
        EndUser user = new EndUser(1,muni);
        ServiceEquipement se = new ServiceEquipement();
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

        //se.ajouter(new Equipement("aaaaa","bala","bbbb","aaaaa",15,"ccccc","dddddd",user));
        Equipement equipement = new Equipement(1,"RTYUJ","vide","mobile",sqlDate,10,"","cet equipement est nouveau",user,muni);
        se.modifier(equipement);
        //se.supprimer(1);
        //System.out.println(se.getAll());
        //System.out.println(se.getOneByID(2));
        ServiceAvis sr = new ServiceAvis();
        //sr.ajouter(new Avis(user,equipement,20,"hhhhhhhhh",new Date()));
        //System.out.println(sr.getAll());
        //System.out.println(sr.getOneByID(3));
        //Avis avis= new Avis(3,user,equipement,20,"hihihii",new Date());
        //sr.modifier(avis);
        //sr.supprimer(3);
    }
}





