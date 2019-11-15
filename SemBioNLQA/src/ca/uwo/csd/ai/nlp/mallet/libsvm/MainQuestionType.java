package ca.uwo.csd.ai.nlp.mallet.libsvm;

import ca.uwo.csd.ai.nlp.kernel.CompositeKernel;
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
import ca.uwo.csd.ai.nlp.kernel.RBFKernel;
import ca.uwo.csd.ai.nlp.kernel.TreeKernel;
import ca.uwo.csd.ai.nlp.libsvm.ex.SVMPredictor;
import cc.mallet.classify.Classification;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.types.Instance;
import cc.mallet.types.Label;
import cc.mallet.types.LabelAlphabet;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 
 * @author Syeed Ibn Faiz
 */
public class MainQuestionType {

    public static void main(String[] args) throws IOException, Exception {
        ArrayList<Pipe> pipes = new ArrayList<Pipe>();Pattern tokenPattern = Pattern.compile("[\\p{L}\\p{N}_]+");
       //pipes.add(  new CharSequence2TokenSequence(tokenPattern));
        pipes.add(new Target2Label());
       // pipes.add(new TokenSequenceRemoveStopwords(true, true) );
        pipes.add(new CharSequence2TokenSequence(tokenPattern));
        pipes.add(new TokenSequence2FeatureSequence());
         pipes.add(new FeatureSequence2FeatureVector());
        
        SerialPipes pipe = new SerialPipes(pipes);
 DecimalFormat df = new DecimalFormat("0.0000");
        //prepare training instances
        InstanceList trainingInstanceList = new InstanceList(pipe);
        trainingInstanceList.addThruPipe(new CsvIterator(new FileReader("E:\\these\\Nouveau dossier\\Mallet-LibSVM\\src\\QuestionType\\training.txt"),
                "(.*)\t(.*)", 2, 1, -1));

        //prepare test instances
        InstanceList testingInstanceList = new InstanceList(pipe);
        testingInstanceList.addThruPipe(new CsvIterator(new FileReader("E:\\these\\Nouveau dossier\\Mallet-LibSVM\\src\\QuestionType\\test.txt"),
                "(.*)\t(.*)", 2, 1, -1));

        ClassifierTrainer trainer = new SVMClassifierTrainer(new LinearKernel());
        //LinearKernel()
        Classifier classifier = trainer.train(trainingInstanceList);
        
        System.out.println("Accuracy: " + classifier.getAccuracy(testingInstanceList));
        classifier.getAverageRank(testingInstanceList);
        System.out.println("YES/NO:"+" P:"+df.format(classifier.getPrecision(testingInstanceList, "yesno"))
                +" R:"+df.format(classifier.getRecall(testingInstanceList, "yesno"))
                +" F:"+df.format(classifier.getF1(testingInstanceList, "yesno")));
        
        System.out.println("Factoid:"
        +" P:"+df.format(classifier.getPrecision(testingInstanceList, "factoid"))
                +" R:"+df.format(classifier.getRecall(testingInstanceList, "factoid"))
                +" F:"+df.format(classifier.getF1(testingInstanceList, "factoid")));
       
        System.out.println("List:"+" P"+df.format(classifier.getPrecision(testingInstanceList, "list"))
                +" R"+df.format(classifier.getRecall(testingInstanceList, "list"))
                +" F:"+df.format(classifier.getF1(testingInstanceList, "list")));
        
        System.out.println("Summary:"+" P:"+df.format(classifier.getPrecision(testingInstanceList, "summary"))
                +" R:"+df.format(classifier.getRecall(testingInstanceList, "summary"))
                +" F:"+df.format(classifier.getF1(testingInstanceList, "summary")));
        
        double AP= (classifier.getPrecision(testingInstanceList, "yesno")+
                classifier.getPrecision(testingInstanceList, "factoid")+
                classifier.getPrecision(testingInstanceList, "list")+
                classifier.getPrecision(testingInstanceList, "summary"))/4;
        
        double AR=(classifier.getRecall(testingInstanceList, "yesno")+
                classifier.getRecall(testingInstanceList, "factoid")+
                classifier.getRecall(testingInstanceList, "list")+
                classifier.getRecall(testingInstanceList, "summary"))/4;
        double AF=(classifier.getF1(testingInstanceList, "yesno")+
                classifier.getF1(testingInstanceList, "factoid")+
                classifier.getF1(testingInstanceList, "list")+
                classifier.getF1(testingInstanceList, "summary"))/4;
        System.out.println();
        System.out.println("Average Precison:   "+df.format(AP));
        System.out.println("Average Recall:   "+df.format(AR));
        System.out.println("Average F-measure:   "+df.format(AF));
    }
}
