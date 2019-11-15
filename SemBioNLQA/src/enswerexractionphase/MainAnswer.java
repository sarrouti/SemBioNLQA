
package enswerexractionphase;

import Models.SentimentWordNet;
import Processing.StanfordLemmatizer;
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
public class MainAnswer {

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
         if(question.get("id").equals(question2.get("id"))){
             
         }
         
         
         }
     }
    
    
    }
    
}
