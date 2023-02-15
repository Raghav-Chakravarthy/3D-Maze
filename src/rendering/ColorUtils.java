package rendering;

public class ColorUtils {
	public static int rgbToHex(int r, int g, int b) {
		int hex = (r << 8 << 8) | (g << 8) | b;
		return hex;
	}
	
	public static int rgbToHex(Vector3 color) {
		return rgbToHex((int) Math.min((Math.sqrt(color.x())*255), 255),
				(int) Math.min((Math.sqrt(color.y())*255), 255),
				(int) Math.min((Math.sqrt(color.z())*255), 255));
	}
}
