import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Klasa przechowuj¹ca dane poszczególnych zdarzeñ.
 */
public class Event implements Comparable<Event>, Serializable, Individual, Chronologic, Cloneable{
	private String name;
	private Date data = new Date();
	private String description;
	private String place;
	
	/**
	 * Czas w minutach, przed którym nale¿y przypomnieæ o zdarzeniu.
	 */
	private int reminderTime;
	private Comparator<Event> comparator;
	
	/**
	 * Czy o zdarzeniu ju¿ przypomniano.
	 */
	public boolean remindedAbout = false;
	
	/**
	 * Statyczny obiekt s³u¿¹cy do parsowania stringów w formacie "dd/MM/yyyy" na daty.
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	/**
	 * Statyczny obiekt s³u¿¹cy do parsowania stringów w formacie "dd/MM/yyyy HH:mm" na daty.
	 */
	private static SimpleDateFormat sdfHour = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	//private GregorianCalendar date = new GregorianCalendar();
	
	/**
	 * Podstawowy konstruktor pozostawiaj¹cy wszystkie pola pustymi.
	 */
	public Event(){
		comparator = new DateComparator();
		description = "";
		place = "";
	}
	
	/**
	 * Konstruktor przyjmuj¹cy nazwê i datê.
	 * @param name Nazwa zdarzenia.
	 * @param dataa Data w formie ci¹gu znaków "dd/MM/yyyy".
	 * @throws DateFormatException Mo¿liwoœæ b³êdnego odczytu daty.
	 */
	public Event(String name, String dataa) throws DateFormatException {
		this();
		setName(name);
		setDate(dataa);
	}
	
	/**
	 * Konstruktor przyjmuj¹cy nazwê i datê.
	 * @param name Nazwa zdarzenia.
	 * @param dataa Data jako obiekt klasy Date
	 */
	public Event(String name, Date dataa) {
		this();
		setName(name);
		setDate(dataa);
	}
	
	/**
	 * Konstruktor przyjmuj¹cy nazwê i datê.
	 * @param name Nazwa zdarzenia.
	 * @param dataa Data jako suma milisekund od 01.01.1970.
	 */
	public Event (String name, long dataa){
		this();
		setName(name);
		setDate(dataa);
	}
	
	/**
	 * Konstruktor przyjmuj¹cy nazwê, datê i opis.
	 * @param name Nazwa zdarzenia.
	 * @param dataa Data w formie ci¹gu znaków "dd/MM/yyyy".
	 * @param description Opis zdarzenia.
	 * @throws DateFormatException Mo¿liwoœæ b³êdnego odczytu daty.
	 */
	public Event (String name, String dataa, String description) throws DateFormatException{
		this();
		setName(name);
		setDateHour(dataa);
		setDescription(description);
	}
	
	/**
	 * Konstruktor przyjmuj¹cy nazwê, datê i opis.
	 * @param name Nazwa zdarzenia.
	 * @param dataa Data jako obiekt klasy Date
	 * @param description Opis zdarzenia.
	 */
	public Event (String name, Date dataa, String description){
		this();
		setName(name);
		setDate(dataa);
		setDescription(description);
	}
	
	/**
	 * Podstawowa metoda s³u¿¹ca do serializacji pól obiektu do strumienia.
	 * @param o strumieñ do którego s¹ zapisywane dane.
	 * @throws IOException potencjalne b³êdy zapisu obiektów.
	 */
	 private void writeObject(ObjectOutputStream o)
	   throws IOException {  
		 o.writeObject(name);
		 o.writeObject(data);
		 o.writeObject(description);
		 o.writeObject(place);
		 o.writeObject(reminderTime);
	 }
	  
	 /**
	  * Metoda s³u¿¹ca do wczytywania pól obiektu ze strumienia.
	  * @param o Strumieñ odczytu.
	  * @throws IOException B³¹d odczytu danych
	  * @throws ClassNotFoundException Brak klasy odpowiadaj¹cej informacjom ze strumienia.
	  */
	 private void readObject(ObjectInputStream o)
	    throws IOException, ClassNotFoundException {  
		 name = (String) o.readObject();
		 data = (Date) o.readObject();
		 description = (String) o.readObject();
		 place = (String) o.readObject();
		 reminderTime = (int)o.readObject();
	 }
	
	 /**
	  * Zwraca nazwê zdarzenia.
	  */
	public String getName() {
		return name;
	}

	/**
	 * Ustawia nazwê zdarzenia.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Ustawia opis zdarzenia.
	 * @param desc Opis
	 */
	public void setDescription(String desc){
		this.description = desc;
	}
	
	/**
	 * Zwraca opis zdarzenia.
	 * @return Opis zdarzenia.
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * @param desc Miejsce zdarzenia do ustawienia.
	 */
	public void setPlace(String desc){
		this.place = desc;
	}
	
	/**
	 * @return Miejsce zdarzenia.
	 */
	public String getPlace(){
		return place;
	}
	
	/**
	 * Ustawia czas do zdarzenia, przed którym program wywo³a przypomnienie.
	 * @param mm Czas w minutach, podany jako ci¹g znaków.
	 */
	public void setReminder(String mm){
		this.reminderTime = Integer.parseInt(mm);
		remindedAbout = false;
	}
	
	/**
	 * Ustawia czas do zdarzenia, przed którym program wywo³a przypomnienie.
	 * @param mm Czas w minutach
	 */
	public void setReminder(int mm){
		this.reminderTime = mm;
		remindedAbout = false;
	}
	
	/**
	 * @return Czas przypomnienia przed zdarzeniem w minutach.
	 */
	public int getReminder(){
		return reminderTime;
	}
	
	@Override
	public void setDate (String dat) throws DateFormatException{
	    Date d; 
	    try {
	    	data = sdf.parse(dat);
	    } catch ( ParseException pe){
	    	throw new DateFormatException();
	    }
	}
	
	@Override
	public void setDate (Date dat){
	    this.data = dat;
	}
	
	@Override
	public void setDateHour(String dat) throws DateFormatException{
	    Date d; 
	    try {
	    	data = sdfHour.parse(dat);
	    } catch ( ParseException pe){
	    	throw new DateFormatException();
	    }
	}
	
	@Override
	public void setDate(long dataa){
		data.setTime(dataa);
	}
	
	@Override
	public Date getDate(){
		return data;
	}
	
	@Override
	public String getDateHour(){
		return sdfHour.format(data);
	}
	
	@Override
	public String getHour(){
		SimpleDateFormat hour = new SimpleDateFormat("HH:mm");
		return hour.format(data);
	}
	
	@Override
	public String getDay(){
		return sdf.format(data);
	}
	
	@Override
	public long getMiliseconds(){
		return data.getTime();
	}

	public String toString(){
		return getName() + " [" + getDateHour() + "]";
	}
	
	public int compareTo(Event ev){
	    return comparator.compare(this, ev);
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
