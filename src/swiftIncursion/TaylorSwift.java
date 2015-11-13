package swiftIncursion;

import java.util.ArrayList;
import java.util.Random;

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

public class TaylorSwift extends Enemy{

	private Image image;
	private Image hoverSheet;
	private Image shootSheet;
	private Rectangle rect;
	private Image[] ramImages;
	private Image[] hoverImages;
	private Image[] shootImages;
	private SpriteSheet ramSprites;
	private SpriteSheet hoverSprites;
	private SpriteSheet shootSprites;
	private Animation ramAnimation;
	private Animation hoverAnimation;
	private Animation shootAnimation;
	private int shooting;
	private Player player;
	private Random random = new Random();
	
	TaylorSwift(Rectangle rect, Level level, Player player) throws SlickException {
		super("Swift", rect, 2, level, 7, 200);
		this.rect = rect;
		image = new Image("Data/tay swift.png");
		hoverSheet = new Image ("Data/tay swift float.png");
		shootSheet = new Image("Data/tay hit.png");
        setUpAnimation();
       	this.player = player;
       	shotDelay = 30;
	}
	
	private void setUpAnimation(){
        ramSprites = new SpriteSheet(image, image.getWidth()/4, image.getHeight());
        ramImages = new Image[4];
        hoverSprites = new SpriteSheet(hoverSheet, hoverSheet.getWidth()/3, hoverSheet.getHeight());
        hoverImages = new Image[3];
        shootSprites = new SpriteSheet(shootSheet, shootSheet.getWidth()/5, shootSheet.getHeight());
        shootImages = new Image[5];
        for(int i = 0; i < 4; i++){
            ramImages[i] = ramSprites.getSubImage(i, 0);
        }
        for(int i = 0; i < 3; i++){
            hoverImages[i] = hoverSprites.getSubImage(i, 0);
        }
        for(int i = 0; i < 5; i++)
        {
        	shootImages[i] = shootSprites.getSubImage(i,0);
        }
        ramAnimation = new Animation(ramImages, 100);
        hoverAnimation = new Animation(hoverImages, 100);
        shootAnimation = new Animation(shootImages, 33);
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
        	
            if(xVel == 0 && shooting < 10)
            {
            	shootAnimation.draw(xPos, this.getPos().y, width+10, shootImages[0].getHeight());
            }
            if(xVel == 0 && shooting > 9){
            	ramAnimation.draw(xPos, this.getPos().y-50, width+5, image.getHeight());
            
            }	
            if(xVel <0 || xVel > 0){
            	hoverAnimation.draw(xPos, this.getPos().y-50, width+5, image.getHeight());
                	
            }
    }
    public void incrementShooting(){
    	shooting++;
    }
    public int getShooting(){
    	return shooting;
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
        sm.beam();
        Bullet bul;
        bul = makeBullet(bulletSpeed,cm, random.nextFloat()*this.getHeight());
        bullets.add(bul);
        cm.addCollidable(bul);
        shooting = 0;
	
	}
	private Bullet makeBullet(int bulletSpeed, CollisionManager cm, float height) throws SlickException
	{
        Bullet bul;
        if(getDirectionFacing() == DIRECTION_FACING.RIGHT){
        bul = new Bullet("Bullet", new Circle(this.getPos().x + this.getWidth(), 
                    this.getPos().y + height, 
                    5), bulletSpeed, cm, 8, Facing.RIGHT, new Image("Data/tay beam.png"), 2);
        }else{
            bul = new Bullet("Bullet", new Circle(this.getPos().x, 
                    this.getPos().y + height, 
                    5), bulletSpeed, cm, 8, Facing.LEFT, new Image("Data/tay beam.png"), 2);
        }
        return bul;
	}
}