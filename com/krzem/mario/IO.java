package com.krzem.mario;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Exception;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class IO extends Constants{
	private static String _ID=new File(LOG_FILE_PATH+"/"+Long.toString(System.nanoTime())+".log").getAbsolutePath();
	private static PrintWriter _wr=IO._writer();
	private static Map<String,BufferedImage> _il=new HashMap<String,BufferedImage>();
	private static Map<String,double[][][]> _cl=new HashMap<String,double[][][]>();
	private static Map<String,double[][]> _pl=new HashMap<String,double[][]>();
	private static MessageDigest md=null;
	public static boolean _err=false;



	public static void _close(){
		if (IO._wr==null){
			return;
		}
		IO.dump_log("Saving Log to file: "+IO._ID);
		IO._wr.close();
	}



	public static void load_menu_config(Menu mn){
		try{
			IO.dump_log(String.format("Loading Menu Config File: %s",MENU_CONFIG_FILE_PATH));
			mn.il=new HashMap<String,List<MenuItem>>();
			mn.til=new HashMap<String,Integer>();
			mn.hl=new HashMap<String,String>();
			Document doc=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(MENU_CONFIG_FILE_PATH);
			doc.getDocumentElement().normalize();
			Element root=doc.getDocumentElement();
			for (Element me:IO._xml_child(root,"menu")){
				mn.il.put(me.getAttribute("name"),new ArrayList<MenuItem>());
				int ti=0;
				for (Element ie:IO._children(me)){
					switch (ie.getTagName()){
						case "button":
							mn.il.get(me.getAttribute("name")).add(new MenuButton(mn.cls,mn.g,mn,Integer.parseInt(ie.getAttribute("pos-x")),Integer.parseInt(ie.getAttribute("pos-y")),Integer.parseInt(ie.getAttribute("width")),Integer.parseInt(ie.getAttribute("height")),ie.getAttribute("text"),ie.getAttribute("texture"),ie.getAttribute("func"),ie.getAttribute("color"),Integer.parseInt(ie.getAttribute("growth")),ti));
							ti++;
							break;
						case "scrollbox":
							mn.il.get(me.getAttribute("name")).add(new MenuScrollbox(mn.cls,mn.g,mn,Integer.parseInt(ie.getAttribute("pos-x")),Integer.parseInt(ie.getAttribute("pos-y")),Integer.parseInt(ie.getAttribute("width")),Integer.parseInt(ie.getAttribute("height")),ie.getAttribute("texture"),ie.getAttribute("color"),ie.getAttribute("func"),ti));
							ti++;
							break;
						case "image":
							mn.il.get(me.getAttribute("name")).add(new MenuImage(mn.cls,mn.g,mn,ie.getAttribute("src"),Integer.parseInt(ie.getAttribute("pos-x")),Integer.parseInt(ie.getAttribute("pos-y")),Boolean.parseBoolean(ie.getAttribute("loop"))));
							break;
					}
				}
				mn.til.put(me.getAttribute("name"),ti);
				mn.hl.put(me.getAttribute("name"),(me.getAttribute("back-func").length()==0?null:me.getAttribute("back-func")));
			}
		}
		catch (Exception e){
			IO.dump_error(e);
		}
	}



	public static BufferedImage get_image(String fp){
		try{
			if (IO._il.get(fp)==null){
				IO.dump_log(String.format("Loading Image: %s (%s)",IMAGE_FILE_BASE_PATH+fp,fp));
				IO._il.put(fp,ImageIO.read(new File(IMAGE_FILE_BASE_PATH+fp)));
			}
			return IO._il.get(fp);
		}
		catch (Exception e){
			IO.dump_error(e);
		}
		return null;
	}



	public static double[][][] get_contour(String fp,double x,double y){
		try{
			if (IO._cl.get(fp)==null){
				IO.dump_log(String.format("Loading Contour: %s (%s)",CONTOUR_FILE_BASE_PATH+fp,fp));
				String dt=new String(Files.readAllBytes(Paths.get(CONTOUR_FILE_BASE_PATH+fp))).split("\\|")[0];
				double[][][] c=new double[dt.length()-dt.replace(";","").length()+1][][];
				int i=0;
				for (String p:dt.split(";")){
					c[i]=new double[p.length()-p.replace(":","").length()+1][2];
					int j=0;
					for (String pt:p.split(":")){
						c[i][j]=new double[]{Double.parseDouble(pt.split(",")[0]),Double.parseDouble(pt.split(",")[1])};
						j++;
					}
					i++;
				}
				IO._cl.put(fp,c);
			}
			double[][][] o=IO._cl.get(fp);
			double[][][] c=new double[o.length][][];
			for (int i=0;i<o.length;i++){
				c[i]=new double[o[i].length][2];
				for (int j=0;j<o[i].length;j++){
					c[i][j]=new double[]{o[i][j][0]+x,o[i][j][1]+y};
				}
			}
			return c;
		}
		catch (Exception e){
			IO.dump_error(e);
		}
		return null;
	}



	public static double[][] get_polygon(String fp,double x,double y){
		try{
			if (IO._pl.get(fp)==null){
				IO.dump_log(String.format("Loading Polygon: %s (%s)",CONTOUR_FILE_BASE_PATH+fp,fp));
				String dt=new String(Files.readAllBytes(Paths.get(CONTOUR_FILE_BASE_PATH+fp))).split("\\|")[1];
				double[][] p=new double[dt.length()-dt.replace(":","").length()+1][];
				int i=0;
				for (String pt:dt.split(":")){
					p[i]=new double[]{Double.parseDouble(pt.split(",")[0]),Double.parseDouble(pt.split(",")[1])};
					i++;
				}
				IO._pl.put(fp,p);
			}
			double[][] o=IO._pl.get(fp);
			double[][] p=new double[o.length][];
			for (int i=0;i<o.length;i++){
				p[i]=new double[]{o[i][0]+x,o[i][1]+y};
			}
			return p;
		}
		catch (Exception e){
			IO.dump_error(e);
		}
		return null;
	}



	public static void load_level(Level lv,String fp){
		try{
			if (IO.md==null){
				IO.md=MessageDigest.getInstance("MD5");
			}
			IO.dump_log(String.format("Loading Level File: %s",LEVEL_FILE_PATH+fp+".ld"));
			byte[] _fbl=Files.readAllBytes(Paths.get(LEVEL_FILE_PATH+fp+".ld"));
			byte[] _h=new byte[16];
			byte[] dt=new byte[_fbl.length-16];
			System.arraycopy(_fbl,_fbl.length-16,_h,0,16);
			System.arraycopy(_fbl,0,dt,0,dt.length);
			IO.md.update(dt);
			lv.ld.hs=new java.math.BigInteger(1,_h).toString(16);
			if (!new java.math.BigInteger(1,IO.md.digest()).toString(16).equals(lv.ld.hs)){
				IO.dump_error_ln("WRONG HASH!");
				return;
			}
			while (lv.ld.hs.length()<32){
				lv.ld.hs="0"+lv.ld.hs;
			}
			lv.ld.cl=(dt[0]>>7==1?true:false);
			lv.ld.v=IO._get_bits(dt[0],4,3);
			int sl=IO._get_bits(dt[0],0,4);
			byte[] sb=new byte[sl];
			System.arraycopy(dt,1,sb,0,sb.length);
			lv.ld.nm=new String(sb);
			lv.ld.cr_d=new Date(IO._get_long(dt,1+sl));
			lv.ld.lp_d=new Date(IO._get_long(dt,9+sl));
			new Thread(new Runnable(){
				@Override
				public void run(){
					lv.ld.bg_t=dt[17+sl]&0xff;
					lv.ld.w=((dt[18+sl]&0xff)<<8)|(dt[19+sl]&0xff);
					lv.ld.h=dt[20+sl]&0xff;
					lv.ld.sx=dt[21+sl]>>4;
					lv.ld.ex=IO._get_bits(dt[21+sl],0,4);
					lv.ld.sy=dt[22+sl]&0xff;
					lv.ld.ey=dt[23+sl]&0xff;
					lv.ld.ec=IO._get_int(dt,24+sl);
					lv.ld.el=new LevelEntityData[lv.ld.ec];
					int off=28+sl;
					for (int i=0;i<lv.ld.ec;i++){
						lv.ld.el[i]=new LevelEntityData(IO._get_int(dt,off),((dt[off+4]&0xff)<<8)|(dt[off+5]&0xff),dt[off+6]&0xff,IO._get_int(dt,off+7),null);
						lv.ld.el[i].d=new byte[lv.ld.el[i].dl];
						System.arraycopy(dt,off+11,lv.ld.el[i].d,0,lv.ld.el[i].dl);
						off+=11+lv.ld.el[i].dl;
					}
					lv.ld.bc=IO._get_int(dt,off);
					lv.ld.bl=new ArrayList[lv.ld.w][lv.ld.h];
					off+=4;
					LevelBlockData bd;
					for (int i=0;i<lv.ld.bc;i++){
						bd=new LevelBlockData(IO._get_int(dt,off),((dt[off+4]&0xff)<<8)|(dt[off+5]&0xff),dt[off+6]&0xff,IO._get_int(dt,off+7),null);
						bd.d=new byte[bd.dl];
						System.arraycopy(dt,off+11,bd.d,0,bd.dl);
						off+=11+bd.dl;
						if (lv.ld.bl[bd.x][bd.y]==null){
							lv.ld.bl[bd.x][bd.y]=new ArrayList<LevelBlockData>();
						}
						lv.ld.bl[bd.x][bd.y].add(bd);
					}
					lv.ld._done();
				}
			}).start();
		}
		catch (Exception e){
			IO.dump_error(e);
		}
	}



	public static void dump_log(String s){
		try{
			if (IO._wr!=null){
				IO._wr.println(s);
				IO._wr.flush();
			}
			System.out.println(s);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}



	public static void dump_error(Exception e){
		IO._err=true;
		try{
			if (IO._wr!=null){
				e.printStackTrace(IO._wr);
				IO._wr.flush();
			}
			e.printStackTrace(System.err);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}



	public static void dump_error_ln(String ln){
		IO._err=true;
		try{
			if (IO._wr!=null){
				IO._wr.println(ln);
				IO._wr.flush();
			}
			System.err.println(ln);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}



	private static ArrayList<Element> _xml_child(Element p,String tn){
		ArrayList<Element> o=new ArrayList<Element>();
		NodeList cl=p.getChildNodes();
		for (int j=0;j<cl.getLength();j++){
			if (cl.item(j).getNodeType()!=Node.ELEMENT_NODE){
				continue;
			}
			Element e=(Element)cl.item(j);
			if (e.getTagName().equals(tn)){
				o.add(e);
			}
		}
		return o;
	}



	private static ArrayList<Element> _children(Element p){
		ArrayList<Element> o=new ArrayList<Element>();
		NodeList cl=p.getChildNodes();
		for (int j=0;j<cl.getLength();j++){
			if (cl.item(j).getNodeType()!=Node.ELEMENT_NODE){
				continue;
			}
			o.add((Element)cl.item(j));
		}
		return o;
	}



	private static PrintWriter _writer(){
		try{
			System.out.println("Log File Path: "+IO._ID);
			return new PrintWriter(IO._ID,"utf-8");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}



	private static int _get_bits(int v,int a,int b){
		return (((1<<b)-1)&(v>>a));
	}



	private static int _get_int(byte[] dt,int off){
		return ((dt[off]&0xff)<<24)|((dt[off+1]&0xff)<<16)|((dt[off+2]&0xff)<<8)|(dt[off+3]&0xff);
	}



	private static long _get_long(byte[] dt,int off){
		return (((((dt[off]&0xff)<<24)|((dt[off+1]&0xff)<<16)|((dt[off+2]&0xff)<<8)|(dt[off+3]&0xff))&0xffffffffL)<<32)|((((dt[off+4]&0xff)<<24)|((dt[off+5]&0xff)<<16)|((dt[off+6]&0xff)<<8)|(dt[off+7]&0xff))&0xffffffffL);
	}
}