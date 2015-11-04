package swiftIncursion;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import swiftIncursion.Bullet.Facing;

public class Upgrade extends CollidableShapeObject{
	private int upgradeType;
    private SpriteSheet upgradeSprites;
    private Image[] upgrade;
    private Animation upgradeAnimation;
    private int numberOfSprites;
    private Rectangle rect;
    
	Upgrade(String name, Shape shape,  int collisionType, int upgradeType, Image image, int numberOfSprites) {
		super(name, shape, collisionType);
		this.upgradeType = upgradeType;
		this.numberOfSprites = numberOfSprites;
		upgradeAnimationSetUp(image);
		this.rect = (Rectangle) shape;
	}
	
	public int getUpgradeType()
	{
		return upgradeType;
	}
	
	public void upgradeAnimationSetUp(Image image){
	    System.out.println(numberOfSprites);
	    upgradeSprites = new SpriteSheet(image, image.getWidth()/numberOfSprites, image.getHeight());
	    upgrade = new Image[numberOfSprites];
	    for(int i = 0; i < numberOfSprites; i++){
	        upgrade[i] = upgradeSprites.getSubImage(i, 0);
	    }
	    upgradeAnimation = new Animation(upgrade, 100);
	}
	
	public void upgradeAnimation(){

	    upgradeAnimation.draw(this.getPos().x, this.getPos().y, rect.getWidth(), rect.getHeight());
	    
	}
	
}
