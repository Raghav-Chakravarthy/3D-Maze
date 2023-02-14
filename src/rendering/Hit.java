package rendering;

public class Hit {
	private SceneObject obj;
	private Vector3 point;
	private float dist;
	
	public Hit(SceneObject obj, Vector3 point, float dist) {
		this.obj = obj;
		this.point = point;
		this.dist = dist;
	}

	public SceneObject getObj() {
		return obj;
	}

	public Vector3 getPoint() {
		return point;
	}
	
	public float getDist() {
		return dist;
	}
}
