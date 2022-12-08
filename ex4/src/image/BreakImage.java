package image;

import java.awt.*;
import java.util.Objects;

public class BreakImage implements Image {


    private final int width;
    private final int height;
    private final Image image;
    private final int x;
    private final int y;

    public BreakImage(Image image, int x, int y, int width, int height) {
        if (checkArgs(image, x, y, width, height)) {
            throw new IllegalArgumentException();
        }
        this.width = width;
        this.height = height;

        if (!(image instanceof BreakImage)){
            this.image = image;
            this.x = x;
            this.y = y;
        }
        else{
            var tmpImage = (BreakImage)image;
            this.x = tmpImage.x + x;
            this.y = tmpImage.y + y;
            this.image = tmpImage.image;
        }
    }

    @Override
    public Color getPixel(int x, int y) {
        if (checkBorders(x, y)){
            throw new IndexOutOfBoundsException();
        }
        return image.getPixel(this.x + x, this.y + y);
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
    public boolean equals(Object obj) {
        if (!(obj instanceof  BreakImage)){
            return false;
        }
        BreakImage tmpObj = (BreakImage) obj;
        return this .image.equals((tmpObj.image))
                && this.width == tmpObj.getWidth() && this.height == tmpObj.getHeight()
                && this.x == tmpObj.x && this.y == tmpObj.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(image.hashCode(), x, y, width, height);
    }

    private boolean checkBorders(int x, int y) {
        return x < 0 || y < 0 || x >= getWidth() || y >= getHeight();
    }

    private static boolean checkArgs(Image image, int x, int y, int width, int height) {
        return x < 0 || y < 0 || width <= 0 || height <= 0 ||
                x + width > image.getWidth() || y + height > image.getHeight();
    }
}
