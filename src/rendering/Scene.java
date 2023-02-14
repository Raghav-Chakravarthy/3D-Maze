package rendering;
import java.util.ArrayList;

public class Scene {
	private ArrayList<SceneObject> objects;
	private ArrayList<Light> lights;
	
	public Scene() {
		objects = new ArrayList<SceneObject>();
		lights = new ArrayList<Light>();
	}
	
	//TODO: Alternate chamber constructor
	
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
