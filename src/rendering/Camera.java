package rendering;
public class Camera {
	private Vector3 position;
	private Vector3 direction;
	private float pitch = 0;
	private float yaw = 90;
	private float nearPlane;
	private Vector3 viewportSize;
	
	public Camera() {
		this.position = new Vector3(0, 0, 0);
		setRotation(pitch, yaw);
		this.viewportSize = new Vector3(2*(16f/9), 2, 0);
		this.nearPlane = 1;
	}
	
	public Camera(Vector3 position, Vector3 direction) {
		this.position = position;
		this.direction = direction;
		this.viewportSize = new Vector3(5*(16f/9), 2, 0);
		this.nearPlane = 1;
	}
	
	public Camera(Vector3 position, float pitch, float yaw) {
		this.pitch = pitch;
		this.yaw = yaw;
		this.position = position;
		setRotation(pitch, yaw);
		this.viewportSize = new Vector3(5*(16f/9), 2, 0);
		this.nearPlane = 1;
	}
	
	public Ray generatePrimaryRay(int screenX, int screenY, int screenWidth, int screenHeight) {
		setViewportSize(new Vector3(((float)screenWidth/screenHeight)*1.5f, 1.5f, 0));
		Vector3 adjacent = new Vector3(0,1,0).cross(direction).normalize(); //Vec3(1,0,0)
		Vector3 localUp = adjacent.cross(direction).normalize();
		
		Vector3 bottomLeft = adjacent.scale(-viewportSize.x() / 2).add(localUp.scale(-viewportSize.y()/2));
		
		return new Ray(position, bottomLeft
				.add(adjacent.scale(viewportSize.x()*((float)screenX/screenWidth)))
				.add(localUp.scale(viewportSize.y()*((float)(screenY)/screenHeight)))
				.add(direction.scale(nearPlane)).normalize());
	}
	
	public void update(float dt, int screenWidth, int screenHeight) {}
	
	public void lookAt(Vector3 point) {
		direction = point.subtract(position).normalize();
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public Vector3 getDirection() {
		return direction;
	}

	public void setDirection(Vector3 direction) {
		this.direction = direction;
	}

	public float getNearPlane() {
		return nearPlane;
	}

	public void setNearPlane(float nearPlane) {
		this.nearPlane = nearPlane;
	}

	public Vector3 getViewportSize() {
		return viewportSize;
	}
	
	public void setViewportSize(Vector3 viewportSize) {
		this.viewportSize = viewportSize;
	}
	
	public void translate(Vector3 amount) {
		setPosition(position.add(amount));
	}
	
	public void setRotation(float pitch, float yaw) {
		this.pitch = pitch;
		this.yaw = yaw;
	    setDirection(new Vector3(
	    	(float)(Math.cos(Math.toRadians(yaw)) *
	    			Math.cos(Math.toRadians(pitch))),
	    	(float)Math.sin(Math.toRadians(pitch)),
	    	(float)(Math.sin(Math.toRadians(yaw)) * 
	    			Math.cos(Math.toRadians(pitch)))
	    ).normalize());
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	public String toString() {
		return "Camera(" + position + ", " + direction + ")";
	}
}
