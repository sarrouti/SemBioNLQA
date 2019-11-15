/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enswerexractionphase;

import Processing.AA;
import Processing.Entity;
import Processing.EntityAA;
import Processing.MappingtoUMLSUsingMetamap;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sarrouti
 */
public class CorrectingCorpus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
    JSONParser parser = new JSONParser();
    Object obj = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASQ\\Answer Extration\\BioASQ-3b-2015\\DataSet-Batch5.json"));
    JSONObject jsonObject = (JSONObject) obj;
    JSONArray questions=(JSONArray) jsonObject.get("questions"); 
    
    //write
     JSONObject jsonObjWrite = new JSONObject();
     FileWriter file = new FileWriter("E:\\these\\corpus d evaluation et api\\bioASQ\\Answer Extration\\BioASQ-3b-2015\\DataSet-3b-Batch5-v2.json");
     jsonObjWrite.put("username", "sarrouti");
     jsonObjWrite.put("password", "076902107");
     jsonObjWrite.put("system", "sarrouti");             
     MappingtoUMLSUsingMetamap mapping=new MappingtoUMLSUsingMetamap();

     JSONArray Questions = new JSONArray(); 
     
     for (int i = 0; i < questions.size(); i++) {
     JSONObject question=(JSONObject) questions.get(i);
         JSONObject objput = new JSONObject();
         objput.put("id", question.get("id"));
         objput.put("type", question.get("type"));
         objput.put("body", question.get("body"));
         System.out.println(+i+": "+question.get("body"));
         JSONArray concepts=(JSONArray) question.get("concepts");
         JSONArray conceptsset = new JSONArray();
         JSONArray AAset = new JSONArray();
         if (concepts.isEmpty()) {
          EntityAA EnAA=mapping.getListConceptsAndAA(question.get("body").toString());
         
         // get concepts and abbreviation
         List concept=EnAA.getEEs();
         List AAs=EnAA.getAAs();
         for (int j = 0; j < concept.size(); j++) {
             JSONObject objC = new JSONObject();
             Entity e=(Entity) concept.get(j);
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
         
         }
         else{
            conceptsset=(JSONArray) question.get("concepts");
            AAset=(JSONArray) question.get("abbreviations");
         }
         JSONArray snippets=(JSONArray) question.get("snippets");
         JSONArray snippetswrite=new JSONArray();
         for (int j = 0; j < snippets.size(); j++) {//snippets.size()
             JSONObject snippet = new JSONObject();
             JSONObject objDwrite = new JSONObject();
             
             snippet=(JSONObject) snippets.get(j);
             objDwrite.put("text",snippet.get("text"));
              concepts=(JSONArray) snippet.get("concepts");
             if (concepts.isEmpty()) {
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
             else{
         objDwrite.put("concepts", concepts);
         objDwrite.put("abbreviations", snippet.get("abbreviations"));
         snippetswrite.add(objDwrite);
             }
         
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
