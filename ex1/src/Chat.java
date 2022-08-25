import java.util.Scanner;

class Chat{
    public static void main(String[] args){
        String[] repliesToIllegalRequestBot1 = {"what", "say should I say"};
        String[] repliesToIllegalRequestBot2 = {"whaaat", "say say"};
        ChatterBot[] bots = {new ChatterBot(repliesToIllegalRequestBot1), new ChatterBot(repliesToIllegalRequestBot2)};
        Scanner scanner = new Scanner(System.in);
        String statement = "Hello";

        for (int i = 0; ; ++i){
            statement = bots[i% bots.length].replyTo(statement);
            System.out.print(statement);
            scanner.nextLine();
        }
    }
}