package com.krzem.mario;



import java.awt.Graphics2D;



public class Player extends Entity{
	public int w=1;
	public int h=1;
	public int lx;
	public int ly;
	public int d=1;
	public boolean dead=false;
	public boolean jmp=false;
	public boolean cr=false;
	private boolean _j=false;
	private double _sjy=0;
	private boolean _sp=false;
	private double _dsy=0;
	private int _rd=0;
	private double _cfi=0;



	@Override
	public void init(){

	}



	@Override
	public void update(){
		if (this.dead==true){
			this.vx=0;
			this.y+=this.vy;
			this.vy+=PLAYER_DEATH_GRAVITY/this.lv.cls.FPS;
			if (this.y>this._dsy+GAME_SIZE.height+PLAYER_DEATH_END_BUFFOR){
				this.lv.end();
			}
			return;
		}
		if (Math.abs(this.lv.g.c.get("left-joystick-x"))<30){
			this._sp=false;
		}
		if (this.lv.g.c.get("circle")==1){
			this._sp=true;
		}
		if (this.lv.g.c.get("down")==1){
			this.cr=true;
		}
		else{
			this.cr=false;
		}
		this._rd=0;
		if (this.cr==false&&this.lv.g.c.get("left-joystick-x")>=30){
			this.vx=Math.max(this.vx,(this._sp==true?PLAYER_RUN_SPEED:PLAYER_WALK_SPEED)/this.lv.cls.FPS*(this.on_g==true?1:PLAYER_AIR_MOVE_MULT));
			this._rd=(this._sp==true?2:1);
			this.d=1;
		}
		if (this.cr==false&&this.lv.g.c.get("left-joystick-x")<=-30){
			this.vx=-Math.max(-this.vx,(this._sp==true?PLAYER_RUN_SPEED:PLAYER_WALK_SPEED)/this.lv.cls.FPS*(this.on_g==true?1:PLAYER_AIR_MOVE_MULT));
			this._rd=(this._sp==true?2:1);
			this.d=0;
		}
		if (this.cr==false&&this.lv.g.c.get("cross")==1&&this.lv.g.c.get("circle")==1&&this.on_g==true){
			this.vy=-HIGH_JUMP_HEIGHT_GRAVITY/60;
			this._j=true;
			this.jmp=true;
			this._sjy=this.y+0;
		}
		else if (this.cr==false&&this.lv.g.c.get("cross")==1&&this.on_g==true){
			this.vy=-SMALL_JUMP_HEIGHT_GRAVITY/60;
			this._j=true;
			this.jmp=true;
			this._sjy=this.y+0;
		}
		this.lv.ce.collide(this);
		if (this.on_g==true||this._sjy<this.y){
			this._j=false;
		}
		this.lx=(int)this.x-PLAYER_SCREEN_CENTER.width;
		if (this._j==false){
			this.ly=(int)(this.y-0.5)-PLAYER_SCREEN_CENTER.height;
		}
		if (Math.abs(this._rd)==1){
			this._cfi=(this._cfi+PLAYER_WALK_ANIMATION_ADD/this.lv.cls.FPS)%3;
		}
		if (Math.abs(this._rd)==2){
			this._cfi=(this._cfi+PLAYER_RUN_ANIMATION_ADD/this.lv.cls.FPS)%3;
		}
		this.vx=Math.min(this.vx,PLAYER_MAX_X_VEL/this.lv.cls.FPS);
		this.vy=Math.min(this.vy,PLAYER_MAX_Y_VEL/this.lv.cls.FPS);
		this.x+=this.vx;
		this.y+=this.vy;
		if (this.on_g==true){
			this.vx*=PLAYER_GROUD_FRICTION_X/this.lv.cls.FPS;
		}
		else{
			this.vx*=PLAYER_AIR_FRICTION_X/this.lv.cls.FPS;
		}
		this.vy+=PLAYER_GRAVITY/this.lv.cls.FPS;
		if (this.vy>0&&this.jmp==true){
			this.jmp=false;
		}
		this.dead=this._check_dead();
		if (this.dead==true){
			this.vx=0;
			this.vy=-PLAYER_DEATH_JUMP/this.lv.cls.FPS;
			this._dsy=this.y+0;
		}
		this.vx=Math.min(this.vx,PLAYER_MAX_X_VEL/this.lv.cls.FPS);
		this.vy=Math.min(this.vy,PLAYER_MAX_Y_VEL/this.lv.cls.FPS);
	}



	@Override
	public void draw(Graphics2D g,Graphics2D og){
		String p=this.get_image_fr();
		g.drawImage(this.get_image(p),(int)(this.x*TILE_SIZE.width)-this.get_image(p).getWidth()/2,(int)(this.y*TILE_SIZE.width)-this.get_image(p).getHeight(),this.get_image(p).getWidth(),this.get_image(p).getHeight(),null);
	}



	@Override
	public double[][][] next_shapes(){
		return this.get_contour(this.get_base_fr()+".cf",this.x+this.vx-this.w/2d,this.y+this.vy-this.h);
	}



	@Override
	public double[][] next_polygon(){
		return this.get_polygon(this.get_base_fr()+".cf",this.x+this.vx-this.w/2d,this.y+this.vy-this.h);
	}



	@Override
	public double[][][] shapes(){
		return this.get_contour(this.get_base_fr()+".cf",this.x-this.w/2d,this.y-this.h);
	}



	@Override
	public double[][] polygon(){
		return this.get_polygon(this.get_base_fr()+".cf",this.x-this.w/2d,this.y-this.h);
	}



	private boolean _check_dead(){
		// if (this.cl.get(SpikeBlock.class).size()>0){
		// 	return true;
		// }
		return false;
	}



	private String get_base_fr(){
		String d=(this.d==0?"left-":"right-");
		if (this.dead==true){
			return d+"death";
		}
		if (this._rd==0&&this.cr==false&&this.on_g==true){
			return d+"stand";
		}
		if (this._rd>=1&&this.on_g==true){
			return d+"walk";
		}
		if (this.on_g==false&&this.jmp==true){
			return d+"jump";
		}
		if (this.on_g==false&&this.jmp==false){
			return d+"fall";
		}
		if (this.cr==true&&this.on_g==true){
			return d+"crouch";
		}
		return null;
	}



	private String get_image_fr(){
		String fr=this.get_base_fr();
		if (fr.endsWith("walk")){
			fr+="@"+Integer.toString((int)this._cfi);
		}
		return fr+".png";
	}
}