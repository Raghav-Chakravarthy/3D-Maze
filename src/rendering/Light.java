package rendering;

public abstract class Light {
	public abstract Vector3 lVector(Vector3 p);
	public abstract Vector3 intensity();
}
