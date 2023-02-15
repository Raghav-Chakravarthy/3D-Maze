package rendering;

public abstract class Plane extends SceneObject {
	private Vector3 normal;
	
	public Plane(Vector3 normal, Vector3 position) {
		this.normal = normal.scale(-1);
		setPosition(position);
	}
	
	@Override
	public Vector3 intersect(Ray ray) {
		float denom = normal.dot(ray.getDirection());
		if (denom > 1e-6) { 
	        Vector3 p0l0 = position().subtract(ray.getOrigin()); 
	        float t = p0l0.dot(normal) / denom;
	        if(t > 0)
	        	return ray.getOrigin().add(ray.getDirection().scale(t)); 
	    }
	 
	    return null; 
	}
	
	public Vector3 getNormal() {
		return normal;
	}
	
	public void setNormal(Vector3 normal) {
		this.normal = normal;
	}

	@Override
	public Vector3 normalAt(Vector3 point) {
		return normal.scale(-1);
	}

	public String toString() {
		return "Plane(" + position() + ", " + normal.scale(-1) + ")";
	}
}
