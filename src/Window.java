import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.thoughtworks.xstream.XStream;

import java.awt.event.ActionListener;
import java.util.Date;


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
	
	private Timer timer;
	private JButton modifyEventButton;
	private final EditAction editAction = new EditAction();
	private JButton filterButton;
	private final Action action_2 = new FilterAction();
	
	JButton addEventButton;
	private JButton deletePastButton;
	private final Action action_3 = new DeletePastAction();

	/**
	 * Create the frame.
	 */
	public Window() {
		setTitle("Organizer");
		
		
		//Code();
		XStream xst = new XStream();
		events = new EventContainer(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 514, 440);
		
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
		
		addEventButton = new JButton("New button");
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
		
		modifyEventButton = new JButton("Edytuj zdarzenie");
		modifyEventButton.setAction( editAction);
		modifyEventButton.setBounds(295, 139, 193, 53);
		contentPane.add(modifyEventButton);
		
		filterButton = new JButton("");
		filterButton.setAction(action_2);
		filterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		filterButton.setBounds(295, 199, 193, 53);
		contentPane.add(filterButton);
		
		deletePastButton = new JButton("");
		deletePastButton.setAction(action_3);
		deletePastButton.setBounds(295, 263, 193, 53);
		contentPane.add(deletePastButton);
		eventList.setBounds(10, 11, 275, 358);
		contentPane.add(eventList);
		
		events.sort();
		updateEventList();
		
		this.addWindowListener(new WindowAdapter()  {
			
			@Override
			public void windowClosing(WindowEvent e) {
				timer.setActive(false);
				while(timer.isAlive())
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		
		timer = new Timer(events, 2000);
		timer.start();
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
		
		EventAdder a = new EventAdder(events, true);
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
	private class EditAction extends AbstractAction {
		public EditAction() {
			putValue(NAME, "Edytuj zdarzenie");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			EventAdder ea = new EventAdder(events, false);
			ea.setEvent(selectedItemIndex);
			ea.setVisible(true);
		}
	}
	private class FilterAction extends AbstractAction {
		public FilterAction() {
			putValue(NAME, "Filtruj wed³ug daty");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			if (!events.getFiltered()){
				Event ev = new Event();
				try {
					ev.setDate("03/03/2014");
				} catch (DateFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				events.dateFilter(ev.getDate(), true);
				updateEventList();
				addEventButton.setEnabled(false);
				putValue(NAME, "Cofnij filtrowanie");
				
			} else {
				events.defilter();
				updateEventList();
				putValue(NAME, "Filtruj wed³ug daty");
				addEventButton.setEnabled(true);
			}
		}
	}
	private class DeletePastAction extends AbstractAction {
		public DeletePastAction() {
			putValue(NAME, "Usuñ minione zdarzenia");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			events.deleteBefore(new Date());
		}
	}
}
