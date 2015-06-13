import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import edu.emory.mathcs.backport.java.util.Arrays;


public class XmlJavaSerial implements CollectionSerializator {
	
	
	@Override
	public void serialize(String saveLocation, Collection<?> d) {
		// TODO Auto-generated method stub
		ArrayList arrayList = new ArrayList(Arrays.asList(d.toArray()));
		XMLEncoder encoder;
		try {
			encoder = new XMLEncoder(
			      new BufferedOutputStream(
			        new FileOutputStream(saveLocation)));
			encoder.writeObject(arrayList);
	        encoder.close();
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public <T> ArrayList<T> deserialize(String loadLocation)
			throws IOException {
		XMLDecoder decoder;
		decoder = new XMLDecoder(new BufferedInputStream(
            new FileInputStream(loadLocation)));
		
		ArrayList<T> readObject = (ArrayList)decoder.readObject();
		decoder.close();
		return readObject;
        
	}

}
