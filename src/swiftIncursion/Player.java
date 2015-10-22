package swiftIncursion;

import org.newdawn.slick.Animation;
//import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.*;

public class Player extends People implements KeyListener {
	
	//private int speed;
	private Level level;
	private Rectangle rectangle;
	private static final float GRAVITY = 0.4f;
	private static boolean GROUNDED = true;
	private boolean CROUCHED = false;
    private Image[] runAnimation;
    private Image[] walkAnimation;
    private SpriteSheet playerSprites;
    private SpriteSheet playerWalkSprites;
    private Animation walkingAnimation;
    private SpriteSheet playerCrouchSprites;
    private Image[] crawling;
    private Animation crawlingAnimation;
		
	public Player(String name, Rectangle rectangle, int speed, Level level, int collisionType) throws SlickException{
		super (name, rectangle, collisionType, level, collisionType);
		this.rectangle = rectangle;
		//this.speed = speed;
		yVel = 1f;
		this.level = level;
		animationSetUp();
	}
	
	public void setShape(float x, float y){
		rectangle.setWidth(x);
		rectangle.setHeight(y);
	}

		
		public void move(){
			GROUNDED = false;
			yVel += GRAVITY;
			Vector2f pos = shape.getLocation();
			
			if(level.getplayerCollidingWithPlatform())
			{
				yVel = 0;
			}
			float y = pos.y;
			float x = pos.x;
			y+=yVel;
			x+=xVel;
			shape.setLocation(x, y);
			level.setplayerCollidingWithPlatform(false);
			level.setplayerCollidingWithLeftEdge(false);
			level.setplayerCollidingWithRightEdge(false);
		}
		
		public void setCrouching(boolean t){
			CROUCHED = t;
		}
		public boolean isCrouching(){
			return CROUCHED;
		}
		public void setGrounded(boolean t){
			GROUNDED = t;
		}
		public boolean isGrounded(){
			return GROUNDED;
		}
		
		private void animationSetUp() throws SlickException{
            runAnimation = new Image[5];
            walkAnimation = new Image[5];
            crawling = new Image[4];
            playerSprites = new SpriteSheet("Data/NumberSprite.png", 100, 200);
            playerWalkSprites = new SpriteSheet("Data/main walk jump.png", 99, 115);
            playerCrouchSprites = new SpriteSheet("Data/main crawling.png", 160, 140);
            for(int i = 0; i < 5; i++){
                runAnimation[i] = playerSprites.getSubImage(i, 0);
                walkAnimation[i] = playerWalkSprites.getSubImage(i, 0);
            }
            for(int i = 0; i < 4; i++){
                crawling[i] = playerCrouchSprites.getSubImage(i, 0);
            }
            walkingAnimation = new Animation(walkAnimation, 100);
            crawlingAnimation = new Animation(crawling, 100);
        }
        
        public void runAnimation(int i){ 
            float xPos;
            float width;
            float xPosC;
            float widthC;
            if(getDirectionFacing() == DIRECTION_FACING.RIGHT){
                xPos = this.getPos().x - (walkAnimation[0].getWidth()-this.getWidth())/2;
                xPosC = this.getPos().x - (crawling[0].getWidth()-this.getWidth())/2;
                width = walkAnimation[0].getWidth();
                widthC = crawling[0].getWidth();
            }else{
                xPos = this.getPos().x + this.getWidth();
                xPosC = this.getPos().x + this.getWidth();
                width = -walkAnimation[0].getWidth(); 
                widthC = -crawling[0].getWidth(); 
            }
            if(i == 1 && GROUNDED)walkingAnimation.draw(xPos+15, this.getPos().y, width+5, walkAnimation[0].getHeight());
            else if(i == 2 && GROUNDED)playerWalkSprites.getSubImage(5, 0).draw(xPos+15, this.getPos().y, width+5, walkAnimation[0].getHeight());
            else if (i == 3 && GROUNDED)crawlingAnimation.draw(xPos, this.getPos().y-110, widthC, crawling[0].getHeight());
            else if(i == 4 && GROUNDED)crawling[0].draw(xPos, this.getPos().y-110, widthC, crawling[0].getHeight());
            else if (yVel < 0)playerWalkSprites.getSubImage(6, 0).draw(xPos+15, this.getPos().y, width+5, walkAnimation[0].getHeight());
            else if (yVel > 0)playerWalkSprites.getSubImage(7, 0).draw(xPos+15, this.getPos().y, width+5, walkAnimation[0].getHeight());
            
        }
		@Override
		public void inputEnded() {
			
		}

		@Override
		public void inputStarted() {
			
		}

		@Override
		public boolean isAcceptingInput() {
			return true;
		}

		@Override
		public void setInput(Input arg0) {
			
		}

		@Override
		public void keyPressed(int numKey, char key) {
		    
		   
			switch(numKey){
			case 57:
			    System.out.println(GROUNDED + " " + yVel + " " + CROUCHED);
				if(GROUNDED &&yVel >= 0f && !CROUCHED)
				{
					yVel = -15f;
					System.out.println(yVel);
					GROUNDED = false;
					level.setplayerCollidingWithPlatform(false);
					SoundAndMusic sm = new SoundAndMusic();
					try
					{
					    sm.jump();
					} catch (SlickException e)
					{
                // TODO Auto-generated catch block
					    e.printStackTrace();
					}
				}
				
				break;
			}
		}	
			
		

		@Override
		public void keyReleased(int arg0, char key) {
			switch(arg0){
			case 57:
				if (yVel < 0f && !CROUCHED)
				{
					yVel = 0f;
				}
				break;
			}		
		}
	}
