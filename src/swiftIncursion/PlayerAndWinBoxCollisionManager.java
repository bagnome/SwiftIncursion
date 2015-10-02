package swiftIncursion;

import org.newdawn.slick.SlickException;

public class PlayerAndWinBoxCollisionManager implements ICollisionHandler {

	private Level level;

	public PlayerAndWinBoxCollisionManager(Level level) {
		this.level = level;
	}

	@Override
	public int getCollider1Type() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getCollider2Type() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void performCollision(ICollidableObject collidable1,
			ICollidableObject collidable2) throws SlickException {

		if (!collidable1.isCollidingWith(collidable2))
			return;
		
		level.setPlayerHitWinBox(true);
	}

}
