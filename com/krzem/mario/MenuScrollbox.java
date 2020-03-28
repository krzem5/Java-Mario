package com.krzem.mario;



import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



public class MenuScrollbox extends MenuItem{
	public Main cls;
	public Game g;
	public Menu mn;
	public MenuItem[] il;
	private int x;
	private int y;
	private int w;
	private int h;
	private int ti;
	private Color c;
	private BufferedImage bg;



	public MenuScrollbox(Main cls,Game g,Menu mn,int x,int y,int w,int h,String tx,String cl,String f,int ti){
		this.cls=cls;
		this.g=g;
		this.mn=mn;
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.c=new Color(Integer.parseInt(cl.split(",")[0]),Integer.parseInt(cl.split(",")[1]),Integer.parseInt(cl.split(",")[2]));
		this.bg=this._box(tx,this.w,this.h,this.c);
		this.ti=ti;
		this.mn.mf.call(f,this);
	}



	public void reset(){

	}



	public void update(){
		if (this.g.c.get("triangle")==1){
			this.mn.mf.call("start_level","0",this);
		}
	}



	public void draw(Graphics2D g,Graphics2D og){
		g.drawImage(this.bg,this.x,this.y,null);
	}



	public int _ti_a(){
		return this.il.length;
	}
}