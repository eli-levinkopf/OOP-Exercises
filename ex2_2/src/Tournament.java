public class Tournament {
    public static final String INVALID_PLAYER_MSG = "Choose a player, and start again\nThe " +
            "players:" + " [human, clever, whatever, genius]";
    public static final String INVALID_WIN_STREAK_MSG = "Invalid winStreak. Choose winStreak " +
            "between 2 to size," + " and start again.";
    public static final String INVALID_ARGS_MSG =
            "Please enter the arguments in the following " + "format: [round " + "count][size" +
                    "][win_streak][render target: " + "console/none][player: " + "human/whatever" +
                    "/clever/genius]X2";
    public static final int NUMBER_OF_PLAYERS = 2;
    public static final int TIES = 2;

    private static final int ARG_FOR_NUM_OF_ROUNDS = 0;
    private static final int ARG_FOR_SIZE = 1;
    private static final int ARG_FOR_WIN_STREAK = 2;
    private static final int ARG_FOR_RENDERER_TYPE = 3;
    private static final int ARG_FOR_PLAYER_1 = 4;
    private static final int ARG_FOR_PLAYER_2 = 5;
    private static final int ARGS_NUM = 6;
    private static final int FIRST_PLAYER_IDX = 0;
    private static final int SECOND_PLAYER_IDX = 1;
    private static final int DRAWS_IDX = 2;
    private static final PlayerFactory playerFactory = new PlayerFactory();
    private static final RendererFactory rendererFactory = new RendererFactory();
    private static int rounds;
    private static Renderer renderer;
    private static Player player1, player2;
    private static String playerType1;
    private static String playerType2;

    public Tournament(int rounds, Renderer renderer, Player[] players) {
        Tournament.rounds = rounds;
        Tournament.renderer = renderer;
        player1 = players[FIRST_PLAYER_IDX];
        player2 = players[SECOND_PLAYER_IDX];
    }

    public void playTournament(int size, int winStreak, String[] playerNames) {
        Game game;
        Mark winner;
        int currentRound = 1;
        int[] results = new int[3]; // [player1, player2, draw]
        while (currentRound <= rounds) {
            if (currentRound % NUMBER_OF_PLAYERS == 0) {
                game = new Game(player1, player2, size, winStreak, renderer);
            } else {
                game = new Game(player2, player1, size, winStreak, renderer);
            }
            winner = game.run();
            updateResults(results, winner, currentRound);
//            showResultsInTable(results);
            currentRound++;
        }
        showResults(results);
    }

    private void updateResults(int[] results, Mark winner, int currentRound) {
        if (currentRound % 2 == 0) {
            switch (winner) {
                case X:
                    results[FIRST_PLAYER_IDX]++;
                    break;
                case O:
                    results[SECOND_PLAYER_IDX]++;
                    break;
                default:
                    results[TIES]++;
            }
        } else {
            switch (winner) {
                case X:
                    results[SECOND_PLAYER_IDX]++;
                    break;
                case O:
                    results[FIRST_PLAYER_IDX]++;
                    break;
                default:
                    results[TIES]++;
            }
        }
    }

    private void showResultsInTable(int[] results) { // Not in API.
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
        System.out.format("######### Results #########\nPlayer 1, %s won: %d rounds\nPlayer 2, " +
                        "%s won: %d rounds\nTies: %d"
                , playerType1, results[FIRST_PLAYER_IDX], playerType2,
                results[SECOND_PLAYER_IDX], results[DRAWS_IDX]);
    }

    public static void main(String[] args) {
        if (args.length != ARGS_NUM || Integer.parseInt(args[ARG_FOR_NUM_OF_ROUNDS]) <= 0) {
            System.out.println(INVALID_ARGS_MSG);
            return;
        }
        int rounds = Integer.parseInt(args[ARG_FOR_NUM_OF_ROUNDS]);
        int size = Integer.parseInt(args[ARG_FOR_SIZE]);
        int winStreak = Integer.parseInt(args[ARG_FOR_WIN_STREAK]);
        Renderer renderer = rendererFactory.buildRenderer(args[ARG_FOR_RENDERER_TYPE], size);
        playerType1 = args[ARG_FOR_PLAYER_1];
        playerType2 = args[ARG_FOR_PLAYER_2];
        Player[] players = {playerFactory.buildPlayer(playerType1),
                playerFactory.buildPlayer(playerType2)};
        if (players[0] == null || players[1] == null){
            System.out.println(INVALID_PLAYER_MSG);
            return;
        }
        if (winStreak < 2 || winStreak > size) {
            System.out.println(INVALID_WIN_STREAK_MSG);
            return;
        }
        Tournament tournament = new Tournament(rounds, renderer, players);
        tournament.playTournament(size, winStreak, new String[]{args[ARG_FOR_PLAYER_1],
                args[ARG_FOR_PLAYER_2]});
    }
}
