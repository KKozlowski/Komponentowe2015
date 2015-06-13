import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public interface CollectionSerializator {
	void SerializeXstream(String saveLocation, Collection<?> d);
	<T> ArrayList<T> DeserializeXstream(String loadLocation) throws IOException;
}
