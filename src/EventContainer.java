import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class EventContainer implements ObjectContainer {
	ArrayList<Event> eventy = new ArrayList<Event>();
	Window window;
	
	public EventContainer(){
		try{
			Event ev = new Event("Spotkanie", "02/03/2014");
			Event ev2 = new Event("Spotkanie2", "05/03/2014");
			add(ev);
			add(ev2);
			add(new Event("Spotkanie3", "05/02/2014"));
			eventy.sort(ev.new DateComparator());
		}
		catch (DateFormatException d) {
			System.out.println(d.getMessage());
		}
		
		
		
		printList(eventy);
	}
	
	public EventContainer(Window mainWin){
		this();
		window = mainWin;
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

	@Override
	public void add(Object added) {
		eventy.add((Event) added);
		
	}
}
