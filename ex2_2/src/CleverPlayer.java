public class CleverPlayer implements Player {
    private int lastRow = 0;
    private int lastCol = 0;
    private boolean playInRows = true;
    private boolean playInCols = true;
    private boolean playInDiag = true;
    private Mark lastMark;
    private final WhateverPlayer WhateverPlayer = new WhateverPlayer();

    @Override
    public void playTurn(Board board, Mark mark) {
        if (lastMark != null && lastMark != mark){
            lastRow = 0;
            lastCol = 0;
            playInRows = true;
            playInCols = true;
            playInDiag = true;
        }
        lastMark = mark;
        if (playInRows && board.putMark(mark, lastRow, lastCol)) {
            lastCol++;
        } else {
            if (playInRows){
                playInRows = false;
                lastCol = 0;
            }
            if (playInCols && board.putMark(mark, lastRow + 1, lastCol)) {
                lastRow++;
            } else {
                if (playInCols){
                    playInCols = false;
                    lastRow = 0;
                    lastCol = 0;
                }
                if(playInDiag && board.putMark(mark, lastRow + 1, lastCol+1)){
                    lastRow++;
                    lastCol++;
                }else{
                    playInDiag = false;
                    WhateverPlayer.playTurn(board, mark);
                }
            }
        }
    }
}
