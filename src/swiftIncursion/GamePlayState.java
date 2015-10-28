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
import org.newdawn.slick.Font;

import swiftIncursion.Bullet.Facing;
import swiftIncursion.People.DIRECTION_FACING;

public class GamePlayState extends BasicGameState {

	private static final int PLAYERSPEED = 5;
	private static int BULLETSPEED = 10;
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
    private final float PLAYER_HIEHGT = 120;
    private final float PLAYER_WIDTH = 25;
    private Image bgStart;
    private Image bgMid;
    private Image bgEnd;
    private int moveBG;
    private int healthNum;
    private Image health;
    private int maxEnemies;
    private int enemiesSpawned;
    private Image[] levelBoss;
    private SoundAndMusic sm;
    private static enum STATE {
    	PLAY, PAUSED, MENU
    };
    private STATE currentState;
    private STATE prevState;
    private Font font;
	
	public GamePlayState(int id) {
		stateId = id;
		level = new Level();
		bullets = new ArrayList<Bullet>();
		enemyShot = 0;
		
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)throws SlickException {

	    player = new Player("Player", new Rectangle(300, 400, PLAYER_WIDTH, PLAYER_HIEHGT), 3, level, 1);
	    base = new Platform("Base", new Rectangle(-2500, container.getHeight() - 10, 5000, 10), 2);
        //wall = new Box("Wall", new Rectangle(container.getWidth()-100, 0, 15, container.getHeight()), 3);
        dummyBox = new Box("", new Rectangle(-1,-2,1,1),3);
        dummyBullet = new Bullet("", new Rectangle(-1, -1, 1, 1), 0, cm, 5, Facing.RIGHT, new Image("Data/fire main.png"), 3);
        dummyBullet2 = new Bullet("", new Rectangle(-1, -1, 1, 1), 0, cm, 8, Facing.RIGHT, new Image("Data/fire main.png"), 3);
        dummyImage = new ImagePlatform("", new Image("Data/GrassPlatform.png"),new Rectangle(-0,-50,0,0),6);
		dummyEnemy = new Enemy("", new Rectangle(1,-10,1,1),0, level, 7, 5);
		dummyLeft = new Platform("", new Rectangle(0, -10, 1, 1), 10);
		dummyRight = new Platform("", new Rectangle(0, -10, 1, 1), 9);
		bgStart = new Image("Data/background 3.png");
		bgMid = new Image("Data/background 1.png");
		bgEnd = new Image("Data/background 2.png");
		health = new Image("data/health.png");
		maxEnemies = 3;
		
		sm = GameInfo.getCurrentGameInfo().getSounds();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)throws SlickException {
		switch(currentState){
		
		case PLAY:
		    bgEnd.draw(moveBG-800, 0);
		    bgStart.draw(moveBG, 0);
		    bgMid.draw(moveBG+800, 0);
		    bgEnd.draw(moveBG+800*2, 0);
		    bgStart.draw(moveBG+800*3, 0);
		    
			player.render(g);
			//base.render(g);
			//wall.render(g);
			for(CollidableObject c: level.getPlatforms()){
				c.render(g);
			}
			
			for(Box b: level.getBoxes()){
			    b.render(g);
			}
			
			if(!level.getEnemies().isEmpty()){
			    for(Enemy e: level.getEnemies()){
			        e.enemyAnimation(0);
			        e.render(g);
			    }
			}
			
			if(!bullets.isEmpty()){
				for(Bullet b: bullets){
				    b.bulletAnimation();
					b.render(g);
				}
			}
			Input input = container.getInput(); 
			
			
			
			
			//Player Animations
			if((container.getInput().isKeyDown(input.KEY_LEFT) || 
	                                container.getInput().isKeyDown(input.KEY_RIGHT)) &&
	                                !container.getInput().isKeyDown(input.KEY_DOWN)){
			    
			    player.runAnimation(1);
			    
			}else if(container.getInput().isKeyDown(input.KEY_DOWN) && 
			        (container.getInput().isKeyDown(input.KEY_LEFT) || 
	                container.getInput().isKeyDown(input.KEY_RIGHT))){
			    
			    player.runAnimation(3);
			    
			}else if(!(container.getInput().isKeyDown(input.KEY_LEFT) || 
	                container.getInput().isKeyDown(input.KEY_RIGHT)) &&
	                container.getInput().isKeyDown(input.KEY_DOWN)){
			    
			    player.runAnimation(4);
			    
			}else player.runAnimation(2);
	        
			//g.drawString("Health: "+GameInfo.getCurrentGameInfo().getLives(), 85, 10);
			if(healthNum == 3 ) {health.draw(); health.draw(80, 0); health.draw(160, 0);} 
			if(healthNum == 2) {health.draw(); health.draw(80,0);}
			if(healthNum == 1) health.draw();
			break;
			
		case PAUSED:
			g.drawString("Paused", container.getWidth()/2-20, container.getHeight()/2);
			break;
			
		case MENU:
			g.drawString("Quit to menu? [Enter]", container.getWidth()/2-50, container.getHeight()/2);
			break;
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int arg2)throws SlickException {

		//Input
		Input input = container.getInput(); 

		switch(currentState){
		
		case PLAY:
			if(container.getInput().isKeyDown(input.KEY_F) && shot >= shotDelay && !player.isCrouching()){
			    sm.bullet();
				if (player.getDirectionFacing() == DIRECTION_FACING.RIGHT)
				{
						Bullet bul = new Bullet("Bullet", new Circle(player.getPos().x + player.getWidth()+30, 
				        player.getPos().y + player.getHeight()*0.4f, 
				        5), BULLETSPEED, cm, 5, Facing.RIGHT, new Image("Data/fire main.png"), 3);
						bullets.add(bul);
						cm.addCollidable(bul);
				}
				else if (player.getDirectionFacing() == DIRECTION_FACING.LEFT)
				{
						Bullet bul = new Bullet("Bullet", new Circle(player.getPos().x-30, 
				        player.getPos().y + player.getHeight()*0.4f, 
				        5), -BULLETSPEED, cm, 5, Facing.LEFT, new Image("Data/fire main.png"), 3);
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
			if(container.getInput().isKeyDown(input.KEY_DOWN) && player.isGrounded() && !player.isCrouching()){
				player.setShape(PLAYER_HIEHGT, PLAYER_WIDTH+20);
				player.setCrouching(true);
				crouchFacing = player.getDirectionFacing();
				Vector2f adjust = new Vector2f(player.getPos().x, player.getPos().y+(PLAYER_HIEHGT-PLAYER_WIDTH));
				
				if(crouchFacing == DIRECTION_FACING.LEFT){
					adjust.x -= PLAYER_WIDTH;
				}
				player.setPos(adjust);
			}
			else if(!container.getInput().isKeyDown(input.KEY_DOWN) && player.getWidth() == PLAYER_HIEHGT)
			{
				player.setShape(PLAYER_WIDTH, PLAYER_HIEHGT);
				player.setCrouching(false);
				Vector2f adjust = new Vector2f(player.getPos().x, player.getPos().y-(PLAYER_HIEHGT-PLAYER_WIDTH)+20);
				
				if(crouchFacing == DIRECTION_FACING.LEFT){
					adjust.x += PLAYER_WIDTH;
				}
				player.setPos(adjust);
			}
			if(container.getInput().isKeyDown(input.KEY_RIGHT) && !level.getplayerCollidingWithLeftEdge()){			
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
					moveBG-=PLAYERSPEED;
			}else if(container.getInput().isKeyDown(input.KEY_LEFT) && !level.getplayerCollidingWithRightEdge()){
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
			    moveBG+=PLAYERSPEED;
			}else{
				playerBulletSpeed = 0;
			}
			
			//Move player
			player.move();
			
			//Spawn Enemies
			if(level.getEnemies().isEmpty() && enemiesSpawned < maxEnemies){
			    Enemy e = level.getLevelEnemy();
			    level.addEnemy(e);
			    cm.addCollidable(e);
			    enemiesSpawned++;
			}else if(level.getEnemies().isEmpty() && enemiesSpawned == maxEnemies){
			    Enemy e = level.getLevelBoss();
			    level.addEnemy(e);
	            cm.addCollidable(e);
			    enemiesSpawned++;
			}
			
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
			        
			    }else{
			        e.move(0);
			    }
			    
			    if(e.getDirectionFacing() == DIRECTION_FACING.LEFT){
			        if(enemyShot >= shotDelay){
			            if(r.nextInt(2) == 1){
			                //Bullet bul = new Bullet("Bullet", new Circle(e.getPos().x + e.getWidth(), 
			                //        e.getPos().y + e.getHeight()/4, 
			                //        5), BULLETSPEED, cm, 8, Facing.LEFT, new Image("Data/fire main.png"), 3);
			                e.shoot(bullets, BULLETSPEED-5, cm);
			                e.shootSound();
			                //bullets.add(bul);
			                //cm.addCollidable(bul);
			            }
			            enemyShot = 0;
			        }
			    }else if(e.getDirectionFacing() == DIRECTION_FACING.RIGHT){
			        if(enemyShot >= shotDelay){
			            if(r.nextInt(2) == 1){
			                /*Bullet bul = new Bullet("Bullet", new Circle(e.getPos().x + e.getWidth(),
			                    e.getPos().y + e.getHeight()/4, 
		                        5), BULLETSPEED, cm, 8, Facing.RIGHT, new Image("Data/fire main.png"), 3);
			            
			                bullets.add(bul);
			                cm.addCollidable(bul);*/
			                e.shootSound();
			                e.shoot(bullets, BULLETSPEED-5, cm);
			            }
			            enemyShot = 0;
			        }
			    }
			    e.moveY();
			    enemyShot++;
			}
			
			
			
			
			
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
			    sm.stopMusic();
			    level.setPlayerHitWinBox(false);
			    GameInfo.getCurrentGameInfo().nextLevel();
			    level.removeGameObjects();
			    game.enterState(1);
			}
			
			healthNum = GameInfo.getCurrentGameInfo().getLives();
			if(healthNum == 0)sm.dead();
			if(container.getInput().isKeyPressed(input.KEY_PAUSE)) currentState = STATE.PAUSED;
			if(container.getInput().isKeyPressed(input.KEY_ESCAPE)) currentState = STATE.MENU;
			break;
			
		case PAUSED:
			sm.pauseMusic();
			if(container.getInput().isKeyPressed(input.KEY_PAUSE)) {
				sm.resumeMusic();
				currentState = STATE.PLAY;
			}
			break;
			
		case MENU:
			sm.pauseMusic();
			if(container.getInput().isKeyPressed(input.KEY_ENTER))game.enterState(0);
			if(container.getInput().isKeyPressed(input.KEY_ESCAPE)) {
				sm.resumeMusic();
				currentState = STATE.PLAY;
			}
			break;
		}

	}
	
	public void enter(GameContainer container, StateBasedGame game)throws SlickException{
		currentState = STATE.PLAY;
	    sm.level1Song();
	    moveBG = 0;
	    enemiesSpawned = 0;
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
		cm.addHandler(new LeftEndPlatformCollisionHandler(cm, level, player));
		cm.addHandler(new RightEndPlatformCollisionHandler(cm, level, player));
		//cm.addHandler(new WallAndBulletCollisionHandler(cm, level));
	}

	@Override
	public int getID() {
		return stateId;
	}

	

}
