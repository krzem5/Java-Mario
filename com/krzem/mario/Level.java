package com.krzem.mario;



import java.awt.Graphics2D;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;



public class Level extends Constants{
	public Main cls;
	public Game g;
	public Menu mn;
	public LevelData ld;
	public Player p;
	public Background bg;
	public CollisionEngine ce;
	public List<Block> bl=new ArrayList<Block>();
	private int[] cto=null;
	public int[] co=new int[2];
	public int[] cdo=new int[2];
	private int[] _ll=new int[]{-1,-1};
	private boolean[] _wl;
	public boolean _vs=false;



	public Level(Main cls,Game g,Menu mn,String fp){
		this.cls=cls;
		this.g=g;
		this.mn=mn;
		this.ld=new LevelData(this);
		this.ce=new CollisionEngine(this);
		IO.load_level(this,fp);
	}



	public void update(){
		if (this.g.c==null){
			return;
		}
		if (this.p.dead==true){
			this.p.update();
			return;
		}
		this._wl=this._recalc_wl();
		if (this.co[0]!=this.p.lx||this.co[1]!=this.p.ly){
			this._try_move_cam(this.p.lx,this.p.ly);
		}
		if (this._vs==false||this.p==null){
			return;
		}
		for (Block b:this.bl){
			b.update();
		}
		this.p.update();
		if (this.cto==null){
			return;
		}
		double s=Math.sqrt(this.p.vx*this.p.vx+this.p.vy*this.p.vy);
		double mx=CAM_SCROLL_SPEED/this.cls.FPS;
		if (s>CAM_SCROLL_PLAYER_MIN_VEL+CAM_SCROLL_SPEED/this.cls.FPS){
			mx=CAM_FAST_SCROLL_SPEED/this.cls.FPS;
		}
		if (Math.abs(this.p.vy)>CAM_SCROLL_PLAYER_MIN_VEL+CAM_FAST_SCROLL_SPEED_MAX/this.cls.FPS){
			mx=CAM_SUPER_FAST_SCROLL_SPEED/this.cls.FPS;
		}
		this.cdo[0]+=(int)(Math.min(Math.abs(this.cdo[0]-this.cto[0]*TILE_SIZE.width),mx*TILE_SIZE.width)*(this.cdo[0]<this.cto[0]*TILE_SIZE.width?1:-1));
		this.cdo[1]+=(int)(Math.min(Math.abs(this.cdo[1]-this.cto[1]*TILE_SIZE.height),mx*TILE_SIZE.height)*(this.cdo[1]<this.cto[1]*TILE_SIZE.height?1:-1));
		this.co[0]=(int)((this.cdo[0]+TILE_SIZE.width/2)/TILE_SIZE.width);
		this.co[1]=(int)((this.cdo[1]+TILE_SIZE.height/2)/TILE_SIZE.height);
		this.bg.update();
	}



	public void draw(Graphics2D g,Graphics2D og){
		if (this.cto==null){
			return;
		}
		this.bg.draw(g,og);
		g.translate(-this.cdo[0],-this.cdo[1]-TILE_SIZE.height/2);
		for (Block b:this.bl){
			b.draw(g,og);
		}
		this.p.draw(g,og);
	}



	public void end(){
		this.mn.mf.call("end_level",this);
	}



	public void _create(){
		this.bg=new Background(this,this.ld.bg_t);
		this.p=(Player)Entity.get(-1,this,this.ld.sx,this.ld.sy,null);
		for (List<LevelBlockData>[] i:this.ld.bl){
			for (List<LevelBlockData> j:i){
				if (j==null){
					continue;
				}
				for (LevelBlockData k:j){
					this.bl.add(Block.get(k.id,this,k.x,k.y,k.d));
				}
			}
		}
		this.update();
	}



	private void _try_move_cam(int x,int y){
		if (this.cto==null){
			this.cto=new int[]{0,y};
			this.cdo[1]=y*TILE_SIZE.height;
			this._wl=this._recalc_wl();
			return;
		}
		boolean mv=false;
		if (!((x<this.co[0]&&(x<0||this._wl[0]==true))||(x>this.co[0]&&(x+GAME_SIZE.width>=this.ld.w||this._wl[2]==true)))){
			this.cto[0]=x;
			mv=true;
		}
		if (!((y<this.co[1]&&(y<0||this._wl[1]==true))||(y>this.co[1]&&(y+GAME_SIZE.height>=this.ld.h||this._wl[3]==true)))){
			this.cto[1]=y;
			mv=true;
		}
		if (mv==true){
			this._wl=this._recalc_wl();
		}
	}



	private boolean[] _recalc_wl(){
		boolean[] o=new boolean[4];
		int[] bc=new int[4];
		for (Block b:this.bl){
			if (b.on_screen()==false){
				continue;
			}
			if (b.x-this.co[0]==0){
				bc[0]++;
				if (bc[0]==GAME_SIZE.height){
					o[0]=true;
				}
			}
			if (b.x-this.co[0]==GAME_SIZE.width-1){
				bc[2]++;
				if (bc[2]==GAME_SIZE.height+1){
					o[2]=true;
				}
			}
			if (b.y-this.co[1]==0){
				bc[1]++;
				if (bc[1]==GAME_SIZE.width){
					o[1]=true;
				}
			}
			if (b.y-this.co[1]==GAME_SIZE.height-1){
				bc[3]++;
				if (bc[3]==GAME_SIZE.width){
					o[3]=true;
				}
			}
		}
		return o;
	}
}