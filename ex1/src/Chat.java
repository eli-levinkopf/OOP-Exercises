import java.util.Scanner;

class Chat {
    static final String STATEMENT_TO_START = "Hello";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String statement = STATEMENT_TO_START;
        String[] repliesToIllegalRequestBot1 =
                {"say what? " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER + "? " +
                        "what’s " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER +
                        "?", "whaaat??", "Now I'm tired! So I don't " +
                        "understand you", "I don't know how to say " +
                                ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER +
                                ", try something else"};
        String[] repliesToIllegalRequestBot2 =
                {"I don't want to say " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER,
                        "I don't like to say " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER +
                                ", but for you I say this: " +
                                ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER, "whaaat??"};
        String[] repliesToLegalRequestBot1 =
                {"say " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER + "? okay: "
                        + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER, "okay, " +
                        "here goes: " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER};
        String[] repliesToLegalRequestBot2 =
                {ChatterBot.REQUESTED_PHRASE_PLACEHOLDER + " is my favorite " +
                        "thing to say. Here: " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER,
                        "You want me to say " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER +
                         ", do you? alright: " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER};

        ChatterBot[] bots = {new ChatterBot("Bot1", repliesToLegalRequestBot1
                , repliesToIllegalRequestBot1), new ChatterBot("Bot2",
                repliesToLegalRequestBot2, repliesToIllegalRequestBot2)};

        for (int i = 0; ; ++i) {
            statement = bots[i % bots.length].replyTo(statement);
            System.out.print(bots[i % bots.length].getName() + ": " + statement);
            scanner.nextLine();
        }
    }
}