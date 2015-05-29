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


public class Window extends JFrame {
	private EventContainer events;
	private JPanel contentPane;
	private JList eventList;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Window() {
		//Code();
		XStream xst = new XStream();
		events = new EventContainer(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 514, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton addEventButton = new JButton("New button");
		addEventButton.setBounds(295, 11, 193, 98);
		addEventButton.setAction(action);
		contentPane.add(addEventButton);
		
		eventList = new JList();
		eventList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting())
					System.out.println(eventList.getSelectedIndex());
			}
		});
		eventList.setBounds(10, 11, 275, 239);
		contentPane.add(eventList);
		
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
		
		//SecondaryWindow w = new SecondaryWindow();
		//w.open();
		
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
			VisualCallendar vc = new VisualCallendar();
			vc.setVisible(true);
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "SwingAction_1");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	public void updateEventList(){
		if (eventList != null) eventList.setListData(events.ToStringArray());
	}
}
