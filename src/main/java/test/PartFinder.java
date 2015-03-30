package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.openimaj.image.ImageUtilities;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.FastBasicKeypointMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.MatchingUtilities;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.math.geometry.transforms.estimation.RobustAffineTransformEstimator;
import org.openimaj.math.model.fit.RANSAC;

public class PartFinder {

public static String findPart() throws IOException {
	File folder = new File("images/");
	File[] listOfFiles = folder.listFiles(); 
	File tempfolder = new File("temp/");
	File[] tempFiles = tempfolder.listFiles();

	int matches, max = 0, closest = 0;
	MBFImage query = ImageUtilities.readMBF(tempFiles[0]);
	MBFImage target; 
	System.out.println("Target = " + tempFiles[0].getName());
	DoGSIFTEngine engine = new DoGSIFTEngine();
	try {
		for (int i = 0; i < listOfFiles.length; i++){
			target = ImageUtilities.readMBF(listOfFiles[i]);
			LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(query.flatten()); 
			LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(target.flatten());
			LocalFeatureMatcher<Keypoint> matcher;// = new BasicMatcher<Keypoint>(80);
			RobustAffineTransformEstimator modelFitter = new RobustAffineTransformEstimator(5.0, 1500,  new RANSAC.PercentageInliersStoppingCondition(0.5));
			matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(new FastBasicKeypointMatcher<Keypoint>(800), modelFitter);
			matcher.setModelFeatures(queryKeypoints); 
			matcher.findMatches(targetKeypoints);
			matches = matcher.getMatches().size();
			if (matches > max){
				max = matches;
				closest = i;
			}
			System.out.println("Matches for " + listOfFiles[i].getName() + ": " + matches);
		}
		target = ImageUtilities.readMBF(listOfFiles[closest]);
		LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(query.flatten()); 
		LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(target.flatten());
		LocalFeatureMatcher<Keypoint> matcher;// = new BasicMatcher<Keypoint>(80);
		RobustAffineTransformEstimator modelFitter = new RobustAffineTransformEstimator(5.0, 1500,  new RANSAC.PercentageInliersStoppingCondition(0.5));
		matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(new FastBasicKeypointMatcher<Keypoint>(800), modelFitter);
		matcher.setModelFeatures(queryKeypoints); 
		matcher.findMatches(targetKeypoints);
		MBFImage consistentMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(), RGBColour.RED);
		DisplayUtilities.display(consistentMatches);
	}catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	return "OK";
	}
}
