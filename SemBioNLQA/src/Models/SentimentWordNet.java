
package Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class SentimentWordNet {
    private Map<String, Double> dictionary;
        
	public SentimentWordNet( ) throws IOException {
		
// This is our main dictionary representation
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();    
	    
	    
      //  String pathToSWN="E:\\these\\question answering\\Answer extraction Phase\\home\\swn\\www\\admin\\dump\\SentiWordNet_3.0.0_20130122.txt";
		
        String pathToSWN=request.getRealPath("WEB-INF/resources/files/SentiWordNet_3.0.0_20130122.txt");
        
        dictionary = new HashMap<String, Double>();

		// From String to list of doubles.
		HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();

		BufferedReader csv = null;
		try {
			csv = new BufferedReader(new FileReader(pathToSWN));
			int lineNumber = 0;

			String line;
			while ((line = csv.readLine()) != null) {
				lineNumber++;

				// If it's a comment, skip this line.
				if (!line.trim().startsWith("#")) {
					// We use tab separation
					String[] data = line.split("\t");
					String wordTypeMarker = data[0];

					// Example line:
					// POS ID PosS NegS SynsetTerm#sensenumber Desc
					// a 00009618 0.5 0.25 spartan#4 austere#3 ascetical#2
					// ascetic#2 practicing great self-denial;...etc

					// Is it a valid line? Otherwise, through exception.
					if (data.length != 6) {
						throw new IllegalArgumentException(
								"Incorrect tabulation format in file, line: "
										+ lineNumber);
					}

					// Calculate synset score as score = PosS - NegS
					Double synsetScore = Double.parseDouble(data[2])
							- Double.parseDouble(data[3]);

					// Get all Synset terms
					String[] synTermsSplit = data[4].split(" ");

					// Go through all terms of current synset.
					for (String synTermSplit : synTermsSplit) {
						// Get synterm and synterm rank
						String[] synTermAndRank = synTermSplit.split("#");
						String synTerm = synTermAndRank[0] + "#"
								+ wordTypeMarker;

						int synTermRank = Integer.parseInt(synTermAndRank[1]);
						// What we get here is a map of the type:
						// term -> {score of synset#1, score of synset#2...}

						// Add map to term if it doesn't have one
						if (!tempDictionary.containsKey(synTerm)) {
							tempDictionary.put(synTerm,
									new HashMap<Integer, Double>());
						}

						// Add synset link to synterm
						tempDictionary.get(synTerm).put(synTermRank,
								synsetScore);
					}
				}
			}

			// Go through all the terms.
			for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary
					.entrySet()) {
				String word = entry.getKey();
				Map<Integer, Double> synSetScoreMap = entry.getValue();

				// Calculate weighted average. Weigh the synsets according to
				// their rank.
				// Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
				// Sum = 1/1 + 1/2 + 1/3 ...
				double score = 0.0;
				double sum = 0.0;
				for (Map.Entry<Integer, Double> setScore : synSetScoreMap
						.entrySet()) {
					score += setScore.getValue() / (double) setScore.getKey();
					sum += 1.0 / (double) setScore.getKey();
				}
				score /= sum;

				dictionary.put(word, score);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (csv != null) {
				csv.close();
			}
		}
	}

	public double extract(String word) {
            
         Double total = new Double(0);
        if(dictionary.get(word+"#n") != null)
             total = dictionary.get(word+"#n")+ total;
        if(dictionary.get(word+"#a") != null)
            total = dictionary.get(word+"#a") + total;
        if(dictionary.get(word+"#r") != null)
            total = dictionary.get(word+"#r") + total;
        if(dictionary.get(word+"#v") != null)
            total = dictionary.get(word+"#v") + total;
        return total;
	}
        public Double extract2(String word, String tail) {
        if (tail.contains("NN") || tail.contains("NNS")
            || tail.contains("NNP")
            || tail.contains("NNPS"))
        return dictionary.get(word + "#n");    
        else if (tail.contains("VB") || tail.contains("VBD")
            || tail.contains("VBG") || tail.contains("VBN")
            || tail.contains("VBP") || tail.contains("VBZ")) 
        return dictionary.get(word + "#v"); 
        else if (tail.contains("JJ") || tail.contains("JJR")
            || tail.contains("JJS"))
        return dictionary.get(word + "#a");
        else if (tail.contains("RB") || tail.contains("RBR")
            || tail.contains("RBS"))
        return dictionary.get(word + "#r");
        else
        return null;}
        
        
}
