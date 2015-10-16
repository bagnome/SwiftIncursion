package swiftIncursion;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import swiftIncursion.Bullet.Facing;

public class Enemy extends People{
    
    private Random r;
    private int speed;
    private Level level;
    private static final float GRAVITY = 1f;
    private static boolean GROUNDED = true;
    private boolean collidingWithPlatform;
    private int offSetFromPlayer;

    Enemy(String name, Shape shape, int speed, Level level, int collisionType)
    {
        super(name, shape, speed, level, collisionType);
        this.speed = speed;
        this.level = level;
        collidingWithPlatform = true;
        r = new Random();
        offSetFromPlayer = r.nextInt(60);
    }
    
    public void render(Graphics g){
        Vector2f pos = shape.getLocation();
        g.draw(shape);
        g.drawString(name, pos.x, pos.y-20);
    }
    
    public void move(float xVel){
        
        Vector2f pos = shape.getLocation();
        
        float y = pos.y;
        float x = pos.x;
        y+=yVel;
        x+=xVel;
        shape.setLocation(x, y);
    }
    
    public void shoot(ArrayList<Bullet> bullets, int bulletSpeed, CollisionManager cm)
    {
        bullets.add(new Bullet("Bullet", new Circle(this.getPos().x + this.getWidth(), 
                    this.getPos().y + this.getHeight()/4, 
                    5), bulletSpeed, cm, 8, Facing.RIGHT));
        
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

    

}
