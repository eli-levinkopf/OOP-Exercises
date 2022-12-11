package ascii_art.img_to_char;

import image.Image;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class BrightnessImgCharMatcher {

    public static final int NUM_OF_PIXELS_FOR_CHAR = 16;
    public static final double FACTOR_FOR_RED = 0.2126;
    public static final double FACTOR_FOR_GREEN = 0.7152;
    public static final double FACTOR_FOR_BLUE = 0.0722;
    public static final int RGB_RANGE = 255;
    public static final int POWER = 2;

    private final Image img;
    private final String font;
    private double maxBrightness = Double.MIN_VALUE;
    private double minBrightness = Double.MAX_VALUE;
    private final Map<Double, Character> brightnessToAsciiMap = new HashMap<>();
    private final HashMap<Image, Double> cache = new HashMap<>();


    public BrightnessImgCharMatcher(Image img, String font) {
        this.img = img;
        this.font = font;
    }

    /**
     * @param numCharsInRow The number of characters per line in the ascii image.
     * @param charSet       The set of characters with which we will draw the picture. T
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

    /**
     * Renders the ascii character the most suitable to the image's brightness.
     * @param subImage image to calculate suitable ascii character.
     * @return ascii character for subImage.
     */
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

    /**
     * Calculates the brightness of an image.
     * @param subImage the image to calculate the brightness.
     * @return the brightness value of the image.
     */
    private double calculateBrightnessOfSubImage(Image subImage) {
        if (cache.containsKey(subImage)) { // if the image has already been cached.
            return cache.get(subImage);
        }
        double sumOfPixels = 0;
        for (Color color : subImage.pixels()) { // if the image not in tha cache.
            sumOfPixels += (color.getRed() * FACTOR_FOR_RED + color.getGreen() * FACTOR_FOR_GREEN +
                    color.getBlue() * FACTOR_FOR_BLUE) / RGB_RANGE;
        }
        double subImageBrightness = sumOfPixels / (Math.pow(subImage.getWidth(), POWER));
        cache.put(subImage, subImageBrightness);
        return sumOfPixels / (Math.pow(subImage.getWidth(), POWER));
    }

    /**
     * Map brightness to ascii characters.
     * @param charSet set of characters.
     */
    private void mapBrightnessToAscii(Character[] charSet) {
        brightnessToAsciiMap.clear();
        for (Character character : charSet) {
            if (BrightnessImgChar(character) == BrightnessImgChar('#')){
                double c = BrightnessImgChar(character);
                int x  = 0;
            }
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
        double count = 0;
        double s = 0;
        for (boolean[] row : booleanMatrix) {
            for (boolean bool : row) {
                ++s;
                if (bool) {
                    ++count;
                }
            }
        }
//        return count / Math.pow(NUM_OF_PIXELS_FOR_CHAR, POWER);
        return  count / s;
    }

    /**
     * apply linear transformation for brightnessSet.
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

    /**
     * Calculates the minimum and maximum brightness values in the brightnessToAsciiMap.
     */
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