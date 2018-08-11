package math;

public class Vector3f {
	
	public double e[] = new double[3];
	
	public Vector3f() {}
	
	public Vector3f(double e0, double e1, double e2) {
		e[0] = e0;
		e[1] = e1;
		e[2] = e2;
	}
	
	public double x() { return e[0]; }
	public double y() { return e[1]; }
	public double z() { return e[2]; }
	
	public double r() { return e[0]; }
	public double g() { return e[1]; }
	public double b() { return e[2]; }
	
	public Vector3f invert() {
		return new Vector3f(-e[0], -e[1], -e[2]);
	}
	
	public double length() {
		return Math.sqrt(e[0]*e[0]+e[1]*e[1]+e[2]*e[2]);
	}
	
	public double squared_length() {
		return e[0]*e[0]+e[1]*e[1]+e[2]*e[2];
	}

	public static Vector3f unit_vector(Vector3f vec) {
		double x = vec.x()/vec.length();
		double y = vec.y()/vec.length();
		double z = vec.z()/vec.length();
		return new Vector3f(x,y,z);
	}
	
	public Vector3f make_unit_vector() {
		double k = 1.0 / Math.sqrt(e[0]*e[0]+e[1]*e[1]+e[2]*e[2]);
		double x = e[0] *k;
		double y = e[1] *k;
		double z = e[2] *k;
		return new Vector3f(x,y,z);
	}
	
	public static double dot(Vector3f vec0, Vector3f vec1) {
		return vec0.x() * vec1.x() + vec0.y() * vec1.y() + vec0.z() * vec1.z();
	}
	
	public static Vector3f cross(Vector3f vec0, Vector3f vec1) {
		return new Vector3f( (vec0.y() * vec1.z()) - (vec0.z() * vec1.y()),
							 (vec0.z() * vec1.x()) - (vec0.x() * vec1.z()),
							 (vec0.x() * vec1.y()) - (vec0.y() * vec1.x()));
	}
	
	public Vector3f add(Vector3f vec) {
		return new Vector3f(e[0]+vec.x(),e[1]+vec.y(),e[2]+vec.z());
	}
	
	public Vector3f sub(Vector3f vec) {
		return new Vector3f(e[0]-vec.x(),e[1]-vec.y(),e[2]-vec.z());
	}
	
	public Vector3f mul(Vector3f vec) {
		return new Vector3f(e[0]*vec.x(),e[1]*vec.y(),e[2]*vec.z());
	}
	
	public Vector3f mul(double d) {
		return new Vector3f(e[0]*d,e[1]*d,e[2]*d);
	}
	
	public Vector3f div(double d) {
		return new Vector3f(e[0]/d,e[1]/d,e[2]/d);
	}
	
	public void copy(Vector3f v) {
		e[0] = v.e[0];
		e[1] = v.e[1];
		e[2] = v.e[2];
	}
	
	  public double get(int n) {
	    switch (n)
	    {
	    case 0:
	      return e[0];
	    case 1:
	      return e[1];
	    case 2:
	      return e[2];
	    default:
	      throw new IllegalArgumentException("0 <= n <= 2, was " + n);
	    }
	  }

	  @Override
	  public String toString()
	  {
	    return String.format("(%.3f %.3f %.3f)", e[0], e[1], e[2]);
	}
}
