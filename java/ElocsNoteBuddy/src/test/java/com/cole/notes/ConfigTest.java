package com.cole.notes;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {
    
    @Test                                                                               // Test 1
    public void testNotesDirectoryIsSet() {                                             // Ensures the directory where i am storing my notes is set

        assertNotNull(Config.NOTES_DIRECTORY);                                          // makes sure NOTES_DIRECTORY is not null
        assertFalse(Config.NOTES_DIRECTORY.isEmpty());                                  // makes sure NOTES_DIRECTORY is not empty
        assertTrue(Config.NOTES_DIRECTORY.contains("noteVault"));                    // makes sure NOTES_DIRECTORY contains noteVault (folder where notes actually go)

        System.out.println("Notes will be saved to: " + Config.NOTES_DIRECTORY);        // prints where the notes will be saved
    }

    @Test                                                                               // Test 2
    public void testFileNamesHaveCorrectExtension() {                                   // ensures filename is using the config constant

        String filename = "test-note" + Config.NOTE_EXTENSION;                          // creates a filename using the config constant
        assertTrue(filename.endsWith(".md"));                                   // Verify it ends with .note extension
        assertEquals("test-note.md", filename);                               // Verify complete filename is correct
    }
}
