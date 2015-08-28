package view;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import controller.Controller;
import model.Settings;
import model.Word;

public class Question extends JDialog{

	private static final long serialVersionUID = 1L;
	
	private JTextField field;
	private JLabel label;
	
	private Word word;
	
	public Question() {
		frameCreate();
		add(View.createFlowPanel(createLabel(), createField(), createOkButton()));
		pack();
	}
	
	private void updateLabel() {
		Random rand = new Random();
		System.out.println("Updating");
		if (!word.equals(null))
		if (Controller.getModel().getSettings().isTranslationAsks())
			label.setText(word.getNames().
					get(rand.nextInt(word.getNames().size())) + " ");
		else
			label.setText(word.getTranslations().
					get(rand.nextInt(word.getTranslations().size())) + " ");
	}
	
	private JLabel createLabel() {
		label = new JLabel();
		label.setPreferredSize(View.fieldDimension);
		return label;
	}
	
	private JButton createOkButton() {
		JButton okButton = new JButton("OK");
		okButton.setPreferredSize(View.buttonDimension);
		okButton.addActionListener(new ActionListener() {
 	        public void actionPerformed(ActionEvent e) {
 	        	check();
 	        }
 		});
		return okButton;
	}
	
	private JTextField createField() {
		field = new JTextField();
		field.setPreferredSize(View.fieldDimension);
		addEnterKeyListener(field);
		return field;
	}
	
	public void open(Word w) {
		Controller.getModel().setQuestedNow(true);
		this.word = w;
		updateLabel();
		View.setOnCenter(this);
		field.setText("");
		setVisible(true);
	}
	
	private void frameCreate() {
		setIconImage(Settings.ICON);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Question");
		setResizable(false);
		setAlwaysOnTop(true);
	}
	
	private void check() {
		
		String answer = field.getText();
		ArrayList<String> list;
		if (Controller.getModel().getSettings().isTranslationAsks()) 
			list = word.getTranslations();
		else
			list = word.getNames();
		
		for (String translation : list) 
			if (answer.equalsIgnoreCase(translation)) {
				if (!Controller.getModel().getWordList().isContainValidWords(
						Controller.getModel().getTestingList()))
					Controller.getModel().setRunning(false);
				Controller.getModel().setQuestedNow(false);
				
	     		setVisible(false);
	     		Controller.getModel().getWordList().increment(word);
				return;
			}
		
		View.errorBlink(field);
		
	}

	private void addEnterKeyListener(final JComponent c) {
		c.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER) 
					check();
			}
			public void keyReleased(KeyEvent event) {}
			public void keyTyped(KeyEvent event) {}
    	});
    }
	
}
