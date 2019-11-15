
package Question_Type_Detection;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rita.RiWordNet;
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
//import NavigationBean.CustomKernel;

import ca.uwo.csd.ai.nlp.libsvm.ex.SVMPredictor;
import ca.uwo.csd.ai.nlp.mallet.libsvm.SVMClassifierTrainer;
import cc.mallet.classify.Classification;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.types.Instance;
import cc.mallet.types.Label;
import cc.mallet.types.LabelAlphabet;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author sarrouti
 */
public class QuestionTypeDetection {
   MaxentTagger tagger;
  RiWordNet wordnet;
  String Questiontype;
    public QuestionTypeDetection() {
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();    
	    String path=request.getRealPath("WEB-INF/resources/models/english-caseless-left3words-distsim.tagger");
        tagger = new MaxentTagger(path);
	    String pathwordnet=request.getRealPath("WEB-INF/resources/WordNet-3.0/");

        wordnet= new RiWordNet(pathwordnet);//E:\\these\\corpus d evaluation et api\\library\\WordNet-3.0\\
    }
    
    public String getQuestiontype(String q) throws IOException {
    	
      //  File Test=new File("E:\\these\\Nouveau dossier\\Mallet-LibSVM\\src\\QuestionType\\test_1.txt");
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();    
	    String path3=request.getRealPath("WEB-INF/resources/files/test_1.txt");
        File Test=new File(path3);
 
	    
        FileWriter writetraining=new FileWriter(Test);
         String ListWords[]={"role","definition","effect","mechanism",
        "name","mode","incidence","indication","frequency","prevalence","inheritance","inheritances"
        ,"value","treatment","number","action","aim","outcomes","raison","meaning",
        "role","definition","effect","mechanism","","form","characteristic","target","symptom"
        ,"names","modes","incidences","indications","frequency","prevalences"
        ,"values","treatments","numbers","actions","aims","outcomes","raisons","","characteristics"
        ,"advantage","classes","classe","factors","method","features","association"
        ,"proportion","observations","known","main","meaning","function","principle"
          };//meaning 
         
            String tagged = tagger.tagString(q);
            String Q="";
            String[] tagged2 = tagged.split(" ");
            String firsttag[]=tagged2[0].split("_");
            Q=firsttag[0]+" ";
        if(firsttag[0].toLowerCase().equals("what")||firsttag[0].toLowerCase().equals("which")){
                for(int jj=1;jj<tagged2.length;jj++){//tagged2.length
            String Tag1 []=tagged2[jj].split("_");
           for(int ii=0;ii<ListWords.length;ii++){
               String syns[] = wordnet.getAllSynonyms(ListWords[ii], "n");
                 List<String> wordList =new ArrayList<String>();
                 for(int k=0;k<syns.length;k++){
                     wordList.add(syns[k].toLowerCase());
                 }
                  
            if((Tag1[0].toLowerCase().equals(ListWords[ii]))){
//                           
                        //&&(Tag1[1].equals("DT")==false)
           if((Tag1[1].equals("-LRB-")==false)&&(Tag1[1].equals("-RRB-")==false)&&(Tag1[1].equals("IN")==false)){
               String Tag []=tagged2[jj-1].split("_"); 
              String TagA []=tagged2[jj+1].split("_"); 
                Q+= Tag[0]+" ";
               Q+= Tag1[0]+" ";
             //  Q+= TagA[0]+" ";
            for(int k=0;k<wordList.size();k++)
            {
                Q+=wordList.get(k)+" ";
            }
               //Q+= TagA[0]+" ";
               break;
            }
            }
else if(wordList.contains(Tag1[0].toLowerCase())){
                if((Tag1[1].equals("-LRB-")==false)&&(Tag1[1].equals("-RRB-")==false)&&(Tag1[1].equals("DT")==false)&&(Tag1[1].equals("IN")==false)){
               String Tag []=tagged2[jj-1].split("_"); 
//               String TagA []=tagged2[jj+1].split("_"); 
                Q+= Tag[0]+" ";
               Q+= Tag1[0]+" ";
  //             Q+= TagA[0]+" ";
               break;
            }
            }                
            
            }
         
               if((Tag1[1].equals("-LRB-")==false)&&(Tag1[1].equals("-RRB-")==false)&&(Tag1[1].equals("DT")==false)&&(Tag1[1].equals("IN")==false)){
                Q+= Tag1[1]+" ";
                           
            }
            }}
             else if(firsttag[0].toLowerCase().equals("how")){
                for(int jj=1;jj<2;jj++){
            String Tag1 []=tagged2[jj].split("_");
            if((Tag1[1].equals("-LRB-")==false)&&(Tag1[1].equals("-RRB-")==false)&&(Tag1[1].equals("DT")==false)&&(Tag1[1].equals("IN")==false)){

                Q+= Tag1[1]+" ";
                           }
            }}
             else if(firsttag[0].toLowerCase().equals("why")||firsttag[0].toLowerCase().equals("describe")
                    ||firsttag[0].toLowerCase().equals("list")||firsttag[0].toLowerCase().equals("Explain")
                      ||firsttag[0].toLowerCase().equals("define")
                     ){
                for(int jj=1;jj<2;jj++){
            String Tag1 []=tagged2[jj].split("_");
            if((Tag1[1].equals("-LRB-")==false)&&(Tag1[1].equals("-RRB-")==false)&&(Tag1[1].equals("DT")==false)&&(Tag1[1].equals("IN")==false)){

               //Q+= Tag1[1]+" ";
                           }
            }}
              else if(firsttag[0].toLowerCase().equals("is")
                    ||firsttag[0].toLowerCase().equals("am")
                    ||firsttag[0].toLowerCase().equals("was")
                    ||firsttag[0].toLowerCase().equals("are")
                    ||firsttag[0].toLowerCase().equals("been")
                    ||firsttag[0].toLowerCase().equals("being")
                    ||firsttag[0].toLowerCase().equals("were")
                    ||firsttag[0].toLowerCase().equals("could")
                    ||firsttag[0].toLowerCase().equals("can")
                    ||firsttag[0].toLowerCase().equals("shall")
                    ||firsttag[0].toLowerCase().equals("should")
                    ||firsttag[0].toLowerCase().equals("will")
                    ||firsttag[0].toLowerCase().equals("would")
                    ||firsttag[0].toLowerCase().equals("may")
                    ||firsttag[0].toLowerCase().equals("might")
                    ||firsttag[0].toLowerCase().equals("do")
                    ||firsttag[0].toLowerCase().equals("did")
                    ||firsttag[0].toLowerCase().equals("does")
                    ||firsttag[0].toLowerCase().equals("have")
                    ||firsttag[0].toLowerCase().equals("has")
                    ||firsttag[0].toLowerCase().equals("had")){
                
            }
            //}
            else{
            for(int jj=1;jj<tagged2.length;jj++){
            String Tag1 []=tagged2[jj].split("_");
          
            if((Tag1[1].equals("-LRB-")==false)&&(Tag1[1].equals("-RRB-")==false)&&(Tag1[1].equals("DT")==false)&&(Tag1[1].equals(".")==false)){//&&(Tag1[1].equals("DT")==false)
                Q+= Tag1[1]+" ";
                
            }
            }
            }
            writetraining.write("factoid"+"\t"+Q);
            writetraining.write("\n");
            writetraining.close();
        ArrayList<Pipe> pipes = new ArrayList<Pipe>();Pattern tokenPattern = Pattern.compile("[\\p{L}\\p{N}_]+");
        pipes.add(new Target2Label());
        pipes.add(new CharSequence2TokenSequence(tokenPattern));
        pipes.add(new TokenSequence2FeatureSequence());
         pipes.add(new FeatureSequence2FeatureVector());
        
        SerialPipes pipe = new SerialPipes(pipes);
        DecimalFormat df = new DecimalFormat("0.0000");
        //prepare training instances
        InstanceList trainingInstanceList = new InstanceList(pipe);
        
        //HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();    
	    String path1=request.getRealPath("WEB-INF/resources/files/test_1.txt");
	    String path2=request.getRealPath("WEB-INF/resources/files/training.txt");
        trainingInstanceList.addThruPipe(new CsvIterator(new FileReader(path2),
                "(.*)\t(.*)", 2, 1, -1));//E:\\these\\Nouveau dossier\\Mallet-LibSVM\\src\\QuestionType\\training.txt

        //prepare test instances
        InstanceList testingInstanceList = new InstanceList(pipe);
        testingInstanceList.addThruPipe(new CsvIterator(new FileReader(path1),
                "(.*)\t(.*)", 2, 1, -1));//E:\\these\\Nouveau dossier\\Mallet-LibSVM\\src\\QuestionType\\test_1.txt

        ClassifierTrainer trainer = new SVMClassifierTrainer(new LinearKernel());
        //LinearKernel()
        Classifier classifier = trainer.train(trainingInstanceList);
        
        
        Questiontype= classifier.classify(testingInstanceList.get(0)).getLabeling().getBestLabel().toString();
        return Questiontype;
    }
    
    
}
