package view;

import controller.Controller;
import model.Settings;
import model.Word;
import view.secondary.Observer;
import view.secondary.WordTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ListFrame extends JFrame implements Observer {
	
	private JButton runStopButton = new JButton();
	private JButton testButton = new JButton("Test");
	private JButton addButton = new JButton("Add");
	private JButton quickAddButton;
	private JButton settingsButton = new JButton("Settings");
	
	private final MenuItem stopItem = new MenuItem("Stop");
	private final MenuItem runItem = new MenuItem("Run");
	
	
	public ListFrame() {
		frameCreate();
		setLayout(new BorderLayout());
		add(new JScrollPane(createWordTable()), BorderLayout.CENTER);
		add(createButtonsPanel(), BorderLayout.EAST);
		setTrayIcon();
		pack();
		View.setOnCenter(this);
		Controller.getModel().addObserver(this);

		UIManager.put("Button.font", View.wordFont.deriveFont(16.0f));
		UIManager.put("Label.font", View.wordFont);
		UIManager.put("RadioButton.font", View.wordFont);
		UIManager.put("CheckBox.font", View.wordFont);
		UIManager.put("TextField.font", View.wordFont);
		UIManager.put("ComboBox.font", View.wordFont);
		UIManager.put("TextArea.font", View.wordFont);
		UIManager.put("List.font", View.wordFont);
		
		SwingUtilities.updateComponentTreeUI(this);
		
	}
	
	private WordTable createWordTable() {
		WordTable wordTable = new WordTable();
		addDeleteKeyListener(wordTable);
		addPrioritetEnebledListener(wordTable);
		wordTable.setFont(View.wordFont.deriveFont(28.0f));
		wordTable.getTableHeader().setFont(View.wordFont.deriveFont(28.0f));
		wordTable.setRowHeight(View.screenWidth / 25);
		wordTable.getTableHeader().setBackground(new Color(190 ,190 ,190));
		wordTable.setGridColor(new Color(176, 196, 222));

		return wordTable;
	}
	
	private void frameCreate() {
		setIconImage(Settings.ICON);
		setTitle(Settings.APPLICATION_NAME);
		setResizable(false);
		setVisible(true);
		setRunButtonsState(Controller.getModel().getSettings().isRunWithStart());
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
            public void windowClosing(WindowEvent event) {
            	   View.exitDialog();
            }
             public void windowDeactivated(WindowEvent event) {}
             public void windowDeiconified(WindowEvent event) {}
             public void windowIconified(WindowEvent event) {}
             public void windowOpened(WindowEvent event) {}
             public void windowActivated(WindowEvent event) {}
             public void windowClosed(WindowEvent event) {}
        });
	}
	
	private JPanel createButtonsPanel() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(createAddButton(), c);
		
		c.gridy = 1;
		panel.add(createQuickAddButton(), c);

		c.insets = new Insets(100, 5, 5, 5);
		c.gridy = 2;
		panel.add(createTestButton(), c);

		c.insets = new Insets(5, 5, 5, 5);
		c.gridy = 3;
		panel.add(createRunStopButton(), c);
		
		c.insets = new Insets(100, 5, 5, 5);
		c.gridy = 4;
		panel.add(createSettingsButton(), c);
		
		return panel;
	}
	
	private JButton createSettingsButton() {
		settingsButton.setPreferredSize(View.buttonDimension);
		settingsButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	Controller.getView().getSettingsFrame().open();
			}
		});
		return settingsButton;
	}

	private JButton createRunStopButton() {
		runStopButton.setPreferredSize(View.buttonDimension);
		runStopButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	Controller.getModel().setRunning(!Controller.getModel().isRunning());	
			}
		});
		return runStopButton;
	}
	
	private JButton createTestButton() {
		testButton.setPreferredSize(View.buttonDimension);
		testButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	Controller.getView().getQuestion().open(
	        			Controller.getModel().getWordList().getRandomWord(
								Controller.getModel().getTestingList(),
								Controller.getModel().getSettings().isPrioritetConsidered()));
			}
		});
		return testButton;
	}
	
	private JButton createQuickAddButton() {
		
		quickAddButton = new JButton("Quick Add");//<html><center> Quick </br> Add </center></html>
		quickAddButton.setPreferredSize(View.buttonDimension);
		
		KeyStroke keyQuickAddButton =
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK); 
		
		quickAddButton.getActionMap().put("ctrl_Q",
				new AbstractAction() {  
					private static final long serialVersionUID = 1L;
					public void actionPerformed(ActionEvent e) {     
				    	Controller.getView().getQuickAdd().open();
				    }
				}
		);
		quickAddButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
			.put(keyQuickAddButton, "ctrl_Q"); 

		quickAddButton.setToolTipText("Add new word into vocabulary");
		
		quickAddButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	Controller.getView().getQuickAdd().open();
			}
		});
		return quickAddButton;
	}
	
	private JButton createAddButton() {
		addButton.setPreferredSize(View.buttonDimension);
		addButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	Controller.getView().getAddingFrame().open();
			}
		});
		return addButton;
	}
	
	private void setTrayIcon() {
		
	    if (!SystemTray.isSupported()) 
	    	return;

	    PopupMenu trayMenu = new PopupMenu();
	    
        MenuItem openItem = new MenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListFrame.this.setVisible(true);
                ListFrame.this.setExtendedState(JFrame.NORMAL);
            }
        });
        trayMenu.add(openItem);
       
		    MenuItem addItem = new MenuItem("Quick adding");
		    addItem.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		Controller.getView().getQuickAdd().open();
		    	}
		    });
           trayMenu.add(addItem);
           
           runItem.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
            	   Controller.getModel().setRunning(true);	
               }
           });
           trayMenu.add(runItem);
           
           stopItem.setEnabled(false);
           stopItem.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
            	   Controller.getModel().setRunning(false);	
               }
           });
           trayMenu.add(stopItem);
          
   	    MenuItem settingsItem = new MenuItem("Settings");
   	    settingsItem.addActionListener(new ActionListener() {
   	    	public void actionPerformed(ActionEvent e) {
   	    		Controller.getView().getSettingsFrame().open();
   	    	}
   	    });
   	    trayMenu.add(settingsItem);
           
           MenuItem exitItem = new MenuItem("Exit");
           exitItem.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
            	   View.exitDialog();
               }
           });
           trayMenu.add(exitItem);

           
	    
		final TrayIcon trayIcon = new TrayIcon(Settings.ICON, 
				Settings.APPLICATION_NAME, trayMenu);
	    trayIcon.setImageAutoSize(true);

	    trayIcon.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent evt) {
	        	if (evt.getClickCount() == 2) {
                	SystemTray.getSystemTray().remove(trayIcon);
                	ListFrame.this.setVisible(true);
                	View.setOnCenter(ListFrame.this);
                	ListFrame.this.setState(JFrame.NORMAL);
	        	}
	        }
	    });
	    
	    this.addWindowStateListener(new WindowStateListener() {
	    	public void windowStateChanged(WindowEvent event) {
	    		
	    		if(event.getNewState() == JFrame.ICONIFIED) {
                	try {
                    	SystemTray.getSystemTray().add(trayIcon);
                    	ListFrame.this.setVisible(false);
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                }
                
                if(event.getNewState() == JFrame.NORMAL) {
                	SystemTray.getSystemTray().remove(trayIcon);
                	View.setOnCenter(ListFrame.this);
                	setVisible(true);
                }
            }
        });
	  }

	private void addDeleteKeyListener(final JTable table) {

	    	table.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent event) {
					if (event.getKeyCode() == KeyEvent.VK_DELETE)
				    	if (table.getSelectedRow() != -1) {
				    		if (View.deletingDialog((String) table.getValueAt(table.getSelectedRow(), 0)))
				    			table.clearSelection();
				    	}
				}
				public void keyReleased(KeyEvent event) {}
				public void keyTyped(KeyEvent event) {}
	    	});
	    }
	
	private void addPrioritetEnebledListener(final JTable table) {

    	table.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_E)
			    	if (table.getSelectedRow() != -1) {
			    			for (int row : table.getSelectedRows()) {
					    		Word word = Controller.getModel().getWordList().getWord(
			    						(String) table.getValueAt(row, 0));
					    		if (word.getPrioritet() > 0)
					    			Controller.getModel().getWordList().setPriority(word, -1);
					    		else
					    			Controller.getModel().getWordList().setPriority(word, 100);
			    			}
			    	}
			}
			public void keyReleased(KeyEvent event) {}
			public void keyTyped(KeyEvent event) {}
    	});
    }

	@Override
	public void update() {
		setRunButtonsState(Controller.getModel().isRunning());
		testButton.setEnabled(!Controller.getModel().isQuestedNow());
	}
	
	private void setRunButtonsState(boolean state) {
		if (state) {
			runStopButton.setText("Stop");
		 	stopItem.setEnabled(true);
		 	runItem.setEnabled(false);
		} else {
			runStopButton.setText("Run");
		 	stopItem.setEnabled(false);
		 	runItem.setEnabled(true);
		}
	}
	
}
