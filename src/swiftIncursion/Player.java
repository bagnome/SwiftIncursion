package swiftIncursion;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.geom.Rectangle;

public class Player extends People implements KeyListener {
	
	private int speed;
	private Level level;
	private Rectangle rectangle;
	private static final float GRAVITY = 1f;
	private static boolean GROUNDED = true;
	private boolean CROUCHED = false;
		
	public Player(String name, Rectangle rectangle, int speed, Level level, int collisionType){
		super (name, rectangle, collisionType, level, collisionType);
		this.rectangle = rectangle;
		this.speed = speed;
		yVel = 1f;
		this.level = level;
	}
	
	public void setShape(float x, float y){
		rectangle.setWidth(x);
		rectangle.setHeight(y);
	}

		
		public void move(){
			if (yVel > 0)
			{
				GROUNDED = false;
			}
			Vector2f pos = shape.getLocation();
			yVel += GRAVITY;
			
			if(level.getplayerCollidingWithPlatform())
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
			}
			float y = pos.y;
			float x = pos.x;
			y+=yVel;
			x+=xVel;
			shape.setLocation(x, y);
			level.setplayerCollidingWithPlatform(false);
		}
		
		public void setCrouching(boolean t){
			CROUCHED = t;
		}
		public boolean isGrounded(){
			return GROUNDED;
		}
		@Override
		public void inputEnded() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void inputStarted() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isAcceptingInput() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void setInput(Input arg0) {
			// TODO Auto-generated method stub
			
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
