
package Clasificador;

import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;


public class Arbol {
    private Vista vs;
    	public static String DatosEntrenamiento="sb1-T.arff";
	public static  String DatosPrueba="sb1-P.arff";
    
    public Arbol(Vista vs, String nombreT, String nombreP){
        this.vs=vs;
        this.DatosEntrenamiento=nombreT+".arff";
        this.DatosPrueba=nombreP+".arff";
    }

        
        public static Instances getDatos(String fileName) throws IOException {
		
		ArffLoader datos = new ArffLoader();
		
		datos.setSource(Arbol.class.getResourceAsStream(fileName));
		
		Instances dataSet = datos.getDataSet();
		
		dataSet.setClassIndex(dataSet.numAttributes() - 1);
		
                return dataSet;
	}
                
    public void Ejecuta() throws IOException, Exception           {
        
                Instances trainingDataSet = getDatos(DatosEntrenamiento);
		Instances testingDataSet = getDatos(DatosPrueba);
		
		System.out.println("************************** J48 *************************");

		J48 classifier = new J48();
                

		classifier.buildClassifier(trainingDataSet);
      
		Evaluation eval = new Evaluation(trainingDataSet);
		eval.evaluateModel(classifier, testingDataSet);

		System.out.println(classifier);
                vs.setText(""+classifier);
                vs.setText("\n\n"+eval.toSummaryString());
                vs.setText("\n\n"+eval.toMatrixString());
                

    }
}
