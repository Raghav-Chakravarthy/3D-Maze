package rendering;

import java.awt.image.BufferedImage;

import maze.Chamber;
import utils.TextureManager;

public class ImageWallArt extends WallArt {
    private BufferedImage[] images;

    public ImageWallArt(BufferedImage[] images) {
        this.images = images;
    }

    public static ImageWallArt generateWallArtFor(Chamber chamber, int amount) {
        BufferedImage[] art = new BufferedImage[4];
        int placed = 0;
        
        for(int i = 0; i < 4; i++) {
            if(placed < amount) {
                /*
                if(chamber.getAdjacentChamber(i) == null) {
                    art[i] = TextureManager.main.nextArt();
                    placed++;
                }

                 */
            }
        }

        return new ImageWallArt(art);
    }

    @Override
    public ImageTexture getArt(int direction) {
        return new ImageTexture(images[direction]);
    }
}
