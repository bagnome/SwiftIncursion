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
    private Sound[] sounds;
    private Music music;
    private Music[] soundTracks;
    private Sound pop;
    private Sound beam;
    public SoundAndMusic() throws SlickException{
        soundTracks = new Music[5];
        sounds = new Sound[11];
        soundTracks[0] = new Music("Game sounds/ts/shake it off 8-bit.ogg");
        soundTracks[1] = new Music("Game sounds/ts/style 8 bit.ogg");
        soundTracks[2] = new Music("Game sounds/ts/new romantics 8 bit.ogg");
        soundTracks[3] = new Music("Game sounds/ts/i knew you were trouble 8-bit.ogg");
        soundTracks[4] = new Music("Game sounds/ts/wildest dreams 8 bit.ogg");
        sounds[0] = new Sound("Game sounds/shotgun.ogg");
        sounds[1] = new Sound("Game sounds/fireball.ogg");
        sounds[2] = new Sound("Game sounds/punch.ogg");
        sounds[3] = new Sound("Game sounds/thwomp3.ogg");
        sounds[4] = new Sound("Game sounds/lifebar.ogg");
        sounds[5] = new Sound("Game sounds/dead.ogg");
        sounds[6] = new Sound("Game sounds/ding.ogg");
        sounds[7] = new Sound("Game sounds/tank fire.ogg");
        sounds[8] = new Sound("Game sounds/tank tracks.ogg");
        sounds[9] = new Sound("Game sounds/blop.ogg");
        sounds[10] = new Sound("Game sounds/beam.ogg");
    }
    
    public void titleScreenSong() throws SlickException{
        music = soundTracks[0];
        //music.setVolume(0.2f);
        music.loop();
    }
    
    public void levelSong(int i) throws SlickException{
        music = soundTracks[i];
        //music.setVolume(0.001f);
        music.loop();
    }
    
    public void stopMusic(){

        music.stop();
    }

    public void pauseMusic(){
    	music.pause();
    }
    
    public void resumeMusic(){
    	music.resume();
    }
    
    public void bullet() throws SlickException{
        bullet = sounds[0];
        bullet.play(1, 10);
        bullet.play();
    }
    
    public void fireball() throws SlickException{
        fireball = sounds[1];
        fireball.play();
    }
    
    public void hit() throws SlickException{
        hit = sounds[2];
        hit.play();
    }
    public void pop() throws SlickException{
    	pop = sounds[9];
    	pop.play();
    }
    
    public void jump() throws SlickException{
        jump = sounds[3];
        jump.play();
    }
    public void beam() throws SlickException{
    	beam = sounds[10];
    	beam.play();
    }
    public void health() throws SlickException{
        health = sounds[4];
        health.play();
    }

    public void dead() throws SlickException{
        dead = sounds[5];
        dead.play();
    }

    public void ding() throws SlickException{
        ding = sounds[6];
        ding.play();
    }

    public void tankFire() throws SlickException{
        tankFire = sounds[7];
        tankFire.play();
    }

    public void tracks() throws SlickException{
        tracks = sounds[8];
        tracks.play();
    }
    
}
