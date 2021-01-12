package com.krzem.mario;



import java.lang.Math;



public class Collision extends Constants{
	public CollisionObject co;
	public double x;
	public double y;
	public double d;



	public Collision(CollisionObject co,double x,double y){
		this.co=co;
		this.x=x;
		this.y=y;
		this.d=Math.atan2(y,x);
	}



	public boolean is_g(){
		return (this.d>-Math.PI*0.75&&this.d<-Math.PI*0.25);
	}
}