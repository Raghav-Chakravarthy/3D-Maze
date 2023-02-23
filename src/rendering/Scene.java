package rendering;
import java.util.ArrayList;
import maze.Chamber;
import maze.Coordinate;
import utils.Direction;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class Scene {
	private ArrayList<SceneObject> objects;
	private ArrayList<Light> lights;

	private final int TEXTURE_SIZE = 720;
	private final Color PASSAGE_COLOR = new Color(ColorUtils.rgbToHex(new Vector3(0.01f)));
	private final float CHAMBER_SIZE = 2.01f;
	
	public Scene() {
		objects = new ArrayList<SceneObject>();
		lights = new ArrayList<Light>();
	}

	//TODO: Alternate chamber constructor
	public Scene(Chamber[] chambers) {
		objects = new ArrayList<SceneObject>();
		lights = new ArrayList<Light>();

		for(Chamber chamber : chambers) {
			Coordinate coord = chamber.getCoordinates();
			
			Vector3 center = new Vector3(coord);
			System.out.println(center);

			addLight(new PointLight(center.add(new Vector3(0,0.5f,0)), new Vector3(0.7f)));

			for(int dir = 0; dir < 6; dir++) {
				if(chamber.getAdjacentChamber(dir) == null) {
					addObject(createWall(dir, center, createWallTexture(chamber, dir)));
				} else {
					if(dir == Direction.UP) {
						addObject(createHatch(center, chamber));
					} else if(dir == Direction.DOWN) {
						addObject(createTrapdoor(center, chamber));
					} else {
						addObject(createDoor(center, dir, chamber));
					}
				}
			}
		}
	}

	private Quad createWall(int dir, Vector3 center, ImageTexture tex) {
		if(dir == Direction.NORTH) {
			return new Quad(new Vector3(0,0,-1), center.add(new Vector3(0,0,1)), tex, CHAMBER_SIZE, CHAMBER_SIZE);
		} else if(dir == Direction.SOUTH) {
			return new Quad(new Vector3(0,0,1), center.add(new Vector3(0,0,-1)), tex, CHAMBER_SIZE, CHAMBER_SIZE);
		} else if(dir == Direction.EAST) {
			return new Quad(new Vector3(-1,0,0), center.add(new Vector3(1,0,0)), tex, CHAMBER_SIZE, CHAMBER_SIZE);
		} else if(dir == Direction.WEST) {
			return new Quad(new Vector3(1,0,0), center.add(new Vector3(-1,0,0)), tex, CHAMBER_SIZE, CHAMBER_SIZE);
		} else if(dir == Direction.UP) {
			return new Quad(new Vector3(0,-1,0), center.add(new Vector3(0,1,0)), tex, CHAMBER_SIZE, CHAMBER_SIZE);
		} else if(dir == Direction.DOWN) {
			return new Quad(new Vector3(0,1,0), center.add(new Vector3(0,-1,0)), tex, CHAMBER_SIZE, CHAMBER_SIZE);
		} else {
			return null;
		}
	}

	private Quad createTrapdoor(Vector3 center, Chamber chamber) {
		return createWall(Direction.DOWN, center, createTrapdoorTexture(chamber));
	}

	private Quad createHatch(Vector3 center, Chamber chamber) {
		return createWall(Direction.UP, center, createHatchTexture(chamber));
	}

	private Quad createDoor(Vector3 center, int dir, Chamber chamber) {
		return createWall(dir, center, createDoorTexture(chamber));
	}

	private BufferedImage emptyWallTexture(Chamber chamber) {
		BufferedImage img = new BufferedImage(TEXTURE_SIZE,TEXTURE_SIZE,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(chamber.getWallColor());
		g.fillRect(0, 0, TEXTURE_SIZE, TEXTURE_SIZE);

		return img;
	}

	private ImageTexture createWallTexture(Chamber chamber, int dir) {
		BufferedImage wallTexture = emptyWallTexture(chamber);
		Graphics2D g = (Graphics2D) wallTexture.getGraphics();
		if(chamber.getWallArt().getArt(dir) != null)
			g.drawImage(chamber.getWallArt().getArt(dir).getImage(), TEXTURE_SIZE/4, TEXTURE_SIZE/4, TEXTURE_SIZE/2, TEXTURE_SIZE/2, null);
		return new ImageTexture(wallTexture);
	}

	private ImageTexture createDoorTexture(Chamber chamber) {
		BufferedImage img = emptyWallTexture(chamber);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(PASSAGE_COLOR);
		g.fillRect(TEXTURE_SIZE/4, TEXTURE_SIZE/4, TEXTURE_SIZE/2, TEXTURE_SIZE);
		g.setColor(Color.GREEN);
		g.setStroke(new BasicStroke(5));
		g.drawRect(TEXTURE_SIZE/4, TEXTURE_SIZE/4, TEXTURE_SIZE/2, TEXTURE_SIZE);

		return new ImageTexture(img);
	}

	private ImageTexture createTrapdoorTexture(Chamber chamber) {
		BufferedImage img = emptyWallTexture(chamber);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(PASSAGE_COLOR);
		g.fillRect(TEXTURE_SIZE/4, TEXTURE_SIZE/4, TEXTURE_SIZE/2, TEXTURE_SIZE/2);
		g.setColor(Color.GREEN);
		g.setStroke(new BasicStroke(5));
		g.drawRect(TEXTURE_SIZE/4, TEXTURE_SIZE/4, TEXTURE_SIZE/2, TEXTURE_SIZE/2);

		return new ImageTexture(img);
	}

	private ImageTexture createHatchTexture(Chamber chamber) {
		return createTrapdoorTexture(chamber);
	}
	
	public void update(float dt) {
		for(SceneObject obj : objects) {
			obj.update(dt);
		}
	}
	
	public ArrayList<SceneObject> getObjects() {
		return objects;
	}
	
	public void addObject(SceneObject obj) {
		objects.add(obj);
	}
	
	public ArrayList<Light> getLights() {
		return lights;
	}
	
	public void addLight(Light light) {
		lights.add(light);
	}
}
