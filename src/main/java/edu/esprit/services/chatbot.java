package edu.esprit.services;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class chatbot {

    private String ta = "";
    private Map<String, String> questionResponseMap = new HashMap<>();

    public chatbot() {
        loadResponsesFromExcel();
    }

    public String repondre(String message) {
        try {
            return chatTest(message, "");
        } catch (IOException e) {
            e.printStackTrace();
            return "Une erreur s'est produite lors du traitement de la demande.";
        }
    }

    private void loadResponsesFromExcel() {
        try {
            FileInputStream file = new FileInputStream("src/main/resources/datasets/dataset.xlsx");
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell questionCell = row.getCell(0);
                Cell responseCell = row.getCell(1);

                if (questionCell != null && responseCell != null) {
                    String question = questionCell.getStringCellValue();
                    String response = responseCell.getStringCellValue();

                    questionResponseMap.put(question.toLowerCase(), response);
                }
            }

            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception de manière appropriée en fonction de votre application
        }
    }

    public String chatTest(String tf, String ta) throws IOException {
        this.ta = ta;
        this.ta = "";

        String userInput = tf.trim().toLowerCase();

        // Rechercher la question la plus similaire dans le dataset
        String mostSimilarQuestion = findMostSimilarQuestion(userInput);

        // Si une question similaire est trouvée et la similarité est supérieure à un certain seuil
        if (!mostSimilarQuestion.isEmpty()) {
            // Obtenir la réponse correspondante
            String response = questionResponseMap.getOrDefault(mostSimilarQuestion, "Je suis désolé, je ne comprends pas.");
            addText("\n--> baladity: " + response);
        } else {
            // Si aucune question similaire n'est trouvée, répondre par défaut
            addText("\n--> baladity: Je suis désolé, je ne comprends pas.");
        }

        return this.ta;
    }

    // Méthode pour trouver la question la plus similaire en utilisant la distance de Levenshtein
    private String findMostSimilarQuestion(String userInput) {
        String mostSimilarQuestion = "";
        int minDistance = Integer.MAX_VALUE;

        // Parcourir toutes les questions dans le dataset
        for (String question : questionResponseMap.keySet()) {
            // Calculer la distance de Levenshtein entre la question saisie par l'utilisateur et la question actuelle du dataset
            int distance = LevenshteinDistance.getDefaultInstance().apply(userInput, question);
            // Mettre à jour la question la plus similaire si la distance est minimale
            if (distance < minDistance) {
                minDistance = distance;
                mostSimilarQuestion = question;
            }
        }

        return mostSimilarQuestion;
    }

    public void addText(String str) {
        ta = ta + str;
    }

    public String firstText() {
        addText("-->baladity: Good day! Welcome to Baladity, How can we assist you today? \n");
        return this.ta;
    }
}
