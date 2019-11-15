
package DocumentRetrieval;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class MainAbstractsDocs {

    public static void main(String[] args) throws IOException, ParseException {
           JSONArray Questions = new JSONArray();
           AbstractDoc abstractDoc=new AbstractDoc();
           JSONObject jsonObjWrite = new JSONObject();
           FileWriter file = new FileWriter("E:\\these\\corpus d evaluation et api\\bioASQ\\Passage Retrieval\\BioASQ 4b 2016\\Docs\\Docs-2015-BioASQ-task4bPhaseA-testset5-abstract4-jou.json");

           JSONParser parser = new JSONParser(); 
           Object obj = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASQ\\Passage Retrieval\\BioASQ 4b 2016\\Docs\\Docs-2015-BioASQ-task4bPhaseA-testset5.json"));
           JSONObject jsonObject = (JSONObject) obj;
           JSONArray questions=(JSONArray) jsonObject.get("questions");    
           for(int i=0;i<questions.size();i++){

            JSONObject question=(JSONObject) questions.get(i);
        
            String q=(String) question.get("body");
            System.out.println("Body: "+(String) question.get("body"));
            System.out.println("Type: "+(String) question.get("type"));
            System.out.println("Type: "+(String) question.get("id"));
            JSONArray documents=(JSONArray) question.get("documents");
            
            JSONObject obj22 = new JSONObject();
            obj22.put("body", question.get("body"));
            obj22.put("type", question.get("type"));
            obj22.put("id", question.get("id"));
           
            JSONArray docs = new JSONArray();
           
            JSONArray snipts = new JSONArray();
            
            for (int j = 0; j < documents.size(); j++) {
                   JSONObject objD = new JSONObject();
                   JSONObject objDD = new JSONObject();
                   System.out.println("document:"+j+"->"+documents.get(j).toString());
                   String []docsplite=documents.get(j).toString().split("/");
                   Document doc=abstractDoc.FindAbstract(docsplite[4]);
                   if(doc.getAbstract().equals("null")==false){
                   objD.put("link", documents.get(j).toString());
                   objD.put("title", doc.getTitle().toString());
                   objD.put("abstract", doc.getAbstract().toString());
                   docs.add(objD);
                   }
                   else{
                   objD.put("link", documents.get(j).toString());
                   objD.put("title", doc.getTitle().toString());
                   objD.put("abstract", "null");
                   docs.add(objD); 
                   }
               }
//            JSONArray snippets=(JSONArray) question.get("snippets");
//            JSONObject objS = new JSONObject();
//               for (int j = 0; j < snippets.size(); j++) {
//                   JSONObject snpt=new JSONObject();
//                   snpt=(JSONObject) snippets.get(j);
//                   System.out.println("snippets:"+j+"->"+snpt.get("text"));
//                   objS.put("offsetInBeginSection", snpt.get("offsetInBeginSection"));
//                   objS.put("offsetInEndSection", snpt.get("offsetInEndSection"));
//                   objS.put("text", snpt.get("text"));
//                   objS.put("beginSection", snpt.get("beginSection"));
//                   objS.put("document", snpt.get("document"));
//                   objS.put("endSection", snpt.get("endSection"));
//                   snipts.add(objS);
//               }
                  obj22.put("documents",docs);
            //obj22.put("snippets", snippets);
	          Questions.add(obj22);
                  jsonObjWrite.put("questions", Questions);
             }
    file.write(jsonObjWrite.toJSONString());
    System.out.println("Successfully Copied JSON Object to File...");
    System.out.println("\nJSON Object: " + jsonObjWrite);
    file.flush();
    file.close();
    
    }
    
}
