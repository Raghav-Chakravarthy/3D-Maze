package rendering;

import java.awt.Color;
import java.util.HashSet;

public class ColorUtils {
	private static HashSet<Integer> usedColors = new HashSet<Integer>();

	public static int rgbToHex(int r, int g, int b) {
		int hex = (r << 8 << 8) | (g << 8) | b;
		return hex;
	}
	
	public static int rgbToHex(Vector3 color) {
		return rgbToHex((int) Math.min((Math.sqrt(color.x())*255), 255),
				(int) Math.min((Math.sqrt(color.y())*255), 255),
				(int) Math.min((Math.sqrt(color.z())*255), 255));
	}
	
	public static Color randomChamberColor() {
		int color = 0;
		
		do {
			color = rgbToHex(randColorChannel(), randColorChannel(), randColorChannel());
		} while(usedColors.contains(color));
		
		usedColors.add(color);

		return new Color(color);
	}

	private static int randColorChannel() {
		return 128+(int) (Math.random()*128);
	}
}
