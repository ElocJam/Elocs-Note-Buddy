package com.cole.notes;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Random; 
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;


public class App {

    private static Process musicProcess = null;

    public static void main( String[] args ) {                 // **************** NOTE BUDDY'S BEATING HEART

    String PURPLE = "\u001B[35m";
    System.out.print(PURPLE);

        Scanner scanner = new Scanner(System.in);                           // start the scanner
        boolean running = true;                                             // create my running boolean
    
        System.out.println();
        System.out.println("░        ░░  ░░░░░░░░░      ░░░░      ░░░░      ░░░░░░░░░   ░░░  ░░░      ░░░        ░░        ░░░░░░░░       ░░░  ░░░░  ░░       ░░░       ░░░  ░░░░  ░");
        System.out.println("▒  ▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒  ▒▒▒▒  ▒▒  ▒▒▒▒  ▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒    ▒▒  ▒▒  ▒▒▒▒  ▒▒▒▒▒  ▒▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒  ▒▒▒▒  ▒▒  ▒▒▒▒  ▒▒  ▒▒▒▒  ▒▒  ▒▒▒▒  ▒▒▒  ▒▒  ▒▒");
        System.out.println("▓      ▓▓▓▓  ▓▓▓▓▓▓▓▓  ▓▓▓▓  ▓▓  ▓▓▓▓▓▓▓▓▓      ▓▓▓▓▓▓▓▓▓  ▓  ▓  ▓▓  ▓▓▓▓  ▓▓▓▓▓  ▓▓▓▓▓      ▓▓▓▓▓▓▓▓▓▓       ▓▓▓  ▓▓▓▓  ▓▓  ▓▓▓▓  ▓▓  ▓▓▓▓  ▓▓▓▓    ▓▓▓");
        System.out.println("█  ████████  ████████  ████  ██  ████  ████████  ████████  ██    ██  ████  █████  █████  ██████████████  ████  ██  ████  ██  ████  ██  ████  █████  ████");
        System.out.println("█        ██        ███      ████      ████      █████████  ███   ███      ██████  █████        ████████       ████      ███       ███       ██████  ████");
        System.out.println();                                                                                                                                    

        while (running) {                                        // start my app loop
            displayMenu();                                       // *************** MENU OPTIONS

            System.out.print("\nEnter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    createNewNote(scanner);
                    break;
                case "0":
                    stopMusic();
                    System.out.println();
                    System.out.println("░  ░░░░  ░░        ░░░      ░░░░      ░░░  ░░░░  ░░        ░░        ░░        ░░░░░░░░  ░░░░  ░░░      ░░░   ░░░  ░░░      ░░░░      ░░░        ░░       ░░");
                    System.out.println("▒   ▒▒   ▒▒▒▒▒  ▒▒▒▒▒  ▒▒▒▒▒▒▒▒  ▒▒▒▒  ▒▒  ▒▒▒▒  ▒▒▒▒▒  ▒▒▒▒▒  ▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒   ▒▒   ▒▒  ▒▒▒▒  ▒▒    ▒▒  ▒▒  ▒▒▒▒  ▒▒  ▒▒▒▒▒▒▒▒  ▒▒▒▒▒▒▒▒  ▒▒▒▒  ▒");
                    System.out.println("▓        ▓▓▓▓▓  ▓▓▓▓▓▓      ▓▓▓  ▓▓▓▓▓▓▓▓        ▓▓▓▓▓  ▓▓▓▓▓      ▓▓▓▓      ▓▓▓▓▓▓▓▓▓▓        ▓▓  ▓▓▓▓  ▓▓  ▓  ▓  ▓▓  ▓▓▓▓  ▓▓  ▓▓▓   ▓▓      ▓▓▓▓  ▓▓▓▓  ▓");
                    System.out.println("█  █  █  █████  ███████████  ██  ████  ██  ████  █████  █████  ████████  ██████████████  █  █  ██        ██  ██    ██        ██  ████  ██  ████████  ████  █");
                    System.out.println("█  ████  ██        ███      ████      ███  ████  ██        ██        ██  ██████████████  ████  ██  ████  ██  ███   ██  ████  ███      ███        ██       ██");
                    System.out.println();
                    running = false;
                    break;
                case "2":
                    displayNotes(scanner);
                    break;
                case "3":
                    editNote(scanner);
                    break;
                case "4":
                    deleteNote(scanner);
                    break;
                case "5":
                    searchNotes(scanner);
                    break;
                case "6":
                    filterByTag(scanner);
                    break;
                case "7":
                    listByCreated(scanner);
                case "8":
                    playGame(scanner);
                    break;
                case "9":
                    tellJoke();
                    break;
                case "10":
                    aquarium(scanner);
                    break;
                case "11":
                    playMusic(scanner);
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void displayMenu() {                                // ******************** THE MENU

        String BOLD = "\u001B[1m";
        String UNBOLD = "\u001B[22m";

        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║     " + BOLD + "WHAT WOULD YOU LIKE TO DO?" + UNBOLD + "        ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("Create new note               ---> Enter 1");
        System.out.println("List all notes                ---> Enter 2");
        System.out.println("Edit existing note            ---> Enter 3");
        System.out.println("Delete existing note          ---> Enter 4");
        System.out.println("Search notes by keyword       ---> Enter 5");
        System.out.println("Filter notes by tag           ---> Enter 6");
        System.out.println("List notes by creation date   ---> Enter 7");
        System.out.println("Need a break? Play a game!    ---> Enter 8");
        System.out.println("Need a laugh? Tell me a joke! ---> Enter 9");
        System.out.println("Stressed out? Take a dip!     ---> Enter 10");
        System.out.println("Active lock in playlist       ---> Enter 11");
        System.out.println("Quit                          ---> Enter 0");

    }
                                                                        
    private static void createNewNote(Scanner scanner) {            // ************************* CREATE NOTE
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
                System.out.print("\nEnter note number to read (or 0 to cancel): ");
                String input = scanner.nextLine().trim();

                if (input.equals("0") || input.isEmpty()) {
                    return;
                }

                try {
                    int choice = Integer.parseInt(input);

                    if (choice < 1 || choice > notes.size()) {
                        System.out.println("Invalid note number!");
                        return;
                    }
                    String filename = notes.get(choice - 1);
                    readNote(fileService, filename, scanner);

                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number!");
                }
            } catch (Exception e) {
                System.out.println("Error listing notes: " + e.getMessage());
            }
        }

    private static void readNote(FileService fileService, String filename, Scanner scanner) {           // ************** READ NOTES
            try {
                Note note = fileService.loadNote(filename);

                System.out.println("\n" + "=".repeat(50));
                System.out.println(note.getTitle());
                System.out.println("=".repeat(50));
                System.out.println();
                System.out.println("Created: " + note.getCreated());
                System.out.println("Modified: " + note.getModified());

                if (!note.getTags().isEmpty()) {
                    System.out.print("Tags: ");
                    for (int i = 0; i < note.getTags().size(); i++) {
                        System.out.print(note.getTags().get(i));
                        if (i < note.getTags().size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("-".repeat(50));
                System.out.println(note.getContent());
                System.out.println("-".repeat(50));

                System.out.print("\nPress Enter to continue. . . ");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error reading note: " + e.getMessage());
            }
        }

    private static void editNote(Scanner scanner) {                             // *************** EDIT NOTES
            System.out.println("\n=== EDIT NOTE ===\n");

            try {
                FileService fileService = new FileService();
                List<String> notes = fileService.listNotes();

                if (notes.isEmpty()) {
                    System.out.println("No notes found, create one first!");
                    return;
                }

                for (int i = 0; i < notes.size(); i++) {
                    System.out.println((i + 1) + ". " + notes.get(i));
                }

                System.out.print("\nEnter note number to edit (or 0 to cancel): ");
                String input = scanner.nextLine().trim();

                if (input.equals("0") || input.isEmpty()) {
                    return;
                }

                int choice = Integer.parseInt(input);
                if (choice < 1 || choice > notes.size()) {
                    System.out.println("Invalid note number!");
                    return;
                }

                String filename = notes.get(choice - 1);
                Note note = fileService.loadNote(filename);
                System.out.println("\nOpening '" + note.getTitle() + "' in nano. . .");
                System.out.println("Edit the content, then press Ctrl+X to save and exit.");
                System.out.print("Press Enter to continue. . .");
                scanner.nextLine();

                String newContent = editInNano(note.getContent());
                if (newContent == null) {
                    System.out.println("Edit cancelled or failed.");
                    return;
                }

                note.setContent(newContent);
                note.updateModifiedTime();
                fileService.updateNote(filename, note);
                System.out.println("\nNote updated successfully!");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            } catch (Exception e) {
                System.out.println("Error editing note: " + e.getMessage());
            }
        }

    private static String editInNano(String content) {                     // ****************** NANO EDITOR
            try {
                Path tempFile = Files.createTempFile("note-edit-", ".md");
                Files.writeString(tempFile, content);

                ProcessBuilder pb = new ProcessBuilder("nano", tempFile.toString());
                pb.inheritIO();
                Process process = pb.start();
                int exitCode = process.waitFor();

                if (exitCode != 0) {
                    System.out.println("Nano exited with error code: " + exitCode);
                    Files.deleteIfExists(tempFile);
                    return null;
                }

                String editedContent = Files.readString(tempFile);
                Files.deleteIfExists(tempFile);
                return editedContent;
            } catch (Exception e) {
                System.out.println("Error opening nano: " + e.getMessage());
                return null;
            }
        }

    private static void deleteNote(Scanner scanner) {                   // ************* DELETE NOTE
        System.out.println("\n=== DELETE NOTE ===\n");

        try {
            FileService fileService = new FileService();
            List<String> notes = fileService.listNotes();

            if (notes.isEmpty()) {
                System.out.println("No notes found.");
                return;
            }

            for (int i = 0; i < notes.size(); i++) {
                System.out.println((i + 1) + ". " + notes.get(i));
            }

            System.out.print("\nEnter note number to delete (or 0 to cancel): ");
            String input = scanner.nextLine().trim();

            if (input.equals("0") || input.isEmpty()) {
                return;
            }

            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > notes.size()) {
                System.out.println("Invalid note number!");
                return;
            }

            String filename = notes.get(choice - 1);
            Note note = fileService.loadNote(filename);
            System.out.print("\nAre you sure you want to delete '" + note.getTitle() + "'? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("yes")) {
                fileService.deleteNote(filename);
                System.out.println("Note deleted successfully!");
            } else {
                System.out.println("\nDeletion cancelled.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
        } catch (Exception e) {
            System.out.println("Error deleting note: " + e.getMessage());
        }
    }

    private static void searchNotes(Scanner scanner) {              // ************** SEARCH NOTE BY KEYWORD METHOD
        System.out.println("\n=== SEARCH NOTES ===\n");
        System.out.print("Enter seach keyword: ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("Search cancelled.");
            return;
        }

        try {
            FileService fileService = new FileService();
            List<Note> results = fileService.searchNotesByKeyword(keyword);

            if (results.isEmpty()) {
                System.out.println("\nNo notes found containing '" + keyword + "'");
                return;
            }

            System.out.println("\nFound " + results.size() + " notes containing '" + keyword + "':\n");
            for (int i = 0; i < results.size(); i++) {
                Note note = results.get(i);
                System.out.println((i + 1) + ". " + note.getTitle());
                System.out.println(" Created: " + note.getCreated());

                if (!note.getTags().isEmpty()) {
                    System.out.print(" Tags: ");
                    for (int j = 0; j < note.getTags().size(); j++) {
                        System.out.print(note.getTags().get(j));
                        if (j < note.getTags().size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
            }
            System.out.print("Enter note number to read (or 0 to cancel): ");
            String input = scanner.nextLine().trim();

            if (!input.equals("0") && !input.isEmpty()) {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice <= results.size()) {
                    displayNoteDetails(results.get(choice - 1), scanner);
                }
            }
        } catch (Exception e) {
            System.out.println("Error searching notes: " + e.getMessage());
        }
    }

    private static void displayNoteDetails(Note note, Scanner scanner) {            // ************** DISPLAY NOTE HELPER METHOD
        System.out.println("\n" + "=".repeat(50));
        System.out.println(note.getTitle());
        System.out.println("=".repeat(50));
        System.out.println();
        System.out.println("Created: " + note.getCreated());
        System.out.println("Modified: " + note.getModified());
        
        if (!note.getTags().isEmpty()) {
            System.out.print("Tags: ");
            for (int i = 0; i < note.getTags().size(); i++) {
                System.out.print(note.getTags().get(i));
                if (i < note.getTags().size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.println("-".repeat(50));
        System.out.println(note.getContent());
        System.out.println("-".repeat(50));
        
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void filterByTag(Scanner scanner) {                              // ************* FILTER BY TAG METHOD
        System.out.println("\n=== FILTER BY TAG ===\n");

        try {
            FileService fileService = new FileService();
            List<String> allTags = fileService.getAllTags();

            if (allTags.isEmpty()) {
                System.out.println("No tags found in notes.");
                return;
            }

            System.out.println("Available tags:");
            for (String tag : allTags) {
                System.out.println("  - " + tag);
            }

            System.out.print("\nEnter tag to filter by: ");
            String tag = scanner.nextLine().trim();

            if (tag.isEmpty()) {
                System.out.println("Filter cancelled.");
                return;
            }

            List<Note> results = fileService.filterNotesByTag(tag);
            if (results.isEmpty()) {
                System.out.println("\nNo notes found with tag '" + tag + "'");
                return;
            }

            System.out.println("\nFound " + results.size() + " notes with tag '" + tag + "':\n"); 
        
            for (int i = 0; i < results.size(); i++) {
                Note note = results.get(i);
                System.out.println((i + 1) + ". " + note.getTitle());
                System.out.println("  Created: " + note.getCreated());
                System.out.println();
            }

            System.out.print("Enter note number to read (or 0 to cancel): ");
            String input = scanner.nextLine().trim();

            if (!input.equals("0") && !input.isEmpty()) {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice <= results.size()) {
                    displayNoteDetails(results.get(choice - 1), scanner);
                }
            }
        } catch (Exception e) {
            System.out.println("Error filtering notes: " + e.getMessage());
        }
    }

    private static void listByCreated(Scanner scanner) {                        // *************** LIST NOTES BY CREATION DATE
        System.out.println("\n=== NOTES BY CREATION DATE ===\n");

        try {
            FileService fileService = new FileService();
            List<Note> notes = fileService.listNotesSortedByCreated();

            if (notes.isEmpty()) {
                System.out.println("No notes found.");
                return;
            }

            for (int i = 0; i < notes.size(); i++) {
                Note note = notes.get(i);
                System.out.println((i + 1) + ". " + note.getTitle());
                System.out.println("  Created: " + note.getCreated());
                System.out.println("  Modified: " + note.getModified());
                System.out.println();
            }

            System.out.println(notes.size() + " notes found (sorted by creation date, newest first).");
            System.out.print("\nEnter note number to read (or 0 to cancel): ");
            String input = scanner.nextLine().trim();

            if (!input.equals("0") && !input.isEmpty()) {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice <= notes.size()) {
                    displayNoteDetails(notes.get(choice - 1), scanner);
                }
            }
        } catch (Exception e) {
            System.out.println("Error listing notes: " + e.getMessage());
        }
    }

    private static void playGame(Scanner scanner) {                                 // **************** PLAY A GAME METHOD

        System.out.println("\n=== TAKE A BREAK AND PLAY A GAME ===\n");
        System.out.println("Launching game menu. . .");
        System.out.println("(Use arrow keys to select, Enter to play, 'q' to quit back to notes)");
        System.out.print("\nPress Enter to continue. . .");

            try {
                scanner.nextLine();

                ProcessBuilder pb = new ProcessBuilder("nbsdgames");
                pb.inheritIO();
                Process process = pb.start();
                process.waitFor();

                System.out.println("\nBack to notes!");
            } catch (Exception e) {
                System.out.println("\nError launching games: " + e.getMessage());
                System.out.println("Make sure nbsdgames is installed: brew install nbsdgames");
            }
    }

    private static void aquarium(Scanner scanner) {                                 // *************** AQUARIUM METHOD
        System.out.println("\n=== TIME TO SWIM WITH THE FISHES ===\n");
        System.out.println("Just keep swimming...just keep swimming...");
        System.out.println("(Press 'q' to exit back to notes)");
        System.out.print("\nPress Enter to start. . .");

            try {
                scanner.nextLine();

                ProcessBuilder pb = new ProcessBuilder("asciiquarium");
                pb.inheritIO();
                Process process = pb.start();
                process.waitFor();

                System.out.println("\nDid you find Nemo? Back to notes!");
            } catch (Exception e) {
                System.out.println("\nError launching aquarium: " + e.getMessage());
                System.out.println("Install with: brew install asciiquarium");
            }
    }


    private static void printStatus(String msg) {                               // *************** CURSOR FIX
                                                            // Save cursor position
        System.out.print("\u001B[s");                   // Save
        System.out.print("\u001B[1A");                   // Move up one line
        System.out.print("\u001B[2K");                  // Clear the line                                      
        System.out.println(msg);
                                                            // Restore cursor to saved position
        System.out.print("\u001B[u");                   // Restore
        System.out.print("Enter your choice: ");        // Reprint the input prompt so the cursor is always at the input line
        System.out.flush();
    }
    
    private static Thread musicThread = null;                                   // **************** JUKEBOX METHOD
    private static boolean keepPlaying = false;

    private static void playMusic(Scanner scanner) {
        System.out.println("\n=== NOTHIN' BEATS THE CLASSICS ===\n");

        if (keepPlaying) {
            System.out.println("Stopping music...");
            keepPlaying = false;

            if (musicProcess != null && musicProcess.isAlive()) {
                musicProcess.destroyForcibly();
            }

            if (musicThread != null && musicThread.isAlive()) {
                musicThread.interrupt();
            }

            return;
        }

        String musicDir = System.getProperty("user.home") + "/Music/notebuddy-playlist/";
        File folder = new File(musicDir);
        File[] mp3Files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));

        if (mp3Files == null || mp3Files.length == 0) {
            System.out.println("No MP3 files found in " + musicDir);
            return;
        }

        keepPlaying = true;
        System.out.println("Starting playlist loop. (Enter option 11 again to stop)");

        musicThread = new Thread(() -> {
            Random rand = new Random();

            while (keepPlaying) {
                File nextSong = mp3Files[rand.nextInt(mp3Files.length)];
                printStatus("Now playing: " + nextSong.getName());

                try {
                    ProcessBuilder pb = new ProcessBuilder("afplay", nextSong.getAbsolutePath());
                    musicProcess = pb.start();

                    musicProcess.waitFor();
                } catch (InterruptedException e) {

                    if (musicProcess != null && musicProcess.isAlive()) {
                        musicProcess.destroyForcibly();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Playback error: " + e.getMessage());
                }
            }
        });

    musicThread.setDaemon(true);
    musicThread.start();
}

    private static void stopMusic() {                                   // *************** KILL THE MUSIC!
        if (keepPlaying) {
            keepPlaying = false;

            if (musicProcess != null && musicProcess.isAlive()) {
                musicProcess.destroyForcibly();
            }

            if (musicThread != null && musicThread.isAlive()) {
                musicThread.interrupt();
            }

            printStatus("Music stopped.");
        }
    }

    private static void tellJoke() {                                      // ******************** THE SACRED COW!!!!!!
            
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

    private static String getRandomCow() {                             // ******************* COW RANDOMIZER
            String[] cows = {"default", "dragon", "stegosaurus", "tux", "vader", "moose", "camel", "beavis.zen", "turkey", "turtle", "llama"};
            Random rand = new Random();
            return cows[rand.nextInt(cows.length)];
        }
    }

    // i finally did it. i am a coder