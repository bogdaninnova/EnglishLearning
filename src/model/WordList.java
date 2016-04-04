package model;

import controller.Controller;
import org.jdom2.Document;
import org.jdom2.Element;
import view.secondary.Observable;
import view.secondary.Observer;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class WordList implements Observable {

	private List<Observer> observers = new ArrayList<Observer>();

	private boolean isA_Zsorted = true;

	private Element root;
	private Document doc;
	private ArrayList<Word> list;

	public WordList() {
		root = new Element("words");
        doc = new Document(root);
        loadWords();
        sortByNames(true);
	}

	@Override
    public void addObserver(Observer... obs) {
		for (Observer observer : obs)
			observers.add(observer);
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

	private void loadWords()  {
        list = ConverterXML.readListFromFile();
        ListIterator<Word> iter = list.listIterator();
        int i = 0;
        while (iter.hasNext())
        	addWord(i++, iter.next());
	}

	public boolean removeWord(String name) {
		boolean isRemoved = list.remove(getWord(name));
		root.removeChild(name);
		ConverterXML.wright(doc);
		notifyObservers();
		return isRemoved;
	}

	public void addWord(int index, Word word) {
		if (!list.contains(word))
			list.add(index, word);
		root.addContent(ConverterXML.convert(word));
		ConverterXML.wright(doc);
		notifyObservers();
	}

	public void increment(Word word) {

		setPriority(word,
				word.getPrioritet() - Controller.getModel().getSettings().getPriorityDecrement());

		for (Word w : list)
			if (w.equals(word))
				w.setCounter(word.getCounter() + 1);

		root.getChild(word.getNames().get(0)).
				setAttribute("counter", Integer.toString(word.getCounter() + 1));
		ConverterXML.wright(doc);
		notifyObservers();
	}

	public void setPriority(Word word, int prior) {
		for (Word w : list)
			if (w.equals(word))
				w.setPrioriter(prior);

		root.getChild(word.getNames().get(0)).setAttribute("prioritet", Integer.toString(prior));
		ConverterXML.wright(doc);
		notifyObservers();
	}

	public List<Word> getList() {
		return list;
	}

	public Word getWord(String name) {
		Word word;
		ListIterator<Word> iter = list.listIterator();
		while (iter.hasNext()) {
			word = iter.next();
			if (word.getNames().get(0).equals(name))
				return word;
		}
		return null;
	}

	public void sortByNames(boolean isNames) {
		    Word tmp;
		    Collator c = Collator.getInstance(Settings.EN);

		    boolean isFlop;

		    for (int i = 0; i < list.size(); i++)
		        for (int j = i + 1; j < list.size(); j++) {

		        	if (isNames)
			        	isFlop = c.compare(
			        			list.get(i).getNames().get(0),
			        			list.get(j).getNames().get(0)) > 0;
			        else
			        	isFlop = c.compare(
			        			list.get(i).getTranslations().get(0),
			        			list.get(j).getTranslations().get(0)) > 0;

		            if ((isA_Zsorted && isFlop) || (!isA_Zsorted && !isFlop)) {
		                tmp = list.get(i);
		                list.set(i, list.get(j));
		                list.set(j, tmp);
		            }
		        }
		        isA_Zsorted = !isA_Zsorted;
	}

	public Word getRandomWord(List<Word> list, boolean isPrioritetConsidered) {

		if (!isPrioritetConsidered || (!isContainValidWords(list) && isPrioritetConsidered))
			return list.get(new Random().nextInt(list.size()));

		Word temp;
		ListIterator<Word> iter = list.listIterator();
		int counter = 0;
		ArrayList<Integer> hat = new ArrayList<Integer>();

		while (iter.hasNext()) {
			temp = iter.next();
			for (int i = 0; i < temp.getPrioritet(); i++)
				hat.add(counter);
			counter++;
		}
		return list.get(hat.get(new Random().nextInt(hat.size())));
	}

	public boolean isContainValidWords(List<Word> list) {
		for (Word w : list)
			if (w.getPrioritet() > 0)
				return true;
		return false;
	}

	public List<Word> getRandomNWords(int amount, boolean isPrioritetConsidered) {
		ArrayList<Word> newList = new ArrayList<>();


		if (isPrioritetConsidered) {
			int counter = 0;
			for (Word word : list)
				if (word.getPrioritet() > 0)
					counter++;
			if (counter <= amount)
				return list;
		} else {
			if (list.size() <= amount)
				return list;
		}
		System.out.println(list.size());
		System.out.println(amount);

		Word word;
		while (true) {
			word = list.get(new Random().nextInt(list.size()));
			if ((isPrioritetConsidered && (word.getPrioritet() > 0)) && (!newList.contains(word))) {
				newList.add(word);
				amount--;
				if (amount == 0)
					break;
			} else
				if (!newList.contains(word)) {
					newList.add(word);
					amount--;
					if (amount == 0)
						break;
				}
		}
		if (newList.size() != 0)
			return newList;
		//TODO
		System.out.println("All chosen Words is already learned");
			return list;
	}


}