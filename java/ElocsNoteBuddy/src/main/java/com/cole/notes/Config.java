package com.cole.notes;

public class Config {
    
    public static final String NOTES_DIRECTORY = System.getProperty("user.home") + "/noteVault/";  // Sets the NOTES_DIRECTORY (where my notes are saved) to the /noteVault directory created in home

    public static final String NOTE_EXTENSION = ".md";            // sets the file extension for my notes to .md

    public static final int MAX_TITLE_LENGTH = 200;                 // sets hard limit for title length, prevents rididculous titles
}
