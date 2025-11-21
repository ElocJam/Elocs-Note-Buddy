package com.cole.notes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

            output.append("---\n");
            output.append("title: ").append(note.getTitle()).append("\n");
            output.append("created: ").append(note.getCreated().toString()).append("\n");
            output.append("modified: ").append(note.getModified().toString()).append("\n");

            if (!note.getTags().isEmpty()) {
                output.append("tags: [");
                for (int i = 0; i < note.getTags().size(); i++) {
                    output.append(note.getTags().get(i));
                    if (i < note.getTags().size() - 1) {
                        output.append(", ");
                    }
                }
                output.append("]\n");
            }
        output.append("---\n\n");
        output.append(note.getContent());

        return output.toString();
    }
}
