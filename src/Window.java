import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.SwingConstants;

import java.awt.CardLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import com.thoughtworks.xstream.XStream;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;


public class Window extends JFrame  {
	private EventContainer events;
	private JPanel contentPane;
	private JList eventList;
	private final Action action = new SwingAction();
	private JButton deleteEventButton;
	private final Action action_1 = new SwingAction_2();
	
	private int selectedItemIndex = -1;
	private JMenuBar menuBar;
	private JMenu mnFileMenu;
	private JMenuItem saveButton;
	private JMenuItem loadButton;
	private final Action saveAction = new SaveAction();
	private final Action loadAction = new LoadAction();

	/**
	 * Create the frame.
	 */
	public Window() {
		setTitle("Organizer");
		
		//Code();
		XStream xst = new XStream();
		events = new EventContainer(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 514, 353);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFileMenu = new JMenu("File");
		menuBar.add(mnFileMenu);
		
		saveButton = new JMenuItem("Save");
		saveButton.setAction(saveAction);
		mnFileMenu.add(saveButton);
		
		loadButton = new JMenuItem("Load");
		loadButton.setAction(loadAction);
		mnFileMenu.add(loadButton);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton addEventButton = new JButton("New button");
		addEventButton.setBounds(295, 11, 193, 53);
		addEventButton.setAction(action);
		contentPane.add(addEventButton);
		
		
		eventList = new JList();
		eventList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()){
					System.out.println(eventList.getSelectedIndex());
					selectedItemIndex = eventList.getSelectedIndex();
				}
			}
		});
		
		deleteEventButton = new JButton("Usu\u0144 zdarzenie");
		deleteEventButton.setAction(action_1);
		deleteEventButton.setBounds(295, 75, 193, 53);
		contentPane.add(deleteEventButton);
		eventList.setBounds(10, 11, 275, 271);
		contentPane.add(eventList);
		
		events.sort();
		updateEventList();
	}
	
	private void Code(){	
		SerializowanaTablica ser = new SerializowanaTablica(15,70);
		ser.wypisz();
		ser.save("data/inty.bin");
		ser.load("data/inty.bin");
		ser.wypisz();
		ser.sortuj();
		ser.wypisz();
		
		//events.getEvents();
		events.saveToBin();
		events.loadFromBin();
		events.print();
		events.saveObjects();
		events.loadObjects();
		events.print();
		
		EventAdder a = new EventAdder(events);
		a.setVisible(true);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Dodaj zdarzenie");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			Code();
		}
	}

	
	public void updateEventList(){
		if (eventList != null) eventList.setListData(events.ToStringArray());
	}
	private class SwingAction_2 extends AbstractAction {
		public SwingAction_2() {
			putValue(NAME, "Usuñ zdarzenie");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			events.removeAt(selectedItemIndex);
			updateEventList();
		}
	}
	private class SaveAction extends AbstractAction {
		public SaveAction() {
			putValue(NAME, "Save");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			SaveLoadWindow slw = new SaveLoadWindow(events, true);
			slw.setVisible(true);
		}
	}
	private class LoadAction extends AbstractAction {
		public LoadAction() {
			putValue(NAME, "Load");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			SaveLoadWindow slw = new SaveLoadWindow(events, false);
			slw.setVisible(true);
		}
	}
}
