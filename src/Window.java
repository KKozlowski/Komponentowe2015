import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.thoughtworks.xstream.XStream;

import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * G³ówne okno programu.
 */
public class Window extends JFrame   {
	private EventContainer events;
	private JPanel contentPane;
	private JList eventList;
	private final Action action = new AddAction();
	private JButton deleteEventButton;
	private final Action removeAction = new RemoveAction();
	
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
	private final Action filterAction = new FilterAction();
	
	JButton addEventButton;
	private JButton deletePastButton;
	private final Action deletePastAction = new DeletePastAction();
	private JMenuItem aboutButton;

	/**
	 * Tworzy g³ówne okno
	 */
	public Window() {
		setTitle("Organizer");
		setResizable(false);
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
		
		aboutButton = new JMenuItem("About");
		aboutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AboutWindow aw = new AboutWindow();
				aw.setVisible(true);
			}
		});
		mnFileMenu.add(aboutButton);
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
		deleteEventButton.setAction(removeAction);
		deleteEventButton.setBounds(295, 75, 193, 53);
		contentPane.add(deleteEventButton);
		
		modifyEventButton = new JButton("Edytuj zdarzenie");
		modifyEventButton.setAction( editAction);
		modifyEventButton.setBounds(295, 139, 193, 53);
		contentPane.add(modifyEventButton);
		
		filterButton = new JButton("");
		filterButton.setAction(filterAction);
		filterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		filterButton.setBounds(295, 199, 193, 53);
		contentPane.add(filterButton);
		
		deletePastButton = new JButton("");
		deletePastButton.setAction(deletePastAction);
		deletePastButton.setBounds(295, 263, 193, 53);
		contentPane.add(deletePastButton);
		eventList.setBounds(10, 11, 275, 358);
		contentPane.add(eventList);
		
		events.sort();
		updateEventList();
		
		/*this.addWindowListener(new WindowAdapter()  {
			
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
		});*/
		
		timer = new Timer(events, 10000);
		timer.start();
	}
	
	/*private void Code(){	
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
		
		
	}*/
	
	/**
	 * Wydaje dŸwiêk "boop".
	 */
	private void boop(){
		Clip clip = null;
		try{
	        clip = AudioSystem.getClip();
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		
		try{
	        // getAudioInputStream() also accepts a File or InputStream
	        AudioInputStream ais = AudioSystem.
	            getAudioInputStream( new File("boop.wav") );
	        clip.open(ais);
	        clip.loop(0);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Wywo³uje przypomnienie o zdarzeniu w formie okna komunikatu oraz dŸwiêku.
	 * @param ev Przypominane zdarzenie
	 * @param time Czas do zdarzenia.
	 */
	public void remindEventMessage(final Event ev, final int time){
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	boop();
            	JOptionPane.showMessageDialog(null, "Zbli¿a siê zdarzenie o nazwie " +ev.getName()+ ".\n Pozosta³y czas: " + time);
            	
            }
        });
		
	}
	
	/**
	 * Aktualizuje wyœwietlan¹ listê zdarzeñ.
	 */
	public void updateEventList(){
		if (eventList != null) eventList.setListData(events.ToStringArray());
	}
	
	/**
	 * Otwiera okno dodawania zdarzenia.
	 */
	private class AddAction extends AbstractAction {
		public AddAction() {
			putValue(NAME, "Dodaj zdarzenie");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			//Code();
			EventAdder a = new EventAdder(events, true);
			a.setVisible(true);
		}
	}

	
	
	/**
	 * Usuwanie zaznaczonego zdarzenia.
	 */
	private class RemoveAction extends AbstractAction {
		public RemoveAction() {
			putValue(NAME, "Usuñ zdarzenie");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			events.removeAt(selectedItemIndex);
			updateEventList();
		}
	}
	
	/**
	 * Otwiera okno serializacji zdarzeñ.
	 */
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
	
	/**
	 * Otwiera okno deserializacji zdarzeñ.
	 */
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
	
	/**
	 * Otwiera okno edycji zdarzenia.
	 */
	private class EditAction extends AbstractAction {
		public EditAction() {
			putValue(NAME, "Edytuj zdarzenie");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			if(selectedItemIndex>=0){
				EventAdder ea = new EventAdder(events, false);
				ea.setEvent(selectedItemIndex);
				ea.setVisible(true);
			}
		}
	}
	
	/**
	 * Otwiera okno ustawieñ filtrowania zdarzeñ. Po wykonaniu operacji filtrowania s³u¿y do jej cofniêcia.
	 */
	private class FilterAction extends AbstractAction implements DateReceiver {
		public FilterAction() {
			putValue(NAME, "Filtruj wed³ug daty");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			if (!events.getFiltered()){
				
				VisualCallendar vis = new VisualCallendar(this);
				vis.setVisible(true);
			} else {
				defilter();
			}
		}
		
		public void defilter(){
			events.defilter();
			updateEventList();
			putValue(NAME, "Filtruj wed³ug daty");
			addEventButton.setEnabled(true);
		}
		
		@Override
		public void sendDate(int day, int month, int year) {
			GregorianCalendar cal1 = new GregorianCalendar(year,month-1,day,0,1);
			GregorianCalendar cal2 = new GregorianCalendar(year,month-1,day,23,59);
			events.dateFilter(cal1.getTime(), cal2.getTime());
			updateEventList();
			addEventButton.setEnabled(false);
			putValue(NAME, "Cofnij filtrowanie");
		}
	}
	
	/**
	 * Pozwala cofn¹æ filtrowanie zdarzeñ z zewn¹trz, je¿eli filtrowanie nast¹pi³o.
	 */
	void defilter(){
		if(events.getFiltered())
			((FilterAction)filterAction).defilter();
	}
	
	/**
	 * Usuwa minione zdarzenia.
	 */
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
