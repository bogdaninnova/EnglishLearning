package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.Settings;
import controller.Controller;

public class SettingsFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	private JTextField minutesTextField = new JTextField();
	private JTextField secondsTextField = new JTextField();
	private JTextField testingWordsTextField = new JTextField();
	private JButton closeButton;
	private JRadioButton translationAsksRadio;
	private JRadioButton wordAsksRadio;
	private JCheckBox isRunWithStart;
	private JCheckBox isPrioritetConsidered;

	private JComboBox<Integer> priorityComboBox;
		
	public SettingsFrame() {
		frameCreate();

		add(View.createDownPanel(
				createDelayPanel(),
				createLangPanel(),
				createPriorityDecrementPanel(),
				createTestingWordsPanel(),
				createIsRunnungPanel(),
				createIsPrioritetConsidered(),
				View.createFlowPanel(closeButtonCreate("Close"), closeButtonCreate("Save"))));
		
		pack();
	}
	
	private void frameCreate() {
		setIconImage(Settings.ICON);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Settings");
		setResizable(false);
	}
	
	public void open() {
		setSettings();
		View.setOnCenter(this);
		setVisible(true);
	}
	
	private JPanel createDelayPanel() {
		minutesTextField.setPreferredSize(View.littleFieldDimension);
		secondsTextField.setPreferredSize(View.littleFieldDimension);

		return View.createFlowPanel(
				new JLabel("Delay: "),
				minutesTextField, new JLabel("m "),
				secondsTextField, new JLabel("s"));
	}
	
	private JPanel createTestingWordsPanel() {
		testingWordsTextField.setPreferredSize(View.littleFieldDimension);
		return View.createFlowPanel(new JLabel("Amount of testing words: "), testingWordsTextField);
	}
	
	private void setSettings() {
		int delay = Controller.getModel().getSettings().getDelay() / 1000;//seconds
		minutesTextField.setText(Integer.toString(delay / 60));
		delay -= (delay / 60) * 60;
		secondsTextField.setText(Integer.toString(delay));
		priorityComboBox.setSelectedIndex(
				Controller.getModel().getSettings().getPriorityDecrement());
		isRunWithStart.setSelected(
				Controller.getModel().getSettings().isRunWithStart());
		isPrioritetConsidered.setSelected(
				Controller.getModel().getSettings().isPrioritetConsidered());
		translationAsksRadio.setSelected(
				Controller.getModel().getSettings().isTranslationAsks());
		wordAsksRadio.setSelected(
				!Controller.getModel().getSettings().isTranslationAsks());
		testingWordsTextField.setText(Integer.toString(
				Controller.getModel().getSettings().getTestingWord()));
	}
	
	private int getNewDelay() {
		
    	int delay = 0;
    	if (!minutesTextField.getText().equals(""))
    		delay += Integer.valueOf(minutesTextField.getText()) * 60;
    	if (!secondsTextField.getText().equals(""))
    		delay += Integer.valueOf(secondsTextField.getText());
    	if (delay == 0) return 1000;

    	return 1000 * delay;
	}
	
	private JPanel createLangPanel() {
		createRadioButtons();
		JPanel panel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		panel.add(new JLabel("What's asks: "), c);
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		panel.add(wordAsksRadio, c);
		c.gridy = 1;
		panel.add(translationAsksRadio, c);
		return panel;
	}
	
	private JCheckBox createIsRunnungPanel() {
		isRunWithStart = new JCheckBox("Run testing with start");
		isRunWithStart.setSelected(Controller.getModel().getSettings().isRunWithStart());
		return isRunWithStart;
	}
	
	private JCheckBox createIsPrioritetConsidered() {
		isPrioritetConsidered = new JCheckBox("Prioritet Considered");
		isPrioritetConsidered.setSelected(
				Controller.getModel().getSettings().isPrioritetConsidered());
		return isPrioritetConsidered;
	}
	
	private JPanel createPriorityDecrementPanel() {
		int i = 0;
		Integer[] items = new Integer[101];
		while (i < items.length)
			items[i] = i++;
		
		priorityComboBox = new JComboBox<Integer>(items);
		return View.createFlowPanel(new JLabel("Priortiy decrement (%): "), priorityComboBox);
	}
	
	
	
	private JButton closeButtonCreate(final String text) {
		closeButton = new JButton(text);
		closeButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	setVisible(false);
	        	if (text.equals("Save"))
	        		savePreferences();
			}
		});
		closeButton.setPreferredSize(View.buttonDimension);
		return closeButton;
	}
	
	private void createRadioButtons() {

		translationAsksRadio = new JRadioButton("Translation");
		wordAsksRadio = new JRadioButton("Word");
		
		if (Controller.getModel().getSettings().isTranslationAsks())
			translationAsksRadio.setSelected(true);
		else 
			wordAsksRadio.setSelected(true);
		
		translationAsksRadio.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	wordAsksRadio.setSelected(false);
	        	translationAsksRadio.setSelected(true);
			}
		});
		
		wordAsksRadio.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	translationAsksRadio.setSelected(false);
	        	wordAsksRadio.setSelected(true);
			}
		});
	}
	
	private void savePreferences() {
		
		Controller.getModel().getSettings().setTranslationAsks(
				translationAsksRadio.isSelected());
		
		Controller.getModel().getSettings().setRunWithStart(
				isRunWithStart.isSelected());
		
		Controller.getModel().getSettings().setPrioritetConsidered(
				isPrioritetConsidered.isSelected());
		
		Controller.getModel().getSettings().setDelay(getNewDelay());
		
		Controller.getModel().getSettings().setPriorityDecrement(
				priorityComboBox.getSelectedIndex());
		try {
			int res = Integer.valueOf(testingWordsTextField.getText());
			if (res <= 0) throw new NumberFormatException();
			Controller.getModel().getSettings().setTestingWord(res);
		} catch(NumberFormatException e) {
			//TODO maybe nothing
		}
		
	}
	
}
