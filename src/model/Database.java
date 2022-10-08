package model;

import util.CustomComparator;
import util.exceptions.NoSuchCompanyException;
import util.exceptions.NoSuchPersonException;
import util.exceptions.NoSuchProductException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A container to simulate a database, which provides countless useful operations
 */
public class Database {
    /**
     * Contains every {@link Person} based on their {@code id}
     */
    private final Map<Integer, Person> peopleMap = new HashMap<>();

    /**
     * Contains a {@link List} containing every {@link Person} in the {@link Database}
     */
    private final List<Person> people = new ArrayList<>();

    /**
     * Contains every {@link Product} based on their {@code id}
     */
    private final Map<Integer, Product> productsMap = new HashMap<>();

    /**
     * Contains a {@link List} containing every {@link Product} in the {@link Database}
     */
    private final List<Product> products = new ArrayList<>();

    /**
     * Contains every {@link Company} based on their {@code id}
     */
    private final Map<Integer, Company> companiesMap = new HashMap<>();

    /**
     * Contains a {@link List} containing every {@link Company} in the {@link Database}
     */
    private final List<Company> companies = new ArrayList<>();

    /**
     * Add a {@link Person} to the {@link Database}
     *
     * @param id The {@code id} of the new {@link Person}
     * @param p An instance of the new {@link Person}
     */
    public void addPerson(int id, Person p) {
        peopleMap.put(id, p);
        people.add(p);
    }

    /**
     * Add a {@link Product} to the {@link Database}
     *
     * @param id The {@code id} of the new {@link Product}
     * @param p An instance of the new {@link Product}
     */
    public void addProduct(int id, Product p) {
        productsMap.put(id, p);
        products.add(p);
    }

    /**
     * Add a {@link Company} to the {@link Database}
     *
     * @param id The {@code id} of the new {@link Company}
     * @param c An instance of the new {@link Company}
     */
    public void addCompany(int id, Company c) {
        companiesMap.put(id, c);
        companies.add(c);
    }

    /**
     * Adds a 'friend' correlation
     *
     * @param id1 The {@code id} of the first {@link Person}
     * @param id2 The {@code id} of the second {@link Person}
     *
     * @throws NoSuchPersonException If either of the provided {@code id}'s is invalid
     */
    public void setFriends(int id1, int id2) throws NoSuchPersonException {
        // Get the person based on their id
        Person p1 = peopleMap.get(id1);
        Person p2 = peopleMap.get(id2);

        // Throw exception if either of the doesn't exist
        if (p1 == null)
            throw new NoSuchPersonException(id1);

        if (p2 == null)
            throw new NoSuchPersonException(id2);

        // Add 'friend' correlation
        p1.addFriend(p2);
        p2.addFriend(p1);
    }

    /**
     * Adds a {@link Product} to the {@link List} of owned products of a {@link Person}
     *
     * @param idPerson The {@code id} of the {@link Person}
     * @param idProduct The {@code id} of the {@link Product}
     *
     * @throws NoSuchPersonException If the {@link Person} with the {@code id} can't be found
     * @throws NoSuchProductException If the {@link Product} with the {@code id} can't be found
     */
    public void setPersonProduct(int idPerson, int idProduct) throws NoSuchPersonException, NoSuchProductException {
        // Get the person/product based on their id
        Person pe = peopleMap.get(idPerson);
        Product pr = productsMap.get(idProduct);

        // Throw fitting exceptions
        if (pe == null)
            throw new NoSuchPersonException(idPerson);

        if (pr == null)
            throw new NoSuchProductException(idProduct);

        // Add the product to the list of owned products
        pe.buyProduct(pr);
    }

    /**
     * Adds a {@link Product} to the {@link List} of produced products of a {@link Company}
     *
     * @param idProduct The {@code id} of the {@link Product}
     * @param idCompany The {@code id} of the {@link Company}
     *
     * @throws NoSuchProductException If the {@link Product} with the {@code id} can't be found
     * @throws NoSuchCompanyException If the {@link Company} with the {@code id} can't be found
     */
    public void setManufacturer(int idProduct, int idCompany) throws NoSuchProductException, NoSuchCompanyException {
        // Get the product/company based on their id
        Product p = productsMap.get(idProduct);
        Company c = companiesMap.get(idCompany);

        // Throw fitting exceptions
        if (p == null)
            throw new NoSuchProductException(idProduct);

        if (c == null)
            throw new NoSuchCompanyException(idProduct);

        // Set corresponding correlations
        p.setMadeBy(c);
        c.addProduct(p);
    }

    /**
     * Get all the {@link Person people} based on a provided {@link String}
     *
     * @param name The {@link String} to search for
     *
     * @return A {@link Stream} of {@link Person} who's {@code name} contains a specific {@link String}
     *
     * @throws NoSuchPersonException If no {@link Person} could be found
     */
    public Stream<Person> getPeopleByName(String name) throws NoSuchPersonException {
        // Get a stream with all people, who's name contains the specific string
        Stream<Person> personStream = getPeople().stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()));

        // Return the stream if it's not empty or throw an exception
        if (personStream.count() > 0)
            return getPeople().stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()));
        else
            throw new NoSuchPersonException(name);
    }

    /**
     * Get all the {@link Product products} based on a provided {@link String}
     *
     * @param name The {@link String} to search for
     *
     * @return A {@link Stream} of {@link Product} who's {@code name} contains a specific {@link String}
     *
     * @throws NoSuchProductException If no {@link Product} could be found
     */
    public Stream<Product> getProductsByName(String name) throws NoSuchProductException {
        // Get a stream with all products, who's name contains the specific string
        Stream<Product> productStream = getProducts().stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()));

        // Return the stream if it's not empty or throw an exception
        if(productStream.count() > 0)
            return getProducts().stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()));
        else
            throw new NoSuchProductException(name);
    }

    /**
     * Get the {@link Product} or {@link Company} network of a {@link Person}
     * Sorted in {@code ascending} order by the {@link CustomComparator}
     *
     * @param p A {@link Person} to base the search on
     * @param func A {@link Function} which will be called for each friend of the provided {@link Person}
     * @param <T> A TypeParameter restricting this method to children of the superclass {@link Parent}
     *
     * @return A {@link List} of {@link Person people} or {@link Product products} which match the required criteria
     */
    public <T extends Parent> List<T> getNetwork(Person p, Function<Person, List<T>> func) {
        // Create a new ArrayList
        List<T> results = new ArrayList<>();

        // Add all products/companies of every friend of the provided person
        for (Person f : p.getFriends())
            results.addAll(func.apply(f));

        // Remove products/companies of the provided person
        results.removeAll(func.apply(p));

        // Sort the list with the CustomComparator
        results.sort(new CustomComparator<>());

        // Remove all duplicates and return the list
        return results.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Get a {@link Person} based on their {@code id}
     *
     * @param id The {@code id} to search for
     *
     * @return A {@link Person} if the {@code id} is correct
     *
     * @throws NoSuchPersonException If no {@link Person} can be found with that {@code id}
     */
    public Person getPersonById(int id) throws NoSuchPersonException {
        Person p = peopleMap.get(id);
        if (p != null)
            return p;
        else
            throw new NoSuchPersonException(id);
    }

    /**
     * Get a {@link Product} based on their {@code id}
     *
     * @param id The {@code id} to search for
     *
     * @return A {@link Product} if the {@code id} is correct
     *
     * @throws NoSuchProductException If no {@link Product} can be found with that {@code id}
     */
    public Product getProductById(int id) throws NoSuchProductException {
        Product p = productsMap.get(id);
        if (p != null)
            return p;
        else
            throw new NoSuchProductException(id);
    }

    /**
     * Get a {@link Company} based on their {@code id}
     *
     * @param id The {@code id} to search for
     *
     * @return A {@link Company} if the {@code id} is correct
     *
     * @throws NoSuchCompanyException If no {@link Company} can be found with that {@code id}
     */
    public Company getCompanyById(int id) throws NoSuchCompanyException {
        Company c = companiesMap.get(id);
        if (c != null)
            return c;
        else
            throw new NoSuchCompanyException(id);
    }

    /**
     * Get every {@link Person}
     *
     * @return The {@link List} containing every {@link Person}
     */
    public List<Person> getPeople() {
        return people;
    }

    /**
     * Get every {@link Product}
     *
     * @return The {@link List} containing every {@link Product}
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Get every {@link Company}
     *
     * @return The {@link List} containing every {@link Company}
     */
    public List<Company> getCompanies() {
        return companies;
    }
}
