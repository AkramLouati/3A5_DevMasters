package edu.esprit.tests;

import edu.esprit.entities.Avis;
import edu.esprit.entities.Equipement;
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
        //ServiceAvis sr = new ServiceAvis();
        //sr.ajouter(new Avis(1,12,"hhhhhhhhh",new Date()));
        ServiceEquipement se = new ServiceEquipement();
        se.ajouter(new Equipement("aaaaa","bala","bbbb",15,"ccccc","dddddd",1));
    }
    }






