package material;

import geometry.Hit_record;
import math.Ray;
import math.Vector3f;

public class Dielectric extends Material{

	double ref_idx;
	
	public Dielectric(double ri) {
		ref_idx = ri;
	}
	
	@Override
	public boolean scatter(Ray r, Hit_record rec, Vector3f attenuation, Ray scattered) {
		Vector3f outward_normal;
		Vector3f reflected = reflect(r.direction(),rec.normal);
		double ni_over_nt;
		attenuation.copy(new Vector3f(1.0,1.0,1.0));
		Vector3f refracted = new Vector3f();
		double reflect_prob;
		double cos;
		if(Vector3f.dot(r.direction(), rec.normal) > 0) {
			outward_normal = rec.normal.mul(-1.0);
			ni_over_nt = ref_idx;
			cos = ref_idx * Vector3f.dot(r.direction(), rec.normal) / r.direction().length();
		}else {
			outward_normal = rec.normal;
			ni_over_nt = 1.0 / ref_idx;
			cos = -(Vector3f.dot(r.direction(), rec.normal)) / r.direction().length();
		}
		if(refract(r.direction(), outward_normal, ni_over_nt, refracted)) {
			reflect_prob = sclick(cos, ref_idx);
		}else {
			scattered.copy(new Ray(rec.p,reflected));
			reflect_prob = 1.0;
		}
		if(rnd.nextDouble() < reflect_prob) {
			scattered.copy(new Ray(rec.p,reflected));
		}else {
			scattered.copy(new Ray(rec.p,refracted));	
		}
		return true;
	}

}
