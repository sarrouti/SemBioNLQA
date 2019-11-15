/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processing;

import gov.nih.nlm.nls.skr.GenericObject;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sarrouti
 */
public class MappingtoUMLSUsingMetamap {
     GenericObject myIntMMObj;
     String Q;
     Entity e;
     Double score;
     AA a;
    public MappingtoUMLSUsingMetamap() {
     myIntMMObj = new GenericObject(100, "sarrouti", "SARROUTI076902107_");
     myIntMMObj.setField("Email_Address", "sarrouti.mourad@gmail.com");
     myIntMMObj.setField("SilentEmail", true);
     myIntMMObj.setField("KSOURCE", "2017AB");//semrep MTI metamap 2014AA
     myIntMMObj.setField("Batch_Command", "metamap -E");
     myIntMMObj.setField("COMMAND_ARGS", "-ANy");
     myIntMMObj.setField("BatchNotes","SKR Web API test");
    }
    public EntityAA getListConceptsAndAA(String Q) {
     
     StringBuffer buffer = new StringBuffer(Q); 
     String bufferStr = buffer.toString();
     myIntMMObj.setField("APIText", bufferStr);
     String result = myIntMMObj.handleSubmission();
     EntityAA EA = new EntityAA();
     if(result.equals("")){
     List<Entity> listconcepts=new ArrayList<Entity>();
     List<AA> AAs=new ArrayList<AA>();
           ///System.out.println(result);
                   String[] results = myIntMMObj.handleSubmission().split("\n");
                   for (int i=1;i<results.length;i++){
                         String [] line=results[i].split("\\|");
                         if(line[1].equals("MMI")){
                         e=new Entity();
                         //System.out.println(results[i]);
                         
                         String []virefy =line [6].split("-");
                         String[] entites=line[6].split("\"");
                         String tt[]=entites[4].split("-");
                         
                      // System.out.println(tt[1]);
                         if(tt[1].equals("noun")){
                         e.setCUI(line[4].toString());
                         e.setConcept(line[3].toString());
                         e.setEntity(entites[1].toLowerCase());
                         e.setScore(Double.valueOf(line[2].toString()));
                         e.setSemantictype(line[5]);
                         listconcepts.add(e);
                         }}
                         else if(line[1].equals("AA")){
                             a=new AA();
                             a.setAA(line[2].toString());
                             a.setConcept(line[3].toString());
                             AAs.add(a);
                         }
                 }
                   EA.setEEs(listconcepts);
                   EA.setAAs(AAs);
     } else{
          Q = Normalizer.normalize(Q, Normalizer.Form.NFD);
          Q = Q.replaceAll("[^\\x00-\\x7F]", "");
          buffer = new StringBuffer(Q); 
          bufferStr = buffer.toString();
          myIntMMObj.setField("APIText", bufferStr);
          result = myIntMMObj.handleSubmission();
          List<Entity> listconcepts=new ArrayList<Entity>();
          List<AA> AAs=new ArrayList<AA>();
           ///System.out.println(result);
                   String[] results = myIntMMObj.handleSubmission().split("\n");
                   for (int i=1;i<results.length;i++){
                         String [] line=results[i].split("\\|");
                         if(line[1].equals("MMI")){
                         e=new Entity();
                         //System.out.println(results[i]);
                         
                         String []virefy =line [6].split("-");
                         String[] entites=line[6].split("\"");
                         String tt[]=entites[4].split("-");
                         
                      // System.out.println(tt[1]);
                         if(tt[1].equals("noun")){
                         e.setCUI(line[4].toString());
                         e.setConcept(line[3].toString());
                         e.setEntity(entites[1].toLowerCase());
                         e.setScore(Double.valueOf(line[2].toString()));
                         e.setSemantictype(line[5]);
                         listconcepts.add(e);
                         }}
                         else if(line[1].equals("AA")){
                             a=new AA();
                             a.setAA(line[2].toString());
                             a.setConcept(line[3].toString());
                             AAs.add(a);
                         }
                 }
                   EA.setEEs(listconcepts);
                   EA.setAAs(AAs);
     }
        return EA;
    }
}
