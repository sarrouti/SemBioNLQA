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

/**
 *
 * @author sarrouti
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
      JSONObject jsonObjWrite = new JSONObject();
           FileWriter file = new FileWriter("E:\\these\\corpus d evaluation et api\\bioASK\\Answer Extration\\batch1.json");
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
    int correct=0;int incorrect=0;
     for(int i=0;i<questions.size();i++){
          JSONObject question=(JSONObject) questions.get(i);
         //put
         JSONObject objput = new JSONObject();
         objput.put("id", question.get("id"));
         //         
         if(question.get("type").equals("yesno")){
         System.out.println(i+"**********************************"+question.get("body"));

         String q=(String) question.get("body");
         JSONArray passages=(JSONArray) question.get("snippets");
         JSONArray ideal= (JSONArray) question.get("ideal_answer");
             
         double score=0.000;
//         for (int j = 0; j < ideal.size(); j++) {
//                  ArrayList<Token> tokenset=(ArrayList<Token>) slem.tokenPOSlemmatize(ideal.get(j).toString());
//            for (int k = 0; k < tokenset.size(); k++) {
//                 Token w=tokenset.get(k);
//                 score+=SemtWordnet.extract(w.getText());
//             }
//             }
         if (passages.equals("null")==false) {
         for (int j = 0; j < 1; j++) {
             
             JSONObject passage=(JSONObject) passages.get(j);
             String text=(String) passage.get("text");
             ArrayList<Token> tokenset=(ArrayList<Token>) slem.tokenPOSlemmatize(text);
             for (int k = 0; k < tokenset.size(); k++) {
                 Token w=tokenset.get(k);
                 score+=SemtWordnet.extract(w.getText());
             }
             
         }
         }
         String output="";
         if(score<0)
         {
             objput.put("exact_answer", "no");
             objput.put("ideal_answer", "bla bla");
             //output="no";
             //System.out.println(question.get("exact_answer")+"--No");
         }
         else{
             objput.put("exact_answer", "yes");
             objput.put("ideal_answer", "bla bla");
             //output="yes";
             //System.out.println(question.get("exact_answer")+"--Yes");
         }
//         if(question.get("exact_answer").equals(output)){
//             correct++;
//         }
//         else{
//             incorrect++;
//         }
         
     }
         else if(question.get("type").equals("factoid")){
             JSONArray f=new JSONArray();
             JSONArray ff=new JSONArray();
             f.add("Gene");
             ff.add(f);
             objput.put("exact_answer", ff);
             objput.put("ideal_answer", "bla bla");
         }
          else if(question.get("type").equals("summary")){
             objput.put("ideal_answer", "bla bla");
         }
         else if(question.get("type").equals("list")){
             JSONArray l=new JSONArray();
             l.add("bla");
             JSONArray l2=new JSONArray();
             l2.add(l);
             objput.put("exact_answer", l2);
             objput.put("ideal_answer", "bla bla");
         }
            Questions.add(objput);
            jsonObjWrite.put("questions", Questions);
     }
    file.write(jsonObjWrite.toJSONString());
    System.out.println("Successfully Copied JSON Object to File...");
    System.out.println("\nJSON Object: " + jsonObjWrite);
    file.flush();
    file.close();
    
     //System.out.println("correct: "+correct+" Incorrect: "+incorrect);
    }
    
}
