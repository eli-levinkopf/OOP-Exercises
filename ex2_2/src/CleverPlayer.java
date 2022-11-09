import java.util.Random;

public class CleverPlayer implements Player {

    private final Random random = new Random();
    private final WhateverPlayer WhateverPlayer = new WhateverPlayer();

    @Override
    public void playTurn(Board board, Mark mark) {
        for (int row = 0; row < board.getSize(); ++row) {
            for (int col = 0; col < board.getSize(); ++col) {
                if (board.getMark(row, col) == mark) {
                    if (board.putMark(mark, row + random.nextInt(-1, 2),
                            col + random.nextInt(-1, 2))) {
                        return;
                    }
                }
            }
        }
        WhateverPlayer.playTurn(board, mark);
    }
}
