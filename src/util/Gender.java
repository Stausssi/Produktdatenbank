package util;

import util.exceptions.UnknownGenderException;

/**
 * An {@link Enum} to represents all supported genders
 */
public enum Gender {
    /**
     * Represents a male person
     */
    MALE,

    /**
     * Represents a female person
     */
    FEMALE;

    /**
     * Converts a {@link String} to a {@link Gender}
     *
     * @param s The {@link String} which should be converted to a {@link Gender}
     *
     * @return The associated {@link Gender}
     * @throws UnknownGenderException If the provided {@link String} can't be parsed to a gender
     */
    public static Gender getGenderFromString(String s) throws UnknownGenderException {
        // Remove leading and trailing spaces
        s = s.strip();

        if ("male".equalsIgnoreCase(s))
            return MALE;
        else if ("female".equalsIgnoreCase(s))
            return FEMALE;
        else
            throw new UnknownGenderException("Das angegebene Geschlecht '" + s + "' kann nicht verarbeitet werden.");
    }
}
