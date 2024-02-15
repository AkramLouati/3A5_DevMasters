package edu.esprit.services;

import edu.esprit.entities.CommentaireTache;

import java.util.Set;

public interface IGTache<T> {
    public boolean ajouter(T t);

    public boolean addComment(T t);

    public void modifier(T t);

    public void supprimer(int id);

    public Set<T> getAll();

    public T getOneByID(int id);
    public  boolean isValid(T t);
}
