package com.cole.notes;

import static org.junit.jupiter.api.Assertions.*;                               // import assertion methods from JUnit
import org.junit.jupiter.api.Test;                                              // import test annotation
import java.time.LocalDateTime;

public class NoteTest {                                                                 // Test class for the Note class

    @Test                                                                               // ***********Test 1: the "happy path" - testing normal usage
    public void testNoteCreation() {                                            
                                                                                
        Note note = new Note("My First Note", "This is the content");    // Create new note object with title and content
                                                                                            // + calls the Note constructor with two String parameters
        assertEquals("My First Note", note.getTitle());                        // Check that title was stored correctly
        assertEquals("This is the content", note.getContent());                // Check that content was stored correctly
        assertNotNull(note.getCreated());                                       // Verify creation timestamp was set
        assertNotNull(note.getModified());                                      // Verify modification timestamp was set
    }

    @Test                                                                       // ************Test 2: Verify that Note rejects empty titles
    public void testNoteRejectsEmptyTitle() {                                  
                                                                                
        assertThrows(IllegalArgumentException.class, () -> {       // assertThrows expects 2 things: type of exception expected / arrow function that should throw that exception
            new Note("", "Some content");                            // () -> {} = lambda (anonymous function) that contains code that SHOULD throw that exception
        });                                                                     // test creates a note with empty string as the title, test SHOULD fail and throw IllegalArgException
    }                                                                               // if it doesn't throw the exception, test fails

    @Test                                                                       // ************Test 3: Verify Note rejects null titles
    public void testNoteRejectsNullTitle() {                                        // different than empty: need to test for both
                                                                                
        assertThrows(IllegalArgumentException.class, () -> {       //  Same as test 2, but testing for null as title instead of empty string
            new Note(null, "Some content");
        });
    }

    @Test                                                                       // **************Test 4: Verify that we can add tags to a Note and retrieve them
    public void testNoteWithTags() {                                            
                                                                                
        Note note = new Note("Tagged Note", "Content");            // create basic note
        note.addTag("java");                                                  // add test tags
        note.addTag("project");                                               // add test tages
                                                                                
        assertTrue(note.getTags().contains("java"));                            // Check that "java" tag exists in note tag list - getTags() returns a List<String> and contains() checks it for "java". assertTrue fails is false
        assertTrue(note.getTags().contains("project"));                         // Checks that "project" tag also exists
        assertEquals(2, note.getTags().size());                               // Verifies that exactly 2 tags were added (no dupes, no extras)
    }                                                                              // getTags().size() returns the number of tags

    @Test
    public void testTimestampsAreSet() {                                          // **************Test 5: ensures the timestamps are functional
        
        Note note = new Note("Title", "Content");                  // creates basic note
        assertNotNull(note.getCreated());                                         // ensures there is a timestamp when created
        assertNotNull(note.getModified());                                        // ensures there is a timestamp when modified
    }                                                                                 // this test is a bit limited, maybe update to test to ensure note gets timestamp when modified after initial creation

    @Test                                                                          // **************Test 6: ensures ridiculously long titles dont get created
    public void testNoteRejectsLongTitle() {                                
        
        String longTitle = "a".repeat(250);                                 // create a title that has 250 chars
        assertThrows(IllegalArgumentException.class, () -> {                // throw an exception if title is longer than 200 chars
            new Note(longTitle, "Content");                               // creates the note with the long title
        });
    }

    @Test                                                                           // **************Test 7: tests that content gets updated
    public void testSetContent() {
        
        Note note = new Note("Test", "Original content");
        assertEquals("Original content", note.getContent());
        note.setContent("New content");
        assertEquals("New content", note.getContent());
    }

    @Test                                                                           // **************Test 8: test that updateModifiedTime updates the modified timestamp
    public void testUpdateModifiedTIme() throws InterruptedException {

        Note note = new Note("Test", "Content");
        LocalDateTime originalModified = note.getModified();
        Thread.sleep(1000);
        note.updateModifiedTime();
        LocalDateTime newModified = note.getModified();

        assertNotEquals(originalModified, newModified);
        assertTrue(newModified.isAfter(originalModified));
        System.out.println("Modified time updated successfully");
    }

    @Test                                                                           // *************Test 9: Tests the second constructor that takes timestamps
    public void testConstructorWithTimestamps() {

        LocalDateTime testCreated = LocalDateTime.of(2025, 1, 1, 12, 0);
        LocalDateTime testModified = LocalDateTime.of(2025, 1, 2, 14, 30);
        Note note = new Note("Test", "Content", testCreated, testModified);

        assertEquals("Test", note.getTitle());
        assertEquals("Content", note.getContent());
        assertEquals(testCreated, note.getCreated());
        assertEquals(testModified, note.getModified());

        System.out.println("Constructor with timestamps works correctly");
    }

    @Test                                                                           // **************Test 10: makes sure created time stays the same
    public void testCreatedTimeNeverChanges() throws InterruptedException {

        Note note = new Note("Test", "Original");
        LocalDateTime originalCreated = note.getCreated();
        Thread.sleep(1000);

        note.setContent("Updated");
        note.updateModifiedTime();
        assertEquals(originalCreated, note.getCreated());

        System.out.println("Created time remains unchanged");
    }
}
