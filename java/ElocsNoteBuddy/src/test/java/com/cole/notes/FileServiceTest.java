package com.cole.notes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class FileServiceTest {

    private static final String ORIGINAL_DIR = System.getProperty("user.home") + "/noteVault/";
    private static final String TEST_DIR = System.getProperty("user.home") + "/noteVault-test/";

    @BeforeAll
    public static void setupTest() throws IOException {

        Config.NOTES_DIRECTORY = TEST_DIR;

        Path testPath = Paths.get(TEST_DIR);
        if (Files.exists(testPath)) {
            Files.walk(testPath)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {

                    }
                });
        }
        System.out.println("Tests using: " + TEST_DIR);
    }

    @AfterAll
    public static void teardownTests() throws IOException {

        Path testPath = Paths.get(TEST_DIR);
        if (Files.exists(testPath)) {
            Files.walk(testPath)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {

                    }
                });
        }

        Config.NOTES_DIRECTORY = ORIGINAL_DIR;
        System.out.println("Tests cleaned up, restored to: " + ORIGINAL_DIR);
    }
    
    @Test
    public void testGenerateFilenameWithSpace() {                                                       // ************Test 1: create a file name with spaces
                                                                                                            // will be used to check if we convert those spaces to "-", that our chars are all lowercase and that it ends with the .md extension
        FileService fileService = new FileService();
        String filename = fileService.generateFilename("My First Note");                          // generates test filename with spaces
        System.out.println("DEBUG - Generated filename: " + filename);
        assertEquals(filename, filename.toLowerCase(), "Filename should all be lowercase");     // ensures characters are lowercase
        assertTrue(filename.contains("my-first-note"));                                              // ensures spaces were made into dashes
        assertTrue(filename.endsWith(".md"));                                                   // ensures filename ends with .md extension

        String[] parts = filename.split("-");                                                   // creates string array and splits the filename into parts based on the dashes
        assertTrue(parts.length >= 5, "Filename should have title parts + date + time");       // makes sure there are 5 or more "parts" which are the name words, and the timestamp

        System.out.println("Generated filename: " + filename);
    }

    // keep testing!!!!!

    @Test                                                                                   // **********Test 2: create a note with some data
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

    @Test
    public void testListNotesReturnsEmpty() throws IOException {                        // ************Test 3: tests listing if directory is empty 

        FileService fileService = new FileService();
        List<String> notes = fileService.listNotes();
        assertNotNull(notes);
        System.out.println("Found " + notes.size() + " notes");
    }

    @Test
    public void testListNotesAfterCreating() throws IOException {                       // ***********Test 4: creates a note and verifies that it can be found in the list

        FileService fileService = new FileService();
        Note testNote = new Note("Test List Note", "This is for testing listing");
        String savedFilename = fileService.saveNote(testNote);

        List<String> notes = fileService.listNotes();
        assertTrue(notes.contains(savedFilename));
        assertTrue(notes.size() > 0);
        System.out.println("List contains: " + notes);
    }

    @Test
    public void testListNotesIsSorted() throws IOException {                        // ***********Test 5: verifies that notes are sorted by date modified

        FileService fileService = new FileService();                    
        Note note1 = new Note("First Note", "Content 1");           // creates first test note
        String file1 = fileService.saveNote(note1);

        try {
            Thread.sleep(1000);                                             // does a 1 second delay to ensure the timestamps are different
        } catch (InterruptedException e) {
    }

    Note note2 = new Note("Second Note", "Content 2");              // creates second test note
    String file2 = fileService.saveNote(note2);

    List<String> notes = fileService.listNotes();

    int pos1 = notes.indexOf(file1);
    int pos2 = notes.indexOf(file2);

    assertTrue(pos2 < pos1, "Newer note should appear first in list");      // compares the two note times, newest should be first
    }

    @Test
    public void testSaveAndLoadNote() throws IOException {                          // **********Test 6: verifies saving and loading notes with in tact data
        FileService fileService = new FileService();
        Note originalNote = new Note("Load Test Note", "This is test content for loading");
        originalNote.addTag("test");
        originalNote.addTag("loading");

        String filename = fileService.saveNote(originalNote);
        Note loadedNote = fileService.loadNote(filename);

        assertEquals(originalNote.getTitle(), loadedNote.getTitle());
        assertEquals(originalNote.getContent(), loadedNote.getContent());
        assertEquals(2, loadedNote.getTags().size());
        assertTrue(loadedNote.getTags().contains("test"));
        assertTrue(loadedNote.getTags().contains("loading"));

        System.out.println("Successfully loaded note: " + loadedNote.getTitle());
    }

    @Test                                                                           // **********Test 7: test that loading a non existant note throws error
    public void testLoadNonExistentNote() {
        FileService fileService = new FileService();

        assertThrows(IOException.class, () -> {
            fileService.loadNote("this-file-does-not-exist.md");
        });
    }

    @Test                                                                           // ************Test 8: make sure updating a note changed content and modified time
    public void testUpdateNote() throws IOException, InterruptedException {
        FileService fileService = new FileService();

        Note originalNote = new Note("Update Test", "Original content");
        String filename = fileService.saveNote(originalNote);

        Note loadedNote = fileService.loadNote(filename);
        String originalContent = loadedNote.getContent();
        LocalDateTime originalModified = loadedNote.getModified();

        Thread.sleep(1000);

        loadedNote.setContent("Updated content - this is new!");
        loadedNote.updateModifiedTime();
        fileService.updateNote(filename, loadedNote);

        Note updatedNote = fileService.loadNote(filename);

        assertNotEquals(originalContent, updatedNote.getContent());
        assertEquals("Updated content - this is new!", updatedNote.getContent());
        assertTrue(updatedNote.getModified().isAfter(originalModified), "Modified time should be newer after update");
        assertEquals(loadedNote.getCreated(), updatedNote.getCreated());

        System.out.println("update test passed!");
        System.out.println("Original modified: " + originalModified);
        System.out.println("New modified: " + updatedNote.getModified());
    }

    @Test                                                                               // ***********Test 9: ensures we kept the original timestamp
    public void testLoadPreservesTimestamps() throws IOException, InterruptedException {
        FileService fileService = new FileService();

        Note originalNote = new Note("Timestamp Test", "Content");
        LocalDateTime originalCreated = originalNote.getCreated();
        String filename = fileService.saveNote(originalNote);
        Thread.sleep(1000);

        Note loadedNote = fileService.loadNote(filename);

        long createdDiff = Math.abs(
            originalCreated.toLocalTime().toSecondOfDay() - loadedNote.getCreated().toLocalTime().toSecondOfDay()
        );

        assertTrue(createdDiff < 2, "Creation timestamp should be preserved when loading");
        System.out.println("Timestamp preservation test passed!");
    }

    @Test                                                                               // **************Test 10: make sure note can be deleted
    public void testDeleteNote() throws IOException {

        FileService fileService = new FileService();
        Note testNote = new Note("Note to Delete", "This will be deleted");
        String filename = fileService.saveNote(testNote);

        List<String> notesBefore = fileService.listNotes();
        assertTrue(notesBefore.contains(filename));
        fileService.deleteNote(filename);

        List<String> notesAfter = fileService.listNotes();
        assertFalse(notesAfter.contains(filename));
        System.out.println("Note successfuly deleted: " + filename);
    }

    @Test                                                                           // *************Test 11: make sure deleting a non existent note throws error
    public void testDeleteNonExistentNote() {

        FileService fileService = new FileService();

        assertThrows(IOException.class, () -> {
            fileService.deleteNote("this-does-not-exist.md");
        });
    }

    @Test                                                                           // *************Test 12: make sure deleting a note doesnt affect other notes
    public void testDeleteDoesNotAffectOtherNotes() throws IOException {

        FileService fileService = new FileService();
        Note note1 = new Note("Keep This", "Content 1");
        Note note2 = new Note("Delete This", "Content 2");
        String filename1 = fileService.saveNote(note1);
        String filename2 = fileService.saveNote(note2);

        fileService.deleteNote(filename2);

        List<String> notes = fileService.listNotes();
        assertTrue(notes.contains(filename1));
        assertFalse(notes.contains(filename2));

        Note loadedNote1 = fileService.loadNote(filename1);
        assertEquals("Keep This", loadedNote1.getTitle());
        System.out.println("Other notes remain intact after deletion");
    }

    @Test                                                                           // **************Test 13: make sure you can search notes by keyword
    public void testSearchByKeyword() throws IOException {
        FileService fileService = new FileService();

        Note note1 = new Note("Java is cool", "Learn Java and be cool too");
        Note note2 = new Note("Python is for data nerds", "Imagine caring about indents");
        Note note3 = new Note("How many tests do i need", "My fingers hurt");

        fileService.saveNote(note1);
        fileService.saveNote(note2);
        fileService.saveNote(note3);

        List<Note> results = fileService.searchNotesByKeyword("Java");
        assertTrue(results.size() >= 1);
        assertTrue(results.stream().anyMatch(n -> n.getTitle().contains("Java")));

        System.out.println("Search found " + results.size() + " notes containing 'Java'");
    }

    @Test                                                                           // **************Test 14: tests that we can filter notes by tags
    public void testFilterByTag() throws IOException {
        FileService fileService = new FileService();

        Note note1 = new Note("Tagged 1", "Content");
            note1.addTag("important");
            note1.addTag("work");

        Note note2 = new Note("Tagged 2", "Content");
            note2.addTag("personal");

        Note note3 = new Note("Tagged 3", "Content");
            note3.addTag("important");

        fileService.saveNote(note1);
        fileService.saveNote(note2);
        fileService.saveNote(note3);

        List<Note> results = fileService.filterNotesByTag("important");

        assertTrue(results.size() >=2);
            for (Note note : results) {
                assertTrue(note.getTags().contains("important"));
            }

        System.out.println("Filter found " + results.size() + " notes with tag 'important'");
    }

    @Test
    public void testGetAllTags() throws IOException {                           // ****************Test 15: tests that we can get all tags
        FileService fileService = new FileService();

        Note note1 = new Note("Test 1", "Content");
            note1.addTag("java");
            note1.addTag("coding");

        Note note2 = new Note("Test 2", "Content");
            note2.addTag("python");
            note2.addTag("coding");

        fileService.saveNote(note1);
        fileService.saveNote(note2);

        List<String> allTags = fileService.getAllTags();

        assertTrue(allTags.contains("java"));
        assertTrue(allTags.contains("python"));
        assertTrue(allTags.contains("coding"));
        assertTrue(allTags.size() >= 3);

        System.out.println("All unique tags: " + allTags);
    }

    @Test
    public void testListNotesSortedByCreated() throws IOException, InterruptedException {
        FileService fileService = new FileService();

        Note note1 = new Note("First", "Content");
        fileService.saveNote(note1);

        Thread.sleep(1000);

        Note note2 = new Note("Second", "Content");
        fileService.saveNote(note2);

        List<Note> sorted = fileService.listNotesSortedByCreated();
        assertTrue(sorted.size() >= 2);

        Note first = sorted.stream().filter(n -> n.getTitle().equals("First")).findFirst().orElse(null);
        Note second = sorted.stream().filter(n -> n.getTitle().equals("Second")).findFirst().orElse(null);
        assertNotNull(first);
        assertNotNull(second);
        assertTrue(second.getCreated().isAfter(first.getCreated()));

        System.out.println("Notes correctly sorted by creation date");
    }
}
