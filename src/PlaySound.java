import java.io.File;

import lejos.nxt.Sound;

public class PlaySound implements Runnable{
	private Thread thread;
	private String threadName;
	private String soundFile;
	
	public PlaySound(String soundFile){
		this.threadName= soundFile;
		this.soundFile = soundFile;
	}

	
	public void run() {
		File sound = new File(soundFile);
		Sound.playSample(sound);
		
	}
	
	public void start(){
		if(thread == null){
			thread = new Thread(this,threadName);
			thread.start();
		}
	}
	
}
