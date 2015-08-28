package view.secondary;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import view.QuickAdd;
import view.View;

@SuppressWarnings("serial")
public class SynonymsTablePanel extends JTable {

	private final SelectedListCellRenderer selectionColorRED =
			new SelectedListCellRenderer(Color.RED);
	private final SelectedListCellRenderer selectionColorBLUE =
			new SelectedListCellRenderer(Color.cyan);
	
	private List<String> wordsList;
	private JList<String> list;
	private final String name;
	public JTextField field = new JTextField();
	private JButton addButton = new JButton("OK");
	private JLabel nameLabel;
	
	private String wordChanging = "";
	
	private Locale locale;
	
	public SynonymsTablePanel(String name, List<String> wordsList, Locale locale) {
		this.wordsList = new ArrayList<String>();
		if (!wordsList.get(0).equals("")) 
			this.wordsList.addAll(wordsList);
		this.name = name;
		this.locale = locale;
		create();
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
	}
	
	public void resetSelected() {
		list.clearSelection();
	}
	
	public void update(List<String> wordsList) {
		this.wordsList = new ArrayList<String>();
		if (!wordsList.get(0).equals("")) 
			this.wordsList.addAll(wordsList);
		((wordListModel) list.getModel()).update();
	}
	
	private void create() {

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridwidth = 2;
		
		nameLabel = new JLabel(name);
		this.add(nameLabel, c);
		c.gridwidth = 1;
		c.gridy = 1;
		this.add(field, c);
		c.gridx = 1;
		this.add(addButton, c);
		c.gridwidth = 2;
		c.gridy = 2;
		c.gridx = 0;
		JScrollPane scroll = new JScrollPane(listCreate());
		scroll.setPreferredSize(
				new Dimension(View.fieldDimension.width + View.buttonDimension.width + 10,
						View.fieldDimension.height * 3));
		this.add(scroll, c);
		
		addButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	add();
			}
		});
		
		addDeleteKeyListener(list);
		
		addButton.setPreferredSize(View.buttonDimension);
		field.setPreferredSize(View.fieldDimension);
		addEnterKeyListener(field);
		QuickAdd.addFocusListener(field, locale);
				
		setPreferredSize(
				new Dimension((int)((scroll.getPreferredSize().width) + 15),
						(View.fieldDimension.height + 
								scroll.getPreferredSize().height + 
								nameLabel.getPreferredSize().height) 
								+ 75));
	}
	
	private JList<String> listCreate() {
		
		list = new JList<String>(new wordListModel());
		list.setLayoutOrientation(JList.VERTICAL);
		addMouseWordChangeListener();
		list.setCellRenderer(selectionColorBLUE);
		list.setSelectedIndex(0);
		
		return list;
	}
	
	private void add() {
		String newWord = field.getText();
		int index = 0;
		
		if (!wordsList.contains(newWord)) {
			if (wordsList.contains(wordChanging)) {
				index = wordsList.indexOf(wordChanging);
				wordsList.remove(wordChanging);
				wordChanging = "";
			}
			if (!newWord.equals(""))	{
						wordsList.add(index, newWord);
					((wordListModel) list.getModel()).update();
			}
		}
			field.setText("");
			list.setCellRenderer(selectionColorBLUE);
			
	}
	
	public void remove() {
		int selected = list.getSelectedIndex();
		if ((selected != -1) && (!wordsList.isEmpty())) {
			resetSelected();
			wordsList.remove(selected);
			((wordListModel) list.getModel()).update();
		}
	}
	
	public void editElement(int index, String word) {
		wordsList.add(index, word);
		((wordListModel) list.getModel()).update();
	}
	
	public List<String> getWordsList() {
		return wordsList;
	}
	
	private void addDeleteKeyListener(final JComponent c) {

	    	c.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent event) {
					if (event.getKeyCode() == KeyEvent.VK_DELETE)
						if (list.getSelectedIndex() != -1)
							remove();
				}

				@Override
				public void keyReleased(KeyEvent event) {}

				@Override
				public void keyTyped(KeyEvent event) {}
	    	});
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
	
	private class wordListModel extends AbstractListModel<String> {
		 
		private static final long serialVersionUID = 1L;

		public void update() {
            fireContentsChanged(this, 0, 0);
        }
 
        @Override
        public int getSize() {
            return wordsList.size();
        }
 
        @Override
        public String getElementAt(int index) {
            return wordsList.get(index);
        }
    }
	
	private void addMouseWordChangeListener() {
		list.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent evt) {
	        	if (evt.getClickCount() == 2) {
	        		list.setCellRenderer(selectionColorRED);
	        		field.setText(list.getSelectedValue());
	        		wordChanging = list.getSelectedValue();
	        	} else if (evt.getClickCount() == 1) {
	        		list.setCellRenderer(selectionColorBLUE);
	        		if (!wordChanging.equals(""))
	        			field.setText("");
	        		wordChanging = "";
	        	}
	        }
	    });
	}
	
	private class SelectedListCellRenderer extends DefaultListCellRenderer {

		private Color color;
		
		public SelectedListCellRenderer(Color color) {
			this.color = color;
		}
				
		@Override
	     public Component getListCellRendererComponent(
    		 @SuppressWarnings("rawtypes")
    		 JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(
	        		 list, value, index, isSelected, cellHasFocus);
				if (isSelected) 
					c.setBackground(color);
				return c;
	     }
	}
	
}