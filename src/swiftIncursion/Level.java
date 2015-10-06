package swiftIncursion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Level {
	
	private ArrayList<Platform> platforms;
	private ArrayList<Box> boxes;
	private boolean playerCollidingWithPlatform;
	private boolean hitWinBox;
	private boolean bulletCollision;
	
	public Level(){
		platforms = new ArrayList<Platform>();
		boxes = new ArrayList<Box>();
		playerCollidingWithPlatform = false;
		hitWinBox = false;
		bulletCollision = false;
	}
	
	private void addPlatforms(Platform p){
		platforms.add(p);
	}
	
	private void addBoxes(Box b){
	    boxes.add(b);
	}
	
	public ArrayList<Platform> getPlatforms(){
		return platforms;
	}
	
	public ArrayList<Box> getBoxes(){
	    return boxes;
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
