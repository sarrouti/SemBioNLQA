package enswerexractionphase;

import java.util.ArrayList;

public class ExactAnswerF {

	private String answer;
	private ArrayList<String> synonyms;
	private double score;
	
	public ExactAnswerF() {
		
	}
	
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public ArrayList<String> getSynonyms() {
		return synonyms;
	}
	public void setSynonyms(ArrayList<String> synonyms) {
		this.synonyms = synonyms;
	}
	
	
}
