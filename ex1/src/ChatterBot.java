import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 *
 * @author Dan Nirel
 */

class ChatterBot {
    static final String REQUEST_PREFIX = "say ";
    static final String REQUESTED_PHRASE_PLACEHOLDER = "<phrase>";
    static final String ILLEGAL_REQUEST_PLACEHOLDER = "<request>";

    Random rand = new Random();
    String[] repliesToIllegalRequest, repliesToLegalRequest;
    String name;

    ChatterBot(String name, String[] repliesToLegalRequest,
               String[] repliesToIllegalRequest) {
        this.name = name;
        this.repliesToLegalRequest = new String[repliesToLegalRequest.length];
        this.repliesToIllegalRequest =
                new String[repliesToIllegalRequest.length];

        System.arraycopy(repliesToLegalRequest, 0, this.repliesToLegalRequest
                , 0, repliesToLegalRequest.length);
        System.arraycopy(repliesToIllegalRequest, 0,
                this.repliesToIllegalRequest, 0,
                repliesToIllegalRequest.length);
    }

    String replyTo(String statement) {
        if (statement.startsWith(REQUEST_PREFIX)) {
            return replacePlaceholderInARandomPattern(repliesToLegalRequest,
                    REQUESTED_PHRASE_PLACEHOLDER,
                    statement.replaceFirst(REQUEST_PREFIX, ""));
        }
        return replacePlaceholderInARandomPattern(repliesToIllegalRequest,
                ILLEGAL_REQUEST_PLACEHOLDER, statement);
    }

    String replacePlaceholderInARandomPattern(String[] possibleResponses,
                                              String placeholder,
                                              String replaceTo) {
        int randomIndex = rand.nextInt(possibleResponses.length);
        String responsePattern = possibleResponses[randomIndex];
        return responsePattern.replaceAll(placeholder, replaceTo);
    }

    String getName() {
        return name;
    }
}