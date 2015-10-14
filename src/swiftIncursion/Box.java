package swiftIncursion;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Box extends CollidableShapeObject{

	Box(String name, Shape shape, int collisionType) {
		super(name, shape, collisionType);
		// TODO Auto-generated constructor stub
	}
	
	public void render(Graphics g){
        Vector2f pos = shape.getLocation();
        g.draw(shape);
        g.drawString(name, pos.x, pos.y-20);
    }

}
