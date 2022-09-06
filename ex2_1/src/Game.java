public class Game {
    private final Renderer renderer;
    private final Player playerX;
    private final Player playerO;

    public Game(Player playerX, Player playerO, Renderer renderer){
        this.renderer = renderer;
        this.playerX = playerX;
        this. playerO = playerO;
    }

    private Mark run(){
        Board board = new Board();
        Player[] players = {playerX, playerO};
        Mark[] marks = {Mark.X, Mark.O};
        int turn = 0;
        while(!board.gameEnded()){
            renderer.renderBoard(board);
            players[turn%2].playTurn(board, marks[turn%2]);
            turn++;
//            renderer.renderBoard(board);
        }
        renderer.renderBoard(board);
        return board.getWinner();
    }

    public static void main(String[] args) {
        Renderer renderer = new Renderer();
        Player playerO = new Player(), playerX = new Player();
        Game game = new Game(playerO, playerX, renderer);
        Mark winner = game.run();
        System.out.println("The winner is: " + winner);
    }
}