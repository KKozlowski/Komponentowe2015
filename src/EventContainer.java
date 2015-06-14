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
import java.util.Iterator;
import java.io.*;
import java.net.SocketException;

import net.fortuna.ical4j.data.*;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;

import com.thoughtworks.xstream.XStream;

/**
 * Klasa s³u¿¹ca do przechowywania zbioru zdarzeñ oraz wykonywania na nim prostych operacji.
 */
public class EventContainer extends CollectionAdapter<Event> implements CollectionExtender<Event>, Tickable, FilterableByDate {
	/**
	 * Lista zdarzeñ
	 */
	private ArrayList<Event> eventy = new ArrayList<Event>();
	
	/**
	 * Okno wyœwietlaj¹ce zawartoœæ kolekcji (mo¿e byæ null). Pole wykorzystywane do wywo³ywania odœwie¿ania widoku.
	 */
	private EventListWindow window;
	
	/**
	 * Wskazuje, czy kontener jest w trakcie filtrowania.
	 */
	private boolean isFiltered;
	
	/**
	 * Kopia list zdarzeñ na czas filtrowania.
	 */
	private ArrayList<Event> kopiaEventow;
	
	/**
	 * Postawowy konstruktor
	 */
	public EventContainer(){
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
	public EventContainer(EventListWindow mainWin){
		this();
		window = mainWin;
		
		try {
			add(new Event("Hue", "11/11/2011"));
		} catch (DateFormatException e) {
			e.printStackTrace();
		}

		if (window!=null) window.refreshDisplayedList();
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
		if (window!= null) window.refreshDisplayedList();
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
		if (window!=null) window.refreshDisplayedList();
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
	
	@Override
	public Event get(int i){
		return eventy.get(i);
	}
	
	@Override
	public String[] ToStringArray(){
		ArrayList<String> strings = new ArrayList<String>();
		for(Event e : eventy)
			strings.add(e.toString());
		
		String[] result = new String[strings.size()];
		result = strings.toArray(result);
		return result;
	}
	
	@Override
	public void filterByDate(java.util.Date date1, java.util.Date date2){
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
	
	@Override
	public void defilter(){
		eventy = kopiaEventow;
		isFiltered = false;
	}
	
	@Override
	public boolean getFiltered(){
		return isFiltered;
	}
	
	@Override
	public void deleteBefore(java.util.Date date){
		ArrayList<Event> toDelete = new ArrayList<Event>();
		for (Event e : eventy){
			if (e.getDate().compareTo(date) == -1) toDelete.add(e);
		}
		
		for(Event e: toDelete){
			eventy.remove(e);
		}
		if (window!=null) window.refreshDisplayedList();
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
	 * Pozwala ustawiæ referencjê do okna ju¿ po stworzeniu obiektu.
	 * @param win Ustawiane okno.
	 */
	public void SetWindow(EventListWindow win){
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
		if(window != null) window.refreshDisplayedList();
		return powodzenie;
	}
	
	@Override
	public Iterator<Event> iterator() {
		return eventy.iterator();
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
