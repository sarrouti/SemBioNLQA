
package PassageRetrieval;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordToSentenceProcessor;
import edu.stanford.nlp.util.StringUtils;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class SentenceStanfordNLP {

    public SentenceStanfordNLP() {
    }
   
    public ArrayList<String> getSentences(String paragraph){
        ArrayList<String> sentenceList;
        /* ** APPROACH 2 ** */
        //// Tokenize
        List<CoreLabel> tokens = new ArrayList<CoreLabel>();
        PTBTokenizer<CoreLabel> tokenizer = new PTBTokenizer<CoreLabel>(new StringReader(paragraph), new CoreLabelTokenFactory(), "");
        while (tokenizer.hasNext()) {
            tokens.add(tokenizer.next());
        }
        //// Split sentences from tokens
        List<List<CoreLabel>> sentences = new WordToSentenceProcessor<CoreLabel>().process(tokens);
        //// Join back together
        int end;
        int start = 0;
        sentenceList = new ArrayList<String>();
        for (List<CoreLabel> sentence: sentences) {
            end = sentence.get(sentence.size()-1).endPosition();
            sentenceList.add(paragraph.substring(start, end).trim());
            start = end;
        }
    //System.out.println(StringUtils.join(sentenceList, " _ "));

    return sentenceList;
    }
    
    
}
