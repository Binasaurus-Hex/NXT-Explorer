import java.util.Properties;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

public class ControlCentre {
	private static int wheelDiameter = 40;
	private static int trackWidth = 185;
	private static String calibrationsFilename = "cal.properties";

	public static void main(String[] args) {
		LightSensor lightLeft = new LightSensor(SensorPort.S2);
		LightSensor lightRight = new LightSensor(SensorPort.S1);
		UltrasonicSensor ultrasonic = new UltrasonicSensor(SensorPort.S4);
		DifferentialPilot pilot = new DifferentialPilot(wheelDiameter,trackWidth,Motor.B,Motor.C);
		Properties calibrations = null;
		System.out.println("press right for calibration");
		System.out.println("press any other button to start...");
		int pressed = Button.waitForAnyPress();
		LCD.clear();
		int right = Button.ID_RIGHT;
		if(pressed == right){
			Calibration.calibrate();
			System.exit(0);
		}
		else{
			calibrations = Calibration.getCalibrations(calibrationsFilename);
			Robot explorer = new Robot(pilot,ultrasonic,lightLeft,lightRight,calibrations,"Explorer");
			explorer.run(); 
		}
	}

}
