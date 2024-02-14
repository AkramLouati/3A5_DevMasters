package edu.esprit.tests;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.utils.DataSource;
import java.util.Date;
import javax.xml.crypto.Data;

public class Main {
    public static void main(String[] args) {
        ServiceReclamation sr = new ServiceReclamation();
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
        System.out.println(sr.getAll());
    }
}
