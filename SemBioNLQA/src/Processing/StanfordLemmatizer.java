/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processing;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.util.CoreMap;
import java.io.StringReader;
import java.util.ArrayList;

public class StanfordLemmatizer {

    protected StanfordCoreNLP pipeline;

    public StanfordLemmatizer() {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");

        this.pipeline = new StanfordCoreNLP(props);
    }

    public List<Token> tokenPOSlemmatize(String documentText)
    {
        List<Token> tokenset = new ArrayList<Token>();
        // Create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);
        // run all Annotators on this text
        this.pipeline.annotate(document);
        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                Token w=new Token();
                // Retrieve and add the lemma for each word into the
                // list of lemmas
                //lemmas.add(token.get(LemmaAnnotation.class));
                if(token.get(PartOfSpeechAnnotation.class).equals("-LCB-")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals("-LRB-")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals("-RCB-")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals("-RRB-")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals(".")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals(":")==false &&
                       // token.get(PartOfSpeechAnnotation.class).equals("SYM")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals(",")==false  &&
                        token.get(PartOfSpeechAnnotation.class).equals("NN")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals("NNS")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals("IN")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals("PDT")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals("NNS")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals("NNP")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals("NNP")==false &&
                        //token.get(PartOfSpeechAnnotation.class).equals("VBZ")==false &&
                        //token.get(PartOfSpeechAnnotation.class).equals("VBN")==false &&
                        token.get(PartOfSpeechAnnotation.class).equals("DT")==false &&

                        token.get(TextAnnotation.class).toString().length()>1)

                {
                    w.setText(token.get(TextAnnotation.class));
                    w.setPoz(token.get(PartOfSpeechAnnotation.class));
                    w.setLema(token.get(LemmaAnnotation.class));
                    tokenset.add(w);
                }
                
            }
        }
        return tokenset;
    }


    
    
}