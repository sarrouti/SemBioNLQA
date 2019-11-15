package PassageRetrieval;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DocumentRetrieval.Document;
import DocumentRetrieval.DocumentRetrieval;

public class MaintestPassageGlobalesyst {

	public static void main(String[] args) throws JDOMException, IOException, ParseException {
//	   String inputQ="Which is the molecular function of the protein CCDC40?";
//		ArrayList<Document> documents;
//		DocumentRetrieval documentRetrieval=new DocumentRetrieval();
//        documents=documentRetrieval.getDocuments(inputQ);
//        PassageRetrievalIncludeCorpusCostructor passages=new PassageRetrievalIncludeCorpusCostructor();
//       
//       JSONObject listpassages= passages.getPassages(inputQ, documents);
//        
//        System.out.println(listpassages);
//        
//        List<passage> passagesliste=new ArrayList<passage>();
        
        
        
        
        
        
		
		JSONParser parser = new JSONParser(); 
        Object obj = parser.parse(new FileReader("C:\\Users\\sarrouti\\Desktop\\new 2.json"));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray questions=(JSONArray) jsonObject.get("questions");
        System.out.println(questions.size());
        for (int i=0;i<1;i++){
            
            JSONObject question=(JSONObject) questions.get(i);
            System.out.println(+i+": "+question.get("body"));
            JSONArray documents=(JSONArray) question.get("snippets");
            int c=documents.size();
            System.out.println(documents.size());
            for  (int n = 0; n <documents.size(); n++) {// documents2.size()
                
                JSONObject document=new JSONObject();
                 document=(JSONObject) documents.get(n);
                    System.out.println(document.get("text"));              
                  
            }  
        }
//        
        
	}

}
