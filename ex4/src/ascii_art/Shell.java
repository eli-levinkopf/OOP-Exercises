package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_art.img_to_char.tmp;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.*;

public class Shell {

    private static final String FONT = "Courier New";
    public static final String CONSOLE_COMMAND = "console";
    public static final String RENDER_COMMAND = "render";
    public static final String RES_COMMAND = "res";
    public static final String UP_COMMAND = "up";
    public static final String DOWN_COMMAND = "down";
    public static final String REMOVE_COMMAND = "remove";
    public static final String ALL_COMMAND = "all";
    public static final String SPACE_COMMAND = "space";
    public static final String ADD_COMMAND = "add";
    public static final String CHARS_COMMAND = "chars";
    public static final String EXIT_COMMAND = "exit";
    public static final String GET_INPUT = ">>>";
    public static final String SPACE_REGEX = " ";
    public static final String REGEX = "[ -]+";
    public static final String CHANGE_RESOLUTION_MSG = "Width set to %d\n";
    public static final String EXCEEDING_BOUNDARIES_MSG = "Did not change due to exceeding " +
            "boundaries";
    public static final String REMOVE_INCORRECT_FORMAT = "Did not remove due to incorrect format";
    public static final char SPACE = ' ';
    public static final String ADD_INCORRECT_FORMAT = "Did not add due to incorrect format";
    public static final String INCORRECT_COMMAND = "Did not executed due to incorrect command";
    public static final String OUTPUT_FILENAME = "out.html";
    public static final char FIRST_NUM = '0';
    public static final char LAST_NUM = '9';
    private static final int MIN_PIXELS_PER_CHAR = 2;
    private static final int INITIAL_CHARS_IN_ROW = 64;
    public static final int SECOND_ARG = 1;
    public static final int THIRD_ARG = 2;
    public static final int FIRST_ASCII_CHAR = 32;
    public static final int LAST_ASCII_CHAR = 126;
    public static final int FIRST_ARG = 0;
    public static final int CHAR_SIZE = 1;
    public static final int ONE_ARG = 2;
    public static final int TWO_ARGS = 3;
    public static final int CHANGE_RESOLUTION_FACTOR = 2;

    private boolean console = false;
    private final Scanner scanner = new Scanner(System.in);
    private final Set<Character> characterDatabase = new HashSet<>();

    private final int minCharsInRow;
    private final int maxCharsInRow;
    private int charsInRow;
    private final BrightnessImgCharMatcher charMatcher;
    private final HtmlAsciiOutput htmlAsciiOutput;
    private final ConsoleAsciiOutput consoleAsciiOutput;

    private final tmp t;


    /**
     * Creates a new instance of Shell.
     * @param image image to work with.
     */
    public Shell(Image image) {
        minCharsInRow = Math.max(1, image.getWidth() / image.getHeight());
        maxCharsInRow = image.getWidth() / MIN_PIXELS_PER_CHAR;
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        charMatcher = new BrightnessImgCharMatcher(image, FONT);
        consoleAsciiOutput = new ConsoleAsciiOutput();
        htmlAsciiOutput = new HtmlAsciiOutput(OUTPUT_FILENAME, FONT);
        databaseInitialization();

         t = new tmp(image, FONT);

    }

    /**
     * Initialize the database with the characters 0-9.
     */
    private void databaseInitialization() {
        addCharactersToDatabase(FIRST_NUM, LAST_NUM);

    }

    /**
     * Manages the all program. Gets input from user and exports the results to the output file
     * (html) or to the console (according to the user's choice).
     */
    public void run() {
        System.out.print(GET_INPUT);
        String command = scanner.nextLine();
        while (!command.equals(EXIT_COMMAND)) {
            checkCommand(command);
            ViewingCharacterDatabase(command);
            addCharacters(command);
            removeCharacters(command);
            changeResolution(command);
            renderToConsole(command);
            renderImage(command);
            System.out.print(GET_INPUT);
            command = scanner.nextLine();
        }
    }

    /**
     * Check if the user's input is a valid command.
     * @param command the user's input.
     */
    private void checkCommand(String command) {
        String[] args = command.split(SPACE_REGEX);
        String[] validCommands = {EXIT_COMMAND, ADD_COMMAND, REMOVE_COMMAND, RES_COMMAND,
                CONSOLE_COMMAND, RENDER_COMMAND, CHARS_COMMAND};
        if (!Arrays.asList(validCommands).contains(args[FIRST_ARG])) {
            System.out.println(INCORRECT_COMMAND);
        }
    }

    /**
     * Update the filed console to true if the user asked for render the results to the console.
     * @param command the user's input.
     */
    private void renderToConsole(String command) {
        if (command.equals(CONSOLE_COMMAND)) {
            console = true;
        }
    }

    /**
     * Renders the results by using BrightnessImgCharMatcher.chooseChars.
     * @param command the user's input.
     */
    private void renderImage(String command) {
        if (command.equals(RENDER_COMMAND)) {
            Character[] characters =
                    characterDatabase.toArray(new Character[characterDatabase.size()]);
//            var chars = charMatcher.chooseChars(charsInRow, characters);
            var chars = t.chooseChars(charsInRow, characters);
            if (!console) {
                htmlAsciiOutput.output(chars);
            } else {
                consoleAsciiOutput.output(chars);
            }

        }
    }

    /**
     * Change the resolution of the output according to the user's command.
     * @param command the user's input.
     */
    private void changeResolution(String command) {
        String[] args = command.split(SPACE_REGEX);
        if (args[FIRST_ARG].equals(RES_COMMAND)) {
            if (args.length == ONE_ARG && args[SECOND_ARG].equals(UP_COMMAND)) {
                ChangeResolutionByFactor2(true);
            } else if (args.length == ONE_ARG && args[SECOND_ARG].equals(DOWN_COMMAND)) {
                ChangeResolutionByFactor2(false);
            } else {
                System.out.println(INCORRECT_COMMAND);
            }
        }
    }

    /**
     * Multiply/divide the resolution by factor 2.
     * @param multiply true if the user wants to reader the result to the console.
     */
    private void ChangeResolutionByFactor2(boolean multiply) {
        if ((!multiply && charsInRow / CHANGE_RESOLUTION_FACTOR >= minCharsInRow) ||
                (multiply && charsInRow * CHANGE_RESOLUTION_FACTOR <= maxCharsInRow)) {
            if (multiply) {
                charsInRow *= CHANGE_RESOLUTION_FACTOR;
            } else {
                charsInRow /= CHANGE_RESOLUTION_FACTOR;
            }
            System.out.printf(CHANGE_RESOLUTION_MSG, charsInRow);
        } else {
            System.out.println(EXCEEDING_BOUNDARIES_MSG);
        }
    }

    /**
     * Remove characters form database.
     * @param command the user's input.
     */
    private void removeCharacters(String command) {
        String[] args = command.split(REGEX);
        if (args[FIRST_ARG].equals(REMOVE_COMMAND)) {
            if (args.length == ONE_ARG && args[SECOND_ARG].length() == CHAR_SIZE) {
                removeSingleCharacter(args);
            } else if (args.length == ONE_ARG && args[SECOND_ARG].equals(ALL_COMMAND)) {
                removeAllCharacters();
            } else if (args.length == ONE_ARG && args[SECOND_ARG].equals(SPACE_COMMAND)) {
                removeSpaceCharacter();
            } else if (args.length == TWO_ARGS && args[SECOND_ARG].length() == CHAR_SIZE &&
                    args[THIRD_ARG].length() == CHAR_SIZE) {
                removeCharactersInRange(args);
            } else {
                System.out.println(REMOVE_INCORRECT_FORMAT);
            }
        }
    }

    /**
     * remove all characters in a specific range from the database.
     * @param args the user's input.
     */
    private void removeCharactersInRange(String[] args) {
        int firstChar = args[SECOND_ARG].charAt(0);
        int secondChar = args[THIRD_ARG].charAt(0);
        int start = Math.min(firstChar, secondChar);
        int end = Math.max(firstChar, secondChar);
        for (char c = (char) start; c <= (char) end; c++) {
            characterDatabase.remove(c);
        }
    }

    /**
     * Remove space character from database.
     */
    private void removeSpaceCharacter() {
        characterDatabase.remove(SPACE);
    }

    /**
     * Remove all characters from database.
     */
    private void removeAllCharacters() {
        characterDatabase.clear();
    }

    /**
     * remove a single character form database.
     * @param args the user's input.
     */
    private void removeSingleCharacter(String[] args) {
        characterDatabase.remove(args[SECOND_ARG].charAt(0));
    }

    /**
     * add character to database.
     * @param command the user's input.
     */
    private void addCharacters(String command) {
        String[] args = command.split(REGEX);
        if (args[FIRST_ARG].equals(ADD_COMMAND)) {
            if (args.length == ONE_ARG && args[SECOND_ARG].length() == CHAR_SIZE) {
                addSingleCharacter(args);
            } else if (args.length == ONE_ARG && args[SECOND_ARG].equals(ALL_COMMAND)) {
                addAllCharacters();
            } else if (args.length == ONE_ARG && args[SECOND_ARG].equals(SPACE_COMMAND)) {
                addSpaceCharacter();
            } else if (args.length == TWO_ARGS && args[SECOND_ARG].length() == CHAR_SIZE &&
                    args[THIRD_ARG].length() == CHAR_SIZE) {
                addCharactersInRange(args);
            } else {
                System.out.println(ADD_INCORRECT_FORMAT);
            }
        }
    }

    /**
     * Add all characters in a specific range from the database.
     * @param commands the user's input.
     */
    private void addCharactersInRange(String[] commands) {
        int firstChar = commands[SECOND_ARG].charAt(0);
        int secondChar = commands[THIRD_ARG].charAt(0);
        int start = Math.min(firstChar, secondChar);
        int end = Math.max(firstChar, secondChar);
        addCharactersToDatabase((char) start, (char) end);
    }

    /**
     * Add all characters from start to end.
     * @param start character to star from.
     * @param end character end.
     */
    private void addCharactersToDatabase(char start, char end) {
        for (char c = start; c <= end; c++) {
            characterDatabase.add(c);
        }
    }

    /**
     * Add space character.
     */
    private void addSpaceCharacter() {
        characterDatabase.add(' ');
    }

    /**
     * Add all characters to database.
     */
    private void addAllCharacters() {
        addCharactersToDatabase((char) FIRST_ASCII_CHAR, (char) LAST_ASCII_CHAR);
    }

    /**
     * add a single character to database.
     * @param commands the user's input.
     */
    private void addSingleCharacter(String[] commands) {
        characterDatabase.add(commands[SECOND_ARG].charAt(0));
    }

    /**
     * Prints the database.
     * @param command the user's input.
     */
    private void ViewingCharacterDatabase(String command) {
        if (command.equals(CHARS_COMMAND)) {
            System.out.println(Arrays.toString(characterDatabase.toArray()));
        }
    }
}
