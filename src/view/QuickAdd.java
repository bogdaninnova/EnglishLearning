package view;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

import javax.swing.*;

import view.secondary.Field;
import model.Settings;
import model.Word;
import controller.Controller;

public class QuickAdd extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton addButton;
	private Field nameField;
	private Field transField;

	public QuickAdd() {
		frameCreate();
		createComponents();

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		add(nameField, c);
		c.gridy = 1;
		add(transField, c);
		c.gridy = 2;
		add(addButton, c);

		pack();
	}

	private void frameCreate() {
		setIconImage(Settings.ICON);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Quick adding");
		setResizable(false);
		setVisible(false);
	}

	public void open() {
		nameField.update();
		transField.update();
		View.setOnCenter(this);
		addButton.requestFocusInWindow();
		setVisible(true);
	}

	@SuppressWarnings("serial")
	private void createComponents() {
		addButton = new JButton("Add") {
			@Override
			public boolean isDisplayable() {
				return true;
			}
		};
		addButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	add();
			}
		});

		nameField = new Field("EN");
		transField = new Field("RU");
		//nameField.setToolTipText("EN");
		//transField.setToolTipText("RU");

		addEnterKeyListener(nameField);
		addEnterKeyListener(transField);
		addButton.setPreferredSize(View.fieldDimension);
		nameField.setPreferredSize(View.fieldDimension);
		transField.setPreferredSize(View.fieldDimension);

		addFocusListener(nameField, Settings.EN);
		addFocusListener(transField, Settings.RU);


	}

	private void add() {
		String name = nameField.getText();
		String trans = transField.getText();

		if (name.equals(""))
			View.errorBlink(nameField);
		if (trans.equals(""))
			View.errorBlink(transField);
		if (!name.equals("") && !trans.equals("")) {
			Controller.getModel().getWordList().addWord(0, new Word(name, trans, "", ""));
			setVisible(false);
		}
	}

	private void addEnterKeyListener(final JComponent c) {
		c.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER)
					add();
			}
			public void keyReleased(KeyEvent event) {}
			public void keyTyped(KeyEvent event) {}
    	});
    }

	public static void addFocusListener(final JComponent c, final Locale locale) {
		c.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				c.setLocale(locale);
				c.getInputContext().selectInputMethod(locale);

			}
			@Override
			public void focusLost(FocusEvent e) {}
    	});
    }

}
