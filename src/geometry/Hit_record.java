package geometry;

import material.Material;
import math.Vector3f;



public class Hit_record {
	
	public double t;
	public Vector3f p;
	public Vector3f normal;
	public Material material;
	
	public Hit_record() {};
	
	public Hit_record(double t, Vector3f p, Vector3f normal, Material m) {
		this.t= t;
		this.p = p;
		this.normal = normal;
		this.material = m;
	}
	
	public void copy(Hit_record h) {
		this.t= h.t;
		this.p = h.p;
		this.normal = h.normal;
		this.material = h.material;
	}

	@Override
	public String toString() {
		return "Hit_record [t=" + t + ", p=" + p + ", normal=" + normal + "]";
	}
	
	

}
