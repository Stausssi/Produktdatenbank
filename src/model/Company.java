package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A subclass of {@link Parent} representing a company
 */
public class Company extends Parent {

    /**
     * A {@link List} containing every {@link Product} this {@link Company} has made
     */
    private final List<Product> products;

    /**
     * Creates a new instance with the given params
     *
     * @param id   The {@code id} of the {@link Company}
     * @param name The {@code name} of the {@link Company}
     */
    public Company(int id, String name) {
        // Let superclass take care of the initialisation
        super(id, name);

        // Init products list
        products = new ArrayList<>();
    }

    /**
     * Add a {@link Product} to the {@link List}
     *
     * @param p The {@link Product} to add to the {@link List}
     */
    public void addProduct(Product p) {
        products.add(p);
    }

    /**
     * Remove a {@link Product} from the {@link List}
     */
    public void removeProduct(Product p) {
        products.remove(p);
    }

    /**
     * Get every {@link Product} of this {@link Company}
     */
    public List<Product> getProducts() {
        return products;
    }
}
