package swiftIncursion;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Ram extends Enemy{
	
	private Shape shape;

	public Ram(Level level) {
		super("Ram", new Rectangle(900, 400, 75, 150), 3, level, 7, 5);
		this.shape = new Rectangle(900, 400, 75, 150);
	}
	
	public void render(Graphics g){
		g.draw(shape);
    }
	
	public void shoot(ArrayList<Bullet> bullets, int bulletSpeed, CollisionManager cm) throws SlickException{
		
	}

}
