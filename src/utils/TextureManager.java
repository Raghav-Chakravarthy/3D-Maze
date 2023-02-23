package utils;

import java.util.HashMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureManager {
    public static final TextureManager main = new TextureManager();
    private HashMap<String, BufferedImage> textures;
    private int artCounter = 0;

    public TextureManager() {
        textures = new HashMap<String, BufferedImage>();
        try {
            loadArtImages();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage nextArt() {
        BufferedImage image = getTexture("art"+artCounter);
        artCounter++;

        if(image == null) System.err.println("TextureManager: Ran out of unique wall art!");

        return image;
    }

    public int getArtCounter() {
        return artCounter;
    }

    private void loadArtImages() throws IOException {
        for(int i = 0; i < 15; i++) {
            loadTextureFromDisk("assets/wallArt/art"+i+".jpg", "art"+i);
        }
    }

    public void loadTextureFromDisk(String path, String textureName) throws IOException {
        BufferedImage source = ImageIO.read(new File(path));
        loadTexture(source, textureName);
    }

    public void loadTexture(BufferedImage source, String textureName) {
        textures.put(textureName, source);
    }

    public BufferedImage getTexture(String textureName) {
        return textures.get(textureName);
    }
}
