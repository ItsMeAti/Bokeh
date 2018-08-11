package geometry;

import material.Lambertian;
import material.Material;
import math.Ray;
import math.Vector3f;

public class Sphere extends Hitable {
	
	Vector3f center;
	double radius;
	Material material;
	
	public Sphere() {}
	public Sphere(Vector3f cen, double r, Material m) {
		center = cen;
		radius = r;
		material = m;
	}

	@Override
	public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {
		Vector3f oc = r.origin().sub(center);
		double a = Vector3f.dot(r.direction(), r.direction());
		double b = Vector3f.dot(oc, r.direction());
		double c = Vector3f.dot(oc, oc) - radius * radius;
		double discriminant = b*b-a*c;
		if (discriminant > 0) {
			double temp = (-b -Math.sqrt(b*b-a*c))/a;
			if(temp < t_max && temp > t_min){
				rec.t = temp;
				rec.p = r.point_at_parameter(rec.t);
				rec.normal = rec.p.sub(center).div(radius);
				rec.material = material;
				return true;
			}
			temp = (-b +Math.sqrt(b*b-a*c))/a;
			if(temp < t_max && temp > t_min) {
				rec.t = temp;
				rec.p = r.point_at_parameter(rec.t);
				Vector3f temp0 = (rec.p.sub(center));
				rec.normal = temp0.div(radius);
				rec.material = material;
				return true;
			}
		}
		return false;
	}

}
