package com.krzem.mario;



import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Menu extends Constants{
	public Main cls;
	public Game g;
	public Map<String,List<MenuItem>> il=null;
	public Map<String,Integer> til=null;
	public Map<String,String> hl=null;
	public double mx=SIZE.width/2;
	public double my=SIZE.height/2;
	public double dmx=SIZE.width/2;
	public double dmy=SIZE.height/2;
	public boolean mm=true;
	public int ti=0;
	public MenuFunctions mf;
	public boolean _anim=false;
	private String _pg=null;
	private boolean _op=false;
	private boolean ld=false;
	private boolean rd=false;
	private boolean cd=false;



	public Menu(Main cls,Game g){
		this.cls=cls;
		this.g=g;
		this.mf=new MenuFunctions(this.cls,this.g,this);
		IO.load_menu_config(this);
	}



	public void update(){
		if (this._op==false||this._anim==true){
			return;
		}
		if (this.g.c.get("left-joystick-x")<-30){
			this.dmx-=this._map(this.g.c.get("left-joystick-x"),29,-128,0,MENU_MAX_CURSOR_SPEED);
			this.mm=true;
		}
		if (this.g.c.get("left-joystick-x")>30){
			this.dmx+=this._map(this.g.c.get("left-joystick-x"),29,128,0,MENU_MAX_CURSOR_SPEED);
			this.mm=true;
		}
		if (this.g.c.get("left-joystick-y")<-30){
			this.dmy-=this._map(this.g.c.get("left-joystick-y"),29,-128,0,MENU_MAX_CURSOR_SPEED);
			this.mm=true;
		}
		if (this.g.c.get("left-joystick-y")>30){
			this.dmy+=this._map(this.g.c.get("left-joystick-y"),29,128,0,MENU_MAX_CURSOR_SPEED);
			this.mm=true;
		}
		this.dmx=Math.min(Math.max(this.dmx,0),SIZE.width);
		this.dmy=Math.min(Math.max(this.dmy,0),SIZE.height);
		this.mx=this._lerp(this.mx,this.dmx,MENU_CURSOR_LERP_PROC);
		this.my=this._lerp(this.my,this.dmy,MENU_CURSOR_LERP_PROC);
		this.mx=Math.min(Math.max(this.mx,0),SIZE.width);
		this.my=Math.min(Math.max(this.my,0),SIZE.height);
		if (this.til.get(this._pg)!=0){
			if (this.g.c.get("r1")==1){
				if (this.mm==false&&this.rd==false){
					this.ti++;
				}
				this.mm=false;
				this.rd=true;
			}
			else{
				this.rd=false;
			}
			if (this.g.c.get("l1")==1){
				if (this.mm==false&&this.ld==false){
					this.ti--;
				}
				this.mm=false;
				this.ld=true;
			}
			else{
				this.ld=false;
			}
			this.ti=(this.ti+this.til.get(this._pg))%this.til.get(this._pg);
		}
		if (this.g.c.get("circle")==1){
			if (this.cd==false&&this.hl.get(this._pg)!=null){
				this.mf.call(this.hl.get(this._pg));
			}
			this.cd=true;
		}
		else{
			this.cd=false;
		}
		for (MenuItem mi:this.il.get(this._pg)){
			mi.update();
		}
	}



	public void draw(Graphics2D g,Graphics2D og){
		if (this._op==false){
			this.mf.draw(g,og);
			return;
		}
		for (MenuItem mi:this.il.get(this._pg)){
			mi.draw(g,og);
		}
		g.drawImage(IO.get_image(MENU_ASSETS_FILE_PATH+CURSOR_IMAGE_FILE_PATH),(int)this.mx-IO.get_image(MENU_ASSETS_FILE_PATH+CURSOR_IMAGE_FILE_PATH).getWidth()/2,(int)this.my-IO.get_image(MENU_ASSETS_FILE_PATH+CURSOR_IMAGE_FILE_PATH).getWidth()/2,null);
		this.mf.draw(g,og);
	}



	public void open(String pg){
		if (this._pg!=null){
			for (MenuItem mi:this.il.get(this._pg)){
				mi.reset();
			}
		}
		this._pg=pg;
		this._op=(this._pg==null?false:true);
		this.ld=false;
		this.rd=false;
		this.ti=0;
		this.mm=true;
	}



	private double _map(double v,double aa,double ab,double ba,double bb){
		return (v-aa)/(ab-aa)*(bb-ba)+ba;
	}



	private double _lerp(double a,double b,double t){
		return a+t*(b-a);
	}
}