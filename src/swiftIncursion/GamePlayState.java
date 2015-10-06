package swiftIncursion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GamePlayState extends BasicGameState {

	private Level level;
	private int stateId;
	private Player player;
	private CollidableShapeObject base;
	private CollisionManager cm;
	private CollidableShapeObject winBox;
	private CollidableShapeObject wall;
	private Bullet dummyBullet;
	private ArrayList<Bullet> bullets;
	
	
	public GamePlayState(int id) {
		stateId = id;
		level = new Level();
		bullets = new ArrayList<Bullet>();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)throws SlickException {
		
		
		player = new Player("Player", new Rectangle(100, 100, 25, 25), 3, level, 1);
		base = new Platform("Base", new Rectangle(0, container.getHeight() - 10, container.getWidth() + 1, 10), 2);
		wall = new Box("Wall", new Rectangle(container.getWidth()-100, 0, 15, container.getHeight()), 3);
		dummyBullet = new Bullet("", new Rectangle(-1, -1, 1, 1), 0, cm, 5);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)throws SlickException {
		
		player.render(g);
		base.render(g);
		wall.render(g);
		for(Platform p: level.getPlatforms()){
			p.render(g);
		}
		for(Box b: level.getBoxes()){
		    b.render(g);
		}
		if(!bullets.isEmpty()){
			for(Bullet b: bullets)
				b.render(g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int arg2)throws SlickException {
		
		player.move();
		Input input = container.getInput();
		if(container.getInput().isKeyPressed(input.KEY_F)){
			Bullet bul = new Bullet("Bullet", new Circle(player.getPos().x, player.getPos().y, 5), 5, cm, 5);
			bullets.add(bul);
			cm.addCollidable(bul);
		}
		if(!bullets.isEmpty()){
			for(int i = 0; i < bullets.size(); i++){
				bullets.get(i).move();
				if(level.bulletCollision() ){
                    cm.removeCollidable(bullets.get(i));
					bullets.remove(i);
					level.setBulletCollsion(false);
				}
			}
		}
		cm.processCollisions();
		if(level.getPlayerHitWinBox()) game.enterState(2);
	}
	
	public void enter(GameContainer container, StateBasedGame game)throws SlickException{
	    try
        {
            level.loadLevel(new FileInputStream(new File("Data/level1.txt")));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
		container.getInput().addKeyListener(player);
		cm = new CollisionManager();
		cm.addCollidable(player);
		cm.addCollidable(base);
		cm.addCollidable(dummyBullet);
		cm.addCollidable(wall);
		for(Platform p: level.getPlatforms()){
			cm.addCollidable(p);
		}
		for(Box b: level.getBoxes()){
		    cm.addCollidable(b);
		}
		cm.addHandler(new PlayerAndPlatformCollisionHandler(cm, level, player));
		cm.addHandler(new PlayerAndWinBoxCollisionManager(level));
		cm.addHandler(new WallAndBulletCollisionHandler(cm, level));
	}

	@Override
	public int getID() {
		return stateId;
	}

	

}
