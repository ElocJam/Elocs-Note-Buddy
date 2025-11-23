package com.cole.notes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.DirectoryStream;

public class FileService {
    
    public String generateFilename(String title) {                               // ************ FILENAME CREATOR
        
        String sanitized = title.toLowerCase();                                                     // convert all chars in name to lowercase
        sanitized = sanitized.replaceAll("[^a-z0-9 ]", "");                      // uses regex to keep only letters, numbers, and spaces
        sanitized = sanitized.replaceAll(" ", "-");                             // replaces spaces with dashes
        sanitized = sanitized.replaceAll("-+", "-");                            // removes any consecutive dashes
        sanitized = sanitized.replaceAll("^-|-$", "");                          // removes any leading (^-) or trailing (-$) dashes

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");       // generates a timestamp on the file with the correct format
        String timestamp = now.format(formatter);

        String filename = sanitized + "-" + timestamp + ".md";                                      // adds our sanitized title, our timestamp, and our extension

        return filename;
    }

    public String formatNoteForFile(Note note) {                                  // ************ NOTE FORMATTER
        
        StringBuilder output = new StringBuilder();

            output.append("---\n");                                                                     // creating our YAML header
            output.append("title: ").append(note.getTitle()).append("\n");
            output.append("created: ").append(note.getCreated().toString()).append("\n");       
            output.append("modified: ").append(note.getModified().toString()).append("\n");         // creating our tags, converting to ISO format

            if (!note.getTags().isEmpty()) {
                output.append("tags: [");
                for (int i = 0; i < note.getTags().size(); i++) {
                    output.append(note.getTags().get(i));                                                  // if there are no tags, add them!
                    if (i < note.getTags().size() - 1) {
                        output.append(", ");
                    }
                }
                output.append("]\n");
            }
        output.append("---\n\n");
        output.append(note.getContent());                                   // add content

        return output.toString();
    }

    private void ensureNotesDirectoryExists() throws IOException {            // ************ DOES THE DIRECTORY EXIST METHOD
        Path notesDir = Paths.get(Config.NOTES_DIRECTORY);
        if (!Files.exists(notesDir)) {
            System.out.println("Creating notes directory: " + notesDir);
            Files.createDirectories(notesDir);
        }
    }

    public String saveNote(Note note) throws IOException {                  // *************** SAVE THE NOTE METHOD
        ensureNotesDirectoryExists();

        String filename = generateFilename(note.getTitle());                // generating the safe filename.       (might change name)
        String formattedContent = formatNoteForFile(note);                  // placing that in 'formattedContent'

        Path filePath = Paths.get(Config.NOTES_DIRECTORY + filename);       // building our path

        Files.writeString(filePath, formattedContent);                      // actually saving it!

        return filename;                                                    // give the final file name
    }

    public List<String> listNotes() throws IOException {                    // **************** LIST ALL NOTES METHOD
        ensureNotesDirectoryExists();

        Path notesDir = Paths.get(Config.NOTES_DIRECTORY);

        List<String> noteFiles = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(notesDir, "*.md")) {
            for (Path file : stream) {
                noteFiles.add(file.getFileName().toString());
            }
        }

        noteFiles.sort((f1, f2) -> {
            try {
                Path path1 = Paths.get(Config.NOTES_DIRECTORY + f1);
                Path path2 = Paths.get(Config.NOTES_DIRECTORY + f2);

                long time1 = Files.getLastModifiedTime(path1).toMillis();           // converts timestamp to milliseconds, easier to compare
                long time2 = Files.getLastModifiedTime(path2).toMillis();

                return Long.compare(time2, time1);
            } catch (IOException e) {
                return 0;
            }
        });
        return noteFiles;
    }
}
