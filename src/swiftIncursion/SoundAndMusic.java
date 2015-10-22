package swiftIncursion;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundAndMusic{
    private Sound bullet;
    private Sound fireball;
    private Sound hit;
    private Sound jump;
    private Sound health;
    private Sound dead;
    private Sound ding;
    private Sound tankFire;
    private Sound tracks;
    private Music music;
    
    public SoundAndMusic(){
        
    }
    
    public void titleScreenSong() throws SlickException{
        music = new Music("Game sounds/ts/shake it off 8-bit.ogg");
        music.setVolume(0.2f);
        music.loop();
    }
    
    public void level1Song() throws SlickException{
        music = new Music("Game sounds/ts/style 8 bit.ogg");
        music.setVolume(0.001f);
        music.loop();
    }
    
    public void stopMusic(){
        music.stop();
    }
    
    public void bullet() throws SlickException{
        bullet = new Sound("Game sounds/shotgun.ogg");
        bullet.play(1, 10);
        bullet.play();
    }
    
    public void fireball() throws SlickException{
        fireball = new Sound("Game sounds/fireball.ogg");
        fireball.play();
    }
    
    public void hit() throws SlickException{
        hit = new Sound("Game sounds/punch.ogg");
        hit.play();
    }
    
    public void jump() throws SlickException{
        jump = new Sound("Game sounds/thwomp3.ogg");
        jump.play();
    }

    public void health() throws SlickException{
        health = new Sound("Game sounds/lifebar.ogg");
        health.play();
    }

    public void dead() throws SlickException{
        dead = new Sound("Game sounds/dead.ogg");
        dead.play();
    }

    public void ding() throws SlickException{
        ding = new Sound("Game sounds/ding.ogg");
        ding.play();
    }

    public void tankFire() throws SlickException{
        tankFire = new Sound("Game sounds/tank fire.ogg");
        tankFire.play();
    }

    public void tracks() throws SlickException{
        tracks = new Sound("Game sounds/tank tracks.ogg");
        tracks.play();
    }
    
}
