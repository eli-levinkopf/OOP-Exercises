package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_art.img_to_char.CharRenderer;

import java.util.Arrays;

public class Driver {
    public static void main(String[] args){
//        boolean[][] cs = CharRenderer.getImg('c', 16, "Ariel");
//        CharRenderer.printBoolArr(cs);
        BrightnessImgCharMatcher brightnessImgCharMatcher = new BrightnessImgCharMatcher();
        brightnessImgCharMatcher.helper();

    }
}
