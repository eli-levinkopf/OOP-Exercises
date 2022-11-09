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
}


