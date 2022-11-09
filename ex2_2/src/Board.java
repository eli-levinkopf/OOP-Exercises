import java.util.ArrayList;

public class Board {
    public static int SIZE = 4; //TODO: public only for tests
    public static int WIN_STREAK = 3; //TODO: public only for tests

    private final Mark[][] board;
    private final int size;

    public Board() {
        this(SIZE);
    }

    public Board(int size){
        this.size = size;
        board = new Mark[size][size];
//        numOfEmptyCells = this.size * this.size;
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                board[row][col] = Mark.BLANK;
            }
        }
    }

    /**
     * @return the size of the board.
     */
    public int getSize(){
        return size;
    }

    /**
     * @return the 2-D array that represents the board.
     */
    public Mark[][] getMark(){
        return board;
    }

    /**
     * Try to put the ceil board[row][column] as mark.
     * @param mark The mark to try to put into board[row][column].
     * @param row Number of the row.
     * @param col Number of the column.
     * @return True if successful and false otherwise.
     */
    public boolean putMark(Mark mark, int row, int col) {
        if (row >= size || row < 0 || col >= size || col < 0 || board[row][col]
                != Mark.BLANK) {
            return false;
        }
        board[row][col] = mark;
//        updateNumOfEmp tyCells(mark);
//        checkWinner(mark, row, col);
        return true;
    }

    /**
     * @param row Number of the row.
     * @param col Number of the column.
     * @return the mark in board[row][col] ceil.
     */
    public Mark getMark(int row, int col) {
        if (row >= size || row < 0 || col >= size || col < 0) {
            return Mark.BLANK;
        }
        return board[row][col];
    }

//    public boolean gameEnded() { // TODO
//        return gameEnded;
//    }
//
//    public Mark getWinner() { // TODO
//        return winner;
//    }

//    private void updateNumOfEmptyCells(Mark mark) {
//        if (mark != Mark.BLANK) {
//            numOfEmptyCells--;
//        } else {
//            numOfEmptyCells++;
//        }
//        if (numOfEmptyCells == 0) {
//            gameEnded = true;
//        }
//    }
//
//    private boolean checkWinnerForRowsAndCols(Mark mark, int row, int col, boolean flagForRow) {
//        int countOfCellsToWin = 0;
//        for (int i = 0; i < size; i++) {
//            if ((flagForRow && board[row][i] != mark) || (!flagForRow && board[i][col] != mark)) {
//                countOfCellsToWin = 0;
//                continue;
//            }
//            countOfCellsToWin++;
//            if (countOfCellsToWin == Game.winStreak) {
//                winner = mark;
//                gameEnded = true;
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void checkWinner(Mark mark, int row, int col) {
////      check col
//        if (checkWinnerForRowsAndCols(mark, row, col, true)) {
//            return;
//        }
////      check row
//        if (checkWinnerForRowsAndCols(mark, row, col, false)) {
//            return;
//        }
////      check diagonal
//        int countOfCellsToWin = 0;
//        int diagonalOffset = Math.abs(row - col);
//        int diagonalSize = size - diagonalOffset;
//        for (int i = 0; i < diagonalSize && diagonalSize >= Game.winStreak; i++) {
//            if (((col - row) >= 0 && board[i][diagonalOffset + i] != mark) ||
//                    ((col - row) < 0 && board[diagonalOffset + i][i] != mark)) {
//                countOfCellsToWin = 0;
//                continue;
//            }
//            countOfCellsToWin++;
//            if (countOfCellsToWin == Game.winStreak) {
//                winner = mark;
//                gameEnded = true;
//                return;
//            }
//        }
////      check anti-diagonal
//        countOfCellsToWin = 0;
//        int antiDiagonalOffset = Math.abs(row + col - (size - 1));
//        diagonalSize = size - antiDiagonalOffset;
//        for (int i = 0; i < diagonalSize && diagonalSize >= Game.winStreak; i++) {
//            if (((col + row) < size && board[size - 1 - antiDiagonalOffset - i][i] != mark) ||
//                    (col + row) >= size && board[size - 1 - i][antiDiagonalOffset + i] != mark) {
//                countOfCellsToWin = 0;
//                continue;
//            }
//            countOfCellsToWin++;
//            if (countOfCellsToWin == Game.winStreak) {
//                winner = mark;
//                gameEnded = true;
//                return;
//            }
//        }
//    }
}


