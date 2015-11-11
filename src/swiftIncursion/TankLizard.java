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

public class TankLizard extends Enemy
{

    //private Image[] images;
    private SpriteSheet bossRunningSprites;
    private SpriteSheet bossShootingSprites;
    private Image[] runningImages;
    private Image[] shootingImages;
    private Animation runningAnimation;
    private Animation shootingAnimation;
    private SoundAndMusic sm;
	private Image[] levelBossImg;

    public TankLizard (Level level) throws SlickException
    {
        super("Boss", new Rectangle(900, 400, 75, 150), 3, level, 7, 15);
        
        setUpAnimation();
        sm = new SoundAndMusic();
    }
    
    private void setUpAnimation() throws SlickException{
        bossRunningSprites = new SpriteSheet(new Image("Data/boss walking.png"), 127, 168);
        bossShootingSprites = new SpriteSheet(new Image("Data/boss1 shooting.png"), 127, 168);
        runningImages = new Image[2];
        shootingImages = new Image[5];
        for(int i = 0; i < 2; i++){
            runningImages[i] = bossRunningSprites.getSubImage(i, 0);
        }
        for(int i = 0; i < 5; i++){
            shootingImages[i] = bossShootingSprites.getSubImage(i, 0);
        }
        runningAnimation = new Animation(runningImages, 100);
        shootingAnimation = new Animation(runningImages, 100);
    }
    
    public void enemyAnimation(int i){
        float xPos;
        float width;
        if(getDirectionFacing() == DIRECTION_FACING.LEFT){
            xPos = this.getPos().x - 15 /*- (mageImages[0].getWidth()-this.getWidth())/2*/;
            width = runningImages[0].getWidth();
        }else{
            xPos = this.getPos().x + this.getWidth() + 15;
            width = -runningImages[0].getWidth(); 
        }
        if(xVel == 0)runningImages[0].draw(xPos, this.getPos().y, width+10, runningImages[0].getHeight());
        if(xVel <0 || xVel > 0)runningAnimation.draw(xPos, this.getPos().y, width+10, runningImages[0].getHeight());
    }
    
    public void shootSound() throws SlickException{
    	sm.tankFire();
    }
    
    public void render(Graphics g){
        g.setColor(new Color(0,0,0));
        g.drawRect(this.getPos().x, this.getPos().y - 10, runningImages[0].getWidth(), 5);
        g.setColor(new Color(34,177,76));
        if(health/startingHealth <= 0.5) g.setColor(new Color(255,255,0));
        if(health/startingHealth <= 0.25) g.setColor(new Color(255,0,0));
        g.fillRect(this.getPos().x+1, this.getPos().y - 9, (float)((health/startingHealth)*runningImages[0].getWidth()-1), 4);
        g.setColor(new Color(255,255,255));

    }

    public void shoot(ArrayList<Bullet> bullets, int bulletSpeed, CollisionManager cm) throws SlickException
    {
        SoundAndMusic sm = new SoundAndMusic();
        sm.tankFire();
        Bullet bul;
        if(getDirectionFacing() == DIRECTION_FACING.RIGHT){
        bul = new Bullet("Bullet", new Circle(this.getPos().x + this.getWidth(), 
                    this.getPos().y + this.getHeight()*(3f/4f), 
                    10), bulletSpeed, cm, 8, Facing.RIGHT, new Image("Data/missle mahaha anim.png"), 2);
        }else{
            bul = new Bullet("Bullet", new Circle(this.getPos().x-50 + this.getWidth(), 
                    this.getPos().y + this.getHeight()*(3f/4f), 
                    10), bulletSpeed, cm, 8, Facing.LEFT, new Image("Data/missle mahaha anim.png"), 2);
        }
        bullets.add(bul);
        cm.addCollidable(bul);
    
    }

}
