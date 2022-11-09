import java.util.Scanner;

public class HumanPlayer implements Player{
    // =========== constants ===========
    public static final String NOT_A_NUMBER_MSG = "That's not a number! Please enter new " +
            "coordinates: ";

    // =========== private variables ===========
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Play one turn for this human player.
     * @param board the board to play.
     * @param mark the mark of this player.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int num;
        System.out.print("Please enter coordinates: ");
        getNumber();
        num = scanner.nextInt();
        while (!board.putMark(mark, num / 10 - 1, num % 10 - 1)) {
            System.out.print("Player " + mark + ", " + num + " Invalid " +
                    "coordinates, type again: ");
            getNumber();
            num = scanner.nextInt();
        }
    }

    /**
     * Get a number from the user.
     */
    private void getNumber() {
        while (!scanner.hasNextInt()){
            System.out.print(NOT_A_NUMBER_MSG);
            scanner.next();
        }
    }
}
