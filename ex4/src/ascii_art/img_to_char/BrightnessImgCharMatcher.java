package ascii_art.img_to_char;

import image.Image;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class BrightnessImgCharMatcher {

    public static final int NUM_OF_PIXELS_FOR_CHAR = 16;
    private final Image img;
    private final String font;
    double maxBrightness = Double.MIN_VALUE;
    double minBrightness = Double.MAX_VALUE;
    private final Map<Double, Character> brightnessToAsciiMap = new HashMap<>();


    public BrightnessImgCharMatcher(Image img, String font) {
        this.img = img;
        this.font = font;
    }

    /**
     * @param numCharsInRow The set of characters with which we will draw the picture.
     * @param charSet       The number of characters per line in the ascii image.
     * @return 2-dimensional array of Ascii characters representing the image.
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {
        int sizeOfSubImage = img.getWidth() / numCharsInRow;
        char[][] asciiImage =
                new char[img.getHeight() / sizeOfSubImage][img.getWidth() / sizeOfSubImage];
        mapBrightnessToAscii(charSet);
        Iterable<Image> subImageIter = img.breakToSquareSubImage(sizeOfSubImage);
        Iterator<Image> iterator = subImageIter.iterator();
        for (int y = 0; y < asciiImage.length; y++) {
            for (int x = 0; x < asciiImage[y].length; x++) {
                asciiImage[y][x] = getAsciiFromSubImage(iterator.next());
            }
        }
        return asciiImage;
    }

    private char getAsciiFromSubImage(Image subImage) {
        double subImageBrightness = calculateBrightnessOfSubImage(subImage);
        double minimalDistance = 1;
        double closestBrightness = 0;
        for (double brightness : brightnessToAsciiMap.keySet()) {
            if (Math.abs(subImageBrightness - brightness) < minimalDistance) {
                minimalDistance = Math.abs(subImageBrightness - brightness);
                closestBrightness = brightness;
            }
        }
        return brightnessToAsciiMap.get(closestBrightness);
    }

    private double calculateBrightnessOfSubImage(Image subImage) {
        double sumOfPixels = 0;
        for (Color color : subImage.pixels()) {
            sumOfPixels += (color.getRed() * 0.2126 + color.getGreen() * 0.7152 + color.getBlue() * 0.0722) / 255;
        }
        return sumOfPixels / (Math.pow(subImage.getWidth(), 2));
    }

    private void mapBrightnessToAscii(Character[] charSet) {
        for (Character character : charSet) {
            brightnessToAsciiMap.put(BrightnessImgChar(character), character);
        }
        linearTransformationForBrightness();
    }

    /**
     * @param c characters to calculate the brightness
     * @return the brightness value of the characters
     */
    private double BrightnessImgChar(Character c) {
        boolean[][] booleanMatrix = CharRenderer.getImg(c, NUM_OF_PIXELS_FOR_CHAR, font);
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
     */
    private void linearTransformationForBrightness() {
        getMinAndMaxBrightness();
        Map<Double, Character> tmpMap = new HashMap<>(brightnessToAsciiMap);
        for (double brightness : tmpMap.keySet()) {
            brightnessToAsciiMap.put(linearTransformation(brightness),
                    brightnessToAsciiMap.remove(brightness));
        }
    }

    /**
     * Computes the linear transformation of the given brightness value.
     *
     * @param brightness the brightness value to be computed.
     * @return The linear transformation of the given brightness value
     */
    private double linearTransformation(double brightness) {
        if (minBrightness == maxBrightness) {
            return 0f;
        }
        return (brightness - minBrightness) / (maxBrightness - minBrightness);
    }

    private void getMinAndMaxBrightness() {
        for (double brightens : brightnessToAsciiMap.keySet()) {
            if (brightens < minBrightness) {
                minBrightness = brightens;
            } else if (brightens > maxBrightness) {
                maxBrightness = brightens;
            }
        }
    }
}
