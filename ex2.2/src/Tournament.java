public class Tournament {
    public static void main(String[] args) {
        Renderer renderer = new Renderer();
        Player playerO = new Player(), playerX = new Player();
        Game game = new Game(playerO, playerX, renderer);
        Cell.Mark winner = game.run();
        System.out.println("The winner is: " + winner);
    }
}
