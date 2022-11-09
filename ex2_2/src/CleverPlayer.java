import java.util.Random;

public class CleverPlayer implements Player {

    // =========== private variables ===========
    private final Random random = new Random();
    private final WhateverPlayer WhateverPlayer = new WhateverPlayer();

    /**
     * Play one turn for this clever player.
     * @param board the board to play.
     * @param mark the mark of this player.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        for (int row = 0; row < board.getSize(); ++row) {
            for (int col = 0; col < board.getSize(); ++col) {
                if (board.getMark(row, col) == mark) {
                    if (board.putMark(mark, row + getRandomDirection(),
                            col + getRandomDirection())) {
                        return;
                    }
                }
            }
        }
        WhateverPlayer.playTurn(board, mark);
    }

    /**
     * @return random number between -1 to 1. The number represents direction.
     */
    private int getRandomDirection() {
        int rand = random.nextInt(3);
        switch (rand) {
            case 0:
                return -1;
            case 1:
                return -0;
            case 2:
                return 1;
        }
        return 0;
    }
}
