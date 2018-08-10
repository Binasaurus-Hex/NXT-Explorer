import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class Calibration {
	
	/*
	 * saves the properties object containing the calibration variables to the given filename
	 * @param calibrations (Properties)
	 * @param filename (String)
	 */
	private static void saveCalibrations(Properties calibrations,String filename){
		FileOutputStream output = null;
		File calibrationFile = new File(filename);
		try{
			output = new FileOutputStream(calibrationFile);
			calibrations.store(output, null);
			output.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return;
		
	}
	
	
	public static Properties getCalibrations(String filename){
		Properties calibrations = new Properties();
		InputStream input = null;
		
		
		File calibrationFile = new File(filename);
		try {
			input = new FileInputStream(calibrationFile);
			calibrations.load(input);
			input.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return calibrations;
	}
	
	/*
	 * runs a calibration sequence for the arm of the robot returns max and min values of roatation
	 * @returns armAngles (int[])
	 */
	private static int[] calibrateArm(){
		System.out.println("put the arm in the opened position");
		System.out.println("then press enter...");
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		int armAngleOpen = Motor.A.getTachoCount();
		System.out.println("put the arm in the closed position");
		System.out.println("then press enter...");
		Button.ENTER.waitForPressAndRelease();
		LCD.clear();
		int armAngleClosed = Motor.A.getTachoCount();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int[] armAngles = {armAngleOpen,armAngleClosed};
		return armAngles;
		
	}
	
	public static void calibrate(){
		int[] armAngles = calibrateArm();
		int armAngleOpen = armAngles[0];
		int armAngleClosed = armAngles[1];
		double pilotRotationModifier = 1.5;
		Properties calibrations = new Properties();
		calibrations.setProperty("armAngleOpen", String.valueOf(armAngleOpen));
		calibrations.setProperty("armAngleClosed",String.valueOf(armAngleClosed));
		calibrations.setProperty("pilotRotationModifier", String.valueOf(pilotRotationModifier));
		saveCalibrations(calibrations,"cal.properties");
		return;
	}
	
}
