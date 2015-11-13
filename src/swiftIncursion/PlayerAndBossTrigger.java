package swiftIncursion;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class PlayerAndBossTrigger implements ICollisionHandler{
	private CollisionManager cm;
    private Level level;
    private SoundAndMusic sm;
    public PlayerAndBossTrigger(CollisionManager cm, Level level, SoundAndMusic sm){
        this.cm = cm;
        this.level = level;
        this.sm = sm;
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
        return 12;
    }

    @Override
    public void performCollision(ICollidableObject collidable1,
            ICollidableObject collidable2) throws SlickException
    {
    	Player player;
        BossTrigger bossTrigger;
        
        if (collidable1 instanceof Player){
            player = (Player)collidable1;
            bossTrigger = (BossTrigger)collidable2;
        }else{
            player = (Player)collidable2;
            bossTrigger = (BossTrigger)collidable1; 
        }
        cm.removeCollidable(bossTrigger);
        if (!bossTrigger.isTrigger())
        	{
        	System.out.println("Boss Trigger activated");
        	bossTrigger.setTrigger(true);
			Enemy e = level.getLevelBoss();
			level.addEnemy(e);
			cm.addCollidable(e);
        	}
        
    }

}
