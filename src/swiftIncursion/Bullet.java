package swiftIncursion;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Bullet extends CollidableShapeObject{
	private int velocity;
	private CollisionManager cm;
	public enum Facing{
		LEFT, RIGHT
	}
	private Facing facing;
	Bullet(String name, Shape shape, int velocity, CollisionManager cm, int collisionType, Facing facing) {
		super(name, shape, collisionType);
		this.velocity = velocity;
		this.cm = cm;
		this.facing  = facing;
	}
	
	public Facing getFacing(){
		return facing;
	}
	public void setFacing(Facing facing){
		this.facing = facing;
	}
	public void move(){
		shape.setLocation(shape.getLocation().x += velocity, shape.getLocation().y);
	}
	
	public int getVelocity(){
		return velocity;
	}
		
	public Vector2f getPosition(){
		return shape.getLocation();
	}
	
	public void setPosition(Vector2f pos){
		shape.setLocation(pos);
	}
	
	public void setVelocity(int vel){
		velocity = vel;
	}
	
	public boolean isOnScreen(GameContainer container){
	    if(shape.getLocation().x < 0 || shape.getLocation().x > container.getWidth()) return false;
	    return true;
	}

}
