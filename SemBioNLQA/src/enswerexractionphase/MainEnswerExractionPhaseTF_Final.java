/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enswerexractionphase;

import BioProtail.BioPortailSynonyms;
import Processing.StanfordLemmatizer;
import Processing.Token;
import java.util.ArrayList;
import Models.*;
import Processing.MyFindSimilarTermsCall;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sarrouti
 */
public class MainEnswerExractionPhaseTF_Final {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
           TFIDF tfidf=new TFIDF();
           StanfordLemmatizer slem = new StanfordLemmatizer();
           SentimentWordNet SemtWordnet=new SentimentWordNet();
           MyFindSimilarTermsCall similarTerms=new MyFindSimilarTermsCall();
           BioPortailSynonyms bioPortailSynonyms=new BioPortailSynonyms();
        //Wrtie
           JSONObject jsonObjWrite = new JSONObject();
           FileWriter file = new FileWriter("E:\\these\\corpus d evaluation et api\\bioASQ\\Answer Extration\\BioASQ-4b-2016\\results\\batch5-last-finalGrepMap.json");
           jsonObjWrite.put("username", "sarrouti");
           jsonObjWrite.put("password", "076902107");
           jsonObjWrite.put("system", "sarrouti");
           JSONArray Questions = new JSONArray();
        //End Write
        
        //Read
           JSONParser parser = new JSONParser();
           Object obj = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASQ\\BioASQ 4\\Task 4b-Phase B\\BioASQ-task4bPhaseB-testset1"));
           JSONObject jsonObject = (JSONObject) obj;
           JSONArray questions=(JSONArray) jsonObject.get("questions");
    
           JSONParser parser2 = new JSONParser();
           Object obj2 = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASQ\\Answer Extration\\BioASQ-4b-2016\\DataSetB1Grep.json"));
           JSONObject jsonObject2 = (JSONObject) obj2;
           JSONArray questions2=(JSONArray) jsonObject2.get("questions");
        //End Rend
           for(int i=0;i<questions.size();i++){
         JSONObject question=(JSONObject) questions.get(i);
         //put
         JSONObject objput = new JSONObject();
        
         for (int j = 0; j < questions2.size(); j++) {
             
         JSONObject question2=(JSONObject) questions2.get(j);
         JSONArray Qconcepts=(JSONArray) question2.get("concepts");
         if(question.get("id").equals(question2.get("id"))){
             System.out.println(question.get("id"));
              objput.put("id", question.get("id"));
              JSONArray passages=(JSONArray) question2.get("snippets");
         //YES/NO
         if(question.get("type").equals("yesno")){
               int neg=0;
               int pos=0;
            for (int jj = 0; jj < passages.size(); jj++) {
                double score=0.000;
             JSONObject passage=(JSONObject) passages.get(jj);
             String text=(String) passage.get("text");
             ArrayList<Token> tokenset=(ArrayList<Token>) slem.tokenPOSlemmatize(text);
             for (int k = 0; k < tokenset.size(); k++) {
                 Token w=tokenset.get(k);
                 score+=SemtWordnet.extract(w.getText());
             }
            String output="";
             if(score<0)
             {
                 neg++;
             //output="no";
             }
             else{
                 pos++;
            //output="yes";
             }
             if(score==0){
                 pos++;
             }
             }
            String output="yes";
             if(neg>pos)
             {
                
             output="no";
             }
             else{
                 //pos++;
            output="yes";
             }
            objput.put("exact_answer",output);
            if(passages.size()>=2){
             JSONObject passage2=(JSONObject) passages.get(1);
                
                JSONObject passage=(JSONObject) passages.get(0);
                String text2=(String) passage2.get("text");
                String text=(String) passage.get("text");
                //JSONArray ideal=(JSONArray) question2.get("ideal_answer");
                objput.put("ideal_answer",text+" "+text2);
            }
            else{
                 JSONObject passage=(JSONObject) passages.get(0);
                 objput.put("ideal_answer",passage.get("text"));
            }
           }
         //Factoid
          else if(question.get("type").equals("factoid")){
              if(passages.size()!=0){
               for (int jj = 0; jj < 1; jj++) {
                JSONObject passage=(JSONObject) passages.get(jj);
              // JSONArray ideal=(JSONArray) question2.get("ideal_answer");
                JSONArray concepts=(JSONArray) passage.get("concepts");
                JSONArray abr=(JSONArray) passage.get("abbreviations");
                JSONArray l=new JSONArray();
                //JSONArray l2=new JSONArray();
//                if(concepts.size()==0){                     
//                      l2.add("gene");
//                      l.add(l2);
//                }
                ArrayList<concept> conceptsSort=tfidf.getBestConcepts(passages, (JSONArray) question2.get("concepts"));
                if(conceptsSort.size()>5){
                  for (int k = 0; k < 5; k++) {
                     JSONArray l2=new JSONArray();
                      concept  c=conceptsSort.get(k);
                      l2.add(c.getConcept().toString().toLowerCase());
                   System.out.println(conceptsSort.get(k).getConcept()+"  :"+conceptsSort.get(k).getIdf());
                    ArrayList <String> synonyms=bioPortailSynonyms.getSynonynms((String) (c.getConcept().toString().toLowerCase()));
                      if(synonyms.size()>5){
                      for (int ii = 0; ii < 5; ii++) {
                      l2.add(synonyms.get(ii).toString().toLowerCase());
                      }
                      }
                      else{
                          for (int ii = 0; ii < synonyms.size(); ii++) {
                      l2.add(synonyms.get(ii).toString().toLowerCase());
                      }
                      }
                      l.add(l2);
                  }
//                  if(abr.size()>1){
//                   JSONObject ab=(JSONObject) abr.get(0);
//                   l2.add(ab.get("AA").toString().toLowerCase());
//                                   }
                  }
                  else{
                      for (int k = 0; k < conceptsSort.size(); k++) {
                           JSONArray l2=new JSONArray();
                          concept  c=conceptsSort.get(k);
                      l2.add(c.getConcept().toString().toLowerCase());
                                            System.out.println(conceptsSort.get(k).getConcept()+"  :"+conceptsSort.get(k).getIdf());

                      //break;
                      ArrayList <String> synonyms=bioPortailSynonyms.getSynonynms((String) (c.getConcept().toString().toLowerCase()));
                      if(synonyms.size()>5){
                      for (int ii = 0; ii < 5; ii++) {
                      l2.add(synonyms.get(ii).toString().toLowerCase());
                      }
                      }
                      else{
                          for (int ii = 0; ii < synonyms.size(); ii++) {
                      l2.add(synonyms.get(ii).toString().toLowerCase());
                      }
                      }
                      l.add(l2);
                      }    
//                           if(abr.size()!=0){
//                               for (int k = 0; k < 1; k++) {
//                                   JSONObject ab=(JSONObject) abr.get(k);
//                           l2.add(ab.get("AA").toString().toLowerCase());
//                                   }
//                                   }
                      }
                     
                //l.add(l2);
                if(l.size()==0){
                
                    JSONArray l2=new JSONArray();
                     
                      l2.add("gene");
                      l.add(l2);
                
                }
                if(passages.size()>2){
                JSONObject passage2=(JSONObject) passages.get(1);
                String text2=(String) passage2.get("text");
                objput.put("exact_answer",l);
                objput.put("ideal_answer",passage.get("text")+" "+text2);
                }
                else{
                     objput.put("exact_answer",l);
                objput.put("ideal_answer",passage.get("text"));
                }
                    
                }              
              }
              else{
                  JSONArray l=new JSONArray();
                  JSONArray l2=new JSONArray();
                  l2.add("gene");
                  l.add(l2);
                      
                objput.put("exact_answer",l);
                if(passages.size()>=2){
                JSONObject passage=(JSONObject) passages.get(0);
                JSONObject passage2=(JSONObject) passages.get(1);
                
                String text2=(String) passage2.get("text");
                String text=(String) passage.get("text");
                objput.put("ideal_answer",text+" "+text2);
                }
                else if(passages.size()>=1){
                    JSONObject passage=(JSONObject) passages.get(0);
                    String text=(String) passage.get("text");
                    objput.put("ideal_answer",text);
                    
                }
                else{
                    objput.put("ideal_answer","bla bla");
                }
              
              }
              
              }
          //list
            else if(question.get("type").equals("list")){
                //JSONArray ideal=(JSONArray) question2.get("ideal_answer");
                  for (int jj = 0; jj < 1; jj++) {
             
                JSONObject passage=(JSONObject) passages.get(jj);
                JSONArray concepts=(JSONArray) passage.get("concepts");
                JSONArray abr=(JSONArray) passage.get("abbreviations");
                JSONArray l=new JSONArray();
                //JSONArray l2=new JSONArray();
//                if(concepts.size()==0){
//                    JSONArray l2=new JSONArray();
//                      l2.add("gene");
//                      l.add(l2);
//                }
                 ArrayList<concept> conceptsSort=tfidf.getBestConcepts(passages, (JSONArray) question2.get("concepts"));
                
                if(conceptsSort.size()>5){
                  for (int k = 0; k < 5; k++) {
                    JSONArray l2=new JSONArray(); 
                      concept  c=conceptsSort.get(k);
                      l2.add(c.getConcept().toString().toLowerCase());
                   System.out.println(conceptsSort.get(k).getConcept()+"  :"+conceptsSort.get(k).getIdf());
                  
//                      ArrayList<String> similars=similarTerms.FindSimilarTerms((String)c.getConcept().toString().toLowerCase());
//                      if(similars.size()!=0){
//                         if(similars.size()>10){
//                             for (int n = 0; n < 10; n++) {
//                                 for(int kk=0;kk<l2.size();kk++){
//                                 String sim=l2.get(kk).toString().toLowerCase();
//                                 if(l2.contains(similars.get(n).toString().toLowerCase())==false){
//                                 l2.add(similars.get(n).toString().toLowerCase());}
//                                 }
//                             }
//                         } else{
//                             for (int n = 0; n < similars.size(); n++) {
//                                 for(int kk=0;kk<l2.size();kk++){
//                                 if(l2.contains(similars.get(n).toString().toLowerCase())==false){
//                                 l2.add(similars.get(n).toString().toLowerCase());}
//                                 }
//                             }
//                         }
//                      }
//                      //bioportail syn
                      ArrayList <String> synonyms=bioPortailSynonyms.getSynonynms((String) (c.getConcept().toString().toLowerCase()));
                      if(synonyms.size()>10){
                      for (int ii = 0; ii < 10; ii++) {
                      l2.add(synonyms.get(ii).toString().toLowerCase());
                      }
                      }
                      else{
                          for (int ii = 0; ii < synonyms.size(); ii++) {
                      l2.add(synonyms.get(ii).toString().toLowerCase());
                      }
                      }
                      //
//                      if(abr.size()!=0){
//                               for (int kk = 0; kk < abr.size(); kk++) {
//                                   JSONObject ab=(JSONObject) abr.get(kk);
//                           l2.add(ab.get("AA").toString().toLowerCase());
//                                   }
//                                   }
                      l.add(l2);
                      
                  }
                
                  }
                else{
                  for (int k = 0; k < conceptsSort.size(); k++) {
                  JSONArray l2=new JSONArray(); 
                      concept  c=conceptsSort.get(k);
                      l2.add(c.getConcept().toString().toLowerCase());
                   System.out.println(conceptsSort.get(k).getConcept()+"  :"+conceptsSort.get(k).getIdf());
                  
//                      ArrayList<String> similars=similarTerms.FindSimilarTerms((String)c.getConcept().toString().toLowerCase());
//                      if(similars.size()!=0){
//                         if(similars.size()>10){
//                             for (int n = 0; n < 10; n++) {
//                                 for(int kk=0;kk<l2.size();kk++){
//                                 if(l2.get(kk).toString().equals(similars.get(n).toString().toLowerCase())==false){
//                                 l2.add(similars.get(n).toString().toLowerCase());}
//                                 }
//                             }
//                         } else{
//                             for (int n = 0; n < similars.size(); n++) {
//                                 for(int kk=0;kk<l2.size();kk++){
//                                 if(l2.get(kk).toString().equals(similars.get(n).toString().toLowerCase())==false){
//                                 l2.add(similars.get(n).toString().toLowerCase());}
//                                 }
//                             }
//                         }
//                      }
//                     
                   
                    ArrayList <String> synonyms=bioPortailSynonyms.getSynonynms((String) (c.getConcept().toString().toLowerCase()));
                      if(synonyms.size()>5){
                      for (int ii = 0; ii < 5; ii++) {
                      l2.add(synonyms.get(ii).toString().toLowerCase());
                      }
                      }
                      else{
                          for (int ii = 0; ii < synonyms.size(); ii++) {
                      l2.add(synonyms.get(ii).toString().toLowerCase());
                      }
                      }
                      //
//                      if(abr.size()!=0){
//                               for (int kk = 0; kk < abr.size(); kk++) {
//                                   JSONObject ab=(JSONObject) abr.get(kk);
//                           l2.add(ab.get("AA").toString().toLowerCase());
//                                   }
//                                   }
                      l.add(l2);
                  } 
                }
                if(l.size()!=0){
                JSONArray ll=(JSONArray) l.get(0);
                if (ll.size()==0){
                    JSONArray l2=new JSONArray();
                     
                      l2.add("gene");
                      l.add(l2);
                }
                }
                
                if(passages.size()>=2){
                JSONObject passage2=(JSONObject) passages.get(1);
                
                String text2=(String) passage2.get("text");
                objput.put("exact_answer",l);
                objput.put("ideal_answer",passage.get("text")+" "+text2);
                }
                else{
                    objput.put("exact_answer",l);
                objput.put("ideal_answer",passage.get("text"));
                }
              }}
              else if(question.get("type").equals("summary")){
               //JSONArray ideal=(JSONArray) question2.get("ideal_answer");
                if(passages.size()>=2){
                JSONObject passage=(JSONObject) passages.get(0);
                JSONObject passage2=(JSONObject) passages.get(1);
                
                String text2=(String) passage2.get("text");
                String text=(String) passage.get("text");
                objput.put("ideal_answer",text+" "+text2);
                }
                else{
                    JSONObject passage=(JSONObject) passages.get(0);
                    String text=(String) passage.get("text");
                    objput.put("ideal_answer",text);
                }
              }
              
         
         
         }
         }
         Questions.add(objput);
         jsonObjWrite.put("questions", Questions);
     }
    file.write(jsonObjWrite.toJSONString());
    System.out.println("Successfully Copied JSON Object to File...");
    System.out.println("\nJSON Object: " + jsonObjWrite);
    file.flush();
    file.close();
    }
    
    
}
    

