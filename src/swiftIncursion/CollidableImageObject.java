package swiftIncursion;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

public class CollidableImageObject extends CollidableObject
{
    private Image image;
    
    public CollidableImageObject(String name, Image image, Shape shape, int collisionType)
    {
        super(name, shape, collisionType);
        this.image = image;
        
    }
    
    public void render(Graphics g){
        g.drawImage(image, shape.getX(), shape.getY());
    }

}
