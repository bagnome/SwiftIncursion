package swiftIncursion;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class BossTrigger extends CollidableShapeObject{
	private Rectangle rect;
	private boolean trigger;
    
	BossTrigger(String name, Shape shape,  int collisionType) {
		super(name, shape, collisionType);
		this.rect = (Rectangle) shape;
	}	
	
	public boolean isTrigger(){
		return trigger;
	}
	public void setTrigger(boolean trigger)
	{
		this.trigger = trigger;
	}
}
