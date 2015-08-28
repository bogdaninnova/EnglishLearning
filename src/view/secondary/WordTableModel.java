package view.secondary;

import java.util.*;

import javax.swing.table.AbstractTableModel;

import controller.Controller;
import model.Word;

@SuppressWarnings("serial")
public class WordTableModel extends AbstractTableModel implements Observer{

	private List<Word> list;
		
	public WordTableModel(List<Word> words) {
		list = words;
		update();
	}
	
	@Override
	public void update() {
		fireTableRowsInserted(list.size() - 1, list.size() - 1);
		fireTableDataChanged();
	}

	//@Override
    public int getRowCount() {
        return list.size();
    }

    //@Override
    public int getColumnCount() {
        return 3;
    }
    
    //@Override
    public Object getValueAt(int r, int c) {
    	if (c == 0) return list.get(r).getNames().get(0);
    	if (c == 1) return list.get(r).getTranslations().get(0);
    	else return Controller.getModel().getTestingList().contains(list.get(r));
    }
    
    @Override
    public String getColumnName(int c) {
    	if (c == 0) return "Word";
    	if (c == 1) return "Translation";
    	else return "Testing";
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            default:
                return Boolean.class;
        }
    }

}