package rendering;

import java.awt.image.BufferedImage;

public class ImageWallArt extends WallArt {
    private BufferedImage[] images;

    public ImageWallArt(BufferedImage[] images) {
        this.images = images;
    }

    @Override
    public ImageTexture getArt(int direction) {
        return new ImageTexture(images[direction]);
    }
}
