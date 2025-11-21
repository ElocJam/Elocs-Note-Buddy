package com.cole.notes;
import java.util.Scanner;


public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Welcome to Eloc's Note Buddy!" );
        System.out.println("=============================");
        System.out.println("What would you like to do?");
        System.out.println("Create new note               ---> Enter 1");
        System.out.println("Read existing note            ---> Enter 2");
        System.out.println("Update existing note          ---> Enter 3");
        System.out.println("Delete existing note          ---> Enter 4");
        System.out.println("Search notes by tag           ---> Enter 5");
        System.out.println("Search notes by time created  ---> Enter 6");
        System.out.println("Search notes by date modified ---> Enter 7");
        System.out.println("Add tag to existing note      ---> Enter 8");
        System.out.println("Tell me a joke                ---> Enter 9");
        System.out.println("Quit                          ---> Enter 0");

        Scanner inputScanner = new Scanner(System.in);

    }
}
