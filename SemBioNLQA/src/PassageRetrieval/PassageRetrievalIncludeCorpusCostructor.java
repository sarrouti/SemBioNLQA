package PassageRetrieval;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

import salvo.jesus.graph.visual.layout.ForceDirectedLayout;
import bsh.commands.dir;
import BioProtail.BioportalMapping;
import DocumentRetrieval.Document;
import DocumentRetrieval.DocumentRetrieval;
import Processing.AA;
import Processing.Entity;
import Processing.EntityAA;
import Processing.MappingtoUMLSUsingMetamap;

public class PassageRetrievalIncludeCorpusCostructor {

	public PassageRetrievalIncludeCorpusCostructor(){
		
	}
	
	public JSONObject getPassages (String inputQ, ArrayList<Document> documents) throws ClientHandlerException, UniformInterfaceException, ParseException{
	    
	    //writting
	     JSONObject jsonObjWrite = new JSONObject();
	     jsonObjWrite.put("username", "sarrouti");
	     jsonObjWrite.put("password", "076902107");
	     jsonObjWrite.put("system", "sarrouti");  
	     JSONObject jsonObjWrite2 = new JSONObject();
         jsonObjWrite2.put("username", "sarrouti");
         jsonObjWrite2.put("password", "076902107");
         jsonObjWrite2.put("system", "sarrouti");
        
	      // Metammap//
         MappingtoUMLSUsingMetamap mapping=new MappingtoUMLSUsingMetamap();

	      //BioPortal Mapping
        // BioportalMapping mapping =new BioportalMapping();
         
         
	     
	     JSONArray Questions = new JSONArray();
	     JSONArray Questions2 = new JSONArray();
	         JSONObject objput = new JSONObject();
	         
	         objput.put("body", inputQ);
	
	         //mapping to UMLS
	         EntityAA EnAA=mapping.getListConceptsAndAA(inputQ.toString());//
	         JSONArray conceptsset = new JSONArray();
	         JSONArray AAset = new JSONArray();
	         // get concepts and abbreviation
	         List concepts=EnAA.getEEs();
	         List AAs=EnAA.getAAs();
	         for (int j = 0; j < concepts.size(); j++) {
	             JSONObject objC = new JSONObject();
	             Entity e=(Entity) concepts.get(j);
	             objC.put("entity", e.getEntity());
	             objC.put("concept", e.getConcept());
	             objC.put("cui", e.getCUI());
	             objC.put("score", e.getScore());
	             objC.put("semantic type", e.getSemantictype());
	             conceptsset.add(objC);
	          }
	         for (int j = 0; j < AAs.size(); j++) {
	             JSONObject objA = new JSONObject();
	             AA a=(AA) AAs.get(j);
	             objA.put("AA", a.getAA());
	             objA.put("concept", a.getConcept());
	             AAset.add(objA);
	          }
	         
	         // split abstract into sentences and mapping to UMLS
	         
	         

	         JSONArray docs = new JSONArray();

	         for (int j = 0; j < documents.size(); j++) {//
	                Document d = new Document();
	                JSONObject objDwrite = new JSONObject();
	                d = documents.get(j);
	                System.out.println("Doc:"+j+" \t"+d.getPmid());
	                objDwrite.put("link", d.getPmid());
	                objDwrite.put("abstract", d.getAbstract());
	                JSONArray Sents = new JSONArray();
	                //SentenceLingePipe sentenceLingePipe=new SentenceLingePipe();
	                SentenceStanfordNLP sentenceStanfordNLP=new SentenceStanfordNLP();
	                ArrayList<String> sentences=new ArrayList<String>();
	                //sentences=sentenceLingePipe.getSentences((String) objDread.get("abstract"));
	                sentences=sentenceStanfordNLP.getSentences((String) d.getAbstract());
	            
	                for (int k = 0; k < sentences.size(); k++) {
	                JSONObject objS = new JSONObject();
	                objS.put("text",sentences.get(k));
	                System.out.println("\tsentence:"+k+" "+sentences.get(k));
	                EnAA=mapping.getListConceptsAndAA(sentences.get(k).toString());
	                JSONArray SentConceptsSet = new JSONArray();
	                JSONArray SentAAset = new JSONArray();
	         
	                concepts=EnAA.getEEs();
	                AAs=EnAA.getAAs();
	                for (int kk = 0; kk < concepts.size(); kk++) {
	                JSONObject objC = new JSONObject();
	                Entity e=(Entity) concepts.get(kk);
	                objC.put("entity", e.getEntity());
	                objC.put("concept", e.getConcept());
	                objC.put("cui", e.getCUI());
	                objC.put("score", e.getScore());
	                objC.put("semantic type", e.getSemantictype());
	                SentConceptsSet.add(objC);
	              }
	          
	                for (int kk = 0; kk < AAs.size(); kk++) {
	                JSONObject objA = new JSONObject();
	                AA a=(AA) AAs.get(kk);
	                objA.put("AA", a.getAA());
	                objA.put("concept", a.getConcept());
	                SentAAset.add(objA);
	               }
	            
	               objS.put("concepts", SentConceptsSet);
	               objS.put("abbreviations", SentAAset);
	               Sents.add(objS);
	                
	                } //end sentences
	               objDwrite.put("sentences", Sents);
	               docs.add(objDwrite);
	         }
	         
	         
	        
	         objput.put("documents", docs);
	         objput.put("concepts", conceptsset);
	         objput.put("abbreviations", AAset);
	         Questions.add(objput);
	         jsonObjWrite.put("questions", Questions);
	        
	         System.out.println(jsonObjWrite);
	         JSONArray questions=(JSONArray) jsonObjWrite.get("questions");
	         JSONObject question=(JSONObject) questions.get(0);
           
             JSONArray documents2=(JSONArray) question.get("documents");
            
             JSONArray Qconcepts=(JSONArray) question.get("concepts");
          
            JSONObject obj222 = new JSONObject();
         
               obj222.put("body", question.get("body"));
               obj222.put("concepts", question.get("concepts"));
               obj222.put("abbreviations", question.get("abbreviations"));
	      
                              JSONArray docs2 = new JSONArray();
                              JSONArray snippets = new JSONArray(); 
                              JSONArray snippets2 = new JSONArray();   
                              JSONArray SnippetsSet =new JSONArray();
                                              
                                              for  (int j = 0; j <docs.size(); j++) {// documents2.size()
                                                  JSONObject document=new JSONObject();
                                                  document=(JSONObject) docs.get(j);
                                                  
                                                  JSONArray sents=new JSONArray();
                                                  sents=(JSONArray) document.get("sentences");
                                                  for  (int k = 0; k < sents.size(); k++) {
                                                      JSONObject sent=new JSONObject();
                                                      sent=(JSONObject) sents.get(k);
                                                      SnippetsSet.add(sent);
                                                      }
                                              }  
           PassageRetrieval passageRetrieval=new PassageRetrieval();
          JSONArray passages=passageRetrieval.getRankedPassages(question, SnippetsSet);
	   
          for (int k=0;k<passages.size();k++){
              
              JSONObject objS = new JSONObject(); 
              JSONObject objS2 = new JSONObject();
              String txt=(String) passages.get(k);
            //  int tt=documents2.size();
              for (int j = 0; j < docs.size(); j++) {
                  JSONObject document=new JSONObject();
                  document=(JSONObject) docs.get(j);
                  
               String abst=(String) document.get("abstract");
                      if(abst.contains(txt.toString())==true){
                          
                     objS.put("offsetInBeginSection",abst.indexOf(txt.toString()));
                     objS.put("offsetInEndSection", abst.indexOf(txt.toString())+txt.toString().length());
                     objS.put("text", txt.toString());
                     objS.put("beginSection", "abstract");
                     objS.put("document", document.get("link"));
                     objS.put("endSection", "abstract");
                     snippets.add(objS);
                     
                     objS2.put("offsetInBeginSection",abst.indexOf(txt.toString()));
                     objS2.put("offsetInEndSection", abst.indexOf(txt.toString())+txt.toString().length());
                     objS2.put("text", txt.toString());
                     objS2.put("beginSection", "abstract");
                     objS2.put("document", document.get("link"));
                     objS2.put("abstract", abst);
                     objS2.put("endSection", "abstract");
                     snippets2.add(objS2);
                     break;
                      }
          }
          }      
          
      
          JSONArray snippetsF=new JSONArray();
          for (int jj=0;jj<snippets2.size();jj++){
              JSONObject objS = (JSONObject) snippets2.get(jj);
              String passage=(String) objS.get("text");
             
              for (int ii=0;ii<docs.size();ii++){//documents2.size()
                JSONObject doc=(JSONObject) docs.get(ii);
                JSONArray sentences=(JSONArray) doc.get("sentences");
                for (int iii=0;iii<sentences.size();iii++){
                    JSONObject objSS=(JSONObject) sentences.get(iii);
                    if (objS.get("text").toString().equals(objSS.get("text"))){
                        objS.put("concepts", objSS.get("concepts"));
                        objS.put("abbreviations", objSS.get("abbreviations"));
                        snippetsF.add(objS);
                    }
                }
              }
          }
          
          obj222.put("documents",docs);
          obj222.put("snippets", snippetsF);
          Questions2.add(obj222);
          
          jsonObjWrite2.put("questions", Questions2);
          
          System.out.println(jsonObjWrite2);
		return jsonObjWrite2;
          
          
          
          
          
          
          
	    }
	     
	     
	     
	    
	}

