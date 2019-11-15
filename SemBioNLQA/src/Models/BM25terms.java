
package Models;

import edu.cmu.lti.ws4j.util.PorterStemmer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class BM25terms {
	MaxentTagger    tagger;
 
 //MaxentTagger    tagger = new MaxentTagger("E:\\these\\corpus d evaluation et api\\library\\stanford-postagger-full-2015-01-30\\models\\english-left3words-distsim.tagger");
   
 
 
 
    public BM25terms() {
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();  
    	String path=request.getRealPath("WEB-INF/resources/models/english-caseless-left3words-distsim.tagger");
    	tagger = new MaxentTagger(path);
    }
    
    public double getBM25Similarity(String question,String sentence, JSONArray sents){
        
        ArrayList<String []> Stoken=new  ArrayList<String []>();
        String[] Qtoken= getToken(question);
        String []Sentencetoken=getToken(sentence);
        for (int i = 0; i < sents.size(); i++) {
            JSONObject sent=new JSONObject();
            sent=(JSONObject) sents.get(i);
            String s=(String) sent.get("text");
            String [] S=getToken(s);
            Stoken.add(S);
        }
        double bm25=0.0;
        
        double avgD=0.0;
       //System.out.println(Stoken.size());
        for (int j = 0; j < Stoken.size(); j++) {
            avgD+=Stoken.get(j).length;
        }
        avgD=avgD/(double)(Stoken.size());
        int D=Sentencetoken.length;
        for (int i = 0; i < Qtoken.length; i++) {
            double idf=idfCalculator(Stoken,Qtoken[i]);//idf(qi);
            double tf=tfCalculator(Sentencetoken, Qtoken[i]);//tf(qi)
            
            if(idf>=0){
            bm25+=idf*((tf*(1.2+1))/(tf+1.2*(1-(0.90)+(0.90)*(D/avgD))));
            }//0.75 0.85 0.90 top
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
    public String [] getToken(String sentence){
      //http://www.textfixer.com/resources/common-english-words.txt
        String stop= "a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at,be,because,been,but,"
               + "by,can,cannot,could,dear,did,do,does,either,else,ever,every,for,from,get,got,had,has,have,he,her,hers,"
               + "him,his,how,however,i,if,in,into,is,it,its,just,least,let,like,likely,may,me,might,most,must,my,neither,"
               + "no,nor,not,of,off,often,on,only,or,other,our,own,rather,said,say,says,she,should,since,so,some,than,that,"
               + "the,their,them,then,there,these,they,this,tis,to,too,twas,us,wants,was,we,were,what,when,where,which,while,"
               + "who,whom,why,will,with,would,yet,you,your,%";
       String stopword[]=stop.split(",");
        PorterStemmer ps=new PorterStemmer();
       ArrayList<String> stopwords=new ArrayList<String>(Arrays.asList(stopword));
      
        String tagged = tagger.tagString(sentence);
        String[] tagged2 = tagged.split(" ");
        List<String> tokenQQ=new ArrayList<String>();
        //List<String> posQ=new ArrayList<String>();
          for(int j=0;j<tagged2.length;j++)
            {
                String Tag[]=tagged2[j].split("_");
                if(Tag[1].equals("-LCB-")==false &&
                                        Tag[1].equals("-LRB-")==false&&
                                        Tag[1].equals("-RCB-")==false&&
                                        Tag[1].equals("-RRB-")==false&&
                                        Tag[1].equals(".")==false&&
                                        Tag[1].equals("CD")==false&&
                        Tag[1].equals(",")==false&&
                        Tag[1].equals(":")==false&&
                        Tag[1].equals("SYM")==false&&
                        Tag[1].equals("SYM")==false&&
                        

                                        stopwords.contains(Tag[0].toLowerCase())==false
                                       )
                {
                     if(Tag[0].toLowerCase().length()>3  
                            &&Tag[0].equalsIgnoreCase("aed")==false&&Tag[0].equalsIgnoreCase("aeds")==false
                            &&Tag[0].equalsIgnoreCase("eed")==false&&Tag[0].equalsIgnoreCase("eeds")==false
                            &&Tag[0].equalsIgnoreCase("oed")==false&&Tag[0].equalsIgnoreCase("oeds")==false
                            ){
                        //System.out.println(Tag[0].toLowerCase());
                    
                    if (!ps.stemWord(Tag[0]).isEmpty() && ps.stemWord(Tag[0]).length()!=0 && ps.stemWord(Tag[0]).charAt(0)!='#')  { 
                    tokenQQ.add(ps.stemWord(Tag[0].toLowerCase()));//ps.stemWord()
                    }
                    }
                }
                //posQ.add(Tag[1]);

            }
          String [] tabtoken = tokenQQ.toArray(new String[tokenQQ.size()]);
          return tabtoken;
    }
    
}
