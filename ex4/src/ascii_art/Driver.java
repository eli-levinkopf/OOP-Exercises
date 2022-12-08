package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Driver {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("USAGE: java asciiArt ");
            return;
        }
        Image img = Image.fromFile(args[0]);
        if (img == null) {
            Logger.getGlobal().severe("Failed to open image file " + args[0]);
            return;
        }
//        new Shell(img).run();



        BrightnessImgCharMatcher charMatcher = new BrightnessImgCharMatcher(img, "Ariel");
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Character[] charSet = new Character[alphabet.length];
        int i = 0;
        for (char c : alphabet) {
            charSet[i++] = c;
        }

        var chars = charMatcher.chooseChars(1024, charSet);
//        System.out.println(Arrays.deepToString(chars));

        HtmlAsciiOutput consoleAsciiOutput = new HtmlAsciiOutput("output", "arial");
        consoleAsciiOutput.output(chars);
    }
}