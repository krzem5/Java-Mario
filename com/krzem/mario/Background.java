package com.krzem.mario;



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



public class Background extends Constants{
	private String[] nm_l=new String[]{"ground"};
	public Level lv;
	public int t;
	public String t_nm;
	private double[] off=new double[2];
	private BufferedImage _img;



	public Background(Level lv,int t){
		this.lv=lv;
		this.t=t;
		this.t_nm=this.nm_l[this.t];
		this._img=new BufferedImage(this.lv.ld.w*80/2+GAME_SIZE.width*80/2,this.lv.ld.h*80/2+GAME_SIZE.height*80/2,BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig=(Graphics2D)this._img.createGraphics();
		for (int i=0;i<this._img.getWidth();i+=2048){
			for (int j=this._img.getHeight();j>=0;j-=2048){
				ig.drawImage(this.get_image((j==this._img.getHeight()?"-ground":"-air")),i,j-2048,null);
			}
		}
		ig.dispose();
	}



	public void update(){
	}



	public void draw(Graphics2D g,Graphics2D og){
		g.drawImage(this._img,-this.lv.cdo[0]/2,-this.lv.cdo[1]/2,null);
	}



	private BufferedImage get_image(String t){
		return IO.get_image(BG_TEXTURE_FILE_PATH+this.t_nm+t+".png");
	}
}