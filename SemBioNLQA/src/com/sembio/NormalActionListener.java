package com.sembio;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class NormalActionListener implements ActionListener{
   boolean hidden;
   int i=0;
	@Override
	public void processAction(ActionEvent event)
		throws AbortProcessingException {

		System.out.println("Any use case here?");

	}
	public void hideOrShow(){

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

}
