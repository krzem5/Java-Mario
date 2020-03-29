package com.krzem.mario;






public class CollisionObject extends Constants{
	public CollisionEngine ce;
	public double[][][] s;
	public double[][] aabb;
	public double[] t_aabb;
	public double[][][] p;
	public double[][] pl;
	public int[][] cd;
	public int len;
	public Object _val;
	private Block _b;
	private Entity _e;



	public CollisionObject(CollisionEngine ce,Object o,double[][][] s,double[][] pl,int[][] cd){
		this.ce=ce;
		this._val=o;
		if (Block.class.isAssignableFrom(o.getClass())){
			this._b=(Block)o;
		}
		else{
			this._e=(Entity)o;
		}
		this.update(s,pl,cd);
	}



	public void update(double[][][] s,double[][] pl,int[][] cd){
		this.pl=pl;
		this.s=s;
		this.len=this.s.length;
		this.aabb=new double[this.len][4];
		this.t_aabb=this.ce._aabb(this.pl);
		this.p=new double[this.len][][];
		for (int i=0;i<this.len;i++){
			this.aabb[i]=this.ce._aabb(this.s[i]);
			this.p[i]=this.ce._normals(this.s[i]);
		}
		this.cd=cd;
	}



	public Collision coll_o(double x,double y){
		return new Collision(this,x,y);
	}



	public Block get_block(){
		return this._b;
	}



	public Entity get_entity(){
		return this._e;
	}
}