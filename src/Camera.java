import java.util.SplittableRandom;

import math.Ray;
import math.Vector3f;



public class Camera {
	
	Vector3f lower_left_corner = new Vector3f();
	Vector3f horizontal = new Vector3f();
	Vector3f vertical = new Vector3f();
	Vector3f origin = new Vector3f();
	Vector3f u = new Vector3f();
	Vector3f v = new Vector3f();
	Vector3f w = new Vector3f();
	double lens_radius;
	SplittableRandom rnd = new SplittableRandom();
	
	public Camera(Vector3f lookfrom, Vector3f lookat, Vector3f up, double fov, double aspect, double aperture, double focus_dist) {
		lens_radius = aperture / 2.0;
		double theta = fov*Math.PI/180.0;
		double half_height = Math.tan(theta/2.0);
		double half_width = aspect * half_height;
		origin.copy(lookfrom);
		w.copy(Vector3f.unit_vector(lookfrom.sub(lookat)));
		u.copy(Vector3f.unit_vector(Vector3f.cross(up,w)));
		v.copy(Vector3f.cross(w, u));
		lower_left_corner.copy(origin.sub(u.mul(half_width*focus_dist)).sub(v.mul(half_height*focus_dist)).sub(w.mul(focus_dist))); 
		horizontal.copy(u.mul(half_width).mul(2.0).mul(focus_dist));
		vertical.copy(v.mul(half_height).mul(2.0).mul(focus_dist));
	}
	
	Ray get_ray(double s, double t) {
		Vector3f rd = random_in_unit_disk().mul(lens_radius);
		Vector3f offset = u.mul(rd.x()).add(v.mul(rd.y()));
		return new Ray(origin.add(offset), lower_left_corner.add(horizontal.mul(s)).add(vertical.mul(t)).sub(origin).sub(offset));
	}
	
	private Vector3f random_in_unit_disk() {
		Vector3f p = new Vector3f();
		Vector3f vec = new Vector3f(1.0,1.0,0.0);
		do {
			p.e[0] = rnd.nextDouble();
			p.e[1] = rnd.nextDouble();
			p.e[2] = 0.0;
			p.mul(2.0);
			p.sub(vec);
		}while(Vector3f.dot(p, p) >= 1.0);
		return p;
	}
}
