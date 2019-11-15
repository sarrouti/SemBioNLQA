package enswerexractionphase;
import java.io.FileReader;
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

import BioProtail.BioPortailSynonyms;
import DocumentRetrieval.Document;
import DocumentRetrieval.DocumentRetrieval;
import Models.SentimentWordNet;
import Models.TFIDF;
import Models.concept;
import PassageRetrieval.PassageRetrievalIncludeCorpusCostructor;
import PassageRetrieval.passage;
import Processing.MyFindSimilarTermsCall;
import Processing.StanfordLemmatizer;
import Processing.Token;
import Question_Type_Detection.QuestionTypeDetection;
public class MainTestGlobaleSystem {

	public static void main(String[] args) throws ClientHandlerException, UniformInterfaceException, JDOMException, IOException, ParseException {

		 TFIDF tfidf=new TFIDF();
         StanfordLemmatizer slem = new StanfordLemmatizer();
         SentimentWordNet SemtWordnet=new SentimentWordNet();
         MyFindSimilarTermsCall similarTerms=new MyFindSimilarTermsCall();
         BioPortailSynonyms bioPortailSynonyms=new BioPortailSynonyms();
		
		    String inputQ="Which is the molecular function of the protein CCDC40?";
		    QuestionTypeDetection question_Type_Detection=new QuestionTypeDetection();
			  
		    String output =question_Type_Detection.getQuestiontype(inputQ.toString()).toString();
		    
			ArrayList<Document> documents;
			DocumentRetrieval documentRetrieval=new DocumentRetrieval();
	        documents=documentRetrieval.getDocuments(inputQ);
	        PassageRetrievalIncludeCorpusCostructor passages=new PassageRetrievalIncludeCorpusCostructor();
	       
	        JSONObject listpassages= passages.getPassages(inputQ, documents);
	        
	        System.out.println(listpassages);
	        
	        List<passage> passagesliste=new ArrayList<passage>();
	        
	        String idealanswer="";
	        String exactanswer="yes";
	        ArrayList<ExactAnswerF> exactanswers=new ArrayList<ExactAnswerF>();
	        
	        JSONArray questions=(JSONArray) listpassages.get("questions");
	        
	        for (int j = 0; j < questions.size(); j++) {
	        JSONObject question=(JSONObject) questions.get(j);
	        JSONArray snippets=(JSONArray) question.get("snippets");	
	       
	        //ideal Answers:
	        if(snippets.size()>=2){
                JSONObject passage=(JSONObject) snippets.get(0);
                JSONObject passage2=(JSONObject) snippets.get(1);
                
                String text2=(String) passage2.get("text");
                String text=(String) passage.get("text");
                idealanswer=text+" "+text2;
                }
                else{
                    JSONObject passage=(JSONObject) snippets.get(0);
                    String text=(String) passage.get("text");
                    idealanswer=text;
                }
	        //fin ideal answers
	        
	          if(output.equals("yesno")){
	        	  int neg=0;
	              int pos=0;
	              for (int jj = 0; jj < snippets.size(); jj++) {
	            	  double score=0.000;
	            	  JSONObject passage=(JSONObject) snippets.get(jj);
	                  String text=(String) passage.get("text");
	                  ArrayList<Token> tokenset=(ArrayList<Token>) slem.tokenPOSlemmatize(text);
	                  for (int k = 0; k < tokenset.size(); k++) {
	                      Token w=tokenset.get(k);
	                      score+=SemtWordnet.extract(w.getText());
	                  }
	                
	                 if(score<0)
	                 {
	                     neg++;
	                 //output="no";
	                 }
	                 else{
	                     pos++;
	                 }
	                 if(score==0){
	                     pos++;
	                 }
	                 }
	               
	                 if(neg>pos)
	                 {
	                	 exactanswer="no";
	                 }
	                 else{
	                	 exactanswer="yes";
	                 }
	                 
	              }
	         else if(output.equals("factoid") || output.equals("factoid")){
	        	
	        	  if(snippets.size()!=0){
	         ArrayList<concept> conceptsSort=tfidf.getBestConcepts(snippets, (JSONArray) question.get("concepts"));
	         if(conceptsSort.size()>5){
	        	 for (int k = 0; k < 5; k++) {
	        	 concept  c=conceptsSort.get(k);
	        	 ExactAnswerF exact=new ExactAnswerF();
	        	 exact.setAnswer(c.getConcept());
	        	 exact.setScore(c.getIdf());;
	        	 ArrayList<String> synms=new ArrayList<String>();
	        	 ArrayList <String> synonyms=bioPortailSynonyms.getSynonynms((String) (c.getConcept().toString().toLowerCase()));
	        	                      
	        	      if(synonyms.size()>5){
                      for (int ii = 0; ii < 5; ii++) {
                    	  synms.add(synonyms.get(ii).toString().toLowerCase());
                      }
                      exact.setSynonyms(synms); 
                      }
                      else{
                          for (int ii = 0; ii < synonyms.size(); ii++) {
                        	  synms.add(synonyms.get(ii).toString().toLowerCase());
                      }
                      exact.setSynonyms(synms); 
                  
                      }
	        	      exactanswers.add(exact);
	        	 } 
	        	 }
	         else{
	        	 
	        	 for (int k = 0; k < conceptsSort.size(); k++) {
		        	 concept  c=conceptsSort.get(k);
		        	 ExactAnswerF exact=new ExactAnswerF();
		        	 exact.setAnswer(c.getConcept());
		        	 exact.setScore(c.getIdf());;
		        	 ArrayList<String> synms=new ArrayList<String>();
		        	 ArrayList <String> synonyms=bioPortailSynonyms.getSynonynms((String) (c.getConcept().toString().toLowerCase()));
		        	                      
		        	      if(synonyms.size()>5){
	                      for (int ii = 0; ii < 5; ii++) {
	                    	  synms.add(synonyms.get(ii).toString().toLowerCase());
	                      }
	                      exact.setSynonyms(synms); 
	                      }
	                      else{
	                          for (int ii = 0; ii < synonyms.size(); ii++) {
	                        	  synms.add(synonyms.get(ii).toString().toLowerCase());
	                      }
	                      exact.setSynonyms(synms); 
	                  
	                      }
		        	      exactanswers.add(exact);
		        	 } 
                    }//fin else
	  
	        	  }
	        	  
	          }

	        }//fin main for
	        
	        System.out.println("Ideal Answers: "+ idealanswer);
	        
	        if(output.equals("yesno")){
	        	System.out.println("Exact Answers: "+exactanswer);
	        }
	        else {
	        	for (int i = 0; i < exactanswers.size(); i++) {
					
	        		ExactAnswerF ex=exactanswers.get(i);
	        		System.out.println("Exact Entity:"+ex.getAnswer()+ " Score: "+ex.getScore());
	        	    for (int j = 0; j < ex.getSynonyms().size(); j++) {
						System.out.println("Synonyms: "+ex.getSynonyms().get(j).toLowerCase());
						
					}
				}
	        }
	        
	}

}
