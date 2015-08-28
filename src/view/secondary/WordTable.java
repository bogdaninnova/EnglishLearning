package view.secondary;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

import model.Word;
import controller.Controller;

@SuppressWarnings("serial")
public class WordTable extends JTable {
	
    public WordTable() {
        super(Controller.getModel().getTableModel());

		addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent evt) {
	        	
	        	int selectedRow = getSelectedRow();
	        	int  selectedColumn = getSelectedColumn();
	        	if (selectedColumn == 2) {
	        		Word word = Controller.getModel().getWordList().getWord(
		        			(String) getValueAt(selectedRow, 0));
	        		if (!Controller.getModel().getTestingList().remove(word))
		        		Controller.getModel().getTestingList().add(word);
	        			repaint();
	        	} else if (evt.getClickCount() == 2) {
		        	if (selectedRow != -1) 
		        		Controller.getView().getAddingFrame().open(
			        			Controller.getModel().getWordList().getWord(
			        			(String) getValueAt(selectedRow, 0)));
		        }
	        	
	        	
	        }
	    });
		
		getTableHeader().addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {

		    	if (columnAtPoint(e.getPoint()) == 2) {
		    		
		    		if (Controller.getModel().getTestingList().isEmpty())
		    			Controller.getModel().setTestingList(Controller.getModel().getWordList().getList());
		    		else
		    			Controller.getModel().setTestingList(new ArrayList<Word>());
		    			
		    	} else		    	
		        	Controller.getModel().getWordList().
		        	sortByNames(columnAtPoint(e.getPoint()) == 0);
		    }
		});
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		getTableHeader().setResizingAllowed(false);
		getColumnModel().getColumn(2).setPreferredWidth(20);
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        int column = columnAtPoint(event.getPoint());
        int row = rowAtPoint(event.getPoint());
        if (column != - 1 && row != -1) {
        	String name = (String) this.getValueAt(row, 0);
        	Word word = Controller.getModel().getWordList().getWord(name);
        	return "Test's counter: " + word.getCounter();
        }
        return "";
    }
    
	    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
	        Component c = super.prepareRenderer(renderer, row, column);

	        if (!isRowSelected(row)) {
	        	String name = (String) this.getValueAt(row, 0);
	        	Word word = Controller.getModel().getWordList().getWord(name);
        		c.setBackground(word.getPrioritet() <= 0 ? Color.GRAY: getBackground());
	        }
	        return c;
	    }
}
