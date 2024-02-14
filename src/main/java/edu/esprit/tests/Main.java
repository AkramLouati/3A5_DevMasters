package edu.esprit.tests;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.entities.Tache;
import edu.esprit.services.serviceTache;
import edu.esprit.utils.DataSource;

import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Instantiate DataSource to establish a connection
        DataSource ds = new DataSource();

        // Example usage with Tache entity
        serviceTache serviceTache = new serviceTache();

        // Create a new task
        Tache newTache = new Tache();
        newTache.setCategorie_T("Category");
        newTache.setTitre_T("New Task");
        newTache.setPieceJointe_T("file.txt");
        newTache.setDate_DT(new Date());
        newTache.setDate_FT(new Date());
        newTache.setDesc_T("Description of the new task");
        newTache.setEtat_T(false);
        newTache.setId_user(14); // Assuming the user ID

        // Add the new task
        if (serviceTache.ajouter(newTache)) {
            System.out.println("Task added successfully.");
        } else {
            System.out.println("Error adding task.");
            return; // Exit the program if adding the task fails
        }

        // Retrieve all tasks and print them
        System.out.println("All Tasks:");
        serviceTache.getAll().forEach(System.out::println);

        // Example usage with CommentaireTache entity
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the task to add a comment to:");
        int taskId = scanner.nextInt();

        // Check if the task ID exists
        Tache existingTask = serviceTache.getOneByID(taskId);
        if (existingTask == null) {
            System.out.println("Task with the given ID does not exist.");
            return; // Exit the program if the task ID does not exist
        }

        System.out.println("Enter your comment:");
        scanner.nextLine(); // Consume the newline character left by nextInt()
        String commentText = scanner.nextLine();

        // Create a new comment
        CommentaireTache newComment = new CommentaireTache();
        newComment.setId_user(existingTask.getId_user()); // Use the user ID associated with the task
        newComment.setId_T(taskId); // Specify the task ID
        newComment.setText_C(commentText);
        newComment.setDate_C(new Date());
        newComment.setDate_FT(new Date());

        // Add the comment to the task
        if (serviceTache.addComment(newComment)) {
            System.out.println("Comment added successfully.");
        } else {
            System.out.println("Error adding comment.");
        }
    }
}
