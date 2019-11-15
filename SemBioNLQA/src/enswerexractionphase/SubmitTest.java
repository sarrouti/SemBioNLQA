/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enswerexractionphase;

import Models.SentimentWordNet;
import Processing.StanfordLemmatizer;
import Processing.Token;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Processing.*;
/**
 *
 * @author sarrouti
 */
public class SubmitTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
         JSONObject jsonObjWrite = new JSONObject();
           FileWriter file = new FileWriter("E:\\these\\corpus d evaluation et api\\bioASK\\Answer Extration\\SubmitTest.json");
           jsonObjWrite.put("username", "sarrouti");
           jsonObjWrite.put("password", "076902107");
           jsonObjWrite.put("system", "sarrouti");
           JSONArray Questions = new JSONArray();
           

           StanfordLemmatizer slem = new StanfordLemmatizer();
    SentimentWordNet SemtWordnet=new SentimentWordNet();
    
    JSONParser parser = new JSONParser();
    Object obj = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASK\\BioAsk 3\\Task 3b-Phase b\\BioASQ-task3bPhaseB-testset1"));
    JSONObject jsonObject = (JSONObject) obj;
    JSONArray questions=(JSONArray) jsonObject.get("questions");
    
    JSONParser parser2 = new JSONParser();
    Object obj2 = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASK\\Answer Extration\\datasets\\DataSet-Batch1.json"));
    JSONObject jsonObject2 = (JSONObject) obj2;
    JSONArray questions2=(JSONArray) jsonObject2.get("questions");
    
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
              if(question.get("type").equals("yesno")){
               double score=0.000;  
            for (int jj = 0; jj < 1; jj++) {
             
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
             output="no";
         }
         else{
            output="yes";
         }  
                  
                objput.put("exact_answer",output);
                //JSONArray ideal=(JSONArray) question2.get("ideal_answer");
                
                objput.put("ideal_answer","bla bla");
              }}
              else if(question.get("type").equals("factoid")){
                
               for (int jj = 0; jj < 1; jj++) {
             
                JSONObject passage=(JSONObject) passages.get(jj);
              // JSONArray ideal=(JSONArray) question2.get("ideal_answer");
                JSONArray concepts=(JSONArray) passage.get("concepts");
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
                  }}
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
                  }    }
                     
                l.add(l2);
                JSONArray ll=(JSONArray) l.get(0);
                if (ll.size()==0){
                
                    
                      l2.add("gene");
                      l.add(l2);
                }
                objput.put("exact_answer",l);
                objput.put("ideal_answer","bla bla");}              
              }
              else if(question.get("type").equals("list")){
                //JSONArray ideal=(JSONArray) question2.get("ideal_answer");
                  for (int jj = 0; jj < 1; jj++) {
             
             JSONObject passage=(JSONObject) passages.get(jj);
                JSONArray concepts=(JSONArray) passage.get("concepts");
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
                      l.add(l2);break;
                  } 
                }}}
                JSONArray ll=(JSONArray) l.get(0);
                if (ll.size()==0){
                    JSONArray l2=new JSONArray();
                     
                      l2.add("gene");
                      l.add(l2);
                }
                objput.put("exact_answer",l);
                objput.put("ideal_answer","bla bla");
              }}
              else if(question.get("type").equals("summary")){
               //JSONArray ideal=(JSONArray) question2.get("ideal_answer");
                
                objput.put("ideal_answer","bla bla");
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
