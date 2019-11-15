/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sembio;
 
//import Question_Type_Detection.QuestionTypeDetection;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
 









import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;






















import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import enswerexractionphase.ExactAnswerF;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rita.RiWordNet;
import ca.uwo.csd.ai.nlp.kernel.CompositeKernel;
import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.FeatureSequence2FeatureVector;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.types.InstanceList;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ca.uwo.csd.ai.nlp.kernel.LinearKernel;
import ca.uwo.csd.ai.nlp.kernel.RBFKernel;
import ca.uwo.csd.ai.nlp.kernel.TreeKernel;
import ca.uwo.csd.ai.nlp.libsvm.ex.SVMPredictor;
import ca.uwo.csd.ai.nlp.mallet.libsvm.SVMClassifierTrainer;
import cc.mallet.classify.Classification;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.types.Instance;
import cc.mallet.types.Label;
import cc.mallet.types.LabelAlphabet;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
//import NavigationBean.CustomKernel;

import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;

import org.jdom2.JDOMException;
import org.json.simple.JSONObject;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

@ManagedBean
@SessionScoped
public class resultsBean implements  Serializable {
 
    private static final long serialVersionUID = 7765876811740798583L;
  private String input;
  private String output;
  private String msg1="hello";
  private String msg2;  private String msg3;
  private String msg4;
  private ArrayList<Document> documents;
  private boolean hidden;
  private boolean hidden2;  private boolean hidden3;private boolean hidden4;private boolean hidden5;
  private int j=0;
  private int index;
  private ArrayList<passage> passagesliste;
  
  String idealanswer="";
  String exactanswer="";
  ArrayList<ExactAnswerF> exactanswers;
  
//@ManagedProperty(value="#{navigationBean}")
//    private NavigationBean navigationBean;

  
  public void submittest() throws IOException, JDOMException {
	    // handle form submission
		  documents=new ArrayList<Document>();
		  for (int i = 0; i < 5; i++) {
			Document d=new Document();
			d.setPmid("123456");
			d.setTitle("The coiled-coil domain containing protein CCDC40 is essential for motile cilia function and left-right axis formation.");
			d.setAbstract("Primary ciliary dyskinesia (PCD) is a genetically heterogeneous autosomal recessive disorder characterized by recurrent infections of the respiratory tract associated with the abnormal function of motile cilia. Approximately half of individuals with PCD also have alterations in the left-right organization of their internal organ positioning, including situs inversus and situs ambiguous (Kartagener's syndrome). Here, we identify an uncharacterized coiled-coil domain containing a protein, CCDC40, essential for correct left-right patterning in mouse, zebrafish and human. In mouse and zebrafish, Ccdc40 is expressed in tissues that contain motile cilia, and mutations in Ccdc40 result in cilia with reduced ranges of motility. We further show that CCDC40 mutations in humans result in a variant of PCD characterized by misplacement of the central pair of microtubules and defective assembly of inner dynein arms and dynein regulatory complexes. CCDC40 localizes to motile cilia and the apical cytoplasm and is required for axonemal recruitment of CCDC39, disruption of which underlies a similar variant of PCD.");
			d.setIndex(i+1);
			documents.add(d);
			msg3="Exact answers:";
			msg4="Ideal Answers:";
			System.out.println("ok");
			
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();    
		    String path=request.getRealPath("WEB-INF/resources/models/english-caseless-left3words-distsim.tagger");
		    System.out.println(path);
			
		}
		  passagesliste=new ArrayList<passage>();
		  for (int i = 0; i < 5; i++) {


			  passage p=new passage();
              p.setText("All the mutations were nonsense, splice, and frameshift predicting early protein truncation, which suggests this defect is caused by \"null\" alleles conferring complete protein loss.");
              p.setLink("http://www.ncbi.nlm.nih.gov/pubmed/23255504");
              p.setIndex(i+1);
              passagesliste.add(p);
		}
		  index=1;
		  setHidden(true);
		  
//	    QuestionTypeDetection question_Type_Detection=new QuestionTypeDetection();
//		  
//	     output =question_Type_Detection.getQuestiontype(input.toString()).toString();//+ question_Type_Detection.getQuestiontype(input);
//	     DocumentRetrieval documentRetrieval=new DocumentRetrieval();
//	     documents=documentRetrieval.getDocuments(input);
//	     
//	     System.out.println(output);
	     msg1="Question Type :";
	     msg2=" is ";
	    // HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    
	  }
  public void submit() throws IOException, JDOMException, ClientHandlerException, UniformInterfaceException, ParseException {
    // handle form submission
	documents=new ArrayList<Document>();
	 exactanswers=new ArrayList<ExactAnswerF>();
    QuestionTypeDetection question_Type_Detection=new QuestionTypeDetection();
	  
     output =question_Type_Detection.getQuestiontype(input.toString()).toString();//+ question_Type_Detection.getQuestiontype(input);
     DocumentRetrieval documentRetrieval=new DocumentRetrieval(); System.out.println(output);
     documents=documentRetrieval.getDocuments(input);
    
          documents=documentRetrieval.getDocuments(input);
          PassageRetrievalIncludeCorpusCostructor passages=new PassageRetrievalIncludeCorpusCostructor();
         
          JSONObject listpassages= passages.getPassages(input, documents);
         passagesliste=new ArrayList<passage>();
          
        JSONArray questions=(JSONArray) listpassages.get("questions");
        System.out.println(questions.size());
        for (int i=0;i<questions.size();i++){
            
            JSONObject question=(JSONObject) questions.get(i);
            System.out.println(+i+": "+question.get("body"));
            JSONArray snippets=(JSONArray) question.get("snippets");
            System.out.println(snippets.size());
            for  (int n = 0; n <snippets.size(); n++) {// documents2.size()
                
                JSONObject snippet=new JSONObject();
                
                snippet=(JSONObject) snippets.get(n);
                passage p=new passage();
                p.setText((String) snippet.get("text"));
                p.setLink((String) snippet.get("document"));
                p.setIndex(n+1);
                passagesliste.add(p);
            }  
        }
        
       //exact and ideal
       
        TFIDF tfidf=new TFIDF();
        StanfordLemmatizer slem = new StanfordLemmatizer();
        SentimentWordNet SemtWordnet=new SentimentWordNet();
        MyFindSimilarTermsCall similarTerms=new MyFindSimilarTermsCall();
        BioPortailSynonyms bioPortailSynonyms=new BioPortailSynonyms();
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
	                  exactanswer="";
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
	              System.out.println("neg:"+neg+" pos:"+pos);
	              exactanswer="Yes";
	                 if(neg>pos)
	                 {
	                	 exactanswer="No";
	                 }
	                 else{
	                	 exactanswer="Yes";
	                 }
	                 
	              }
	         else if(output.equals("factoid") || output.equals("list")){
	        	
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
	        	setHidden2(true);
	        }
	        else if (output.equals("factoid")||output.equals("list")){
	        	setHidden3(true);
	        	for (int i = 0; i < exactanswers.size(); i++) {
					
	        		ExactAnswerF ex=exactanswers.get(i);
	        		System.out.println("Exact Entity:"+ex.getAnswer()+ " Score: "+ex.getScore());
	        	    for (int j = 0; j < ex.getSynonyms().size(); j++) {
						System.out.println("Synonyms: "+ex.getSynonyms().get(j).toLowerCase());
						
					}
				}
	        }
	        else{
	        	setHidden4(true);
	        }
        
       //fin exact and ideal
        
        
        setHidden(true);
          
        
//     System.out.println(output);
     msg1="Question Type : ";
     msg2=" is ";
    // HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    
  }
  
  public void hideOrShow(){
      System.out.println("ok");
		 
		    setHidden(true);
		 
		   }
  
  
  public void skinValueChanged(ValueChangeEvent e) {
	    msg1 =  (String) e.getNewValue();
	    FacesContext.getCurrentInstance().renderResponse();
	}
  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }
    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }
    
    public ArrayList<passage> getPassages() {
        return  passagesliste;
    }

    public void setPassages(ArrayList<passage> passagesliste) {
        this.passagesliste = passagesliste;
    }
    
    public String getMsg3() {
        return msg3;
    }

    public void setMsg3(String msg3) {
        this.msg3 = msg3;
    }
    public String getMsg4() {
        return msg4;
    }

    public void setMsg4(String msg4) {
        this.msg4 = msg4;
    }
   

    public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	/**
     * @param hidden the hidden to set
     */
    public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public boolean isHidden2() {
		return hidden2;
	}

	public void setHidden2(boolean hidden2) {
		this.hidden2 = hidden2;
	}
	public boolean isHidden3() {
		return hidden3;
	}

	public void setHidden3(boolean hidden3) {
		this.hidden3 = hidden3;
	}
	public boolean isHidden4() {
		return hidden4;
	}

	public void setHidden4(boolean hidden4) {
		this.hidden4 = hidden4;
	}

    /**
     * @return the i
     */
    public int getJ() {
        return j;
    }
	public String getIdealanswer() {
		return idealanswer;
	}
	public void setIdealanswer(String idealanswer) {
		this.idealanswer = idealanswer;
	}
	public String getExactanswer() {
		return exactanswer;
	}
	public void setExactanswer(String exactanswer) {
		this.exactanswer = exactanswer;
	}
	public ArrayList<ExactAnswerF> getExactanswers() {
		return exactanswers;
	}
	public void setExactanswers(ArrayList<ExactAnswerF> exactanswers) {
		this.exactanswers = exactanswers;
	}
    
}