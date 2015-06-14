
public interface CollectionExtender<T> {
	void print();
	void sort();
	
	/**
	 * Wywo³uje metodê toString wszystkich obiektów znajduj¹cych siê w kontenerze i zwraca w formie tablicy.
	 * @return Tablica stringów okreœlaj¹cych elementy kontenera. Mo¿e s³u¿yæ na przyk³ad do obs³ugi JList.
	 */
	String[] ToStringArray();
	
	/**
	 * Prosta metoda zwracaj¹ca obiekt o podanym indeksie w kolekcji.
	 * @param i numer indeksu na liœcie
	 * @return Event o indeksie i
	 */
	T get(int i);
}
