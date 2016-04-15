import weka.classifiers.trees.J48;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.Filter;
import weka.attributeSelection.*;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

import java.util.ArrayList;
import java.util.Random;


public class selection_variables {	

	public static Instances chargeDonnees(String path) throws Exception {		
		DataSource source = new DataSource(path);
		Instances data = source.getDataSet();
		if (data.classIndex() == -1)
			   data.setClassIndex(data.numAttributes() - 1);
		return data;
	}


	protected static ArrayList<Instances> filtres(Instances data) throws Exception{
		
		ArrayList<Instances> datas = new ArrayList<Instances> ();
		
		AttributeSelection filter11 = new AttributeSelection(); 	 //Creation d'un nouvel attribut de selection	
		AttributeSelection filter12 = new AttributeSelection();
		AttributeSelection filter13 = new AttributeSelection();
		AttributeSelection filter14 = new AttributeSelection();
		AttributeSelection filter15 = new AttributeSelection();
		AttributeSelection filter16 = new AttributeSelection();
		AttributeSelection filter17 = new AttributeSelection();
		AttributeSelection filter18 = new AttributeSelection();
		AttributeSelection filter21 = new AttributeSelection();
		AttributeSelection filter22 = new AttributeSelection();
		AttributeSelection filter23 = new AttributeSelection();
		
		CfsSubsetEval eval = new CfsSubsetEval(); 
		eval.setOptions(new String[] {"-P", "1", "-E", "1"});
		
		GreedyStepwise search = new GreedyStepwise();
		search.setSearchBackwards(true);		
		String[][] GreedyOpt = 
			{ {"-R", "-T", "0.055", "-N", "-1"}, {"-R", "-T", "0.0525", "-N", "-1"}, 
			{"-R", "-T", "0.050", "-N", "-1"}, {"-R", "-T", "0.0475", "-N", "-1"},
			{"-R", "-T", "0.045", "-N", "-1"}, {"-R", "-T", "0.04", "-N", "-1"},
			{"-R", "-T", "0.035", "-N", "-1"}, {"-R", "-T", "0.03", "-N", "-1"}};
		search.setOptions(GreedyOpt[0]);
		
		BestFirst search2 = new BestFirst();
		String[][] BestOpt = 
			{ {"-D", "1", "-N", "5"}, {"-D", "2", "-N", "5"}, {"-D", "0", "-N", "5"} };
		search2.setOptions(BestOpt[0]);
		
		filter11.setInputFormat(data);
		filter12.setInputFormat(data);
		filter13.setInputFormat(data);
		filter14.setInputFormat(data);
		filter15.setInputFormat(data);
		filter16.setInputFormat(data);
		filter17.setInputFormat(data);
		filter18.setInputFormat(data);
		filter21.setInputFormat(data);
		filter22.setInputFormat(data);
		filter23.setInputFormat(data);
		
		filter11.setEvaluator(eval);
		filter11.setSearch(search);
		Instances newData = Filter.useFilter(data, filter11);
		datas.add(newData);
		
		search.setOptions(GreedyOpt[1]);
		filter17.setEvaluator(eval);
		filter17.setSearch(search);
		newData = Filter.useFilter(data, filter17);
		datas.add(newData);
		
		search.setOptions(GreedyOpt[2]);
		filter12.setEvaluator(eval);
		filter12.setSearch(search);
		newData = Filter.useFilter(data, filter12);
		datas.add(newData);
		
		search.setOptions(GreedyOpt[3]);
		filter18.setEvaluator(eval);
		filter18.setSearch(search);
		newData = Filter.useFilter(data, filter18);
		datas.add(newData);
		
		search.setOptions(GreedyOpt[4]);
		filter13.setEvaluator(eval);
		filter13.setSearch(search);
		newData = Filter.useFilter(data, filter13);
		datas.add(newData);
		
		search.setOptions(GreedyOpt[5]);
		filter16.setEvaluator(eval);
		filter16.setSearch(search);
		newData = Filter.useFilter(data, filter16);
		datas.add(newData);
		
		search.setOptions(GreedyOpt[6]);
		filter14.setEvaluator(eval);
		filter14.setSearch(search);
		newData = Filter.useFilter(data, filter14);
		datas.add(newData);
		
		search.setOptions(GreedyOpt[7]);
		filter15.setEvaluator(eval);
		filter15.setSearch(search);
		newData = Filter.useFilter(data, filter15);
		datas.add(newData);
		
		

		filter21.setEvaluator(eval);
		filter21.setSearch(search);
		newData = Filter.useFilter(data, filter21);
		datas.add(newData);
		
		search2.setOptions(BestOpt[1]);
		filter22.setEvaluator(eval);
		filter22.setSearch(search);
		newData = Filter.useFilter(data, filter22);
		datas.add(newData);
		
		search2.setOptions(BestOpt[2]);
		filter23.setEvaluator(eval);
		filter23.setSearch(search);
		newData = Filter.useFilter(data, filter23);
		datas.add(newData);
		
		return datas;
	}

	public static ArrayList<Instances> instTest (ArrayList<Instances> datas, Instances data){
		ArrayList<Instances> instTest = new ArrayList<Instances> ();
		for(int i = 0; i < datas.size(); i++){
			Instances test = new Instances(data);
			for(int j = 0; j < 45-datas.get(i).numAttributes(); j++){
				int numAtt = (int)(Math.random()*(test.numAttributes()-1));
				test.deleteAttributeAt(numAtt);
			}
			instTest.add(test);
		}
		return instTest;
	}

// Exemple permettant de comprendre un peu la classe individuellement 
 
	public static void main(String[] args) throws Exception {
		
		System.out.println("Loading data");		
		Instances data = chargeDonnees(System.getProperty("user.dir")+"/data_arff/train_set.arff");
		Instances testSet = chargeDonnees(System.getProperty("user.dir")+"/data_arff/test_set.arff");
		System.out.println("Filtrage des attributs");
		ArrayList<Instances> datas = filtres(data);
		
		System.out.println(datas.size());
		Classifier cls = new weka.classifiers.trees.J48();
		/*cls.buildClassifier(data);
		System.out.println(cls);
		 Evaluation eval = new Evaluation(data);
		 Random rand = new Random(1);  // using seed = 1
		 int folds = 10;
		 eval.crossValidateModel(cls, data, folds, rand);
		 System.out.println(eval.toSummaryString());*/
		((J48) cls).setOptions(new String[]{"-C", "0.25", "-M", "2"});
		for(int i = 0; i < datas.size(); i++){
			cls.buildClassifier(datas.get(i));
			Instances dataSet = data;
			for(int j = 0; j < datas.get(i).numInstances(); j++){
				cls.classifyInstance(datas.get(i).instance(j));
			}
			Evaluation eval2 = new Evaluation(dataSet);
			eval2.crossValidateModel(cls, dataSet, 10, new Random(1));
			System.out.println(eval2.toSummaryString());
			System.out.println(eval2.toClassDetailsString());
			/*System.out.println("datas.get("+i+").numAttributes() = "+datas.get(i).numAttributes());
			for(int j = 0; j < datas.get(i).numAttributes(); j++)
				System.out.print(datas.get(i).attribute(j)+", ");
			System.out.println();*/
		}
		System.out.println("Fin");
		
		/*ArrayList<Instances> instaTest = instTest(datas, data);
		System.out.println(instaTest.size());
		for(int i = 0; i < instaTest.size(); i++){
			System.out.println("instaTest.get("+i+").numAttributes() = "+instaTest.get(i).numAttributes());
		}
		System.out.println("Fin");*/
		
	}
}
