public class PlayerFactory {

    public Player buildPlayer(String playerType){
        return switch (playerType.toLowerCase()) {
            case "human" -> new HumanPlayer();
            case "whatever" -> new WhateverPlayer();
            case "clever" -> new CleverPlayer();
            case "genius" -> new GeniusPlayer();
            default -> null;
        };
    }
}
