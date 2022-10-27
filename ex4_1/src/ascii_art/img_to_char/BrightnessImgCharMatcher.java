package ascii_art.img_to_char;

import java.util.Arrays;

public class BrightnessImgCharMatcher {

    public static final int NUM_OF_PIXELS_FOR_CHAR = 16;

    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {
        return null;
    }

    private float[] getBrightness(char[] charsArray) {
        float[] brightnessArray = new float[charsArray.length];
        for (int i = 0; i < brightnessArray.length; i++) {
            brightnessArray[i] = BrightnessImgChar(charsArray[i]);
        }
        return brightnessArray;
    }

    private float BrightnessImgChar(char c) {
        boolean[][] booleanMatrix = CharRenderer.getImg(c, NUM_OF_PIXELS_FOR_CHAR, "Ariel");
        int count = 0;
        for (boolean[] row : booleanMatrix) {
            for (boolean bool : row) {
                if (bool) {
                    ++count;
                }
            }
        }
        return count / (float) Math.pow(NUM_OF_PIXELS_FOR_CHAR, 2);
    }

//    debugging function
    public void helper(){
        char[] charsArray = {'a', 'b', 'c', 'd'};
        System.out.println(Arrays.toString(this.getBrightness(charsArray)));
    }

}
