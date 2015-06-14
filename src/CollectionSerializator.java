import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public interface CollectionSerializator {
	/**
	 * Serializuje wszystkie obiekty do pliku.
	 * @param saveLocation nazwa pliku zapisu.
	 * @param d Dowolna kolekcja
	 */
	void serialize(String saveLocation, Collection<?> d);
	
	/**
	 * Deserializuje wszystkie obiekty z pliku.
	 * @param loadLocation nazwa pliku odczytu.
	 * @param <T> klasa obiektów, które zawiera kolekcja.
	 * @return ArrayLista zawieraj¹ca wszystkie elementy zdeserializowanego obiektu.
	 * @throws IOException Plik nie odnaleziony.
	 */
	<T> ArrayList<T> deserialize(String loadLocation) throws IOException;
}
