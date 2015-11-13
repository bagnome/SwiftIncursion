package swiftIncursion;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;


public class GameOverState extends BasicGameState {

	private int stateId;
	private static final int EXITSTATE = 4;
	private static final int TRYAGAIN = 5;
	private static final int MAIN = 6;
	
	private Image background;
	private Image tryAgain;
	private Image quit;
	
	private int selection;
	private int optionSelected;
	private boolean selected;
	
	private SoundAndMusic sm;
	
	
	public GameOverState(int id){
		this.stateId = id;
	}
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		optionSelected = selection;
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);// TODO Auto-generated method stub
		if(newx > 78 && newx < 190 && newy > 450 && newy < 516){
		
				selection = TRYAGAIN;
			} 
		else if(newx > 454 && newx < 621 && newy > 427 && newy < 573){
			
			selection = MAIN;
		} 
		else 
		{
			selection = -1;
			selected = false;
		}
}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateId;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		background = new Image("Data/menu/dSBackground.png");
	    tryAgain = new Image("Data/menu/tryAgain.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		background.draw();
		tryAgain.draw(72,439);
	}
	
	public void enter(GameContainer container, StateBasedGame game)throws SlickException{
		optionSelected = -1;
	}

	@Override
	public void update(GameContainer container, StateBasedGame GameOverState, int arg2) throws SlickException {
		Input input = container.getInput();
		if(container.getInput().isKeyPressed(input.KEY_ENTER))
		GameOverState.enterState(0);

		if(container.getInput().isKeyPressed(input.KEY_ESCAPE))
			System.exit(0);

		if(container.getInput().isKeyPressed(input.KEY_BACK))
		{
			GameInfo.getCurrentGameInfo().resetLives();
			GameOverState.enterState(1);
			GameInfo.getCurrentGameInfo().resetLevelID();
		}
	
		if (optionSelected == TRYAGAIN) {
			GameInfo.getCurrentGameInfo().resetLives();
			GameInfo.getCurrentGameInfo().resetLevelID();
			GameOverState.enterState(1);
		} else if (optionSelected == MAIN) {
			GameInfo.getCurrentGameInfo().resetLives();
			GameInfo.getCurrentGameInfo().resetLevelID();
			GameOverState.enterState(0);
		}
}

}

