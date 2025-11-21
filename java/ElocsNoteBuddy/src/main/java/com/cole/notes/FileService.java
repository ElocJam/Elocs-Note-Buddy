package com.cole.notes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {
    
    public String generateFilename(String title) {
        
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

    public String formatNoteForFile(Note note) {
        
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

    public String saveNote(Note note) throws IOException {                  // declares method, throws an error if it fails

        String filename = generateFilename(note.getTitle());                // generating the safe filename.       (might change name)
        String formattedContent = formatNoteForFile(note);                  // placing that in 'formattedContent'

        Path filePath = Paths.get(Config.NOTES_DIRECTORY + filename);       // building our path
        Files.createDirectories(filePath.getParent());                      // making sure directory exists, makes one if not. (take a look)
        Files.writeString(filePath, formattedContent);                      // actually saving it!

        return filename;                                                    // give the final file name
    }
}
