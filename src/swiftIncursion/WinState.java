package swiftIncursion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class WinState extends BasicGameState{
	
	private int stateId;
	private ArrayList<String[]> times;
	private Calendar cal;
	
	public WinState(int id){
		this.stateId = id;
		times = new ArrayList<String[]>();
		cal = new GregorianCalendar(TimeZone.getTimeZone("America/Chicago"));
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawString("Congradulations! You have won the game!\n You completed the game in: " + GameInfo.getCurrentGameInfo().getStoredTime() + " \n Press [Enter] to return to the start screen.", 100, 100);
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2)
			throws SlickException {
		Input input = container.getInput();
		if ((container.getInput().isKeyDown(input.KEY_ENTER))){
			saveTime();
			GameInfo.getCurrentGameInfo().resetLevelID();
			arg1.enterState(3);
		}
		// TODO Auto-generated method stub
		
	}
	
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		
	}
	
	private void saveTime() throws SlickException{
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("Data/times.txt"))));
			String[] args = readNextValidLine(br);
			while(args != null){
				times.add(args);
				args = readNextValidLine(br);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't load times.txt");
			e.printStackTrace();
		}
		
		
		
		String current = GameInfo.getCurrentGameInfo().getStoredTime();
		System.out.println(current);
		ArrayList<String[]> newTimes = new ArrayList<String[]>();
		boolean placed = false;
		String[] list = new String[2];
		
		for(int i = 0; i < times.size(); i++){
			
			String s = times.get(i)[0];
			int n = Integer.parseInt(s.substring(0,	s.indexOf(":")))*60*1000 + Integer.parseInt(s.substring(s.indexOf(":") + 1,	s.indexOf(".")))*1000 + Integer.parseInt(s.substring(s.indexOf(".") + 1));
			int currentTime = Integer.parseInt(current.substring(0,	current.indexOf(":")))*60*1000 + Integer.parseInt(current.substring(current.indexOf(":") + 1,	current.indexOf(".")))*1000 + Integer.parseInt(current.substring(current.indexOf(".") + 1));
			
			
			if(currentTime <= n && !placed){
				
				list[0] = current;
				list[1] = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR) + " @ " + cal.get(Calendar.HOUR_OF_DAY) + ":" + (cal.get(Calendar.MINUTE) < 10? "0" + cal.get(Calendar.MINUTE) : cal.get(Calendar.MINUTE)) + ":" + 
						(cal.get(Calendar.SECOND) < 10? "0" + cal.get(Calendar.SECOND) : cal.get(Calendar.SECOND));
				newTimes.add(list);
				placed = true;
			}
			
			newTimes.add(times.get(i));
			
		}
		
		newTimes.remove(5);
		
		
		for(String[] st: newTimes){
			for(String td: st){
				System.out.print(td + " ");
			}
			System.out.println();
		}
		
		try {
			BufferedWriter buffedWriter = new BufferedWriter(new FileWriter("Data/times.txt"));
			buffedWriter.write(newTimes.get(0)[0] + "|" + newTimes.get(0)[1]);
			buffedWriter.newLine();
			buffedWriter.write(newTimes.get(1)[0] + "|" + newTimes.get(1)[1]);
			buffedWriter.newLine();
			buffedWriter.write(newTimes.get(2)[0] + "|" + newTimes.get(2)[1]);
			buffedWriter.newLine();
			buffedWriter.write(newTimes.get(3)[0] + "|" + newTimes.get(3)[1]);
			buffedWriter.newLine();
			buffedWriter.write(newTimes.get(4)[0] + "|" + newTimes.get(4)[1]);
			buffedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String[] readNextValidLine(BufferedReader br) throws SlickException {
		boolean read = false;
        String[] args = null;

        while (!read) {
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new SlickException("Could not read level", e);
            }
            if(line == null) return null;
            if (!(line.startsWith("#") || line.isEmpty())) {
                read = true;
                args = line.split("\\|");
            }
        }
        return args;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateId;
	}

}
