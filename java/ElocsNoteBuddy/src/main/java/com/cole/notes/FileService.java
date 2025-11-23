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

    public Note loadNote(String filename) throws IOException {               // ****************** LOAD EXISTING NOTE METHOD
        ensureNotesDirectoryExists();

        Path filePath = Paths.get(Config.NOTES_DIRECTORY + filename);
        if (!Files.exists(filePath)) {
            throw new IOException("Note file not found: " + filename);
        }
        String content = Files.readString(filePath);

        return parseNoteFromString(content);
        }

    private Note parseNoteFromString(String content) throws IOException {       // **************** PARSE NOTE FROM STRING METHOD
        if (!content.startsWith("---")) {
            throw new IOException("Invalid note format: missing YAML header");
        }

        String[] parts = content.split("---", 3);

        if (parts.length < 3) {
            throw new IOException("invalid note format: incomplete YAML header");
        }
        String yamlContent = parts[1].trim();
        String noteContent = parts[2].trim();

        String title = "";
        String created = "";                            
        String modified = "";                         
        List<String> tags = new ArrayList<>();

        for (String line : yamlContent.split("\n")) {
            line = line.trim();

            if (line.startsWith("title:")) {
                title = line.substring(6).trim();
            } else if (line.startsWith("created:")) {
                created = line.substring(8).trim();
            } else if (line.startsWith("modified:")) {
                modified = line.substring(9).trim();
            } else if (line.startsWith("tags:")) {
                
                String tagStr = line.substring(5).trim();
                if (tagStr.startsWith("[") && tagStr.endsWith("]")) {
                    tagStr = tagStr.substring(1, tagStr.length() -1);
                    for (String tag : tagStr.split(",")) {
                        tags.add(tag.trim());
                    }
                }
            }
        }

        LocalDateTime createdTime = LocalDateTime.parse(created);
        LocalDateTime modifiedTime = LocalDateTime.parse(modified);

        Note note = new Note(title, noteContent, createdTime, modifiedTime);

        for (String tag : tags) {
            note.addTag(tag);
        }
        return note;
    }

    public void updateNote(String filename, Note note) throws IOException {
        ensureNotesDirectoryExists();

        Path filePath = Paths.get(Config.NOTES_DIRECTORY + filename);
        String formattedContent = formatNoteForFile(note);
        Files.writeString(filePath, formattedContent);
    }
}

