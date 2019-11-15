/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processing;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author sarrouti
 */
public class MyFindSimilarTermsCall {
  private static final String server = "http://gopubmed.org/web/bioasq/similar/json";

    public MyFindSimilarTermsCall() {
    }
  
  public ArrayList<String> FindSimilarTerms(String term) throws MalformedURLException, UnsupportedEncodingException, IOException{
      List<String> keywords = Arrays.asList(term /*nothing will be found for ASDF*/);

        // Prepare Session URL; do this only once, the session can be used
        // several times.
        URL url = new URL(server);
        StringBuilder sessionURL = new StringBuilder();
        char[] buf = new char[1024];
        Reader is = new InputStreamReader(url.openStream(), "UTF-8");
        for (int r = is.read(buf); r >= 0; r = is.read(buf)) {
            sessionURL.append(buf, 0, r);
        }

        // Build the request JSON object
        // json={"findEntities": ["nitric oxide synthase", 0, 10]}
        JSONObject requestObject = new JSONObject();
        JSONArray parameterList = new JSONArray();
        parameterList.add(keywords);
        requestObject.put("findSimilar", parameterList);

        // Build the HTTP POST Request
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(sessionURL.toString());
        StringPart stringPart = new StringPart("json", requestObject.toJSONString());
        stringPart.setCharSet("utf-8");
        stringPart.setContentType("application/json");
        method.setRequestEntity(new MultipartRequestEntity(new Part[] { stringPart }, method.getParams()));

        // Execute and retrieve result
        client.executeMethod(method);
        String response = method.getResponseBodyAsString();
        //System.out.println(response);

        // Parse result
        Object parsedResult = JSONValue.parse(response);

        JSONArray result = (JSONArray) ((Map) parsedResult).get("result");
//        for (Object object : result) {
//            JSONArray elementList = (JSONArray)object;
//            
//            System.out.println(elementList);            
//        }
        ArrayList<String> SynList=new ArrayList();
        for (int i=0;i<result.size();i++){
        //    System.out.println("AAAAA:   "+result.get(i));
           SynList=(ArrayList) result.get(i);
//            for(int j=0;j<SynList.size();j++)
//                System.out.println(SynList.get(j));   
        }
        return  SynList;
  }
   
}
