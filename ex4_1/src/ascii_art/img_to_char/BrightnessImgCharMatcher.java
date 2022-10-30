package ascii_art.img_to_char;

import java.util.Arrays; //TODO: remove this import

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

public class BrightnessImgCharMatcher {

    public static final int NUM_OF_PIXELS_FOR_CHAR = 16;
//    private final Image image;
//
//    public BrightnessImgCharMatcher(Image image){
//        this.image = image;
//    }

    /**
     * @param numCharsInRow The set of characters with which we will draw the picture.
     * @param charSet       The number of characters per line in the ascii image.
     * @return 2-dimensional array of Ascii characters representing the image.
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {
        float[] brightnessSet = getBrightness(charSet);
        linearTransformationForBrightnessSet(brightnessSet);

        return null;
    }

    /**
     * @param charSet array of characters.
     * @return array that contains the brightness of the characters.
     */
    private float[] getBrightness(Character[] charSet) {
        float[] brightnessSet = new float[charSet.length];
        for (int i = 0; i < brightnessSet.length; i++) {
            brightnessSet[i] = BrightnessImgChar(charSet[i]);
        }
        return brightnessSet;
    }

    /**
     * @param c characters to calculate the brightness
     * @return the brightness value of the characters
     */
    private float BrightnessImgChar(Character c) {
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

    /**
     * apply linear transformation for brightnessSet
     *
     * @param brightnessSet brightness of the charactersSet to apply transformation to.
     */
    private void linearTransformationForBrightnessSet(float[] brightnessSet) {
        float maxBrightness = MIN_VALUE;
        float minBrightness = MAX_VALUE;
        for (float brightens : brightnessSet) {
            if (brightens < minBrightness) {
                minBrightness = brightens;
            } else if (brightens > maxBrightness) {
                maxBrightness = brightens;
            }
        }
        for (int i = 0; i < brightnessSet.length; i++) {
            brightnessSet[i] = linearTransformation(brightnessSet[i], minBrightness, maxBrightness);
        }
    }

    /**
     * Computes the linear transformation of the given brightness value.
     *
     * @param brightness    the brightness value to be computed.
     * @param minBrightness The minimum brightness value in the brightness set.
     * @param maxBrightness The maximum brightness value in the brightness set.
     * @return The linear transformation of the given brightness value
     */
    private float linearTransformation(float brightness, float minBrightness, float maxBrightness) {
        if (minBrightness == maxBrightness) {
            return 0f;
        }
        return (brightness - minBrightness) / (maxBrightness - minBrightness);
    }


    //    debugging function
    public void helper() {
        Character[] charsArray = {'a', 'b', 'c', 'd'};
        float[] brightnessSet = getBrightness(charsArray);
        System.out.println(Arrays.toString(brightnessSet));
        linearTransformationForBrightnessSet(brightnessSet);
        System.out.println(Arrays.toString(brightnessSet));
    }

}
