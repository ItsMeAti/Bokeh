package material;

import geometry.Hit_record;
import math.Ray;
import math.Vector3f;

public class Lambertian extends Material {
	
	Vector3f albedo;
	
	public Lambertian(Vector3f a) {
		albedo=a;
	}

	@Override
	public boolean scatter(Ray r, Hit_record rec, Vector3f attenuation, Ray scattered) {
		Vector3f target = rec.p.add(rec.normal).add(random_in_unit_sphere());
		scattered.copy(new Ray(rec.p,target.sub(rec.p)));
		attenuation.copy(albedo);
		return true;
	}
	

}
