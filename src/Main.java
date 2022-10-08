import model.*;
import util.exceptions.InvalidNetworkException;
import util.exceptions.NoSuchPersonException;
import util.exceptions.NoSuchProductException;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {
    /**
     * This regulates console output
     */
    private static final boolean talkative = true;

    /**
     * This represents the {@link Database}
     */
    private static Database database;

    /**
     * This will later be used for associating {@link Operation Operations.}
     */
    private static final Map<String, Operation> argsMap = new HashMap<>();

    /**
     * This {@link Enum} represents every supported Operation
     */
    private enum Operation {
        SEARCH_PERSON,
        SEARCH_PRODUCT,
        PRODUCT_NETWORK,
        COMPANY_NETWORK
    }

    /**
     * This represents the relative path of the file containing the database content
     */
    public static final String pathToFile = "data.db";

    public static void main(String[] args) {
        // Associate Strings with the corresponding Operations
        argsMap.put("--personensuche", Operation.SEARCH_PERSON);
        argsMap.put("--produktsuche", Operation.SEARCH_PRODUCT);
        argsMap.put("--produktnetzwerk", Operation.PRODUCT_NETWORK);
        argsMap.put("--firmennetzwerk", Operation.COMPANY_NETWORK);

        database = Reader.readFileToDatabase(pathToFile);

        if (database != null) {
            // Go through every provided argument
            for (String s : args) {
                String[] tmp = s.replace("\"", "").split("=");
                try {
                    Operation op = argsMap.get(tmp[0]);
                    if (op != null && tmp.length == 2) {
                        switch (op) {
                            // Search for a person and print the results, if there are any
                            case SEARCH_PERSON -> printStreamResults("der Person " + tmp[1], () -> {
                                try {
                                    return database.getPeopleByName(tmp[1]);
                                } catch (NoSuchPersonException e) {
                                    System.out.println(e.getMessage());
                                }
                                return null;
                            });

                            // Search for a product and print the results, if there are any
                            case SEARCH_PRODUCT -> printStreamResults("dem Produkt " + tmp[1], () -> {
                                try {
                                    return database.getProductsByName(tmp[1]);
                                } catch (NoSuchProductException e) {
                                    System.out.println(e.getMessage());
                                }
                                return null;
                            });

                            // Get the product network of the specified person
                            case PRODUCT_NETWORK -> getNetwork(Integer.parseInt(tmp[1]), "Produkt");

                            // Get the company network of the specified person
                            case COMPANY_NETWORK -> getNetwork(Integer.parseInt(tmp[1]), "Firmen");
                        }
                    }
                } catch (Exception e) {
                    // Print unhandled exceptions
                    e.printStackTrace();
                }
            }
        } else
            System.out.println("Die angegebene Datei " + pathToFile + " ist fehlerhaft!");
    }

    /**
     * This method prints the content of the provided {@link Stream}
     *
     * @param searchFor The sought identifier
     * @param supplier The {@link Supplier} supplying the wanted {@link Stream}
     * @param <T> TypeParameter which only accepts children of {@link Parent}
     */
    public static <T extends Parent> void printStreamResults(String searchFor, Supplier<Stream<T>> supplier) {
        if (talkative)
            System.out.print("Die Suche nach " + searchFor + " ergab ");

        // Check whether the stream is existing
        if (supplier.get() != null) {
            long resultCount = supplier.get().count();

            // Check whether the stream is not empty
            if (resultCount > 0) {
                if (talkative)
                    System.out.println(resultCount + " Treffer:");

                // Print stream content
                supplier.get().forEach(p -> System.out.println(p.getName() + ", ID:" + p.getId()));
            } else if (talkative)
                System.out.println("keine Treffer!");
        }
    }

    /**
     * This method converts a {@link List} to a readable {@link String}
     *
     * @param list The to be converted {@link List}
     * @param <T> TypeParameter which only accepts children of {@link Parent}
     *
     * @return A {@link String} containing every item of the provided {@link List}
     */
    public static <T extends Parent> String createOutputString(List<T> list) {
        StringBuilder builder = new StringBuilder();

        // Create output stream
        list.forEach(s -> builder.append(s.getName()).append(","));

        // Ignore last ','
        return builder.toString().substring(0, builder.length() - 1);
    }

    /**
     * Get a network of a {@link Person}
     *
     * @param personId The id of the {@link Person}
     * @param network A {@link String} representing the network

     * @throws InvalidNetworkException If there is no network with the provided name
     * @throws NoSuchPersonException If the personId is invalid
     */
    private static void getNetwork(int personId, String network) throws InvalidNetworkException, NoSuchPersonException {
        Person person = database.getPersonById(personId);

        if (talkative)
            System.out.println("Das " + network + "netzwerk von " + person.getName() + ", " + person.getId() + ":");

        // Switch network based on provided identifier
        // -> Switch functions
        if ("Produkt".equals(network))
            System.out.println(createOutputString(database.getNetwork(person, Person::getProducts)));
        else if ("Firmen".equals(network))
            System.out.println(createOutputString(database.getNetwork(person, Person::getCompanies)));
        else
            throw new InvalidNetworkException(network);
    }
}
