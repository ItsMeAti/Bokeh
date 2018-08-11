package material;

import java.util.SplittableRandom;

import geometry.Hit_record;
import math.Ray;
import math.Vector3f;

public abstract class Material {
	
	//Random rnd = new Random();
	SplittableRandom rnd = new SplittableRandom();
	
	public abstract boolean scatter(Ray r, Hit_record rec, Vector3f attenuation, Ray scattered);
	
	protected Vector3f random_in_unit_sphere() {
		Vector3f p = new Vector3f();
		Vector3f one = new Vector3f(1.0,1.0,1.0);
		do {
			p.e[0] = rnd.nextDouble();
			p.e[1] = rnd.nextDouble();
			p.e[2] = rnd.nextDouble();
			p.mul(2.0);
			p.sub(one);
		} while(p.squared_length() >= 1.0);
		return p;
	}
	
	protected Vector3f reflect(Vector3f v, Vector3f n) {
		return v.sub(n.mul(Vector3f.dot(v, n)).mul(2.0));
	}
	
	protected boolean refract(Vector3f v, Vector3f n, double ni_over_nt, Vector3f refracted) {
		Vector3f uv = Vector3f.unit_vector(v);
		double dt = Vector3f.dot(uv, n);
		double discriminant = 1.0 - ni_over_nt*ni_over_nt*(1-dt*dt);
		if(discriminant > 0) {
			refracted.copy(uv.sub(n.mul(dt)).mul(ni_over_nt).sub(n.mul(Math.sqrt(discriminant))));
			return true;
		}else
			return false;
	}
	
	double sclick(double cos, double ref_idx) {
		double r0 = (1.0-ref_idx) / (1.0+ref_idx);
		r0 = r0*r0;
		return r0 + (1.0-r0) *Math.pow((1.0-cos), 5);
	}
}
