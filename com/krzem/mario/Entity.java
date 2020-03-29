package com.krzem.mario;



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.Exception;



public abstract class Entity extends Constants{
	private static String[] _enl=new String[]{"muncher"};
	public Level lv;
	public int id;
	public String nm;
	public double x;
	public double y;
	public double vx;
	public double vy;
	public boolean on_g=false;
	public CollisionList cl=new CollisionList();
	public CollisionObject co;



	public abstract void init();



	public abstract void update();



	public abstract void draw(Graphics2D g,Graphics2D og);



	public abstract double[][][] next_shapes();



	public abstract double[][] next_polygon();



	public abstract double[][][] shapes();



	public abstract double[][] polygon();



	public final double[][][] get_contour(String fp,double x,double y){
		return IO.get_contour(ENTITY_CONTOUR_FILE_PATH+this.nm+"/"+fp,x,y);
	}



	public final double[][] get_polygon(String fp,double x,double y){
		return IO.get_polygon(ENTITY_CONTOUR_FILE_PATH+this.nm+"/"+fp,x,y);
	}



	public final BufferedImage get_image(String t){
		return IO.get_image(ENTITY_TEXTURE_FILE_PATH+this.nm+"/"+t);
	}



	private void _init(Level lv,int id,String nm,int x,int y,byte[] dt){
		this.lv=lv;
		this.id=id;
		this.nm=nm;
		this.x=x;
		this.y=y;
		this.vx=0;
		this.vy=0;
		this.init();
		this.co=this.lv.ce.add_object(this,this.shapes(),this.polygon(),null);
	}



	public static Entity get(int id,Level lv,int x,int y,byte[] dt){
		try{
			Entity e=(Entity)Entity.class.getClassLoader().loadClass("com.krzem.mario."+(id>-1?Entity._enl[id].substring(0,1).toUpperCase()+Entity._enl[id].substring(1).toLowerCase()+"Entity":"Player")).getDeclaredConstructor().newInstance();
			e._init(lv,id,(id>-1?Entity._enl[id].toLowerCase():"player"),x,y,dt);
			return e;
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}