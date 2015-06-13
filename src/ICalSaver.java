import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;


public class ICalSaver {

	/**
	 * Eksportuje zdarzenia do formatu iCalendar.
	 * @param saveLocation Nazwa pliku zapisu.
	 *  @param d kolekcja zawieraj¹ca zdarzenia.
	 */
	public void serialize(String saveLocation, Collection<? extends Event> d) {
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
		
		for(Event e: d){
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

}
