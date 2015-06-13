import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public interface CollectionSerializator {
	/**
	 * Serializuje wszystkie obiekty do pliku XML za pomoc� standardowej biblioteki Javy
	 * @param saveLocation nazwa pliku zapisu.
	 * @param d Dowolna kolekcja
	 */
	void serialize(String saveLocation, Collection<?> d);
	
	/**
	 * Deserializuje wszystkie obiekty z pliku XML za pomoc� standardowej biblioteki Javy
	 * @param loadLocation nazwa pliku odczytu.
	 * @throws IOException Plik nie odnaleziony.
	 */
	<T> ArrayList<T> deserialize(String loadLocation) throws IOException;
}
