
package PassageRetrieval;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainPassageRetrieval {

    public static void main(String[] args) throws IOException, ParseException {
       
           JSONObject jsonObjWrite = new JSONObject();
           FileWriter file = new FileWriter("E:\\these\\corpus d evaluation et api\\bioASQ\\Passage Retrieval\\BioASQ 3b 2015\\Retrieved passages for BioMedNLQA\\passages-batch5-3b-eval.json");
           jsonObjWrite.put("username", "sarrouti");
           jsonObjWrite.put("password", "076902107");
           jsonObjWrite.put("system", "sarrouti");
           JSONArray Questions = new JSONArray();
           
           JSONObject jsonObjWrite2 = new JSONObject();
           FileWriter file2 = new FileWriter("E:\\these\\corpus d evaluation et api\\bioASQ\\Passage Retrieval\\BioASQ 3b 2015\\Retrieved passages for BioMedNLQA\\passages-batch5-3b-for-answer-extraction.json");
           jsonObjWrite2.put("username", "sarrouti");
           jsonObjWrite2.put("password", "076902107");
           jsonObjWrite2.put("system", "sarrouti");
           JSONArray Questions2 = new JSONArray();
           
           JSONParser parser = new JSONParser(); 
           Object obj = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASQ\\Passage Retrieval\\BioASQ 3b 2015\\Constructed Corpus for Passages BioMedNLQA\\DataSet-3b-Batch5 - v2.json"));
           JSONObject jsonObject = (JSONObject) obj;
           JSONArray questions=(JSONArray) jsonObject.get("questions");
           PassageRetrieval passageRetrieval=new PassageRetrieval();
           for (int i=0;i<questions.size();i++){
               
               JSONObject question=(JSONObject) questions.get(i);
               System.out.println(+i+": "+question.get("body"));
               JSONArray documents=(JSONArray) question.get("documents");
              
            JSONArray Qconcepts=(JSONArray) question.get("concepts");
            
            JSONObject obj22 = new JSONObject();JSONObject obj222 = new JSONObject();
            obj22.put("id", question.get("id"));obj222.put("id", question.get("id"));
                                                obj222.put("body", question.get("body"));
                                                obj222.put("concepts", question.get("concepts"));
                                                obj222.put("abbreviations", question.get("abbreviations"));
            JSONArray docs = new JSONArray();
            JSONArray snippets = new JSONArray(); 
            JSONArray snippets2 = new JSONArray();   
               
            JSONArray SnippetsSet =new JSONArray();
            for  (int j = 0; j < documents.size(); j++) {
                
                   JSONObject document=new JSONObject();
                   
                   document=(JSONObject) documents.get(j);
                   docs.add(document.get("link"));
                   JSONArray sents=new JSONArray();
                   sents=(JSONArray) document.get("sentences");
                   for  (int k = 0; k < sents.size(); k++) {
                       JSONObject sent=new JSONObject();
                       sent=(JSONObject) sents.get(k);
                       SnippetsSet.add(sent);
                       }
               }
           JSONArray passages=passageRetrieval.getRankedPassages(question, SnippetsSet);
           for (int k=0;k<passages.size();k++){
               
               JSONObject objS = new JSONObject(); JSONObject objS2 = new JSONObject();
               String txt=(String) passages.get(k);
               for (int j = 0; j < documents.size(); j++) {
                   JSONObject document=new JSONObject();
                   document=(JSONObject) documents.get(j);
                   
                String abst=(String) document.get("abstract");
                       if(abst.contains(txt.toString())==true){
                           
                      objS.put("offsetInBeginSection",abst.indexOf(txt.toString()));
                      objS.put("offsetInEndSection", abst.indexOf(txt.toString())+txt.toString().length());
                      objS.put("text", txt.toString());
                      objS.put("beginSection", "abstract");
                      objS.put("document", document.get("link"));
                      objS.put("endSection", "abstract");
                      snippets.add(objS);
                      
                      objS2.put("offsetInBeginSection",abst.indexOf(txt.toString()));
                      objS2.put("offsetInEndSection", abst.indexOf(txt.toString())+txt.toString().length());
                      objS2.put("text", txt.toString());
                      objS2.put("beginSection", "abstract");
                      objS2.put("document", document.get("link"));
                      objS2.put("abstract", abst);
                      objS2.put("endSection", "abstract");
                      snippets2.add(objS2);
                      break;
                       }
           }
           }
           JSONArray snippetsF=new JSONArray();
           for (int jj=0;jj<snippets2.size();jj++){
               JSONObject objS = (JSONObject) snippets2.get(jj);
               String passage=(String) objS.get("text");
               for (int ii=0;ii<documents.size();ii++){
                 JSONObject doc=(JSONObject) documents.get(ii);
                 JSONArray sentences=(JSONArray) doc.get("sentences");
                 for (int iii=0;iii<sentences.size();iii++){
                     JSONObject objSS=(JSONObject) sentences.get(iii);
                     if (objS.get("text").toString().equals(objSS.get("text"))){
                         objS.put("concepts", objSS.get("concepts"));
                         objS.put("abbreviations", objSS.get("abbreviations"));
                         snippetsF.add(objS);
                     }
                 }
               }
           }
               
            obj22.put("documents",docs);obj222.put("documents",docs);
            obj22.put("snippets", snippets);obj222.put("snippets", snippetsF);
	    Questions.add(obj22);Questions2.add(obj222);
            jsonObjWrite.put("questions", Questions);jsonObjWrite2.put("questions", Questions2);
            
           }
           file.write(jsonObjWrite.toJSONString());file2.write(jsonObjWrite2.toJSONString());
           System.out.println("Successfully Copied JSON Object to File...");
           System.out.println("\nJSON Object: " + jsonObjWrite);
           file.flush();file2.flush();
           file.close();file2.close();
    }
    
}
