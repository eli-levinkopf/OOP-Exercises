package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 *
 * @author Dan Nirel
 */
class FileImage implements Image {
    private static final Color DEFAULT_COLOR = Color.WHITE;

    private final Color[][] pixelArray;
    private final int width;
    private final int height;

    public FileImage(String filename) throws IOException {
        java.awt.image.BufferedImage im = ImageIO.read(new File(filename));
        int origWidth = im.getWidth(), origHeight = im.getHeight();
        //im.getRGB(x, y)); getter for access to a specific RGB rates

        width = getNextPowerOfTwo(origWidth);
        height = getNextPowerOfTwo(origHeight);

        pixelArray = new Color[height][width];

        float padWidthX = (width - origWidth) / 2.0f;
        float padWidthY = (height - origHeight) / 2.0f;

        // zero padding the original image.
        for (int y = 0; y < height; y++) { // if pixel pixelArray[x][y] belongs to the background
            for (int x = 0; x < width; x++) {
                if (x < (int) Math.floor(padWidthX) || x >= width - Math.ceil(padWidthX) ||
                        y < (int) Math.floor(padWidthY) || y >= height - Math.ceil(padWidthY)) {
                    pixelArray[y][x] = DEFAULT_COLOR;
                } else { // if pixel pixelArray[x][y] belongs to the original image.
                    pixelArray[y][x] = new Color(im.getRGB(x-(int)Math.floor(padWidthX),
                            y-(int)Math.floor(padWidthY)));
                }
            }
        }
    }

    private static int getNextPowerOfTwo(int num) {
        return num == 1 ? 1 : Integer.highestOneBit(num - 1) * 2;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Color getPixel(int x, int y) {
        return  pixelArray[y][x];
    }

//    public Iterable<Image> breakImage(int sizeOfSubImage){
//        return Image.breakToSquareSubImage(sizeOfSubImage);
//    }

}
