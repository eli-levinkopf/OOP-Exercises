public interface Player {
    /**
     * Play one turn for current player.
     * @param board the board to play.
     * @param mark the mark of this player.
     */
    void playTurn(Board board, Mark mark);

}
