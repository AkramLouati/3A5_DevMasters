package edu.esprit.tests;

import edu.esprit.entities.Publicite;
import edu.esprit.services.ServicePublicite;
import edu.esprit.utils.DataSource;

import javax.xml.crypto.Data;

public class Main {
    public static void main(String[] args) {
        DataSource ds= new DataSource();
        ServicePublicite sp = new ServicePublicite();
        sp.ajouter(new Publicite("dd","aa",123,"aa","aaaaaaaaaaaa",10));
    }
}
