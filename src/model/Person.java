package model;

import util.Gender;

import java.util.ArrayList;
import java.util.List;

/**
 * A subclass of {@link Parent} representing a person
 */
public class Person extends Parent {
    /**
     * The {@link Gender} of the {@link Person}
     */
    private Gender gender;

    /**
     * A {@link List} containing every friend of the {@link Person}
     */
    private final List<Person> friends;

    /**
     * A {@link List} containing every bought {@link Product} of the {@link Person}
     */
    private final List<Product> products;

    /**
     * Creates a new instance with the given params
     *
     * @param id The {@code id} of the {@link Person}
     * @param name The {@code name} of the {@link Person}
     * @param gender The {@link Gender} of the {@link Person}
     */
    public Person(int id, String name, Gender gender) {
        // Let the parent take care of name and id
        super(id, name);

        // Initialise lists
        friends = new ArrayList<>();
        products = new ArrayList<>();

        this.gender = gender;
    }

    /**
     * Add a friend to the friends {@link List}
     *
     * @param p The {@link Person} to add to the {@link List}
     */
    public void addFriend(Person p) {
        if (!hasFriend(p))
            friends.add(p);
    }

    /**
     * Remove a friend to the friens {@link List}
     *
     * @param p The {@link Person} to remove from the {@link List}
     */
    public void removeFriend(Person p) {
        // Only remove friend if the person is friends with it
        if (hasFriend(p))
            friends.remove(p);

        // Also remove this person from the friends list of the other person
        // Friends are bidirectional
        if (p.hasFriend(this))
            p.friends.remove(this);
    }

    /**
     * Returns whether the {@link Person} is friends with the provided {@link Person}
     *
     * @param p The {@link Person} in question
     *
     * @return {@code true} if they are friends, {@code false} otherwise
     */
    public boolean hasFriend(Person p) {
        return friends.contains(p);
    }

    /**
     * Add a product to the products {@link List}
     *
     * @param p The {@link Product} to add to the {@link List}
     */
    public void buyProduct(Product p) {
        products.add(p);
    }

    /**
     * Get the {@link Gender} of the {@link Person}
     *
     * @return The {@link Gender} of the {@link Person}
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Set the {@link Gender} of the {@link Person}
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Get friends of the {@link Person}
     *
     * @return A {@link List} containing every friend
     */
    public List<Person> getFriends() {
        return friends;
    }

    /**
     * Get every owned {@link Product} of the {@link Person}
     *
     * @return A {@link List} containing every {@link Product}
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Get every {@link Company} the {@link Person} owns a {@link Product} of
     *
     * @return A {@link List} containing every {@link Company}
     */
    public List<Company> getCompanies() {
        List<Company> temp = new ArrayList<>();
        // Go through every owned product and add the company which produced it
        for (Product p : products)
            temp.add(p.getMadeBy());
        return temp;
    }
}
