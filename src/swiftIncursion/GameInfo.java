package swiftIncursion;

public class GameInfo
{
    private static GameInfo _instance = null;
    private int levelID;
    private int lives;
    private boolean playerExists;
    
    public static GameInfo getCurrentGameInfo(){
        return _instance;
    }
    
    public static GameInfo creatNewGameInfo(){
        _instance = new GameInfo();
        return getCurrentGameInfo();
    }
    
    private GameInfo(){
        lives = 3;
        levelID = 1;
        playerExists = false;
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
    
    public boolean getPlayerExists(){
        return playerExists;
    }
    
    public void setPlayerExists(boolean p){
        playerExists = p;
    }

}
