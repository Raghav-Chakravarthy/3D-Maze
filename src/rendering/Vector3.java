package rendering;

import maze.Coordinate;

public class Vector3 {
	private float x, y, z;
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(float a) {
		this.x = a;
		this.y = a;
		this.z = a;
	}

	public Vector3(Coordinate coord) {
		z = coord.getRow()*2;
		y = coord.getLevel()*2;
		x = coord.getColumn()*2;
	}
	
	public Vector3 add(Vector3 other) {
		return new Vector3(other.x()+x, other.y() + y, other.z() + z);
	}
	
	public Vector3 subtract(Vector3 other) {
		return new Vector3(x - other.x(), y - other.y(), z - other.z());
	}
	
	public Vector3 multiply(Vector3 other) {
		return new Vector3(other.x()*x, other.y()*y, other.z() * z);
	}
	
	public float dot(Vector3 other) {
		float result = 0;
		
		result += x*other.x;
		result += y*other.y;
		result += z*other.z;
		
		return result;
	}
	
	public Vector3 cross(Vector3 b) {
		return new Vector3(
			y * b.z - z * b.y, 
	        z * b.x - x * b.z, 
	        x * b.y - y * b.x 
		);
	}
	
	public Vector3 scale(float scalar) {
		return new Vector3(x*scalar, y*scalar, z*scalar);
	}
	
	public float magnitude() {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	
	public Vector3 normalize() {
		return new Vector3(x / magnitude(), y / magnitude(), z / magnitude());
	}
	
	public float x() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float y() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float z() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public boolean equals(Object other) {
		if(other instanceof Vector3) {
			Vector3 o = (Vector3) other;
			if(o.x() == x && o.y() == y && o.z() == z) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String toString() {
		return String.format("Vec3(%.2f, %.2f, %.2f)", x,y,z);
	}
}
