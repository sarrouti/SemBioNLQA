/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DocumentRetrieval;

import Processing.Entity;
import Processing.EntityAA;
import Processing.MappingtoUMLSUsingMetamap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

/**
 *
 * @author sarrouti
 */
public class test {

    /**
     * @param args the command line arguments
     * @throws JDOMException 
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     * @throws MalformedURLException 
     */
    public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException, IOException, JDOMException {

   /*
    MappingtoUMLSUsingMetamap mapping=new MappingtoUMLSUsingMetamap();
    String q="What is the association of spermidine with α-synuclein neurotoxicity?";
            q = Normalizer.normalize(q, Normalizer.Form.NFD);
    String resultString = q.replaceAll("[^\\x00-\\x7F]", "");
    System.out.println(resultString);
    EntityAA EnAA=mapping.getListConceptsAndAA(q);
    
         List concepts=EnAA.getEEs();
         List AAs=EnAA.getAAs();
         for (int j = 0; j < concepts.size(); j++) {
             Entity e=(Entity) concepts.get(j);
             System.out.println(e.getEntity());
             System.out.println(e.getConcept());
             System.out.println(e.getCUI());
             System.out.println(e.getScore());
          }*/
    	
    	
    	AbstractDocPubMed abst=new AbstractDocPubMed();
    	 Document doc=new Document();
    	 doc=abst.FindAbstract("25860343");
         System.out.println(doc.getAbstract());
    	
    	
}}

