import java.util.Random;

public class WhateverPlayer implements Player {
    // =========== constants ===========
    private final int ROW = 0;
    private final int COL = 1;

    // =========== private variables ===========
    private final Random rand = new Random();

    /**
     * Play one turn for this whatever player.
     * @param board the board to play.
     * @param mark the mark of this player.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int[] randomCell = new int[2];
        choseRandomCell(board, randomCell);
        while (!board.putMark(mark, randomCell[ROW], randomCell[COL])) {
            choseRandomCell(board, randomCell);
        }
    }

    /**
     * Choose a random cell to play.
     * @param board the board to play.
     * @param randomCell array of random 2 numbers. Represents the cell.
     */
    private void choseRandomCell(Board board, int[] randomCell) {
        randomCell[ROW] = rand.nextInt(board.getSize());
        randomCell[COL] = rand.nextInt(board.getSize());
    }
}
