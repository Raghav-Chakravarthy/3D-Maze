package rendering;

public abstract class SceneObject {
	private Vector3 position;
	
	public abstract Vector3 intersect(Ray ray);
	public abstract Vector3 normalAt(Vector3 point);
	
	public abstract Vector3 diffuseAt(Vector3 point);
	public abstract float shininess();
	
	public abstract void update(float dt);
	
	public Vector3 position() {
		return position;
	}
	
	public void setPosition(Vector3 pos) {
		this.position = pos;
	}
}
