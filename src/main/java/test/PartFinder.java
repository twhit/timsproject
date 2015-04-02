package test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import test.Part;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.net.URL;
/*import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.alignment.RotateScaleAligner;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.image.processing.face.recognition.FaceRecognitionEngine;
import org.openimaj.image.processing.face.recognition.EigenFaceRecogniser;
import org.openimaj.image.processing.face.feature.FaceImageFeature.Extractor;
import org.openimaj.ml.annotation.ScoredAnnotation;
import org.openimaj.util.pair.IndependentPair;*/
import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.experiment.dataset.split.GroupedRandomSplitter;
import org.openimaj.experiment.dataset.util.DatasetAdaptors;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.BasicMatcher;
import org.openimaj.feature.local.matcher.BasicTwoWayMatcher;
import org.openimaj.feature.local.matcher.FastBasicKeypointMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.MatchingUtilities;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.image.model.EigenImages;
import org.openimaj.image.pixel.statistics.HistogramModel;
import org.openimaj.image.processing.face.alignment.RotateScaleAligner;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.image.processing.face.recognition.EigenFaceRecogniser;
import org.openimaj.image.processing.face.recognition.FaceRecognitionEngine;
import org.openimaj.math.geometry.transforms.estimation.RobustAffineTransformEstimator;
import org.openimaj.math.model.fit.RANSAC;
import org.openimaj.math.statistics.distribution.MultidimensionalHistogram;
import org.openimaj.ml.annotation.AnnotatedObject;
import org.openimaj.ml.annotation.ScoredAnnotation;
import org.openimaj.util.pair.IndependentPair;


public class PartFinder {

public static Part findPart(String fileName, InputStream fis) throws IOException {
	//File folder = new File("images/");
	//File[] listOfFiles = folder.listFiles(); 
	//File tempfolder = new File("temp/");
	//File[] tempFiles = tempfolder.listFiles();

	//	System.out.println(listOfFiles[i].getName());  
	
	//DBimage = per.getFile();
	//fs = new FileOutputStream("temp.png");
    //fs.write(per.getFile(), 0, (int)per.getFileSize());
    //fs.close();
	//     DBfimg = ImageUtilities.readF(listOfFiles[i]);
	ListPartsCommand l = new ListPartsCommand();
	Part pt = null;
	ArrayList<Part> parts = l.execute();
	int matches, max = 0, closest = 0;
	//imgData = IOUtils.toByteArray(fis);
	BufferedImage imag = ImageIO.read(new ByteArrayInputStream(IOUtils.toByteArray(fis)));
	ImageIO.write(imag, "png", new File("temp.png"));
	MBFImage query = ImageUtilities.createMBFImage(imag, true);
	MBFImage target;
	DoGSIFTEngine engine = new DoGSIFTEngine();
	InputStream is = null;
	GetFileFromPOSTGRESCommand gf = new GetFileFromPOSTGRESCommand();
	int i = 0;
	try {
		for (Part p : parts){
			is = gf.execute(parts.get(i).getFileName());
			BufferedImage imag2 = ImageIO.read(new ByteArrayInputStream(IOUtils.toByteArray(is)));
			ImageIO.write(imag2, "png", new File("temp2.png"));
			target = ImageUtilities.createMBFImage(imag2, true);
			LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(query.flatten()); 
			LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(target.flatten());
			LocalFeatureMatcher<Keypoint> matcher;
			RobustAffineTransformEstimator modelFitter = new RobustAffineTransformEstimator(5.0, 1500,  new RANSAC.PercentageInliersStoppingCondition(0.5));
			matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(new FastBasicKeypointMatcher<Keypoint>(80), modelFitter);
			matcher.setModelFeatures(queryKeypoints); 
			matcher.findMatches(targetKeypoints);
			matches = matcher.getMatches().size();
			if (matches > max){
				max = matches;
				closest = i;
			}
			System.out.println("Matches for " + p.fileName + ": " + matches);
			i++;
		}
		pt = parts.get(closest);
	/*	target = ImageUtilities.readMBF(listOfFiles[closest]);
		LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(query.flatten()); 
		LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(target.flatten());
		LocalFeatureMatcher<Keypoint> matcher;// = new BasicMatcher<Keypoint>(80);
		RobustAffineTransformEstimator modelFitter = new RobustAffineTransformEstimator(5.0, 1500,  new RANSAC.PercentageInliersStoppingCondition(0.5));
		matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(new FastBasicKeypointMatcher<Keypoint>(800), modelFitter);
		matcher.setModelFeatures(queryKeypoints); 
		matcher.findMatches(targetKeypoints);
		MBFImage consistentMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(), RGBColour.RED);
		DisplayUtilities.display(consistentMatches);*/
	}catch (IOException e) {
		e.printStackTrace();
	} 
	return pt;
	}
}
