package edu.esprit.tests;

import edu.esprit.entities.Actualite;
import edu.esprit.entities.Publicite;
import edu.esprit.services.ServiceActualite;
import edu.esprit.services.ServicePublicite;
import edu.esprit.utils.DataSource;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ServicePublicite sp1 = new ServicePublicite();
        ServiceActualite sp2 = new ServiceActualite();
       sp1.ajouter(new Publicite("dd","aa",1263863963,"aa","aaa",12,3));
        //sp1.modifier(new Publicite(, "amine", "yahyaoui", 987654, "la petite ariana","a",1));
        //sp1.supprimer(451);
       // System.out.println(sp.getAll());
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
       // sp2.ajouter(new Actualite("ss", "a", sqlDate, "ee"));
    }
}
