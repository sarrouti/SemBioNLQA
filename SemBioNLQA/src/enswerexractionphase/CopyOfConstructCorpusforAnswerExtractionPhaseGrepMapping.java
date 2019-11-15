/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enswerexractionphase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import BioProtail.BioportalMapping;
import Processing.*;

import java.util.List;
public class CopyOfConstructCorpusforAnswerExtractionPhaseGrepMapping {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
    //read  
    JSONParser parser = new JSONParser();
    Object obj = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASQ\\BioASQ 4\\Task 4b-Phase B\\BioASQ-task4bPhaseB-testset1"));
    JSONObject jsonObject = (JSONObject) obj;
    JSONArray questions=(JSONArray) jsonObject.get("questions"); 
    
    //write
     JSONObject jsonObjWrite = new JSONObject();
     FileWriter file = new FileWriter("E:\\these\\corpus d evaluation et api\\bioASQ\\Answer Extration\\BioASQ-4b-2016\\DataSetB1Grep.json");
     jsonObjWrite.put("username", "sarrouti");
     jsonObjWrite.put("password", "076902107");
     jsonObjWrite.put("system", "sarrouti");   
     
    // Metammap//
     //MappingtoUMLSUsingMetamap mapping=new MappingtoUMLSUsingMetamap();

	      //BioPortal Mapping
     BioportalMapping mapping =new BioportalMapping();
    

     JSONArray Questions = new JSONArray();
        for (int i = 0; i < questions.size(); i++) {//13 14 40 questions.size()

         JSONObject question=(JSONObject) questions.get(i);
         JSONObject objput = new JSONObject();
         objput.put("id", question.get("id"));
         objput.put("type", question.get("type"));
         objput.put("body", question.get("body"));
         System.out.println(+i+": "+question.get("body"));
         //mapping to UMLS
         EntityAA EnAA=mapping.getListConceptsAndAA(question.get("body").toString());
         JSONArray conceptsset = new JSONArray();
         JSONArray AAset = new JSONArray();
         // get concepts and abbreviation
         List concepts=EnAA.getEEs();
         List AAs=EnAA.getAAs();
         for (int j = 0; j < concepts.size(); j++) {
             JSONObject objC = new JSONObject();
             Entity e=(Entity) concepts.get(j);
             objC.put("entity", e.getEntity());
             objC.put("concept", e.getConcept());
             objC.put("cui", e.getCUI());
             objC.put("score", e.getScore());
             objC.put("semantic type", e.getSemantictype());
             conceptsset.add(objC);
          }
         for (int j = 0; j < AAs.size(); j++) {
             JSONObject objA = new JSONObject();
             AA a=(AA) AAs.get(j);
             objA.put("AA", a.getAA());
             objA.put("concept", a.getConcept());
             AAset.add(objA);
          }
        //réupurer la list de passages
         JSONArray snippets=(JSONArray) question.get("snippets");
         JSONArray snippetswrite=new JSONArray();
         for (int j = 0; j < snippets.size(); j++) {//snippets.size()
             JSONObject snippet = new JSONObject();
             JSONObject objDwrite = new JSONObject();
             
             snippet=(JSONObject) snippets.get(j);
             //System.out.println(snippet.get("text"));
             objDwrite.put("text",snippet.get("text"));
             EntityAA EnAAsnippet=mapping.getListConceptsAndAA(snippet.get("text").toString());
             JSONArray conceptSsnippetset = new JSONArray();
             JSONArray AASnippetset = new JSONArray();
         // get concepts and abbreviation
             List conceptsSnippet=EnAAsnippet.getEEs();
             List AAsSnippet=EnAAsnippet.getAAs();
             JSONArray conceptsS = new JSONArray();
             for (int k = 0; k < conceptsSnippet.size(); k++) {
             JSONObject objCC = new JSONObject();
             Entity ee=(Entity) conceptsSnippet.get(k);
             objCC.put("entity", ee.getEntity());
             objCC.put("concept", ee.getConcept());
             objCC.put("cui", ee.getCUI());
             objCC.put("score", ee.getScore());
             objCC.put("semantic type", ee.getSemantictype());
             conceptSsnippetset.add(objCC);
          }
         for (int jj = 0; jj < AAsSnippet.size(); jj++) {
             JSONObject objAA = new JSONObject();
             AA aa=(AA) AAsSnippet.get(jj);
             objAA.put("AA", aa.getAA());
             objAA.put("concept", aa.getConcept());
             
             AASnippetset.add(objAA);
           
          }
         objDwrite.put("concepts", conceptSsnippetset);
         objDwrite.put("abbreviations", AASnippetset);
         snippetswrite.add(objDwrite);
         }
         objput.put("snippets", snippetswrite);
         objput.put("concepts", conceptsset);
         objput.put("abbreviations", AAset);
         Questions.add(objput);
         jsonObjWrite.put("questions", Questions);
        
        
        
        }
        file.write(jsonObjWrite.toJSONString());
    System.out.println("Successfully Copied JSON Object to File...");
    System.out.println("\nJSON Object: " + jsonObjWrite);
    file.flush();
    file.close();
           System.out.println();
     
     
    }
    
}
