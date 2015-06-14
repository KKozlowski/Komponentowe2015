import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Wykonuje operacje serializacji i deserializacji do bazy danych Sqlite.
 */
public class SqliteSerial {
	/**
	 * Serializuje wszystkie obiekty do bazy danych Sqlite.
	 * @param saveLocation nazwa bazy danych.
	 * @param d Kolekcja Eventów
	 * @throws SQLException B³¹d ³¹czenia z baz¹ danych.
	 */
	public void serialize(String saveLocation, Collection<Event> d) throws SQLException{
		SqliteDatabase db= new SqliteDatabase();
		db.dbConnect(saveLocation);
        db.execute("drop table if exists events; create table events(id INTEGER IDENTITY (1,1), nazwa VARCHAR(100), data VARCHAR(20), description VARCHAR(300), place VARCHAR(100), reminder INTEGER, primary key (id))");
        StringBuilder finalUpdate = new StringBuilder();
        for(Event e : d){
        	finalUpdate.append("insert into events(nazwa,data, description, place, reminder) values ('"+e.getName()+"', '"
        			+ e.getDateHour() +"', '"+ e.getDescription() +"', '"+ e.getPlace() +"', '"+ e.getReminder() + "'); ");
        }
        db.execute(finalUpdate.toString());
	}
	
	/**
	 * Deserializuje wszystkie obiekty z pliku bazy danych Sqlite.
	 * @param loadLocation nazwa pliku odczytu.
	 * @throws DateFormatException Mo¿liwe podanie daty w nieprawid³owym formacie do konstruktora zdarzenia.
	 * @throws SQLException B³¹d ³¹czenia z baz¹ danych.
	 * @return ArrayList zawieraj¹ca wszystkie elementy zdeserializowanej kolekcji.
	 */
	public ArrayList<Event> deserialize(String loadLocation) throws SQLException, DateFormatException{
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
    	return nowe;
	}
}
