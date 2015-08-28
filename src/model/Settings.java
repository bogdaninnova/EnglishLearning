package model;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

import controller.Controller;

public class Settings {

	private Preferences prefs = Preferences.userNodeForPackage(model.Model.class);

	private static final String PREF_DELAY = "DELAY_VALUE";
	private static final String PREF_LANGASKS = "ASKS_VALUE";
	private static final String PREF_RUNNING = "RUNNING_VALUE";
	private static final String PREF_PRIORITET = "PRIORITET_VALUE";
	private static final String PREF_PRIOR_DECREMENT = "PRIOR_DECREMENT_VALUE";
	private static final String PREF_TESTING_WORDS = "TESTING_WORDS_VALUE";

	public static final Image ICON = Toolkit.getDefaultToolkit().getImage("icon.png");
	public static final String APPLICATION_NAME = "English Words Learning";
	public static final String XML_FILE_NAME = "words.xml";

	public static final Locale EN = new Locale("en", "US");
	public static final Locale RU = new Locale("ru", "RU");

	private int delay;//ms
	private boolean isRunWithStart;
	private boolean isTranslationAsks;
	private boolean isPrioritetConsidered;
	private int priorityDecrement;
	private int testingWord;

	public Settings() {
		setRunWithStart(Boolean.valueOf(prefs.get(PREF_RUNNING, "false")));
		setDelay(Integer.valueOf(prefs.get(PREF_DELAY, "10000")));
		setTranslationAsks(Boolean.valueOf(prefs.get(PREF_LANGASKS, "true")));
		setPrioritetConsidered(Boolean.valueOf(prefs.get(PREF_PRIORITET, "true")));
		setPriorityDecrement(Integer.valueOf(prefs.get(PREF_PRIOR_DECREMENT, "1")));
		setTestingWord(Integer.valueOf(prefs.get(PREF_TESTING_WORDS, "10")));
		if (isRunWithStart)
			runTasks();
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		prefs.put(PREF_DELAY, Integer.toString(delay));
		this.delay = delay;
	}

	public boolean isRunWithStart() {
		return isRunWithStart;
	}

	public void setRunWithStart(boolean isRunning) {
		prefs.put(PREF_RUNNING, String.valueOf(isRunning));
		this.isRunWithStart = isRunning;
	}

	public boolean isTranslationAsks() {
		return isTranslationAsks;
	}

	public void setTranslationAsks(boolean isTranslationAsks) {
		prefs.put(PREF_LANGASKS, String.valueOf(isTranslationAsks));
		this.isTranslationAsks = isTranslationAsks;
	}

	private void runTasks() {
		new Timer().schedule(new TimerTask() {
			public void run() {
				while (true) {
					if (Controller.getView() != null) {
						Controller.getModel().setRunning(true);
						break;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, 0);
	}

	public boolean isPrioritetConsidered() {
		return isPrioritetConsidered;
	}

	public void setPrioritetConsidered(boolean isPrioritetConsidered) {
		prefs.put(PREF_PRIORITET, String.valueOf(isPrioritetConsidered));
		this.isPrioritetConsidered = isPrioritetConsidered;
	}

	public int getPriorityDecrement() {
		return priorityDecrement;
	}

	public void setPriorityDecrement(int priorityDecrement) {
		prefs.put(PREF_PRIOR_DECREMENT, Integer.toString(priorityDecrement));
		this.priorityDecrement = priorityDecrement;
	}

	public int getTestingWord() {
		return testingWord;
	}

	public void setTestingWord(int testingWord) {
		this.testingWord = testingWord;
		prefs.put(PREF_TESTING_WORDS, Integer.toString(testingWord));

	}
}
