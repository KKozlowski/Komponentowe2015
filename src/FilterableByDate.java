
public interface FilterableByDate {
	/**
	 * Filtruje przechowywane zdarzenia, daj�c dost�p do wszystkich w podanym przedziale czasowym.
	 * @param date1 Minimalna granica filtrowanych zdarze�
	 * @param date2 Maksymalna granica filtrowanych zdarze�
	 */
	void filterByDate(java.util.Date date1, java.util.Date date2);
	
	/**
	 * Cofa filtrowanie
	 */
	void defilter();
	
	/**
	 * sprawdza, czy kontener jest po filtrowaniu.
	 * @return True - kontener jest po filtorowaniu. False - kontener jest w stanie standardowym.
	 */
	boolean getFiltered();
	
	/**
	 * Usuwa wszystkie obiekty przed podan� dat�.
	 * @param date Data, przed kt�r� zostan� skasowane zdarzenia.
	 */
	public void deleteBefore(java.util.Date date);
}
