package model;

import util.*;
import util.exceptions.NoSuchCompanyException;
import util.exceptions.NoSuchPersonException;
import util.exceptions.NoSuchProductException;
import util.exceptions.UnknownGenderException;

import java.io.*;

/**
 * This class reads file from a provided path into a {@link Database}
 */
public class Reader {
    /**
     * Represents the {@code BetterBufferedReader} instance
     *
     * @see BetterBufferedReader
     */
    private static BetterBufferedReader fileReader;

    /**
     * An {@link Enum} to represents all valid operations
     */
    private enum Operation {
        ADD_PERSON,
        ADD_PRODUCT,
        ADD_COMPANY,
        SET_FRIEND,
        SET_PERSON_PRODUCT,
        SET_PRODUCT_COMPANY,
        NONE;
    }

    // This class shouldn't be instantiated
    private Reader() {
    }

    /**
     * Loads the content of a file to a database
     *
     * @param file The path to the file, which will be read
     * @return Database instance, or null, if the file couldn't be found or has no valid entries
     */
    public static Database readFileToDatabase(String file) {
        Database db = new Database();

        // Read file from the provided path, use own File Reader
        try (BetterBufferedReader reader = new BetterBufferedReader(new FileReader(new File(file)))) {
            fileReader = reader;

            String line = reader.readLine();
            Operation op;

            // Read every line
            while (line != null) {
                // Check for a new set of entries
                if (line.contains("New_Entity")) {
                    // Remove unnecessary information
                    line = line.replace("New_Entity:", "").replace("\"", "");

                    // Change operation based on unique identifiers
                    if (line.contains("person_name"))
                        op = Operation.ADD_PERSON;
                    else if (line.contains("product_name"))
                        op = Operation.ADD_PRODUCT;
                    else if (line.contains("company_name"))
                        op = Operation.ADD_COMPANY;
                    else if (line.contains("person1_id"))
                        op = Operation.SET_FRIEND;
                    else if (line.contains("person_id"))
                        op = Operation.SET_PERSON_PRODUCT;
                    else if (line.contains("company_id"))
                        op = Operation.SET_PRODUCT_COMPANY;
                    else
                        op = Operation.NONE;

                    // Read the first entry
                    line = reader.readLine();

                    do {
                        // Split the read line at ','
                        String[] attributes = line.replace("\"", "").split(",");

                        try {
                            // Convert first attribute to an int which always represents the id
                            int id = Integer.parseInt(attributes[0]);

                            try {
                                // Remove leading and trailing spaces
                                String att1 = attributes[1].strip();

                                try {
                                    // Switch behaviour based on current operation
                                    switch (op) {
                                        case ADD_PERSON -> {
                                            try {
                                                // Try adding a person to the database
                                                db.addPerson(id, new Person(id, att1, Gender.getGenderFromString(attributes[2])));
                                            } catch (IndexOutOfBoundsException | UnknownGenderException e) {
                                                // Log error
                                                logInvalidLine(e.getClass().getSimpleName(), e.getMessage());
                                            }
                                        }
                                        // Add a product to the database
                                        case ADD_PRODUCT -> db.addProduct(id, new Product(id, att1));

                                        // Add a company to the database
                                        case ADD_COMPANY -> db.addCompany(id, new Company(id, att1));

                                        // Set "friend" correlation based on IDs
                                        case SET_FRIEND -> db.setFriends(id, Integer.parseInt(att1));

                                        // Add a product to the person's list
                                        case SET_PERSON_PRODUCT -> db.setPersonProduct(id, Integer.parseInt(att1));

                                        // Add a product to the company's list
                                        case SET_PRODUCT_COMPANY -> db.setManufacturer(id, Integer.parseInt(att1));

                                        // Log invalid line if no identifier was found
                                        case NONE -> logInvalidLine("Falscher Identifier!", "");
                                    }
                                } catch (NoSuchPersonException | NoSuchProductException | NoSuchCompanyException e) {
                                    // Log errors which have something to do with missing/bad entries
                                    logInvalidLine(e.getClass().getSimpleName(), e.getMessage());
                                }
                            } catch (IndexOutOfBoundsException e) {
                                // Log invalid array operations
                                logInvalidLine(e.getClass().getSimpleName(), e.getMessage());
                            }
                        } catch (NumberFormatException e) {
                            // Log invalid ids
                            logInvalidLine(e.getClass().getSimpleName(), e.getMessage());
                        }

                        line = reader.readLine();

                        // Continue, until no more lines are found or a 'New_Entity' starts
                    } while (line != null && !line.contains("New_Entity"));
                } else
                    line = reader.readLine();
            }

            // Return filled database
            return db;
        } catch (FileNotFoundException e) {
            System.err.println("Die angegebene Datei '" + file + "' konnte nicht gefunden werden!");
        } catch (Exception e) {
            // Print other exceptions
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Logs an invalid line in the file
     *
     * @param message The reason why the line is invalid
     */
    private static void logInvalidLine(String error, String message) {
        System.err.println("Fehlerhafter Datensatz in Zeile " + fileReader.getCurrentLine() + "! " + error + ": " + message);
    }
}
