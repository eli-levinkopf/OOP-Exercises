public class PlayerFactory {

    public Player buildPlayer(String playerType){
        return switch (playerType) {
            case "human" -> new HumanPlayer();
            case "whatever" -> new WhateverPlayer();
            case "clever" -> new CleverPlayer();
            case "snartypamts" -> new SnartypamtsPlayer();
            default -> null;
        };
    }
}
