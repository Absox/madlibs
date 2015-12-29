package com.madlibs.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Helper class for keeping track of identifier configs for anonymous users.
 * Created by Ran on 12/24/2015.
 */
public class AnonymousIdentifiers {

    private List<String> identifiers;

    private static AnonymousIdentifiers instance;

    static {
        try {
            instance = new AnonymousIdentifiers("data/Celebs");
        } catch (FileNotFoundException e) {
            System.err.println("Configuration file not found: anonymous identifiers");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Reads in list of identifiers
     * @param filename Filename to read from
     * @throws FileNotFoundException File not found
     */
    public AnonymousIdentifiers(String filename) throws FileNotFoundException {

        this.identifiers = new ArrayList<>();
        Scanner fileScanner = new Scanner(new File(filename));

        while (fileScanner.hasNextLine()) {
            String currentLine = fileScanner.nextLine();
            if (currentLine.length() > 0 && !currentLine.startsWith("//")) {
                identifiers.add(currentLine);

            }
        }
        fileScanner.close();
    }

    /**
     * Accessor for singleton instance.
     * @return Singleton instance.
     */
    public static AnonymousIdentifiers getInstance() {
        return instance;
    }

    /**
     * Accessor for list of identifiers.
     * @return List of identifiers.
     */
    public List<String> getIdentifiers() {
        return this.identifiers;
    }

    /**
     * Returns a random identifier.
     * @return Random identifier.
     */
    public String getRandomIdentifier() {
        Random random = new Random();
        return identifiers.get(random.nextInt(identifiers.size()));
    }
}
