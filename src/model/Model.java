package model;

import view.secondary.Observable;
import view.secondary.Observer;
import view.secondary.WordTableModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model implements Observable {
	
	private List<Observer> observers = new ArrayList<>();
	
	private WordList wordList;
	private WordTableModel tm;
	private static boolean isRunning;
	private static boolean isQuestedNow = false;
	private Settings settings;
	private List<Word> testingList = new ArrayList<>();
	
	private Clock clock = new Clock();
		
	public Model() {
		setSettings(new Settings());
		setWordList(new WordList());
		setTableModel(new WordTableModel(wordList.getList()));
		wordList.addObserver(tm);
		addObserver(clock);
		setRunning(false);
	}

	public WordTableModel getTableModel() {
		return tm;
	}

	private void setTableModel(WordTableModel tm) {
		this.tm = tm;
	}

	public WordList getWordList() {
		return wordList;
	}

	private void setWordList(WordList wordList) {
		this.wordList = wordList;
	}

	public static boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		clock.setFirst();
		if (testingList.size() == 0)
			setTestingList(wordList.getRandomNWords(
					settings.getTestingWord(),
					settings.isPrioritetConsidered()));
		Model.isRunning = isRunning;
		notifyObservers();
	}

	@Override
	public void addObserver(Observer... obs) {
		Collections.addAll(observers, obs);
	}

	@Override
	public boolean removeObserver(Observer o) {
		return observers.remove(o);
	}

	@Override
	public void notifyObservers() {
        for (Observer observer : observers) 
            observer.update();
	}

	public static boolean isQuestedNow() {
		return isQuestedNow;
	}

	public void setQuestedNow(boolean isQuestedNow) {
		Model.isQuestedNow = isQuestedNow;
		notifyObservers();
	}

	public Settings getSettings() {
		return settings;
	}

	private void setSettings(Settings settings) {
		this.settings = settings;
	}

	public List<Word> getTestingList() {
		return testingList;
	}

	public void setTestingList(List<Word> list) {
		this.testingList = list;
	}

}