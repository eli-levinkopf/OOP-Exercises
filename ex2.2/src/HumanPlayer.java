import java.util.Scanner;

public class HumanPlayer implements Player{
    private static final Scanner scanner = new Scanner(System.in);
    public HumanPlayer(){}

    public void playTurn(Board board, Cell.Mark mark) {
        int num;
        System.out.print("Please enter coordinates: ");
        getNumber();
        num = scanner.nextInt();
        while (!board.putMark(mark, num / 10 - 1, num % 10 - 1)) {
            System.out.print("Your coordinates are invalid. Please enter new coordinates: ");
            getNumber();
            num = scanner.nextInt();
        }
    }

    private void getNumber() {
        while (!scanner.hasNextInt()){
            System.out.print("That's not a number! Please enter new coordinates: ");
            scanner.next();
        }
    }
}
