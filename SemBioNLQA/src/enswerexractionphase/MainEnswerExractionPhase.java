/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enswerexractionphase;

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
public class MainEnswerExractionPhase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        
           StanfordLemmatizer slem = new StanfordLemmatizer();
           SentimentWordNet SemtWordnet=new SentimentWordNet();
           MyFindSimilarTermsCall similarTerms=new MyFindSimilarTermsCall();
        //Wrtie
           JSONObject jsonObjWrite = new JSONObject();
           FileWriter file = new FileWriter("E:\\these\\corpus d evaluation et api\\bioASK\\Answer Extration\\batch3_allpass.json");
           jsonObjWrite.put("username", "sarrouti");
           jsonObjWrite.put("password", "076902107");
           jsonObjWrite.put("system", "sarrouti");
           JSONArray Questions = new JSONArray();
        //End Write
        
        //Read
           JSONParser parser = new JSONParser();
           Object obj = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASK\\BioAsk 3\\Task 3b-Phase b\\BioASQ-task3bPhaseB-testset3"));
           JSONObject jsonObject = (JSONObject) obj;
           JSONArray questions=(JSONArray) jsonObject.get("questions");
    
           JSONParser parser2 = new JSONParser();
           Object obj2 = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASK\\Answer Extration\\datasets\\DataSet-Batch3.json"));
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
             JSONObject passage2=(JSONObject) passages.get(1);
                JSONObject passage=(JSONObject) passages.get(0);
                String text2=(String) passage2.get("text");
                //JSONArray ideal=(JSONArray) question2.get("ideal_answer");
                objput.put("ideal_answer",passage.get("text")+" "+text2);
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
                JSONArray l2=new JSONArray();
                if(concepts.size()==0){                     
                      l2.add("gene");
                      l.add(l2);
                }
                
                if(concepts.size()>5){
                  for (int k = 0; k < 5; k++) {
                      for (int m = 0; m < Qconcepts.size(); m++) {
                      JSONObject Qc=(JSONObject) Qconcepts.get(m);
                      JSONObject c=(JSONObject) concepts.get(k);
                      if(Qc.get("entity").toString().toLowerCase().equals(c.get("entity").toString().toLowerCase())==false){    
                      l2.add(c.get("entity"));
                      }
                      break;
                      }
                  }
                  if(abr.size()>1){
                   JSONObject ab=(JSONObject) abr.get(0);
                   l2.add(ab.get("AA").toString());
                                   }
                  }
                  else{
                      for (int k = 0; k < concepts.size(); k++) {
                          for (int m = 0; m < Qconcepts.size(); m++) {
                      JSONObject Qc=(JSONObject) Qconcepts.get(m);
                      JSONObject c=(JSONObject) concepts.get(k);
                      if(Qc.get("entity").toString().toLowerCase().equals(c.get("entity").toString().toLowerCase())==false){    
                      
                      l2.add(c.get("entity"));
                      }
                      break;
                      }
                      }    
                           if(abr.size()!=0){
                               for (int k = 0; k < 1; k++) {
                                   JSONObject ab=(JSONObject) abr.get(k);
                           l2.add(ab.get("AA").toString());
                                   }
                                   }
                      }
                     
                l.add(l2);
                JSONArray ll=(JSONArray) l.get(0);
                if (ll.size()==0){
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
                if(concepts.size()==0){
                    JSONArray l2=new JSONArray();
                      l2.add("gene");
                      l.add(l2);
                }
                if(concepts.size()>5){
                  for (int k = 0; k < 5; k++) {
                      for (int m = 0; m < Qconcepts.size(); m++) {
                      JSONObject Qc=(JSONObject) Qconcepts.get(m);
                      JSONObject c=(JSONObject) concepts.get(k);
                      if(Qc.get("entity").toString().toLowerCase().equals(c.get("entity").toString().toLowerCase())==false){    
                      
                      JSONArray l2=new JSONArray();                      
                      l2.add(c.get("entity"));
//                      ArrayList<String> similars=similarTerms.FindSimilarTerms((String) c.get("entity"));
//                      if(similars.size()!=0){
//                         if(similars.size()>10){
//                             for (int n = 0; n < 10; n++) {
//                                 l2.add(similars.get(n).toString());
//                             }
//                         } else{
//                             for (int n = 0; n < similars.size(); n++) {
//                                 l2.add(similars.get(n).toString());
//                             }
//                         }
//                      }
                      if(abr.size()!=0){
                               for (int kk = 0; kk < abr.size(); kk++) {
                                   JSONObject ab=(JSONObject) abr.get(kk);
                           l2.add(ab.get("AA").toString());
                                   }
                                   }
                      l.add(l2);
                      break;
                  }}}}
                else{
                   for (int k = 0; k < concepts.size(); k++) {
                       for (int m = 0; m < Qconcepts.size(); m++) {
                      JSONObject Qc=(JSONObject) Qconcepts.get(m);
                      JSONObject c=(JSONObject) concepts.get(k);
                      if(Qc.get("entity").toString().toLowerCase().equals(c.get("entity").toString().toLowerCase())==false){    
                      JSONArray l2=new JSONArray();
                      
                      l2.add(c.get("entity"));
//                      ArrayList<String> similars=similarTerms.FindSimilarTerms((String) c.get("entity"));
//                      if(similars.size()!=0){
//                         if(similars.size()>10){
//                             for (int n = 0; n < 10; n++) {
//                                 l2.add(similars.get(n).toString());
//                             }
//                         } else{
//                             for (int n = 0; n < similars.size(); n++) {
//                                 l2.add(similars.get(n).toString());
//                             }
//                         }
//                      }
                      
                      if(abr.size()!=0){
                               for (int kk = 0; kk < abr.size(); kk++) {
                                   JSONObject ab=(JSONObject) abr.get(kk);
                           l2.add(ab.get("AA").toString());
                                   }
                                   }
                      l.add(l2);break;
                  } 
                }}}
                JSONArray ll=(JSONArray) l.get(0);
                if (ll.size()==0){
                    JSONArray l2=new JSONArray();
                     
                      l2.add("gene");
                      l.add(l2);
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
                JSONObject passage=(JSONObject) passages.get(0);
                //JSONObject passage2=(JSONObject) passages.get(1);
                
                //String text2=(String) passage2.get("text");
                String text=(String) passage.get("text");
                objput.put("ideal_answer",text);//+" "+text2);
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
    

