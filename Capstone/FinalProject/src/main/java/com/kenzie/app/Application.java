package com.kenzie.app;

// import necessary libraries

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static String URL = "https://jservice.kenzie.academy/api/random-clue";
    private static final int NUM_CLUES = 10;
    /* Java Fundamentals Capstone project:
       - Define as many variables, properties, and methods as you decide are necessary to
       solve the program requirements.
       - You are not limited to only the class files included here
       - You must write the HTTP GET call inside the CustomHttpClient.sendGET(String URL) method
         definition provided
       - Your program execution must run from the main() method in Main.java
       - The rest is up to you. Good luck and happy coding!
     */
public static Clues generateClue() throws URISyntaxException, IOException, InterruptedException {

    String json = CustomHttpClient.sendGET(URL);
    ObjectMapper objectMapper = new ObjectMapper();

    return objectMapper.readValue(json, Clues.class);
}
    public static void main(String[] args)  {
        //Write main execution code here
        Scanner scanner = new Scanner(System.in);
        int score = 0;


        for (int i = 0; i < NUM_CLUES; i++) {
            System.out.println("********** Question " + (i + 1) + " **********");

            // Generate a random clue
            Clues clue = null;
            try {
                clue = generateClue();
            } catch (IOException | InterruptedException | URISyntaxException e) {
                System.out.println("Error while generating clue: " + e.getMessage());
                return;
            }

            // Print the category and question
            System.out.println("Category: " + clue.getCategory().getTitle());
            System.out.println("Question: " + clue.getQuestion());

            // Read the user's answer
            String userAnswer = scanner.nextLine();

            // Calculate whether the user's answer is correct
            String correctAnswer = clue.getAnswer().toLowerCase().replaceAll("\\s", "");
            userAnswer = userAnswer.toLowerCase().replaceAll("\\s", "");

            if (userAnswer.equals(correctAnswer)) {
                score++;
                System.out.println("HUZZAH! Your answer is correct.");
            } else {
                System.out.println("Womp womp! That answer was incorrect. The correct answer is \"" + clue.getAnswer() + "\".");
            }

            // Display the score
            System.out.println("Your score is " + score + " out of " + (i + 1) + ".");

            // Add a line break for readability
            System.out.println();
        }

        // Display the final score
        System.out.println("********** Game Over **********");
        System.out.println("Your final score is " + score + " out of " + NUM_CLUES + ".");
    }
    }
