//Class menu State
//to use, move into src files and add the state to the class SwiftIncursion
//changes in SI

//public final static int MENUSTATE = 0; to public class SwiftIncursion extends StateBasedGame{

//addState(new MenuState(MENUSTATE));    to inits states list

package swiftIncursion;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;



import swiftIncursion.SwiftIncursion;
import swiftIncursion.GameInfo;

public class MenuState extends BasicGameState {
	private static final int HELPSTATE = 3;
	private static final int EXITSTATE = 4;
	//background image
	private Image background;
	private Image helpstate;
	//button Images
	private Image help1;
	private Image help2;
	private Image play1;
	private Image play2;
	private Image quit1;
	private Image quit2;
	private int stateId;
	private Level level;

	private int selection;
	private int optionSelected;
	
	private boolean selected;
	
	private SoundAndMusic sm;

		//initialize buttons
	public MenuState(int id) {
		this.stateId = id;
		
	}
		
	@Override
	public void init(GameContainer container, StateBasedGame menu) throws SlickException {
		
		background = new Image("data/menu/mm2.png");
		//buttons
		help1 = new Image("Data/menu/help 1.png");
		help2 = new Image("Data/menu/help 2.png");
		play1 = new Image("Data/menu/play 1.png");
		play2 = new Image("Data/menu/play 2.png");
		quit1 = new Image("Data/menu/quit 1.png");
		quit2 = new Image("Data/menu/quit 2.png");
		helpstate = new Image("Data/menu/HELPSTATE.png");
		sm = GameInfo.getCurrentGameInfo().getSounds();
		sm.titleScreenSong();
		selected = false;
		//hud = new Hud();
	}

	/*/
	 * Render Method
	 * add text to string, add background... ect
	 * adds images 
	 * */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)throws SlickException {
		{
		background.draw(0,0);
		play1.draw(50, 150, 2);
		help1.draw(50, 250,2);
		quit1.draw(50, 350, 2);
		if (selection == SwiftIncursion.GAMEPLAYSTATE) {
			play2.draw(50, 150, 2);}
		else if (selection == EXITSTATE){
			quit2.draw(50, 350, 2);}
		else if (selection == HELPSTATE){
			help2.draw(50, 250, 2);
			helpstate.draw(375, 450);}
			}
		g.drawImage(new Image("Data/title.png"), -10, -100);
	
		}
	
	//mouse function test 
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		optionSelected = selection;
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		if (newx > 50 && newx < 250 ) {
			// start game
			if (newy > 200 && newy < 300) {
			    if(!selected){
			        try
                    {
                        sm.ding();
                    } catch (SlickException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
			        selected = true;
			    }
			selection = SwiftIncursion.GAMEPLAYSTATE;
				// exit game
			}else if(newy > 308 && newy < 389){
			    if(!selected){
                    try
                    {
                        sm.ding();
                    } catch (SlickException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    selected = true;
                }
				selection = HELPSTATE;
			} else if (newy > 420 && newy < 500) {
			    if(!selected){
                    try
                    {
                        sm.ding();
                    } catch (SlickException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    selected = true;
                }
			selection = EXITSTATE;}
			 else {
				selection = -1;
				selected = false;
			}
		}
	}
	
	//end
	
	
	
	/*/
	 * Update Method
	 * updates game info
	 * */
	@Override
	public void update(GameContainer container, StateBasedGame menu, int arg2)throws SlickException {
		Input input = container.getInput();
		if(container.getInput().isKeyPressed(input.KEY_ENTER))
		menu.enterState(1);
		
		if (optionSelected == SwiftIncursion.GAMEPLAYSTATE) {
		 //   sm.stopMusic();
		    GameInfo.getCurrentGameInfo().resetLives();
		    GameInfo.getCurrentGameInfo().resetLevelID();
			menu.enterState(SwiftIncursion.GAMEPLAYSTATE);
		} else if (optionSelected == EXITSTATE) {
			System.exit(0);		
		} else if (optionSelected == HELPSTATE){
			draw();}
			//System.exit(0);}
	}
	
	private void draw() {
		helpstate.draw();
		
	}

	public void enter(GameContainer container, StateBasedGame game)throws SlickException{
		sm.titleScreenSong();
		optionSelected = -1;
	}

	@Override
	public int getID() {
		return stateId;
	}
}