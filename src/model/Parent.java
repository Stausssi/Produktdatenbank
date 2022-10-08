package model;

/**
 * A superclass to handle basic operations
 */
public class Parent {
    /**
     * Represents the unique {@code id}
     */
    private int id;

    /**
     * Represents the {@code name}
     */
    private String name;

    /**
     * Handles {@code id} and {@code name} initialisation
     *
     * @param id The {@code id}
     * @param name The {@code name}
     */
    public Parent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Get the {@code id}
     *
     * @return The {@code id}
     */
    public int getId() {
        return id;
    }

    /**
     * Set the {@code id}
     *
     * @param id The new {@code id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the {@code name}
     *
     * @return The {@code name}
     */
    public String getName() {
        return name;
    }

    /**
     * Set the {@code name}
     *
     * @param name The new {@code name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Override {@link Object#toString()} and use the {@code id} and {@code name} for it
     *
     * @return A {@link String} containing both {@code id} and {@code name}
     */
    @Override
    public String toString() {
        return id + ": " + name;
    }
}
