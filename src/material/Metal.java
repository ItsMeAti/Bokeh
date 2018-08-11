package material;

import geometry.Hit_record;
import math.Ray;
import math.Vector3f;

public class Metal extends Material{
	
	Vector3f albedo;
	double fuzz;
	
	public Metal(Vector3f a, double f) {
		albedo = a;
		if(f<1.0)
			fuzz=f;
		else
			fuzz=1;
	}

	@Override
	public boolean scatter(Ray r, Hit_record rec, Vector3f attenuation, Ray scattered) {
		Vector3f reflected = reflect(r.direction().make_unit_vector(),rec.normal);
		scattered.copy(new Ray(rec.p,reflected.add(random_in_unit_sphere().mul(fuzz))));
		attenuation.copy(albedo);
		return (Vector3f.dot(scattered.direction(),rec.normal) > 0);
	}
	

}
