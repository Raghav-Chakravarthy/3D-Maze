package utils;

import java.util.HashMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import rendering.ImageTexture;

public class TextureManager {
    public static final TextureManager main = new TextureManager();
    private HashMap<String, ImageTexture> textures;

    public TextureManager() {
        
    }

    public void loadTextureFromDisk(String path, String textureName) throws IOException {
        BufferedImage source = ImageIO.read(new File(path));
        loadTexture(source, textureName);
    }

    public void loadTexture(BufferedImage source, String textureName) {
        textures.put(textureName, new ImageTexture(source));
    }

    public ImageTexture getTexture(String textureName) {
        return textures.get(textureName);
    }
}
