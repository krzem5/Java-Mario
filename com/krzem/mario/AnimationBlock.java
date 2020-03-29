package com.krzem.mario;



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



public abstract class AnimationBlock extends Block{
	public int f_cnt=-1;
	public double f_add=3;
	private double _f_tm=0;



	@Override
	public void update(){
		this._f_tm=(this._f_tm+this.f_add/this.lv.cls.FPS)%this.f_cnt;
	}



	public final BufferedImage image(){
		return this.get_image("@"+Integer.toString((int)this._f_tm));
	}
}