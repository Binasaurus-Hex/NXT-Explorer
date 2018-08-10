import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;

public class ObjectDetect implements FeatureListener{
	Robot bot;
	public ObjectDetect(Robot bot){
		this.bot = bot;
	}
	public void featureDetected(Feature feature, FeatureDetector detector) {
		
		
		int distance = (int)feature.getRangeReading().getRange();
		if(distance<20){
			bot.objectDetected();
		}
		
	}
	

}
