public class GeniusPlayer implements Player {

    // =========== private variables ===========
    private int lastRow = 0;
    private int lastCol = 0;
    private boolean playInRows = true;
    private boolean playInCols = true;
    private boolean playInDiag = true;
    private Mark lastMark;
    private boolean firstCell = true;
    private int idxForLastCell = 0;
    private final WhateverPlayer WhateverPlayer = new WhateverPlayer();

    /**
     * Play one turn for this genius player.
     * @param board the board to play.
     * @param mark the mark of this player.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        if (lastMark != null && lastMark != mark) {
            lastRow = 0;
            lastCol = 0;
            idxForLastCell = 0;
            playInRows = true;
            playInCols = true;
            playInDiag = true;
            firstCell = true;
        }
        if (!firstCell) {
            playInLastCol(board, mark);
            return;
        }
        lastMark = mark;
        if (playInRows && board.putMark(mark, lastRow, lastCol)) {
            lastCol++;
        } else {
            if (lastRow == 0 && lastCol == 0) {
                firstCell = false;
                playInLastCol(board, mark);
                return;
            }
            if (playInRows) {
                playInRows = false;
                lastCol = 0;
            }
            if (playInCols && board.putMark(mark, lastRow + 1, lastCol)) {
                lastRow++;
            } else {
                if (playInCols) {
                    playInCols = false;
                    lastRow = 0;
                    lastCol = 0;
                }
                if (playInDiag && board.putMark(mark, lastRow + 1, lastCol + 1)) {
                    lastRow++;
                    lastCol++;
                } else {
                    playInDiag = false;
                    WhateverPlayer.playTurn(board, mark);
                }
            }
        }
    }

    /**
     * Play in the last column.
     * @param board board to play.
     * @param mark player's mark.
     */
    private void playInLastCol(Board board, Mark mark) {
        board.putMark(mark, idxForLastCell, board.getSize() - 1);
        idxForLastCell++;
    }
}
