public class Board {
    public static  int SIZE = 4;
    public static  int WIN_STREAK = 3;
    private boolean gameEnded = false;
    private Mark winner = Mark.BLANK;
    private final Mark[][] board = new Mark[SIZE][SIZE];
    private int numOfEmptyCells = SIZE * SIZE;

    public Board() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = Mark.BLANK;
            }
        }
    }

    public boolean putMark(Mark mark, int row, int col) {
        if (row >= SIZE || row < 0 || col >= SIZE || col < 0 || board[row][col] != Mark.BLANK) {
            return false;
        }
        board[row][col] = mark;
        updateNumOfEmptyCells(mark);
        checkWinner(mark, row, col);
        return true;
    }

    public Mark getMark(int row, int col) {
        if (row >= SIZE || row < 0 || col >= SIZE || col < 0) {
            return Mark.BLANK;
        }
        return board[row][col];
    }

    public boolean gameEnded() {
        return gameEnded;
    }

    public Mark getWinner() {
        return winner;
    }

    private void updateNumOfEmptyCells(Mark mark) {
        if (mark != Mark.BLANK) {
            numOfEmptyCells--;
        } else {
            numOfEmptyCells++;
        }
        if (numOfEmptyCells == 0) {
            gameEnded = true;
        }
    }

    private boolean checkWinnerForRowsAndCols(Mark mark, int row, int col, boolean flagForRow) {
        int countOfCellsToWin = 0;
        for (int i = 0; i < SIZE; i++) {
            if ((flagForRow && board[row][i] != mark) || (!flagForRow && board[i][col] != mark)) {
                countOfCellsToWin = 0;
                continue;
            }
            countOfCellsToWin++;
            if (countOfCellsToWin == WIN_STREAK) {
                winner = mark;
                gameEnded = true;
                return true;
            }
        }
        return false;
    }

    private void checkWinner(Mark mark, int row, int col) {
//      check col
        if (checkWinnerForRowsAndCols(mark, row, col, true)) {
            return;
        }
//      check row
        if (checkWinnerForRowsAndCols(mark, row, col, false)) {
            return;
        }
//      check diagonal
        int countOfCellsToWin = 0;
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

