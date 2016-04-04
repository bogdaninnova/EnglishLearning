package model;

import java.util.ArrayList;

public class Word {

	private ArrayList<String> names;
	private ArrayList<String> translations;

	private String theme;
	private String note;

	private int prioritet;
	private int counter;

	public Word(String name, String translation, String theme, String note) {
		this.setTheme(theme);
		this.setNote(note);
		this.names = new ArrayList<String>();
		this.translations = new ArrayList<String>();
		addName(name);
		addTranslation(translation);
		counter = 0;
		prioritet = 100;

	}

	public boolean isExistName(String name) {
		return (names.indexOf(name) != -1);
	}

	public boolean isExistTranslation(String translation) {
		return (translations.indexOf(translation) != -1);
	}

	public void addName(String name) {
		this.names.add(name);
	}

	public void addTranslation(String translation) {
		this.translations.add(translation);
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public ArrayList<String> getTranslations() {
		return translations;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getPrioritet() {
		return prioritet;
	}

	public void setPrioriter(int prior) {
		this.prioritet = prior;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
