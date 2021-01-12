package com.krzem.mario;



import java.lang.Math;
import java.util.ArrayList;
import java.util.List;



public class CollisionEngine extends Constants{
	public Level lv;
	private List<CollisionObject> ol;



	public CollisionEngine(Level lv){
		this.lv=lv;
		this.ol=new ArrayList<CollisionObject>();
	}



	public CollisionObject add_object(Object o,double[][][] s,double[][] pl,int[][] cd){
		this.ol.add(new CollisionObject(this,o,s,pl,cd));
		return this.ol.get(this.ol.size()-1);
	}



	public void collide(Entity e){
		double[][][] sl=e.next_shapes();
		e.cl.clear();
		for (double[][] s:sl){
			double[] aabb=this._aabb(s);
			double[][] p=this._normals(s);
			this._walls(e,sl,aabb);
			for (CollisionObject co:this.ol){
				if (e.co==co||this._intersect_aabb_aabb(co.t_aabb,aabb)==false){
					continue;
				}
				boolean[] cd=this._collision_dir(e,co);
				if (cd==null){
					continue;
				}
				for (int j=0;j<co.len;j++){
					if (this._separate(e,co,s,sl,aabb,p,j,cd)==true){
						break;
					}
				}
			}
		}
		double[][] pl=e.next_polygon();
		double[] t_aabb=this._aabb(pl);
		boolean on_g=false;
		for (CollisionObject co:this.ol){
			if (e.co==co||this._intersect_aabb_aabb(co.t_aabb,t_aabb)==false||this._collision_dir(e,co)==null){
				continue;
			}
			if (this._collision_poly_poly(co.pl,pl)==true){
				Collision c=co.coll_o(t_aabb[0]-co.t_aabb[0],t_aabb[1]-co.t_aabb[1]);
				if (c.is_g()){
					on_g=true;
				}
				e.cl.add(c);
			}
		}
		e.on_g=on_g;
	}



	public double[] _aabb(double[][] s){
		double[] o=new double[]{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE};
		for (double[] p:s){
			o[0]=Math.min(o[0],p[0]);
			o[1]=Math.min(o[1],p[1]);
			o[2]=Math.max(o[2],p[0]);
			o[3]=Math.max(o[3],p[1]);
		}
		return o;
	}



	public double[][] _normals(double[][] s){
		double[][] o=new double[s.length][2];
		for (int i=0;i<s.length;i++){
			double x=s[(i+1)%s.length][0]-s[i][0];
			double y=s[(i+1)%s.length][1]-s[i][1];
			double m=Math.sqrt(x*x+y*y);
			o[i][0]=y/m;
			o[i][1]=-x/m;
		}
		return o;
	}



	public boolean _intersect_aabb_aabb(double[] a,double[] b){
		return (a[2]>=b[0]&&a[0]<=b[2]&&a[3]>=b[1]&&a[1]<=b[3]);
	}



	private void _walls(Entity e,double[][][] sl,double[] aabb){
		double[] off=new double[2];
		if (aabb[0]<0){
			off[0]-=aabb[0];
		}
		if (aabb[1]<0){
			off[1]-=aabb[1];
		}
		if (aabb[2]>=this.lv.ld.w-1){
			off[0]+=this.lv.ld.w-1-aabb[2];
		}
		if (aabb[3]>=this.lv.ld.h-1){
			off[1]+=this.lv.ld.h-1-aabb[3];
		}
		e.vx+=off[0];
		e.vy+=off[1];
		if (off[0]!=0||off[1]!=0){
			for (double[][] s:sl){
				this._move_shape(s,aabb,off[0],off[1]);
			}
		}
	}



	private boolean _separate(Entity e,CollisionObject co,double[][] e_s,double[][][] e_sl,double[] e_aabb,double[][] e_p,int idx,boolean[] cd){
		if (this._intersect_aabb_aabb(e_aabb,co.aabb[idx])==false){
			return false;
		}
		List<double[]> ca=new ArrayList<double[]>();
		double[] r=new double[4];
		double mo=Double.MAX_VALUE;
		double[] ov=new double[2];
		for (int i=0;i<e_p.length+co.len;i++){
			double[] v=(i>=e_p.length?co.p[idx][i-e_p.length]:e_p[i]);
			this._flatten(e_s,v,r,0);
			this._flatten(co.s[idx],v,r,2);
			if (r[0]>r[3]||r[2]>r[1]){
				return false;
			}
			double o=0;
			if (r[0]<r[2]){
				if (r[1]<r[3]){
					o=r[1]-r[2];
				}
				else{
					o=(r[1]-r[2]<r[3]-r[0]?r[1]-r[2]:-r[3]+r[0]);
				}
			}
			else{
				if (r[1]>r[3]){
					o=r[0]-r[3];
				}
				else{
					o=(r[1]-r[2]<r[3]-r[0]?r[1]-r[2]:-r[3]+r[0]);
				}
			}
			if (Math.abs(o)<mo){
				mo=Math.abs(o);
				ov=new double[]{-v[0]*o,v[1]*o};
				if (o<0){
					ov=new double[]{v[0]*o,-v[1]*o};
				}
			}
		}
		if (Double.MAX_VALUE-mo==0){
			return false;
		}
		if (e.vx!=0&&ov[0]!=0&&this._sign(e.vx)==this._sign(ov[0])&&e.vx>=ov[0]){
			ov[0]=0;
		}
		if (e.vy!=0&&ov[1]!=0&&this._sign(e.vy)==this._sign(ov[1])&&e.vy>=ov[1]){
			ov[1]=0;
		}
		e.vx+=((ov[0]<0&&cd[0]==true)||(ov[0]>0&&cd[2]==true)?ov[0]:0);
		e.vy+=((ov[1]<0&&cd[1]==true)||(ov[1]>0&&cd[3]==true)?ov[1]:0);
		for (double[][] ms:e_sl){
			this._move_shape(ms,e_aabb,ov[0],ov[1]);
		}
		return true;
	}



	private boolean[] _collision_dir(Entity e,CollisionObject co){
		if (co.cd==null){
			return new boolean[]{true,true,true,true};
		}
		double[] e_aabb=this._aabb(e.polygon());
		double[] e_naabb=this._aabb(e.next_polygon());
		boolean[] o=new boolean[4];
		boolean ro=false;
		for (int[] d:co.cd){
			if ((d[0]==0||(d[0]<0?e_aabb[2]<=co.t_aabb[0]&&e_naabb[2]>=co.t_aabb[0]:e_aabb[0]>=co.t_aabb[2]&&e_naabb[0]<=co.t_aabb[2]))&&(d[1]==0||(d[1]<0?e_aabb[3]<=co.t_aabb[1]&&e_naabb[3]>=co.t_aabb[1]:e_aabb[1]>=co.t_aabb[3]&&e_naabb[1]<=co.t_aabb[3]))){
				if (d[0]<0){
					o[0]=true;
					ro=true;
				}
				if (d[1]<0){
					o[1]=true;
					ro=true;
				}
				if (d[0]>0){
					o[2]=true;
					ro=true;
				}
				if (d[1]>0){
					o[3]=true;
					ro=true;
				}
			}
		}
		return (ro==true?o:null);
	}



	private void _flatten(double[][] s,double[] v,double[] r,int off){
		double a=Double.MAX_VALUE;
		double b=-Double.MAX_VALUE;
		for (int i=0;i<s.length;i++){
			double d=s[i][0]*v[0]+s[i][1]*v[1];
			a=Math.min(a,d);
			b=Math.max(b,d);
		}
		r[off]=a;
		r[off+1]=b;
	}



	private void _move_shape(double[][] s,double[] aabb,double x,double y){
		for (int i=0;i<s.length;i++){
			s[i][0]+=x;
			s[i][1]+=y;
		}
		aabb[0]+=x;
		aabb[1]+=y;
		aabb[2]+=x;
		aabb[3]+=y;
	}



	private boolean _collision_poly_poly(double[][] p1,double[][] p2){
		for (int i=0;i<p1.length;i++){
			for (int j=0;j<p2.length;j++){
				if (this._collision_line_line(p1[i][0],p1[i][1],p1[(i+1)%p1.length][0],p1[(i+1)%p1.length][1],p2[j][0],p2[j][1],p2[(j+1)%p2.length][0],p2[(j+1)%p2.length][1])){
					return true;
				}
			}
		}
		return false;
	}



	private boolean _collision_line_line(double l1ax,double l1ay,double l1bx,double l1by,double l2ax,double l2ay,double l2bx,double l2by){
		double t=((l1ax-l2ax)*(l2ay-l2by)-(l1ay-l2ay)*(l2ax-l2bx))/((l1ax-l1bx)*(l2ay-l2by)-(l1ay-l1by)*(l2ax-l2bx));
		double u=-((l1ax-l1bx)*(l1ay-l2ay)-(l1ay-l1by)*(l1ax-l2ax))/((l1ax-l1bx)*(l2ay-l2by)-(l1ay-l1by)*(l2ax-l2bx));
		return (0<=u&&u<=1&&0<=t&&t<=1);
	}



	private int _sign(double v){
		return (v==0?0:(int)(v/Math.abs(v)));
	}
}
