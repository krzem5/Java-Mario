package com.krzem.mario;



import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.lang.Exception;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;



public class Game extends Constants{
	public Main cls;
	public Controller c;
	public Menu mn;
	public Level lv;



	public Game(Main cls){
		this.cls=cls;
		this.mn=new Menu(this.cls,this);
		// this.mn.open("title-screen");
		this.lv=new Level(this.cls,this,this.mn,"0");this.lv._vs=true;
		this._find_controller();
	}



	public void update(){
		if (this.c==null){
			return;
		}
		if (this.lv!=null){
			this.lv.update();
		}
		this.mn.update();
	}



	public void draw(Graphics2D g,Graphics2D og){
		if (this.lv!=null){
			this.lv.draw(g,og);
		}
		this.mn.draw(g,og);
	}



	private void _find_controller(){
		Game cls=this;
		new Thread(new Runnable(){
			@Override
			public void run(){
				while (true){
					ArrayList<Controller> cl=Controller.list();
					if (cl.size()>0){
						cls.c=cl.get(0);
						return;
					}
					try{
						Thread.sleep(50);
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}