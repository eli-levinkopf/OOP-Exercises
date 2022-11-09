public class PlayerFactory {

    // =========== constants ===========
    public static final String HUMAN = "human";
    public static final String WHATEVER = "whatever";
    public static final String CLEVER = "clever";
    public static final String GENIUS = "genius";

    /**
     * builds a new instance of player.
     * @param playerType the type of player.
     * @return new instance of player. null if no such player.
     */
    public Player buildPlayer(String playerType){
        switch (playerType.toLowerCase()) {
            case HUMAN:
                return new HumanPlayer();
            case WHATEVER:
                return new WhateverPlayer();
            case CLEVER:
                return new CleverPlayer();
            case GENIUS:
                return new GeniusPlayer();
        }
        return null;
    }
}
