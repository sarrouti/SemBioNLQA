package BioProtail;

import Processing.AA;
import Processing.Entity;
import Processing.EntityAA;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import javax.security.sasl.AuthenticationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import net.sf.json.JSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BioportalMapping {
	Entity e;
    Double score;
    AA a;
    public BioportalMapping(){
    	
    }
	
	public EntityAA getListConceptsAndAA(String Q) throws ClientHandlerException, UniformInterfaceException, ParseException {
		
		
		
		ClientConfig config=new DefaultClientConfig();
	    Client client=Client.create(config);
	    
	    
	    Q = Normalizer.normalize(Q, Normalizer.Form.NFD);
        Q = Q.replaceAll("[^\\x00-\\x7F]", "");
        Q = Q.replaceAll(" ", "+");
        Q = Q.replaceAll("%", "");
        Q = Q.replaceAll("\"", ""); Q = Q.replaceAll("=", "");
        Q = Q.replaceAll("\'", "");
        Q = Q.replaceAll("<", "");  
        Q = Q.replaceAll(">", "");


        
        
        
        
	    WebResource service=client.resource(UriBuilder.fromUri("http://data.bioontology.org/annotator?text="+Q+"&apikey=2c2408f5-b611-4f95-98ee-6b8d5dfdaaa1&exclude_synonyms=true").build());//title
	     
	    ClientResponse response = service.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
	    service.accept(MediaType.APPLICATION_JSON);
	
	    JSONParser parser = new JSONParser();
	    JSONArray jsonObjWrite = (JSONArray) parser.parse(response.getEntity(String.class).toString());
	    
	    ArrayList<String> mots=new ArrayList<String>();
	    for (int i = 0; i < jsonObjWrite.size(); i++) {
			
	    	JSONObject classe=(JSONObject) jsonObjWrite.get(i);
	    	JSONArray annotations=(JSONArray) classe.get("annotations");
	    	for (int j = 0; j < annotations.size(); j++) {
	    		JSONObject anotation=(JSONObject) annotations.get(j);
	    		//System.out.println(anotation.get("text"));
	    		if(!mots.contains((String) anotation.get("text")))
	    		mots.add((String) anotation.get("text"));
	    		
			}
	    	
		}
	    EntityAA EA = new EntityAA();
	    List<Entity> listconcepts=new ArrayList<Entity>();
	    List<AA> AAs=new ArrayList<AA>();
	    for (int j = 0; j < mots.size(); j++) {
	    //	System.out.println(mots.get(j).toLowerCase());
	    
	    	e=new Entity();
	    	
	    	 e.setCUI(mots.get(j).toLowerCase());
	         e.setConcept(mots.get(j).toLowerCase());
	         e.setEntity(mots.get(j).toLowerCase());
	         e.setScore(1.0);
	         e.setSemantictype(mots.get(j).toLowerCase());
	         listconcepts.add(e);
		}
	    EA.setEEs(listconcepts);
        EA.setAAs(AAs);
		return EA;
        
	}
	

}
