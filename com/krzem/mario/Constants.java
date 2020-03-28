package com.krzem.mario;



import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.util.Arrays;
import java.util.List;



public class Constants{
	public static final int DISPLAY_ID=0;
	public static final GraphicsDevice SCREEN=GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[DISPLAY_ID];
	public static final Rectangle WINDOW_SIZE=SCREEN.getDefaultConfiguration().getBounds();
	public static final Rectangle SIZE=new Rectangle(0,0,1920,1080);
	public static final Rectangle GAME_SIZE=new Rectangle(0,0,24,14);
	public static final Rectangle TILE_SIZE=new Rectangle(0,0,80,80);
	public static final Rectangle PLAYER_SCREEN_CENTER=new Rectangle(0,0,12,8);
	public static final double MAX_FPS=60;

	public static final Font DEFAULT_FONT=Constants._load_custom_font("./com/krzem/mario/assets/fonts/default.ttf");
	public static final String MENU_CONFIG_FILE_PATH="./com/krzem/mario/assets/layout/menu.xml";
	public static final String LOG_FILE_PATH="./com/krzem/mario/logs/";
	public static final String CONTOUR_FILE_BASE_PATH="./com/krzem/mario/assets/contours/";
	public static final String IMAGE_FILE_BASE_PATH="./com/krzem/mario/assets/images/";
	public static final String LEVEL_FILE_PATH="./com/krzem/mario/assets/layout/";
	public static final String ENTITY_CONTOUR_FILE_PATH="entity/";
	public static final String BLOCK_TEXTURE_FILE_PATH="block/";
	public static final String ENTITY_TEXTURE_FILE_PATH="entity/";
	public static final String BG_TEXTURE_FILE_PATH="background/";
	public static final String MENU_ASSETS_FILE_PATH="gui/";
	public static final String MENU_TEXTURE_FILE_PATH="texture/";
	public static final String CURSOR_IMAGE_FILE_PATH="cursor.png";

	public static final Color BG_COLOR=new Color(0,0,0);

	public static final double LIGHT_RAY_ANGLE_OFFSET=1e-4;
	public static final double LIGHT_RAY_INTERSECTION_BUFFOR=1e-7;

	public static final double MENU_MAX_CURSOR_SPEED=5;
	public static final double MENU_CURSOR_LERP_PROC=0.3;
	public static final int MENU_CURSOR_LIST_LEN=4;
	public static final List<String> MENU_EXCLUDE_FUNCTION_NAMES=Arrays.asList("draw","call");

	public static final double CAM_SCROLL_SPEED=3;
	public static final double CAM_FAST_SCROLL_SPEED=4;
	public static final double CAM_SUPER_FAST_SCROLL_SPEED=8;
	public static final double CAM_FAST_SCROLL_SPEED_MAX=6;
	public static final double CAM_SCROLL_PLAYER_MIN_VEL=1e-2;

	public static final double PLAYER_WALK_SPEED=3;
	public static final double PLAYER_RUN_SPEED=6;
	public static final double PLAYER_WALK_ANIMATION_ADD=12;
	public static final double PLAYER_RUN_ANIMATION_ADD=21;
	public static final double PLAYER_MAX_X_VEL=8;
	public static final double PLAYER_MAX_Y_VEL=8;
	public static final double PLAYER_AIR_MOVE_MULT=0.1;
	public static final double PLAYER_GROUD_FRICTION_X=2.4;
	public static final double PLAYER_AIR_FRICTION_X=59.4;
	public static final double PLAYER_GRAVITY=0.3;
	public static final double SMALL_JUMP_HEIGHT_GRAVITY=8.75;
	public static final double HIGH_JUMP_HEIGHT_GRAVITY=10.5;
	public static final double PLAYER_DEATH_JUMP=7.75;
	public static final double PLAYER_DEATH_GRAVITY=0.25;
	public static final double PLAYER_DEATH_END_BUFFOR=2;



	private static Font _load_custom_font(String fp){
		try{
			IO.dump_log(String.format("Loading Font: %s",fp));
			Font f=Font.createFont(Font.TRUETYPE_FONT,new File(fp));
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
			return f;
		}
		catch (Exception e){
			IO.dump_error(e);
		}
		return null;
	}
}