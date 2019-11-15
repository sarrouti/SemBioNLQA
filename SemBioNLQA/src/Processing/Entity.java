/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processing;

import java.util.List;

/**
 *
 * @author sarrouti
 */
public class Entity {
    String entity;
    String Concept;
    String CUI;
    double score;
    String semantictype; 

   
    
    public Entity() {
    }

    public String getSemantictype() {
        return semantictype;
    }

    public void setSemantictype(String semantictype) {
        this.semantictype = semantictype;
    }
    
   public void setCUI(String CUI) {
        this.CUI = CUI;
    }

    public void setConcept(String Concept) {
        this.Concept = Concept;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getCUI() {
        return CUI;
    }

    public String getConcept() {
        return Concept;
    }

    public String getEntity() {
        return entity;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    
}
