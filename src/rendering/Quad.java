package rendering;
public class Quad extends Plane {
	private float width;
	private float height;
	private ImageTexture texture;
	
	public Quad(Vector3 pos, Vector3 normal, ImageTexture texture, float width, float height) {
		super(normal, pos);
		this.width = width;
		this.height = height;
		this.texture = texture;
	}
	
	@Override
	public Vector3 intersect(Ray ray) {
		Vector3 p = super.intersect(ray);
		if(p != null) {
			Vector3 u;
			if(getNormal().equals(new Vector3(0,1,0)) || getNormal().equals(new Vector3(0,-1,0))) {
				u = new Vector3(1,0,0).cross(getNormal());
			} else {
				u = new Vector3(0,1,0).cross(getNormal());
			}
			Vector3 v = getNormal().cross(u);
			
			float x = u.dot(p.subtract(position()));
			float y = v.dot(p.subtract(position()));
					
			if(x < width/2f && y < height/2f && x > -width/2f && y > -height/2f) {
				return p;
			}
		}
		return null;
	}

	@Override
	public Vector3 normalAt(Vector3 point) {
		return getNormal().scale(-1);
	}

	@Override
	public Vector3 diffuseAt(Vector3 point) {
		Vector3 u;
		if(getNormal().equals(new Vector3(0,1,0)) || getNormal().equals(new Vector3(0,-1,0))) {
			u = new Vector3(1,0,0).cross(getNormal());
		} else {
			u = new Vector3(0,1,0).cross(getNormal());
		}
		Vector3 v = getNormal().cross(u);
		
		float x = u.dot(point.subtract(position()));
		float y = v.dot(point.subtract(position()));
		float imx = Math.min((x+width/2f)/width, 0.99f);
		float imy = Math.min(1-(y+height/2f)/height, 0.99f);
		
		return texture.sampleColorAt(imx, imy);
	}

	@Override
	public float shininess() {
		return 0;
	}

	@Override
	public void update(float dt) {
	}

}
