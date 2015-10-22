package swiftIncursion;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class PlayerAndPlatformCollisionHandler implements ICollisionHandler{
	
	private Level level;
	
	public PlayerAndPlatformCollisionHandler(CollisionManager manager, Level level, Player player){
		this.level = level;
	}

	@Override
	public int getCollider1Type() {
		return 1;
	}

	@Override
	public int getCollider2Type() {
		return 2;
	}

	@Override
	public void performCollision(ICollidableObject collidable1, ICollidableObject collidable2) throws SlickException {
		
		
		if(!collidable1.isCollidingWith(collidable2)) {
			level.setplayerCollidingWithPlatform(false);
			return;
		}
		
		Player player;
		Platform base;
		
		if(collidable1 instanceof Player){
			player = (Player)collidable1;
			base = (Platform)collidable2;
		}else{
			player = (Player)collidable2;
			base = (Platform)collidable1;
		}
		if(player.isCollidingWith(base)){
			//if(player.getYVel() > 0) player.setYVel(0);
			level.setplayerCollidingWithPlatform(true);
			Vector2f correctY = player.getPos();
			Vector2f platformPos = base.getPos();
			if (correctY.y + (player.getHeight()/2) > platformPos.y)
			{
				player.setPos(new Vector2f(correctY.x, platformPos.y + base.getHeight() + 0.1f));
				player.setGrounded(false);
				//System.out.println("Set on bottom");
			}
			if (correctY.y + (player.getHeight()/2) < platformPos.y)
			{
				player.setPos(new Vector2f(correctY.x, platformPos.y - player.getHeight()));
				player.setGrounded(true);
				//System.out.println("Set on top");
			}
		}
		
		
	}

}
