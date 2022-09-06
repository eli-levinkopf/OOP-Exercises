public class SnartypamtsPlayer implements Player {
    private static int lastRow = 0;
    private static int lastCol = 0;
    private static boolean playInRows = true;
    private static boolean playInCols = true;
    private static boolean playInDiag = true;
    private static Mark lastMark;
    private static boolean firstCell = true;
    private static int idxForLastCell = 0;
    private static final WhateverPlayer WhateverPlayer = new WhateverPlayer();

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
            if (lastRow == 0 && lastCol == 0){
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

    private void playInLastCol(Board board, Mark mark) {
        board.putMark(mark, idxForLastCell, Board.WIN_STREAK - 1);
        idxForLastCell++;
    }
}
