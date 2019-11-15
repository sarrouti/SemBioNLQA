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
public class elustration {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        String q="Trastuzumab is a monoclonal antibody targeted to the Her2 receptor and approve for treatment of Her2 positive breast cancer.";
                 StanfordLemmatizer slem = new StanfordLemmatizer();
           SentimentWordNet SemtWordnet=new SentimentWordNet();
           
         ArrayList<Token> tokenset=(ArrayList<Token>) slem.tokenPOSlemmatize(q);
         double score=0.000;
         for (int k = 0; k < tokenset.size(); k++) {
                 Token w=tokenset.get(k);
                 score+=SemtWordnet.extract(w.getText());
                 System.out.println(w.getText()+" "+w.getPoz()+" "+SemtWordnet.extract(w.getText()));
             }
         System.out.println(score);
    
//         int yesno=0;
//         int f=0;
//         int l=0;
//         int s=0;
//         JSONParser parser = new JSONParser();
//           Object obj2 = parser.parse(new FileReader("E:\\these\\corpus d evaluation et api\\bioASK\\Answer Extration\\datasets\\DataSet-Batch5.json"));
//           JSONObject jsonObject2 = (JSONObject) obj2;
//           JSONArray questions=(JSONArray) jsonObject2.get("questions");
//           for(int i=0;i<questions.size();i++){
//           JSONObject question=(JSONObject) questions.get(i);
//           if(question.get("type").equals("yesno")){
//               yesno++;
//           }
//           if(question.get("type").equals("factoid")){
//               f++;
//           }
//           if(question.get("type").equals("list")){
//               l++;
//           }
//           if(question.get("type").equals("summary")){
//               s++;
//           }
//           }
//           int sum=yesno+f+l+s;
//           System.out.println(yesno+" "+f+" "+l+" "+s+" "+sum);
    }
    
}
