public class Game {
    private static final int DEFAULT_SIZE = 4;
    private static final int DEFAULT_WIN_STREAK = 3;

    private final Renderer renderer;
    private final Player playerX;
    private final Player playerO;
    private final Board board;
    private final int winStreak;
    private final int size;
    private int numOfEmptyCells;
    private boolean gameEnded = false;
    private Mark winner = Mark.BLANK;


    public Game(Player playerX, Player playerO, Renderer renderer) {
        this(playerX, playerO, DEFAULT_SIZE, DEFAULT_WIN_STREAK, renderer);
    }

    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
        board = new Board(size);
        numOfEmptyCells = size * size;
        this.playerX = playerX;
        this.playerO = playerO;
        this.size = size;
        this.winStreak = winStreak;
        this.renderer = renderer;

    }

    public int getWinStreak() {
        return winStreak;
    }

    public Mark run() {
        Player[] players = {playerX, playerO};
        Mark[] marks = {Mark.X, Mark.O};
        int turn = 0;
        while (!gameEnded) {
            renderer.renderBoard(board);
            players[turn % 2].playTurn(board, marks[turn % 2]);
            updateNumOfEmptyCells(marks[turn % 2]);
            checkWinner(marks[turn % 2]);
            turn++;
//            renderer.renderBoard(board);
        }
        renderer.renderBoard(board);
        return winner;
    }

    private void checkWinner(Mark mark) {
        Mark[][] matrix = board.getMark();
        if (checkWinnerForRowsAndCols(mark, matrix, true)) { //check col
            return;
        }
        if (checkWinnerForRowsAndCols(mark,  matrix, false)) { //check row
            return;
        }
        if (checkWinnerForDiagonal(mark, matrix)){ //check diagonal (/)
            return;
        }
        checkWinnerForAntiDiagonal(mark, matrix); //check anti-diagonal (\)
    }

    private boolean checkWinnerForAntiDiagonal(Mark mark, Mark[][] matrix) {

        int countOfCellsToWin;
        int row, col;
        for (int i = size - 1; i > 0; i--) {
            countOfCellsToWin = 0;
            col = i; row = 0;
            if (size - i < winStreak) {continue;}
            while (col < size) {
                if (matrix[row][col] != mark) {
                    countOfCellsToWin = 0;
                } else {
                    countOfCellsToWin++;
                    if (countOfCellsToWin == winStreak) {
                        winner = mark;
                        gameEnded = true;
                        return true;
                    }
                }
                row++;col++;
            }
        }
        for (int i = 0; i < size; i++) {
            countOfCellsToWin = 0;
            row = i;col = 0;
            if (size - i < winStreak) {continue;}
            while (row < size) {
                if (matrix[row][col] != mark) {
                    countOfCellsToWin = 0;
                } else {
                    countOfCellsToWin++;
                    if (countOfCellsToWin == winStreak) {
                        winner = mark;
                        gameEnded = true;
                        return true;
                    }
                }
                row++;col++;
            }
        }
        return false;
    }

    private boolean checkWinnerForDiagonal(Mark mark, Mark[][] matrix) {
        int countOfCellsToWin;
        int row, col;
        for (int i = 0; i < size; i++) {
            countOfCellsToWin = 0;
            col = i; row = 0;
            if (i + 1 < winStreak) {continue;}
            while (col >= 0 && row < size) {
                if (matrix[row][col] != mark) {
                    countOfCellsToWin = 0;
                } else {
                    countOfCellsToWin++;
                    if (countOfCellsToWin == winStreak) {
                        winner = mark;
                        gameEnded = true;
                        return true;
                    }
                }
                row++; col--;
            }
        }

        for (int i = 1; i < size; i++) {
            countOfCellsToWin = 0;
            row = i; col = size - 1;
            if (size - i < winStreak) {continue;}
            while (row < size) {
                if (matrix[row][col] != mark) {
                    countOfCellsToWin = 0;
                } else {
                    countOfCellsToWin++;
                    if (countOfCellsToWin == winStreak) {
                        winner = mark;
                        gameEnded = true;
                        return true;
                    }
                }
                row++; col--;
            }
        }
        return false;
    }

    private boolean checkWinnerForRowsAndCols(Mark mark, Mark[][] matrix, boolean flagForRow) {
        int countOfCellsToWin;
        for (int i = 0; i < size; i++) {
            countOfCellsToWin = 0;
            for (int j = 0; j < size; j++) {
                if ((flagForRow && matrix[i][j] != mark) || (!flagForRow && matrix[j][i] != mark)) {
                    countOfCellsToWin = 0;
                    continue;
                }
                countOfCellsToWin++;
                if (countOfCellsToWin == winStreak) {
                    winner = mark;
                    gameEnded = true;
                    return true;
                }
            }
        }
        return false;
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
}