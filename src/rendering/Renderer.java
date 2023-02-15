package rendering;
import java.awt.image.BufferedImage;

public class Renderer {
	private static int numBounce = 0;
	private static final int NUM_THREADS = 4;
	private static Thread[] renderThreads = new Thread[NUM_THREADS];
	
	private static final float EPSILON = 0.01f;

	public static void renderTo(Scene scene, Camera cam, BufferedImage frameBuffer) {
		for(int t = 0; t < NUM_THREADS; t++) {
			int startPixelIdx = (frameBuffer.getWidth()/NUM_THREADS)*t;
			int endPixelIdx;
			
			if(t == NUM_THREADS-1)
				endPixelIdx = frameBuffer.getWidth();
			else
				endPixelIdx = startPixelIdx + (frameBuffer.getWidth()/NUM_THREADS);
			
			renderThreads[t] = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int i = startPixelIdx; i < endPixelIdx; i++) {
						for(int j = 0; j < frameBuffer.getHeight(); j++) {
							Ray ray = cam.generatePrimaryRay(i, j, 
									frameBuffer.getWidth(), frameBuffer.getHeight());
							Vector3 color = cast(ray, scene, numBounce);
							frameBuffer.setRGB(i, j, ColorUtils.rgbToHex(color));
						}
					}
				}
			});
		}
		
		for(Thread t : renderThreads) {
			t.start();
		}
		
		try {
			for(Thread t : renderThreads) {
				t.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static Vector3 cast(Ray r, Scene scene, int depth) {
		if(depth < 0)
			return skyColor(r);
		
		Hit hit = intersect(r, scene);
		
		if(hit != null) {
			Vector3 point = hit.getPoint();
			Vector3 normal = hit.getObj().normalAt(point);
			Vector3 color = hit.getObj().diffuseAt(point).scale(0.3f); //ambient
			
			for(Light light : scene.getLights()) {
				Vector3 l = light.lVector(point);
				Hit shadowHit = intersect(new Ray(point.add(normal.scale(EPSILON)), l), scene);
				
				if(light instanceof PointLight) {
					PointLight p = (PointLight) light;
					if(shadowHit == null || shadowHit.getDist() >
							point.subtract(p.getPosition()).magnitude())
						color = color.add(hit.getObj().diffuseAt(point)
								.multiply(light.intensity()
										.scale(Math.max(0, normal.dot(l)))));
				}

				if(numBounce > 0 && hit.getObj().shininess() > 0) {
					color = color.scale(1-hit.getObj().shininess())
					.add(color.multiply(cast(r.reflect(normal, point), scene, depth-1))
							.scale(hit.getObj().shininess()));	
				}
			}
			
			return color;
		} else {
			return skyColor(r);
		}
	}
	
	private static Hit intersect(Ray r, Scene scene) {
		Hit closest = null;
		float minDist = Float.MAX_VALUE;
		
		for(SceneObject obj : scene.getObjects()) {
			Vector3 intersect = obj.intersect(r);
			if(intersect != null) {
				float dist = intersect.subtract(r.getOrigin()).magnitude();
				if(dist < minDist) {
					closest = new Hit(obj, intersect, dist);
					minDist = dist;
				}
			}
		}
		return closest;
	}
	
	private static Vector3 skyColor(Ray r) {
		float t = 0.5f*(r.getDirection().y() + 1.0f);
	    return new Vector3(1-t, 1-t, 1-t).add(
	    		new Vector3((t * 138f/255), (t*188f/255),(t)));
	}
	
	public void setNumBounce(int n) {
		this.numBounce = n;
	}
	
	public int getNumBounce() {
		return numBounce;
	}
}
