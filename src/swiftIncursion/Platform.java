package swiftIncursion;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Platform extends CollidableShapeObject{
    protected CollidableShapeObject leftEnd;
    protected CollidableShapeObject rightEnd;
    private SpriteSheet platSprites;
    private Image[] platImages;
    private Animation platAnimation;

	Platform(String name, Shape shape, int collisionType) throws SlickException {
		super(name, shape, collisionType);
		leftEnd = new CollidableShapeObject
		        ("Left End", new Rectangle(this.getPos().x-6, this.getPos().y,6,this.getHeight()), 10);
		rightEnd = new CollidableShapeObject
		        ("Right End", new Rectangle(this.getPos().x+this.getWidth(), this.getPos().y,6,this.getHeight()), 9);
		setUpAnimation(new Image("Data/platform.png"));
	}
	
	
	private void setUpAnimation(Image image){
        platSprites = new SpriteSheet(image, image.getWidth()/2, image.getHeight());
        platImages = new Image[1];
        platImages[0] = platSprites.getSubImage(0, 0);
        platAnimation = new Animation(platImages, 100);
    }
	public void platAnimation()
	{
		platAnimation.draw(this.getPos().x, this.getPos().y, this.getWidth(), this.getHeight());
	}
	
	public void render(Graphics g)
	{
		//g.draw(shape);
		//g.drawString(name, this.getPos().x,this.getPos().y);
	}
	public CollidableShapeObject getLeftEnd(){
	    return leftEnd;
	}
	
	public CollidableShapeObject getRightEnd(){
        return rightEnd;
    }

}
