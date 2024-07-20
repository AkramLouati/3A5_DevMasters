package edu.esprit.services;

import java.util.Set;

public interface IService<T> {
    void ajouter(T t);

    void modifier(T t);

    void supprimer(int id);

    Set<T> getAll();

    T getOneByID(int id);
}