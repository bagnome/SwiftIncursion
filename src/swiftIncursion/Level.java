package swiftIncursion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;

public class Level {
	
	private ArrayList<CollidableObject> platforms;
	private ArrayList<Box> boxes;
	private ArrayList<Enemy> enemies;
	private boolean playerCollidingWithPlatform;
	private boolean hitWinBox;
	private boolean bulletCollision;
	private boolean enemyBulletCollision;
	private Level level;
	
	public Level(){
		platforms = new ArrayList<CollidableObject>();
		boxes = new ArrayList<Box>();
		enemies = new ArrayList<Enemy>();
		playerCollidingWithPlatform = false;
		hitWinBox = false;
		bulletCollision = false;
		enemyBulletCollision = false;
		this.level = this;
	}
	
	private void addPlatforms(Platform p){
		platforms.add(p);
	}
	
	private void addBoxes(Box b){
	    boxes.add(b);
	}
	
	private void addEnemy(Enemy e){
	    enemies.add(e);
	}
	
	private void addImagePlatforms(ImagePlatform i){
	    platforms.add(i);
	}
	
	public void removeGameObjects(){
	    platforms.clear();
	    boxes.clear();
	    enemies.clear();
	}
	
	public ArrayList<CollidableObject> getPlatforms(){
		return platforms;
	}
	
	public ArrayList<Box> getBoxes(){
	    return boxes;
	}
	
	public ArrayList<Enemy> getEnemies(){
	    return enemies;
	}
	
	public void setplayerCollidingWithPlatform(boolean b){
		playerCollidingWithPlatform = b;
	}
	
	public void setBulletCollisionWithEnemy(boolean b){
	    enemyBulletCollision = b;
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
	
	public boolean getBulletCollisionWithEnemy(){
	    return enemyBulletCollision;
	}
	
	public void setBulletCollsion(boolean bulletCollision){
		this.bulletCollision = bulletCollision;
	}
	
	public void loadLevel(InputStream is) throws SlickException{
	    //Level l = new Level();
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    String[] args = readNextValidLine(br);
	     while(args != null){
	        
	        for(String s: args){
	            System.out.print(s + " ");
	        }
	        if(args[0].equals("SHAPE")){
	            String[] coords = args[3].split(",");
	            if(args[1].equals("PLATFORM")){
	                addPlatforms(new Platform(args[2], new Rectangle(
	                        Integer.parseInt(coords[0]), 
	                        Integer.parseInt(coords[1]), 
	                        Integer.parseInt(coords[2]), 
	                        Integer.parseInt(coords[3])), 
	                        Integer.parseInt(args[4])));
	            }else if(args[1].equals("BOX")){
	                addBoxes(new Box(args[2], new Rectangle(
                            Integer.parseInt(coords[0]), 
                            Integer.parseInt(coords[1]), 
                            Integer.parseInt(coords[2]), 
                            Integer.parseInt(coords[3])), 
                            Integer.parseInt(args[4])));
	            }else if(args[1].equals("ENEMY")){
	                addEnemy(new Enemy(args[2], new Rectangle(
	                        Integer.parseInt(coords[0]), 
                            Integer.parseInt(coords[1]), 
                            Integer.parseInt(coords[2]), 
                            Integer.parseInt(coords[3])),
                            0, level,
                            Integer.parseInt(args[4])));
	            }
	        }else if(args[0].equals("IMAGE")){
	            String[] coords = args[4].split(",");
	            if(args[1].equals("IMAGEPLATFORM")){
	                addImagePlatforms(new ImagePlatform(args[2], new Image(args[3]),new Rectangle(
	                        Integer.parseInt(coords[0]),
	                        Integer.parseInt(coords[1]),
	                        Integer.parseInt(coords[2]),
	                        Integer.parseInt(coords[3])),
	                        Integer.parseInt(args[5])));
	            }
	        }
	        System.out.println();
	        args = readNextValidLine(br);
	    }
	}
	
	/**
	 * Borrowed from https://github.com/plastygrove/SlickOut/blob/master/src/com/slickout/LevelImpl.java
	 * @param br
	 * @return
	 * @throws SlickException
	 */
	private String[] readNextValidLine(BufferedReader br) throws SlickException {
        boolean read = false;
        String[] args = null;

        while (!read) {
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new SlickException("Could not read level", e);
            }
            if(line == null) return null;
            if (!(line.startsWith("#") || line.isEmpty())) {
                read = true;
                args = line.split("\\|");
            }
        }
        return args;
    }

}
