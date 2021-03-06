package swiftIncursion;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class PlayerAndImagePlatformCollisionHandler implements ICollisionHandler
{
private Level level;
    
    public PlayerAndImagePlatformCollisionHandler(CollisionManager manager, Level level, Player player){
        this.level = level;
    }

    @Override
    public int getCollider1Type() {
        return 1;
    }

    @Override
    public int getCollider2Type() {
        return 6;
    }

    @Override
    public void performCollision(ICollidableObject collidable1, ICollidableObject collidable2) throws SlickException {
        
        
        if(!collidable1.isCollidingWith(collidable2)) {
            level.setplayerCollidingWithPlatform(false);
            return;
        }
        
        Player player;
        ImagePlatform base;
        
        if(collidable1 instanceof Player){
            player = (Player)collidable1;
            base = (ImagePlatform)collidable2;
        }else{
            player = (Player)collidable2;
            base = (ImagePlatform)collidable1;
        }
        if(player.isCollidingWith(base)){
            //if(player.getYVel() > 0) player.setYVel(0);
            level.setplayerCollidingWithPlatform(true);
            Vector2f correctY = player.getPos();
            Vector2f platformPos = base.getPos();
            if((correctY.y + player.getHeight()) >= platformPos.y && (correctY.y + player.getHeight()) <= (platformPos.y + base.getHeight())){
                correctY.y = platformPos.y-player.getHeight();
                player.setPos(correctY);
            }
        }
        
        
    }
}
