
package BioProtail;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import net.sf.json.JSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BioPortailSynonyms {

    public BioPortailSynonyms() {
    }
    public ArrayList <String> getSynonynms (String q) throws IOException, ParseException{
    	
    //  File output=new File("E:\\these\\corpus d evaluation et api\\bioASQ\\Answer Extration\\BioASQ-3b-2015\\synonyms.json");
     // FileWriter write=new FileWriter(output);      
      
      HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();    
	  String path=request.getRealPath("WEB-INF/resources/files/synonyms.json");
	  
	  File output=new File(path);
	  FileWriter write=new FileWriter(output);      
	  
      ClientConfig config=new DefaultClientConfig();
      Client client=Client.create(config);
      String query=q;
      if(q.contains("%")){
         q=q.replace("%", "");
      }
       if(q.contains("*^")){
         q=q.replace("*^", "");
      }
       if(q.contains("{")){
         q=q.replace("{", "");
      }
       if(q.contains("}")){
         q=q.replace("}", "");
      }
       if(q.contains("(")){
         q=q.replace("(", "");
      }
       if(q.contains(")")){
         q=q.replace(")", "");
      }
       if(q.contains("[")){
         q=q.replace("[", "");
      }
       if(q.contains("]")){
         q=q.replace("]", "");
      }
       if(q.contains("^")){
         q=q.replace("^", "");
      }
       if(q.contains("*")){
         q=q.replace("*", "");
      }
       if(q.contains("<")){
         q=q.replace("<", "");
      }
       if(q.contains(">")){
         q=q.replace(">", "");
      }
       
      if(q.startsWith(" ")){
         q=q.substring(1, q.length());
      }
      
      
      if(q.endsWith(" ")){
         q=q.substring(0, q.length()-1);
      }
      if(query.contains(" ")){
          q=q.replace(" ", "%20");
      }
      
          
      WebResource service=client.resource(UriBuilder.fromUri("http://data.bioontology.org/search?q="+q+"&apikey=2c2408f5-b611-4f95-98ee-6b8d5dfdaaa1").build());//title
     
      ClientResponse response = service.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
      service.accept(MediaType.APPLICATION_JSON);
     // service.header("Authorization", "apikey=2c2408f5-b611-4f95-98ee-6b8d5dfdaaa1");
//      ClientResponse response = service    
//            .header("Content-Type", "application/json;charset=UTF-8")
//            .type("application/json")
//            .accept("application/json")
//            .get(ClientResponse.class);
//      
//      
//      //service.setProperty("Authorization", "apikey token =2c2408f5-b611-4f95-98ee-6b8d5dfdaaa1");
      
      write.write(response.getEntity(String.class));
      write.close();
      JSONParser parser = new JSONParser();
      ArrayList <String> synonyms=new ArrayList<String>();
      Object obj = parser.parse(new FileReader(path));
      JSONObject jsonObject = (JSONObject) obj;
      JSONArray collections=(JSONArray) jsonObject.get("collection"); 
        for (int i = 0; i < collections.size(); i++) {
            JSONObject collection=(JSONObject) collections.get(i);
            if (collection.containsKey("synonym")) {
            JSONArray synonym=(JSONArray) collection.get("synonym");
                for (int j = 0; j < synonym.size(); j++) {
                    synonyms.add((String) synonym.get(j));
                    
                }break;
            }
            
        }
     return synonyms;
    }
    
}
