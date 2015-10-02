package swiftIncursion;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class CollidableShapeObject implements ICollidableObject{

	private String name;
	protected Shape shape;
	private int collisionType;
	protected int xVel;
	protected int yVel;
	private static enum DIRECTION {
		LEFT, RIGHT, UP, DOWN
	};
	
	CollidableShapeObject(String name, Shape shape, int collisionType){
		
		this.name = name;
		this.shape = shape;
		this.collisionType = collisionType;
		xVel = 0;
		yVel = 0;
	}
	
	public void setPos(Vector2f pos){
		
		shape.setLocation(pos);
	}
	
	public void moveShape(){
		Vector2f pos = shape.getLocation();
		float y = pos.y;
		float x = pos.x;
		y+=yVel;
		x+=xVel;
		shape.setLocation(x, y);
	}
	
	public void setYVel(int y){
		yVel = y;
	}
	
	public void setXVel(int x){
		xVel = x;
	}
	
	public int getYVel(){
		return yVel;
	}
	
	public int getXVel(){
		return xVel;
	}
	
	public Vector2f getPos(){
		return shape.getLocation();
	}
	
	public float getHeight(){
		return shape.getHeight();
	}
	
	public float getWidth(){
		return shape.getWidth();
	}
	
	public String getName(){
		return name;
	}
	
	public void render(Graphics g){
		Vector2f pos = shape.getLocation();
		g.draw(shape);
		g.drawString(name, pos.x, pos.y-20);
	}
	
	@Override
	public Shape getNormalCollisionShape() {
		return shape;
	}

	@Override
	public Shape getCollisionShape() {
		return shape;
	}

	@Override
	public int getCollisionType() {
		return collisionType;
	}

	@Override
	public boolean isCollidingWith(ICollidableObject collidable) {
		// TODO Auto-generated method stub
		return this.getCollisionShape().intersects(collidable.getCollisionShape());
	}

}
