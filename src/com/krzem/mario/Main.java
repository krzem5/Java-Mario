package com.krzem.mario;



import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.Exception;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;



public class Main extends Constants{
	public static void main(String[] args){
		new Main(args);
	}



	public double FPS=1;
	public Game game;
	public JFrame frame;
	public Canvas canvas;
	private boolean _break=false;



	public Main(String[] args){
		this.init();
		this.frame_init();
		this.run();
	}



	public void init(){
		this.game=new Game(this);
	}



	public void frame_init(){
		Main cls=this;
		this.frame=new JFrame("Light Simulation");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setUndecorated(true);
		this.frame.setResizable(false);
		this.frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				cls.quit();
			}
		});
		SCREEN.setFullScreenWindow(this.frame);
		this.canvas=new Canvas(this);
		this.canvas.setSize(WINDOW_SIZE.width,WINDOW_SIZE.height);
		this.canvas.setPreferredSize(new Dimension(WINDOW_SIZE.width,WINDOW_SIZE.height));
		this.frame.setContentPane(this.canvas);
		this.canvas.requestFocus();
		this.canvas.setCursor(this.canvas.getToolkit().createCustomCursor(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB),new Point(),null));
	}



	public void run(){
		Main cls=this;
		new Thread(new Runnable(){
			@Override
			public void run(){
				while (cls._break==false){
					long s=System.nanoTime();
					try{
						cls.canvas.repaint();
						double f=(System.nanoTime()-s)*1e-9;
						if (f<1/MAX_FPS){
							Thread.sleep((long)((1/MAX_FPS-f)*1e3));
						}
						cls.FPS=Math.round(1/((System.nanoTime()-s)*1e-9));
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run(){
				while (cls._break==false){
					long s=System.nanoTime();
					try{
						cls.update();
						double f=(System.nanoTime()-s)*1e-9;
						if (f<1/MAX_FPS){
							Thread.sleep((long)((1/MAX_FPS-f)*1e3));
						}
						// cls.FPS=1/((System.nanoTime()-s)*1e-9);
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}).start();
	}



	public void update(){
		this.game.update();
		if (this.game.c!=null&&this.game.c.get("ps-button")==1){
			this.quit();
		}
	}



	public void draw(Graphics2D _g){
		BufferedImage cnv=new BufferedImage(SIZE.width,SIZE.height,BufferedImage.TYPE_INT_ARGB);
		BufferedImage ocnv=new BufferedImage(WINDOW_SIZE.width,WINDOW_SIZE.height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g=this.canvas._wrap_g2d((Graphics2D)cnv.createGraphics());
		Graphics2D og=this.canvas._wrap_g2d((Graphics2D)ocnv.createGraphics());
		this.game.draw(g,og);
		g.dispose();
		og.setColor(java.awt.Color.white);
		og.setFont(DEFAULT_FONT.deriveFont(15f));
		og.drawString(Double.toString(this.FPS),1,16);
		og.dispose();
		_g.drawImage(cnv,0,0,WINDOW_SIZE.width,WINDOW_SIZE.height,null);
		_g.drawImage(ocnv,0,0,WINDOW_SIZE.width,WINDOW_SIZE.height,null);
	}


	public void quit(){
		if (this._break==true){
			return;
		}
		this._break=true;
		IO._close();
		this.frame.dispose();
		this.frame.dispatchEvent(new WindowEvent(this.frame,WindowEvent.WINDOW_CLOSING));
	}
}