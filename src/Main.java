import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.SplittableRandom;

import geometry.Hit_record;
import geometry.Hitable;
import geometry.Hitable_list;
import geometry.Sphere;
import material.Dielectric;
import material.Lambertian;
import material.Metal;
import math.Ray;
import math.Vector3f;

public class Main {
	
	static int WIDTH = 800;
	static int HEIGHT = 400;
	static int SAMPLES = 10;
	
	public static Vector3f color(Ray r, Hitable world,int depth) {
		Hit_record rec = new Hit_record();
		if(world.hit(r, 0.001, Double.MAX_VALUE, rec)) {
			Ray scattered = new Ray();
			Vector3f attenuation = new Vector3f();
			if(depth < 50 && rec.material.scatter(r, rec, attenuation, scattered)) {
				return attenuation.mul(color(scattered, world, depth+1));
			}else {
				return new Vector3f(0.0,0.0,0.0);
			}
		}else {
			Vector3f unit_direction = r.direction().make_unit_vector();
			double t = 0.5 * (unit_direction.y()+1.0);
			return new Vector3f(1.0,1.0,1.0).mul(1.0-t).add(new Vector3f(0.5,0.7,1.0).mul(t));
		}
	}
	
	public static Hitable random_scene() {
		SplittableRandom rnd = new SplittableRandom();
		int n = 500;
		Hitable list[] = new Hitable[n+1];
		list[0] = new Sphere(new Vector3f(0.0,-1000.0,0.0), 1000, new Lambertian(new Vector3f(0.5,0.5,0.5)));
		int i = 1;
		for(int a = -11; a < 11; a++) {
			for(int b = -11; b < 11; b++) {
				double chose_mat = rnd.nextDouble();
				Vector3f center = new Vector3f(a+0.9*rnd.nextDouble(),0.2,b+0.9*rnd.nextDouble());
				if((center.sub(new Vector3f(4.0, 0.2, 0.0))).length() > 0.9) {
					if(chose_mat < 0.8) { //diff
						list[i++] = new Sphere(center, 0.2,new Lambertian(new Vector3f(rnd.nextDouble()*rnd.nextDouble(),rnd.nextDouble()*rnd.nextDouble(),rnd.nextDouble()*rnd.nextDouble())));
					}
					else if(chose_mat < 0.95) { //metal
						list[i++] = new Sphere(center,0.2, new Metal(new Vector3f(0.5*(1.0+rnd.nextDouble()),0.5*(1.0+rnd.nextDouble()),0.5*(1.0+rnd.nextDouble())),0.5*rnd.nextDouble()));
					}else { //glass
						list[i++] = new Sphere(center,0.2,new Dielectric(1.5));
					}
				}
			}
		}
		
		list[i++] = new Sphere(new Vector3f(0.0,1.0,0.0),1.0,new Dielectric(1.5));
		list[i++] = new Sphere(new Vector3f(-4.0,1.0,0.0),1.0,new Lambertian(new Vector3f(0.4,0.2,0.1)));
		list[i++] = new Sphere(new Vector3f(4,1.0,0.0),1.0,new Metal(new Vector3f(0.7,0.6,0.5), 0.0));
		
		return new Hitable_list(list, i);
	}
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		
		PrintWriter writer = new PrintWriter("img.ppm", "UTF-8");
		writer.println("P3");
		writer.println(WIDTH +" "+ HEIGHT);
		writer.println("255");
		/*
		Hitable list[] = new Hitable[4];
		list[0] = new Sphere(new Vector3f(0.5,0.0,-1.0),0.5,new Metal(new Vector3f(0.2,0.3,0.8), 0.2));
		list[1] = new Sphere(new Vector3f(-0.52,0.0,-1.0),0.5, new Dielectric(1.5));
		list[2] = new Sphere(new Vector3f(-0.52,0.0,-1.0),-0.45, new Dielectric(1.5));
		list[3] = new Sphere(new Vector3f(0.0,-100.5,-1.0),100.0, new Lambertian(new Vector3f(1.0, 0.1, 0.1)));
		Hitable world = new Hitable_list(list,list.length);
		*/
		
		Hitable world = random_scene();
		/*
		Vector3f lookfrom = new Vector3f(3.0,3.0,2.0);
		Vector3f lookat = new Vector3f(0.0,0.0,-1.0);
		double dist_to_focus = (lookfrom.sub(lookat)).length();
		double aperture = 2.0;
		*/
		
		Vector3f lookfrom = new Vector3f(13.0,2.0,3.0);
		Vector3f lookat = new Vector3f(0.0,0.0,0.0);
		double dist_to_focus = (lookfrom.sub(lookat)).length();
		double aperture = 1.2;
		
		Camera cam = new Camera(lookfrom,lookat,new Vector3f(0.0,1.0,0.0), 20.0, (double)WIDTH / (double)HEIGHT, aperture, dist_to_focus);
		
		SplittableRandom rnd = new SplittableRandom();
		
		long time1 = System.currentTimeMillis();
		
		for(int j = HEIGHT-1; j >= 0; j--) {
			for(int i=0; i < WIDTH; i++) {
				Vector3f col = new Vector3f(0.0,0.0,0.0);
				//System.out.println("j = "+ j + " i = " + i );
				for(int s = 0; s < SAMPLES; s++) {
					double u = ((double)i + rnd.nextDouble()) / WIDTH;
					double v = ((double)j + rnd.nextDouble()) / HEIGHT;
					
					Ray r = cam.get_ray(u, v);
					Vector3f p = r.point_at_parameter(2.0);
					col = col.add(color(r, world,0));
				}
				col = col.div((double)SAMPLES);
				//gamma correction
				col = new Vector3f(Math.sqrt(col.x()),Math.sqrt(col.y()),Math.sqrt(col.z()));
				int ir = (int) (255.99*col.x());
				int ig = (int) (255.99*col.y());
				int ib = (int) (255.99*col.z());
				writer.println(ir + " " + ig + " " + ib);
			}
		}
		long time2 = System.currentTimeMillis();
		long ellapsed = time2 - time1;
		writer.close();
		
		System.out.println("Done in: " + ellapsed + "ms");
		
	}

}
