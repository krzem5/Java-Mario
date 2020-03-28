package com.krzem.mario;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



public class LevelData extends Constants{
	public Level lv;
	public String nm=null;
	public int v=-1;
	public boolean cl=false;
	public Date cr_d;
	public Date lp_d;
	public int bg_t;
	public int w=-1;
	public int h=-1;
	public int sx=-1;
	public int sy=-1;
	public int ex=-1;
	public int ey=-1;
	public int ec=-1;
	public LevelEntityData[] el=new LevelEntityData[0];
	public int bc=-1;
	public List<LevelBlockData>[][] bl=new ArrayList[0][0];
	public String hs;
	public boolean _loaded=false;



	public LevelData(Level lv){
		this.lv=lv;
	}



	public void _done(){
		this._loaded=true;
		this.lv._create();
	}



	public void log(){
		String el="";
		for (LevelEntityData e:this.el){
			el+=String.format("      Entity:\n        Id: %d\n        Pos:\n          X: %d\n          Y: %d\n        Data Length: %d\n        Data: %s\n",e.id,e.x,e.y,e.dl,Arrays.toString(e.d));
		}
		String bl="";
		for (List<LevelBlockData>[] i:this.bl){
			for (List<LevelBlockData> j:i){
				if (j==null){
					continue;
				}
				for (LevelBlockData k:j){
					bl+=String.format("      Block:\n        Id: %d\n        Pos:\n          X: %d\n          Y: %d\n        Data Length: %d\n        Data: %s\n",k.id,k.x,k.y,k.dl,Arrays.toString(k.d));
				}
			}
		}
		System.out.printf("=========> %s <=========\nHeader:\n  Format Version: %d\n  Cleared: %b\n  Created: %s\n  Last Played: %s\nBody:\n  Type: %d\n  Size:\n    W: %d\n    H: %d\n  Start Pos:\n    X: %d\n    Y: %d\n  End Pos:\n    X: %d\n    Y: %d\n  Entities:\n    Count: %d\n    List:\n%s  Blocks:\n    Count: %d\n    List:\n%sEnd:\n  Hash: %s\n",this.nm,this.v,this.cl,this.cr_d.toString(),this.lp_d.toString(),this.bg_t,this.w,this.h,this.sx,this.sy,this.w-this.ex,this.ey,this.ec,el,this.bc,bl,this.hs);
	}
}