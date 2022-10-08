package util;

import model.Parent;

import java.util.Comparator;

/**
 * A Comparator comparing subclasses of {@link Parent}
 *
 * @param <T> A TypeParameter restricting this class to children of the superclass {@link Parent}
 */
public class CustomComparator<T extends Parent> implements Comparator<T> {
    /**
     * Compares subclasses of {@link Parent} by using their {@linkplain Parent#getName()} method
     * Use {@linkplain String#toLowerCase()} to implement correct sorting order
     *
     * @param o1 First object
     * @param o2 Second object
     *
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
     *         
     * @see Comparator#compare(Object, Object)
     */
    @Override
    public int compare(T o1, T o2) {
        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
    }
}
