package swiftIncursion;

import org.newdawn.slick.SlickException;

public class RightEndPlatformCollisionHandler implements ICollisionHandler
{
	private Level level;

    public RightEndPlatformCollisionHandler(CollisionManager manager, Level level, Player player){
        this.level = level;
    }

    @Override
    public int getCollider1Type()
    {
        return 1;
    }

    @Override
    public int getCollider2Type()
    {
        return 9;
    }

    @Override
    public void performCollision(ICollidableObject collidable1,
            ICollidableObject collidable2) throws SlickException
    {
    	   Player player;
           CollidableShapeObject end;

   		if(!collidable1.isCollidingWith(collidable2)) {
   			level.setplayerCollidingWithRightEdge(false);
   			return;
   		}
           
           if(collidable1 instanceof Player){
               player = (Player)collidable1;
               end = (CollidableShapeObject)collidable2;
           }else{
               player = (Player)collidable2;
               end = (CollidableShapeObject)collidable1;
           }
           if(player.isCollidingWith(end) && player.getPos().y + player.getHeight() > end.getPos().y)
           {
           	level.setplayerCollidingWithRightEdge(true);
           }
    }

}
