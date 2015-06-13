import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


class SqlServerSerial {
	/**
	 * Serializuje wszystkie obiekty do bazy danych SQL Server.
	 * @param saveLocation nazwa bazy danych.
	 * @param d Kolekcja Eventów
	 */
	void serialize(String saveLocation, Collection<Event> d){
		SqlServerDatabase db = new SqlServerDatabase();
        db.dbConnect("jdbc:jtds:sqlserver://localhost:1433/master;","sa","wowowo");
        //db.executeWithResult("use HR; select last_name from employees; drop database huhue;");
        db.execute("USE [master]");
        db.execute("IF OBJECT_ID('componentProject') = NULL create database componentProject; ");
        db.execute("use componentProject; IF OBJECT_ID('dbo.events', 'U') IS NOT NULL drop table events; create table events (id INTEGER IDENTITY (1,1), nazwa VARCHAR(100), data VARCHAR(20), description VARCHAR(300), place VARCHAR(100), reminder INTEGER, primary key (id));");
        StringBuilder finalUpdate = new StringBuilder();
        for(Event e : d){
        	finalUpdate.append("insert into events(nazwa,data, description, place, reminder) values ('"+e.getName()+"', '"
        			+ e.getDateHour() +"', '"+ e.getDescription() +"', '"+ e.getPlace() +"', '"+ e.getReminder() + "'); ");
        }
        
        db.execute(finalUpdate.toString());
	}
	
	/**
	 * Deserializuje wszystkie obiekty z bazy danych SQL Server.
	 * @param loadLocation nazwa nazwa bazy danych.
	 * @throws DateFormatException Mo¿liwe podanie daty w nieprawid³owym formacie do konstruktora zdarzenia.
	 * @throws SQLException B³¹d ³¹czenia z baz¹ danych.
	 */
	ArrayList<Event> deserialize(String loadLocation) throws SQLException, DateFormatException{
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
    	return nowe;
	}
}
