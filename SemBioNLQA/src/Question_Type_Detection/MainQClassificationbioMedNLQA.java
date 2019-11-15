
package Question_Type_Detection;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.apache.tomcat.util.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainQClassificationbioMedNLQA {

    public static void main(String[] args) throws IOException, ParseException {
       
    	
        JSONParser parser = new JSONParser();
           Object obj = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASQ\\BioASQ 4\\Task 4b-Phase B\\BioASQ-task4bPhaseB-testset5"));
           JSONObject jsonObject = (JSONObject) obj;
           JSONArray questions=(JSONArray) jsonObject.get("questions");
           QuestionTypeDetection question_Type_Detection=new QuestionTypeDetection();
           // for (int i = 0; i < questions.size(); i++) {
                //JSONObject question=(JSONObject) questions.get(i);
                //System.out.println(+i+": "+question.get("body"));
                //System.out.println(+i+": "+question.get("type")+" QC:"+question_Type_Detection.getQuestiontype("Which tool is used for promoterome mining using CAGE data?"));
                //System.out.println(+i+": "+question.get("id"));
          //      System.out.println("-----------------------------------------------------");
            
    //}
    System.out.println(question_Type_Detection.getQuestiontype("Which tool is used for promoterome mining using CAGE data?"));
    }
    
}
