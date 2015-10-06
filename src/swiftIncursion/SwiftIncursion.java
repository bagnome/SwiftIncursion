package swiftIncursion;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SwiftIncursion extends StateBasedGame{


	public final static int GAMEPLAYSTATE = 1;
	public final static int WINSTATE = 2;
	
	public SwiftIncursion() {
		super("Some Title");
		
	}
	
	public static void main(String[]args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new SwiftIncursion());

		app.setDisplayMode(800, 600, false);
		app.setTargetFrameRate(60);
		app.start();
		
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new GamePlayState(GAMEPLAYSTATE));
		addState(new WinState(WINSTATE));
		
	}

}
