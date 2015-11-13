package swiftIncursion;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import swiftIncursion.People.DIRECTION_FACING;

public class Bunny extends Enemy{
	
	private Image image;
	private Rectangle rect;
	private Image[] bunnyImages;
	private SpriteSheet bunnySprites;
	private Animation bunnyAnimation;
	private boolean ramming;
	private Random random = new Random();

	public Bunny(Rectangle rect, Level level) throws SlickException {
		super("bunny", rect, 2, level, 7, 30);
		this.rect = rect;
		image = new Image("Data/bunny.png");
        setUpAnimation();
        
	}
	
	
	private void setUpAnimation(){
		
        bunnySprites = new SpriteSheet(image, image.getWidth()/4, image.getHeight());
        bunnyImages = new Image[4];
        for(int i = 0; i < 4; i++){
            bunnyImages[i] = bunnySprites.getSubImage(i, 0);
        }
        bunnyAnimation = new Animation(bunnyImages, 100);
    }
    public void moveY(){
        if (yVel > 0)
        {
            GROUNDED = false;
        }
        Vector2f pos = shape.getLocation();
        if (yVel < 10)yVel += GRAVITY;
        
        if(collidingWithPlatform)
        {
            if (yVel < 0)
            {
                float adjustY = yVel - 1;
                yVel = 0;
                shape.setLocation(pos.x,pos.y-adjustY);
                pos = shape.getLocation();
            }
            if (yVel > 0)
            {
            	GROUNDED = true;
                yVel = 0;
            }
            if(GROUNDED){
            	if (random.nextInt(60) == 1)
            	{
            	shape.setLocation(pos.x,pos.y-1);
            	yVel = -15;
            	}
            }
            collidingWithPlatform = false;

        }
    }
    
    public void enemyAnimation(int i){
        float xPos;
        float width;
        if(getDirectionFacing() == DIRECTION_FACING.LEFT){
            xPos = this.getPos().x - 15 /*- (mageImages[0].getWidth()-this.getWidth())/2*/;
            width = bunnyImages[0].getWidth();
        }else{
            xPos = this.getPos().x + this.getWidth() + 15;
            width = -bunnyImages[0].getWidth(); 
        }
        bunnyAnimation.draw(xPos, this.getPos().y, width+5, image.getHeight());
    }
	
    public boolean getRamming(){
    	return ramming;
    }
    public void setRamming(boolean ramming){
    	this.ramming = ramming;
    }
	public void render(Graphics g){
        
        
        g.setColor(new Color(0,0,0));
        g.drawRect(this.getPos().x, this.getPos().y - 10, rect.getWidth(), 5);
        g.setColor(new Color(34,177,76));
        if(health/startingHealth <= 0.5) g.setColor(new Color(255,255,0));
        if(health/startingHealth <= 0.25) g.setColor(new Color(255,0,0));
        g.fillRect(this.getPos().x+1, this.getPos().y - 9, (float)((health/startingHealth)*rect.getWidth()-1), 4);
        g.setColor(new Color(255,255,255));
        //g.draw(rect);
     //   if(facing == DIRECTION_FACING.LEFT) g.drawString("<--", rect.getLocation().x+5, rect.getLocation().y+5);
      //  if(facing == DIRECTION_FACING.RIGHT) g.drawString("-->", rect.getLocation().x+5, rect.getLocation().y+5);

    }

	public void shoot(ArrayList<Bullet> bullets, int bulletSpeed, CollisionManager cm) throws SlickException
	{
		
	}

}
