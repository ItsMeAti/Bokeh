package geometry;

import math.Ray;

public abstract class Hitable {
	
	public abstract boolean hit(Ray r, double t_min, double t_max, Hit_record rec);

}
