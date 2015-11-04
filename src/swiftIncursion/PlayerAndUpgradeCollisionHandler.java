package swiftIncursion;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class PlayerAndUpgradeCollisionHandler implements ICollisionHandler{
    private CollisionManager cm;
    private Level level;
    private ArrayList<Upgrade> upgrades;
    private SoundAndMusic sm;
    public PlayerAndUpgradeCollisionHandler(CollisionManager cm, Level level, SoundAndMusic sm){
        this.cm = cm;
        this.level = level;
        upgrades = level.getUpgrades();
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
        return 11;
    }

    @Override
    public void performCollision(ICollidableObject collidable1,
            ICollidableObject collidable2) throws SlickException
    {
    	Player player;
        Upgrade upgrade;
        
        if (collidable1 instanceof Player){
            player = (Player)collidable1;
            upgrade = (Upgrade)collidable2;
        }else{
            player = (Player)collidable2;
            upgrade = (Upgrade)collidable1; 
        }
        sm.pop();
        cm.removeCollidable(upgrade);
        level.getUpgrades().remove(upgrade);
        if (upgrade.getUpgradeType() == 1){
            player.upgradeShotSpeed();
        }     
        if (upgrade.getUpgradeType() == 2){
        	player.upgradeShots();
        }
    }

}
