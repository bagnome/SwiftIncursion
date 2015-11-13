package swiftIncursion;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import swiftIncursion.Bullet.Facing;
import swiftIncursion.People.DIRECTION_FACING;

public class FireMan extends Enemy{

	private Image image;
	
	private Rectangle rect;
	private Image[] ramImages;
	
	private SpriteSheet ramSprites;
	
	private Animation ramAnimation;
	
	
	FireMan(Rectangle rect, Level level) throws SlickException {
		super("Swift", rect, 2, level, 7, 15);
		this.rect = rect;
		image = new Image("Data/fireman.png");
		
        setUpAnimation();
	}
	
	private void setUpAnimation(){
        ramSprites = new SpriteSheet(image, image.getWidth()/6, image.getHeight());
        ramImages = new Image[6];
        
        for(int i = 0; i < 6; i++){
            ramImages[i] = ramSprites.getSubImage(i, 0);
        }
        
        ramAnimation = new Animation(ramImages, 100);
    }
    
    public void enemyAnimation(int i){
        float xPos;
        float width;
        if(getDirectionFacing() == DIRECTION_FACING.LEFT){
            xPos = this.getPos().x  + this.getWidth() + 15 /*- (mageImages[0].getWidth()-this.getWidth())/2*/;
            width = -ramImages[0].getWidth();
        }else{
            xPos = this.getPos().x - 15;
            width = ramImages[0].getWidth(); 
        }
        if(this.getXVel() == 0){
        	ramSprites.getSubImage(5, 0).draw(xPos, this.getPos().y, width+5, image.getHeight());
        }
        else {
        	ramAnimation.draw(xPos, this.getPos().y, width+5, image.getHeight());
       }
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
        //if(facing == DIRECTION_FACING.LEFT) g.drawString("<--", rect.getLocation().x+5, rect.getLocation().y+5);
        //if(facing == DIRECTION_FACING.RIGHT) g.drawString("-->", rect.getLocation().x+5, rect.getLocation().y+5);

    }

	public void shoot(ArrayList<Bullet> bullets, int bulletSpeed, CollisionManager cm) throws SlickException
	{
		SoundAndMusic sm = new SoundAndMusic();
        sm.fireball();
        Bullet bul;
        if(getDirectionFacing() == DIRECTION_FACING.RIGHT){
        bul = new Bullet("Bullet", new Circle(this.getPos().x + this.getWidth() + 20, 
                    this.getPos().y + this.getHeight()/4+25, 
                    5), bulletSpeed, cm, 8, Facing.RIGHT, new Image("Data/fire main.png"), 3);
        }else{
            bul = new Bullet("Bullet", new Circle(this.getPos().x  + 20, 
                    this.getPos().y + this.getHeight()/4+25, 
                    5), bulletSpeed, cm, 8, Facing.LEFT, new Image("Data/fire main.png"), 3);
        }
        bullets.add(bul);
        cm.addCollidable(bul);
	}
	
}
