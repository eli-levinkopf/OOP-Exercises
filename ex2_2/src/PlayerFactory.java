public class PlayerFactory {

    public Player buildPlayer(String playerType){
        switch (playerType.toLowerCase()) {
            case "human":
                return new HumanPlayer();
            case "whatever":
                return new WhateverPlayer();
            case "clever":
                return new CleverPlayer();
            case "genius":
                return new GeniusPlayer();
        }
        return null;
    }
}
