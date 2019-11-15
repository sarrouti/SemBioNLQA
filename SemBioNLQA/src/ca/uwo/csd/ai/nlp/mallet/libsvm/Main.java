package ca.uwo.csd.ai.nlp.mallet.libsvm;

import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.FeatureSequence2FeatureVector;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.types.InstanceList;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import ca.uwo.csd.ai.nlp.kernel.LinearKernel;
import cc.mallet.classify.Classification;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.types.Instance;
import cc.mallet.types.Label;
import cc.mallet.types.LabelAlphabet;
import java.util.regex.Pattern;

/**
 * 
 * @author Syeed Ibn Faiz
 */
public class Main {

    public static void main(String[] args) throws IOException, Exception {
        ArrayList<Pipe> pipes = new ArrayList<Pipe>();Pattern tokenPattern = Pattern.compile("[\\p{L}\\p{N}_]+");
       //pipes.add(  new CharSequence2TokenSequence(tokenPattern));
        pipes.add(new Target2Label());
       // pipes.add(new TokenSequenceRemoveStopwords(true, true) );
        pipes.add(new CharSequence2TokenSequence(tokenPattern));
        pipes.add(new TokenSequence2FeatureSequence());
         pipes.add(new FeatureSequence2FeatureVector());
        
        SerialPipes pipe = new SerialPipes(pipes);

        //prepare training instances
        InstanceList trainingInstanceList = new InstanceList(pipe);
        trainingInstanceList.addThruPipe(new CsvIterator(new FileReader("E:\\these\\Nouveau dossier\\Question classification\\src\\ClinicalQuestions\\ClinicalTrainingsvm.txt"),
                "(.*)\t(.*)", 2, 1, -1));

        //prepare test instances
        InstanceList testingInstanceList = new InstanceList(pipe);
        testingInstanceList.addThruPipe(new CsvIterator(new FileReader("E:\\these\\Nouveau dossier\\Question classification\\src\\ClinicalQuestions\\ClinicalTestgsvm.txt"),
                "(.*)\t(.*)", 2, 1, -1));

        ClassifierTrainer trainer = new SVMClassifierTrainer(new LinearKernel());
        Classifier classifier = trainer.train(trainingInstanceList);
//        System.out.println("Accuracy: " + classifier.getAccuracy(testingInstanceList));

      System.out.println(trainingInstanceList.get(0).getData());
//        (classifier.getRecall(testingInstanceList, "Pharmacological"));
//        System.out.println(classifier.getPrecision(testingInstanceList, "Pharmacological"));
//        System.out.println(classifier.getRecall(testingInstanceList, "Treatment & Prevention"));
//        System.out.println(classifier.getPrecision(testingInstanceList, "Treatment & Prevention"));

//        System.out.println(classifier.getRecall(testingInstanceList, "Pharmacological"));
//        System.out.println(classifier.getPrecision(testingInstanceList, "Pharmacological"));
//        System.out.println(classifier.getRecall(testingInstanceList, "Treatment & Prevention"));
//        System.out.println(classifier.getPrecision(testingInstanceList, "Treatment & Prevention"));

//      double RDevice=classifier.getRecall(testingInstanceList, "Device");
  //    double PDevice=classifier.getPrecision(testingInstanceList, "Device");
    //  System.out.println(RDevice+" "+PDevice);
      //System.out.println("Fscore Device: "+(2*RDevice*PDevice/(RDevice+PDevice)));
    ArrayList reList=  classifier.classify(testingInstanceList);
    int correct=0;
    int nn=0;
    int bbb=0;
    String topic="Treatment & Prevention";
    
       for (int i = 0; i < reList.size(); i++) {
      Classification c= (Classification) reList.get(i);
      System.out.println(c.getLabeling().getBestLabel());
          Instance ins=c.getInstance();
      System.out.println(testingInstanceList.get(i).getData().equals(ins.getData()));

    if(testingInstanceList.get(i).getData().equals(ins.getData())==true&&testingInstanceList.get(i).getLabeling().toString().equals(topic)==true && c.getLabeling().getBestLabel().toString().equals(topic)==true )
       {
         correct++;
       }
    if(testingInstanceList.get(i).getLabeling().toString().equals(topic)==true){
        nn++;
      }
    //if(testingInstanceList.get(i).getData().equals(ins.getData())==true&&testingInstanceList.get(i).getLabeling().toString().equals(topic)==false && c.getLabeling().getBestLabel().toString().equals(topic)==true )
     if(c.getLabeling().getBestLabel().toString().equals(topic)==true){
      bbb++;
     } 
    
        }
       
       double r=(double)correct/(double)nn;
       double p=(double)correct/(double)bbb;
    
   System.out.println((2*r*p)/(p+r));
      
      
     System.out.println(correct+" "+nn+" "+bbb);   
      
      
      
//      double RPharmacological=classifier.getRecall(testingInstanceList, "Pharmacological");
//      double PPharmacological=classifier.getPrecision(testingInstanceList, "Pharmacological");
//      System.out.println("Fscore Pharmacological: "+(2*RPharmacological*PPharmacological/(RPharmacological+PPharmacological)));
//      
//      double RTreatmentPrevention=classifier.getRecall(testingInstanceList, "Treatment & Prevention");
//      double PTreatmentPrevention=classifier.getPrecision(testingInstanceList, "Treatment & Prevention");
//      System.out.println("Fscore Treatment & Prevention: "+(2*RTreatmentPrevention*PTreatmentPrevention/(RTreatmentPrevention+PTreatmentPrevention)));
//    
//      double RManagement=classifier.getRecall(testingInstanceList, "Management");
//      double PManagement=classifier.getPrecision(testingInstanceList, "Management");
//      System.out.println("Fscore Management: "+(2*RManagement*PManagement/(RManagement+PManagement)));
//      
//              
//              double RPhysicalFinding=classifier.getRecall(testingInstanceList, "Physical Finding");
//      double PPhysicalFinding=classifier.getPrecision(testingInstanceList, "Physical Finding");
//      System.out.println("Fscore Physical Finding: "+(2*RPhysicalFinding*PPhysicalFinding/(RPhysicalFinding+PPhysicalFinding)));
//      
//      
//      double REtiology=classifier.getRecall(testingInstanceList, "Etiology");
//      double PEtiology=classifier.getPrecision(testingInstanceList, "Etiology");
//      System.out.println("Fscore Etiology: "+(2*REtiology*PEtiology/(REtiology+PEtiology)));
//      
//      double RHistory=classifier.getRecall(testingInstanceList, "History");
//      double PHistory=classifier.getPrecision(testingInstanceList, "History");
//      System.out.println("Fscore History: "+(2*RHistory*PHistory/(RHistory+PHistory)));
//     
//      double RTest=classifier.getRecall(testingInstanceList, "Test");
//      double PTest=classifier.getPrecision(testingInstanceList, "Test");
//      System.out.println("Fscore Test: "+(2*RTest*PTest/(RTest+PTest)));
//     
//      
//      double RPrognosis=classifier.getRecall(testingInstanceList, "Prognosis");
//      double PPrognosis=classifier.getPrecision(testingInstanceList, "Prognosis");
//      System.out.println("Fscore Prognosis: "+(2*RPrognosis*PPrognosis/(RPrognosis+PPrognosis)));      
    }
}
