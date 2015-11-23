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
	// private CollidableShapeObject wall;
	private Enemy dummyEnemy;
	private Bullet dummyBullet;
	private ImagePlatform dummyImage;
	private Box dummyBox;
	private ArrayList<Bullet> bullets;
	private DIRECTION_FACING crouchFacing = DIRECTION_FACING.RIGHT;
	private Bullet dummyBullet2;
	private PlayerAndPlatformCollisionHandler pp;
	private PlayerAndImagePlatformCollisionHandler pi;
	private PlayerAndWinBoxCollisionManager pw;
	private EnemyAndBulletCollisionHandler eb;
	private EnemyAndPlatformCollisionHandler ep;
	private PlayerAndBulletCollisionHandler pb;
	private LeftEndPlatformCollisionHandler lp;
	private RightEndPlatformCollisionHandler rp;
	private PlayerAndUpgradeCollisionHandler pu;
	private PlayerAndBossTrigger pt;
	private Platform dummyLeft;
	private Platform dummyRight;
	private Upgrade dummyUpgrade;
	private BossTrigger bossTrigger;
	private final float PLAYER_HIEHGT = 120;
	private final float PLAYER_WIDTH = 25;
	private Image bgStart;
	private Image bgMid;
	private Image bgEnd;
	private int moveBG;
	private Timer timer;
	private Image health;
	private int maxEnemies;
	private int enemiesSpawned;
	private Image[] levelBoss;
	private SoundAndMusic sm;
	private PlayerAndEnemyCollisionHandler pe;
	protected CollidableShapeObject leftBound;
    protected CollidableShapeObject rightBound;
    private boolean trigger = false;
    private Random random = new Random();
    private int spawnTimer = 0;
	private static enum STATE {
		PLAY, PAUSED, MENU, EXIT
	};

	private STATE currentState;
	private int gameState;

	public GamePlayState(int id) {
		cm = new CollisionManager();
		stateId = id;
		level = new Level(cm);
		bullets = new ArrayList<Bullet>();
		timer = new Timer();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {


		sm = GameInfo.getCurrentGameInfo().getSounds();
		player = new Player("Player", new Rectangle(300, 400, PLAYER_WIDTH,
				PLAYER_HIEHGT), 3, level, 1);
		level.getPlayer(player);
		base = new Platform("Base", new Rectangle(-2500,
				container.getHeight() - 10, 5000, 10), 2);
		// wall = new Box("Wall", new Rectangle(container.getWidth()-100, 0, 15,
		// container.getHeight()), 3);
		pe = new PlayerAndEnemyCollisionHandler();
		dummyBox = new Box("", new Rectangle(-1, -2, 1, 1), 3);
		dummyBullet = new Bullet("", new Rectangle(-1, -1, 1, 1), 0, cm, 5,
				Facing.RIGHT, new Image("Data/fire main.png"), 3);
		dummyBullet2 = new Bullet("", new Rectangle(-1, -1, 1, 1), 0, cm, 8,
				Facing.RIGHT, new Image("Data/fire main.png"), 3);
		dummyImage = new ImagePlatform("", new Image("Data/GrassPlatform.png"),
				new Rectangle(-0, -50, 0, 0), 6);
		dummyEnemy = new Enemy("", new Rectangle(1, -10, 1, 1), 0, level, 7, 5);
		dummyLeft = new Platform("", new Rectangle(0, -10, 1, 1), 10);
		dummyRight = new Platform("", new Rectangle(0, -10, 1, 1), 9);
		dummyUpgrade = new Upgrade("", new Rectangle(0, -10, 1, 1), 11, 1,
				new Image("Data/Speed Upgrade.png"), 2);
		
		health = new Image("data/health.png");
		maxEnemies = 3;
		pp = new PlayerAndPlatformCollisionHandler(cm, level, player);
		pi = new PlayerAndImagePlatformCollisionHandler(cm, level,
				player);
		pw = new PlayerAndWinBoxCollisionManager(level);
		eb = new EnemyAndBulletCollisionHandler(cm, level, bullets,
				player);
		ep = new EnemyAndPlatformCollisionHandler(level);
		pb = new PlayerAndBulletCollisionHandler(cm, bullets);
		lp = new LeftEndPlatformCollisionHandler(cm, level, player);
		rp = new RightEndPlatformCollisionHandler(cm, level, player);
		pu = new PlayerAndUpgradeCollisionHandler(cm, level, sm);
		pt = new PlayerAndBossTrigger(cm,level,sm);
		container.setShowFPS(false);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		switch (currentState) {

		case PLAY:
			
			bgStart.draw(moveBG, 0);
			bgMid.draw(moveBG + 800, 0);
			bgEnd.draw(moveBG + 800 * 2, 0);
			
			g.drawString(timer.getTime(), 600, 25);
			//player.render(g);
			// base.render(g);
			// wall.render(g);
			//bossTrigger.render(g);
			for (CollidableObject c : level.getPlatforms()) {
				//c.render(g);
				if (c instanceof Platform){
					((Platform)c).platAnimation();
				}
			}

		//	for (Box b : level.getBoxes()) {
			//	b.render(g);
			//}

			if (!level.getEnemies().isEmpty()) {
				for (Enemy e : level.getEnemies()) {
					e.enemyAnimation(0);
					e.render(g);
				}
			}

			if (!bullets.isEmpty()) {
				for (Bullet b : bullets) {
					b.bulletAnimation();
					//b.render(g);
				}
			}
			if (!level.getUpgrades().isEmpty()) {
				for (Upgrade u : level.getUpgrades()) {
					u.upgradeAnimation();
		//			u.render(g);
				}
			}
			Input input = container.getInput();

			// Player Animations
			if ((container.getInput().isKeyDown(input.KEY_LEFT) || container
					.getInput().isKeyDown(input.KEY_RIGHT))
					&& !container.getInput().isKeyDown(input.KEY_DOWN)) {

				player.runAnimation(1);

			} else if (container.getInput().isKeyDown(input.KEY_DOWN)
					&& (container.getInput().isKeyDown(input.KEY_LEFT) || container
							.getInput().isKeyDown(input.KEY_RIGHT))) {

				player.runAnimation(3);

			} else if (!(container.getInput().isKeyDown(input.KEY_LEFT) || container
					.getInput().isKeyDown(input.KEY_RIGHT))
					&& container.getInput().isKeyDown(input.KEY_DOWN)) {

				player.runAnimation(4);

			} else
				player.runAnimation(2);

			// g.drawString("Health: "+GameInfo.getCurrentGameInfo().getLives(),
			// 85, 10);
			if (GameInfo.getCurrentGameInfo().getLives() == 3) {
				health.draw();
				health.draw(80, 0);
				health.draw(160, 0);
			}
			if (GameInfo.getCurrentGameInfo().getLives() == 2) {
				health.draw();
				health.draw(80, 0);
			}
			if (GameInfo.getCurrentGameInfo().getLives() == 1)
				health.draw();
			//g.draw(leftBound.getCollisionShape());
			//g.draw(rightBound.getCollisionShape());
			break;

		case PAUSED:
			g.drawString("Paused", container.getWidth() / 2 - 20,
					container.getHeight() / 2);
			break;

		case MENU:
			g.drawString("Quit to menu? [Enter]",
					container.getWidth() / 2 - 50, container.getHeight() / 2);
			break;
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		// Input
		Input input = container.getInput();

		switch (currentState) {

		case PLAY:
			timer.tick(delta);
			if (container.getInput().isKeyPressed(input.KEY_Y))
			{
				GameInfo.getCurrentGameInfo().resetLives();
				player.upgradeShots();
				player.upgradeShots();
				player.upgradeShotSpeed();
				player.upgradeShotSpeed();
				player.upgradeShotSpeed();
			}
			if (container.getInput().isKeyDown(input.KEY_F)
					&& player.getShot() >= player.getShotDelay()
					&& !player.isCrouching()) {
				sm.bullet();
				if (player.getDirectionFacing() == DIRECTION_FACING.RIGHT) {
					Bullet bul = new Bullet("Bullet", new Circle(
							player.getPos().x + player.getWidth() + 30,
							player.getPos().y + player.getHeight() * 0.4f, 5),
							BULLETSPEED, cm, 5, Facing.RIGHT, new Image(
									"Data/shotgun.png"), 2);
					bullets.add(bul);
					cm.addCollidable(bul);
					if (player.getShotsFired() > 1) {
						Bullet bull = new Bullet("Bullet", new Circle(
								player.getPos().x + player.getWidth() + 30,
								player.getPos().y + player.getHeight() * 0.5f,
								5), BULLETSPEED, cm, 5, Facing.RIGHT,
								new Image("Data/shotgun.png"), 2);
						bullets.add(bull);
						cm.addCollidable(bull);
					}
					if (player.getShotsFired() > 2) {

						Bullet bulle = new Bullet("Bullet", new Circle(
								player.getPos().x + player.getWidth() + 30,
								player.getPos().y + player.getHeight() * 0.3f,
								5), BULLETSPEED, cm, 5, Facing.RIGHT,
								new Image("Data/shotgun.png"), 2);
						bullets.add(bulle);
						cm.addCollidable(bulle);
					}
				} else if (player.getDirectionFacing() == DIRECTION_FACING.LEFT) {
					Bullet bul = new Bullet("Bullet", new Circle(
							player.getPos().x - 30, player.getPos().y
									+ player.getHeight() * 0.4f, 5),
							-BULLETSPEED, cm, 5, Facing.LEFT, new Image(
									"Data/shotgun.png"), 2);
					bullets.add(bul);
					cm.addCollidable(bul);
					if (player.getShotsFired() > 1) {
						Bullet bull = new Bullet("Bullet", new Circle(
								player.getPos().x - 30, player.getPos().y
										+ player.getHeight() * 0.5f, 5),
								-BULLETSPEED, cm, 5, Facing.LEFT, new Image(
										"Data/shotgun.png"), 2);
						bullets.add(bull);
						cm.addCollidable(bull);
					}
					if (player.getShotsFired() > 2) {

						Bullet bulle = new Bullet("Bullet", new Circle(
								player.getPos().x - 30, player.getPos().y
										+ player.getHeight() * 0.3f, 5),
								-BULLETSPEED, cm, 5, Facing.LEFT, new Image(
										"Data/shotgun.png"), 2);
						bullets.add(bulle);
						cm.addCollidable(bulle);
					}
				}
				player.setShot(0);
			}
			if (player.getShot() < player.getShotDelay()) {
				player.incrementShot();
			}
			int newBulletSpeed = 0;
			int playerBulletSpeed;
			if (container.getInput().isKeyDown(input.KEY_DOWN)
					&& player.isGrounded() && !player.isCrouching()) {
				player.setShape(PLAYER_HIEHGT, PLAYER_WIDTH + 20);
				player.setCrouching(true);
				crouchFacing = player.getDirectionFacing();
				Vector2f adjust = new Vector2f(player.getPos().x,
						player.getPos().y + (PLAYER_HIEHGT - PLAYER_WIDTH));

				if (crouchFacing == DIRECTION_FACING.LEFT) {
					adjust.x -= PLAYER_WIDTH;
				}
				player.setPos(adjust);
			} else if (!container.getInput().isKeyDown(input.KEY_DOWN)
					&& player.getWidth() == PLAYER_HIEHGT) {
				player.setShape(PLAYER_WIDTH, PLAYER_HIEHGT);
				player.setCrouching(false);
				Vector2f adjust = new Vector2f(player.getPos().x,
						player.getPos().y - (PLAYER_HIEHGT - PLAYER_WIDTH) + 20);

				if (crouchFacing == DIRECTION_FACING.LEFT) {
					adjust.x += PLAYER_WIDTH;
				}
				player.setPos(adjust);
			}
			if (container.getInput().isKeyDown(input.KEY_RIGHT)
					&& !level.getplayerCollidingWithLeftEdge()) {
				for (CollidableObject c : level.getPlatforms()) {
					c.move(-PLAYERSPEED, 0);
				}
				for (Box b : level.getBoxes()) {
					b.move(-PLAYERSPEED, 0);
				}
				for (Enemy e : level.getEnemies()) {
					e.move(-PLAYERSPEED, 0);
				}
				for (Upgrade u : level.getUpgrades()) {
					u.move(-PLAYERSPEED, 0);
				}
				bossTrigger.move(-PLAYERSPEED, 0);
				player.setDirectionFacing(DIRECTION_FACING.RIGHT);
				playerBulletSpeed = PLAYERSPEED;
				moveBG -= PLAYERSPEED;
				leftBound.move(-PLAYERSPEED, 0);
				rightBound.move(-PLAYERSPEED, 0);
			} else if (container.getInput().isKeyDown(input.KEY_LEFT)
					&& !level.getplayerCollidingWithRightEdge()) {
				for (CollidableObject c : level.getPlatforms()) {
					c.move(PLAYERSPEED, 0);
				}
				for (Box b : level.getBoxes()) {
					b.move(PLAYERSPEED, 0);
				}
				for (Enemy e : level.getEnemies()) {
					e.move(PLAYERSPEED, 0);
				}
				for (Upgrade u : level.getUpgrades()) {
					u.move(PLAYERSPEED, 0);
				}
				bossTrigger.move(PLAYERSPEED, 0);
				player.setDirectionFacing(DIRECTION_FACING.LEFT);
				playerBulletSpeed = -PLAYERSPEED;
				moveBG += PLAYERSPEED;
				leftBound.move(PLAYERSPEED, 0);
				rightBound.move(PLAYERSPEED, 0);
			} else {
				playerBulletSpeed = 0;
			}

			// Move player
			player.move();

			// Spawn Enemies
			if (level.getEnemies().isEmpty() && enemiesSpawned < maxEnemies) {
				Enemy e = level.getLevelEnemy();
				level.addEnemy(e);
				cm.addCollidable(e);
				enemiesSpawned++;
				spawnTimer = 0;

			}else if (enemiesSpawned < maxEnemies){
				spawnTimer += random.nextInt(5);
				if (spawnTimer > 750){
				Enemy e = level.getLevelEnemy();
				level.addEnemy(e);
				cm.addCollidable(e);
				enemiesSpawned++;
				spawnTimer = 0;
				}	
			}
			// Move enemies
			for (Enemy e : level.getEnemies()) {
				Random r = new Random();
				if (e instanceof Ram) {
					
					if (e.getPos().x - player.getPos().x > 280 + e.getOffSet() &! ((Ram)e).getRamming()) 
					{
						e.move(-6);
						e.setDirectionFacing(DIRECTION_FACING.LEFT);
					} 
					else if (e.getPos().x - player.getPos().x < -130 - e.getOffSet() &! ((Ram)e).getRamming())
					{
						e.move(6);
						e.setDirectionFacing(DIRECTION_FACING.RIGHT);
					} 
					else if (((Ram)e).getRamming()) 
					{
						if(e.getDirectionFacing() == DIRECTION_FACING.LEFT) e.move(-6);
						if(e.getDirectionFacing() == DIRECTION_FACING.RIGHT) e.move(6);
						if(Math.abs(e.getPos().x - player.getPos().x) > 400)((Ram)e).setRamming(false);
					} 
					else {
						
						// e.move(0);
						if (e.getPos().x - player.getPos().x <= 280 + e.getOffSet()
								&& e.getPos().x - player.getPos().x >= -130 - e.getOffSet())
							((Ram)e).setRamming(true);
					}

				
					

				}
				else if (e instanceof Bunny){
					if (e.getPos().x - player.getPos().x > 280 + e.getOffSet() &! ((Bunny)e).getRamming()) 
					{
						e.move(-6);
						e.setDirectionFacing(DIRECTION_FACING.LEFT);
					} 
					else if (e.getPos().x - player.getPos().x < -130 - e.getOffSet() &! ((Bunny)e).getRamming())
					{
						e.move(6);
						e.setDirectionFacing(DIRECTION_FACING.RIGHT);
					} 
					else if (((Bunny)e).getRamming()) 
					{
						if(e.getDirectionFacing() == DIRECTION_FACING.LEFT) e.move(-6);
						if(e.getDirectionFacing() == DIRECTION_FACING.RIGHT) e.move(6);
						if(Math.abs(e.getPos().x - player.getPos().x) > 400)((Bunny)e).setRamming(false);
					} 
					else {
						
						// e.move(0);
						if (e.getPos().x - player.getPos().x <= 280 + e.getOffSet()
								&& e.getPos().x - player.getPos().x >= -130 - e.getOffSet())
							((Bunny)e).setRamming(true);
					}

				}
				else if (e.getPos().x - player.getPos().x > 5 /*280 +e.getOffSet()*/ &! (e instanceof Ram) &! (e instanceof Bunny)) {
					if (e instanceof TankLizard && ((TankLizard) e).getShooting() < 30){
						e.move(0);
					} else if (e instanceof TaylorSwift && ((TaylorSwift) e).getShooting() < 10)
					{
						e.move(0);
					}
					else {
						 e.move(-1);
						}
					e.setDirectionFacing(DIRECTION_FACING.LEFT);

				} else if (e.getPos().x - player.getPos().x < -50 /*-130 - e.getOffSet()*/ &! (e instanceof Ram) &! (e instanceof Bunny)) {
					if (e instanceof TankLizard && ((TankLizard) e).getShooting() < 30){
						e.move(0);
					} else if (e instanceof TaylorSwift && ((TaylorSwift) e).getShooting() < 10)
					{
						e.move(0);
					}
					else{
						e.move(1);
					}
					e.setDirectionFacing(DIRECTION_FACING.RIGHT);

				} else {
					e.move(0);
					
				}

				if (e instanceof TankLizard)
				{
					((TankLizard) e).incrementShooting();

				}
				if (e instanceof TaylorSwift)
				{
					((TaylorSwift) e).incrementShooting();
				}
				if (e.getDirectionFacing() == DIRECTION_FACING.LEFT) {
					if (e.getShot() >= e.getShotDelay()) {
						if (r.nextInt(2) == 1) {
							// Bullet bul = new Bullet("Bullet", new
							// Circle(e.getPos().x + e.getWidth(),
							// e.getPos().y + e.getHeight()/4,
							// 5), BULLETSPEED, cm, 8, Facing.LEFT, new
							// Image("Data/fire main.png"), 3);
							e.shoot(bullets, BULLETSPEED - 5, cm);
							e.shootSound();
							// bullets.add(bul);
							// cm.addCollidable(bul);
						}
						e.setShot(0);
					}
				} else if (e.getDirectionFacing() == DIRECTION_FACING.RIGHT) {
					if (e.getShot() >= e.getShotDelay()) {
						if (r.nextInt(2) == 1) {
							/*
							 * Bullet bul = new Bullet("Bullet", new
							 * Circle(e.getPos().x + e.getWidth(), e.getPos().y
							 * + e.getHeight()/4, 5), BULLETSPEED, cm, 8,
							 * Facing.RIGHT, new Image("Data/fire main.png"),
							 * 3);
							 * 
							 * bullets.add(bul); cm.addCollidable(bul);
							 */
							e.shootSound();
							e.shoot(bullets, BULLETSPEED - 5, cm);
						}
						e.setShot(0);
					}
				}
				e.moveY();
				if(!player.isCollidingWith(e)) pe.setCollidingFalse();
				e.incrementShot();
			}

			// move bullet
			if (!bullets.isEmpty()) {
				for (int i = 0; i < bullets.size(); i++) {
					if (bullets.get(i).getFacing() == Facing.RIGHT) {
						newBulletSpeed = BULLETSPEED - playerBulletSpeed;
					} else if (bullets.get(i).getFacing() == Facing.LEFT) {
						newBulletSpeed = -BULLETSPEED - playerBulletSpeed;
					}
					bullets.get(i).setVelocity(newBulletSpeed);
					bullets.get(i).move();
					if (level.bulletCollision()) {
						cm.removeCollidable(bullets.get(i));
						bullets.remove(i);
						level.setBulletCollsion(false);
					}
					if (!bullets.get(i).isOnScreen(container)) {
						cm.removeCollidable(bullets.get(i));
						bullets.remove(i);
						System.out.println("Bullet removed");
					}
				}
			}

			cm.processCollisions(); // Look for collisions
			
			// If player hits the winBox, exit level, enter new level if one
			// exists.
			if (level.getPlayerHitWinBox() && level.isBossDefeated() && level.getEnemies().isEmpty()) {
				// sm.stopMusic();
				level.setPlayerHitWinBox(false);
				GameInfo.getCurrentGameInfo().nextLevel();
				// level.removeGameObjects();
				gameState = 1;
				// game.enterState(1);
				currentState = STATE.EXIT;
			}
			else{
				level.setPlayerHitWinBox(false);
			}

			if (GameInfo.getCurrentGameInfo().getLives() == 0)
				{
				sm.dead();
				gameState = 3;
				timer.resetTimer();
				currentState = STATE.EXIT;
				}
			if (container.getInput().isKeyPressed(input.KEY_PAUSE))
				currentState = STATE.PAUSED;
			if (container.getInput().isKeyPressed(input.KEY_ESCAPE))
				currentState = STATE.MENU;
			break;

		case PAUSED:
			sm.pauseMusic();
			if (container.getInput().isKeyPressed(input.KEY_PAUSE)) {
				sm.resumeMusic();
				currentState = STATE.PLAY;
				//timer.gameIsPaused(delta);
			}
			break;

		case MENU:
			sm.pauseMusic();
			if (container.getInput().isKeyPressed(input.KEY_ENTER)) {
				gameState = 0;// game.enterState(0);
				player.resetUpgrades();
				timer.resetTimer();
				currentState = STATE.EXIT;
			}
			if (container.getInput().isKeyPressed(input.KEY_ESCAPE)) {
				sm.resumeMusic();
				currentState = STATE.PLAY;
				//timer.gameIsPaused(delta);
			}
			break;

		case EXIT:
			cm.removeCollidable(rightBound);
			cm.addCollidable(leftBound);
			sm.stopMusic();
			level.removeGameObjects();
			game.enterState(gameState);
			break;
		}

	}

	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		rightBound = new CollidableShapeObject
		        ("Right Bound", new Rectangle(2400, -1000, 5, container.getHeight() + 1000), 10);
		leftBound = new CollidableShapeObject
		        ("Left Bound", new Rectangle(0, -1000, 5, container.getHeight() + 1000), 9);

		bossTrigger = new BossTrigger("Boss Trigger", new Rectangle(1500,10,1,1000),12);
		bossTrigger.setTrigger(false);
		cm.addCollidable(rightBound);
		cm.addCollidable(leftBound);
		currentState = STATE.PLAY;
		sm.levelSong(GameInfo.getCurrentGameInfo().getLevelID());
		moveBG = 0;
		enemiesSpawned = 0;
		level.setBossDefeated(false);
		if (!GameInfo.getCurrentGameInfo().getPlayerExists()) {

			GameInfo.getCurrentGameInfo().setPlayerExists(true, player);
		}

		try {
			level.loadLevel(new FileInputStream(new File("Data/level"
					+ GameInfo.getCurrentGameInfo().getLevelID() + ".txt")));
		} catch (FileNotFoundException e) {
			System.out.println("No level to load");
			GameInfo.getCurrentGameInfo().recordTime(timer.getTime());
			timer.resetTimer();
			game.enterState(2);
		}
		bgStart = new Image(level.getBackgroundImages()[0]);
		bgMid = new Image(level.getBackgroundImages()[1]);
		bgEnd = new Image(level.getBackgroundImages()[2]);
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
		cm.addCollidable(dummyUpgrade);
		cm.addCollidable(bossTrigger);

		
		// cm.addCollidable(wall);
		for (CollidableObject p : level.getPlatforms()) {
			cm.addCollidable(p);
		}
		for (Box b : level.getBoxes()) {
			cm.addCollidable(b);
		}
		for (Enemy e : level.getEnemies()) {
			cm.addCollidable(e);
		}
		cm.addHandler(pp);
		cm.addHandler(pi);
		cm.addHandler(pw);
		cm.addHandler(eb);
		cm.addHandler(ep);
		cm.addHandler(pb);
		cm.addHandler(lp);
		cm.addHandler(rp);
		// cm.addHandler(new WallAndBulletCollisionHandler(cm, level));
		cm.addHandler(pu);
		cm.addHandler(pe);
		cm.addHandler(pt);
	}

	@Override
	public int getID() {
		return stateId;
	}

}
