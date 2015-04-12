import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
import java.util.Date;

public class Event implements Comparable<Event>, Comparator<Event>, Serializable{
	private String name;
	private Date data = new Date();
	private Comparator<Event> comparator;
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	//private GregorianCalendar date = new GregorianCalendar();
	

	public Event(){
		comparator = new DateComparator();
	}
	
	public Event(String name, String dataa) {
		this();
		setName(name);
		setDate(dataa);
	}
	
	public Event (String name, long dataa){
		this();
		setName(name);
		setDate(dataa);
	}
	
	  private void writeObject(ObjectOutputStream o)
	    throws IOException {  
	    
		  o.writeObject(name);
		  o.writeObject(data);
	    //o.writeObject(propertyOne);  
	   // o.writeObject(propertyTwo);
	  }
	  
	  private void readObject(ObjectInputStream o)
	    throws IOException, ClassNotFoundException {  
	    
		  name = (String) o.readObject();
		  data = (Date) o.readObject();
	    //propertyOne = (String) o.readObject();  
	    //propertyTwo = (String) o.readObject();
	  }
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setDate (String dat){
	    java.util.Date d; 
	    try {
	    	data = sdf.parse(dat);
	    	/*date.setTime(d);
		    System.out.println("Input Date = " + sdf.format(d));
		    int dayBefore = date.get(Calendar.DAY_OF_YEAR);
		    date.roll(Calendar.DAY_OF_YEAR, -1);
		    int dayAfter = date.get(Calendar.DAY_OF_YEAR);
		    if(dayAfter > dayBefore) {
		        date.roll(Calendar.YEAR, -1);
		    }
		    date.get(Calendar.DATE);
		    java.util.Date yesterday = date.getTime();
		    System.out.println("Yesterdays Date = " + sdf.format(yesterday));*/
	    } catch ( ParseException pe){
	    	System.out.println("Bledny format daty.");
	    }
	    
	}
	
	public void setDate(long dataa){
		data.setTime(dataa);
	}
	
	public String getDate(){
		return sdf.format(data);
	}
	
	public long getMiliseconds(){
		return data.getTime();
	}

	public String toString(){
		return getName() + " [" + getDate() + "]";
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
		
	}
	
	class DateComparator implements Comparator<Event>{

		@Override
		public int compare(Event arg0, Event arg1) {
			long dif = arg0.data.getTime() - arg1.data.getTime();
			if (dif > 0) return 1;
			else if (dif < 0) return -1;
			
			return 0;
		}
		
	}
}
