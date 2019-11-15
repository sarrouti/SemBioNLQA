
package ca.uwo.csd.ai.nlp.mallet.libsvm;


import ca.uwo.csd.ai.nlp.kernel.LinearKernel;
import cc.mallet.classify.Classification;
import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.FeatureSequence2FeatureVector;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import rita.RiWordNet;


public class QuestionClassificationSVM {

    public static void main(String[] args) throws IOException, ParseException {
        
         JSONParser parser = new JSONParser(); 
         Object obj = parser.parse(new FileReader("E:\\\\these\\\\corpus d evaluation et api\\\\bioASK\\\\questions\\\\training\\\\BioASQ-trainingDataset3b.json"));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray questions=(JSONArray) jsonObject.get("questions");
        
//        ArrayList<Pipe> pipes = new ArrayList<Pipe>();Pattern tokenPattern = Pattern.compile("[\\p{L}\\p{N}_]+");
//       //pipes.add(  new CharSequence2TokenSequence(tokenPattern));
//        pipes.add(new Target2Label());
//       // pipes.add(new TokenSequenceRemoveStopwords(true, true) );
//        pipes.add(new CharSequence2TokenSequence(tokenPattern));
//        pipes.add(new TokenSequence2FeatureSequence());
//         pipes.add(new FeatureSequence2FeatureVector());
//        
//        SerialPipes pipe = new SerialPipes(pipes);
//        DecimalFormat df = new DecimalFormat("0.0000");
//        //prepare training instances
//        InstanceList trainingInstanceList = new InstanceList(pipe);
//        trainingInstanceList.addThruPipe(new CsvIterator(new FileReader("E:\\these\\Nouveau dossier\\Mallet-LibSVM\\src\\QuestionType\\training.txt"),
//                "(.*)\t(.*)", 2, 1, -1));
//
//        //prepare test instances
//        InstanceList testingInstanceList = new InstanceList(pipe);
//        testingInstanceList.addThruPipe(new CsvIterator(new FileReader("E:\\these\\Nouveau dossier\\Mallet-LibSVM\\src\\QuestionType\\test.txt"),
//                "(.*)\t(.*)", 2, 1, -1));
//
//        ClassifierTrainer trainer = new SVMClassifierTrainer(new LinearKernel());
//           Classifier classifier = trainer.train(trainingInstanceList);
//        
//       
//        
//       
//        ArrayList Clist= classifier.classify(testingInstanceList);
//            for(int i=0;i<Clist.size();i++){
//                JSONObject question=(JSONObject) questions.get(i);
//           // String tagged = tagger.tagString((String) question.get("body"));
//                 Classification c= (Classification) Clist.get(i);
//      System.out.println(i+1+" "+question.get("type")+"  "+c.getLabeling().getBestLabel());
//          Instance ins=c.getInstance();
//      //System.out.println(testingInstanceList.get(i).getData().equals(ins.getData()));
//            }

            for (int i=0;i<questions.size();i++){
                JSONObject questioni=(JSONObject) questions.get(i);
                for(int j=0;j<questions.size();j++){
                  //  if(j!=i){
                    JSONObject questionj=(JSONObject) questions.get(j);
                     if((questioni.get("body").equals(questionj.get("body"))==true) &&
                         (questioni.get("type").equals(questionj.get("type"))==false)   
                             )
                    {
                        System.out.println(questionj.get("body")+" "+questioni.get("type")+" "+questionj.get("type"));
                    }
                }//}
            }
       
    }
    
}
