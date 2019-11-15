
package DocumentRetrieval;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriBuilder;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
public class PubmedSearch {
   
   ClientConfig config;
   Client client;
    public PubmedSearch() {
        config=new DefaultClientConfig();
        client=Client.create(config);
    }
    public ArrayList<String> getDocument(String query) throws JDOMException, IOException{
    SAXBuilder sxb = new SAXBuilder();
    //File outputt=new File("E:/these/corpus d evaluation et api/fichierxml.xml");
    
    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();    
	String path=request.getRealPath("WEB-INF/resources/files/fichierxml.xml");
    
    File outputt=new File(path);
    
    FileOutputStream ecrire = null;
    ecrire=new FileOutputStream(outputt);
    FileWriter write=new FileWriter(outputt);
    org.jdom2.Document document;
    Element racine;
    WebResource service=client.resource(UriBuilder.fromUri("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term="+query+"0001:2015/03/14[PDAT]&retmax=200&sort=relevance").build());//title
   //0001:2013/03/14
    System.out.println(service.toString());
    write.write(service.get(String.class));
    write.close();
    document = sxb.build(new File(path));
   ArrayList<String> ids=  new ArrayList<String>();
   racine = document.getRootElement();
   Element resultlist=racine.getChild("IdList");
   List results=resultlist.getChildren("Id");
   for (int i=0;i<results.size();i++){
            Element result=(Element) results.get(i);
            
            ids.add(result.getText());            
        }
   return ids;
    }
}
