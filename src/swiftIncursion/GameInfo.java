package swiftIncursion;

import org.newdawn.slick.SlickException;

public class GameInfo
{
    private static GameInfo _instance = null;
    private int levelID;
    private int lives;
    private boolean playerExists;
    private SoundAndMusic sm;
    private Player player;
    private String time;
    
    public static GameInfo getCurrentGameInfo(){
        return _instance;
    }
    
    public static GameInfo creatNewGameInfo() throws SlickException{
        _instance = new GameInfo();
        return getCurrentGameInfo();
    }
    
    private GameInfo() throws SlickException{
        lives = 3;
        levelID = 1;
        playerExists = false;
        sm = new SoundAndMusic();
    }
    
    public void nextLevel(){
        System.out.println(levelID);
        levelID++;
    }
    
    public int getLevelID(){
        return levelID;
    }
    
    public int getLives(){
        return lives;
    }
    
    public void removeLife(){
        lives--;
    }
    
    public SoundAndMusic getSounds(){
        return sm;
    }
    
    public boolean getPlayerExists(){
        return playerExists;
    }
    
    public void setPlayerExists(boolean p, Player player){
        playerExists = p;
        this.player = player;
    }

	public void resetLives() {
		lives = 3;
		if (getPlayerExists())
		{
			player.resetUpgrades();
		}
	}
	public void resetLevelID(){
		levelID = 1;
	}

	public void recordTime(String time){
		this.time = time;
	}
	
	public String getStoredTime(){
		return time;
	}
}
