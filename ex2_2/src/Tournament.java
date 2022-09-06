public class Tournament {

    private static final int ARG_FOR_NUM_OF_ROUNDS = 0;
    private static final int ARG_FOR_RENDERER_TYPE = 1;
    private static final int ARG_FOR_PLAYER_1 = 2;
    private static final int ARG_FOR_PLAYER_2 = 3;
    private static final int ARGS_NUM = 4;
    private static final int FIRST_PLAYER_IDX = 0;
    private static final int SECOND_PLAYER_IDX = 1;
    private static final int DRAWS_IDX = 2;
    private static final PlayerFactory playerFactory = new PlayerFactory();
    private static final RendererFactory rendererFactory = new RendererFactory();
    private static int rounds;
    private static Renderer renderer;
    private static Player player1, player2;

    public Tournament(int rounds, Renderer renderer, Player[] players) {
        Tournament.rounds = rounds;
        Tournament.renderer = renderer;
        player1 = players[0];
        player2 = players[1];
    }

    public void playTournament() {
        Game game;
        Mark winner;
        int currentRound = 1;
        int[] results = new int[3]; // [player1, player2, draw]
        while (currentRound <= rounds) {
            if (currentRound % 2 == 0) {
                game = new Game(player1, player2, renderer);
            } else {
                game = new Game(player2, player1, renderer);
            }
            winner = game.run();
//            System.out.printf("The winner is %s.\n", winner);
            updateResults(results, winner, currentRound);
//            showResultsInTable(results);
            currentRound++;
        }
        showResults(results);
    }

    private void updateResults(int[] results, Mark winner, int currentRound) {
        if (currentRound % 2 == 0) {
            switch (winner) {
                case X -> results[0]++;
                case O -> results[1]++;
                default -> results[2]++;
            }
        } else {
            switch (winner) {
                case X -> results[1]++;
                case O -> results[0]++;
                default -> results[2]++;
            }
        }
    }

    private void showResultsInTable(int[] results) {
        String leftAlignFormat = "| %-15s | %-6d |%n";
        System.out.format("+-----------------+--------+%n");
        System.out.format("|  Player name    | # wins |%n");
        System.out.format("+-----------------+--------+%n");
        for (int i = 1; i < 3; i++) {
            System.out.format(leftAlignFormat, "Player" + i, results[i - 1]);
        }
        System.out.format(leftAlignFormat, "draw", results[2]);
        System.out.format("+-----------------+--------+%n");
    }

    private void showResults(int[] results) {
        System.out.format("Player 1: %d | Player 2: %d | Draws: %d \n", results[FIRST_PLAYER_IDX],
                results[SECOND_PLAYER_IDX], results[DRAWS_IDX]);

    }

    public static void main(String[] args) {
        if (args.length != ARGS_NUM || Integer.parseInt(args[ARG_FOR_NUM_OF_ROUNDS]) <= 0) {
            System.err.println("Please enter the arguments in the following format: <round count (positive int)> " +
                    "<render target: console/none> <player: human/clever/whatever/...> X 2");
            return;
        }
        int rounds = Integer.parseInt(args[ARG_FOR_NUM_OF_ROUNDS]);
        Renderer renderer = rendererFactory.buildRenderer(args[ARG_FOR_RENDERER_TYPE]);
        Player[] players = {playerFactory.buildPlayer(args[ARG_FOR_PLAYER_1]), playerFactory.buildPlayer(args[ARG_FOR_PLAYER_2])};
        if (players[0] == null || players[1] == null) {
            System.err.println("Unable to create at least one of the players. Please try again.\n The correct format is:" +
                    " <round count (positive int)> <render target: console/none> <player: human/clever/whatever/...> X 2");
            return;
        }
        Tournament tournament = new Tournament(rounds, renderer, players);
        tournament.playTournament();
    }
}