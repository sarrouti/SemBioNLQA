/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

//import QuestionProcess.PassagesEntitiy;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author sarrouti
 */
public class BM25concepts {

    public BM25concepts() {
    }
    
    
    
    
    public double getscore(String question,JSONArray Qconcepts,String sentence,JSONArray Sconcepts){
        double sim=0.0000;
            double score=0.0000;
            for(int i=0;i<Qconcepts.size();i++){
                JSONObject Qen=(JSONObject) Qconcepts.get(i);
                String c1=(String) Qen.get("concept");
                for (int k = 0; k < Sconcepts.size(); k++) {
                           
                           JSONObject Sen=(JSONObject) Sconcepts.get(k);
                             String c2=(String) Sen.get("concept");
                            if(c1.equals(c2)==true)
                            {
                                //System.out.println(Qen.getConcept());
                                sim++;
                               // score+=sim+Qen.getSc();
                            }   }}
       return sim;
    }
    public double getBM25Similarity(String question,JSONArray Qconcepts,String sentence,JSONArray Sconcepts, JSONArray sents){
        
        ArrayList<String []> Stoken=new  ArrayList<String []>();
        String[] Qtoken= getToken(Qconcepts);
        String []Sentencetoken=getToken(Sconcepts);
        for (int i = 0; i < sents.size(); i++) {
            JSONObject sent=new JSONObject();
            sent=(JSONObject) sents.get(i);
            String s=(String) sent.get("text");
            JSONArray cs=(JSONArray) sent.get("concepts");
           
            String [] S=getToken(cs);
            Stoken.add(S);
        }
        double bm25=0.0;
        
        double avgD=0.0;
        for (int j = 0; j < Stoken.size(); j++) {
            avgD+=Stoken.get(j).length;
        }
        avgD=avgD/(double)(Stoken.size());
        int D=Sentencetoken.length;
        for (int i = 0; i < Qtoken.length; i++) {
            double idf=idfCalculator(Stoken,Qtoken[i]);//idf(qi);
            double tf=tfCalculator(Sentencetoken, Qtoken[i]);//tf(qi)
            if(idf>=0){
            
            bm25+=idf*((tf*(1.2+1))/(tf+1.2*(1-(0.80 )+(0.80)*(D/avgD))));
            }
        }
        return bm25;
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
                String c1=(String) Qen.get("concept");
                token.add(c1.toLowerCase());
        }
        String [] tabtoken = token.toArray(new String[token.size()]);
          return tabtoken;
    }
        
    }

