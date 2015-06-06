import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.GridLayout;

import javax.swing.JTextField;

import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JEditorPane;

import sun.security.krb5.internal.crypto.DesCbcCrcEType;

import java.awt.Dimension;

/**
 * Okno tworzenia lub edytowanie zdarzeñ.
 */
public class EventAdder extends JFrame implements DateReceiver {
	private Event toAdd = new Event();
	private JPanel contentPane;
	private JTextField nameField;
	
	private EventContainer events;
	private final SwingAction action = new SwingAction();
	private JTextField hourField;
	private JTextField placeField;
	private JTextField descriptionField;
	private JTextField reminderField;
	
	JButton addButton;

	/**
	 * Tworzy okno EventAddera
	 * @param ev Kontener zdarzeñ stanowi¹cy kontekst danych.
	 * @param addingOnly Czy okno bêdzie dodawa³o nowe zdarzenie (true), czy edytowa³o ju¿ istniej¹ce (false)
	 */
	public EventAdder(EventContainer ev, boolean addingOnly) {
		events = ev;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 300, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNazwaZdarzenia = new JLabel("Nazwa zdarzenia");
		lblNazwaZdarzenia.setBounds(10, 106, 264, 14);
		contentPane.add(lblNazwaZdarzenia);
		
		nameField = new JTextField();
		
		
		nameField.setBounds(10, 126, 264, 20);
		nameField.addKeyListener(new KeyListener() {//example KeyListener
		      public void keyTyped(KeyEvent e) {
		        System.out.print(e.getKeyChar());
		      }

		      public void keyPressed(KeyEvent e) {

		      }

		      public void keyReleased(KeyEvent e) {

		      }
		});
		
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblMiejsceZdarzenia = new JLabel("Miejsce zdarzenia");
		lblMiejsceZdarzenia.setBounds(10, 157, 264, 14);
		contentPane.add(lblMiejsceZdarzenia);
		
		if(addingOnly){
			addButton = new JButton("Dodaj");
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("BUMP");
					boolean noProblems = true;
					toAdd.setName(nameField.getText());
					toAdd.setDescription(descriptionField.getText());
					try {
						toAdd.setDate(action.getLabel());
						toAdd.setDateHour(action.getLabel() + " " + hourField.getText());
						hourField.setBackground(Color.WHITE);
					} catch (DateFormatException d) {
						hourField.setBackground(Color.RED);
						noProblems = false;
					}
					try {
						toAdd.setReminder(reminderField.getText());
						reminderField.setBackground(Color.WHITE);
					} catch (NumberFormatException n){
						reminderField.setBackground(Color.RED);
						noProblems = false;
					}
					if(noProblems){
						
						events.add(toAdd);
						events.sort();
						events.print();
						dispose();
					}
					
				}
			});
		} else
			addButton = new JButton("Zmieñ");
		
		placeField = new JTextField();
		placeField.setColumns(10);
		placeField.setBounds(10, 179, 264, 20);
		contentPane.add(placeField);
		
		JLabel lblOpisZdarzenia = new JLabel("Opis zdarzenia");
		lblOpisZdarzenia.setBounds(10, 210, 264, 14);
		contentPane.add(lblOpisZdarzenia);
		
		JLabel lblCzasprzypomnienia = new JLabel("Czas przypomnienia [mm]");
		lblCzasprzypomnienia.setBounds(10, 269, 264, 14);
		contentPane.add(lblCzasprzypomnienia);
		
		descriptionField = new JTextField();
		descriptionField.setColumns(10);
		descriptionField.setBounds(10, 235, 264, 20);
		contentPane.add(descriptionField);
		addButton.setBounds(10, 330, 264, 23);
		contentPane.add(addButton);
		
		JButton cancelButton = new JButton("Anuluj");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setBounds(10, 357, 264, 23);
		contentPane.add(cancelButton);
		
		JButton calendarOpener = new JButton("New button");
		calendarOpener.setAction(action);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		GregorianCalendar cal = new GregorianCalendar();
		((SwingAction)action).setLabel(dateFormat.format(cal.getTime()));
		System.out.println(((SwingAction)action).getLabel());
		
		calendarOpener.setBounds(10, 11, 264, 52);
		contentPane.add(calendarOpener);
		DateFormat hourFormat = new SimpleDateFormat("HH:mm");
		hourField = new JTextField();
		hourField.setHorizontalAlignment(SwingConstants.CENTER);
		
		hourField.setText(hourFormat.format(cal.getTime()));
		hourField.setBounds(10, 75, 264, 20);
		contentPane.add(hourField);
		hourField.setColumns(10);
		
		reminderField = new JTextField();
		reminderField.setText("15");
		reminderField.setBounds(10, 292, 264, 20);
		contentPane.add(reminderField);
		reminderField.setColumns(10);
	}
	
	/**
	 * Ustawia edytowane zdarzenie, je¿eli EventAdder jest u¿ywany w forme edytora.
	 * @param targetIndex Indeks edytowanego zdarzenia w EventContainerze podanym poprzez konstruktor.
	 */
	public void setEvent(int targetIndex){
		final EventContainer events = this.events;
		final Event target = events.get(targetIndex);
		nameField.setText(target.getName());
		action.setLabel(target.getDay());
		placeField.setText(target.getPlace());
		descriptionField.setText(target.getDescription());
		reminderField.setText(Integer.toString(target.getReminder()));
		hourField.setText(target.getHour());
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.out.println("BUMP");
				boolean noProblems = true;
				target.setName(nameField.getText());
				target.setDescription(descriptionField.getText());
				try {
					target.setDate(action.getLabel());
					target.setDateHour(action.getLabel() + " " + hourField.getText());
					hourField.setBackground(Color.WHITE);
				} catch (DateFormatException d) {
					hourField.setBackground(Color.RED);
					noProblems = false;
				}
				try {
					target.setReminder(reminderField.getText());
					reminderField.setBackground(Color.WHITE);
				} catch (NumberFormatException n){
					reminderField.setBackground(Color.RED);
					noProblems = false;
				}
				if(noProblems){
					events.sort();
					events.print();
					dispose();
				}
				
			}
		});
	}
	
	/**
	 * Otwiera okno kalendarza.
	 */
	void openCalendar(){
		VisualCallendar vc = new VisualCallendar(this);
		vc.setVisible(true);
	}
	
	/**
	 * Przyjmuje datê z kalendarza.
	 */
	public void sendDate(int day, int month, int year){
		action.setLabel(day + "/" + month + "/" + year);
	}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		public void setLabel(String str){
			putValue(NAME, str);
		}
		
		public String getLabel(){
			return (String)getValue(NAME);
		}
		
		public void actionPerformed(ActionEvent e) {
			openCalendar();
		}
	}
}
