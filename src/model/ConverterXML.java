package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ConverterXML {

	public static ArrayList<Word> readListFromFile() {

		ArrayList<Word> list = new ArrayList<Word>();
		Word word;

		  try {
	            SAXBuilder parser = new SAXBuilder();
	            FileReader fr = new FileReader(Settings.XML_FILE_NAME);
	            Document rDoc = parser.build(fr);

	            List<Element> temp = rDoc.getRootElement().getChildren();
	            List<Attribute> tempWord;

	            for (int i = 0; i < temp.size(); ++i) {
	            	tempWord = temp.get(i).getAttributes();

	            	word = new Word(
	            			temp.get(i).getName(),
	            			temp.get(i).getAttributeValue("translation1"),
	            			temp.get(i).getAttributeValue("theme"),
	            			temp.get(i).getAttributeValue("note"));
	            	word.setCounter(Integer.parseInt(temp.get(i).getAttributeValue("counter")));
	            	word.setPrioriter(Integer.parseInt(temp.get(i).getAttributeValue("prioritet")));

	            	String strAtt, strElem;
	            	for (int j = 0; j < tempWord.size(); j++) {

	            		strAtt = tempWord.get(j).getName();
	            		strElem = temp.get(i).getAttributeValue(strAtt);

	            		if (strAtt.substring(0, 4).equals("name")) {
	            			if (!word.isExistName(strElem))
	            				word.addName(strElem);
	            		} else if (strAtt.substring(0, 4).equals("tran"))
	            			if (!word.isExistTranslation(strElem))
	            				word.addTranslation(strElem);
	            	}
	            	list.add(word);
	            }
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
		  return list;
	}

	public static void wright(Document doc) {
        try {
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(Format.getPrettyFormat());
            FileWriter fw = new FileWriter(Settings.XML_FILE_NAME);
            outputter.output(doc, fw);

            fw.close();
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
	}

	public static Element convert(Word word) {
		Element element = new Element(word.getNames().get(0));

		element.setAttribute("note", word.getNote());
		element.setAttribute("theme", word.getTheme());
		element.setAttribute("counter", Integer.toString((word.getCounter())));
		element.setAttribute("prioritet", Integer.toString((word.getPrioritet())));

		ListIterator<String> iter = word.getNames().listIterator();
		int counter = 0;
		while (iter.hasNext())
			element.setAttribute("name" + (++counter), iter.next());
		counter = 0;
		iter = word.getTranslations().listIterator();

		while (iter.hasNext())
			element.setAttribute("translation" + (++counter), iter.next());

		return element;
	}

}
