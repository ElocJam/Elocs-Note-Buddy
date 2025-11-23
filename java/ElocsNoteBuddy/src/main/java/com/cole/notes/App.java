package com.cole.notes;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Random; 
import java.util.List;


public class App {

    public static void main( String[] args ) {

        Scanner scanner = new Scanner(System.in);                           // start the scanner
        boolean running = true;                                             // create my running boolean
    
        System.out.println( "Welcome to Eloc's Note Buddy!" );           // print intro
        System.out.println("=============================");

        while (running) {                                                   // start my app loop
            displayMenu();                                                  // call my options menu

            System.out.print("\nEnter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    createNewNote(scanner);
                    break;
                case "0":
                    System.out.println("\nGoodbye!");
                    running = false;
                    break;
                case "2":
                    displayNotes(scanner);
                    break;
                case "9":
                    tellJoke();
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("Create new note               ---> Enter 1");
        System.out.println("List all notes                ---> Enter 2");
        System.out.println("Tell me a joke                ---> Enter 9");
        System.out.println("Quit                          ---> Enter 0");

    }
                                                                        // ************************* CREATE NOTE
    private static void createNewNote(Scanner scanner) {
        System.out.println("\n=== CREATE NEW NOTE ===");

        System.out.println("Enter note title: ");
        String title = scanner.nextLine().trim();

            if (title.isEmpty()) {
                System.out.println("Error: Title cannot be empty!");
                return;
            }

        System.out.println("Enter note content (type 'END' on a new line when done):");
        StringBuilder content = new StringBuilder();

            while (true) {
                String line = scanner.nextLine();
                if (line.equals("END")) {
                    break;
                }
                content.append(line).append("\n");
            }
        
        System.out.print("Enter tags (separated by commas, or press enter to skip): ");
        String tagsInput = scanner.nextLine().trim();

        try {

            Note note = new Note(title, content.toString().trim());

            if (!tagsInput.isEmpty()) {
                String[] tags = tagsInput.split(",");
                for (String tag : tags) {
                    note.addTag(tag.trim());
                }
            }

            FileService fileService = new FileService();                    // created in the main, reused everywhere in code make sure it is instance variable of main
            String filename = fileService.saveNote(note);

            System.out.println("\n Note created!");
            System.out.println("Saved as: " + filename);

        } catch (Exception e) {
            System.out.println("\nError creating note: " + e.getMessage());
        }
    }                                                                       

        private static void displayNotes(Scanner scanner) {                     // ************************* LIST ALL NOTES
            System.out.println("\n=== MY NOTES ===\n");

            try {
                FileService fileService = new FileService();
                List<String> notes = fileService.listNotes();

                if (notes.isEmpty()) {
                    System.out.println("No notes found. Create one first!");
                    return;
                }
                for (int i = 0; i < notes.size(); i++) {
                    System.out.println((i + 1) + ". " + notes.get(i));
                }

                System.out.println("\n" + notes.size() + " notes found.");
            } catch (Exception e) {
                System.out.println("Error listing notes: " + e.getMessage());
            }
        }


        private static void tellJoke() {                                            // ******************** THE SACRED COW!!!!!!
            try {
                ProcessBuilder fortuneBuilder = new ProcessBuilder("fortune");
                Process fortuneProcess = fortuneBuilder.start();

                BufferedReader fortuneReader = new BufferedReader (
                    new InputStreamReader(fortuneProcess.getInputStream())
                );

                StringBuilder fortune = new StringBuilder();
                String line;
                while ((line = fortuneReader.readLine()) != null) {
                    fortune.append(line).append("\n");
                }

                fortuneProcess.waitFor();

                ProcessBuilder cowsayBuilder = new ProcessBuilder("cowsay", "-f", getRandomCow());
                Process cowsayProcess = cowsayBuilder.start();

                OutputStream cowsayInput = cowsayProcess.getOutputStream();
                cowsayInput.write(fortune.toString().getBytes());

                cowsayInput.flush();
                cowsayInput.close();

                BufferedReader cowsayReader = new BufferedReader (
                    new InputStreamReader(cowsayProcess.getInputStream())
                );

                System.out.println();
                while ((line = cowsayReader.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println();

                cowsayProcess.waitFor();

            } catch (Exception e) {
                System.out.println("Moo! Something went wrong: " + e.getMessage());
                System.out.println("Make sure 'fortune' and 'cowsay' are installed!");
            }
        }
        private static String getRandomCow() {
            String[] cows = {"default", "dragon", "stegosaurus", "tux", "vader", "moose", "camel", "beavis.zen", "turkey", "turtle", "llama"};
            Random rand = new Random();
            return cows[rand.nextInt(cows.length)];
        }
    }

    // list all notes, read notes created (next steps)
    // basics by monday
    // how to use nano take note, create temp file, nano edit it, overwrite existing note with the temp
    // make sure i write unit test

    

