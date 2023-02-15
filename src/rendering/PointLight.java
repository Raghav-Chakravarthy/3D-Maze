package rendering;

public class PointLight extends Light {
	private Vector3 position;
	private Vector3 intensity;
	
	public PointLight(Vector3 position, Vector3 intensity) {
		this.position = position;
		this.intensity = intensity;
	}
	
	public Vector3 lVector(Vector3 point) {
		return position.subtract(point).normalize();
	}
	
	public Vector3 intensity() {
		return intensity;
	}
	
	public Vector3 getPosition() {
		return position;
	}
}
