package rendering;

public class Ray {
	private Vector3 origin, direction;
	
	public Ray(Vector3 origin, Vector3 direction) {
		this.origin = origin;
		this.direction = direction;
	}
	
	public Ray reflect(Vector3 normal, Vector3 point) {
		return new Ray(point, direction.subtract(normal.scale(2*direction.dot(normal))));
	}
	
	public Vector3 getOrigin() {
		return origin;
	}

	public void setOrigin(Vector3 origin) {
		this.origin = origin;
	}

	public Vector3 getDirection() {
		return direction;
	}

	public void setDirection(Vector3 direction) {
		this.direction = direction.normalize();
	}
 }
