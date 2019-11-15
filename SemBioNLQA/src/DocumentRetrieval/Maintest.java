package DocumentRetrieval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.jdom2.JDOMException;
import org.json.simple.parser.ParseException;

import rita.RiWordNet;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Maintest {

	public static void main(String[] args) throws JDOMException, IOException, ClientHandlerException, UniformInterfaceException, ParseException {

		
		
		MaxentTagger tagger;
	
		    //    tagger = new MaxentTagger("E:\\these\\corpus d evaluation et api\\library\\stanford-postagger-full-2015-01-30\\models\\english-left3words-distsim.tagger");
		
		        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();    
		      String path=request.getRealPath("/SemBi/WebContent/WEB-INF/resources/models/english-caseless-left3words-distsim.tagger");
		        
		        //ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		        //Set<String> resources = ctx.getResourcePaths("/SemBi/WebContent/WEB-INF/resources/models/english-caseless-left3words-distsim.tagger");
		        System.out.println(path);
		        
//		        for (int i = 0; i < resources.size(); i++) {
//		        	
//					System.out.println(resources.get(i));
//				}
		
		/*
		DocumentRetrieval docr=new DocumentRetrieval();
       
		ArrayList<Document> Reldocuments= docr.getDocuments("Which tool is used for promoterome mining using CAGE data?");
		
		if(Reldocuments.size()!=0){
            for (int j = 0; j < Reldocuments.size(); j++) {
                Document d=Reldocuments.get(j);
                
              System.out.println(d.getPmid());
              System.out.println(d.getAbstract());
            }
      }
	*/
	
	}

}
