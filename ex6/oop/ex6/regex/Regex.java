package oop.ex6.regex;


/**
 * The Regex class provides a collection of static final fields that
 * represent commonly used regular expressions for parsing and validating
 * various types of input.
 *
 * @author Eli Levinkopf
 */
public class Regex {

    /**
     * Represents a regular expression that matches a single space.
     */
    public static final String SPACE_REGEX = " ";

    /**
     * Represents a regular expression that matches the prefix "void"
     */
    public static final String VOID_PREFIX = "void";

    /**
     * Represents a regular expression that matches the prefix "return"
     */
    public static final String RETURN_PREFIX = "return";

    /**
     * Represents a regular expression that matches the prefix "final"
     */
    public static final String FINAL_PREFIX = "final";

    /**
     * Represents a regular expression that matches a single-line comment prefix "//"
     */
    public static final String COMMENT_PREFIX = "//";

    /**
     * Represents a regular expression that matches the assignment operator "="
     */
    public static final String ASSIGNMENT_PREFIX = "=";

    /**
     * Represents a regular expression that matches an empty string
     */
    public static final String EMPTY_STRING = "";

    /**
     * Represents a regular expression that matches the semicolon ";"
     */
    public static final String SEMICOLON_PREFIX = ";";


    /**
     * Represents a regular expression that matches a variable assignment statement
     */
    public static final String VARIABLE_ASSIGNMENT_REGEX =
            "([a-zA-Z][a-zA-Z0-9]*)\\s*=\\s*(\"[^\"]*\"|[+-]?\\d+(\\.\\d+)?|true|false);";

    /**
     * Represents a regular expression that matches a variable definition statement
     */
    public static final String VARIABLE_DEFINITION_REGEX = "(?:final\\s+)?(?:int|double|String|char|boolean)\\" +
            "s+[a-zA-Z_]\\w*(?:\\s*=\\s*[^,]+?)?(?:\\s*,\\s*[a-zA-Z_]\\w*(?:\\s*=\\s*[^,]+?)?)*;|\\w+\\s*=\\s*.+?;";

    /**
     * Represents a regular expression that matches a type and variable name in a variable declaration statement
     */
    public static final String TYPE_AND_NAME_REGEX =
            "([a-zA-Z]+)\\s+((?:_[a-zA-Z]+[a-zA-Z0-9_]*)|(?:[a-zA-Z]+[a-zA-Z0-9_]*))";

    /**
     * Represents a regular expression that matches a variable declaration statement
     */
    public static final String VARIABLE_DECLARATION_REGEX = "^(final)?\\s*(int|double|String|char|boolean)\\s.*";

    /**
     * Represents a regular expression that matches the syntax for a condition line, such as an "if" or "while" statement.
     */
    public static final String CONDITION_LINE_SYNTAX = "^\\s*(while|if)\\s*\\(\\s*.*\\s*\\)\\s*\\{\\s*";

    /**
     * Represents a regular expression that matches a single-line comment.
     */
    public static final String COMMENT_REGEX = "^\\s*//.*";

    /**
     * Represents a regular expression that matches any line that contains whitespace.
     */
    public static final String WHITE_SPACE_CHECKER_REGEX = ".*\\s.*";

    /**
     * Represents a regular expression that matches data types, such as "int", "double", "String", "char", and "boolean".
     */
    public static final String DATA_TAPES_REGEX = "(int|double|String|char|boolean).*";

    /**
     * Represents a regular expression that matches a method call, including the opening and closing parentheses
     * and semicolon.
     */
    public static final String METHOD_CALL_REGEX = "^.+\\(.*\\)\\s*;";

    /**
     * Represents a regular expression that matches "if" or "while" statements.
     */
    public static final String IF_WHILE_REGEX = "^(if|while).*";

    /**
     * Represents a regular expression that matches the logical operators "&&" and "||" in condition statements.
     */
    public static final String CONDITION_PARAMS = "&&|\\|\\|";

    /**
     * Represents a regular expression that matches an assignment expression, including the equal sign and
     * semicolon at the end of the line.
     */
    public static final String ASSIGNMENT_EXPRESSION_REGEX = ".*=.*;\\s*$";

    /**
     * Represents a regular expression that matches a "return" statement, including the semicolon at the end of the line.
     */
    public static final String RETURN_STATEMENT_REGEX = "return\\s*;";

    /**
     * Represents a regular expression that matches the variable declaration and initialization syntax.
     */
    public static final String VARIABLE_DECLARATION_PARSER_REGEX = "(?<=\\s=\\s)[^,;]*";

    /**
     * Represents a regular expression that matches the closing curly brace of a scope.
     */
    public static final String REGEX_SCOPE_CLOSURE = "\\s*}\\s*";

    /**
     * Represents a regular expression that matches a comma.
     */
    public static final String COMMA_REGEX = ",";

    /**
     * Represents a regular expression that matches a legal method name, starting with a letter and containing only
     * letters, numbers, and underscores.
     */
    public static final String METHOD_NAME_REGEX = "([a-zA-Z][a-zA-Z0-9_]*)\\s*";

    /**
     * Represents a regular expression that matches a method declaration's opening parenthesis.
     */
    public static final String METHOD_SPLIT_REGEX = "\\(";

    /**
     * Represents a regular expression that matches a method declaration syntax error.
     */
    public static final String METHOD_DECLARATION_SYNTAX_ERROR = ".*\\(.*\\)[ \\t]*\\{[ \\t]*";

    /**
     * Represents a regular expression that matches an open parenthesis.
     */
    public static final String OPEN_PARENTHESES_REGEX = "[(]";

    /**
     * Represents a regular expression that matches a close parenthesis.
     */
    public static final String CLOSE_PARENTHESES_REGEX = "[)]";

    /**
     * Represents a regular expression that matches a string with only whitespace.
     */
    public static final String WHITESPACE_ONLY_REGEX = "^\\s*$";

    /**
     * Represents a regular expression that matches a legal method name.
     */
    public static final String LEGAL_METHOD_NAME = "^[a-zA-Z][a-zA-Z0-9_]*$";

    /**
     * Represents a regular expression that matches an equal sign surrounded by whitespace.
     */
    public static final String EQUAL_SIGN_SURROUNDED_BY_WHITESPACE_REGEX = "\\s*=\\s*";

    /**
     * Represents a regular expression that matches one or more whitespace characters.
     */
    public static final String WHITESPACE_REGEX = "\\s+";

    /**
     * Represents a regular expression that matches a single quoted string.
     */
    public static final String SINGLE_QUOTE_REGEX = "'.'";

    /**
     * Represents a regular expression that matches a quoted string.
     */
    public static final String QUOTED_STRING_REGEX = "\"\\s*(.*)\"\\s*";

    /**
     * Represents a regular expression that matches an integer number.
     */
    public static final String INTEGER_NUMBER_REGEX = "^[+-]?[0-9]+$";

    /**
     * Represents a regular expression that matches the strings "true" or "false".
     */
    public static final String BOOLEAN_REGEX = "(true)|(false)";

    /**
     * Represents a regular expression that matches a double number.
     */
    public static final String DOUBLE_NUMBER_REGEX = "(\\-)?\\d*\\.?\\d*";

}