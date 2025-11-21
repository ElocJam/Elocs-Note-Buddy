package com.cole.notes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileServiceTest {
    
    @Test
    public void testGenerateFilenameWithSpace() {                                                       // Test 1: create a file name with spaces
                                                                                                            // will be used to check if we convert those spaces to "-", that our chars are all lowercase and that it ends with the .md extension
        FileService fileService = new FileService();
        String filename = fileService.generateFilename("My First Note");                                // generates test filename with spaces
        System.out.println("DEBUG - Generated filename: " + filename);
        assertEquals(filename, filename.toLowerCase(), "Filename should all be lowercase");     // ensures characters are lowercase
        assertTrue(filename.contains("my-first-note"));                                              // ensures spaces were made into dashes
        assertTrue(filename.endsWith(".md"));                                                   // ensures filename ends with .md extension

        String[] parts = filename.split("-");                                                   // creates string array and splits the filename into parts based on the dashes
        assertTrue(parts.length >= 5, "Filename should have title parts + date + time");       // makes sure there are 5 or more "parts" which are the name words, and the timestamp

        System.out.println("Generated filename: " + filename);
    }

    // keep testing!!!!!

    @Test                                                                                   // Test 2: create a note with some data
    public void testFormateNoteForFile() {

        Note note = new Note("Test Note", "This is test content");          // create note with title and content
        note.addTag("test");                                                          // add tag
        note.addTag("example");                                                       // add tag

        FileService fileService = new FileService();

        String formatted = fileService.formatNoteForFile(note);                            // format note for file output

        assertTrue(formatted.startsWith("---"));                                   // make sure note starts with YAML delimiter
        assertTrue(formatted.contains("title: Test Note"));                             // should contain title
        assertTrue(formatted.contains("created: "));                                    // should contain timestamp
        assertTrue(formatted.contains("modified: "));                                   // should contain timestamp
        assertTrue(formatted.contains("tags: [test, example"));                         // should contain tags
        assertTrue(formatted.contains("This is test content"));                         // should contain content

        System.out.println("\n === FORMATTED NOTE ===");
        System.out.println(formatted);
        System.out.println("=== END ===\n");
    }
}
