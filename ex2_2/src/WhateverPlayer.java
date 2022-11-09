import java.util.Random;

public class WhateverPlayer implements Player {
    private final int ROW = 0;
    private final int COL = 1;
    private final Random rand = new Random();

    @Override
    public void playTurn(Board board, Mark mark) {
        int[] randomCell = new int[2];
        choseRandomCell(board, randomCell);
        while (!board.putMark(mark, randomCell[ROW], randomCell[COL])) {
            choseRandomCell(board, randomCell);
        }
    }

    private void choseRandomCell(Board board, int[] randomCell) {
        randomCell[ROW] = rand.nextInt(board.getSize());
        randomCell[COL] = rand.nextInt(board.getSize());
    }
}
