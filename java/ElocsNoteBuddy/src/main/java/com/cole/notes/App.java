package com.cole.notes;
import java.util.Scanner;


public class App {

    public static void main( String[] args ) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
    
        System.out.println( "Welcome to Eloc's Note Buddy!" );
        System.out.println("=============================");

        while (running) {
            displayMenu();

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
        System.out.println("Read existing note            ---> Enter 2");
        System.out.println("Update existing note          ---> Enter 3");
        System.out.println("Delete existing note          ---> Enter 4");
        System.out.println("Search notes by tag           ---> Enter 5");
        System.out.println("Search notes by time created  ---> Enter 6");
        System.out.println("Search notes by date modified ---> Enter 7");
        System.out.println("Add tag to existing note      ---> Enter 8");
        System.out.println("Tell me a joke                ---> Enter 9");
        System.out.println("Quit                          ---> Enter 0");

    }

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

            FileService fileService = new FileService();
            String filename = fileService.saveNote(note);

            System.out.println("\n Note created!");
            System.out.println("Saved as: " + filename);

        } catch (Exception e) {
            System.out.println("\nError creating note: " + e.getMessage());
        }
    }
}
    

