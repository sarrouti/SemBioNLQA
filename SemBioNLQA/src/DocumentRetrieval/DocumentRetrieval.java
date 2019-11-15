/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DocumentRetrieval;

import BioProtail.BioportalMapping;
import Processing.Entity;
import Processing.EntityAA;
import Processing.MappingtoUMLSUsingMetamap;
import Processing.QuestionNOUN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

/**
 *
 * @author sarrouti
 */
public class DocumentRetrieval {
   
   ArrayList<String> documents;
   public DocumentRetrieval() {
    }

    public ArrayList<Document> getDocuments(String q) throws JDOMException, IOException, ClientHandlerException, UniformInterfaceException, ParseException {
      PubmedSearch pubmedSearch=new PubmedSearch();
    // Metammap//
      MappingtoUMLSUsingMetamap mapping=new MappingtoUMLSUsingMetamap();

	      //BioPortal Mapping
    //  BioportalMapping mapping =new BioportalMapping();
      QuestionNOUN questionNOUN=new QuestionNOUN();
      EntityAA EnAA=mapping.getListConceptsAndAA(q);
      ArrayList<Entity> Entities=(ArrayList<Entity>) EnAA.getEEs();
      String query="";
      Entity e;
        for (int i = 0; i < Entities.size(); i++) {
            e=Entities.get(i);
        if(e.getEntity().equalsIgnoreCase("name")==false && e.getEntity().equalsIgnoreCase("list")==false){
                String ee="";
                if(e.getEntity().contains("*")){
                    ee=e.getEntity().replace("*", "");
                    query+=ee+"+";
                }  
               else if(e.getEntity().contains("-- ")){
                    ee=e.getEntity().replace("-- ", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("--")){
                    ee=e.getEntity().replace("--", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("% ")){
                    ee=e.getEntity().replace("% ", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("%")){
                    ee=e.getEntity().replace("%", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("*^")){
                    ee=e.getEntity().replace("*^", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("^")){
                    ee=e.getEntity().replace("^", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else{
                    query+=e.getEntity()+"+";
                }
        }
        }
           ArrayList <String> nouns=questionNOUN.getNouN(q);
           
           ArrayList<String> EntiesStrings=new ArrayList();
                for (int j = 0; j < Entities.size(); j++) {
                    Entity en=(Entity) Entities.get(j);
                    String enn=en.getEntity();
                   //   System.out.println("--"+enn);
                    EntiesStrings.add(enn.toLowerCase());
                }
           for (int j = 0; j < nouns.size(); j++) {
               int k=0;
               String noun=nouns.get(j);
               //System.out.println("---"+noun);
               for (int i = 0; i < EntiesStrings.size(); i++) {
                    
                    String ee=EntiesStrings.get(i);
                    
                    if(ee.contains(noun.toLowerCase())==true){
                        k=1;
                    }
                }
                if(k==0){
                    query+=noun+"+"; 
                }
                }
        query=query.replace("*", "");  //query=query.replace("-", ""); 
        query=query.replace("%", "");  
        query=query.replace("^", "");
        query= query.replace("<", "");
        query=query.replace(">", "");
        query=query.replace("(", "");
        query=query.replace(")", "");
        query=query.replace("{", "");
        query=query.replace("}", "");
        query=query.replace(" ", "+"); 
         System.out.println("-"+query);
        documents= (ArrayList)pubmedSearch.getDocument(query.toString());
        // If nothing is returned
        
        if(documents.size()==0){
              query="";
         for (int i = 0; i < Entities.size()-1; i++) {
            e=Entities.get(i);
        if(e.getEntity().equalsIgnoreCase("name")==false && e.getEntity().equalsIgnoreCase("list")==false){
                String ee="";
                if(e.getEntity().contains("*")){
                    ee=e.getEntity().replace("*", "");
                    query+=ee+"+";
                }  
               else if(e.getEntity().contains("-- ")){
                    ee=e.getEntity().replace("-- ", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("--")){
                    ee=e.getEntity().replace("--", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("% ")){
                    ee=e.getEntity().replace("% ", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("%")){
                    ee=e.getEntity().replace("%", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("*^")){
                    ee=e.getEntity().replace("*^", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("^")){
                    ee=e.getEntity().replace("^", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else{
                    query+=e.getEntity()+"+";
                }
        }
        }
         for (int j = 0; j < nouns.size(); j++) {
               int k=0;
               String noun=nouns.get(j);
               //System.out.println("---"+noun);
               for (int i = 0; i < EntiesStrings.size(); i++) {
                    
                    String ee=EntiesStrings.get(i);
                    
                    if(ee.contains(noun.toLowerCase())==true){
                        k=1;
                    }
                }
                if(k==0){
                    query+=noun+"+"; 
                }
                }
        query=query.replace("*", "");  //query=query.replace("-", ""); 
        query=query.replace("%", "");  
        query=query.replace("^", "");
        query= query.replace("<", "");
        query=query.replace(">", "");
        query=query.replace("(", "");
        query=query.replace(")", "");
        query=query.replace("{", "");
        query=query.replace("}", "");
        query=query.replace(" ", "+");  
        System.out.println(query);
        documents= (ArrayList)pubmedSearch.getDocument(query.toString());
        }
          if(documents.size()==0){
              query="";
         for (int i = 0; i < Entities.size(); i++) {
            e=Entities.get(i);
        if(e.getEntity().equalsIgnoreCase("name")==false && e.getEntity().equalsIgnoreCase("list")==false){
                String ee="";
                if(e.getEntity().contains("*")){
                    ee=e.getEntity().replace("*", "");
                    query+=ee+"+";
                }  
               else if(e.getEntity().contains("-- ")){
                    ee=e.getEntity().replace("-- ", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("--")){
                    ee=e.getEntity().replace("--", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("% ")){
                    ee=e.getEntity().replace("% ", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("%")){
                    ee=e.getEntity().replace("%", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("*^")){
                    ee=e.getEntity().replace("*^", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("^")){
                    ee=e.getEntity().replace("^", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else{
                    query+=e.getEntity()+"+";
                }
        }
        }
        
        query=query.replace("*", "");  //query=query.replace("-", ""); 
        query=query.replace("%", "");  
        query=query.replace("^", "");
        query= query.replace("<", "");
        query=query.replace(">", "");
        query=query.replace("(", "");
        query=query.replace(")", "");
        query=query.replace("{", "");
        query=query.replace("}", "");
        query=query.replace(" ", "+"); 
         System.out.println(query);
        documents= (ArrayList)pubmedSearch.getDocument(query.toString());
        }
           if(documents.size()==0){
              query="";
         for (int i = 0; i < Entities.size()-1; i++) {
            e=Entities.get(i);
        if(e.getEntity().equalsIgnoreCase("name")==false && e.getEntity().equalsIgnoreCase("list")==false){
                String ee="";
                if(e.getEntity().contains("*")){
                    ee=e.getEntity().replace("*", "");
                    query+=ee+"+";
                }  
               else if(e.getEntity().contains("-- ")){
                    ee=e.getEntity().replace("-- ", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("--")){
                    ee=e.getEntity().replace("--", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("% ")){
                    ee=e.getEntity().replace("% ", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("%")){
                    ee=e.getEntity().replace("%", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("*^")){
                    ee=e.getEntity().replace("*^", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("^")){
                    ee=e.getEntity().replace("^", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else{
                    query+=e.getEntity()+"+";
                }
        }
        }
        
        query=query.replace("*", "");  //query=query.replace("-", ""); 
        query=query.replace("%", "");  
        query=query.replace("^", "");
        query= query.replace("<", "");
        query=query.replace(">", "");
        query=query.replace("(", "");
        query=query.replace(")", "");
        query=query.replace("{", "");
        query=query.replace("}", "");
        query=query.replace(" ", "+"); 
         System.out.println(query);
        documents= (ArrayList)pubmedSearch.getDocument(query.toString());
        }
        if(documents.size()==0){
              query="";
        if(Entities.size()>=2)
         for (int i = 0; i < 2; i++) {
            e=Entities.get(i);
        if(e.getEntity().equalsIgnoreCase("name")==false && e.getEntity().equalsIgnoreCase("list")==false){
                String ee="";
                if(e.getEntity().contains("*")){
                    ee=e.getEntity().replace("*", "");
                    query+=ee+"+";
                }  
               else if(e.getEntity().contains("-- ")){
                    ee=e.getEntity().replace("-- ", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("--")){
                    ee=e.getEntity().replace("--", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("% ")){
                    ee=e.getEntity().replace("% ", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("%")){
                    ee=e.getEntity().replace("%", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("*^")){
                    ee=e.getEntity().replace("*^", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("^")){
                    ee=e.getEntity().replace("^", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else{
                    query+=e.getEntity()+"+";
                }
        }
        }
        
        query=query.replace("*", "");  //query=query.replace("-", ""); 
        query=query.replace("%", "");  
        query=query.replace("^", "");
        query= query.replace("<", "");
        query=query.replace(">", "");
        query=query.replace("(", "");
        query=query.replace(")", "");
        query=query.replace("{", "");
        query=query.replace("}", "");
        query=query.replace(" ", "+"); 
         System.out.println(query);
        documents= (ArrayList)pubmedSearch.getDocument(query.toString());
        }
         if(documents.size()==0){
              query="";
        if(Entities.size()>=1)
         for (int i = 0; i < 1; i++) {
            e=Entities.get(i);
        if(e.getEntity().equalsIgnoreCase("name")==false && e.getEntity().equalsIgnoreCase("list")==false){
                String ee="";
                if(e.getEntity().contains("*")){
                    ee=e.getEntity().replace("*", "");
                    query+=ee+"+";
                }  
               else if(e.getEntity().contains("-- ")){
                    ee=e.getEntity().replace("-- ", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("--")){
                    ee=e.getEntity().replace("--", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("% ")){
                    ee=e.getEntity().replace("% ", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("%")){
                    ee=e.getEntity().replace("%", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("*^")){
                    ee=e.getEntity().replace("*^", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else if(e.getEntity().contains("^")){
                    ee=e.getEntity().replace("^", "");
                    System.out.println(ee);
                    query+=ee+"+";
                }
                else{
                    query+=e.getEntity()+"+";
                }
        }
        }
        
        query=query.replace("*", "");  //query=query.replace("-", ""); 
        query=query.replace("%", "");  
        query=query.replace("^", "");
        query= query.replace("<", "");
        query=query.replace(">", "");
        query=query.replace("(", "");
        query=query.replace(")", "");
        query=query.replace("{", "");
        query=query.replace("}", "");
        query=query.replace(" ", "+"); 
         System.out.println(query);
        documents= (ArrayList)pubmedSearch.getDocument(query.toString());
        } 
        String link="http://www.ncbi.nlm.nih.gov/pubmed/";
        ArrayList<Document> docs=new ArrayList<Document>();
        
        //for final system
        AbstractDocPubMed abstractDoc=new AbstractDocPubMed();
        if(documents.size()>10){   // ihave add this code recenlty
          
            for (int j = 0; j < 10; j++) {
                    Document d=new Document();
                    Document dd=new Document();
                    d=abstractDoc.FindAbstract(documents.get(j));
                   if(d.getAbstract()!=""){
                       dd.setAbstract(d.getAbstract());
                       dd.setTitle(d.getTitle());
                       dd.setPmid(link+d.getPmid());
                       dd.setIndex(j+1);
                       docs.add(dd);
                   }
                   
                }
             }
             else {
                 for (int j = 0; j < documents.size(); j++) {
                     Document d=new Document();
                     Document dd=new Document();
                     d=abstractDoc.FindAbstract(documents.get(j));
                   if(d.getAbstract()!=""){
                       dd.setAbstract(d.getAbstract());
                       dd.setTitle(d.getTitle());
                       dd.setPmid(link+d.getPmid());
                       docs.add(dd);
                       dd.setIndex(j+1);
                   }
                }
             }
        //for evaluation
      /* if(documents.size()>10){   
          
            for (int j = 0; j < 10; j++) {
                    Document dd=new Document();
                   
                       dd.setAbstract("null");
                       dd.setTitle("null");
                       dd.setPmid(link+documents.get(j));
                       docs.add(dd);
                   
                   
                }
             }
             else {
                 for (int j = 0; j < documents.size(); j++) {
                     Document dd=new Document();
                     
                       dd.setAbstract("null");
                       dd.setTitle("null");
                       dd.setPmid(link+documents.get(j));
                       docs.add(dd);
                   
                }
             }*/
        return docs;
    }
    
}
