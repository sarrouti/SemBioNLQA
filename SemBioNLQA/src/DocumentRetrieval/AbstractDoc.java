/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DocumentRetrieval;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import static java.lang.System.in;
import java.net.MalformedURLException;
import java.net.URL;
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
public class AbstractDoc {

    private static final String server = "http://gopubmed.org/web/gopubmed/bioasq/pubmed";

    public AbstractDoc() {
    }
    
    public Document FindAbstract(String id) throws MalformedURLException, UnsupportedEncodingException, IOException{
      
        int page = 0;
        int articlesPerPage = 10;

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
         //json={"findPubMedCitations": ["transcription factor foxp3", 80, 5]}
        JSONObject requestObject = new JSONObject();
        JSONArray parameterList = new JSONArray();
        parameterList.add(id+"[PMID]");
        parameterList.add(page);
        parameterList.add(articlesPerPage);
        requestObject.put("findPubMedCitations", parameterList);

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
        
        
        Object parsedResult = JSONValue.parse(response);
        System.out.println(parsedResult);
        JSONObject result = (JSONObject) ((Map) parsedResult).get("result");
        JSONArray documents = (JSONArray) result.get("documents");
      Document doc=new Document();
        String abst="";
        if(documents.size()==0){
         abst="null";
            doc.setPmid("null");
            doc.setTitle("null");
            doc.setAbstract("null");
        }
        else{
            JSONObject document= (JSONObject) documents.get(0);
            abst=  (String) document.get("documentAbstract");
            doc.setPmid((String) document.get("pmid"));
            doc.setTitle((String) document.get("title"));
            doc.setAbstract((String) document.get("documentAbstract"));
        }
                  
        return  doc;
    }
    
}