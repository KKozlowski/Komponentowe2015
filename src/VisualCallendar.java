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
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;



public class VisualCallendar extends JFrame {
	
	String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private DateReceiver ea;
	
	private JPanel contentPane;
	private JLabel monthLabel;

	private int currentMonth;
	private int currentYear;

	/**
	 * Create the frame.
	 */
	
	public ArrayList<ArrayList<GridButton>> days = new ArrayList<ArrayList<GridButton>>();
	private final Action previousMonth = new SwingAction();
	private final Action nextMonth = new SwingAction_1();
	
	
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
		
		monthLabel = new JLabel("Month");
		monthLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		monthLabel.setBounds(142, 28, 177, 47);
		contentPane.add(monthLabel);
		
		JButton prevButton = new JButton("<");
		prevButton.setAction(previousMonth);
		prevButton.setBounds(10, 46, 89, 23);
		contentPane.add(prevButton);
		
		JButton nextButton = new JButton(">");
		nextButton.setAction(nextMonth);
		nextButton.setBounds(359, 46, 89, 23);
		contentPane.add(nextButton);
		
		
		for(int i=0; i<6; i++){
			days.add(new ArrayList<GridButton>());
			for(int k=0; k<7; k++){
				GridButton butt = new GridButton(i,k, this);
				panel.add(butt);
				days.get(i).add(butt);
			}
		}
		
		GregorianCalendar g = new GregorianCalendar();
		updateCalendar(g.get(Calendar.MONTH), g.get(Calendar.YEAR));
		
	}
	
	public VisualCallendar(DateReceiver eve){
		this();
		ea = eve;
	}
	
	private void updateCalendar(int month, int year) { //0 is January
		reset();
		currentMonth = month;
		currentYear = year;
		GregorianCalendar gregor = new GregorianCalendar(year, month, 1);
		//gregor.getActualMaximum(Calendar.DAY_OF_MONTH)
		for(int i= 0; i< 31; i++){
			if (gregor.get(Calendar.MONTH) == currentMonth){
				int weekOfMonth = gregor.get(Calendar.WEEK_OF_MONTH);
				int dayOfWeek = gregor.get(GregorianCalendar.DAY_OF_WEEK);
				dayOfWeek-=2;
				if(dayOfWeek == -1) dayOfWeek = 6;

				days.get(weekOfMonth).get(dayOfWeek).setNumber(gregor.get(Calendar.DAY_OF_MONTH));
				gregor.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		
		monthLabel.setText(months[month] + " " + year);
	} 
	
	private void updateCalendar(){
		updateCalendar(currentMonth, currentYear);
	}
	
	private void reset(){
		for(int i=0; i<6; i++){
			for(int k=0; k<7; k++){
				days.get(i).get(k).clear();
			}
		}
	}
	
	private void chooseDay(int day){
		ea.sendDate(day, currentMonth+1, currentYear);
		dispose();
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
		
		public void clear(){
			setLabel("");
			dayNumber = -1;
		}
		
		private class BeClicked extends AbstractAction {
			public BeClicked() {
				putValue(SHORT_DESCRIPTION, "Some short description");
			}
			
			public void setLabel(String str){
				putValue(NAME, str);
			}
			
			public void actionPerformed(ActionEvent e) {
				if(dayNumber >0 ) chooseDay(dayNumber);
			}
		}
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "<");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			currentMonth-=1;
			if(currentMonth<0) {
				currentMonth=11;
				currentYear-=1;
			}
			updateCalendar();
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, ">");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			currentMonth+=1;
			if(currentMonth>11) {
				currentMonth=0;
				currentYear+=1;
			}
			updateCalendar();
		}
	}
}
