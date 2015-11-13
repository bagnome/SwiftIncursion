package swiftIncursion;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import swiftIncursion.People.DIRECTION_FACING;

public class Ram extends Enemy{
	
	private Image image;
	private Rectangle rect;
	private Image[] ramImages;
	private SpriteSheet ramSprites;
	private Animation ramAnimation;
	private boolean ramming;

	public Ram(Rectangle rect, Level level) throws SlickException {
		super("Ram", rect, 2, level, 7, 12);
		this.rect = rect;
		image = new Image("Data/gotman2.png");
        setUpAnimation();
	}
	
	private void setUpAnimation(){
        ramSprites = new SpriteSheet(image, 111, 136);
        ramImages = new Image[8];
        for(int i = 0; i < 8; i++){
            ramImages[i] = ramSprites.getSubImage(i, 0);
        }
        ramAnimation = new Animation(ramImages, 100);
    }
    
    public void enemyAnimation(int i){
        float xPos;
        float width;
        if(getDirectionFacing() == DIRECTION_FACING.LEFT){
            xPos = this.getPos().x - 15 /*- (mageImages[0].getWidth()-this.getWidth())/2*/;
            width = ramImages[0].getWidth();
        }else{
            xPos = this.getPos().x + this.getWidth() + 15;
            width = -ramImages[0].getWidth(); 
        }
        ramAnimation.draw(xPos, this.getPos().y, width+5, image.getHeight());
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
    //    if(facing == DIRECTION_FACING.LEFT) g.drawString("<--", rect.getLocation().x+5, rect.getLocation().y+5);
       // if(facing == DIRECTION_FACING.RIGHT) g.drawString("-->", rect.getLocation().x+5, rect.getLocation().y+5);

    }

	public void shoot(ArrayList<Bullet> bullets, int bulletSpeed, CollisionManager cm) throws SlickException
	{
		
	}

}
