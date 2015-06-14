import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.thoughtworks.xstream.XStream;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Wykonuje operacje serializacji i deserializacji do pliku Xml za pomoc¹ biblio
 */
public class XstreamSerial implements CollectionSerializator {
	/**
	 * Serializuje wszystkie obiekty do pliku XML za pomoc¹ biblioteki Xstream
	 * @param saveLocation nazwa pliku zapisu.
	 */
	
	/**
	 * Serializator xstream
	 */
	private XStream xstream;
	
	public XstreamSerial(){
		xstream = new XStream();
	}
	
	/**
	 * Serializuje wszystkie obiekty do pliku XML za pomoc¹ biblioteki Xstream
	 * @param saveLocation nazwa pliku zapisu.
	 * 
	 */
	
	@Override
	public void serialize(String saveLocation, Collection<?> d){
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
	
	@Override
	public <T> ArrayList<T> deserialize(String loadLocation) throws IOException{
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
