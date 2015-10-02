package swiftIncursion;

import org.newdawn.slick.SlickException;

public class WallAndBulletCollisionHandler implements ICollisionHandler{
	
	private CollisionManager cm;
	private Level level;
	
	public WallAndBulletCollisionHandler(CollisionManager cm, Level level){
		
		this.cm = cm;
		this.level = level;
	}
	
	@Override
	public int getCollider1Type() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getCollider2Type() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public void performCollision(ICollidableObject collidable1, ICollidableObject collidable2) throws SlickException {
		
		if (!collidable1.isCollidingWith(collidable2))
			return;
		
		Bullet bullet;
		Box wall;
		
		if(collidable1 instanceof Bullet){
			bullet = (Bullet)collidable1;
			wall = (Box)collidable2;
		}else{
			bullet = (Bullet)collidable2;
			wall = (Box)collidable1;
		}
		
		System.out.println("Bullet Collided");
		level.setBulletCollsion(true);
	}

}
