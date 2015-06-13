import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.io.*;
import java.net.SocketException;

import net.fortuna.ical4j.data.*;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;

import com.thoughtworks.xstream.XStream;

/**
 * Klasa s³u¿¹ca do wykonywania podtawowych operacji na danych oraz serializowania ich.
 */
public class EventContainer extends CollectionAdapter<Event> implements ObjectContainer, Tickable, DateFilterable {
	/**
	 * Lista zdarzeñ
	 */
	ArrayList<Event> eventy = new ArrayList<Event>();
	
	/**
	 * Okno powi¹zane z logik¹. Opcjonalne, s³u¿y do odœwie¿ania listy eventów w oknie.
	 */
	Window window;
	
	/**
	 * Serializator xstream
	 */
	XStream xstream;
	
	/**
	 * Wskazuje, czy kontener jest w trakcie filtrowania.
	 */
	private boolean isFiltered;
	
	/**
	 * Kopia list zdarzeñ na czas filtrowania.
	 */
	ArrayList<Event> kopiaEventow;
	
	/**
	 * Postawowy konstruktor
	 */
	public EventContainer(){
		xstream = new XStream();
		try{
			Event ev = new Event("Spotkanie", "02/03/2014");
			Event ev2 = new Event("Spotkanie2", "05/03/2014");
			add(ev);
			add(ev2);
			try {
				add((Event)(ev2.clone()));
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			add(new Event("Spotkanie3", "05/02/2014"));
			eventy.sort(ev.new DateComparator());
		}
		catch (DateFormatException d) {
			System.out.println(d.getMessage());
		}
		
		
		printList(eventy);
	}
	
	/**
	 * Konstruktor ustawiaj¹cy pole Window klasy. Pozwala na kontakt warstwy logiki z warstw¹ interfejsu.
	 * @param mainWin Obiekt g³ównego okna
	 */
	public EventContainer(Window mainWin){
		this();
		window = mainWin;
		
		try {
			add(new Event("Hue", "11/11/2011"));
		} catch (DateFormatException e) {
			e.printStackTrace();
		}

		if (window!=null) window.updateEventList();
	}
	
	public void printList(ArrayList<Event> eventy){
		for (Event i : eventy)
			System.out.println(i);
		System.out.println();
	}
	
	public void print(){
		printList(eventy);
	}
	
	/**
	 * Sortuje przechowywane zdarzenia wed³ug daty.
	 */
	public void sort(){
		eventy.sort(new Event().new DateComparator());
		if (window!= null) window.updateEventList();
	}
	
	/*public void getEvents(){
		Inputter inp = new Inputter();
		while(true){
			String nazwa = inp.getString("Podaj nazwe zdarzenia:");
			String data = inp.getString("Podaj date zdarzenia [dd/mm/yyyy]:");
			if(nazwa.equals("0") && data.equals("0")) break;
			addEvent(nazwa, data);
			sort();
			printList(eventy);
		}
	}
	
	public void addEvent(String nazwa, String data){
		try{
			Event ev = new Event(nazwa, data);
			eventy.add(ev);
			
		} catch (DateFormatException d) {
			System.out.println(d.getMessage());
		}
	}*/
	
	@Override
	public int size() {
		return eventy.size();
	}

	/**
	 * Dodaje nowy obiekt typu Event do listy.
	 */
	@Override
	public boolean add(Event arg0) {
		if(isFiltered) return false;
		eventy.add(arg0);
		if (window!=null) window.updateEventList();
		return true;
	}
	
	/**
	 * Usuwa obiekt z kontenera.
	 * @param arg0 Obiekt klasy Event.
	 * @return Czy operacja siê powiod³a.
	 */
	@Override
	public boolean remove(Object arg0) {
		if(isFiltered){
			kopiaEventow.remove(arg0);
		}
		return eventy.remove(arg0);
	}
	
	/**
	 * Usuwa obiekt o podanym indeksie na liœcie.
	 * @param i Indeks usuwanego obiektu.
	 */
	public void removeAt(int i){
		if(i>=0 && i< eventy.size()) {
			Event usuwany = eventy.get(i);
			eventy.remove(usuwany);
			if(isFiltered){
				kopiaEventow.remove(usuwany);
			}
		}
		
	}
	
	/**
	 * @param i numer indeksu na liœcie
	 * @return Event o indeksie i
	 */
	public Event get(int i){
		return eventy.get(i);
	}
	
	/**
	 * Wykonuje zaawansowan¹ operacjê toString.
	 * @return Tablica stringów u¿ywana do ustawiania zawartoœci JList.
	 */
	public String[] ToStringArray(){
		ArrayList<String> strings = new ArrayList<String>();
		for(Event e : eventy)
			strings.add(e.toString());
		
		String[] result = new String[strings.size()];
		result = strings.toArray(result);
		return result;
	}
	
	/**
	 * 
	 * @param date1 Minimalna granica filtrowanych zdarzeñ
	 * @param date2 Maksymalna granica filtrowanych zdarzeñ
	 */
	public void dateFilter(java.util.Date date1, java.util.Date date2){
		if(!isFiltered){
			isFiltered = true;
			
			kopiaEventow = eventy;
			eventy = new ArrayList<Event>();
			for(Event e : kopiaEventow){
				if (e.getDate().compareTo(date1) >=0
						&& e.getDate().compareTo(date2) == -1)
				eventy.add(e);
			}
		}
	}
	
	/**
	 * Cofa filtrowanie
	 */
	public void defilter(){
		eventy = kopiaEventow;
		isFiltered = false;
	}
	
	/**
	 * Sprawdza, czy kontener zawiera dany obiekt.
	 * @param arg0 Szukany obiekt.
	 * @return czy kontener zawiera szukany obiekt.
	 */
	@Override
	public boolean contains(Object arg0) {
		return eventy.contains(arg0);
	}
	
	/**
	 * 
	 * @return Czy kontener jest pusty.
	 */
	@Override
	public boolean isEmpty() {
		return eventy.isEmpty();
	}
	
	/**
	 * sprawdza, czy kontener jest po filtrowaniu.
	 * @return True - kontener jest po filtorowaniu. False - kontener jest w stanie standardowym.
	 */
	public boolean getFiltered(){
		return isFiltered;
	}
	
	/**
	 * Usuwa wszystkie obiekty przed podan¹ dat¹.
	 * @param date Data, przed któr¹ zostan¹ skasowane zdarzenia.
	 */
	public void deleteBefore(java.util.Date date){
		ArrayList<Event> toDelete = new ArrayList<Event>();
		for (Event e : eventy){
			if (e.getDate().compareTo(date) == -1) toDelete.add(e);
		}
		
		for(Event e: toDelete){
			eventy.remove(e);
		}
		if (window!=null) window.updateEventList();
	}
	
	/**
	 * Pozwala ustawiæ referencjê do okna ju¿ po stworzeniu obiektu.
	 * @param win Ustawiane okno.
	 */
	public void SetWindow(Window win){
		window = win;
	}
	
	
	
	@Override
	public Object[] toArray() {
		return eventy.toArray();
	}
	
	
	/**
	 * Usuwa wszystkie elementy kontenera.
	 */
	@Override
	public void clear() {
		eventy = new ArrayList<Event>();
	}
	
	@Override
	public boolean addAll(Collection<? extends Event> arg0) {
		boolean powodzenie = eventy.addAll(arg0);
		if(window != null) window.updateEventList();
		return powodzenie;
	}
	
	/**
	 * Serializuje wszystkie obiekty do bazy danych SQL Server.
	 * @param saveLocation nazwa bazy danych.
	 */
	public void SerializeSqlServer(String saveLocation){
		SqlServerDatabase db = new SqlServerDatabase();
        db.dbConnect("jdbc:jtds:sqlserver://localhost:1433/master;","sa","wowowo");
        //db.executeWithResult("use HR; select last_name from employees; drop database huhue;");
        db.execute("USE [master]");
        db.execute("IF OBJECT_ID('componentProject') = NULL create database componentProject; ");
        db.execute("use componentProject; IF OBJECT_ID('dbo.events', 'U') IS NOT NULL drop table events; create table events (id INTEGER IDENTITY (1,1), nazwa VARCHAR(100), data VARCHAR(20), description VARCHAR(300), place VARCHAR(100), reminder INTEGER, primary key (id));");
        StringBuilder finalUpdate = new StringBuilder();
        for(Event e : eventy){
        	finalUpdate.append("insert into events(nazwa,data, description, place, reminder) values ('"+e.getName()+"', '"
        			+ e.getDateHour() +"', '"+ e.getDescription() +"', '"+ e.getPlace() +"', '"+ e.getReminder() + "'); ");
        }
        
        db.execute(finalUpdate.toString());
	}
	
	/**
	 * Deserializuje wszystkie obiekty z bazy danych SQL Server.
	 * @param loadLocation nazwa bazy danych do odczytu.
	 * @throws DateFormatException Mo¿liwe podanie daty w nieprawid³owym formacie do konstruktora zdarzenia.
	 * @throws SQLException B³¹d ³¹czenia z baz¹ danych.
	 */
	public void DeserializeSqlServer(String loadLocation) throws SQLException, DateFormatException{
		SqlServerDatabase db = new SqlServerDatabase();
        db.dbConnect("jdbc:jtds:sqlserver://localhost:1433/master;","sa","wowowo");
        ResultSet rs = db.executeWithResult("use componentProject; select nazwa,data, description, place, reminder from events");
    	ArrayList<Event> nowe = new ArrayList<Event>();
    	while (rs.next()) {
    	  Event ev = new Event(rs.getString("nazwa"), rs.getString("data"));
    	  ev.setDescription(rs.getString("description"));
    	  ev.setPlace(rs.getString("place"));
    	  ev.setReminder(rs.getInt("reminder"));
    	  nowe.add(ev);
    	  System.out.println(ev);
    	}
    	eventy = nowe;
    	if (window!=null) window.updateEventList();
	}
	
	/**
	 * Serializuje wszystkie obiekty do bazy danych Sqlite.
	 * @param saveLocation nazwa pliku bazy danych.
	 * @throws SQLException B³¹d ³¹czenia z baz¹ danych.
	 */
	public void SerializeSqlite(String saveLocation) throws SQLException{
		SqliteDatabase db= new SqliteDatabase();
		db.dbConnect(saveLocation);
        db.execute("drop table if exists events; create table events(id INTEGER IDENTITY (1,1), nazwa VARCHAR(100), data VARCHAR(20), description VARCHAR(300), place VARCHAR(100), reminder INTEGER, primary key (id))");
        StringBuilder finalUpdate = new StringBuilder();
        for(Event e : eventy){
        	finalUpdate.append("insert into events(nazwa,data, description, place, reminder) values ('"+e.getName()+"', '"
        			+ e.getDateHour() +"', '"+ e.getDescription() +"', '"+ e.getPlace() +"', '"+ e.getReminder() + "'); ");
        }
        db.execute(finalUpdate.toString());
	}
	
	
	/**
	 * Deserializuje wszystkie obiekty z bazy danych Sqlite
	 * @param loadLocation nazwa pliku bazy danych.
	 * @throws DateFormatException Mo¿liwe podanie daty w nieprawid³owym formacie do konstruktora zdarzenia.
	 * @throws SQLException B³¹d ³¹czenia z baz¹ danych.
	 */
	public void DeserializeSqlite(String loadLocation) throws SQLException, DateFormatException{
		SqliteDatabase db= new SqliteDatabase();
		db.dbConnect(loadLocation);
		ResultSet rs = db.executeWithResult("select nazwa,data, description, place, reminder from events");
    	ArrayList<Event> nowe = new ArrayList<Event>();
    	while (rs.next()) {
    	  Event ev = new Event(rs.getString("nazwa"), rs.getString("data"));
    	  ev.setDescription(rs.getString("description"));
    	  ev.setPlace(rs.getString("place"));
    	  ev.setReminder(rs.getInt("reminder"));
    	  nowe.add(ev);
    	  System.out.println(ev);
    	}
    	eventy = nowe;
    	if (window!=null) window.updateEventList();
	}
	
	/**
	 * Eksportuje zdarzenia do formatu iCalendar.
	 * @param saveLocation Nazwa pliku zapisu.
	 */
	public void SaveToICal(String saveLocation){
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(saveLocation);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
		icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
		icsCalendar.getProperties().add(Version.VERSION_2_0);
		icsCalendar.getProperties().add(CalScale.GREGORIAN);
		
		UidGenerator ug = null;
		try {
			ug = new UidGenerator("1");
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(Event e: eventy){
			DateTime dt = new DateTime(e.getMiliseconds());
			VEvent v = new VEvent(dt, e.getName());
			v.getProperties().add(ug.generateUid());
			v.getProperties().add(new Description(e.getDescription()));
			v.getProperties().add(new Location(e.getPlace()));
			icsCalendar.getComponents().add(v);
		}
		
		CalendarOutputter outputter = new CalendarOutputter();
		try {
			outputter.output(icsCalendar, fout);
		} catch (IOException | ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Wykonuje operacje czasowe. Odpowiada za sprawdzanie alertów. Jest wywo³ywane przez klasê Timer.
	 */
	@Override
	public void timeTick() {
		long currentTime = (new GregorianCalendar()).getTimeInMillis()/1000/60;
		for(Event e : eventy){
			long eventTime = e.getMiliseconds()/1000/60;
			int diff = (int) (eventTime-currentTime);
			if (e.remindedAbout == false && diff>0 && diff < e.getReminder()){
				if (window!=null) window.remindEventMessage(e, diff);
				e.remindedAbout = true;
			}
		}	
	}
}
