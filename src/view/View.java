package view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

import controller.Controller;
import model.Settings;

public class View {

	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	public static final Dimension buttonDimension =
			new Dimension((int) (0.09 * screenWidth), (int) (0.03 * screenHeight));

	public static final Dimension fieldDimension =
			new Dimension((int) (0.15 * View.screenWidth), (int) (0.03 * View.screenHeight));

	public static final Dimension littleFieldDimension =
			new Dimension((int) (0.05 * View.screenWidth), (int) (0.03 * View.screenHeight));

	public static final Dimension areaDimension =
			new Dimension(
					(int) (2 * (0.24 * screenWidth + 20)), (int) (0.03 * View.screenHeight * 3));

	public static final Font wordFont = createFont();


	private ListFrame listFrame;
	private SettingsFrame settingsFrame;
	private QuickAdd quickAdd;
	private AddingFrame addingFrame;
	private Question question;

	public View() {

		setListFrame(new ListFrame());
		setSettingsFrame(new SettingsFrame());
		setQuickAdd(new QuickAdd());
		setAddingFrame(new AddingFrame());
		setQuestion(new Question());
	}

	private static Font createFont() {
		Font font = null;
		try {
			font = Font.createFont(
					Font.TRUETYPE_FONT, new File("ComicSans.ttf")).deriveFont(25f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("ComicSans.ttf")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(FontFormatException e) {
            e.printStackTrace();
        }

		return font;
	}

	public ListFrame getListFrame() {
		return listFrame;
	}

	private void setListFrame(ListFrame listFrame) {
		this.listFrame = listFrame;
	}

	public static JPanel createFlowPanel(Component... c) {
		JPanel panel = new JPanel();
		FlowLayout layout = new FlowLayout();
		panel.setLayout(layout);

		for (int i = 0; i < c.length; i++)
			panel.add(c[i]);
		return panel;
	}

	public static JPanel createDownPanel(Component... c) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		for (int i = 0; i < c.length; i++) {
			gbc.gridy = i;
			panel.add(c[i], gbc);
		}
		return panel;
	}

	public static void setOnCenter(Window frame) {
		frame.setLocation(
				(screenWidth - frame.getWidth()) / 2,
				(screenHeight - frame.getHeight()) / 2);
	}

	public SettingsFrame getSettingsFrame() {
		return settingsFrame;
	}

	public void setSettingsFrame(SettingsFrame settingsFrame) {
		this.settingsFrame = settingsFrame;
	}

	public QuickAdd getQuickAdd() {
		return quickAdd;
	}

	public void setQuickAdd(QuickAdd quickAdd) {
		this.quickAdd = quickAdd;
	}

	public AddingFrame getAddingFrame() {
		return addingFrame;
	}

	public void setAddingFrame(AddingFrame addingFrame) {
		this.addingFrame = addingFrame;
	}

	public static void errorBlink(final JTextField field) {
		field.setBackground(Color.red);
		//Toolkit.getDefaultToolkit().beep();
		new Timer().schedule(new TimerTask() {
			public void run() {
				field.setBackground(Color.white);
			}
		}, 100);
	}

	public static void exitDialog() {
	   	  Object[] options = { "OK", "Cancel" };
	   	  int choice = JOptionPane.showOptionDialog(null, "You really want to quit?", "Quit?",
	   	      JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
	   	      new ImageIcon(Settings.ICON), options, options[0]);

	   	  if (choice == JOptionPane.YES_OPTION)
	   	    System.exit(0);
		}

	public static boolean deletingDialog(String word) {
	   	  Object[] options = { "YES", "NOW" };
	   	  int choice = JOptionPane.showOptionDialog(null,
	   			  "You really want delete word \"" + word + "\"?", "Delete?",
	   			  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
	   			  new ImageIcon(Settings.ICON), options, options[0]);

	   	  if (choice == JOptionPane.YES_OPTION)
	   		return Controller.getModel().getWordList().removeWord(word);
	   	  return false;
		}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
