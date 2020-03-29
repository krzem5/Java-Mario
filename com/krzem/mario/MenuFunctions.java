package com.krzem.mario;



import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.lang.Exception;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MenuFunctions extends Constants{
	public Main cls;
	public Game g;
	public Menu mn;
	private Map<String,Method> _ft;
	private int _tt=0;
	private int _ts=0;
	private long _fes;
	private long _fee;
	private double[] _stlx;
	private double[] _stly;
	private int _sta=0;



	public MenuFunctions(Main cls,Game g,Menu mn){
		this.cls=cls;
		this.g=g;
		this.mn=mn;
		this._gen_f_table();
	}



	public void title_screen_back(){
		MenuFunctions cls=this;
		this.mn._anim=true;
		this._tt=1;
		new Thread(new Runnable(){
			@Override
			public void run(){
				cls._fes=System.nanoTime();
				cls._fee=System.nanoTime()+(long)0.45e9;
				cls._ts=1;
				try{
					Thread.sleep(600);
				}
				catch (Exception e){
					e.printStackTrace();
				}
				cls.mn.open("title-screen");
				cls._fes=System.nanoTime();
				cls._fee=System.nanoTime()+(long)0.45e9;
				cls._ts=2;
				try{
					Thread.sleep(700);
				}
				catch (Exception e){
					e.printStackTrace();
				}
				cls._tt=0;
				cls._ts=0;
				cls.mn._anim=false;
			}
		}).start();
	}



	public void open_local_levels(){
		MenuFunctions cls=this;
		this.mn._anim=true;
		this._tt=1;
		new Thread(new Runnable(){
			@Override
			public void run(){
				cls._fes=System.nanoTime();
				cls._fee=System.nanoTime()+(long)0.45e9;
				cls._ts=1;
				try{
					Thread.sleep(700);
				}
				catch (Exception e){
					e.printStackTrace();
				}
				cls.mn.open("local-levels");
				cls._fes=System.nanoTime();
				cls._fee=System.nanoTime()+(long)0.45e9;
				cls._ts=2;
				try{
					Thread.sleep(450);
				}
				catch (Exception e){
					e.printStackTrace();
				}
				cls._tt=0;
				cls._ts=0;
				cls.mn._anim=false;
			}
		}).start();
	}



	public void start_level(String lfp,MenuScrollbox sb){
		MenuFunctions cls=this;
		this.mn._anim=true;
		this._stlx=new double[64];
		this._stly=new double[64];
		this._tt=2;
		this._ts=1;
		new Thread(new Runnable(){
			@Override
			public void run(){
				cls.g.lv=new Level(cls.cls,cls.g,cls.mn,lfp);
				for (int i=0;i<192+120;i++){
					for (int j=0;j<=Math.min(i/3,63);j++){
						cls._stlx[j]=Math.min(cls._stlx[j]+2,240);
						cls._stly[j]=Math.min(cls._stly[j]+1.125,135);
					}
					try{
						Thread.sleep(3);
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
				try{
					Thread.sleep(250);
				}
				catch (Exception e){
					e.printStackTrace();
				}
				cls.mn.open(null);
				cls._ts=2;
				cls._sta=0;
				for (int i=0;i<255;i++){
					cls._sta++;
					try{
						Thread.sleep(3);
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
				try{
					Thread.sleep(1500);
				}
				catch (Exception e){
					e.printStackTrace();
				}
				for (int i=0;i<255;i++){
					cls._sta--;
					try{
						Thread.sleep(3);
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
				try{
					Thread.sleep(350);
				}
				catch (Exception e){
					e.printStackTrace();
				}
				cls.g.lv._vs=true;
				while (cls.g.lv.ld._loaded==false){
					try{
						Thread.sleep(1000/60);
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
				cls._ts=3;
				cls._sta=255;
				for (int i=0;i<255;i++){
					cls._sta--;
					try{
						Thread.sleep(1);
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
				cls._tt=0;
				cls._ts=0;
				cls.mn._anim=false;
			}
		}).start();
	}



	public void end_level(Level lv){
		cls.quit();
	}



	public void create_local_level_scrollbox(MenuScrollbox sb){

	}



	public void draw(Graphics2D g,Graphics2D og){
		g.setColor(Color.black);
		if (this._tt==1&&this._ts==1){
			g.fillRect((int)this._map(Math.min(System.nanoTime(),this._fee),this._fes,this._fee,SIZE.width,0),0,SIZE.width,SIZE.height);
			og.clearRect((int)this._map(Math.min(System.nanoTime(),this._fee),this._fes,this._fee,WINDOW_SIZE.width,0),0,WINDOW_SIZE.width,WINDOW_SIZE.height);
		}
		else if (this._tt==1&&this._ts==2){
			g.fillRect((int)this._map(System.nanoTime(),this._fes,this._fee,0,-SIZE.width),0,SIZE.width,SIZE.height);
			og.clearRect((int)this._map(System.nanoTime(),this._fes,this._fee,0,-WINDOW_SIZE.width),0,WINDOW_SIZE.width,WINDOW_SIZE.height);
		}
		if (this._tt==2&&this._ts==1){
			int sx=WINDOW_SIZE.width/SIZE.width;
			int sy=WINDOW_SIZE.height/SIZE.height;
			for (int i=SIZE.width-1;i>=0;i-=240){
				for (int j=0;j<SIZE.height;j+=135){
					int k=Math.min(((SIZE.width-1-i)/240+1)*(j/135+1),63);
					g.fillRect(i-(int)this._stlx[k],j,(int)this._stlx[k],(int)this._stly[k]);
					og.clearRect((i-(int)this._stlx[k])*sx,j*sy,(int)this._stlx[k]*sx,(int)this._stly[k]*sy);
				}
			}
		}
		else if (this._tt==2&&this._ts==2){
			g.fillRect(0,0,SIZE.width,SIZE.height);
			og.clearRect(0,0,WINDOW_SIZE.width,WINDOW_SIZE.height);
			og.setColor(new Color(255,255,255,this._sta));
			og.setFont(DEFAULT_FONT.deriveFont(500f));
			FontMetrics fm=og.getFontMetrics();
			og.drawString(this.g.lv.ld.nm,WINDOW_SIZE.width/2-fm.stringWidth(this.g.lv.ld.nm)/2,WINDOW_SIZE.height/2+(int)fm.getStringBounds(this.g.lv.ld.nm,0,this.g.lv.ld.nm.length(),og).getHeight()/2-fm.getDescent());
		}
		else if (this._tt==2&&this._ts==3){
			g.setColor(new Color(0,0,0,this._sta));
			og.setColor(new Color(0,0,0,this._sta));
			g.fillRect(0,0,SIZE.width,SIZE.height);
			og.fillRect(0,0,WINDOW_SIZE.width,WINDOW_SIZE.height);
		}
	}



	public void call(String fn,Object... args){
		try{
			IO.dump_log(String.format("Calling Menu Function: '%s' with args %s",fn,Arrays.toString(args)));
			this._ft.get(fn).invoke(this,args);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}



	private void _gen_f_table(){
		this._ft=new HashMap<String,Method>();
		for (Method m:this.getClass().getDeclaredMethods()){
			if (m.getExceptionTypes().length==0&&m.getModifiers()==Modifier.PUBLIC&&m.getReturnType().toString().equals("void")&&!MENU_EXCLUDE_FUNCTION_NAMES.contains(m.getName())){
				IO.dump_log(String.format("Registering Menu Function: %s",m.getName()));
				this._ft.put(m.getName(),m);
			}
		}
	}



	private double _map(double v,double aa,double ab,double ba,double bb){
		return (v-aa)/(ab-aa)*(bb-ba)+ba;
	}
}