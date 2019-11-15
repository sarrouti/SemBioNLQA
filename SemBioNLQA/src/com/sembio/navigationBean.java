/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sembio;

import java.io.Serializable;
 

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
 
/**
 * Simple navigation bean
 * @author itcuties
 *
 */
@ManagedBean
@SessionScoped
public class navigationBean implements Serializable {
	private boolean hidden;
	int i=1;
    private static final long serialVersionUID = 1520318172495977648L;
 
    /**
     * Redirect to login page.
     * @return Login page name.
     */
    
    public void hideOrShow(){
        System.out.println("ok");
		 
        if (!hidden)
        {
           i++; 
           hidden=true;
        }
        else
        {
           i++; 
           hidden=false;
        }
		 
		   }
    
    public String redirectToLogin() {
        return "/login.xhtml";
    }
     
    /**
     * Go to login page.
     * @return Login page name.
     */
    public String toLogin() {
        return "/index.xhtml";
    }
     
    /**
     * Redirect to info page.
     * @return Info page name.
     */
    public String redirectToInfo() {
        return "/info.xhtml";
    }
     
    /**
     * Go to info page.
     * @return Info page name.
     */
    public String toInfo() {
        return "/info.xhtml";
    }
     
    /**
     * Redirect to welcome page.
     * @return Welcome page name.
     */
    public String redirectToWelcome() {
        return "/welcome.xhtml";
    }
     
    /**
     * Go to welcome page.
     * @return Welcome page name.
     */
    public String toWelcome() {
        return "/welcome.xhtml";
    }
   
    public String redirectToResults() {  
        return "/results.xhtml";
    }
  
    public String toResults() {
        return "/results.xhtml";
    }
     public String redirectToAboutUs() {  
        return "/AboutUs.xhtml";
    }
  
    public String toAboutUs() {
        return "/AboutUs.xhtml";
    }
     public String redirectToPublications() {  
        return "/Publications.xhtml";
    }
  
    public String toPublications() {
        return "/Publications.xhtml";
    }
    public String redirectToSemBioNLQA() {  
        return "/search.xhtml";
    }
    public String toSemBioNLQA() {
        return "/search.xhtml";
    }
    public String redirectToIndex() {  
        return "/index.xhtml";
    }
    public String toIndex() {
        return "/index.xhtml";
    }

    public String redirectToFutureProjects() {  
        return "/FutureProjetcs.xhtml";
    }
  
    public String toFutureProjects() {
        return "/FutureProjetcs.xhtml";
    }
    
    public String redirectToForums() {  
        return "/Forums.xhtml";
    }
  
    public String toForums() {
        return "/Forums.xhtml";
    }
    public String redirectToResources() {  
        return "/Resources.xhtml";
    }
  
    public String toResources() {
        return "/Resources.xhtml";
    }
    
	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
     
}
