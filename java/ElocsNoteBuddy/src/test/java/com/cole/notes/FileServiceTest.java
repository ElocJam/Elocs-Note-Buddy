package com.cole.notes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileServiceTest {
    
    @Test
    public void testGenerateFilenameWithSpace() {                                                       // Test 1: create a file name with spaces
                                                                                                            // will be used to check if we convert those spaces to "-", that our chars are all lowercase and that it ends with the .md extension
        FileService fileService = new FileService();
        String filename = fileService.generateFilename("My First Note");                                // generates test filename with spaces
        
        assertEquals(filename, filename.toLowerCase());                                                 // ensures characters are lowercase
        assertTrue(filename.contains("my-first-note"));                                              // ensures spaces were made into dashes
        assertTrue(filename.endsWith(".md"));                                                   // ensures filename ends with .md extension

        String[] parts = filename.split("-");                                                   // creates string array and splits the filename into parts based on the dashes
        assertTrue(parts.length >= 5, "Filename should have title parts + date + time");       // makes sure there are 5 or more "parts" which are the name words, and the timestamp

        System.out.println("Generated filename: " + filename);
    }

}
