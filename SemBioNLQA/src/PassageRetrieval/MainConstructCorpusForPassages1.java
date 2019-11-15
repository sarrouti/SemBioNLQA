
package PassageRetrieval;

import Processing.AA;
import Processing.Entity;
import Processing.EntityAA;
import Processing.MappingtoUMLSUsingMetamap;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainConstructCorpusForPassages1 {

    public static void main(String[] args) throws IOException, ParseException {
    //reading
     JSONParser parser = new JSONParser();
     Object obj = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASQ\\Passage Retrieval\\BioASQ 3b 2015\\Relevant Docs for BioMedNLQA\\Docs-BioASQ-task3bPhaseA-testset5-abstract.json"));
     JSONObject jsonObject = (JSONObject) obj;
     JSONArray questions=(JSONArray) jsonObject.get("questions"); 
    
    //writting
     JSONObject jsonObjWrite = new JSONObject();
     FileWriter file = new FileWriter("E:\\these\\corpus d evaluation et api\\bioASQ\\Passage Retrieval\\BioASQ 3b 2015\\Constructed Corpus for Passages BioMedNLQA\\c.json");
     jsonObjWrite.put("username", "sarrouti");
     jsonObjWrite.put("password", "076902107");
     jsonObjWrite.put("system", "sarrouti");             
     MappingtoUMLSUsingMetamap mapping=new MappingtoUMLSUsingMetamap();

     JSONArray Questions = new JSONArray();//21-31
        for (int i = 0; i < 1; i++) {//13 14 40 questions.size()

         JSONObject question=(JSONObject) questions.get(i);
         JSONObject objput = new JSONObject();
         objput.put("id", question.get("id"));
         objput.put("type", question.get("type"));
         objput.put("body", question.get("body"));
         System.out.println(+i+": "+question.get("body"));
         //mapping to UMLS
         EntityAA EnAA=mapping.getListConceptsAndAA("The random breakage and variants keeping genome size and chromosome number independent raise no serious objection to the relevance of correlations consistent with Menzerath-Altmann law across taxonomic groups and the possibility of a connection between human language and genomes through that law");//question.get("body").toString()
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
         // split abstract into sentences and mapping to UMLS
         JSONArray documents=(JSONArray) question.get("documents");
         
         JSONArray docs = new JSONArray();

         for (int j = 0; j < 1; j++) {//documents.size()
                JSONObject objDread = new JSONObject();
                JSONObject objDwrite = new JSONObject();
                objDread =(JSONObject) documents.get(j);
                System.out.println("Doc:"+j+" \t"+objDread.get("link"));
                objDwrite.put("link", objDread.get("link"));
                objDwrite.put("abstract", objDread.get("abstract"));
                JSONArray Sents = new JSONArray();
                //SentenceLingePipe sentenceLingePipe=new SentenceLingePipe();
                SentenceStanfordNLP sentenceStanfordNLP=new SentenceStanfordNLP();
                ArrayList<String> sentences=new ArrayList<String>();
                //sentences=sentenceLingePipe.getSentences((String) objDread.get("abstract"));
                sentences=sentenceStanfordNLP.getSentences((String) objDread.get("abstract"));
            
                for (int k = 0; k <1 ; k++) {//sentences.size()
                JSONObject objS = new JSONObject();
                objS.put("text",sentences.get(k));
                System.out.println("\tsentence:"+k+" "+sentences.get(k));
                EnAA=mapping.getListConceptsAndAA(sentences.get(k).toString());
                List SentConceptsSet = new JSONArray();
                List SentAAset = new JSONArray();
         
                concepts=EnAA.getEEs();
                AAs=EnAA.getAAs();
                for (int kk = 0; kk < concepts.size(); kk++) {
                JSONObject objC = new JSONObject();
                Entity e=(Entity) concepts.get(kk);
                objC.put("entity", e.getEntity());
                objC.put("concept", e.getConcept());
                objC.put("cui", e.getCUI());
                objC.put("score", e.getScore());
                objC.put("semantic type", e.getSemantictype());
                SentConceptsSet.add(objC);
              }
          
                for (int kk = 0; kk < AAs.size(); kk++) {
                JSONObject objA = new JSONObject();
                AA a=(AA) AAs.get(kk);
                objA.put("AA", a.getAA());
                objA.put("concept", a.getConcept());
                SentAAset.add(objA);
               }
            
               objS.put("concepts", SentConceptsSet);
               objS.put("abbreviations", SentAAset);
               Sents.add(objS);
                
                } //end sentences
               objDwrite.put("sentences", Sents);
               docs.add(objDwrite);
         
         }
         
         
        
         objput.put("documents", docs);
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

