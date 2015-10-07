package swiftIncursion;

public class GameInfo
{
    private static GameInfo _instance = null;
    private int levelID;
    
    public static GameInfo getCurrentGameInfo(){
        return _instance;
    }
    
    public static GameInfo creatNewGameInfo(){
        _instance = new GameInfo();
        return getCurrentGameInfo();
    }
    
    private GameInfo(){
        levelID = 1;
    }
    
    public void nextLevel(){
        System.out.println(levelID);
        levelID++;
    }
    
    public int getLevelID(){
        return levelID;
    }

}
