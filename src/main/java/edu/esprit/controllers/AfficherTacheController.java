package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import edu.esprit.entities.Tache;
import edu.esprit.services.ServiceTache;

import java.util.Set;

public class AfficherTacheController {

    @FXML
    private ListView<Tache> listView;
    private ServiceTache serviceTache;

    public AfficherTacheController() {
        this.serviceTache = new ServiceTache();
    }

    @FXML
    public void initialize() {
        afficherToutesTaches();
    }

    public void afficherToutesTaches() {
        Set<Tache> taches = serviceTache.getAll();
        listView.getItems().clear();
        listView.getItems().addAll(taches);
    }
}