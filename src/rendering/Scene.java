package rendering;
import java.util.ArrayList;
import maze.Chamber;
import maze.Coordinate;
import utils.Direction;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;

public class Scene {
	private ArrayList<SceneObject> objects;
	private ArrayList<Light> lights;
	
	public Scene() {
		objects = new ArrayList<SceneObject>();
		lights = new ArrayList<Light>();
	}

	
	//TODO: Alternate chamber constructor
	public Scene(Chamber[] chambers) {
		objects = new ArrayList<SceneObject>();
		lights = new ArrayList<Light>();

		BufferedImage placeholder = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) placeholder.getGraphics();
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, 10, 10);

		for(Chamber chamber : chambers) {
			Coordinate coord = chamber.getCoordinates();
			
			Vector3 center = new Vector3(coord);
			System.out.println(center);

			addLight(new PointLight(center, new Vector3(1)));

			for(int dir = 0; dir < 6; dir++) {
				
				addObject(createWall(dir, center, new ImageTexture(placeholder)));
				
			}
		}
	}

	private Quad createWall(int dir, Vector3 center, ImageTexture art) {
		if(dir == Direction.NORTH) {
			return new Quad(new Vector3(0,0,-1), center.add(new Vector3(0,0,1)), art, 2, 2);
		} else if(dir == Direction.SOUTH) {
			return new Quad(new Vector3(0,0,1), center.add(new Vector3(0,0,-1)), art, 2, 2);
		} else if(dir == Direction.EAST) {
			return new Quad(new Vector3(-1,0,0), center.add(new Vector3(1,0,0)), art, 2, 2);
		} else if(dir == Direction.WEST) {
			return new Quad(new Vector3(1,0,0), center.add(new Vector3(-1,0,0)), art, 2, 2);
		} else if(dir == Direction.UP) {
			return new Quad(new Vector3(0,-1,0), center.add(new Vector3(0,1,0)), art, 2, 2);
		} else if(dir == Direction.DOWN) {
			return new Quad(new Vector3(0,1,0), center.add(new Vector3(0,-1,0)), art, 2, 2);
		} else {
			return null;
		}
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
