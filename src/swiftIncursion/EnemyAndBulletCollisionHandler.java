package swiftIncursion;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class EnemyAndBulletCollisionHandler implements ICollisionHandler{
    
    private Level level;
    private CollisionManager cm;
    private ArrayList<Bullet> bullets;
    
    EnemyAndBulletCollisionHandler(CollisionManager cm, Level level, ArrayList<Bullet> bullets){
        this.level = level;
        this.cm = cm;
        this.bullets = bullets;
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
        
        if(enemy.isCollidingWith(bullet)) {
            cm.removeCollidable(enemy);
            cm.removeCollidable(bullet);
            level.getEnemies().remove(enemy);
            bullets.remove(bullet);
        }
    }

}
