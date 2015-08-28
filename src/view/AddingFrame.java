package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListIterator;

import javax.swing.*;

import view.secondary.SynonymsTablePanel;
import controller.Controller;
import model.Settings;
import model.Word;

@SuppressWarnings("serial")
public class AddingFrame extends JFrame {

	private Word word;
	
	private JTextArea noteArea;
	private JCheckBox prioritet;
	
	private SynonymsTablePanel wordsPanel;
	private SynonymsTablePanel translationsPanel;

	public AddingFrame() {
		this.word = new Word("", "", "", "");
		frameCreate();
		pack();
	}
	
	public void open() {
		update(new Word("", "", "", ""));
	}
	
	public void open(Word word) {
		update(word);
	}
	
	private void update(Word word) {
		this.word = word;
		wordsPanel.update(word.getNames());
		translationsPanel.update(word.getTranslations());
		noteArea.setText(word.getNote());
		wordsPanel.resetSelected();
		translationsPanel.resetSelected();
		prioritet.setSelected(word.getPrioritet() > 0);
		View.setOnCenter(this);
		setVisible(true);
	}
	
	private void frameCreate() {
		setIconImage(Settings.ICON);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Word's setting");
		setResizable(false);
		locateItems();
		pack();
	}
	
	private void locateItems() {
		setLayout(new BorderLayout());
		
		wordsPanel = new SynonymsTablePanel("Words", word.getNames(), Settings.EN);
		
		translationsPanel = new SynonymsTablePanel(
				"Translations", word.getTranslations(), Settings.RU);
		
		add(View.createFlowPanel(wordsPanel, translationsPanel), BorderLayout.CENTER);
		add(createSouthPanel(), BorderLayout.SOUTH);

	}
	
	private JPanel createSouthPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(5, 5, 5, 5);
		noteArea = new JTextArea(word.getNote());
		JScrollPane scroll = new JScrollPane(noteArea);
		scroll.setPreferredSize(View.areaDimension);
		
		c.gridx = 0;
		panel.add(new JLabel("Note: "), c);
		
		c.gridy = 1;
		c.gridwidth = 3;
		panel.add(scroll, c);
		c.gridwidth = 2;
		c.gridy = 2;
		c.gridx = 0;
		prioritet = new JCheckBox("Enabled");
		panel.add(prioritet, c);

		c.gridy = 3;
		c.gridx = 1;
		panel.add(View.createFlowPanel(createCancelButton(), createSaveButton()), c);
		return panel;
		
	}
	
	private JButton createCancelButton() {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(View.buttonDimension);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		return cancelButton;
	}
	
	private JButton createSaveButton() {
		JButton saveButton = new JButton("Save");
		saveButton.setPreferredSize(View.buttonDimension);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (wordsPanel.getWordsList().isEmpty())
					View.errorBlink(wordsPanel.field);
				if (translationsPanel.getWordsList().isEmpty())
					View.errorBlink(translationsPanel.field);
				
				if (!wordsPanel.getWordsList().isEmpty() &&
						!translationsPanel.getWordsList().isEmpty()) {

					int index = 0;
					if (!word.getNames().get(0).equals("")) {
						index = Controller.getModel().getWordList().getList().indexOf(word);
						Controller.getModel().getWordList().removeWord(word.getNames().get(0));
					}

					Controller.getModel().getWordList().addWord(index, getWord());
					setVisible(false);
				}
			}
		});
		return saveButton;
	}
	
	private Word getWord() {
		ListIterator<String> wordsIter = wordsPanel.getWordsList().listIterator();
		ListIterator<String> transIter = translationsPanel.getWordsList().listIterator();
			
		Word word = new Word(wordsIter.next(), transIter.next(), 
				"", noteArea.getText());

		if (!prioritet.isSelected())
			word.setPrioriter(0);
		
		while (wordsIter.hasNext())
			word.addName(wordsIter.next());
		
		while (transIter.hasNext())
			word.addTranslation(transIter.next());
		
		return word;
	}
	
}
