package com.krzem.mario;



import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



public class MenuButton extends MenuItem{
	public Main cls;
	public Game g;
	public Menu mn;
	private int x;
	private int y;
	private int w;
	private int h;
	private float lbs;
	private String lb;
	private String tx;
	private String fn;
	private int ti;
	private BufferedImage _ia;
	private BufferedImage _ib;
	private Color c;
	private boolean _hv=false;



	public MenuButton(Main cls,Game g,Menu mn,int x,int y,int w,int h,String lb,String tx,String fn,String cl,int gw,int ti){
		this.cls=cls;
		this.g=g;
		this.mn=mn;
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.lbs=Float.parseFloat(lb.split(":")[0]);
		this.lb=lb.substring(lb.indexOf(":")+1);
		this.tx=tx;
		this.fn=fn;
		this.ti=ti;
		this.c=new Color(Integer.parseInt(cl.split(",")[0]),Integer.parseInt(cl.split(",")[1]),Integer.parseInt(cl.split(",")[2]));
		this._ia=this._get_image_A();
		this._ib=this._get_image_B(gw);
	}



	public void reset(){
		this._hv=false;
	}



	public void update(){
		if (this.mn.mm){
			this._hv=(this.mn.mx>=this.x&&this.mn.mx<=this.x+this.w&&this.mn.my>=this.y&&this.mn.my<=this.y+this.h);
		}
		else{
			this._hv=(this.mn.ti==this.ti);
		}
		if (this._hv==true&&(this.g.c.get("cross")==1)){
			this.mn.mf.call(this.fn);
		}
	}



	public void draw(Graphics2D g,Graphics2D og){
		g.drawImage((this._hv==true?this._ia:this._ib),this.x,this.y,null);
		double mx=WINDOW_SIZE.width/SIZE.width;
		double my=WINDOW_SIZE.height/SIZE.height;
		og.setFont(DEFAULT_FONT.deriveFont((float)(this.lbs*(mx+my)/2)));
		FontMetrics fm=og.getFontMetrics();
		og.drawString(this.lb,(int)((this.x+this.w/2)*mx-fm.stringWidth(this.lb)/2),(int)((this.y+this.h/2)*my+fm.getStringBounds(this.lb,0,this.lb.length(),og).getHeight()/2-fm.getDescent()));
	}



	private BufferedImage _get_image_A(){
		return this._box(this.tx,this.w,this.h,this.c);
	}



	private BufferedImage _get_image_B(int gw){
		BufferedImage bi=new BufferedImage(this.w,this.h,BufferedImage.TYPE_INT_ARGB);
		Graphics2D bg=(Graphics2D)bi.createGraphics();
		bg.drawImage(this._box(this.tx,this.w-gw*2,this.h-gw*2,this.c),gw,gw,null);
		return bi;
	}
}