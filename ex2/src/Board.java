public class Board {
    public static final int SIZE = 4;
    public static final int WIN_STREAK = 3;
    private boolean gameEnded = false;
    private Cell.Mark winner = Cell.Mark.BLANK;
    private final Cell.Mark[][] board = new Cell.Mark[SIZE][SIZE];
    private int numOfEmptyCells = SIZE * SIZE;

    public Board() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = Cell.Mark.BLANK;
            }
        }
    }

    public boolean putMark(Cell.Mark mark, int row, int col) {
        if (row >= SIZE || row < 0 || col >= SIZE || col < 0 || board[row][col] != Cell.Mark.BLANK) {
            return false;
        }
        board[row][col] = mark;
        updateNumOfEmptyCells(mark);
        checkWinner(mark, row, col);
        return true;
    }

    public Cell.Mark getMark(int row, int col) {
        if (row >= SIZE || row < 0 || col >= SIZE || col < 0) {
            return Cell.Mark.BLANK;
        }
        return board[row][col];
    }

    public boolean gameEnded() {
        return gameEnded;
    }

    public Cell.Mark getWinner() {
        return winner;
    }

    private void updateNumOfEmptyCells(Cell.Mark mark) {
        if (mark != Cell.Mark.BLANK) {
            numOfEmptyCells--;
        } else {
            numOfEmptyCells++;
        }
        if (numOfEmptyCells == 0) {
            gameEnded = true;
        }
    }

    private void checkWinner(Cell.Mark mark, int row, int col) {
//      check col
        int countOfCellsToWin = 0;
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] != mark) {
                countOfCellsToWin = 0;
                continue;
            }
            countOfCellsToWin++;
            if (countOfCellsToWin == WIN_STREAK) {
                winner = mark;
                gameEnded = true;
                return;
            }
        }
//      check row
        countOfCellsToWin = 0;
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] != mark) {
                countOfCellsToWin = 0;
                continue;
            }
            countOfCellsToWin++;
            if (countOfCellsToWin == WIN_STREAK) {
                winner = mark;
                gameEnded = true;
                return;
            }
        }
//      check diagonal
        countOfCellsToWin = 0;
        int diagonalOffset = Math.abs(row - col);
        int diagonalSize = SIZE - diagonalOffset;
        for (int i = 0; i < diagonalSize && diagonalSize >= WIN_STREAK; i++) {
            if (((col - row) >= 0 && board[i][diagonalOffset + i] != mark) ||
                    ((col - row) < 0 && board[diagonalOffset + i][i] != mark)) {
                countOfCellsToWin = 0;
                continue;
            }
            countOfCellsToWin++;
            if (countOfCellsToWin == WIN_STREAK) {
                winner = mark;
                gameEnded = true;
                return;
            }
        }
//      check anti-diagonal
        countOfCellsToWin = 0;
        int antiDiagonalOffset = Math.abs(row + col - (SIZE - 1));
        diagonalSize = SIZE - antiDiagonalOffset;
        for (int i = 0; i < diagonalSize && diagonalSize >= WIN_STREAK; i++) {
            if (((col + row) < SIZE && board[SIZE - 1 - antiDiagonalOffset - i][i] != mark) ||
                    (col + row) >= SIZE && board[SIZE - 1 - i][antiDiagonalOffset + i] != mark) {
                countOfCellsToWin = 0;
                continue;
            }
            countOfCellsToWin++;
            if (countOfCellsToWin == WIN_STREAK) {
                winner = mark;
                gameEnded = true;
                return;
            }
        }
    }
}