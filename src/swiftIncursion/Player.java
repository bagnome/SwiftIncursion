package swiftIncursion;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.*;

public class Player extends People implements KeyListener{
	
	private int speed;
	private Level level;
	
	
	public Player(String name, Shape shape, int speed, Level level, int collisionType){
		super (name, shape, collisionType, level, collisionType);
		this.speed = speed;
		yVel = 1;
		this.level = level;
	}
	
	public void move(){
		Vector2f pos = shape.getLocation();
		if(yVel < 10)yVel+=1;
		if(level.getplayerCollidingWithPlatform() && yVel > 0)yVel = 0;
		float y = pos.y;
		float x = pos.x;
		if(xVel < 0)facing = DIRECTION_FACING.LEFT;
		else facing = DIRECTION_FACING.RIGHT;
		y+=yVel;
		x+=xVel;
		shape.setLocation(x, y);
		level.setplayerCollidingWithPlatform(false);
	}
	
	public void render(Graphics g){
        Vector2f pos = shape.getLocation();
        g.draw(shape);
        g.drawString(name, pos.x, pos.y-20);
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
		switch(key){
		/*case 'd':
			xVel = speed;
			break;
		case 'a':
			xVel = -speed;
			break;*/
		case ' ':
			if(yVel == 0)yVel = -25;
		}
		
		
	}

	@Override
	public void keyReleased(int arg0, char key) {
		switch(key){
		case 'd':
			xVel = 0;
			break;
		case 'a':
			xVel = 0;
			break;
		}
		
	}


}
