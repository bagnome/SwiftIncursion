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
	private ArrayList<Upgrade> Upgrades;
	private boolean playerCollidingWithPlatform;
	private boolean playerCollidingWithLeftEdge;
	private boolean playerCollidingWithRightEdge;
	private boolean hitWinBox;
	private boolean bulletCollision;
	private boolean enemyBulletCollision;
	private Level level;
    private boolean enemyCollidingWithPlatform;
    private Enemy levelBoss;
	private Enemy levelEnemy;
	private String levelEnemytxt;
	private CollisionManager cm;
	private String[] bgs;
	private boolean defeated;
	private Player player;
	
	public Level(CollisionManager cm){
		bgs = new String[3];
		platforms = new ArrayList<CollidableObject>();
		boxes = new ArrayList<Box>();
		enemies = new ArrayList<Enemy>();
		Upgrades = new ArrayList<Upgrade>();
		playerCollidingWithPlatform = false;
		hitWinBox = false;
		bulletCollision = false;
		enemyBulletCollision = false;
		this.level = this;
		this.cm = cm;
		defeated = false;
	}
	public void getPlayer(Player player)
	{
		this.player = player;
	}
	private void addPlatforms(Platform p){
		platforms.add(p);
		platforms.add(p.getLeftEnd());
		platforms.add(p.getRightEnd());
	}
	
	private void addBoxes(Box b){
	    boxes.add(b);
	}
	
	public void addEnemy(Enemy e){
	    enemies.add(e);
	}
	public void addUpgrade(Upgrade u){
		Upgrades.add(u);
	}
	
	private void addImagePlatforms(ImagePlatform i){
	    platforms.add(i);
	}
	
	public void removeGameObjects(){
		for(CollidableObject c: platforms) cm.removeCollidable(c);
		for(Box b: boxes) cm.removeCollidable(b);
		for(Enemy e: enemies) cm.removeCollidable(e);
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
	public ArrayList<Upgrade> getUpgrades(){
		return Upgrades;
	}
	
	public void setplayerCollidingWithLeftEdge(boolean b){
		playerCollidingWithLeftEdge = b;
	}
	
	public boolean getplayerCollidingWithLeftEdge(){
		return playerCollidingWithLeftEdge;
	}
	
	public void setplayerCollidingWithPlatform(boolean b){
		playerCollidingWithPlatform = b;
	}
	
	public boolean getplayerCollidingWithRightEdge(){
		return playerCollidingWithRightEdge;
	}
	public void setplayerCollidingWithRightEdge(boolean b){
		playerCollidingWithRightEdge = b;
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
	
	public Enemy getLevelBoss(){
		return levelBoss;
	}
	
	public void setBossDefeated(boolean set){
		defeated = set;
	}
	
	public boolean isBossDefeated(){
		return defeated;
	}
	
	public Enemy getLevelEnemy() throws SlickException{
		if(levelEnemytxt.equals("Ninja Mage")) return new NinjaMage(level);
		if(levelEnemytxt.equals("Ram")) return new Ram(new Rectangle(900, 400, 75, 150), level);
		if(levelEnemytxt.equals("Fire Man")){
        	return new FireMan(new Rectangle(900,400,75,150), level);
        }
		return new NinjaMage(level);
	}

	public String[] getBackgroundImages(){
		return bgs;
	}
	
	public void loadLevel(InputStream is) throws SlickException{
	    //Level l = new Level();
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    String[] args = readNextValidLine(br);
	    
	     while(args != null){
	        
	        for(String s: args){
	            System.out.print(s + " ");
	        }
	        if(args[0].equals("Background")){
	        	bgs[0] = args[1];
	        	bgs[1] = args[2];
	        	bgs[2] = args[3];
	        }
	        if(args[0].equals("Tank Lizard")) levelBoss = new TankLizard(level);
	        if(args[0].equals("Taylor Swift")) levelBoss = new TaylorSwift(new Rectangle(900,400,75,150),level, player);
	        if(args[0].equals("Ninja Mage")) levelEnemytxt = args[0];
	        if(args[0].equals("Ram")) levelEnemytxt = args[0];
	        if(args[0].equals("Fire Man")){ levelEnemytxt = args[0];
	        }
	        if(args[0].equals("Bunny")) levelBoss = new Bunny(new Rectangle(900, 400, 50, 50),level);
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
	            }/*else if(args[1].equals("ENEMY")){
	                addEnemy(new Enemy(args[2], new Rectangle(
	                        Integer.parseInt(coords[0]), 
                            Integer.parseInt(coords[1]), 
                            Integer.parseInt(coords[2]), 
                            Integer.parseInt(coords[3])),
                            3, level,
                            Integer.parseInt(args[4])));
	            }*/
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
