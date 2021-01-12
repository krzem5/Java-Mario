package com.krzem.mario;



import java.awt.Graphics2D;



public class MenuImage extends MenuItem{
	public Main cls;
	public Game g;
	public Menu mn;
	private String p;
	private int x;
	private int y;
	private boolean lp;



	public MenuImage(Main cls,Game g,Menu mn,String p,int x,int y,boolean lp){
		this.cls=cls;
		this.g=g;
		this.mn=mn;
		this.p=p;
		this.x=x;
		this.y=y;
		this.lp=lp;
	}



	public void reset(){

	}



	public void update(){

	}



	public void draw(Graphics2D g,Graphics2D og){
		g.drawImage(IO.get_image(MENU_ASSETS_FILE_PATH+this.p),this.x,this.y,null);
	}
}