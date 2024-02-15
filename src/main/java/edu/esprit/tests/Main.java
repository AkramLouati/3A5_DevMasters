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
       //sp1.ajouter(new Publicite(5,"si amineeeeee","bdann",1263863963,"aa","aaa",12,3));
       sp1.modifier(new Publicite(464,"aaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbb", 00000, "la petite ariana","afzgzeg",1,24));
       //  sp1.modifier(new Publicite(453,"amine", "yah", 4, "","",12,9));
      //  sp1.supprimer(461);
       System.out.println(sp1.getAll());
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
        sp2.ajouter(new Actualite("yahya", "yahya", sqlDate, "daazza",1));
       //sp2.supprimer(1);
        //System.out.println(sp2.getAll());
    }
}
