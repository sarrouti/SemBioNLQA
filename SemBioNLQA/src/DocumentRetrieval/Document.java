
package DocumentRetrieval;

/**
 *
 * @author sarrouti
 */
public class Document {
    String Pmid;
    String Title;
    String Abst;
    int index;

    public Document() {
    }

    public Document(String Pmid, String Title, String Abstract) {
        this.Pmid = Pmid;
        this.Title = Title;
        this.Abst = Abstract;
    }

    public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getPmid() {
        return Pmid;
    }

    public String getTitle() {
        return Title;
    }

    public String getAbstract() {
        return Abst;
    }

    public void setPmid(String Pmid) {
        this.Pmid = Pmid;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setAbstract(String Abstract) {
        this.Abst = Abstract;
    }
    
}
