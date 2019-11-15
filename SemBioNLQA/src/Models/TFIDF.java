/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author sarrouti
 */

public class TFIDF {
public static class CompareConcept implements Comparator<concept> {
        @Override
        public int compare(concept o1, concept o2) {
        //return (int) (o1.getIdf() - o2.getIdf());
            if (o1.getIdf() > o2.getIdf())
        return -1;
            else if (o1.getIdf() < o2.getIdf())
          return 1;
          else
            return 0;
          
        }
        
    }

    public TFIDF() {
    }
    public ArrayList<concept> getBestConcepts(JSONArray passages, JSONArray Qconcepts){
        ArrayList<concept> concepts=getDFConcepts(passages,Qconcepts);
        //Collections.sort(concepts, new CompareConcept());
        // return concepts;
        Collections.sort(concepts, new Comparator<concept>() {
        public int compare(concept o1, concept o2) {
            concept p1 = (concept) o1;
            concept p2 = (concept) o2;
            int retval=Double.compare(p1.getIdf(), p2.getIdf());
              if(retval < 0) {
        return 1;
     }
     else if(retval > 0) {
        return -1;
     }
     else {
        return 0;
     }
        }
        
    });
  return  concepts;
    }
    public ArrayList<concept> getDFConcepts(JSONArray passages, JSONArray Qconcepts){
        ArrayList<concept> concepts=new ArrayList<concept>();
        ArrayList<String []> PassagesToken=new  ArrayList<String []>();
        String[] qconc=getToken(Qconcepts);
        if(passages.size()!=0){
        for (int i = 0; i < passages.size(); i++) {
            JSONObject passage=new JSONObject();
            passage=(JSONObject) passages.get(i);
            String s=(String) passage.get("text");
            JSONArray cs=(JSONArray) passage.get("concepts");
           
            String [] S=getToken(cs);
            PassagesToken.add(S);
        }
         for (int i = 0; i < PassagesToken.size(); i++) {
            String[] passage=PassagesToken.get(i);              
            for(int j = 0; j < passage.length; j++) {
                for (int k = 0; k < qconc.length; k++) {
                    
            if(passage[j].equalsIgnoreCase(qconc[k])==false){   
           // double df=dfCalculator(PassagesToken,passage[j]);
            double tf=tfallCalculator(PassagesToken, passage[j]);
         
             concept c=new concept();
            c.setConcept(passage[j]);
            c.setIdf(tf);
            int var=1;
                for (int l = 0; l < concepts.size(); l++) {
                    concept cc=concepts.get(l);
                    if(c.getConcept().equalsIgnoreCase(cc.getConcept())==true ){
                        var=0;
                   } 
                }
           if(var==1)
           {
             concepts.add(c);  
               
                   
               
                   
               
           }
            }
            }}
         }
        
        }
        return concepts;
    }
    public ArrayList<concept> getIDFConcepts(JSONArray passages, JSONArray Qconcepts){
        ArrayList<concept> concepts=new ArrayList<concept>();
        ArrayList<String []> PassagesToken=new  ArrayList<String []>();
        if(passages.size()!=0){
        for (int i = 0; i < passages.size(); i++) {
            JSONObject passage=new JSONObject();
            passage=(JSONObject) passages.get(i);
            String s=(String) passage.get("text");
            JSONArray cs=(JSONArray) passage.get("concepts");
           
            String [] S=getToken(cs);
            PassagesToken.add(S);
        }
        double bm25=0.0;
        
        double avgD=0.0;
       //System.out.println(Stoken.size());
        for (int j = 0; j < PassagesToken.size(); j++) {
            avgD+=PassagesToken.get(j).length;
        }
        avgD=avgD/(double)(PassagesToken.size());
      
        
         for (int i = 0; i < PassagesToken.size(); i++) {
            String[] passage=PassagesToken.get(i);
              int D=passage.length;
              
            for(int j = 0; j < passage.length; j++) {
            double idf=idfCalculator(PassagesToken,passage[j]);
            double tf=tfCalculator(passage, passage[j]);
           if(idf>0){
            
            bm25=idf*((tf*(1.2+1))/(tf+1.2*(1-(0.75 )+(0.75)*(D/avgD))));
             concept c=new concept();
            c.setConcept(passage[j]);
            c.setIdf(bm25);
              int var=1;
                for (int l = 0; l < concepts.size(); l++) {
                    concept cc=concepts.get(l);
                    if(c.getConcept().equalsIgnoreCase(cc.getConcept())==true){
                        var=0;
                   } 
                }
           if(var==1)
           {
               
               concepts.add(c);
           }
            //concepts.add(c);
            }
           
            }
         }
        
        }
        return concepts;
    }
    
    public double idfCalculator(ArrayList<String []> allTerms, String termToCheck) {
        double count = 0.0;
        for (String[] ss : allTerms) {
            for (String s : ss) {
                if (s.toLowerCase().equalsIgnoreCase(termToCheck.toLowerCase())) {
                    count++;
                   break;
                }
            }
        }
        //return ((allTerms.size()+1)/count);
        return Math.log((allTerms.size()-count+0.5)/ (count+0.5));
    } 
    public double dfCalculator(ArrayList<String []> allTerms, String termToCheck) {
        double count = 0.0;
        for (String[] ss : allTerms) {
            for (String s : ss) {
                if (s.toLowerCase().equalsIgnoreCase(termToCheck.toLowerCase())) {
                    count++;
                   break;
                }
            }
        }
        //return ((allTerms.size()+1)/count);
        return Math.log((allTerms.size()-count+0.5)/ (count+0.5));
    } 
    public double tfallCalculator(ArrayList<String []>  totalterms, String termToCheck) {
        double count = 0.0;  //to count the overall occurrence of the term termToCheck
        for (String[] ss : totalterms) {
        for (String s : ss) {
            if (s.toLowerCase().equalsIgnoreCase(termToCheck.toLowerCase())) {
                count++;
            }
        }}
        //System.out.println(count);
        return count;
    }
    public double tfCalculator(String[] totalterms, String termToCheck) {
        double count = 0.0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.toLowerCase().equalsIgnoreCase(termToCheck.toLowerCase())) {
                count++;
            }
        }
        //System.out.println(count);
        return count;
    }
     public String [] getToken(JSONArray concepts){
        List<String> token=new ArrayList<String>();
        for (int i = 0; i < concepts.size(); i++) {
            JSONObject Qen=(JSONObject) concepts.get(i);
                String c1=(String) Qen.get("entity");
                token.add(c1.toLowerCase());
        }
        String [] tabtoken = token.toArray(new String[token.size()]);
          return tabtoken;
    }
}
