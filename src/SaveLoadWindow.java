import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

/**
 * Okno z opcjami serializacji/deserializacji. Charakter jego dzia³aia jest okreœlany przy tworzeniu.
 */
public class SaveLoadWindow extends JFrame {

	private JPanel contentPane;
	
	/**
	 * Pole przyjmuj¹ce nazwê lokacji zapisu.
	 */
	private JTextField fileName;
	
	/**
	 * Lista mo¿liwych trybów serializacji/deserializacji.
	 */
	private JComboBox comboBox;

	/**
	 * Okreœla, ¿e okno s³u¿y do zapisu, czy do wczytywania.
	 */
	private boolean isToSave;
	
	/**
	 * Przekazany do obiektu kontener ze zdarzeniami.
	 */
	private EventContainer events;
	private Action action;
	
	/**
	 * Tworzy okno zapisu.
	 * @param e Kontener ze zdarzeniami
	 * @param toSave Okreœla, czy to zapis (true), czy odczyt (false)
	 */
	public SaveLoadWindow(EventContainer e, boolean toSave) {
		setResizable(false);
		events = e;
		isToSave = toSave;
		action = new SwingAction(toSave);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 345, 107);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		fileName = new JTextField();
		fileName.setText("Test.serial");
		fileName.setBounds(10, 11, 205, 20);
		contentPane.add(fileName);
		fileName.setColumns(10);
		
		comboBox = new JComboBox();
		if(toSave){
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"XML (Java)", "XML (Xstream)", "SQL Server", "Sqlite", "iCalendar"}));
			setTitle("Zapisz jako...");
		}
		else{
			setTitle("Otwórz...");
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"XML (Java)", "XML (Xstream)", "SQL Server", "Sqlite"}));
		}
		comboBox.setBounds(225, 11, 96, 20);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setAction(action);
		btnNewButton.setBounds(10, 42, 311, 23);
		contentPane.add(btnNewButton);
	}
	
	public boolean isSaveWindow(){
		return isToSave;
	}
	
	/**
	 * Wywo³uje odpowiedni¹ metodê serializacji/deserializacji z EventContainera.
	 */
	private class SwingAction extends AbstractAction {
		public SwingAction(boolean toSave) {
			if(toSave) 
				putValue(NAME, "Save");
			else
				putValue(NAME, "Load");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		public void actionPerformed(ActionEvent e) {
			events.window.defilter();
			String name = fileName.getText();
			try{
				if(isToSave)
					switch (comboBox.getSelectedIndex()){
					case 0:
						events.SerializeXmlJava(name);
						break;
					case 1:
						events.SerializeXstream(name);
						break;
					case 2:
						events.SerializeSqlServer(name);
						break;
					case 3:
						events.SerializeSqlite(name);
						break;
					case 4:
						events.SaveToICal(name);
						break;
					}
				else
					switch (comboBox.getSelectedIndex()){
					case 0:
						events.DeserializeXmlJava(name);
						break;
					case 1:
						events.DeserializeXstream(name);
						break;
					case 2:
						events.DeserializeSqlServer(name);
						break;
					case 3:
						events.DeserializeSqlite(name);
					}
			}
			catch(Exception ex){
				return;
			}
			System.out.println(comboBox.getSelectedIndex());
			dispose();
		}
	}
}
