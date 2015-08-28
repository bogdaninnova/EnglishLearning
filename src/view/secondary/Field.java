package view.secondary;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

import view.View;

@SuppressWarnings("serial")
public class Field extends JTextField implements FocusListener {
	 		
	private final String hint;
	private boolean showingHint;

	public Field(final String hint) {
		super(hint);
		this.hint = hint;
		this.showingHint = true;

		super.setText(hint);
		super.setForeground(Color.LIGHT_GRAY);
		super.addFocusListener(this);
		setFont(View.wordFont);
	  }

	  @Override
	  public void focusGained(FocusEvent e) {
		  if(this.getText().isEmpty()) {
			  super.setText("");
			  super.setForeground(Color.BLACK);
			  showingHint = false;
		  }
	  }
	  
	  @Override
	  public void focusLost(FocusEvent e) {
		  if(this.getText().isEmpty()) {
	    	super.setText(hint);
	    	super.setForeground(Color.LIGHT_GRAY);
	    	showingHint = true;
		  }
	  }
	  
	  public void update() {
		  setText("");
		  focusLost(null);
	  }

	  @Override
	  public String getText() {
	    return showingHint ? "" : super.getText();
	  }

}
