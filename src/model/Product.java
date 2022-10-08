package model;

/**
 * A subclass of {@link Parent} representing a product
 */
public class Product extends Parent {
    /**
     * The {@link Company producer} of this {@link Product}
     */
    private Company madeBy;

    /**
     * Creates a new instance with the given params
     *
     * @param id   The {@code id} of the {@link Product}
     * @param name The {@code name} of the {@link Product}
     */
    public Product(int id, String name) {
        // Let superclass take care of the initialisation
        super(id, name);
    }

    /**
     * Get the {@link Company} this {@link Product} was made by
     *
     * @return The {@link Company producer} of this {@link Product}
     */
    public Company getMadeBy() {
        return madeBy;
    }

    /**
     * Set the {@link Company} this {@link Product} was made by
     */
    public void setMadeBy(Company madeBy) {
        this.madeBy = madeBy;
    }
}
