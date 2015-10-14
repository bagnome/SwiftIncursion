package swiftIncursion;

import org.newdawn.slick.SlickException;

public class EnemyAndBulletCollisionHandler implements ICollisionHandler{
    
    private Level level;
    
    EnemyAndBulletCollisionHandler(Level level){
        this.level = level;
    }

    @Override
    public int getCollider1Type()
    {
        // TODO Auto-generated method stub
        return 7;
    }

    @Override
    public int getCollider2Type()
    {
        // TODO Auto-generated method stub
        return 5; 
    }

    @Override
    public void performCollision(ICollidableObject collidable1,
            ICollidableObject collidable2) throws SlickException
    {
        // TODO Auto-generated method stub
        
    }

}
