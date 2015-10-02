package swiftIncursion;

import org.newdawn.slick.geom.Shape;

public class People extends CollidableShapeObject{
	
	public static enum DIRECTION_FACING{
		LEFT, RIGHT
	};
	
	protected DIRECTION_FACING facing;

	People(String name, Shape shape, int speed, Level level, int collisionType) {
		super(name, shape, collisionType);
		
	}
	
	public DIRECTION_FACING getDirectionFacing(){
		return facing;
	}
	
	public void setDirectionFacing(DIRECTION_FACING facing){
		this.facing = facing;
	}

}
