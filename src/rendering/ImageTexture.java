package rendering;
import java.awt.image.BufferedImage;

public class ImageTexture {
	private BufferedImage image;
	
	public ImageTexture(BufferedImage source) {
		this.image = source;
	}
	
	public Vector3 sampleColorAt(float u, float v) {
		int hex = image.getRGB((int)(u*image.getWidth()), (int)(v*image.getHeight()));
		
		int r = (hex & 0xFF0000) >> 16;
	    int g = (hex & 0xFF00) >> 8;
	    int b = (hex & 0xFF);
	    
		return new Vector3(r/255f, g/255f, b/255f);
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage img) {
		this.image = img;
	}
}
