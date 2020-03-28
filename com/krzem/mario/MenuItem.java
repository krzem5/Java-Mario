package com.krzem.mario;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



public abstract class MenuItem extends Constants{
	public abstract void reset();



	public abstract void update();



	public abstract void draw(Graphics2D g,Graphics2D og);



	public BufferedImage _box(String tx,int w,int h,Color c){
		tx=MENU_ASSETS_FILE_PATH+MENU_TEXTURE_FILE_PATH+tx+".png";
		BufferedImage bi=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		Graphics2D bg=(Graphics2D)bi.createGraphics();
		int sz=IO.get_image(tx).getWidth()/3;
		bg.drawImage(IO.get_image(tx),0,0,sz,sz,0,0,sz,sz,null);
		bg.drawImage(IO.get_image(tx),w-sz,0,w,sz,sz*2,0,sz*3,sz,null);
		bg.drawImage(IO.get_image(tx),0,h-sz,sz,h,0,sz*2,sz,sz*3,null);
		bg.drawImage(IO.get_image(tx),w-sz,h-sz,w,h,sz*2,sz*2,sz*3,sz*3,null);
		bg.drawImage(IO.get_image(tx),sz,0,w-sz,sz,sz,0,sz*2,sz,null);
		bg.drawImage(IO.get_image(tx),sz,h-sz,w-sz,h,sz,sz*2,sz*2,sz*3,null);
		bg.drawImage(IO.get_image(tx),0,sz,sz,h-sz,0,sz,sz,sz*2,null);
		bg.drawImage(IO.get_image(tx),w-sz,sz,w,h-sz,sz*2,sz,sz*3,sz*2,null);
		bg.drawImage(IO.get_image(tx),sz,sz,w-sz,h-sz,sz,sz,sz*2,sz*2,null);
		bg.dispose();
		int r=c.getRed();
		int g=c.getGreen();
		int b=c.getBlue();
		Color v;
		for (int x=0;x<bi.getWidth();x++){
			for (int y=0;y<bi.getHeight();y++){
				v=new Color(bi.getRGB(x,y),true);
				bi.setRGB(x,y,new Color((r*(v.getRed()))/255,(g*(v.getRed()))/255,(b*(v.getRed()))/255,v.getAlpha()).getRGB());
			}
		}
		return bi;
	}
}