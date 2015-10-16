package swiftIncursion;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Platform extends CollidableShapeObject{
    protected CollidableShapeObject leftEnd;
    protected CollidableShapeObject rightEnd;

	Platform(String name, Shape shape, int collisionType) {
		super(name, shape, collisionType);
		leftEnd = new CollidableShapeObject
		        ("Left End", new Rectangle(this.getPos().x-6, this.getPos().y,6,this.getHeight()), 9);
		rightEnd = new CollidableShapeObject
		        ("Right End", new Rectangle(this.getPos().x+this.getWidth()+6, this.getPos().y,6,this.getHeight()), 9);
		
	}
	
	public CollidableShapeObject getLeftEnd(){
	    return leftEnd;
	}
	
	public CollidableShapeObject getRightEnd(){
        return rightEnd;
    }

}
