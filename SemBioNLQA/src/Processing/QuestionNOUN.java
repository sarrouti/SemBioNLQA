/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processing;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import Question_Type_Detection.QuestionTypeDetection;

public class QuestionNOUN {
 //MaxentTagger    tagger = new MaxentTagger("E:\\these\\corpus d evaluation et api\\library\\stanford-postagger-full-2015-01-30\\models\\english-left3words-distsim.tagger");
	MaxentTagger    tagger;
 
	
    public QuestionNOUN() {
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();  
    	String path=request.getRealPath("WEB-INF/resources/models/english-caseless-left3words-distsim.tagger");
    	tagger = new MaxentTagger(path);
    }
    public ArrayList<String> getNouN(String q){
        ArrayList<String>  nouns=new ArrayList<>();
         String tagged = tagger.tagString(q);
         String[] tagged2 = tagged.split(" ");
         for(int j=0;j<tagged2.length;j++)
            {
                String Tag[]=tagged2[j].split("_");
                if(Tag[1].equals("NN")==true||
                        Tag[1].equals("NNS")==true||
                        Tag[1].equals("NNP")==true||
                        Tag[1].equals("NNPS")==true
                        //Tag[1].equals("RB")==true
                        )
                {
                    nouns.add(Tag[0]);
                }
                
            }
         return nouns;
    }
}
