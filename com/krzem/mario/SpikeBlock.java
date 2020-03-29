package com.krzem.mario;



import java.awt.Graphics2D;



public class SpikeBlock extends AnimationBlock{
	@Override
	public void init(){
		this.f_cnt=3;
		this.f_add=2.4;
	}



	@Override
	public void update(){
		super.update();
	}



	@Override
	public void draw(Graphics2D g,Graphics2D og){
		if (this.on_screen()==false){
			return;
		}
		g.drawImage(this.image(),this.x*TILE_SIZE.width,this.y*TILE_SIZE.height,TILE_SIZE.width,TILE_SIZE.height,null);
	}



	@Override
	public boolean on_screen(){
		return this.aabb_on_screen(this.co.t_aabb);
	}



	@Override
	public double[][][] shape(){
		return IO.get_contour("base-block.cf",this.x,this.y);
	}



	@Override
	public double[][] polygon(){
		return IO.get_polygon("base-block.cf",this.x,this.y);
	}



	@Override
	public int[][] collision_dirs(){
		return null;
	}
}