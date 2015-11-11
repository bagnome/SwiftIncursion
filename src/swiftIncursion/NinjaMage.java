package swiftIncursion;

import java.net.URL;
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

public class NinjaMage extends Enemy
{

    private SpriteSheet mageSprites;
    private Image image;
    private Image[] mageImages;
    private Animation mageAnimation;

    NinjaMage(Level level) throws SlickException
    {
        super("Enemy", new Rectangle(900, 400, 75, 150), 2, level, 7, 5);
        image = new Image("Data/ninja mage.png");
        setUpAnimation();
    }
    
    private void setUpAnimation(){
        mageSprites = new SpriteSheet(image, 52, 117);
        mageImages = new Image[2];
        for(int i = 0; i < 2; i++){
            mageImages[i] = mageSprites.getSubImage(i, 0);
        }
        mageAnimation = new Animation(mageImages, 100);
    }
    
    public void enemyAnimation(int i){
        float xPos;
        float width;
        if(getDirectionFacing() == DIRECTION_FACING.LEFT){
            xPos = this.getPos().x - 15 /*- (mageImages[0].getWidth()-this.getWidth())/2*/;
            width = image.getWidth();
        }else{
            xPos = this.getPos().x + this.getWidth() + 15;
            width = -image.getWidth(); 
        }
        mageAnimation.draw(xPos, this.getPos().y, width+5, image.getHeight());
    }
    
    public void shoot(ArrayList<Bullet> bullets, int bulletSpeed, CollisionManager cm) throws SlickException
    {
        SoundAndMusic sm = new SoundAndMusic();
        sm.fireball();
        Bullet bul;
        if(getDirectionFacing() == DIRECTION_FACING.RIGHT){
        bul = new Bullet("Bullet", new Circle(this.getPos().x + this.getWidth(), 
                    this.getPos().y + this.getHeight()/4, 
                    5), bulletSpeed, cm, 8, Facing.RIGHT, new Image("Data/fire main.png"), 3);
        }else{
            bul = new Bullet("Bullet", new Circle(this.getPos().x + this.getWidth(), 
                    this.getPos().y + this.getHeight()/4, 
                    5), bulletSpeed, cm, 8, Facing.LEFT, new Image("Data/fire main.png"), 3);
        }
        bullets.add(bul);
        cm.addCollidable(bul);
        
    }
    
public void render(Graphics g){
        
        
        g.setColor(new Color(0,0,0));
        g.drawRect(this.getPos().x, this.getPos().y - 10, image.getWidth(), 5);
        g.setColor(new Color(34,177,76));
        if(health/startingHealth <= 0.5) g.setColor(new Color(255,255,0));
        if(health/startingHealth <= 0.25) g.setColor(new Color(255,0,0));
        g.fillRect(this.getPos().x+1, this.getPos().y - 9, (float)((health/startingHealth)*image.getWidth()-1), 4);
        g.setColor(new Color(255,255,255));

    }

}
