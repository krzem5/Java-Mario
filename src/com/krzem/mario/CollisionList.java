package com.krzem.mario;



import java.util.ArrayList;
import java.util.List;



public class CollisionList extends Constants{
	private List<Collision> _l;



	public CollisionList(){
		this._l=new ArrayList<Collision>();
	}



	public void clear(){
		this._l.clear();
	}



	public void add(Collision c){
		this._l.add(c);
	}



	public List<Collision> get(Class cl){
		List<Collision> o=new ArrayList<Collision>();
		for (Collision c:this._l){
			if (cl.isAssignableFrom(c.co._val.getClass())){
				o.add(c);
			}
		}
		return o;
	}



	public List<Collision> get(){
		return this._l;
	}
}