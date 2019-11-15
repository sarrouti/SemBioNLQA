
package BioProtail;

import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

public class MainSynonyms {

    public static void main(String[] args) throws IOException, ParseException {
        BioPortailSynonyms bioPortailSynonyms=new BioPortailSynonyms();
        String q="*^patient";
        if(q.contains("^")){
            q = q.replace("^", "");
      }
        System.out.println(q);
        
        ArrayList <String> synonyms=bioPortailSynonyms.getSynonynms(q);
        
        for (int i = 0; i < synonyms.size(); i++) {
            System.out.println(synonyms.get(i));
        }
        
        
    }
    
}
