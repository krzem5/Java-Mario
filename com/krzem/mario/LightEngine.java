package com.krzem.mario;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;



public class LightEngine extends Constants{
	public Main cls;
	public Game g;



	public LightEngine(Main cls,Game g){
		this.cls=cls;
		this.g=g;
	}



	public List<double[]> calculate(List<double[]> pl,int lc,List<double[]> lgl){
		double[][] rl=new double[lc*lgl.size()*2][4];
		double[][] ll=null;
		int k=0;
		int m=0;
		for (double[] ls:lgl){
			boolean cl=(ll==null?true:false);
			int l=0;
			if (cl==true){
				ll=new double[lc][4];
			}
			for (int i=0;i<pl.size();i++){
				for (int j=0;j<pl.get(i).length;j+=2){
					if (cl==true){
						ll[l]=new double[]{pl.get(i)[j],pl.get(i)[j+1],pl.get(i)[(j+2)%pl.get(i).length],pl.get(i)[(j+3)%pl.get(i).length]};
						l++;
					}
					double a=Math.atan2(pl.get(i)[j+1]-ls[1],pl.get(i)[j]-ls[0]);
					rl[k]=new double[]{ls[0],ls[1],pl.get(i)[j]+Math.cos(a+LIGHT_RAY_ANGLE_OFFSET),pl.get(i)[j+1]+Math.sin(a+LIGHT_RAY_ANGLE_OFFSET),m};
					rl[k+1]=new double[]{ls[0],ls[1],pl.get(i)[j]+Math.cos(a-LIGHT_RAY_ANGLE_OFFSET),pl.get(i)[j+1]+Math.sin(a-LIGHT_RAY_ANGLE_OFFSET),m};
					k+=2;
				}
			}
			m++;
		}
		double[][][] ol=new double[lgl.size()][lc*2][5];
		int[] oil=new int[lgl.size()];
		for (double[] r:rl){
			double md=-1;
			double[] bp=new double[]{r[0],r[1]};
			for (double[] l:ll){
				double[] cp=this._intersection_line_line(r[0],r[1],r[2],r[3],l[0],l[1],l[2],l[3]);
				if (cp!=null){
					double d=Math.sqrt((cp[0]-r[0])*(cp[0]-r[0])+(cp[1]-r[1])*(cp[1]-r[1]));
					if (md==-1){
						md=d;
						bp=cp;
					}
					else if (md>d){
						md=d;
						bp=cp;
					}
				}
			}
			double a=Math.atan2(r[3]-r[1],r[2]-r[0]);
			boolean ok=false;
			int i=0;
			for (double[] crl:ol[(int)r[4]]){
				if (i>=oil[(int)r[4]]){
					break;
				}
				if (crl[0]>a){
					double[][] no=new double[lc*2][5];
					System.arraycopy(ol[(int)r[4]],0,no,0,i);
					System.arraycopy(ol[(int)r[4]],i,no,i+1,lc*2-i-1);
					no[i]=new double[]{a,r[0],r[1],bp[0],bp[1]};
					ol[(int)r[4]]=no;
					ok=true;
					break;
				}
				i++;
			}
			if (ok==false){
				ol[(int)r[4]][oil[(int)r[4]]]=new double[]{a,r[0],r[1],bp[0],bp[1]};
			}
			oil[(int)r[4]]++;
		}
		List<double[]> o=new ArrayList<double[]>();
		for (int i=0;i<lgl.size();i++){
			double[] sa=new double[oil[i]*2];
			for (int j=0;j<oil[i];j++){
				sa[j*2]=ol[i][j][3];
				sa[j*2+1]=ol[i][j][4];
			}
			o.add(sa);
		}
		return o;
	}



	public void draw_debug(Graphics2D g,List<double[]> pl,int lc,List<double[]> lgl){
		double[][] rl=new double[lc*lgl.size()*2][4];
		double[][] ll=null;
		int k=0;
		int m=0;
		for (double[] ls:lgl){
			boolean cl=(ll==null?true:false);
			int l=0;
			if (cl==true){
				ll=new double[lc][4];
			}
			for (int i=0;i<pl.size();i++){
				for (int j=0;j<pl.get(i).length;j+=2){
					if (cl==true){
						ll[l]=new double[]{pl.get(i)[j],pl.get(i)[j+1],pl.get(i)[(j+2)%pl.get(i).length],pl.get(i)[(j+3)%pl.get(i).length]};
						l++;
					}
					double a=Math.atan2(pl.get(i)[j+1]-ls[1],pl.get(i)[j]-ls[0]);
					rl[k]=new double[]{ls[0],ls[1],pl.get(i)[j]+Math.cos(a+LIGHT_RAY_ANGLE_OFFSET),pl.get(i)[j+1]+Math.sin(a+LIGHT_RAY_ANGLE_OFFSET),m};
					rl[k+1]=new double[]{ls[0],ls[1],pl.get(i)[j]+Math.cos(a-LIGHT_RAY_ANGLE_OFFSET),pl.get(i)[j+1]+Math.sin(a-LIGHT_RAY_ANGLE_OFFSET),m};
					k+=2;
				}
			}
			m++;
		}
		double[][][] ol=new double[lgl.size()][lc*2][7];
		int[] oil=new int[lgl.size()];
		for (double[] r:rl){
			double md=-1;
			double[] bp=new double[]{r[2],r[3]};
			for (double[] l:ll){
				double[] cp=this._intersection_line_line(r[0],r[1],r[2],r[3],l[0],l[1],l[2],l[3]);
				if (cp!=null){
					double d=Math.sqrt((cp[0]-r[0])*(cp[0]-r[0])+(cp[1]-r[1])*(cp[1]-r[1]));
					if (md==-1){
						md=d;
						bp=cp;
					}
					else if (md>d){
						md=d;
						bp=cp;
					}
				}
			}
			double a=Math.atan2(r[3]-r[1],r[2]-r[0]);
			boolean ok=false;
			int i=0;
			for (double[] crl:ol[(int)r[4]]){
				if (i>=oil[(int)r[4]]){
					break;
				}
				if (crl[0]>a){
					double[][] no=new double[lc*2][7];
					System.arraycopy(ol[(int)r[4]],0,no,0,i);
					System.arraycopy(ol[(int)r[4]],i,no,i+1,lc*2-i-1);
					no[i]=new double[]{a,r[0],r[1],bp[0],bp[1],r[2],r[3]};
					ol[(int)r[4]]=no;
					ok=true;
					break;
				}
				i++;
			}
			if (ok==false){
				ol[(int)r[4]][oil[(int)r[4]]]=new double[]{a,r[0],r[1],bp[0],bp[1],r[2],r[3]};
			}
			oil[(int)r[4]]++;
		}
		g.setColor(Color.green);
		List<double[]> lpl=new ArrayList<double[]>();
		for (int i=0;i<lgl.size();i++){
			double[] sa=new double[oil[i]*2];
			for (int j=0;j<oil[i];j++){
				g.setStroke(new BasicStroke(5));
				g.drawLine((int)ol[i][j][1],(int)ol[i][j][2],(int)ol[i][j][3],(int)ol[i][j][4]);
				g.setStroke(new BasicStroke(1));
				g.drawLine((int)ol[i][j][1],(int)ol[i][j][2],(int)ol[i][j][5],(int)ol[i][j][6]);
				sa[j*2]=ol[i][j][3];
				sa[j*2+1]=ol[i][j][4];
			}
			lpl.add(sa);
		}
		g.setColor(new Color(255,255,255,128));
		for (double[] l:lpl){
			int[] xp=new int[l.length/2];
			int[] yp=new int[l.length/2];
			for (int i=0;i<l.length;i+=2){
				xp[i/2]=(int)l[i];
				yp[i/2]=(int)l[i+1];
			}
			g.fillPolygon(xp,yp,l.length/2);
		}
		g.setColor(Color.blue);
		g.setStroke(new BasicStroke(5));
		for (int i=0;i<pl.size();i++){
			for (int j=0;j<pl.get(i).length;j+=2){
				g.drawLine((int)pl.get(i)[j],(int)pl.get(i)[j+1],(int)pl.get(i)[(j+2)%pl.get(i).length],(int)pl.get(i)[(j+3)%pl.get(i).length]);
			}
		}
		g.setColor(Color.yellow);
		for (int i=0;i<pl.size();i++){
			for (int j=0;j<pl.get(i).length;j+=2){
				g.fillRect((int)pl.get(i)[j]-5,(int)pl.get(i)[j+1]-5,10,10);
			}
		}
		g.setColor(Color.pink);
		for (int i=0;i<lgl.size();i++){
			g.drawRect((int)lgl.get(i)[0]-8,(int)lgl.get(i)[1]-8,16,16);
		}
		g.setColor(new Color(255,128,128));
		g.setStroke(new BasicStroke(5));
		for (double[] l:lpl){
			int[] xp=new int[l.length/2];
			int[] yp=new int[l.length/2];
			for (int i=0;i<l.length;i+=2){
				xp[i/2]=(int)l[i];
				yp[i/2]=(int)l[i+1];
			}
			g.drawPolygon(xp,yp,l.length/2);
		}
	}



	private double[] _intersection_line_line(double l1ax,double l1ay,double l1bx,double l1by,double l2ax,double l2ay,double l2bx,double l2by){
		double t=((l1ax-l2ax)*(l2ay-l2by)-(l1ay-l2ay)*(l2ax-l2bx))/((l1ax-l1bx)*(l2ay-l2by)-(l1ay-l1by)*(l2ax-l2bx));
		double u=-((l1ax-l1bx)*(l1ay-l2ay)-(l1ay-l1by)*(l1ax-l2ax))/((l1ax-l1bx)*(l2ay-l2by)-(l1ay-l1by)*(l2ax-l2bx));
		if (-LIGHT_RAY_INTERSECTION_BUFFOR<t&&-LIGHT_RAY_INTERSECTION_BUFFOR<u&&u<1+LIGHT_RAY_INTERSECTION_BUFFOR){
			return new double[]{l1ax+t*(l1bx-l1ax),l1ay+t*(l1by-l1ay)};
		}
		return null;
	}
}