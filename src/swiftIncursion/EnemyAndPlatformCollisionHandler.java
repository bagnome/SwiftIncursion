package swiftIncursion;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class EnemyAndPlatformCollisionHandler implements ICollisionHandler
{
    private Level level;
    
    EnemyAndPlatformCollisionHandler(Level level){
        this.level = level;
    }

    @Override
    public int getCollider1Type()
    {
        // TODO Auto-generated method stub
        return 2;
    }

    @Override
    public int getCollider2Type()
    {
        // TODO Auto-generated method stub
        return 7;
    }

    @Override
    public void performCollision(ICollidableObject collidable1,
            ICollidableObject collidable2) throws SlickException
    {
        if(!collidable1.isCollidingWith(collidable2)) {
            
            return;
        }
        
        Enemy enemy;
        Platform base;
        
        if(collidable1 instanceof Player){
            enemy = (Enemy)collidable1;
            base = (Platform)collidable2;
        }else{
            enemy = (Enemy)collidable2;
            base = (Platform)collidable1;
        }
        if(enemy.isCollidingWith(base)){
            //if(player.getYVel() > 0) player.setYVel(0);
            enemy.setCollidingWithplatform(true);
            Vector2f correctY = enemy.getPos();
            Vector2f platformPos = base.getPos();
            if((correctY.y + enemy.getHeight()) >= platformPos.y && (correctY.y + enemy.getHeight()) <= (platformPos.y + base.getHeight())){
                correctY.y = platformPos.y-enemy.getHeight();
                enemy.setPos(correctY);
            }
        }
        
    }
}
