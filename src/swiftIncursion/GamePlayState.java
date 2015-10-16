package swiftIncursion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import swiftIncursion.Bullet.Facing;
import swiftIncursion.People.DIRECTION_FACING;

public class GamePlayState extends BasicGameState {

	private static final int PLAYERSPEED = 5;
	private static int BULLETSPEED = 15;
	private int stateId;
	private Level level;
	private Player player;
	private CollidableShapeObject base;
	private CollisionManager cm;
	private CollidableShapeObject winBox;
	//private CollidableShapeObject wall;
	private Enemy dummyEnemy;
	private Bullet dummyBullet;
	private ImagePlatform dummyImage;
	private Box dummyBox;
	private ArrayList<Bullet> bullets;
	private int shotDelay = 60;
	private int shot = shotDelay;
	private DIRECTION_FACING crouchFacing = DIRECTION_FACING.RIGHT;
	private int enemyShot;
    private Bullet dummyBullet2;
    private Platform dummyLeft;
    private Platform dummyRight;
    
	
	public GamePlayState(int id) {
		stateId = id;
		level = new Level();
		bullets = new ArrayList<Bullet>();
		enemyShot = 0;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)throws SlickException {

	    player = new Player("Player", new Rectangle(300, 400, 75, 150), 3, level, 1);
	    base = new Platform("Base", new Rectangle(-2500, container.getHeight() - 10, 5000, 10), 2);
        //wall = new Box("Wall", new Rectangle(container.getWidth()-100, 0, 15, container.getHeight()), 3);
        dummyBox = new Box("", new Rectangle(-1,-2,1,1),3);
        dummyBullet = new Bullet("", new Rectangle(-1, -1, 1, 1), 0, cm, 5, Facing.RIGHT);
        dummyBullet2 = new Bullet("", new Rectangle(-1, -1, 1, 1), 0, cm, 8, Facing.RIGHT);
        dummyImage = new ImagePlatform("", new Image("Data/GrassPlatform.png"),new Rectangle(-0,-50,0,0),6);
		dummyEnemy = new Enemy("", new Rectangle(1,-10,1,1),0, level, 7);
		dummyLeft = new Platform("", new Rectangle(0, -10, 1, 1), 10);
		dummyRight = new Platform("", new Rectangle(0, -10, 1, 1), 9);
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)throws SlickException {
		
	    //render all object in the level
		player.render(g);
		base.render(g);
		//wall.render(g);
		for(CollidableObject c: level.getPlatforms()){
			c.render(g);
		}
		for(Box b: level.getBoxes()){
		    b.render(g);
		}
		for(Enemy e: level.getEnemies()) e.render(g);
		if(!bullets.isEmpty()){
			for(Bullet b: bullets)
				b.render(g);
		}
		g.drawString("Health: "+GameInfo.getCurrentGameInfo().getLives(), 85, 10);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int arg2)throws SlickException {

		//Input
		Input input = container.getInput(); 

		if(container.getInput().isKeyDown(input.KEY_F) && shot >= shotDelay){
			if (player.getDirectionFacing() == DIRECTION_FACING.RIGHT)
			{
					Bullet bul = new Bullet("Bullet", new Circle(player.getPos().x + player.getWidth(), 
			        player.getPos().y + player.getHeight()/4, 
			        5), BULLETSPEED, cm, 5, Facing.RIGHT);
					bullets.add(bul);
					cm.addCollidable(bul);
			}
			else if (player.getDirectionFacing() == DIRECTION_FACING.LEFT)
			{
					Bullet bul = new Bullet("Bullet", new Circle(player.getPos().x, 
			        player.getPos().y + player.getHeight()/4, 
			        5), -BULLETSPEED, cm, 5, Facing.LEFT);
					bullets.add(bul);
					cm.addCollidable(bul);
			}
			shot = 0;
		}
		if (shot < shotDelay)
		{
			shot++;
		}
		int newBulletSpeed = 0;
		int playerBulletSpeed;
		if(container.getInput().isKeyPressed(input.KEY_DOWN) && player.isGrounded()){
			player.setShape(150, 75);
			player.setCrouching(true);
			crouchFacing = player.getDirectionFacing();
			Vector2f adjust = new Vector2f(player.getPos().x, player.getPos().y+75);
			
			if(crouchFacing == DIRECTION_FACING.LEFT){
				adjust.x -= 75;
			}
			player.setPos(adjust);
		}
		else if(!container.getInput().isKeyDown(input.KEY_DOWN) && player.getWidth() == 150)
		{
			player.setShape(75, 150);
			player.setCrouching(false);
			Vector2f adjust = new Vector2f(player.getPos().x, player.getPos().y-75);
			
			if(crouchFacing == DIRECTION_FACING.LEFT){
				adjust.x += 75;
			}
			player.setPos(adjust);
		}
		if(container.getInput().isKeyDown(input.KEY_RIGHT)){
				for(CollidableObject c: level.getPlatforms()){
				c.move(-PLAYERSPEED, 0);
				}
				for(Box b: level.getBoxes()){
	            b.move(-PLAYERSPEED, 0);
				}
				for(Enemy e: level.getEnemies()){
                e.move(-PLAYERSPEED, 0);
				}
				player.setDirectionFacing(DIRECTION_FACING.RIGHT);
				playerBulletSpeed = PLAYERSPEED;
		    
		}else if(container.getInput().isKeyDown(input.KEY_LEFT)){
		    for(CollidableObject c: level.getPlatforms()){
	            c.move(PLAYERSPEED, 0);
	        }
		    for(Box b: level.getBoxes()){
	            b.move(PLAYERSPEED, 0);
	        }
		    for(Enemy e: level.getEnemies()){
		        e.move(PLAYERSPEED, 0);
		    }
		    player.setDirectionFacing(DIRECTION_FACING.LEFT);
		    playerBulletSpeed = -PLAYERSPEED;
		}else{
			playerBulletSpeed = 0;
		}
		
		//Move player
		player.move();
		
		//Move enemies
		for(Enemy e: level.getEnemies()){
		    Random r = new Random();
		    if(e.getPos().x - player.getPos().x > 280 + e.getOffSet()){
		        e.move(-3);
		        e.setDirectionFacing(DIRECTION_FACING.LEFT);
		        
		    }
		    else if(e.getPos().x - player.getPos().x < -130 - e.getOffSet()){
		        e.move(3);
		        e.setDirectionFacing(DIRECTION_FACING.RIGHT);      
		    }
		    
		    if(e.getDirectionFacing() == DIRECTION_FACING.LEFT){
		        if(enemyShot >= shotDelay && r.nextInt(10) == 1){
		            
		            Bullet bul = new Bullet("Bullet", new Circle(e.getPos().x + e.getWidth(), 
	                        e.getPos().y + e.getHeight()/4, 
	                        5), BULLETSPEED, cm, 8, Facing.LEFT);
		            
		            bullets.add(bul);
		            cm.addCollidable(bul);
		            enemyShot = 0;
		        }
		    }else if(e.getDirectionFacing() == DIRECTION_FACING.RIGHT){
		        if(enemyShot >= shotDelay && r.nextInt(10) == 1){
		            
		            Bullet bul = new Bullet("Bullet", new Circle(e.getPos().x + e.getWidth(),
	                        e.getPos().y + e.getHeight()/4, 
	                        5), BULLETSPEED, cm, 8, Facing.RIGHT);
		            
		            bullets.add(bul);
		            cm.addCollidable(bul);
		            enemyShot = 0;
		        }
		    }
		    e.moveY();
		    enemyShot++;
		}
		
		//Enemy shoot
		
		
		
		//move bullet
		if(!bullets.isEmpty()){
			for(int i = 0; i < bullets.size(); i++){
				if (bullets.get(i).getFacing() == Facing.RIGHT)
				{
					newBulletSpeed = BULLETSPEED - playerBulletSpeed;
				}
				else if (bullets.get(i).getFacing() == Facing.LEFT)
				{
					newBulletSpeed = -BULLETSPEED - playerBulletSpeed;
				}
				bullets.get(i).setVelocity(newBulletSpeed);
				bullets.get(i).move();
				if(level.bulletCollision() ){
                    cm.removeCollidable(bullets.get(i));
					bullets.remove(i);
					level.setBulletCollsion(false);
				}
				if(!bullets.get(i).isOnScreen(container)){
				    cm.removeCollidable(bullets.get(i));
				    bullets.remove(i);
				    System.out.println("Bullet removed");
				    }
			}
		}
		
		cm.processCollisions();   //Look for collisions
		
		//If player hits the winBox, exit level, enter new level if one exists.
		if(level.getPlayerHitWinBox()){
		    level.setPlayerHitWinBox(false);
		    GameInfo.getCurrentGameInfo().nextLevel();
		    level.removeGameObjects();
		    game.enterState(1);
		}

	}
	
	public void enter(GameContainer container, StateBasedGame game)throws SlickException{
	    cm = new CollisionManager();
	    if(!GameInfo.getCurrentGameInfo().getPlayerExists()){
	        
	        
	        GameInfo.getCurrentGameInfo().setPlayerExists(true);
	    }
        
	    try
        {
            level.loadLevel(new FileInputStream(new File("Data/level"+ GameInfo.getCurrentGameInfo().getLevelID() +".txt")));
        } catch (FileNotFoundException e)
        {
            System.out.println("No level to load");
            game.enterState(2);
        }
		container.getInput().addKeyListener(player);
		cm.addCollidable(player);
		cm.addCollidable(base);
		cm.addCollidable(dummyBullet);
		cm.addCollidable(dummyBullet2);
		cm.addCollidable(dummyImage);
		cm.addCollidable(dummyBox);
		cm.addCollidable(dummyEnemy);
		cm.addCollidable(dummyLeft);
		cm.addCollidable(dummyRight);
		//cm.addCollidable(wall);
		for(CollidableObject p: level.getPlatforms()){
			cm.addCollidable(p);
		}
		for(Box b: level.getBoxes()){
		    cm.addCollidable(b);
		}
		for(Enemy e: level.getEnemies()){
		    cm.addCollidable(e);
		}
		cm.addHandler(new PlayerAndPlatformCollisionHandler(cm, level, player));
	    cm.addHandler(new PlayerAndImagePlatformCollisionHandler(cm, level, player));
		cm.addHandler(new PlayerAndWinBoxCollisionManager(level));
		cm.addHandler(new EnemyAndBulletCollisionHandler(cm, level, bullets));
		cm.addHandler(new EnemyAndPlatformCollisionHandler(level));
		cm.addHandler(new PlayerAndBulletCollisionHandler(cm, bullets));
		cm.addHandler(new LeftEndPlatformCollisionHandler());
		cm.addHandler(new RightEndPlatformCollisionHandler());
		//cm.addHandler(new WallAndBulletCollisionHandler(cm, level));
	}

	@Override
	public int getID() {
		return stateId;
	}

	

}
