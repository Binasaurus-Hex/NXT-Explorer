
import java.util.Properties;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.RangeFeatureDetector;

public class Robot{
	public DifferentialPilot pilot;
	private UltrasonicSensor ultrasonic;
	private LightSensor lightLeft;
	private LightSensor lightRight;
	private String name;
	private int armAngleOpen;
	private int armAngleClosed;
	private double pilotRotationModifier;
	private Properties calibrations;
	private boolean running;
	
	

	public Robot(
				DifferentialPilot pilot,
				UltrasonicSensor ultrasonic,
				LightSensor lightLeft,
				LightSensor lightRight,
				Properties calibrations,
				String name){
		
		this.lightLeft = lightLeft;
		this.lightRight = lightRight;
		this.pilot = pilot;
		this.ultrasonic = ultrasonic;
		this.calibrations = calibrations;
		this.name = name;
		
		String armAngleOpen = this.calibrations.getProperty("armAngleOpen");
		String armAngleClosed = this.calibrations.getProperty("armAngleClosed");
		String pilotRotationModifier = this.calibrations.getProperty("pilotRotationModifier");
		
		this.armAngleOpen = Integer.parseInt(armAngleOpen);
		this.armAngleClosed = Integer.parseInt(armAngleClosed);
		this.pilotRotationModifier = Double.parseDouble(pilotRotationModifier);
		
		
		
	}
	
	public void mainLoop(){
		while(running){
			
		}
	}
	
	public void run(){
		
		//binding back button to exit program
		Button.ESCAPE.addButtonListener(new ButtonListener(){
			public void buttonPressed(Button b){
				System.exit(0);
			}
			
			public void buttonReleased(Button b){
				
			}
		});
		
		
		//adding listener for objects (ultrasonic)
		ObjectDetect objListener = new ObjectDetect(this);
		RangeFeatureDetector eyes = new RangeFeatureDetector(this.ultrasonic,80,500);
		eyes.addListener(objListener);
		
		//ends program
		Button.waitForAnyPress();
		
		
		
	}
	
	
	public void grab(){
		
		Motor.A.rotateTo(this.armAngleOpen);
		this.pilot.forward();
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		Motor.A.rotateTo(this.armAngleClosed);
		Sound.beep();
		this.pilot.stop();
	}
	
	public void release(){
		Motor.A.rotateTo(this.armAngleOpen);
		pilot.travel(-100);
		Motor.A.rotateTo(this.armAngleClosed);
	}
	
	public void happy(){
		PlaySound r2d2 = new PlaySound("r2d2.wav");
		r2d2.start();
		pilot.rotate(20);
		pilot.rotate(-20);
		for(int x = 0; x<2; x++){
			Motor.A.rotate(10);
			Motor.A.rotateTo(this.armAngleClosed);
			
		}
		
		
		
	}
	
	
	public void objectDetected(){
		grab();
		pilot.rotate(180*pilotRotationModifier);
		pilot.travel(250);
		release();
		happy();
	}
	
	
	
	

}
