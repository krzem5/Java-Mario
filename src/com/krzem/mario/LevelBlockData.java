package com.krzem.mario;



public class LevelBlockData extends Constants{
	public int id;
	public int x;
	public int y;
	public int dl;
	public byte[] d;



	public LevelBlockData(int id,int x,int y,int dl,byte[] d){
		this.id=id;
		this.x=x;
		this.y=y;
		this.dl=dl;
		this.d=d;
	}
}
