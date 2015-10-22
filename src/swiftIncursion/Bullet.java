package swiftIncursion;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Bullet extends CollidableShapeObject{
	private int velocity;
	private CollisionManager cm;
	public enum Facing{
		LEFT, RIGHT
	}
	private Facing facing;
    private SpriteSheet bulletSprites;
    private Image[] bullets;
    private Animation bulletAnimation;
    private int numberOfSprites;
    
	Bullet(String name, Shape shape, int velocity, CollisionManager cm, int collisionType, Facing facing, 
	        Image image, int numberOfSprites) {
		super(name, shape, collisionType);
		this.velocity = velocity;
		this.cm = cm;
		this.facing  = facing;
		this.numberOfSprites = numberOfSprites;
		bulletAnimationSetUp(image);
		
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
	
	public void bulletAnimationSetUp(Image image){
	    System.out.println(numberOfSprites);
	    bulletSprites = new SpriteSheet(image, image.getWidth()/numberOfSprites, image.getHeight());
	    bullets = new Image[numberOfSprites];
	    for(int i = 0; i < numberOfSprites; i++){
	        bullets[i] = bulletSprites.getSubImage(i, 0);
	    }
	    bulletAnimation = new Animation(bullets, 100);
	}
	
	public void bulletAnimation(){
	    float xPos;
	    float width;
	    if(velocity < 0){
	        xPos = this.getPos().x-20;
	        width = bullets[0].getWidth();
	    }else{
	        xPos = this.getPos().x+30;
            width = -bullets[0].getWidth();
	    }
	    
	    bulletAnimation.draw(xPos, this.getPos().y-20, width, bullets[0].getHeight());
	    
	}

}
