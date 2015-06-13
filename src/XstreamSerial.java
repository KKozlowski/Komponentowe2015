import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.thoughtworks.xstream.XStream;

import edu.emory.mathcs.backport.java.util.Arrays;


public class XstreamSerial implements CollectionSerializator {
	/**
	 * Serializuje wszystkie obiekty do pliku XML za pomoc¹ biblioteki Xstream
	 * @param saveLocation nazwa pliku zapisu.
	 */
	
	/**
	 * Serializator xstream
	 */
	XStream xstream;
	
	public XstreamSerial(){
		xstream = new XStream();
	}
	
	/**
	 * Serializuje wszystkie obiekty do pliku XML za pomoc¹ biblioteki Xstream
	 * @param saveLocation nazwa pliku zapisu.
	 * 
	 */
	
	/**
	 * Serializuje wszystkie obiekty do pliku XML za pomoc¹ biblioteki Xstream
	 * @param saveLocation nazwa pliku zapisu.
	 * @param d Dowolna kolekcja
	 */
	public void SerializeXstream(String saveLocation, Collection<?> d){
		ArrayList arrayList = new ArrayList(Arrays.asList(d.toArray()));
		String serial = xstream.toXML(arrayList);
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
	 * @return Kolekcja zawieraj¹ca zdeserializowane obiekty.
	 * @throws IOException Plik nie odnaleziony
	 */
	public <T> ArrayList<T> DeserializeXstream(String loadLocation) throws IOException{
		File file = new File(loadLocation);
		byte[] bytes = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(bytes);
		String str = new String(bytes);
		//System.out.println(str);
		ArrayList<T> eventy = (ArrayList)xstream.fromXML(str);
		return (ArrayList<T>)eventy;
	}
}
