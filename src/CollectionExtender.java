
public interface CollectionExtender<T> {
	void print();
	void sort();
	
	/**
	 * Wywo�uje metod� toString wszystkich obiekt�w znajduj�cych si� w kontenerze i zwraca w formie tablicy.
	 * @return Tablica string�w okre�laj�cych elementy kontenera. Mo�e s�u�y� na przyk�ad do obs�ugi JList.
	 */
	String[] ToStringArray();
	
	/**
	 * Prosta metoda zwracaj�ca obiekt o podanym indeksie w kolekcji.
	 * @param i numer indeksu na li�cie
	 * @return Event o indeksie i
	 */
	T get(int i);
}
