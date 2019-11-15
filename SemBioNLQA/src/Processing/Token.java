/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processing;

/**
 *
 * @author sarrouti
 */
public class Token {
    String text;
    String poz;
    String lema;

    public Token() {
    }

    public String getText() {
        return text;
    }

    public String getPoz() {
        return poz;
    }

    public String getLema() {
        return lema;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPoz(String poz) {
        this.poz = poz;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }
    
}
