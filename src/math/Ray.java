package math;

public class Ray {
	
	Vector3f A;
	Vector3f B;
	
	public Ray() {}
	
	public Ray(Vector3f origin, Vector3f direction) {
		A = origin;
		B = direction;
	}
	
	public Vector3f origin() { return A; }
	public Vector3f direction() { return B; }
	
	public Vector3f point_at_parameter(double t) {
		return A.add(B.mul(t));
	}
	
	public void copy(Ray r) {
		A = r.A;
		B= r.B;
	}

}
