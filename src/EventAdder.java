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
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingConstants;


public class EventAdder extends JFrame {
	private Event toAdd = new Event();
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField dateField;
	
	private EventContainer events;
	private final SwingAction action = new SwingAction();
	private JTextField hourField;

	/**
	 * Create the frame.
	 */
	
	public EventAdder(EventContainer ev) {
		
		events = ev;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 300, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNazwaZdarzenia = new JLabel("Nazwa zdarzenia");
		lblNazwaZdarzenia.setBounds(10, 106, 264, 14);
		contentPane.add(lblNazwaZdarzenia);
		
		nameField = new JTextField();
		
		
		nameField.setBounds(10, 126, 264, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		

		
		JButton addButton = new JButton("Dodaj");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("BUMP");
				try {
					toAdd.setDate(action.getLabel());
					toAdd.setName(nameField.getText());
					toAdd.setDateHour(action.getLabel() + " " + hourField.getText());
					events.add(toAdd);
					events.sort();
					events.print();
					dispose();
				} catch (DateFormatException d) {

				}
			}
		});
		addButton.setBounds(10, 213, 264, 23);
		contentPane.add(addButton);
		
		JButton cancelButton = new JButton("Anuluj");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setBounds(10, 240, 264, 23);
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
	}
	
	void openCalendar(){
		VisualCallendar vc = new VisualCallendar(this);
		vc.setVisible(true);
	}
	
	void setEventDate(int day, int month, int year){
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
