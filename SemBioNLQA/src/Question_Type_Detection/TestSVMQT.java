
package Question_Type_Detection;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import rita.RiWordNet;


public class TestSVMQT {

    public static void main(String[] args) throws IOException, ParseException {
       
        JSONParser parser = new JSONParser(); 
        MaxentTagger tagger = new MaxentTagger("E:\\these\\corpus d evaluation et api\\library\\stanford-postagger-full-2015-01-30\\models\\english-left3words-distsim.tagger");
        File Test=new File("E:\\these\\Nouveau dossier\\Mallet-LibSVM\\src\\QuestionType\\test.txt");
                //File Test=new File("E:\\these\\Nouveau dossier\\Mallet-LibSVM\\src\\QuestionType\\test.txt");
        FileWriter writetraining=new FileWriter(Test);
        //E:\\these\\corpus d evaluation et api\\bioASK\\questions\\phaseA_2b_01.json
        Object obj = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASK\\BioAsk 3\\Task 3b-Phase A\\BioASQ-task3bPhaseA-testset1"));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray questions=(JSONArray) jsonObject.get("questions");
          String ListWords[]={"role","definition","effect","mechanism",
            "name","mode","incidence","indication","frequency","prevalence","inheritance","inheritances"
        ,"value","treatment","number","action","aim","outcomes","raison","meaning",
        "role","definition","effect","mechanism","","form","characteristic","target","symptom"
       ,"names","modes","incidences","indications","frequency","prevalences"
        ,"values","treatments","numbers","actions","aims","outcomes","raisons","","characteristics"
          ,"advantage","classes","classe","factors","method","features","association"
                  ,"proportion","observations","known","main","meaning","function","principle"
          };//meaning 
            
           //"role","definition","effect","mechanism",
//            "name","mode","incidence","indication","frequency","prevalence","inheritance","inheritances"
//        ,"value","treatment","number","action","aim","outcomes","raison","meaning",
//        "roles","definitions","effects","mechanisms","","forms","characteristics","target","symptom"
//       ,"names","modes","incidences","indications","frequency","prevalences"
//        ,"values","treatments","numbers","actions","aims","outcomes","raisons","","characteristics"
//        // ,"advantage","classes","classe","factors","method","features",//"association"
//         ,"known",""
//                //  ,"proportion","observations","known","main","meaning","function","principle"
//          };//meaning 

                 RiWordNet wordnet= new RiWordNet("E:\\these\\corpus d evaluation et api\\library\\WordNet-3.0\\");
for(int i=0;i<questions.size();i++){
                
            JSONObject question=(JSONObject) questions.get(i);
            String tagged = tagger.tagString((String) question.get("body"));
            String Q="";
            String[] tagged2 = tagged.split(" ");
            String firsttag[]=tagged2[0].split("_");
            Q=firsttag[0]+" ";
//            for (int j = 0; j < tagged2.length; j++) {
//            String firsttag[]=tagged2[j].split("_");
//            Q=firsttag[0]+" "+firsttag[0]+" ";
//            }
//            
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
            writetraining.write(question.get("type")+"\t"+Q);
            writetraining.write("\n");
            }
           
            
            writetraining.close();
           
        
        
        
    }
    
}
