package swiftIncursion;

import org.newdawn.slick.SlickException;

public class GameInfo
{
    private static GameInfo _instance = null;
    private int levelID;
    private int lives;
    private boolean playerExists;
    private SoundAndMusic sm;
    
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
    
    public void setPlayerExists(boolean p){
        playerExists = p;
    }

}
