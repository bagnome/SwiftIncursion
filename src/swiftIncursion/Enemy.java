package swiftIncursion;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Enemy extends People{

    Enemy(String name, Shape shape, int speed, Level level, int collisionType)
    {
        super(name, shape, speed, level, collisionType);
        
    }
    
    public void render(Graphics g){
        Vector2f pos = shape.getLocation();
        g.draw(shape);
        g.drawString(name, pos.x, pos.y-20);
    }

}
