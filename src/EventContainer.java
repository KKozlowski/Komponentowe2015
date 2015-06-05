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
import java.io.*;

import com.thoughtworks.xstream.XStream;

public class EventContainer implements ObjectContainer, Tickable {
	ArrayList<Event> eventy = new ArrayList<Event>();
	Window window;
	XStream xstream;
	private boolean isFiltered;
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
		SerializeXstream("XXML.xml");
		SerializeXmlJava("XML.xml");
		DeserializeXmlJava("XML.xml");
		DeserializeXstream("XXML.xml");
		try {
			add(new Event("Hue", "11/11/2011"));
		} catch (DateFormatException e) {
			e.printStackTrace();
		}
		SerializeSqlServer("componentProject");
		DeserializeSqlServer("componentProject");
		SerializeSqlite("test.db");
		DeserializeSqlite("test.db");
		window.updateEventList();
	}
	
	public void printList(ArrayList<Event> eventy){
		for (Event i : eventy)
			System.out.println(i);
		System.out.println();
	}
	
	public void print(){
		printList(eventy);
	}
	
	public void sort(){
		eventy.sort(new Event().new DateComparator());
		if (window!= null) window.updateEventList();
	}
	
	public void getEvents(){
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
	}
	
	public int getSize(){
		return eventy.size();
	}
	
	public void saveToBin(){
		try {
			DataOutputStream eventStream = 		// Strumien zapisujacy liczby
				new DataOutputStream(new FileOutputStream("data/events.bin")); 
			
			eventStream.writeInt(getSize());
			for (int i=0; i< getSize(); i++){
				//strumienTablicy.writeChars(eventy.get(i).getName());
				eventStream.writeUTF(eventy.get(i).getName());
				eventStream.writeLong(eventy.get(i).getMiliseconds());
				
			}
			eventStream.close();
			
			
		}
		catch (IOException io)												
			{System.out.println(io.getMessage());}

		catch (Exception se)
			{System.err.println("blad sec");}
		
	}
	
	public void loadFromBin(){
		try {
			DataInputStream eventStream = 
				new DataInputStream(new FileInputStream("data/events.bin"));
			
			int rozmiar = eventStream.readInt();
			eventy.clear();
			for(int i=0; i<rozmiar; i++){
				eventy.add(new Event(eventStream.readUTF(), eventStream.readLong()));
			}
			eventStream.close();
		}
		catch (FileNotFoundException io)												
			{System.out.println(io.getMessage());}
	
		catch (IOException io)												
			{System.out.println(io.getMessage());} 
		sort();
	}
	
	public void saveObjects(){
		try {
			//Zapisywanie ca³ych obiektów do pliku.
			FileOutputStream fileOut = new FileOutputStream("data/objects.bin");
			
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeInt(getSize());
			
			for(int i=0; i<getSize();i++){
				out.writeObject(eventy.get(i));
			}
			
			out.close();
			fileOut.close();
			
		}
		catch (Exception e)
		{System.out.println("Nie udalo sie zapisac do pliku");}
	}
	
	public void loadObjects(){
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream("data/objects.bin");
		} catch (FileNotFoundException e) {
			System.out.println("Nie odnaleziono pliku z danymi.");
			return;
		}
		try {
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			int rozmiar = in.readInt();
			eventy.clear();

			for(int i=0; i<rozmiar;i++){
				try {
					Event obj = (Event)in.readObject();
					eventy.add(obj);
				} catch (ClassNotFoundException e) {
					System.out.println("Nie odnaleziono klasy");
					return;
				}
			}
		} catch (IOException e) {
			System.out.println("Wystapil blad podczas wczytywania danych");
		}
	}
	
	/**
	 * Dodaje nowy obiekt typu Event do listy.
	 */
	@Override
	public void add(Object added) {
		if((Event)added != null){
			eventy.add((Event) added);
			sort();
			if (window!=null) window.updateEventList();
		}
	}
	
	/**
	 * Usuwa obiekt o podanym indeksie na liœcie.
	 * @param i
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
	 * 
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
	 * @param date Data, wed³ug której jest s¹ filtrowane obiekty
	 * @param greater Czy filtrowane obiekty maj¹ mieæ daty wiêksze, czy mniejsze od podanej daty.
	 */
	public void dateFilter(java.util.Date date1, java.util.Date date2){
		if(!isFiltered){
			isFiltered = true;
			
			kopiaEventow = eventy;
			eventy = new ArrayList<Event>();
			for(Event e : kopiaEventow){
				if (e.getDate().compareTo(date1) == 1
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
	 * sprawdza, czy kontener jest po filtrowaniu.
	 * @return
	 */
	public boolean getFiltered(){
		return isFiltered;
	}
	
	/**
	 * Usuwa wszystkie obiekty przed podan¹ dat¹.
	 * @param date
	 */
	public void deleteBefore(java.util.Date date){
		ArrayList<Event> toDelete = new ArrayList<Event>();
		for (Event e : eventy){
			if (e.getDate().compareTo(date) == -1) toDelete.add(e);
		}
		
		for(Event e: toDelete){
			eventy.remove(e);
		}
		window.updateEventList();
	}
	
	public void SetWindow(Window win){
		window = win;
	}
	
	/**
	 * Serializuje wszystkie obiekty do pliku XML za pomoc¹ biblioteki Xstream
	 * @param saveLocation nazwa pliku zapisu.
	 */
	public void SerializeXstream(String saveLocation){
		String serial = xstream.toXML(eventy);
		FileOutputStream fos = null;
		try {
		    fos = new FileOutputStream(saveLocation);
		    fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8")); 
		    byte[] bytes = serial.getBytes("UTF-8");
		    fos.write(bytes);

		} catch(Exception e) {
		    e.printStackTrace(); 
		} finally {
		    if(fos!=null) {
		        try{ 
		            fos.close();
		        } catch (IOException e) {
		            e.printStackTrace(); 
		        }
		    }
		}
	}
	
	/**
	 * Deserializuje wszystkie obiekty z pliku XML za pomoc¹ biblioteki Xstream
	 * @param loadLocation nazwa pliku odczytu.
	 */
	public void DeserializeXstream(String loadLocation){
		File file = new File(loadLocation);
		byte[] bytes = new byte[(int) file.length()];
		try {
			FileInputStream fis = new FileInputStream(file);
			fis.read(bytes);
			String str = new String(bytes);
			//System.out.println(str);
			eventy = (ArrayList<Event>)xstream.fromXML(str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		window.updateEventList();
	}
	
	/**
	 * Serializuje wszystkie obiekty do pliku XML za pomoc¹ standardowej biblioteki Javy
	 * @param saveLocation nazwa pliku zapisu.
	 */
	public void SerializeXmlJava(String saveLocation){
		XMLEncoder encoder;
		try {
			encoder = new XMLEncoder(
			      new BufferedOutputStream(
			        new FileOutputStream(saveLocation)));
			encoder.writeObject(eventy);
	        encoder.close();
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Deserializuje wszystkie obiekty z pliku XML za pomoc¹ standardowej biblioteki Javy
	 * @param loadLocation nazwa pliku odczytu.
	 */
	public void DeserializeXmlJava(String loadLocation){
		
		XMLDecoder decoder;
		try {
			decoder = new XMLDecoder(new BufferedInputStream(
                new FileInputStream(loadLocation)));
			
			ArrayList<Event> readObject = (ArrayList<Event>)decoder.readObject();
			eventy = readObject;
	        decoder.close();
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        window.updateEventList();
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
	 */
	public void DeserializeSqlServer(String loadLocation){
		SqlServerDatabase db = new SqlServerDatabase();
        db.dbConnect("jdbc:jtds:sqlserver://localhost:1433/master;","sa","wowowo");
        ResultSet rs = db.executeWithResult("use componentProject; select nazwa,data, description, place, reminder from events");
        try{
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
        }
        catch(SQLException s){
        	s.printStackTrace();
        } catch (DateFormatException e) {
			e.printStackTrace();
		}
        window.updateEventList();
	}
	
	/**
	 * Serializuje wszystkie obiekty do bazy danych Sqlite.
	 * @param saveLocation nazwa pliku bazy danych.
	 */
	public void SerializeSqlite(String saveLocation){
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
	 */
	public void DeserializeSqlite(String loadLocation){
		SqliteDatabase db= new SqliteDatabase();
		db.dbConnect(loadLocation);
		ResultSet rs = db.executeWithResult("select nazwa,data, description, place, reminder from events");
        try{
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
        }
        catch(SQLException s){
        	s.printStackTrace();
        } catch (DateFormatException e) {
			e.printStackTrace();
		}
        window.updateEventList();
	}
	
	/**
	 * Wykonuje operacje czasowe. Odpowiada za sprawdzanie alertów. Jest wywo³ywane przez klasê Timer.
	 */
	@Override
	public void timeTick() {
		System.out.println("Tick");		
	}
}
