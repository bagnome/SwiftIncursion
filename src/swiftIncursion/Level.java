package swiftIncursion;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

public class Level {
	
	private ArrayList<Platform> platforms;
	private boolean playerCollidingWithPlatform;
	private boolean hitWinBox;
	private boolean bulletCollision;
	private Platform platform1;
	private Platform platform2;
	private Platform platform3;
	private Platform platform4;
	private Platform platform5;
	private Platform platform6;
	
	public Level(){
		platforms = new ArrayList<Platform>();
		playerCollidingWithPlatform = false;
		hitWinBox = false;
		bulletCollision = false;
		platform1 = new Platform("Platform 1", new Rectangle(500, 450, 200, 10), 2);
		platform2 = new Platform("Platform 2", new Rectangle(190, 100, 200, 10), 2);
		platform3 = new Platform("Platform 3", new Rectangle(0, 200, 100, 10), 2);
		platform4 = new Platform("Platform 4", new Rectangle(430, 500, 50, 10), 2);
		platform5 = new Platform("Platform 5", new Rectangle(400, 350, 100, 10), 2);
		platform6 = new Platform("Platform 6", new Rectangle(180, 275, 200, 10), 2);
		addPlatforms(platform1);
		addPlatforms(platform2);
		addPlatforms(platform3);
		addPlatforms(platform4);
		addPlatforms(platform5);
		addPlatforms(platform6);
	}
	
	private void addPlatforms(Platform p){
		platforms.add(p);
	}
	
	public ArrayList<Platform> getPlatforms(){
		return platforms;
	}
	
	public void setplayerCollidingWithPlatform(boolean b){
		playerCollidingWithPlatform = b;
	}
	
	public boolean getplayerCollidingWithPlatform(){
		return playerCollidingWithPlatform;
	}
	
	public void setPlayerHitWinBox(boolean hitWinBox){
		this.hitWinBox = hitWinBox;
	}
	
	public boolean getPlayerHitWinBox(){
		return hitWinBox;
	}
	
	public boolean bulletCollision(){
		return bulletCollision;
	}
	
	public void setBulletCollsion(boolean bulletCollision){
		this.bulletCollision = bulletCollision;
	}
	

}
