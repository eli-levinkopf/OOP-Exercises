package image;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
class ImageIterableProperty<T> implements Iterable<T> {
    private final Image img;
    private final BiFunction<Integer, Integer, T> propertySupplier;
    private final int addToX;
    private final int addToY;

    public ImageIterableProperty(Image img, BiFunction<Integer, Integer, T> propertySupplier,
                                 int addToX, int addToY) {
        this.img = img;
        this.propertySupplier = propertySupplier;
        this.addToX = addToX;
        this.addToY = addToY;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int x = 0, y = 0;

            @Override
            public boolean hasNext() {
                return y < img.getHeight();
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                var next = propertySupplier.apply(x, y);
                x += addToX;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += addToY;
                }
                return next;
            }
        };
    }

}
