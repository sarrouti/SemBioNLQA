/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enswerexractionphase;

import Processing.GenericObject1;
import gov.nih.nlm.nls.skr.GenericObject;

/**
 *
 * @author sarrouti
 */
public class TestMapping {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     GenericObject1 myIntMMObj;
        myIntMMObj = new GenericObject1(100, "", "");
     myIntMMObj.setField("Email_Address", "sarrouti.mourad@gmail.com");
     myIntMMObj.setField("SilentEmail", true);

     myIntMMObj.setField("KSOURCE", "2016AB");//semrep MTI metamap 2014AA
     myIntMMObj.setField("Batch_Command", "metamap -E");
     myIntMMObj.setField("COMMAND_ARGS", "-ANy");
     myIntMMObj.setField("BatchNotes","SKR Web API test");
     StringBuffer buffer = new StringBuffer("Which two drugs are included in the Harvoni pill?"); 
     String bufferStr = buffer.toString();
     myIntMMObj.setField("APIText", bufferStr);
     String result = myIntMMObj.handleSubmission();
     System.out.println(result);
    }
    
}
