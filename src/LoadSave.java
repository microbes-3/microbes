import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Attribute;
import weka.core.SerializationHelper;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

public class LoadSave {
	public static String[] listClasses(Instances data) {
		Attribute classesAttr = data.attribute(data.classIndex());

		@SuppressWarnings("unchecked")
		Enumeration<String> classesEnum = classesAttr.enumerateValues();

		String[] classes = new String[classesAttr.numValues()];
		int i = 0;
		for (Double val = null; classesEnum.hasMoreElements(); i++) {
			classes[i] = classesEnum.nextElement();
		}

		return classes;
	}

	public String resolvePath(String path) {
		return path.replaceAll("~", System.getProperty("user.home"));
	}

	public Instances loadDataset(String path) {
		try {
			DataSource source = new DataSource(resolvePath(path));
			Instances dataset = source.getDataSet();
			dataset.setClassIndex(dataset.numAttributes() - 1);
			return dataset;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void saveDataset(Instances dataset, String path) {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataset);

		try {
			saver.setFile(new File(resolvePath(path)));
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object loadModel(String path) {
		try {
			return SerializationHelper.read(resolvePath(path));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void saveModel(Classifier model, String path) {
		try {
			SerializationHelper.write(resolvePath(path), model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveResults(Instances set, String path) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(resolvePath(path), "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return;
		}

		String[] classes = listClasses(set);

		for (int i = 0; i < set.numInstances(); i++) {
			try {
				int classIndex = (int) set.instance(i).classValue();
				writer.println(classes[classIndex]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		writer.close();
	}

	public void classify(Instances set, Classifier model) {
		for (int i = 0; i < set.numInstances(); i++) {
			try {
				Instance inst = set.instance(i);
				double pred = model.classifyInstance(inst);
				inst.setClassValue(pred);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
