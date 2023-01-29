package oop.ex6.variable;

import oop.ex6.errorMessage.ErrorMessage;
import oop.ex6.parser.IllegalLineException;
import oop.ex6.regex.Regex;

import java.util.HashMap;
import java.util.Map;

/**
 * The Type enum represents the different types of data that can be used in the program.
 * This includes INT, DOUBLE, STRING, CHAR, and BOOLEAN.
 */
public enum Type {
    /**
     * Integer type
     */
    INT,
    /**
     * Double type
     */
    DOUBLE,
    /**
     * String type
     */
    STRING,
    /**
     * Character type
     */
    CHAR,
    /**
     * Boolean type
     */
    BOOLEAN;

    /**
     * A map that stores the mapping between the string representation of each type and its corresponding Type instance.
     */
    private static final Map<String, Type> typeMap = new HashMap<>();

    /*
     * Initialize the typeMap with the mapping of each string representation to its corresponding Type instance.
     */
    static {
        typeMap.put("int", INT);
        typeMap.put("double", DOUBLE);
        typeMap.put("String", STRING);
        typeMap.put("char", CHAR);
        typeMap.put("boolean", BOOLEAN);
    }

    /**
     * Given a string parameter, returns the corresponding Type instance by matching the string against
     * several regular expressions.
     *
     * @param parameter the input string to match
     * @return the corresponding Type instance
     */
    public static Type getType(String parameter) {
        if (parameter.matches(Regex.SINGLE_QUOTE_REGEX)) {
            return Type.CHAR;
        } else if (parameter.matches(Regex.QUOTED_STRING_REGEX)) {
            return Type.STRING;
        } else if (parameter.matches(Regex.INTEGER_NUMBER_REGEX)) {
            return Type.INT;
        } else if (parameter.matches(Regex.BOOLEAN_REGEX)) {
            return Type.BOOLEAN;
        } else if (parameter.matches(Regex.DOUBLE_NUMBER_REGEX)) {
            return Type.DOUBLE;
        }
        return null;
    }

    /**
     * Given a string, returns its corresponding Type instance by searching the typeMap.
     * If the string is not found in the map, an IllegalLineException with an error message
     * UNSUPPORTED_TYPE_ERROR is thrown.
     *
     * @param strToType the string to search for
     * @return the corresponding Type instance
     * @throws IllegalLineException if the string is not found in the typeMap
     */
    public static Type stringToType(String strToType) throws IllegalLineException {
        Type type = typeMap.get(strToType);
        if (type == null) {
            throw new IllegalLineException(ErrorMessage.UNSUPPORTED_TYPE_ERROR);
        }
        return type;
    }
}
