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


public class EventAdder extends JFrame {
	private Event toAdd = new Event();
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField dateField;
	
	private EventContainer events;

	/**
	 * Create the frame.
	 */
	
	public void open() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EventAdder frame = new EventAdder(events);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public EventAdder(EventContainer ev) {
		
		events = ev;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 300, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNazwaZdarzenia = new JLabel("Nazwa zdarzenia");
		lblNazwaZdarzenia.setBounds(10, 13, 264, 14);
		contentPane.add(lblNazwaZdarzenia);
		
		nameField = new JTextField();
		
		
		nameField.setBounds(10, 33, 264, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblData = new JLabel("Data");
		lblData.setBounds(10, 64, 264, 14);
		contentPane.add(lblData);
		
		dateField = new JTextField();
		dateField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					toAdd.setDate(dateField.getText());
					dateField.setBackground(Color.WHITE);
				} catch (DateFormatException d) {
					dateField.setBackground(Color.RED);
				}
			}
		});
		dateField.setBounds(10, 89, 264, 20);
		contentPane.add(dateField);
		dateField.setColumns(10);
		
		JButton addButton = new JButton("Dodaj");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("BUMP");
				try {
					toAdd.setDate(dateField.getText());
					dateField.setBackground(Color.WHITE);
					toAdd.setName(nameField.getText());
					events.add(toAdd);
					events.sort();
					events.print();
					dispose();
				} catch (DateFormatException d) {
					dateField.setBackground(Color.RED);
				}
			}
		});
		addButton.setBounds(10, 120, 264, 23);
		contentPane.add(addButton);
		
		JButton cancelButton = new JButton("Anuluj");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setBounds(10, 147, 264, 23);
		contentPane.add(cancelButton);
	}
}
