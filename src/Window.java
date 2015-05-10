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


public class Window extends JFrame {

	private JPanel contentPane;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		//Code();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 299, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("New button");
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setAction(action);
		contentPane.add(btnNewButton_1, BorderLayout.CENTER);
	}
	
	private void Code(){	
		SerializowanaTablica ser = new SerializowanaTablica(15,70);
		ser.wypisz();
		ser.save("data/inty.bin");
		ser.load("data/inty.bin");
		ser.wypisz();
		ser.sortuj();
		ser.wypisz();
		
		EventContainer kont = new EventContainer();
		kont.getEvents();
		kont.saveToBin();
		kont.loadFromBin();
		kont.print();
		kont.saveObjects();
		kont.loadObjects();
		kont.print();
		
		SecondaryWindow w = new SecondaryWindow();
		w.open();
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Zacznij");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			Code();
		}
	}
}
