/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author sarrouti
 */
public class concept {
    String concept;
    double idf;

    public concept() {
    }

    public String getConcept() {
        return concept;
    }

    public double getIdf() {
        return idf;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public void setIdf(double idf) {
        this.idf = idf;
    }
    
}
