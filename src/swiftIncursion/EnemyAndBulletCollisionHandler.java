package swiftIncursion;

import java.util.ArrayList;

import java.util.Random;
import org.newdawn.slick.geom.*;

import org.newdawn.slick.Image;

import org.newdawn.slick.SlickException;

public class EnemyAndBulletCollisionHandler implements ICollisionHandler{
    
    private Level level;
    private CollisionManager cm;
    private ArrayList<Bullet> bullets;
    private Random random;
    private Player player;
    
    EnemyAndBulletCollisionHandler(CollisionManager cm, Level level, ArrayList<Bullet> bullets, Player player){
        this.level = level;
        this.cm = cm;
        this.bullets = bullets;
        this.random = new Random();
        this.player = player;
    }

    @Override
    public int getCollider1Type()
    {
        
        return 7;
    }

    @Override
    public int getCollider2Type()
    {
        
        return 5; 
    }

    @Override
    public void performCollision(ICollidableObject collidable1,
            ICollidableObject collidable2) throws SlickException
    {
        System.out.println("collision");
        Enemy enemy;
        Bullet bullet;
        
        if(collidable1 instanceof Enemy){
            enemy = (Enemy)collidable1;
            bullet = (Bullet)collidable2;
        }else{
            enemy = (Enemy)collidable2;
            bullet = (Bullet)collidable1;
        }
        
        enemy.subtractHealth();
        cm.removeCollidable(bullet);
        bullets.remove(bullet);
        
        if(enemy.getHealth() <= 0) {
        
        	if (random.nextInt(10) > 6){
        		Upgrade upgrade;
        		if (random.nextInt(10) > 4){
        			upgrade = new Upgrade("Upgrade", new Rectangle(enemy.getPos().x, enemy.getPos().y, 100, 100), 11, 1, new Image("Data/Speed Upgrade.png"), 12);
        		}
        		
        		else{
        			if (player.getShotsFired() < 3)
        			{
        				upgrade = new Upgrade("Upgrade", new Rectangle(enemy.getPos().x, enemy.getPos().y, 100, 100), 11, 2, new Image("Data/Shot Upgrade.png"), 12);    
        			}
        			else{
        				upgrade = new Upgrade("Upgrade", new Rectangle(enemy.getPos().x, enemy.getPos().y, 100, 100), 11, 1, new Image("Data/Speed Upgrade.png"), 12);    
        			}
        		}
        		
            	level.addUpgrade(upgrade);
                cm.addCollidable(upgrade);	
        	}
        	
        	cm.removeCollidable(enemy);
            level.getEnemies().remove(enemy);
            }
            
    }

}
