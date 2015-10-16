package swiftIncursion;

import org.newdawn.slick.SlickException;

public class RightEndPlatformCollisionHandler implements ICollisionHandler
{
    public RightEndPlatformCollisionHandler(){
        
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
        
        if(collidable1 instanceof Player){
            player = (Player)collidable1;
            end = (CollidableShapeObject)collidable2;
        }else{
            player = (Player)collidable2;
            end = (CollidableShapeObject)collidable1;
        }
        
        System.out.println("Colliding with right end.");
    }

}
