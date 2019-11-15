
package PassageRetrieval;
public class Sentences implements Comparable<Sentences>{
    String abst="";
    String sent="";
    String link="";
    double score;
    public Sentences() {
    }

    public void setAbst(String abst) {
        this.abst = abst;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getAbst() {
        return abst;
    }

    public String getSent() {
        return sent;
    }

    public double getScore() {
        return score;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    
    

    
   public int compareTo(Sentences compareSent) {
	
		double compareQuantity = ((Sentences) compareSent).getScore(); 
		
		//ascending order
		//return (int) (this.score - compareQuantity);
		
		//descending order
		return Double.compare(compareQuantity, this.score);
}
                        //(compareQuantity - this.score);
		
	}

