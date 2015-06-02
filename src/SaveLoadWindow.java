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


public class SaveLoadWindow extends JFrame {

	private JPanel contentPane;
	private JTextField fileName;
	private JComboBox comboBox;

	private boolean isToSave;
	private EventContainer events;
	private Action action;
	
	public SaveLoadWindow(EventContainer e, boolean toSave) {
		events = e;
		isToSave = toSave;
		action = new SwingAction(toSave);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 361, 134);
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
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"XML (Java)", "XML (Xstream)", "SQL Server", "Sqlite"}));
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
	
	private class SwingAction extends AbstractAction {
		public SwingAction(boolean toSave) {
			if(toSave) 
				putValue(NAME, "Save");
			else
				putValue(NAME, "Load");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		public void actionPerformed(ActionEvent e) {
			String name = fileName.getText();
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
			System.out.println(comboBox.getSelectedIndex());
			dispose();
		}
	}
}
