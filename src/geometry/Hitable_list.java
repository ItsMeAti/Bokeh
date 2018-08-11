package geometry;

import math.Ray;

public class Hitable_list extends Hitable {
	
	Hitable[] list;
	int size;
	
	//public Hitable_list() {}
	public Hitable_list(Hitable[] l, int n) {
		list = l;
		size = n;
	}

	@Override
	public boolean hit(Ray r, double t_min, double t_max, Hit_record rec) {
		Hit_record temp_rec = new Hit_record();
		boolean hit_anything = false;
		double closest_so_far = t_max;
		for(int i=0; i < size; i++) {
			if(list[i].hit(r, t_min, closest_so_far, temp_rec)) {
				hit_anything = true;
				closest_so_far = temp_rec.t;
				rec.copy(temp_rec);
			}
		}
		return hit_anything;
	}

}
