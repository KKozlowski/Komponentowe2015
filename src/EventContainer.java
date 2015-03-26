import java.util.ArrayList;


public class EventContainer {
	ArrayList<Event> eventy = new ArrayList<Event>();
	
	public EventContainer(){
		Event ev = new Event("Spotkanie", "02/03/2014");
		Event ev2 = new Event("Spotkanie2", "05/03/2014");
		eventy.add(ev);
		eventy.add(ev2);
		eventy.add(new Event("Spotkanie3", "05/02/2014"));
		
		eventy.sort(ev.new DateComparator());
		
		PrintList(eventy);
	}
	
	public void PrintList(ArrayList<Event> eventy){
		for (Event i : eventy)
			System.out.println(i);
		System.out.println();
	}
	
	public void GetEvents(){
		Inputter inp = new Inputter();
		while(true){
			String nazwa = inp.GetString("Podaj nazwe zdarzenia:");
			String data = inp.GetString("Podaj date zdarzenia [dd/mm/yyyy]:");
			if(nazwa.equals("0") && data.equals("0")) break;
			Event ev = new Event(nazwa, data);
			eventy.add(ev);
			eventy.sort(ev.new DateComparator());
			PrintList(eventy);
		}
	}
}
