package com.krzem.mario;



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.Exception;



public abstract class Block extends Constants{
	private static String[] _bnl=new String[]{"stone","spike"};
	public Level lv;
	public int id;
	public String nm;
	public int x;
	public int y;
	public byte[] dt;
	public CollisionObject co;
	private int _si;



	public abstract void init();



	public abstract void update();



	public abstract void draw(Graphics2D g,Graphics2D og);



	public abstract boolean on_screen();



	public abstract double[][][] shape();



	public abstract double[][] polygon();



	public abstract int[][] collision_dirs();



	public final BufferedImage get_image(String t){
		return IO.get_image(BLOCK_TEXTURE_FILE_PATH+this.nm+t+".png");
	}



	public final boolean aabb_on_screen(double[] aabb){
		return this.co.ce._intersect_aabb_aabb(aabb,new double[]{this.lv.co[0],this.lv.co[1],this.lv.co[0]+GAME_SIZE.width,this.lv.co[1]+GAME_SIZE.height});
	}



	private void _init(Level lv,int id,String nm,int x,int y,byte[] dt){
		this.lv=lv;
		this.id=id;
		this.nm=nm;
		this.x=x;
		this.y=y;
		this.dt=dt;
		this.init();
		this.co=this.lv.ce.add_object(this,this.shape(),this.polygon(),this.collision_dirs());
	}



	public static Block get(int id,Level lv,int x,int y,byte[] dt){
		try{
			Block b=(Block)Block.class.getClassLoader().loadClass("com.krzem.mario."+Block._bnl[id].substring(0,1).toUpperCase()+Block._bnl[id].substring(1).toLowerCase()+"Block").getDeclaredConstructor().newInstance();
			b._init(lv,id,Block._bnl[id].toLowerCase(),x,y,dt);
			return b;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}