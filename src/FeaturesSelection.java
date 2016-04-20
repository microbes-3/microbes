import weka.classifiers.trees.J48;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.Filter;
import weka.attributeSelection.*;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Enumeration;

public class FeaturesSelection {
	protected static ArrayList<Attribute[]> select(Instances data) throws Exception {
		CfsSubsetEval eval = new CfsSubsetEval();
		eval.setOptions(new String[] {"-P", "1", "-E", "1"});

		GreedyStepwise search = new GreedyStepwise();
		search.setSearchBackwards(true);
		String[][] greedyOpt = {
			{"-R", "-T", "0.055", "-N", "-1"},
			{"-R", "-T", "0.0525", "-N", "-1"},
			{"-R", "-T", "0.050", "-N", "-1"},
			{"-R", "-T", "0.0475", "-N", "-1"},
			{"-R", "-T", "0.045", "-N", "-1"},
			{"-R", "-T", "0.04", "-N", "-1"},
			{"-R", "-T", "0.035", "-N", "-1"},
			{"-R", "-T", "0.03", "-N", "-1"}
		};

		BestFirst search2 = new BestFirst();
		String[][] bestOpt = {
			{"-D", "1", "-N", "5"},
			{"-D", "2", "-N", "5"},
			{"-D", "0", "-N", "5"}
		};

		ArrayList<Attribute[]> attributes = new ArrayList<Attribute[]>();
		for (int i = 0; i < greedyOpt.length; i++) {
			AttributeSelection selection = new AttributeSelection();
			selection.setInputFormat(data);

			search.setOptions(greedyOpt[i]);

			selection.setEvaluator(eval);
			selection.setSearch(search);

			for (int j = 0; j < data.numInstances(); j++) {
				selection.input(data.instance(j));
			}
			selection.batchFinished();

			Instances format = selection.getOutputFormat();

			@SuppressWarnings("unchecked")
			Enumeration<Attribute> attrsEnum = format.enumerateAttributes();

			Attribute[] attrs = new Attribute[format.numAttributes()];
			for (int j = 0; attrsEnum.hasMoreElements(); j++) {
				attrs[j] = attrsEnum.nextElement();
			}

			attributes.add(attrs);

			System.out.print(".");
		}
		System.out.println(" done");

		// TODO: MDR pourquoi tu changes search2 et tu utilises encore search tout court ?
		/*filter21.setEvaluator(eval);
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
		datas.add(newData);*/

		return attributes;
	}

	/*public static ArrayList<Instances> instTest(ArrayList<Instances> datas, Instances data){
		ArrayList<Instances> instTest = new ArrayList<Instances> ();
		for(int i = 0; i < datas.size(); i++){
			Instances test = new Instances(data);
			for(int j = 0; j < test.numAttributes()-datas.get(i).numAttributes(); j++){
				int numAtt = (int)(Math.random()*(test.numAttributes()-1));
				test.deleteAttributeAt(numAtt);
			}
			instTest.add(test);
		}
		return instTest;
	}*/

	/**Supprime les attributs qui ont été supprimés par les filtres
	 * dans les instances qu'on va évaluer
	 */

	/*public static ArrayList<Instances> cutInst(Instances inst, ArrayList<Instances> filtres){
		ArrayList<Instances> array = new ArrayList<Instances> ();
		for(int i = 0; i < filtres.size(); i++){
			Instances t = new Instances(inst);
			for(int j = t.numAttributes()-1; j >= 0; j--){
				boolean b = false;
				for(int k = filtres.get(i).numAttributes()-1; k >=0; k--){
					if(filtres.get(i).attribute(k).equals(t.attribute(j)))
						b = true;
				}
				if(!b)
					t.deleteAttributeAt(j);
			}
			array.add(t);
		}
		return array;
	}*/


	/**Juste pour comparer des arrayList d'instances pour voir si les
	 * attributs sont les mêmes
	 */
	/*public static boolean compInst(ArrayList<Instances> i1, ArrayList<Instances> i2){
		if(i1.size() != i2.size())
			return false;
		else{
			for(int j = 0; j < i1.get(0).numAttributes(); j++){
				System.out.println(j);
				System.out.println(i1.get(0).attribute(j).name());
				System.out.println(i2.get(0).attribute(j).name());
			}
			for(int i = 0; i < i1.size(); i++){
				for(int j = 0; j < i1.get(i).numAttributes(); j++){
					if(!i1.get(i).attribute(j).name().equals(i2.get(i).attribute(j).name())){
						System.out.println(i+" "+j);
						System.out.println(i1.get(i).attribute(j).name());
						System.out.println(i2.get(i).attribute(j).name());
						return false;
					}

				}
			}
		}
		return true;
	}*/

	// Exemple permettant de comprendre un peu la classe individuellement

	public static void main(String[] args) throws Exception {

		/*System.out.println("Loading data");
		Instances data = chargeDonnees(System.getProperty("user.dir")+"/data_arff/train_set.arff");
		Instances testSet = chargeDonnees(System.getProperty("user.dir")+"/data_arff/test_set.arff");
		Instances validSet = chargeDonnees(System.getProperty("user.dir")+"/data_arff/valid_set.arff");
		System.out.println("Filtrage des attributs");
		ArrayList<Instances> datas = filtres(data);


		ArrayList<Instances> valid = cutInst(validSet, datas);
		System.out.println(datas.size()+" : ");
		for(int i = 0; i < datas.size(); i++){
			System.out.print(datas.get(i).numAttributes()+" ");
		}
		ArrayList<Classifier> arrCls = new ArrayList<Classifier> ();
		for(int i = 0; i < datas.size(); i++){
			Classifier cls = new J48();
			((J48) cls).setOptions(new String[]{"-C", "0.25", "-M", "2"});
			arrCls.add(cls);
		}
		System.out.println();
		System.out.println(valid.size()+" : ");
		for(int i = 0; i < valid.size(); i++){
			System.out.print(valid.get(i).numAttributes()+" ");
		}
		System.out.println();
		System.out.println(compInst(valid,datas));
		try
		{
			PrintWriter pw = new PrintWriter (new BufferedWriter
					(new FileWriter (new File(System.getProperty("user.dir")+"/resultats3.txt"))));
			/*cls.buildClassifier(data);
		System.out.println(cls);
		 Evaluation eval = new Evaluation(data);
		 Random rand = new Random(1);  // using seed = 1
		 int folds = 10;
		 eval.crossValidateModel(cls, data, folds, rand);
		 System.out.println(eval.toSummaryString());*/
			/*for(int i = 0; i < datas.size(); i++){
				arrCls.get(i).buildClassifier(datas.get(i));
				/*try
				{
					PrintWriter pw1 = new PrintWriter (new BufferedWriter
							(new FileWriter (new File(System.getProperty("user.dir")+"/classifValid"+i+".txt"))));
					for(int j = 0; j < validSet.size(); j++){
						pw1.println(arrCls.get(i).classifyInstance(validSet.get(j)));
					}
					pw1.close();
				}
				catch (IOException exception)
				{
					System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
				}*/
				/*Evaluation eval = new Evaluation(valid.get(i));
				eval.evaluateModel(arrCls.get(i), valid.get(i));
				//eval.crossValidateModel(arrCls.get(i), datas.get(i), 10, new Random(1));
				pw.println(eval.toSummaryString());
				pw.println(eval.toClassDetailsString());
				/*System.out.println("datas.get("+i+").numAttributes() = "+datas.get(i).numAttributes());
			for(int j = 0; j < datas.get(i).numAttributes(); j++)
				System.out.print(datas.get(i).attribute(j)+", ");
			System.out.println();*/
			/*}
			pw.close();
		}
		catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
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
