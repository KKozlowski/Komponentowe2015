import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JTextPane;



public class VisualCallendar extends JFrame {
	
	String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	
	public ArrayList<ArrayList<GridButton>> days = new ArrayList<ArrayList<GridButton>>();
	
	
	public  VisualCallendar() {
		GregorianCalendar gregor = new GregorianCalendar();
		System.out.println(gregor.get(Calendar.DAY_OF_MONTH));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 474, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 109, 438, 255);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(6, 7, 0, 0));
		
		
		for(int i=0; i<6; i++){
			days.add(new ArrayList<GridButton>());
			for(int k=0; k<7; k++){
				GridButton butt = new GridButton(i,k, this);
				panel.add(butt);
				days.get(i).add(butt);
			}
		}
		
		GregorianCalendar g = new GregorianCalendar();
		//updateCalendar(g.get(Calendar.MONTH), Calendar.YEAR);
		updateCalendar(7,2015);
		
	}
	
	private void updateCalendar(int month, int year) { //0 is January
		GregorianCalendar gregor = new GregorianCalendar(year, month, 1);
		int dayOfWeek = gregor.get(GregorianCalendar.DAY_OF_WEEK);
		System.out.println(dayOfWeek);
		days.get(0).get(dayOfWeek-2).setNumber(1);
	}

	private class GridButton extends JButton{
		int row;
		int column;
		int dayNumber;
		VisualCallendar calendar;
		private Action action = new BeClicked();
		
		public GridButton(int row, int col, VisualCallendar cal){
			super();
			this.calendar = cal;
			this.row = row;
			this.column = col;
			this.setAction(action);
			setLabel(row+"."+column);
		}
		
		public void setLabel(String str){
			((BeClicked)action).setLabel(str);
		}
		
		public void setNumber(int dayN){
			dayNumber = dayN;
			setLabel(Integer.toString(dayN));
		}
		
		private class BeClicked extends AbstractAction {
			public BeClicked() {
				putValue(SHORT_DESCRIPTION, "Some short description");
			}
			
			public void setLabel(String str){
				putValue(NAME, str);
			}
			
			public void actionPerformed(ActionEvent e) {
			}
		}
	}
}
