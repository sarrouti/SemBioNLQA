package DocumentRetrieval;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriBuilder;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.simple.JSONObject;
import org.primefaces.expression.impl.ChildExpressionResolver;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class AbstractDocPubMed {

	
	 ClientConfig config;
	   Client client;
	    public AbstractDocPubMed() {
	        config=new DefaultClientConfig();
	        client=Client.create(config);
	    }
	    public Document FindAbstract(String id) throws MalformedURLException, UnsupportedEncodingException, IOException, JDOMException{
	    SAXBuilder sxb = new SAXBuilder();
	    
	    //File outputt=new File("E:\\these\\corpus d evaluation et api\\fichierxml.xml");
	    
	    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();    
		String path=request.getRealPath("WEB-INF/resources/files/fichierxml.xml");
	    
	    File outputt=new File(path);
		
	    FileOutputStream ecrire = null;
	    ecrire=new FileOutputStream(outputt);
	    FileWriter write=new FileWriter(outputt);
	    org.jdom2.Document document;
	    Element racine;
	    WebResource service=client.resource(UriBuilder.fromUri("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id="+id+"&retmode=xml").build());//title
	    
	    OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");

	    FileOutputStream fileStream = new FileOutputStream(outputt);
	    OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
	    
	    
	    
	         writer.write(service.get(String.class));
	    writer.close();
	    document = sxb.build(new File(path));//E:\\these\\corpus d evaluation et api\\fichierxml.xml"
	 
	    
	    ArrayList<String> ids=  new ArrayList<String>();
	    racine = document.getRootElement();
	    System.out.println(racine.getName());
	    List<Element> documents= racine.getChildren("PubmedArticle");
	    Document doc=new Document();
	       String abst="";
	       if(documents.size()==0){
	        abst="null";
	           doc.setPmid("null");
	           doc.setTitle("null");
	           doc.setAbstract("null");
	       }
	      
	       else{
	    	   Element  doc2= documents.get(0);
	           
	           
	           
	           Element medlinecitation= doc2.getChild("MedlineCitation");
	           System.out.println(medlinecitation.getName());
	   	      Element article= medlinecitation.getChild("Article");
	   	      
	   	           
	   	      
	   	      if( article.getChildren("Abstract").size()>=1){
	   	    	 Element abstracte= article.getChild("Abstract");
		   	      
		   	      Element abstractetext= abstracte.getChild("AbstractText");
		   	   System.out.println(abstractetext.getText());
		   	      Element articleTitle= article.getChild("ArticleTitle");
		           doc.setPmid((String) id);
		           doc.setTitle(articleTitle.getText());
		           doc.setAbstract(abstractetext.getText());
	   	      }
	   	      else{
	   	    	 abst="null";
		           doc.setPmid("null");
		           doc.setTitle("null");
		           doc.setAbstract("null");
	   	      }
	   	      
	   	      
	   	     
	       }
	                 
	       return  doc;
	    
	    
	    
	    
	    
	    
	
	    }
}
