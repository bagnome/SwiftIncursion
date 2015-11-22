package swiftIncursion;

import org.newdawn.slick.Graphics;

public class Timer {
	
	private int time;
	private String newTime;
	private int milli;
	private int mins;
	private int secs;
	
	public Timer(){
		time = 0;
		newTime = "0";
		milli = 0;
		mins = 0;
	}
	
	public void render(Graphics g){
		
	}
	
	public void tick(int delta){
		time += delta;
		String milli = "";
		this.milli += delta;
		
		if(this.milli >= 1000)this.milli -= 1000;
		
		secs = time/1000;
		
		if(this.milli < 1000 && this.milli > 99)milli += "." + this.milli;
		if(this.milli < 100 && this.milli > 9)milli += ".0" + this.milli;
		if(this.milli < 10)milli += ".00" + this.milli;
		
		String stringSecs = "";
		String stringMins = "";
		
		if(secs == 60){
			time = 0;
			mins++;
		}
		
		if(secs < 10)stringSecs = "0" + secs;
		else stringSecs = "" + secs;
		
		if(mins < 10)stringMins = "0" + mins;
		else stringMins = "" + mins;
		
		newTime = "" + stringMins + ":" + stringSecs + milli;
	}
	
	public String getTime(){
		return newTime;
	}

	public void resetTimer(){
		time = 0;
		newTime = "0";
		milli = 0;
		mins = 0;
	}
}
