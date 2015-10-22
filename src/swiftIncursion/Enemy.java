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
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import swiftIncursion.Bullet.Facing;
import swiftIncursion.People.DIRECTION_FACING;

public class Enemy extends People{
    
    private Random r;
    private int speed;
    private Level level;
    private static final float GRAVITY = 1f;
    private static boolean GROUNDED = true;
    private boolean collidingWithPlatform;
    private int offSetFromPlayer;
    private Image image;
    private SpriteSheet mageSprites;
    private Image[] mageImages;
    private Animation mageAnimation;
    protected float health;
    protected float startingHealth;
    

    Enemy(String name, Shape shape, int speed, Level level, int collisionType, 
            int health)
    {
        super(name, shape, speed, level, collisionType);
        this.speed = speed;
        this.level = level;
        collidingWithPlatform = true;
        r = new Random();
        offSetFromPlayer = r.nextInt(60);
        
        this.health = health;
        
        startingHealth = health;
    }
    
 
    public void enemyAnimation(int i)
    {
    }
    
    public void render(Graphics g){
        
        
        

    }
    
    public void move(float xVel){
        this.xVel = (int) xVel;
        Vector2f pos = shape.getLocation();
        
        float y = pos.y;
        float x = pos.x;
        y+=yVel;
        x+=xVel;
        shape.setLocation(x, y);
    }
    
    public void shoot(ArrayList<Bullet> bullets, int bulletSpeed, CollisionManager cm) throws SlickException
    {
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
            collidingWithPlatform = false;
        }
    }
    
    public void setCollidingWithplatform(boolean b){
        collidingWithPlatform = b;
    }
    
    public int getOffSet(){
        return offSetFromPlayer;
    }

    public float getHealth(){
        return health;
    }
    
    public void subtractHealth(){
        health--;
    }

    
}
