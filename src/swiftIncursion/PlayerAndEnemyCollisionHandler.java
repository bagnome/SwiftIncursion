package swiftIncursion;

import org.newdawn.slick.SlickException;

public class PlayerAndEnemyCollisionHandler implements ICollisionHandler{

	boolean colliding;
	
	public PlayerAndEnemyCollisionHandler(){
		colliding = false;
	}
	
	public void setCollidingFalse(){
		colliding = false;
	}
	
	@Override
	public int getCollider1Type() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getCollider2Type() {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public void performCollision(ICollidableObject collidable1,
			ICollidableObject collidable2) throws SlickException {
		
		Player player;
		Enemy enemy;
		if(collidable1 instanceof Player){
			player = (Player)collidable1;
			enemy = (Enemy)collidable2;
		}else{
			player = (Player)collidable2;
			enemy = (Enemy)collidable1;
		}
		if(!colliding){
			colliding = true;
			GameInfo.getCurrentGameInfo().removeLife();
		}
		
		
	}

}
