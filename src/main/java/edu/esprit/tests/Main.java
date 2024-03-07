package edu.esprit.tests;

import edu.esprit.entities.Equipement;
import edu.esprit.services.ServiceAvis;
import edu.esprit.services.ServiceEquipement;

import java.util.Date;

public class Main {
    public static void main(String[] args) {

        Muni muni = new Muni(1);
        EndUser user = new EndUser(1,muni);
        ServiceEquipement se = new ServiceEquipement();
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
        Equipement equipement = new Equipement (21);

        //se.ajouter(new Equipement("THIRD","tracteur","MOBILE",sqlDate,3,"","DESC",user,muni));
        //Equipement equipement = new Equipement(20,"RTYUJ","vide","mobile",sqlDate,10,"","cet equipement est nouveau",user,muni);
        //se.modifier(equipement);
        //se.supprimer(22);
        //System.out.println(se.getAll());
        //System.out.println(se.getOneByID(21));
        ServiceAvis sa = new ServiceAvis();
        //sa.ajouter(new Avis(equipement,user,muni,20,"hhhhhhhhh",sqlDate));
        //System.out.println(sa.getAll());
        //System.out.println(sa.getOneByID(12));
        //Avis avis= new Avis(12,user,equipement,muni,20,"hihihii",sqlDate);
        //sa.modifier(avis);
        //sa.supprimer(12);
    }
}





