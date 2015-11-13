package swiftIncursion;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class PlayerAndBulletCollisionHandler implements ICollisionHandler
{
    private CollisionManager cm;
    private ArrayList<Bullet> bullets;
    private boolean bulletHit;
    
    public PlayerAndBulletCollisionHandler(CollisionManager cm, ArrayList<Bullet> bullets){
        this.cm = cm;
        this.bullets = bullets;
    }
    @Override
    public int getCollider1Type()
    {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public int getCollider2Type()
    {
        // TODO Auto-generated method stub
        return 8;
    }

    @Override
    public void performCollision(ICollidableObject collidable1,
            ICollidableObject collidable2) throws SlickException
    {
        Player player;
        Bullet bullet;
        
        if (collidable1 instanceof Player){
            player = (Player)collidable1;
            bullet = (Bullet)collidable2;
        }else{
            player = (Player)collidable2;
            bullet = (Bullet)collidable1; 
        }
        SoundAndMusic sm = GameInfo.getCurrentGameInfo().getSounds();
        sm.health();
        sm.hit();
        cm.removeCollidable(bullet);
        bullets.remove(bullet);
        if (!bullet.isBulletHit())
        {
        System.out.println("Bullet collided with player");
        	GameInfo.getCurrentGameInfo().removeLife();
        	bullet.setBulletHit(true);
        }
    }

}
