/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author sarrouti
 */
public class SnippetsReRanking {

    public SnippetsReRanking() {
    }
    
    public static Map sortByValue(Map unsortMap) {	 
	List list = new LinkedList(unsortMap.entrySet());
 
	Collections.sort(list, new Comparator() {
		public int compare(Object o1, Object o2) {
			return ((Comparable) ((Map.Entry) (o2)).getValue())
						.compareTo(((Map.Entry) (o1)).getValue());
		}
	});
 
	Map sortedMap = new LinkedHashMap();
	for (Iterator it = list.iterator(); it.hasNext();) {
		Map.Entry entry = (Map.Entry) it.next();
		sortedMap.put(entry.getKey(), entry.getValue());
	}
	return sortedMap;
}
    public JSONArray getRankedSnippets(JSONObject question, JSONArray Snippets){
        BM25terms bM25terms=new BM25terms();
        BM25concepts bM25concepts=new BM25concepts();
        Map<String,Double> map = new HashMap<String,Double>() ;
         for(int i=0;i<Snippets.size();i++){
         JSONObject sent=new JSONObject();
         sent=(JSONObject) Snippets.get(i);
         String s=(String) sent.get("text");
         double simBm25= bM25concepts.getBM25Similarity((String) question.get("body"),(JSONArray)question.get("concepts"),s,(JSONArray)sent.get("concepts"),Snippets);
         double sim=bM25terms.getBM25Similarity((String) question.get("body"), s, Snippets);
         double score=sim+simBm25;
         map.put(s, score);
        }
        Map sortedMap =  sortByValue(map); 
                      Set cles = sortedMap.keySet();
                      Iterator it = cles.iterator(); 
                      int ii=0;
                      JSONArray passages=new JSONArray();
                     while (it.hasNext()){
                      Object txt = it.next(); // tu peux typer plus finement ici
                      Object valeur = map.get(txt);  
                      passages.add(txt.toString());
                      
                     }
               return  passages;
    }
}
