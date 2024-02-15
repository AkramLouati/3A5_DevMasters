package edu.esprit.tests;

import edu.esprit.entities.CommentaireTache;
import edu.esprit.services.EtatTache;
import edu.esprit.entities.Tache;
import edu.esprit.services.serviceTache;
import edu.esprit.utils.DataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Instantiate DataSource to establish a connection
        DataSource ds = new DataSource();

        // Example usage with Tache entity
        serviceTache serviceTache = new serviceTache();

        Scanner scanner = new Scanner(System.in);

        // Prompt user for task details
        System.out.println("Enter the category of the task:");
        String category = scanner.nextLine();

        System.out.println("Enter the title of the task:");
        String title = scanner.nextLine();

        System.out.println("Enter the attachment file name (if any):");
        String attachment = scanner.nextLine();

        System.out.println("Enter the description of the task:");
        String description = scanner.nextLine();

        System.out.println("Enter the user ID:");
        int userId = scanner.nextInt();

        // Prompt user for the start date (date_d) of the task
        System.out.println("Enter the start date of the task (format: yyyy-MM-dd):");
        Date startDate = getDateInput(scanner);

        // Prompt user for the finish date (date_f) of the task
        System.out.println("Enter the finish date of the task (format: yyyy-MM-dd):");
        Date finishDate = getDateInput(scanner);

        // Prompt user for the state of the task
        System.out.println("Enter the state of the task (TO_DO, DOING, DONE):");
        String stateInput = scanner.next();
        EtatTache state = EtatTache.valueOf(stateInput);

        // Create a new task with user input
        Tache newTache = new Tache();
        newTache.setCategorie_T(category);
        newTache.setTitre_T(title);
        newTache.setPieceJointe_T(attachment);
        newTache.setDesc_T(description);
        newTache.setDate_DT(startDate);
        newTache.setDate_FT(finishDate);
        newTache.setEtat_T(state);
        newTache.setId_user(userId);

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

        // Add the comment to the task
        if (serviceTache.addComment(newComment)) {
            System.out.println("Comment added successfully.");
        } else {
            System.out.println("Error adding comment.");
        }
    }

    // Method to read date input from the user
    private static Date getDateInput(Scanner scanner) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String input = scanner.next();
        try {
            return dateFormat.parse(input);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use format: yyyy-MM-dd");
            return null;
        }
    }
}
