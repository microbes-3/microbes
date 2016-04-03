import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.Filter;
import weka.attributeSelection.*;


import java.util.ArrayList;


public class selection_variables {	
	
	public static ArrayList<Instances> debut(){
		return new ArrayList<Instances> ();
	}

	public static Instances chargeDonnees(String path) throws Exception {		
		DataSource source = new DataSource(path);
		Instances data = source.getDataSet();
		if (data.classIndex() == -1)
			   data.setClassIndex(data.numAttributes() - 1);
		return data;
	}


	protected static void filtres(Instances data, ArrayList<Instances> datas) throws Exception{
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
		search.setOptions(new String[] {"-R", "-T", "0.055", "-N", "-1"});

		BestFirst search2 = new BestFirst();
		search2.setOptions(new String[] {"-D", "1", "-N", "5"});
		
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
		saveData(newData, datas);
		
		search.setOptions(new String[] {"-R", "-T", "0.0525", "-N", "-1"});
		filter17.setEvaluator(eval);
		filter17.setSearch(search);
		newData = Filter.useFilter(data, filter17);
		saveData(newData, datas);
		
		search.setOptions(new String[] {"-R", "-T", "0.050", "-N", "-1"});
		filter12.setEvaluator(eval);
		filter12.setSearch(search);
		newData = Filter.useFilter(data, filter12);
		saveData(newData, datas);
		
		search.setOptions(new String[] {"-R", "-T", "0.0475", "-N", "-1"});
		filter18.setEvaluator(eval);
		filter18.setSearch(search);
		newData = Filter.useFilter(data, filter18);
		saveData(newData, datas);
		
		search.setOptions(new String[] {"-R", "-T", "0.045", "-N", "-1"});
		filter13.setEvaluator(eval);
		filter13.setSearch(search);
		newData = Filter.useFilter(data, filter13);
		saveData(newData, datas);
		
		search.setOptions(new String[] {"-R", "-T", "0.04", "-N", "-1"});
		filter16.setEvaluator(eval);
		filter16.setSearch(search);
		newData = Filter.useFilter(data, filter16);
		saveData(newData, datas);
		
		search.setOptions(new String[] {"-R", "-T", "0.035", "-N", "-1"});
		filter14.setEvaluator(eval);
		filter14.setSearch(search);
		newData = Filter.useFilter(data, filter14);
		saveData(newData, datas);
		
		search.setOptions(new String[] {"-R", "-T", "0.03", "-N", "-1"});
		filter15.setEvaluator(eval);
		filter15.setSearch(search);
		newData = Filter.useFilter(data, filter15);
		saveData(newData, datas);
		
		

		filter21.setEvaluator(eval);
		filter21.setSearch(search);
		newData = Filter.useFilter(data, filter21);
		saveData(newData, datas);
		
		search2.setOptions(new String[] {"-D", "2", "-N", "5"});
		filter22.setEvaluator(eval);
		filter22.setSearch(search);
		newData = Filter.useFilter(data, filter22);
		saveData(newData, datas);
		
		search2.setOptions(new String[] {"-D", "0", "-N", "5"});
		filter23.setEvaluator(eval);
		filter23.setSearch(search);
		newData = Filter.useFilter(data, filter23);
		saveData(newData, datas);
		
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

	public static void saveData(Instances data, ArrayList<Instances> datas){
		datas.add(data);
	}

/** Exemple permettant de comprendre un peu la classe individuellement 
 * 
	public static void main(String[] args) throws Exception {
		ArrayList<Instances> datas = debut();
		
		System.out.println("Loading data");		
		Instances data = chargeDonnees(System.getProperty("user.dir")+"/data_arff/train_set.arff");
		
		System.out.println("Filtrage des attributs");
		filtres(data, datas);
		
		System.out.println(datas.size());
		for(int i = 0; i < datas.size(); i++){
			System.out.println("datas.get("+i+").numAttributes() = "+datas.get(i).numAttributes());
			for(int j = 0; j < datas.get(i).numAttributes(); j++)
				System.out.print(datas.get(i).attribute(j)+", ");
			System.out.println();
		}
		System.out.println("Fin");
		
		ArrayList<Instances> instaTest = instTest(datas, data);
		System.out.println(instaTest.size());
		for(int i = 0; i < instaTest.size(); i++){
			System.out.println("instaTest.get("+i+").numAttributes() = "+instaTest.get(i).numAttributes());
		}
		System.out.println("Fin");
		
	}*/
}
