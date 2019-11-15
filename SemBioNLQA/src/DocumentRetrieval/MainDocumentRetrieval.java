/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DocumentRetrieval;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.JDOMException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sarrouti
 */
public class MainDocumentRetrieval {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException, JDOMException {
   
        JSONObject obj2 = new JSONObject();
    FileWriter file = new FileWriter("E:\\these\\corpus d evaluation et api\\bioASQ\\Passage Retrieval\\BioASQ 3b 2015\\Relevant Docs for BioMedNLQA\\Docs-BioASQ-task3bPhaseA-testset5.json");
    obj2.put("username", "sarrouti");
    obj2.put("password", "076902107");
    obj2.put("system", "sarrouti");//E:\\these\\corpus d evaluation et api\\bioASK\\document retrieval data\\Test Data\\phase A\\phaseA_2b_05.json
    JSONArray Questions = new JSONArray();
    
    JSONParser parser = new JSONParser();
    Object obj = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASQ\\BioASQ 3\\Task 3b-Phase A\\BioASQ-task3bPhaseA-testset5"));
    JSONObject jsonObject = (JSONObject) obj;
    JSONArray questions=(JSONArray) jsonObject.get("questions");    
    DocumentRetrieval documentRetrieval=new DocumentRetrieval();
    ArrayList<Document> Reldocuments= new ArrayList<Document>();
    for(int i=0;i<questions.size();i++){//questions.size()
         System.out.println(i+"****************************************************************");
         JSONObject question=(JSONObject) questions.get(i);
         JSONObject obj22 = new JSONObject();
         obj22.put("body", question.get("body"));
         obj22.put("type", question.get("type"));
         obj22.put("id", question.get("id"));
         System.out.println(question.get("body"));
         Reldocuments=documentRetrieval.getDocuments((String) question.get("body"));
         JSONArray docs = new JSONArray();
         System.out.println(Reldocuments.size());
         if(Reldocuments.size()!=0){
               for (int j = 0; j < Reldocuments.size(); j++) {
                   Document d=Reldocuments.get(j);
                   //JSONObject objD = new JSONObject();
                   //objD.put("link", d.getPmid());
                   
                   //objD.put("title", d.getTitle());
                   //objD.put("abstract", d.getAbstract());
                   docs.add(d.getPmid());
               
               }
         }
         obj22.put("documents",docs);
            //obj22.put("snippets", snippets);
	 Questions.add(obj22);
         obj2.put("questions", Questions);
    }
    file.write(obj2.toJSONString());
    System.out.println("Successfully Copied JSON Object to File...");
    System.out.println("\nJSON Object: " + obj2);
    file.flush();
    file.close();
           System.out.println();
}
}