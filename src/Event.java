import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Event implements Comparable<Event>, Comparator<Event>, Serializable, Individual, Chronologic, Cloneable{
	private String name;
	private Date data = new Date();
	private String description;
	private String place;
	private int reminderTime;
	private Comparator<Event> comparator;
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat sdfHour = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	//private GregorianCalendar date = new GregorianCalendar();
	

	public Event(){
		comparator = new DateComparator();
		description = "";
		place = "";
	}
	
	public Event(String name, String dataa) throws DateFormatException {
		this();
		setName(name);
		setDate(dataa);
	}
	
	public Event (String name, long dataa){
		this();
		setName(name);
		setDate(dataa);
	}
	
	public Event (String name, String dataa, String description) throws DateFormatException{
		this();
		setName(name);
		setDateHour(dataa);
		setDescription(description);
	}

	 private void writeObject(ObjectOutputStream o)
	   throws IOException {  
		 o.writeObject(name);
		 o.writeObject(data);
		 o.writeObject(description);
		 o.writeObject(place);
		 o.writeObject(reminderTime);
	 }
	  
	 private void readObject(ObjectInputStream o)
	    throws IOException, ClassNotFoundException {  
		 name = (String) o.readObject();
		 data = (Date) o.readObject();
		 description = (String) o.readObject();
		 place = (String) o.readObject();
		 reminderTime = (int)o.readObject();
	 }
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String desc){
		this.description = desc;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setPlace(String desc){
		this.place = desc;
	}
	
	public String getPlace(){
		return place;
	}
	
	public void setReminder(String mm){
		this.reminderTime = Integer.parseInt(mm);
	}
	
	public void setReminder(int mm){
		this.reminderTime = mm;
	}
	
	public int getReminder(){
		return reminderTime;
	}
	
	public void setDate (String dat) throws DateFormatException{
	    Date d; 
	    try {
	    	data = sdf.parse(dat);
	    } catch ( ParseException pe){
	    	throw new DateFormatException();
	    }
	    
	}
	
	public void setDateHour(String dat) throws DateFormatException{
	    Date d; 
	    try {
	    	data = sdfHour.parse(dat);
	    } catch ( ParseException pe){
	    	throw new DateFormatException();
	    }
	    
	}
	
	public void setDate(long dataa){
		data.setTime(dataa);
	}
	
	public Date getDate(){
		return data;
	}
	
	public String getDateHour(){
		return sdfHour.format(data);
	}
	
	public String getHour(){
		SimpleDateFormat hour = new SimpleDateFormat("HH:mm");
		return hour.format(data);
	}
	
	public String getDay(){
		return sdf.format(data);
	}
	
	public long getMiliseconds(){
		return data.getTime();
	}

	public String toString(){
		return getName() + " [" + getDateHour() + "]";
	}
	
	public int compareTo(Event ev){
	    return comparator.compare(this, ev);
	}

	@Override
	public int compare(Event arg0, Event arg1) {
		return comparator.compare(arg0, arg1);
	}
	
	class NameComparator implements Comparator<Event>{

		@Override
		public int compare(Event arg0, Event arg1) {
			return arg0.name.compareTo(arg1.name);
		}
		
		@Override
		public String toString(){
			return "NameComparator";
		}
	}
	
	class DateComparator implements Comparator<Event>{

		@Override
		public int compare(Event arg0, Event arg1) {
			long dif = arg0.data.getTime() - arg1.data.getTime();
			if (dif > 0) return 1;
			else if (dif < 0) return -1;
			
			return 0;
		}
		
		@Override
		public String toString(){
			return "DateComparator";
		}
	}
	
	@Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
